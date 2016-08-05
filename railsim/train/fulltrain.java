/*
 * $Revision: 23 $
 * Copyright 2008 js-home.org
 * $Name: not supported by cvs2svn $
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.railsim.train;

import java.util.*;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.railsim.dataCollector;
import org.railsim.service.*;
import org.railsim.service.exceptions.NotConnectedException;
import org.railsim.service.exceptions.PositionNotFoundException;
import org.railsim.service.exceptions.TrainStoppedException;
import org.railsim.service.trackObjects.pathableObject;
import org.railsim.service.trackObjects.stopObject;
import org.railsim.service.trackObjects.trackObject;
import org.railsim.service.trainorders.*;
import org.xml.sax.Attributes;

/**
 *
 * @author js
 */
public class fulltrain extends LinkedList<trainpart> {

	public static final int NOTEMPVMAX = -1;
	static public boolean PAINTPRERUNNER = false;
	static public boolean paintTrainData = false;
	private boolean forward = true;                   // save(X)
	private boolean ontrack = false;                  // not saved, true after load
	private double vCurrent = 0;                      // save(X), current speed
	private int vMax = 0;                             // save(X), max allowed speed by sign
	private int vTrainMax = 0;                        // save(X), max allowed speed of train
	private int vTempMax = -1;                        // save(X), temparary allowed max speed - eg. by signal
	private int acceleration = 1;                     // save(X)
	private int deceleration = 1;                     // save(X)
	private int weight = 0;                           // save(X)
	volatile private rectangle bounds = null;         // not saved, calc after load
	private stopObject waitSignal = null;             // save(X)
	private trackpoint prerunner = null;              // save(X)
	private trackpoint prerunnerA2 = null;            // save(X)
	private trainpart prerunner_firstpart = null;     // not saved, calc after load
	private stopObject prerunner_waitSignal = null;   // save(X)
	volatile private long prerunner_pos = 0;          // save(X)
	volatile private long runner_pos = 0;             // save(X)
	private ConcurrentLinkedQueue<trainorder> orders = new ConcurrentLinkedQueue<>();   // save(X)
	public java.util.concurrent.ConcurrentHashMap<String, String> store = new java.util.concurrent.ConcurrentHashMap<>(); // save(X)
	private volatile int stopObjectCounter = 0;	    // save(X)
	private String name = "Zug X";                    // save(X)
	private reducedstarttrain startTrain = null;      // save(X) name
	private long creationGametime = 0;                // save(X)
	private trainCommandExecutor mainexec = new trainCommandExecutor(this, false);         // save local store
	private trainCommandExecutor mainexec_c = mainexec;
	private trainCommandExecutor preexec = new trainCommandExecutor(this, true, mainexec);  // save local store
	private trainCommandExecutor preexec_c = preexec;
	private trackpoint manual_lastprerunner = null;   // save(X)
	private trackpoint manual_whenleft = null;        // save(X)
	private boolean manualmode = false;               // save(X)

	/**
	 * Creates a new instance of fulltrain
	 */
	public fulltrain() {
		super();
		vTrainMax = statics.speed2Steps(300);// TODO: test, ändern
		vMax = vTrainMax;      // TODO: test, ändern
		this.setAcceleration(0.7);
		this.setDeceleration(1.1);
	}

	public fulltrain(reducedstarttrain st, String _name, int tvmax, float accel, float decel, int _weight) {
		super();
		startTrain = st;
		name = _name;
		vTrainMax = statics.speed2Steps(tvmax);
		vMax = vTrainMax;
		weight = _weight;
		this.setAcceleration(accel);
		this.setDeceleration(decel);
	}

	public fulltrain(starttrain st, String _name, int tvmax, float accel, float decel) {
		super();
		startTrain = st;
		name = _name;
		vTrainMax = statics.speed2Steps(tvmax);
		vMax = vTrainMax;
		weight = st.getWeight();
		this.setAcceleration(accel);
		this.setDeceleration(decel);
	}

	/**
	 *
	 * @param dir direction
	 * @param kmh Speed
	 */
	public fulltrain(boolean dir, int kmh) {
		this();
		forward = dir;
		vCurrent = statics.speed2Steps(kmh);
	}

	/**
	 *
	 * @param dir direction
	 */
	public fulltrain(boolean dir) {
		this();
		forward = dir;
		vCurrent = 0;
	}

	public void paint(Graphics2D g2, int onlylevel) {
		if (ontrack) {
			if (prerunner != null && PAINTPRERUNNER) {
				g2.setColor(Color.YELLOW);
				g2.fillOval(prerunner.getX() - 4, prerunner.getY() - 4, 8, 8);
			}

			Iterator<trainpart> it = this.iterator();
			while (it.hasNext()) {
				trainpart p = it.next();
				if (p.isOnTrack()) {
					p.paint(g2, onlylevel);
				}
			}
			if (paintTrainData && prerunner_firstpart != null && prerunner_firstpart.getA1().getTrack().getLevel() == onlylevel && bounds != null) {
				int x = (int) bounds.getCenterX();
				int y = (int) bounds.getCenterY();
				g2.setColor(Color.WHITE);
				g2.drawString(name + ": " + mainexec.getRoute() + " " + runner_pos + " -- " + prerunner_pos, x, y);
				g2.drawString("C:" + statics.steps2Speed(vCurrent) + " km/h, M:" + statics.steps2Speed(vMax) + " km/h, tM:" + statics.steps2Speed(vTempMax) + " km/h", x, y + 11);
			}
		}
	}

	/**
	 * Train on track, if not all parts can be placed, only a part is placed,
	 * returns true if full train is on track, false if only parts
	 *
	 * @param t trackpoint where to start placement
	 * @param negativ false: follow track direction t, true: opposide track direction (typical value)
	 * @return returns true if full train is on track, false if only parts
	 * @see isOnTrack(): if no part could be placed, inOnTrack() returns false
	 */
	public boolean onTrack(trackpoint t, boolean negativ) {
		if (t != null) {
			ontrack = false;
		}
		trackpoint ref = null, a1, a2;
		int distanceBack = 0;
		int i = 0;
		Iterator<trainpart> it = this.iterator();
		while (it.hasNext()) {
			++i;
			trainpart p = it.next();
			if (p.isOnTrack()) {
				a1 = p.getA1();
				a2 = p.getA2();
				ref = a2;
				try {
					negativ = (ref.getTrack().getPointDistance(a1, a2) < 0);
				} catch (PositionNotFoundException ex) {
					dataCollector.collector.gotException(ex);
				}
			} else {
				if (ref == null) {
					a1 = t;
				} else {
					a1 = ref.getTrack().onTrack(ref, (distanceBack + p.getDistanceFront()) * (negativ ? -1 : 1));
					if (a1 == null) {
						System.out.println("V:" + i);
						return false;
					}
					try {
						if (!ref.getTrack().hasSameOriantation(a1.getTrack())) {
							negativ = !negativ;
						}
					} catch (NotConnectedException ex) {
						dataCollector.collector.gotException(ex);
					}
				}
				ref = a1;
				if (ref == null || ref.getTrack() == null) {
					return false;
				}
				trackpoint ref2 = ref.getTrack().onTrack(ref, p.getDistance() * (negativ ? -1 : 1));
				if (ref2 == null || ref2.getTrack() == null) {
					return false;
				}
				try {
					if (!ref2.getTrack().hasSameOriantation(ref.getTrack())) {
						negativ = !negativ;
					}
				} catch (NotConnectedException ex) {
					dataCollector.collector.gotException(ex);
				}

				ref = ref2;
				a2 = ref;
				p.onTrack(a1, a2);
				ontrack = true;
			}
			distanceBack = p.getDistanceBack();
		}
		return ontrack;
	}

	/**
	 * places train behind track object
	 *
	 * @param t trackObject
	 * @return returns true if full train is on track, false if only parts
	 */
	public boolean onTrack(trackObject t) {
		return onTrack(t.getTrackData(), t.getTrackData().getForward());
	}

	/**
	 * place train on track
	 *
	 * @param t track
	 * @return returns true if full train is on track, false if only parts
	 */
	public boolean onTrack(track t) {
		return onTrack(t.onTrack(), true);
	}

	public void offTrack() {
		Iterator<trainpart> it = this.iterator();
		while (it.hasNext()) {
			trainpart p = it.next();
			p.offTrack();
		}
		prerunner = null;
		ontrack = false;
	}

	public boolean changeDirection() {
		forward = !forward;
		prerunner = null;
		prerunner_waitSignal = null;
		waitSignal = null;
		stopObjectCounter = 0;
		return forward;
	}

	public boolean isForward() {
		return forward;
	}

	public boolean turnAround() {
		if (!ontrack) {
			return false;
		}

		trackpoint t = this.getLast().getA2();
		boolean negativ = false;
		trackpoint ref = null, a1, a2;
		a1 = this.getLast().getA1();
		a2 = this.getLast().getA2();
		int d;
		try {
			d = a1.getTrack().getPointDistance(a1, a2);
		} catch (PositionNotFoundException ex) {
			dataCollector.collector.gotException(ex);
			return false;
		}
		if (d > 0) {
			negativ = true;
		}
		offTrack();

		return onTrack(t, negativ);
	}

	public boolean isOnTrack() {
		return ontrack;
	}

	public boolean isNextSignalRed() { // TODO: stopObjectCounter Prüfwert richtig?
		return stopObjectCounter <= 1 && prerunner != null;
	}

	public void passedStopObject(stopObject so, int step) {
		if ((step & trackObject.TRAINSTEP_PRERUNNER) != 0) {
			stopObjectCounter++;
		} else if ((step & trackObject.TRAINSTEP_START) != 0 && stopObjectCounter > 0) {
			stopObjectCounter--;
		}
	}

	public void prerunner() {
		//System.out.println("preRunner:"+this.getName());
		if (ontrack) {
			int dcnt = 300; // max number of steps until leave
			while (stopObjectCounter < 3 && dcnt > 0) {
				if (preexec.nextCommand()) {
					if (prerunner == null && (waitSignal == null || !(waitSignal instanceof pathableObject) || prerunner_waitSignal == null)) {
						prerunner_waitSignal = null;
						prerunner_firstpart = null;
						if (forward) {
							prerunner_firstpart = getFirst();
						} else {
							prerunner_firstpart = getLast();
						}
						if (prerunner_firstpart.trainRelativForward == forward) {
							prerunner = prerunner_firstpart.getA1();
							prerunnerA2 = prerunner_firstpart.getA2();
						} else {
							prerunner = prerunner_firstpart.getA2();
							prerunnerA2 = prerunner_firstpart.getA1();
						}
						prerunner_pos = 0;
						runner_pos = 0;
						orders.clear();
					}
					if (prerunner_waitSignal == null && prerunner != null) {
						trackpoint r2 = null;
						trackpoint a2 = null;
						try {
							r2 = prerunner.getTrack().nextPoint1(this, prerunner, prerunnerA2, trackObject.TRAINSTEP_PRERUNNER);
							a2 = prerunnerA2.getTrack().nextPoint2(this, prerunner, prerunnerA2, trackObject.TRAINSTEP_PRERUNNER);
							if (r2 != null && a2 != null) {
								prerunner = r2;
								prerunnerA2 = a2;
								++prerunner_pos;
							} else {
								break;
							}
							--dcnt;
						} catch (TrainStoppedException ex) {
							prerunner_waitSignal = ex.getStopObject();
							if (prerunner_waitSignal instanceof pathableObject) {
								if (!((pathableObject) prerunner_waitSignal).setPath(this)) {
									prerunner_waitSignal = null;
								}
							}
							break;
						}
					} else if (prerunner_waitSignal != null) {
						if (!prerunner_waitSignal.isStoppedT(this, trackObject.TRAINSTEP_PRERUNNER)) {
							prerunner_waitSignal = null;
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}
		}
	}

	public void run() {
		rectangle _bounds = null;
		if (ontrack) {
			if (manual_lastprerunner != null || manual_whenleft != null) {
				trackpoint p;
				if (forward) {
					p = this.getFirst().getA1();
				} else {
					p = this.getLast().getA2();
				}

				if (manual_whenleft != null && manual_whenleft.getTrack() != p.getTrack()) {
					manual_whenleft = null;
					mainexec = mainexec_c;
				} else if (manual_lastprerunner != null && manual_lastprerunner.getTrack() == p.getTrack()) {
					manual_whenleft = manual_lastprerunner;
					manual_lastprerunner = null;
				}
			}
			if (mainexec.nextCommand()) {
				if (waitSignal == null) {
					int spd = Math.min(vMax, vTrainMax);
					if (vTempMax >= 0) {
						spd = Math.min(vTempMax, vTrainMax);
					}
					if (spd != vCurrent) {
						if (spd > vCurrent) {
							vCurrent += acceleration;
							if (vCurrent > spd) {
								vCurrent = spd;
							}
						} else if (spd < vCurrent) {
							vCurrent -= deceleration;
							if (vCurrent < 0) {
								vCurrent = 0;
							}
						}
					}// System.out.println("C:"+vCurrent+" spd:"+spd+" M:"+vMax+" T:"+vTrainMax);
					boolean allowmove = true; //vCurrent>0;
					int d = (int) vCurrent;
					if (!forward) {
						d = -d;
					}
					int v = 0, step, max = this.size() - 1;
					int noOnTrack = 0;
					Iterator<trainpart> it;
					if (forward) {
						it = this.iterator();
					} else {
						it = this.descendingIterator();
					}
					while (it.hasNext()) {
						trainpart p = it.next();
						if (p.isOnTrack()) {
							if (allowmove) {
								try {
									step = 0;
									if (v == 0) {
										step |= trackObject.TRAINSTEP_START;
									} else if (v == max) {
										step |= trackObject.TRAINSTEP_END;
									} else {
										step |= trackObject.TRAINSTEP_PART;
									}

									int realmove = p.move(this, p.trainRelativForward ? d : -d, step);
									allowmove = realmove >= 0;
									if (v == 0) {
										runner_pos += realmove;
									}
								} catch (TrainStoppedException ex) {
									d = ex.getDistance();
									if (!forward) {
										d = -d;
									}
									waitSignal = ex.getStopObject();
									if (prerunner_waitSignal == waitSignal && waitSignal instanceof pathableObject) { // both stop at signal
										vCurrent = 0;
										stopObjectCounter = 0;
										runner_pos = 0;
										prerunner_pos = 0;
										prerunner = null;
										orders.clear();
									} else if (v == 0) {
										runner_pos += Math.abs(d);
									}
									//System.out.println("R:"+runner_pos);
								}
							}
							if (p.getBounds() != null) {
								if (_bounds == null) {
									_bounds = p.getBounds().getBounds();
								} else {
									_bounds = _bounds.union(p.getBounds());
								}
							}
						} else {
							noOnTrack++;
						}
						++v;
					}
					if (noOnTrack > 0) {
						onTrack(null, false);
					}
					if (waitSignal != null) { // register at stopObject, wait for green or direction change or something else
						// not used....
					} else {
						trainorder minto = null;
						long minbpos = Long.MAX_VALUE; //System.out.println("----");
		    /*Iterator<trainorder> tit=orders.iterator();
						 while (tit.hasNext())
						 {
						 trainorder to=tit.next();
						 if (to.pos<runner_pos)
						 tit.remove();
						 }*/
						for (trainorder to : orders) {
							if (to.pos <= runner_pos) {
								orders.remove(to);
							} else {
								to.way = wayToSpeed(vCurrent, to.v);
								to.bpos = to.pos - to.way; //System.out.println("M:"+statics.steps2Speed(to.v)+" Pos:"+to.pos+" W:"+to.way+" =>"+to.bpos);
								if (to.bpos < minbpos) {
									minbpos = to.bpos;
									minto = to;
								}
							}
						}
						if (minto != null && runner_pos >= minbpos) {
							if (minto.doit()) {
								orders.remove(minto);
							}
						}
					}
				} else {
					if (!waitSignal.isStoppedT(this, trackObject.TRAINSTEP_START)) {
						waitSignal = null;
						setTemporaryvMaxTicks(NOTEMPVMAX);
					}
				}
			} else {
				setTemporaryvMaxTicks(NOTEMPVMAX);
			}
		}
		if (_bounds != null) {
			bounds = _bounds;
		}
	}

	public rectangle getBounds() {
		return bounds;
	}

	private void cleanOrder(trackObject to) {
		for (trainorder t : orders) {
			if (t.to == to) {
				orders.remove(t);
			}
		}
	}

	private boolean cleanOrder(trackObject to, int v) {
		for (trainorder t : orders) {
			if (t.to == to) {
				if (t.v == v) {
					return false;
				} else {
					orders.remove(t);
					return true;
				}
			}
		}
		return true;
	}

	private void addOrder(trainorder to) {
		to.ft = this;
		orders.add(to);
	}

	private int wayToSpeedDecel(double vCur, int speed) {
		//System.out.println("v1:"+speed+" v0:"+vCur+" a:"+deceleration+" dW:"+((speed*speed-vCur*vCur)/(-2.0*deceleration)/statics.TICKSPERMOVE));
		return (int) Math.round((speed * speed - vCur * vCur) / (-2.0 * deceleration) / statics.TICKSPERMOVE);
	}

	private int wayToSpeedAccel(double vCur, int speed) {
		return (int) Math.round((speed * speed - vCur * vCur) / (2.0 * acceleration) / statics.TICKSPERMOVE);
	}

	private int wayToSpeed(double vCur, int speed) {
		if (speed > vCur) {
			return wayToSpeedAccel(vCur, speed);
		} else {
			return wayToSpeedDecel(vCur, speed);
		}
	}
	/*
	 * Formeln:
	 *  bremsen: s=v²/2a (s=Strecke)
	 *  beschleunigen: v = v0+a*t
	 *  Kraft = Masse*Beschleunigung bzw. Beschleunigung = Kraft / Masse.
	 *  P=m*v²/t - P: Leistung (kW)
	 *  P: Leistung (kW)
	 *  F: Kraft
	 *  m: Masse
	 *  a: Beschleunigung
	 *  P=F*a ; F=m*a ; a=v/t;  => F=P/a; P/a=m*a => P/a=m*a => P/m=a² => a=sqrt(P/m)
	 */

	public void setAcceleration(double a) {
		// 1000*TICKSPERSECOND/PIXELPERMETER*m/s^2, calculated by train weight and power
		//System.out.println("accel: "+(a*statics.TICKSPERMOVE*statics.PIXELPERMETER/statics.TICKSPERSECOND));
		acceleration = (int) Math.round(a * statics.TICKSPERMOVE * statics.PIXELPERMETER / statics.TICKSPERSECOND / 10);
	}

	public void setDeceleration(double d) {
		deceleration = (int) Math.round(d * statics.TICKSPERMOVE * statics.PIXELPERMETER / statics.TICKSPERSECOND / 10);
	}

	public int getCurrentSpeed() {
		return statics.steps2Speed(vCurrent);
	}

	public int getvMax() {
		return statics.steps2Speed(vMax);
	}

	public int getvMaxTicks() {
		return vMax;
	}

	public int getTemporaryvMaxTicks() {
		return vTempMax;
	}

	public void setCurrentSpeed(int kmh) {
		vMax = statics.speed2Steps(kmh);
		vCurrent = vMax;
	}

	public void setvMaxTicks(int kmh) {
		vMax = kmh;
	}

	public void setvMax(int kmh) {
		vMax = statics.speed2Steps(kmh);
	}

	public void setvMax(int kmh, trackObject to, int step) {
		if ((step & trackObject.TRAINSTEP_PRERUNNER) != 0) {
			int v1 = statics.speed2Steps(kmh);
			if (cleanOrder(to, v1)) {
				trainorder sts;
				sts = new setspeed(v1, prerunner_pos);
				sts.to = to;
				addOrder(sts);
			}
		} else if ((step & trackObject.TRAINSTEP_END) != 0) {
			vMax = statics.speed2Steps(kmh);
		}
	}

	public void setTemporaryvMaxTicks(int kmh) {
		vTempMax = kmh;
	}

	public void setTemporaryvMax(int kmh, trackObject to, int step) {
		if ((step & trackObject.TRAINSTEP_PRERUNNER) != 0) {
			if (kmh >= 0) {
				int v1 = statics.speed2Steps(kmh);
				if (cleanOrder(to, v1)) {
					trainorder sts;
					/*if (to instanceof destinationObject)
					 System.out.println("pR:"+prerunner_pos);*/
					sts = new settempspeed(v1, prerunner_pos);
					sts.to = to;
					addOrder(sts);
				}
			} else {
				cleanOrder(to);
				vTempMax = -1;
			}
		} else if ((step & trackObject.TRAINSTEP_END) != 0) {
			vTempMax = statics.speed2Steps(kmh);
		}
	}

	public trainCommandExecutor getMainExecutor() {
		return mainexec;
	}

	public trainCommandExecutor getPreExecutor() {
		return preexec;
	}

	public trainCommandExecutor getExecutor(int step) {
		if ((step & trackObject.TRAINSTEP_PRERUNNER) != 0) {
			return preexec;
		}
		if ((step & trackObject.TRAINSTEP_START) != 0) {
			return mainexec;
		}
		return null;
	}

	public void setManuelMode(boolean e) {
		if (e != manualmode) {
			manualmode = e;
			if (manualmode) {
				mainexec = new trainCommandExecutor_manual(this, false);
				preexec = new trainCommandExecutor_manual(this, true, mainexec);
				manual_lastprerunner = null;
				manual_whenleft = null;
			} else {
				mainexec_c.setRoute(null);
				preexec_c.setRoute(null);
				manual_whenleft = null;
				manual_lastprerunner = prerunner;
				preexec = preexec_c;
				if (manual_lastprerunner == null) {
					mainexec = mainexec_c;
				}
			}
		}
	}

	public boolean isManualMode() {
		return manualmode;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public void setRoute(route r) {
		preexec_c.setRoute(r);
		mainexec_c.setRoute(r);
	}

	public reducedstarttrain getStartTrain() {
		return startTrain;
	}

	/**
	 * Getter for property creationGametime.
	 *
	 * @return Value of property creationGametime.
	 */
	public long getCreationGametime() {
		return this.creationGametime;
	}

	/**
	 * Setter for property creationGametime.
	 *
	 * @param creationGametime New value of property creationGametime.
	 */
	public void setCreationGametime(long creationGametime) {
		this.creationGametime = creationGametime;
	}

	public int getWeight() {
		return weight;
	}

	public String getSaveString() {
		StringBuffer w = new StringBuffer();
		w.append("<train ");
		w.append("name='" + name + "' ");
		if (startTrain != null) {
			w.append("parent='" + startTrain.getName() + "' ");
		}
		w.append("trainmaxspeed='" + this.vTrainMax + "' ");
		w.append("trackmaxspeed='" + this.vMax + "' ");
		w.append("tempmaxspeed='" + this.vTempMax + "' ");
		w.append("currentspeed='" + this.vCurrent + "' ");
		w.append("acceleration='" + this.acceleration + "' ");
		w.append("deceleration='" + this.deceleration + "' ");
		w.append("weight='" + this.weight + "' ");
		w.append("forward='" + (this.forward ? "y" : "n") + "' ");
		w.append("time='" + this.creationGametime + "' ");
		w.append("stopObjectCounter='" + stopObjectCounter + "' ");
		if (manualmode) {
			w.append("manualmode='y' ");
		}
		w.append(">\n");

		for (trainpart tp : this) {
			w.append(tp.getSaveString());
		}
		if (prerunner != null) {
			w.append(this.prerunner.getSaveString("preA1"));
			if (prerunnerA2 != null) {
				w.append(this.prerunnerA2.getSaveString("preA2"));
			}
		}
		if (manualmode) {
			if (manual_lastprerunner != null) {
				w.append(manual_lastprerunner.getSaveString("lastpre"));
			}
			if (manual_whenleft != null) {
				w.append(manual_whenleft.getSaveString("whenleft"));
			}
		}
		w.append("<tdata type='runner' key='main' value='" + this.runner_pos + "'/>\n");
		w.append("<tdata type='runner' key='pr' value='" + this.prerunner_pos + "'/>\n");

		if (this.waitSignal != null) {
			w.append("<tdata type='po' key='main' value='" + waitSignal.getTemporaryHash() + "'/>\n");
		}
		if (this.prerunner_waitSignal != null) {
			w.append("<tdata type='po' key='pr' value='" + prerunner_waitSignal.getTemporaryHash() + "'/>\n");
		}
		for (trainorder to : orders) {
			w.append("<tdata type='order' key='start' value='" + to.getTypeName() + "'/>\n");
			w.append("<tdata type='order' key='pos' value='" + to.pos + "'/>\n");
			w.append("<tdata type='order' key='to' value='" + to.to.getTemporaryHash() + "'/>\n");
			w.append("<tdata type='order' key='v' value='" + to.v + "'/>\n");
			w.append("<tdata type='order' key='way' value='" + to.way + "'/>\n");
			w.append("<tdata type='order' key='bpos' value='" + to.bpos + "'/>\n");
			w.append("<tdata type='order' key='control' value='commit'/>\n");
		}
		for (String sto : store.keySet()) {
			w.append("<tdata type='gstore' key='" + sto + "' value='" + store.get(sto) + "'/>\n");
		}
		w.append(mainexec_c.getSaveString("mainexec"));
		w.append(preexec_c.getSaveString("preexec"));

		w.append("</train>\n");
		return w.toString();
	}

	public fulltrain(Attributes meta) {
		super();
		name = meta.getValue("name");
		if (meta.getValue("parent") != null) {
			startTrain = new reducedstarttrain(meta.getValue("parent"));
		}
		vTrainMax = Integer.parseInt(meta.getValue("trainmaxspeed"));
		vMax = Integer.parseInt(meta.getValue("trackmaxspeed"));
		vTempMax = Integer.parseInt(meta.getValue("tempmaxspeed"));
		vCurrent = Double.parseDouble(meta.getValue("currentspeed"));
		acceleration = Integer.parseInt(meta.getValue("acceleration"));
		deceleration = Integer.parseInt(meta.getValue("deceleration"));
		weight = Integer.parseInt(meta.getValue("weight"));
		stopObjectCounter = Integer.parseInt(meta.getValue("stopObjectCounter"));
		creationGametime = Long.parseLong(meta.getValue("time"));
		forward = meta.getValue("forward").compareTo("y") == 0;
		if (meta.getValue("manualmode") != null) {
			manualmode = meta.getValue("manualmode").compareTo("y") == 0;
		}
		ontrack = true;
	}
	private trainpart lastFromLoad = null;

	public void handle_start_tpart(Attributes meta) {
		lastFromLoad = null;
		try {
			trainpart tp = new trainpart(meta);
			add(tp);
			lastFromLoad = tp;
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}

	public void handle_end_tpart() {
		if (lastFromLoad != null) {
			lastFromLoad.handle_end();
		}
		lastFromLoad = null;
	}

	public void handle_point(Attributes meta) {
		if (lastFromLoad != null) {
			try {
				lastFromLoad.handle_point(meta);
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		} else {
			String type = trackpoint.getType(meta);
			if (type.compareTo("preA1") == 0) {
				prerunner = new trackpoint(meta);
			} else if (type.compareTo("preA2") == 0) {
				prerunnerA2 = new trackpoint(meta);
			} else if (type.compareTo("lastpre") == 0) {
				manual_lastprerunner = new trackpoint(meta);
			} else if (type.compareTo("whenleft") == 0) {
				manual_whenleft = new trackpoint(meta);
			}
		}
	}

	private void tdata_runner(String key, String value) {
		if (key.compareTo("main") == 0) {
			runner_pos = Integer.parseInt(value);
		} else if (key.compareTo("pr") == 0) {
			prerunner_pos = Integer.parseInt(value);
		}
	}

	private trackObject findTOhash(int h) {
		for (trackObject to : trackObject.allto.keySet()) {
			if (to.getTemporaryHash() == h) {
				return to;
			}
		}
		return null;
	}

	private void tdata_po(String key, String value) {
		if (key.compareTo("main") == 0) {
			waitSignal = (stopObject) findTOhash(Integer.parseInt(value));
		} else if (key.compareTo("pr") == 0) {
			prerunner_waitSignal = (stopObject) findTOhash(Integer.parseInt(value));
		}
	}
	private trainorder lastTOFromLoad = null;

	private void tdata_order(String key, String value) {
		if (key.compareTo("start") == 0) {
			lastTOFromLoad = null;
			lastTOFromLoad = (trainorder) statics.loadClass(statics.TRAINORDERSPATH, value);
			lastTOFromLoad.ft = this;
		} else if (lastTOFromLoad != null) {
			if (key.compareTo("pos") == 0) {
				lastTOFromLoad.pos = Integer.parseInt(value);
			} else if (key.compareTo("v") == 0) {
				lastTOFromLoad.v = Integer.parseInt(value);
			} else if (key.compareTo("way") == 0) {
				lastTOFromLoad.way = Integer.parseInt(value);
			} else if (key.compareTo("bpos") == 0) {
				lastTOFromLoad.bpos = Integer.parseInt(value);
			} else if (key.compareTo("to") == 0) {
				lastTOFromLoad.to = findTOhash(Integer.parseInt(value));
			} else if (key.compareTo("control") == 0) {
				orders.add(lastTOFromLoad);
			}
		}
	}

	private void tdata_gstore(String key, String value) {
		store.put(key, value);
	}

	public void handle_tdata(Attributes meta) {
		String type = meta.getValue("type");
		String key = meta.getValue("key");
		String value = meta.getValue("value");

		if (type.startsWith("po")) {
			tdata_po(key, value);
		} else if (type.startsWith("runner")) {
			tdata_runner(key, value);
		} else if (type.startsWith("gstore")) {
			tdata_gstore(key, value);
		} else if (type.startsWith("order")) {
			tdata_order(key, value);
		} else if (type.startsWith("mainexec")) {
			mainexec_c.tdata(type, key, value);
		} else if (type.startsWith("preexec")) {
			preexec_c.tdata(type, key, value);
		}
	}

	public void handle_end_load() {
		if (forward) {
			prerunner_firstpart = getFirst();
		} else {
			prerunner_firstpart = getLast();
		}
		Iterator<trainpart> it;
		if (forward) {
			it = this.iterator();
		} else {
			it = this.descendingIterator();
		}
		while (it.hasNext()) {
			trainpart p = it.next();
			if (p.isOnTrack()) {
				if (p.getBounds() != null) {
					if (bounds == null) {
						bounds = p.getBounds().getBounds();
					} else {
						bounds = bounds.union(p.getBounds());
					}
				}
			}
		}
	}

	/**
	 * remove first stock by driving direction
	 *
	 * @return true: all stocks removed
	 */
	public boolean removeFirstStock() {
		if (forward) {
			this.removeFirst();
		} else {
			this.removeLast();
		}
		return this.isEmpty();
	}

	public void deleteYourself() {
		clear();
		dataCollector.collector.thepainter.delTrain(this);
	}
}
