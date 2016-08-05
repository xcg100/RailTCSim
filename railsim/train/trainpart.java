/*
 * $Revision: 18 $
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

import java.awt.*;

import org.railsim.dataCollector;
import org.railsim.service.*;
import org.railsim.service.exceptions.TrainStoppedException;
import org.railsim.service.trackObjects.trackObject;
import org.xml.sax.Attributes;

/**
 *
 * @author js
 */
public class trainpart {

	private trackpoint a1 = null;
	private trackpoint a2 = null;
	private int distance = 0;
	private int distanceFront = 0;
	private int distanceBack = 0;
	private rollingstock rs = null;
	private rectangle bounds = null;
	private int moved = 0;
	private boolean forward = true;
	public boolean trainRelativForward = true;

	/**
	 * Creates a new instance of trainpart
	 */
	public trainpart(rollingstock r, boolean _forward) {
		rs = r;
		forward = _forward;
		distance = r.getAxisdistance();
		if (forward) {
			distanceFront = r.getFrontoverlap();
			distanceBack = r.getBackoverlap();
		} else {
			distanceBack = r.getFrontoverlap();
			distanceFront = r.getBackoverlap();
		}
	}

	public trainpart(int _distance, int _distanceFront, int _distanceBack) {
		distance = _distance;
		distanceFront = _distanceFront;
		distanceBack = _distanceBack;
	}

	public void onTrack(trackpoint _a1, trackpoint _a2) {
		a1 = _a1;
		a2 = _a2;
		a1.getTrack().addAxis();
		a2.getTrack().addAxis();
	}

	public void offTrack() {
		if (a1 != null && a2 != null) {
			a1.getTrack().delAxis();
			a2.getTrack().delAxis();
		}
		a1 = null;
		a2 = null;
	}

	public int move(fulltrain t, int dist, int step) throws TrainStoppedException {
		bounds = null;
		if (a1 != null && a2 != null) {
			int dret = 0;
			if (dist > 0) {
				int omoved = moved;
				moved += dist;
				int d = 0;
				if (moved > statics.TICKSPERMOVE) {
					d = moved / statics.TICKSPERMOVE;
					moved = moved % statics.TICKSPERMOVE;
				}
				/*
				 * Problem:
				 *  bei TrainStoppedException ist moved unverändert -> zu hoch
				 *  und Wert "i" in der Exception ist falsch, da keine Ticks sondern Pixel
				 */
				dret = d;
				for (int i = 0; i < d; ++i) {
					trackpoint a11, a21;
					try {
						a11 = a1.getTrack().nextPoint1(t, a1, a2, step & ~trackObject.TRAINSTEP_END);
						a21 = a2.getTrack().nextPoint2(t, a1, a2, step & trackObject.TRAINSTEP_END);
					} catch (TrainStoppedException ex) {
						bounds = new rectangle(a1, a2);
						moved = (omoved + i * statics.TICKSPERMOVE) % statics.TICKSPERMOVE;
						throw new TrainStoppedException(ex, i * statics.TICKSPERMOVE);
					}
					if (a11 == null || a21 == null) {
						return -1;
					}
					a1 = a11;
					a2 = a21;
				}
			} else if (dist < 0) {
				dist = -dist;
				int omoved = moved;
				moved += dist;
				int d = 0;
				if (moved > statics.TICKSPERMOVE) {
					d = moved / statics.TICKSPERMOVE;
					moved = moved % statics.TICKSPERMOVE;
				}
				dret = d;
				for (int i = 0; i < d; ++i) {
					trackpoint a21, a11;
					try {
						a21 = a2.getTrack().nextPoint1(t, a2, a1, step & ~trackObject.TRAINSTEP_END);
						a11 = a1.getTrack().nextPoint2(t, a2, a1, step & trackObject.TRAINSTEP_END);
					} catch (TrainStoppedException ex) {
						bounds = new rectangle(a1, a2);
						moved = (omoved + i * statics.TICKSPERMOVE) % statics.TICKSPERMOVE;
						throw new TrainStoppedException(ex, i * statics.TICKSPERMOVE);
					}
					if (a11 == null || a21 == null) {
						return -1;
					}
					a1 = a11;
					a2 = a21;
				}
			}
			bounds = new rectangle(a1, a2);
			return dret;
		}
		return -1;
	}

	public trackpoint getA1() {
		return a1;
	}

	public trackpoint getA2() {
		return a2;
	}

	public boolean isOnTrack() {
		return (a1 != null && a2 != null);
	}

	public int getLength() {
		return distance;
	}

	public int getDistance() {
		return distance;
	}

	public int getDistanceFront() {
		return distanceFront;
	}

	public int getDistanceBack() {
		return distanceBack;
	}

	public int getWeight() {
		return rs.getWeight_empty();
	}

	public void paint(Graphics2D g2, int onlylevel) {
		if (a1 != null && a2 != null && (a1.getTrack().getLevel() == onlylevel || a2.getTrack().getLevel() == onlylevel)) {
			/*
			 * x:
			 * 0+distanceFont=a1
			 * length+distanceBack=a2
			 * distance+a1=a2
			 * y:
			 *
			 */

			if (rs == null) {
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON); // Test
				g2.setColor(Color.RED);
				g2.setStroke(new BasicStroke(track.RAILRADIUS * 4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
				g2.drawLine(a1.getX(), a1.getY(), a2.getX(), a2.getY());
				g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
			} else {
				double rot;
				if (forward) {
					rot = a1.arc(a2);
				} else {
					rot = a2.arc(a1);
				}
				point c = a1.halfWay(a2);
				Image i = rs.getAppearance(rot);
				if (i != null) {
					Graphics2D g3 = (Graphics2D) g2.create();
					g3.translate(c.getX(), c.getY());
					g3.rotate(rot);
					//g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					//    RenderingHints.VALUE_ANTIALIAS_ON); // poorly slow!!

					//g3.translate(-(this.distance/2+this.distanceFront),-i.getHeight(null)/2);
					g3.drawImage(i, -(this.distance / 2 + this.distanceFront), -i.getHeight(null) / 2, null);
				} else {
					dataCollector.collector.setStatus("Wagen " + rs.getName() + ": keine Darstellung verfügbar");
				}
			}
		}
	}

	public rectangle getBounds() {
		return bounds;
	}

	public trainpart(Attributes meta) {
		idtype id = new idtype(meta.getValue("id"));
		rs = dataCollector.collector.getAllTrainData().stocks.get(id);
		forward = meta.getValue("forward").compareTo("y") == 0;
		distance = Integer.parseInt(meta.getValue("distance"));
		distanceFront = Integer.parseInt(meta.getValue("distanceFront"));
		distanceBack = Integer.parseInt(meta.getValue("distanceBack"));
		moved = Integer.parseInt(meta.getValue("moved"));
		trainRelativForward = meta.getValue("trainRelativForward").compareTo("y") == 0;
	}

	void handle_point(Attributes meta) {
		String type = trackpoint.getType(meta);
		if (type.compareTo("A1") == 0) {
			a1 = new trackpoint(meta);
		} else if (type.compareTo("A2") == 0) {
			a2 = new trackpoint(meta);
		}
	}

	void handle_end() {
		if (a1 != null && a2 != null) {
			bounds = new rectangle(a1, a2);
		}
	}

	public String getSaveString() {
		StringBuffer w = new StringBuffer();
		w.append("<tpart ");
		w.append("id='" + rs.getId().getID() + "' ");
		w.append("forward='" + (forward ? "y" : "n") + "' ");
		w.append("distance='" + distance + "' ");
		w.append("distanceFront='" + distanceFront + "' ");
		w.append("distanceBack='" + distanceBack + "' ");
		w.append("moved='" + moved + "' ");
		w.append("trainRelativForward='" + (trainRelativForward ? "y" : "n") + "' ");
		w.append(">\n");

		if (a1 != null) {
			w.append(a1.getSaveString("A1"));
		}
		if (a2 != null) {
			w.append(a2.getSaveString("A2"));
		}

		/*
		 id                  CDATA   #REQUIRED
		 forward             (y|n)   #REQUIRED
		 distance            CDATA   #REQUIRED
		 distanceFront       CDATA   #REQUIRED
		 distanceBack        CDATA   #REQUIRED
		 moved               CDATA   #REQUIRED
		 trainRelativForward (y|n)   #REQUIRED
		 */
		w.append("</tpart>\n");
		return w.toString();
	}
}
