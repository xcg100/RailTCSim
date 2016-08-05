/*
 * $Revision: 20 $
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
package org.railsim.service;

import java.awt.*;
import java.util.*;

import org.railsim.dataCollector;
import org.railsim.service.exceptions.*;
import org.railsim.service.trackObjects.*;
import org.railsim.service.tracks.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class track {

	static final boolean DEBUGCROSSING = false;
	static final boolean DEBUGAXIS = false;
	/**
	 * Start length for new tracks
	 */
	public static final int STARTLENGTH = 80;
	/**
	 * max possible length of tracks - longer is possible but this is a good value
	 */
	public static final int MAXLENGTH = 240;
	/**
	 * radius between track middle and one rail - distance rail to rail is 2x RAILRADIUS
	 */
	public static final int RAILRADIUS = 2;
	//public static final int RAILRADIUS=3;
	/**
	 * MIN Height Level
	 */
	public static final int MINLEVEL = -5;
	/**
	 * MAX Height Level
	 */
	public static final int MAXLEVEL = 5;
	/**
	 * paintTrack part
	 */
	public static final int PART_ADDITIONAL = 0x01;
	/**
	 * paintTrack part
	 */
	public static final int PART_SLEEPER = 0x02;
	/**
	 * paintTrack part
	 */
	public static final int PART_RAIL = 0x04;
	/**
	 * paintTrack part
	 */
	public static final int PART_ABOVE = 0x08;
	/**
	 * paintTrack part - paint all
	 */
	public static final int PART_ALL = 0xff;
	static public boolean PAINTALLTRACKOBJECTS = false;
	protected int x1;
	protected int y1;
	protected double winkel_a;	// bow - Richtungswinkel
	protected double winkel_s;	// rot - Startwinkel
	protected double length;
	protected int level = 0;
	protected int progress = 0;
	protected track prev = null;
	protected track next = null;
	protected track[] jnext = new track[2];
	private int[] last_junction_meaning = null;
	protected int next_x2;
	protected int next_y2;
	protected double next_winkel_s;
	private boolean helplines = false;
	private trackGroup trg = null;
	private rectangle bounds;
	private rectangle clickbounds;
	private rectangle startarea;
	private rectangle endarea;
	java.util.List<rotatepoint> points = new ArrayList<>();
	java.util.HashMap<rotatepoint, Integer> pointsHash = new HashMap<>();
	java.util.List<point> railNorth = new ArrayList<>();
	java.util.List<point> railSouth = new ArrayList<>();
	java.util.List<trackObject> trackObject_east = new ArrayList<>();
	java.util.List<trackObject> trackObject_west = new ArrayList<>();
	java.util.HashSet<track> crossing = new java.util.HashSet<>();
	protected trackPainter tp = new simpleTrack();

	/**
	 * Creates a new instance of track w/o any connection
	 *
	 * @param x pos x
	 * @param y pos y
	 * @param w_s Start-Winkel (pos x/y)
	 * @param w_a Richtungswinkel
	 * @param l Länge (direkte Linie)
	 */
	public track(int x, int y, double w_s, double w_a, double l) {
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		x1 = x;
		y1 = y;
		winkel_s = w_s;
		winkel_a = w_a;
		length = l;
		calcTrack();
	}

	/**
	 * Creates a new instance of track w/o any connection
	 *
	 * @param xy pos x/x point
	 * @param w_s Start-Winkel (pos x/y)
	 * @param w_a Richtungswinkel
	 * @param l Länge (direkte Linie)
	 */
	public track(point xy, double w_s, double w_a, double l) {
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		x1 = xy.getX();
		y1 = xy.getY();
		winkel_s = w_s;
		winkel_a = w_a;
		length = l;
		calcTrack();
	}

	/**
	 * Creates a new instance of track at NEXT point, connected
	 *
	 * @param p prev track
	 * @param w_a Richtungswinkel
	 * @param l Länge (direkte Linie)
	 */
	public track(track p, double w_a, double l) {
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		prev = p;
		p.setNext(this, -1);
		x1 = p.next_x2;
		y1 = p.next_y2;
		winkel_s = p.next_winkel_s;
		winkel_a = w_a;
		length = l;
		level = p.level;
		calcTrack();
	}

	/**
	 * Creates a new instance of track at X/Y point w/o connection!! Copy data of track.
	 *
	 * @param x X
	 * @param y Y
	 * @param p copy data from track
	 */
	public track(track p, int x, int y) {
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		x1 = x;
		y1 = y;
		winkel_s = p.winkel_s;
		winkel_a = p.winkel_a;
		length = p.length;
		level = p.level;
		calcTrack();
	}

	/**
	 * Creates a new instance of track at X/Y point w/o connection!! Copy data of track.
	 *
	 * @param xy point
	 * @param p copy data from track
	 * @see track(track,int,int)
	 */
	public track(track p, point xy) {
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		x1 = xy.getX();
		y1 = xy.getY();
		winkel_s = p.winkel_s;
		winkel_a = p.winkel_a;
		length = p.length;
		level = p.level;
		calcTrack();
	}

	/**
	 * Creates a new instance of track with w_s rotation w/o connection!! Copy data of track.
	 *
	 * @param p copy data from track
	 * @param w_s Startwinkel
	 */
	public track(track p, double w_s) {
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		x1 = p.x1;
		y1 = p.y1;
		winkel_s = w_s;
		winkel_a = p.winkel_a;
		length = p.length;
		level = p.level;
		calcTrack();
	}

	/**
	 * Creates a new instance of track w/o connection!! copy data from track
	 *
	 * @param p track
	 */
	public track(track p) {
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		x1 = p.x1;
		y1 = p.y1;
		winkel_s = p.winkel_s;
		winkel_a = p.winkel_a;
		length = p.length;
		level = p.level;
		calcTrack();
	}

	/**
	 * Creates a new instance of track at NEXT point to other track
	 * only used for tests, removed later!
	 *
	 * @param start start winkel (rotation)
	 * @param dest track
	 * @param w_a Richtungswinkel
	 * @param l Länge (direkte Linie)
	 * @deprecated only used for tests, removed later!
	 * @throws railsim.simplytrain.service.DestinationNotReachableException dest track not reachable
	 */
	public track(track start, double w_a, double l, track dest) throws DestinationNotReachableException { // only used for tests, removed later!
		for (int i = 0; i < jnext.length; ++i) {
			jnext[i] = null;
		}
		prev = start;
		x1 = start.next_x2;
		y1 = start.next_y2;
		winkel_s = start.next_winkel_s;
		winkel_a = w_a;
		length = l;
		calcTrack();
		if (dest.knowMeNext(this) != 0) {
			start.setNext(this, -1);
		} else {
			throw new DestinationNotReachableException(this, dest);
		}
	}

	/**
	 * Tracks equal? (X/Y/Rotation/Bowing/Length)
	 *
	 * @param o track to compare with
	 * @return equal==true
	 */
	@Override
	public boolean equals(Object o) {
		try {
			track t = (track) o;
			return (t.x1 == x1 && t.y1 == y1 && t.winkel_a == winkel_a && t.winkel_s == winkel_s && t.length == length);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * is given track connected somewhere (start or end)
	 *
	 * @param t given track
	 * @return connected==true
	 */
	public boolean isConnectedTo(track t) {
		boolean r = false;
		for (int i = 0; i < jnext.length; ++i) {
			r |= (jnext[i] != null && jnext[i].equals(t));
		}
		return (prev != null && prev.equals(t)) || r;
	}

	/**
	 * Start (prev) connected?
	 *
	 * @return true if start not connected
	 */
	public boolean isStartFree() {
		return prev == null;
	}

	/**
	 * End (next) connected?
	 *
	 * @return true if end (no end) not connected
	 */
	public boolean isEndFree() {
		return next == null;
	}

	/**
	 * move this track to end of given track (this.prev=given track, given track.next[]=this), calles setNext(track,-1)
	 *
	 * @param p given track
	 * @see setNext(track,int)
	 */
	public void moveNext(track p) {
		prev = p;
		p.setNext(this, -1);
		x1 = p.next_x2;
		y1 = p.next_y2;
		winkel_s = p.next_winkel_s;
		calcTrack();
	}

	/**
	 * move this track to start of given track (this.prev=given track, given track.prev=this))
	 *
	 * @param p given track
	 */
	public void movePrev(track p) {
		prev = p;
		p.setPrev(this);
		x1 = p.x1;
		y1 = p.y1;
		winkel_s = p.winkel_s - 180;
		calcTrack();
	}

	/**
	 * connects this track's end to end of given track (this.next[0]=given track, given track.next[]=this), calles setNext(track,-1)
	 * track is not moved!
	 *
	 * @param p given track
	 */
	public void connectNext(track p) {
		next = p;
		jnext[0] = p;
		p.setNext(this, -1);
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * connects this track's end to start of given track (this.next[0]=given track, given track.prev)
	 * track is not moved!
	 *
	 * @param p given track
	 */
	public void connectPrev(track p) {
		next = p;
		jnext[0] = p;
		p.setPrev(this);
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	public void connectN2N(track p) {
		connectNext(p);
	}

	public void connectN2P(track p) {
		connectPrev(p);
	}

	public void connectP2N(track p) {
		p.connectPrev(this);
	}

	public void connectP2P(track p) {
		prev = p;
		p.setPrev(this);
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * move track to point
	 *
	 * @param _x X
	 * @param _y Y
	 */
	public void move(int _x, int _y) {
		if (prev != null) {
			prev.forgetMe(this);
		}
		prev = null;
		for (int i = 0; i < jnext.length; ++i) {
			if (jnext[i] != null) {
				jnext[i].forgetMe(this);
			}
			jnext[i] = null;
		}
		next = null;
		x1 = _x;
		y1 = _y;
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * move track to point
	 *
	 * @param xy point
	 * @see move(int,int)
	 */
	public void move(point xy) {
		move(xy.getX(), xy.getY());
	}

	/**
	 * set new rotation
	 *
	 * @param w rotation in degree
	 */
	public void rotate(double w) {
		if (prev != null) {
			prev.forgetMe(this);
		}
		prev = null;
		for (int i = 0; i < jnext.length; ++i) {
			if (jnext[i] != null) {
				jnext[i].forgetMe(this);
			}
			jnext[i] = null;
		}
		next = null;
		winkel_s = w;
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * Set bowing of track
	 *
	 * @param w new angle in degree
	 * @deprecated not used
	 */
	public void bow(double w) {
		for (int i = 0; i < jnext.length; ++i) {
			if (jnext[i] != null) {
				jnext[i].forgetMe(this);
			}
			jnext[i] = null;
		}
		next = null;
		winkel_a = w;
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * Resize track
	 *
	 * @param l new length
	 * @deprecated not used
	 */
	public void resize(double l) {
		for (int i = 0; i < jnext.length; ++i) {
			if (jnext[i] != null) {
				jnext[i].forgetMe(this);
			}
			jnext[i] = null;
		}
		next = null;
		length = l;
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * Bow and resize track to reach X/Y
	 *
	 * @param x X
	 * @param y Y
	 */
	public void bowresize(int x, int y) {
		int kx = x - x1;
		int ky = y1 - y;
		double l = Math.sqrt(ky * ky + kx * kx);
		if (l > MAXLENGTH) {
			l = MAXLENGTH;
		}

		double w_ar = Math.atan2(ky, kx);
		double w_a = w_ar * 180 / java.lang.Math.PI - winkel_s;
		while (w_a < -180) {
			w_a += 360;
		}
		if (w_a > 90) {
			w_a = 90;
		}
		if (w_a < -90) {
			w_a = -90;
		}

		for (int i = 0; i < jnext.length; ++i) {
			if (jnext[i] != null) {
				jnext[i].forgetMe(this);
			}
			jnext[i] = null;
		}
		next = null;
		length = l;
		winkel_a = w_a;
		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * set next (end) track, normally not used, only is junction switched!
	 *
	 * @param t given track
	 */
	public void setNext(track t) {
		if (next != null && next != t) {
			next.forgetMe(this);
		}
		next = t;
		neighborUpdate();
	}

	/**
	 * set next (end) track
	 *
	 * @param t given track
	 * @param p at which arm of junction, -1: use first free arm, if non free don't add, return false
	 * @return false if
	 * @param p was -1 and no arm was free
	 */
	public boolean setNext(track t, int p) {
		if (p < 0) {
			for (int i = 0; i < jnext.length; ++i) {
				if (jnext[i] == null) {
					jnext[i] = t;
					p = i;
					break;
				}
			}
		} else if (jnext.length > p) {
			if (jnext[p] != null && jnext[p] != t) {
				jnext[p].forgetMe(this);
			}
			jnext[p] = t;
		}
		if (p == 0) {
			next = t;
		}
		neighborUpdate();
		updateNeighbors();
		return p >= 0;
	}

	/**
	 * set prev (start) track
	 *
	 * @param t given track
	 */
	public void setPrev(track t) {
		if (prev != null && prev != t) {
			prev.forgetMe(this);
		}
		prev = t;
	}

	/**
	 * Set junction direction
	 *
	 * @param p Junction direction
	 * @see getJunctionMeaning()
	 * @see getJunction()
	 */
	public void setJunction(int p) {
		if (jnext.length > p && isFree()) {
			if (jnext[p] != null) {
				next = jnext[p];
				int[] m = getJunctionMeaning();
				int s = getJunction();
				tp.paintJunction(this, m[s]);
			}
		}
	}

	/**
	 * forget given track if this was known
	 *
	 * @param t given track
	 */
	protected void forgetMe(track t) {
		int j, i;
		for (i = 0; i < jnext.length; ++i) {
			if (jnext[i] == t) {
				jnext[i] = null;
			}
		}
		if (next == t) {
			next = null;
		}
		track[] jn = new track[jnext.length];
		for (i = 0; i < jn.length; ++i) {
			jn[i] = null;
		}
		j = 0;
		for (i = 0; i < jnext.length; ++i) {
			if (jnext[i] != null) {
				jn[j] = jnext[i];
				++j;
			}
		}
		jnext = jn;
		if (next == null) {
			next = jnext[0];
		}
		if (prev == t) {
			prev = null;
		}
		neighborUpdate();
		updateNeighbors();
	}

	/**
	 * calls updateNeighbors on known prev and next[]
	 *
	 * @see neighborUpdate()
	 */
	protected void updateNeighbors() {
		if (prev != null) {
			prev.neighborUpdate();
		}
		for (int i = 0; i < jnext.length; ++i) {
			if (jnext[i] != null) {
				jnext[i].neighborUpdate();
			}
		}
		clearAllCrossings();
	}

	/**
	 * cleans cached data, eg. called by updateNeighbor
	 *
	 * @see updateNeighbors()
	 */
	protected void neighborUpdate() {
		last_junction_meaning = null;
	}

	/**
	 * only used for tests, removed later!
	 *
	 * @param t track
	 * @return true: 0: too far, 1: ok at end, -1: ok at start
	 * @deprecated will be removed later
	 */
	protected int knowMeNext(track t) { // only used for tests, removed later!
		neighborUpdate();
		if (next == null) {
			if (Math.abs(t.next_x2 - next_x2) < 4
					&& Math.abs(t.next_y2 - next_y2) < 4) {
				next = t;
				t.setNext(this);
				return 1;
			}
		} else if (prev == null) {
			if (Math.abs(t.next_x2 - x1) <= 6
					&& Math.abs(t.next_y2 - y1) <= 6) {
				prev = t;
				t.setNext(this);
				return -1;
			}
		}
		return 0;
	}
	/*protected int knowMePrev(track t)
	 {
	 if (next==null)
	 {
	 if (Math.abs(t.y1-next_x2)<=6
	 && Math.abs(t.y1-next_y2)<=6)
	 {
	 next=t;
	 t.setPrev(this);
	 return 1;
	 }
	 }
	 else if (prev==null)
	 {
	 if (Math.abs(t.x1-x1)<4
	 && Math.abs(t.y1-y1)<4)
	 {
	 prev=t;
	 t.setPrev(this);
	 return -1;
	 }
	 }
	 return 0;
	 }*/

	/**
	 * can this track's end connect to given track's end without any movement!
	 *
	 * @param t given track
	 * @return can?
	 */
	public boolean canEndConnectEnd(track t) {
		return (next == null && Math.abs(t.next_x2 - next_x2) < 4 && Math.abs(t.next_y2 - next_y2) < 4);
	}

	public boolean canEndConnectEnd_strict(track t) {
		return (next == null && Math.abs(t.next_x2 - next_x2) < 2 && Math.abs(t.next_y2 - next_y2) < 2 && Math.round(next_winkel_s % 360) == Math.round((t.next_winkel_s + 180) % 360));
	}

	/**
	 * can this track's start connect to given track's end without any movement!
	 *
	 * @param t given track
	 * @return can?
	 */
	public boolean canStartConnectEnd(track t) {
		return (prev == null && Math.abs(t.next_x2 - x1) <= 6 && Math.abs(t.next_y2 - y1) <= 6);
	}

	public boolean canStartConnectEnd_strict(track t) {
		return (prev == null && Math.abs(t.next_x2 - x1) <= 2 && Math.abs(t.next_y2 - y1) <= 2 && Math.round(winkel_s) == Math.round(t.next_winkel_s));
	}

	/**
	 * can this track's end connect to given track's start without any movement!
	 *
	 * @param t given track
	 * @return can?
	 */
	public boolean canEndConnectStart(track t) { // with rotation of t
		return (next == null);
	}

	public boolean canEndConnectStart_strict(track t) { // with rotation of t
		return (next == null && Math.abs(t.x1 - next_x2) < 2 && Math.abs(t.y1 - next_y2) < 2 && Math.round(next_winkel_s) == Math.round(t.winkel_s));
	}

	/**
	 * can this track's start connect to given track's start without any movement!
	 *
	 * @param t given track
	 * @return can?
	 */
	public boolean canStartConnectStart(track t) { // with rotation of t
		return (prev == null);
	}

	public boolean canStartConnectStart_strict(track t) { // with rotation of t
		return (prev == null && Math.abs(t.x1 - x1) < 2 && Math.abs(t.y1 - y1) < 2 && Math.round(winkel_s % 360) == Math.round((t.winkel_s + 180) % 360));
	}

	/**
	 * note crossing between this and given track
	 *
	 * @param t other track
	 */
	public void addCrossing(track t) {
		synchronized (crossing) {
			crossing.add(t);
		}
		synchronized (t.crossing) {
			t.crossing.add(this);
		}
	}

	/**
	 * are this and given track crossing or nearby?
	 *
	 * @param t other track
	 * @return true: other track crossing or nearby
	 */
	public boolean isCrossing(track t) {
		synchronized (crossing) {
			return crossing.contains(t);
		}
	}

	/**
	 * remove crossing connection between this and given track
	 *
	 * @param t other track
	 */
	public void delCrossing(track t) {
		synchronized (crossing) {
			crossing.remove(t);
		}
		synchronized (t.crossing) {
			t.crossing.remove(this);
		}
	}

	/**
	 * Clear crossing list
	 */
	public void clearAllCrossings() {
		try {
			synchronized (crossing) {
				while (true) {
					Iterator<track> it = crossing.iterator();
					if (it.hasNext()) {
						track t = it.next();
						t.delCrossing(this);
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}

	/**
	 * Crossing list
	 *
	 * @return
	 */
	public java.util.Set<track> getCrossings() {
		synchronized (crossing) {
			return java.util.Collections.unmodifiableSet(crossing);
		}
	}

	/**
	 * check if this and given track cross or are nearby, if so note (addCrossing() called)
	 *
	 * @param t other track
	 */
	public void calcCrossing(track t) {
		if (level == t.level && !isConnectedTo(t) && this != t && bounds.intersects(t.bounds)) {
			for (rotatepoint p : points) {
				for (rotatepoint tp : t.points) {
					int dist = (int) p.distance(tp);
					if (dist < 5) { // crossing

						addCrossing(t);
						return;
					}
				}
			}
		}
	}

	/**
	 * Turn track around in trackset:
	 * start will be end, end will be start, rotation and bowing is modified,
	 * only possible when not a junction
	 *
	 * @return true: success
	 * false: nothing done
	 */
	public boolean turnAround() {
		if (isJunction()) {
			return false;
		}

		// next<>prev
		// x/y=next_x/next_y
		// winkel_a  bow = -bow
		// winkel_s  rot = next_rot-180°
		// trackObject_east<>trackObject_west

		track oldnext = next;
		track oldprev = prev;
		java.util.List<trackObject> oldeast = trackObject_east;
		java.util.List<trackObject> oldwest = trackObject_west;

		x1 = next_x2;
		y1 = next_y2;
		winkel_a = -winkel_a;
		winkel_s = next_winkel_s + 180;
		trackObject_east = oldwest;
		java.util.Collections.reverse(trackObject_east);
		trackObject_west = oldeast;
		java.util.Collections.reverse(trackObject_west);
		next = oldprev;
		prev = oldnext;
		jnext[0] = next;

		calcTrack();
		neighborUpdate();
		updateNeighbors();

		return true;
	}

	/**
	 * move track by value d pixels, rotate, bow and chance size
	 *
	 * @param editTrack
	 * @param d
	 */
	public void transformRelativTo(track editTrack, int d) {
		/*
		 * x/y + (winkel_s+90°)*d
		 * x2/y2 + (next_winkel_s+90°)*d
		 */

		x1 = editTrack.x1 + (int) Math.round(Math.cos(editTrack.winkel_sr + Math.PI / 2) * d);
		y1 = editTrack.y1 + (int) Math.round(Math.sin(editTrack.winkel_sr + Math.PI / 2) * d);
		winkel_s = editTrack.winkel_s;

		int x2, y2;
		x2 = editTrack.next_x2 + (int) Math.round(Math.cos(-editTrack.next_winkel_s * Math.PI / 180 + Math.PI / 2) * d);
		y2 = editTrack.next_y2 + (int) Math.round(Math.sin(-editTrack.next_winkel_s * Math.PI / 180 + Math.PI / 2) * d);

		this.bowresize(x2, y2);

		calcTrack();
		neighborUpdate();
		updateNeighbors();
	}

	class cBc_container {

		public double w1;
		public double w2;
		public int i1;
		public int i2;
	};

	/**
	 * Connect 2 tracks by calculating connect point (including bow & resize)
	 *
	 * @param t other track
	 * @return 0: failure, 1 on success, 2 partly success, new track must be added
	 */
	public int connectByCalc(track t) {
		/*
		 * next_winkel_s=-next_winkel_s(t)
		 * winkel_a=-90..90
		 * l=0..dist(M,M(t))
		 * l(t)=dist(M,M(t))-l
		 * M=next_x/next_y+(0..dist(next_x/next_y,next_x(t)/next_y(t)))
		 * M(t)=next_x(t)/next_y(t)+(0..dist(next_x/next_y,next_x(t)/next_y(t)))
		 * max(l)=dist(next_x/next_y,M)
		 * max(l(t))=dist(next_x(t)/next_y(t),M(t))
		 * l+l(t)=dist(M,M(t))
		 *
		 */

		TreeMap<Integer, cBc_container> conns = new TreeMap<>();

		boolean extended = false; // false: faster

		Graphics2D g = dataCollector.collector.thepainter.getDrawArea();
		point p1 = new point(x1, y1);
		point p2 = new point(t.x1, t.y1);
		int dist = (int) p1.distance(p2);
		double h = p1.arc(p2);
		double h2 = h + winkel_s * Math.PI / 180;
		/*while (h2<0)
		 h2+=Math.PI*2;*/
		h2 = h2 % (Math.PI * 2);
		//System.out.println("h:"+h+"//"+h2);
		//for(int wi=-900;wi<900;wi+=1)
		for (int wi = 0; wi < 900; wi += 1) {
			double w1 = wi / 10.0;
			if (Math.abs(h2) < 0.2) {
				w1 -= 45;
			} else if (h2 >= 0 && h2 < Math.PI) {
				w1 = -w1;
			}
			double nw1 = winkel_s + w1 * 2.0;
			while (nw1 < 0) {
				nw1 += 360;
			}
			nw1 = nw1 % 360;
			double nw2 = nw1 - 180;
			/*while (nw2<0)
			 nw2+=360;*/
			nw2 = nw2 % 360;
			double w2 = (nw2 - t.winkel_s);
			while (w2 < -180) {
				w2 += 360;
			}
			w2 = w2 % 360;
			w2 = w2 / 2;
			//System.out.println("nw1:"+nw1+" nw2:"+nw2+" w1:"+w1+" w2:"+w2);
			if (w2 < -90 || w2 > 90) {
				//System.out.println("-----<");
				continue;
			}
			for (int i1 = extended ? 0 : dist / 2; i1 < dist; ++i1) {
				// point m1=new point((int)(x1+i1*Math.cos((-winkel_s)*java.lang.Math.PI/180.0)),(int)(y1+i1*Math.sin((-winkel_s)*java.lang.Math.PI/180.0)));

				for (int i2 = extended ? 0 : i1 - 5; i2 < i1 + 5; ++i2) {
					//point m2=new point((int)(t.x1+i2*Math.cos((-t.winkel_s)*java.lang.Math.PI/180.0)),(int)(t.y1+i2*Math.sin((-t.winkel_s)*java.lang.Math.PI/180.0)));
					//int sumr=i1+i2;
					//int mdist=(int)m1.distance(m2);

					/*if (sumr>mdist)
					 break;*/
					/*                    if (i1>mdist)
					 continue;
					 if (i2>mdist)
					 continue;*/
					/* if (sumr<mdist)
					 continue; */
					/*
					 * dist:376 w1:-4.4 w2:-16.900000000000006 ws:348.0 tws:167.0 nw1:339.2 nw2:200.8 h:23.298443635827
					 *
					 */

					double nx1 = x1 + (int) java.lang.Math.round(java.lang.Math.cos((-w1 + -winkel_s) * java.lang.Math.PI / 180.0) * i1);
					double ny1 = y1 + (int) java.lang.Math.round(java.lang.Math.sin((-w1 + -winkel_s) * java.lang.Math.PI / 180.0) * i1);

					double nx2 = t.x1 + (int) java.lang.Math.round(java.lang.Math.cos((-w2 + -t.winkel_s) * java.lang.Math.PI / 180.0) * i2);
					double ny2 = t.y1 + (int) java.lang.Math.round(java.lang.Math.sin((-w2 + -t.winkel_s) * java.lang.Math.PI / 180.0) * i2);

					/*g.setColor(Color.RED);
					 g.drawLine((int)x1,(int)y1,(int)nx1,(int)ny1);
					 g.setColor(Color.ORANGE);
					 g.drawLine((int)t.x1,(int)t.y1,(int)nx2,(int)ny2);*/

					if (Math.abs(nx1 - nx2) < 1 && Math.abs(ny1 - ny2) < 1) { // Treffer
						//System.out.println("dist:"+dist+" w1:"+w1+" w2:"+w2+" ws:"+winkel_s+" tws:"+t.winkel_s+" nw1:"+nw1+" nw2:"+nw2+" h:"+(h*180/Math.PI));

						cBc_container c = new cBc_container();
						c.w1 = w1;
						c.w2 = w2;
						c.i1 = i1;
						c.i2 = i2;
						int d = Math.abs(i1 - i2);
						conns.put(d, c);
					}
				}
			}
		}
		if (!conns.isEmpty()) {
			int ret = 1;
			cBc_container c = conns.firstEntry().getValue();

			this.bow(c.w1);
			t.bow(c.w2);
			if (c.i1 > MAXLENGTH) {
				c.i1 = MAXLENGTH;
				ret = 2;
			}
			if (c.i2 > MAXLENGTH) {
				c.i2 = MAXLENGTH;
				ret = 2;
			}
			this.resize(c.i1);
			t.resize(c.i2);
			if (ret == 1) {
				this.connectNext(t);
			}
			return ret;
		}
		return 0;
	}
	private double lbogen;      // Length
	private double winkel_cr;
	private int px, py;
	double winkel_sr;
	double winkel_ar;
	double l2;

	private void calcTrack() {
		while (winkel_s < 0) {
			winkel_s += 360;
		}
		while (winkel_a < -180) {
			winkel_a += 360;
		}
		winkel_a = winkel_a % 360;
		winkel_s = winkel_s % 360;
		if (winkel_a > 90) {
			winkel_a = 90;
		} else if (winkel_a < -90) {
			winkel_a = -90;
		}
		winkel_ar = (-winkel_a) * java.lang.Math.PI / 180.0;
		winkel_sr = (-winkel_s) * java.lang.Math.PI / 180.0;
		int x2, y2;
		double winkel_br = java.lang.Math.PI / 2.0 + (winkel_ar);
		l2 = length / (2 * java.lang.Math.cos(winkel_br));

		x2 = x1 + (int) java.lang.Math.round(java.lang.Math.cos(winkel_ar + winkel_sr) * length);
		y2 = y1 + (int) java.lang.Math.round(java.lang.Math.sin(winkel_ar + winkel_sr) * length);

		px = x1 + (int) java.lang.Math.round(java.lang.Math.cos(winkel_sr - java.lang.Math.PI / 2.0) * l2);
		py = y1 + (int) java.lang.Math.round(java.lang.Math.sin(winkel_sr - java.lang.Math.PI / 2.0) * l2);
		winkel_cr = java.lang.Math.PI - 2.0 * winkel_br;

		next_x2 = x2;
		next_y2 = y2;
		next_winkel_s = winkel_s + winkel_a * 2.0;
		while (next_winkel_s < 0) {
			next_winkel_s += 360;
		}
		next_winkel_s = next_winkel_s % 360;

		startarea = new rectangle(x1 - 8, y1 - 8, x1 + 8, y1 + 8);
		endarea = new rectangle(x2 - 8, y2 - 8, x2 + 8, y2 + 8);

		points = new ArrayList<>();
		railNorth = new ArrayList<>();
		railSouth = new ArrayList<>();
		pointsHash = new HashMap<>();
		bounds = new rectangle(x1, y1, x2, y2); // wrong if winkel_s>90°
		point p;
		rotatepoint rp;
		//p=new point(x1,y1);
		//points.add(p);
		if (winkel_cr != 0.0) {
			lbogen = l2 * winkel_cr; //System.out.println("lbogen: "+lbogen+" l2: "+l2+" w_cr: "+winkel_cr+" w_br: "+winkel_br);

			int kx, ky, okx = Integer.MIN_VALUE, oky = Integer.MIN_VALUE;
			int sl1x = Integer.MIN_VALUE, sl2x = Integer.MIN_VALUE;
			int sl1y = Integer.MIN_VALUE, sl2y = Integer.MIN_VALUE;
			int i;
			int len = Math.abs((int) java.lang.Math.round(lbogen));
			for (i = 0; i <= len; ++i) {
				double w = winkel_sr + java.lang.Math.PI / 2.0 - (winkel_cr / len * i);
				kx = px + (int) java.lang.Math.round(java.lang.Math.cos(w) * l2);
				ky = py + (int) java.lang.Math.round(java.lang.Math.sin(w) * l2);
				if ((kx != okx || ky != oky) && i < len) {
					rp = new rotatepoint(kx, ky, w - java.lang.Math.PI / 2.0);
					pointsHash.put(rp, new Integer(points.size()));
					points.add(rp);
				}
				okx = kx;
				oky = ky;

				int kxN = px + (int) java.lang.Math.round(java.lang.Math.cos(w) * (l2 - RAILRADIUS));
				int kyN = py + (int) java.lang.Math.round(java.lang.Math.sin(w) * (l2 - RAILRADIUS));
				int kxS = px + (int) java.lang.Math.round(java.lang.Math.cos(w) * (l2 + RAILRADIUS));
				int kyS = py + (int) java.lang.Math.round(java.lang.Math.sin(w) * (l2 + RAILRADIUS));
				if (kxN != sl1x || kyN != sl1y || kxS != sl2x || kyS != sl2y) {
					p = new point(kxN, kyN);
					railNorth.add(p);
					p = new point(kxS, kyS);
					railSouth.add(p);
					if (!bounds.contains(kxN, kyN) || !bounds.contains(kxS, kyS)) {
						bounds = new rectangle(bounds.union(new rectangle(kxS, kyS, kxN, kyN)));
					}
				}
				sl1x = kx;
				sl1y = ky;
				sl2x = kx;
				sl2y = ky;
			}
		} else {
			lbogen = length;
			l2 = 0;
			int kx, ky, okx = -1, oky = -1;
			int sl1x = -1, sl2x = -1;
			int sl1y = -1, sl2y = -1;
			kx = (x2 - x1);
			ky = (y2 - y1);
			int kx2 = (int) Math.round(ky * RAILRADIUS / Math.sqrt(ky * ky + kx * kx));
			int ky2 = (int) Math.round(-kx * RAILRADIUS / Math.sqrt(ky * ky + kx * kx));
			int i;
			int len = Math.abs((int) java.lang.Math.round(length));
			for (i = 0; i <= len; ++i) {
				kx = x1 + (x2 - x1) * i / len;
				ky = y1 + (y2 - y1) * i / len;
				if ((kx != okx || ky != oky) && i < len) {
					rp = new rotatepoint(kx, ky, winkel_sr);
					pointsHash.put(rp, new Integer(points.size()));
					points.add(rp);
				}
				okx = kx;
				oky = ky;

				kx = (x2 - x1) * i / len;
				ky = (y2 - y1) * i / len;
				if (kx != sl1x || ky != sl1y) {
					p = new point(x1 + kx + kx2, y1 + ky + ky2);
					railNorth.add(p);
					p = new point(x1 + kx - kx2, y1 + ky - ky2);
					railSouth.add(p);
				}
				sl1x = kx;
				sl1y = ky;
			}

		}
		clickbounds = bounds.getBounds();
		if (clickbounds.getHeight() < 20) {
			clickbounds.setLocation(clickbounds.x, clickbounds.y - 10);
			clickbounds.setSize(clickbounds.width, clickbounds.height + 20);
		}
		if (clickbounds.getWidth() < 20) {
			clickbounds.setLocation(clickbounds.x - 10, clickbounds.y);
			clickbounds.setSize(clickbounds.width + 20, clickbounds.height);
		}
		if (points.size() < trackObject_east.size()) {
			reduceArray(trackObject_east, points.size());
		} else {
			while (trackObject_east.size() < points.size()) {
				trackObject_east.add(null);
			}
		}
		if (points.size() < trackObject_west.size()) {
			reduceArray(trackObject_west, points.size());
		} else {
			while (trackObject_west.size() < points.size()) {
				trackObject_west.add(null);
			}
		}
		//System.out.println(bounds.toString());
		//System.out.println("X: "+x1+" Y: "+y1+" dX: "+x2+" dY: "+y2);
	}

	private void reduceArray(java.util.List<trackObject> lst, int maxlen) {
		while (lst.size() > maxlen) {
			int j;
			for (j = lst.size() - 1; j >= 0; --j) {
				if (lst.get(j) == null) {
					lst.remove(j);
					break;
				}
			}
			if (j == 0 && lst.size() > maxlen) {
				lst.remove(lst.size() - 1);
			}
		}
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		String ret = "";
		ret += "X1/Y1:" + x1 + "/" + y1 + " X2/Y2:" + next_x2 + "/" + next_y2;
		return ret;
	}

	/**
	 *
	 * @return
	 */
	public String dumpString() {
		String ret = "";
		int i = 0;
		Iterator<rotatepoint> it = points.iterator();
		while (it.hasNext()) {
			point p = it.next();
			ret += "(" + i + ":" + p + ")";
			++i;
		}
		ret += "\n";
		int[] m = getJunctionMeaning();
		for (i = 0; i < jnext.length; ++i) {
			ret += "[" + i + ":";
			ret += m[i] + "/";
			if (jnext[i] != null) {
				ret += jnext[i].winkel_a + "°";
			}
			ret += "]";
		}
		return ret;
	}

	/**
	 * set painter
	 *
	 * @param _tp new track painter
	 */
	public void setPainter(trackPainter _tp) {
		tp = _tp;
	}

	/**
	 * get Painter
	 *
	 * @return current track painter
	 */
	public trackPainter getPainter() {
		return tp;
	}

	/**
	 * Malen - calls paintTrack(g2,PART_ALL);
	 *
	 * @param g2 Graphics2D
	 */
	public void paintTrack(Graphics2D g2) {
		paintTrack(g2, PART_ALL);
	}

	/**
	 * Malen
	 *
	 * @param g2 Graphics2D
	 * @param part PART_...
	 */
	public void paintTrack(Graphics2D g2, int part) {
		if (helplines) {
			paintHelpLines(g2);
		}
		/*	if (axisC>0)
		 g2.setColor(Color.CYAN);
		 else */

		tp.paint(this, (Graphics2D) g2.create(), part, railNorth, railSouth, points, progress);
		if (DEBUGCROSSING && !crossing.isEmpty()) {
			int kx, ky, okx = x1, oky = y1;
			g2.setColor(Color.RED);
			for (point p : points) {
				kx = p.getX();
				ky = p.getY();
				g2.drawLine(okx, oky, kx, ky);
				okx = kx;
				oky = ky;
			}
			g2.drawLine(okx, oky, next_x2, next_y2);
		}
		if ((part & PART_RAIL) != 0) {
			if (prev == null) {
				point p1 = railNorth.get(0);
				point p2 = railSouth.get(0);
				g2.setColor(Color.RED);
				Stroke os = g2.getStroke();
				g2.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
				g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
				g2.setStroke(os);
			}
			if (next == null) {
				point p1 = railNorth.get(railNorth.size() - 1);
				point p2 = railSouth.get(railSouth.size() - 1);
				g2.setColor(Color.RED);
				Stroke os = g2.getStroke();
				g2.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
				g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
				g2.setStroke(os);
			}
			if (jnext[1] != null) {
				int s = getJunction();
				int[] m = getJunctionMeaning();
				Graphics2D g = (Graphics2D) g2.create();
				point xy = railSouth.get(railSouth.size() - 1);
				g.translate(xy.getX(), xy.getY());
				g.rotate(winkel_sr + winkel_ar * 2.0 + Math.PI / 2.0);
				tp.paintJunction(this, g, m[s]);
			}

			if (DEBUGAXIS) {
				g2.setColor(Color.YELLOW);
				g2.drawString("A:" + axisC, x1, y1);
			}
		}
		if ((part & PART_SLEEPER) != 0) {
			int i;
			for (i = 0; i < points.size(); ++i) {
				trackObject to;
				to = trackObject_east.get(i);
				if (to != null && (to.getPaintAlways() || PAINTALLTRACKOBJECTS)) {
					Graphics2D g = (Graphics2D) g2.create();
					rotatepoint xy = points.get(i);
					g.translate(xy.getX(), xy.getY());
					g.rotate(xy.getRotate() + Math.PI / 2.0);
					g.translate(0, -to.getBounds().height);
					to.paintTO(g);
				}
				to = trackObject_west.get(i);
				if (to != null && (to.getPaintAlways() || PAINTALLTRACKOBJECTS)) {
					Graphics2D g = (Graphics2D) g2.create();
					rotatepoint xy = points.get(i);
					g.translate(xy.getX(), xy.getY());
					g.rotate(Math.PI + xy.getRotate() + Math.PI / 2.0);
					g.translate(0, -to.getBounds().height);
					to.paintTO(g);
				}
			}
		}
		if ((part & PART_ABOVE) != 0) {
		}
	}

	/**
	 *
	 * @param g2
	 */
	public void paintHelpLines(Graphics2D g2) {
		//int sx=x1+(int)java.lang.Math.round(java.lang.Math.cos(winkel_sr)*300);
		//int sy=y1+(int)java.lang.Math.round(java.lang.Math.sin(winkel_sr)*300);

		//g2.setColor(Color.BLUE);
		//g2.drawLine(x1,y1,sx,sy);

		//g2.setColor(Color.BLACK);
		//g2.drawLine(x1,y1,next_x2,next_y2);

		g2.setColor(Color.RED);
		g2.drawLine(x1, y1, px, py);
		g2.drawLine(next_x2, next_y2, px, py);
	}

	public void paintMini(Graphics2D g2) {
		int kx, ky, okx = Integer.MIN_VALUE, oky = Integer.MIN_VALUE;
		for (point p : points) {
			kx = p.getX();
			ky = p.getY();
			if (okx > Integer.MIN_VALUE || oky > Integer.MIN_VALUE) {
				g2.drawLine(okx, oky, kx, ky);
			}
			okx = kx;
			oky = ky;
		}
	}

	private int findPoint(int x, int y) {
		return findPoint(new point(x, y));
		/*int i=0;
		 Iterator<point> it=points.iterator();
		 while (it.hasNext())
		 {
		 point p=it.next();
		 if (p.isPoint(x,y))
		 {
		 return i;
		 }
		 ++i;
		 }
		 return -1; */
	}

	private int findPoint(point xy) {
		Integer i = pointsHash.get(xy);
		if (i == null) {
			return -1;
		} else {
			return i.intValue();
		}
		/*

		 Iterator<point> it=points.iterator();
		 while (it.hasNext())
		 {
		 point p=it.next();
		 if (p.isPoint(xy))
		 {
		 return i;
		 }
		 ++i;
		 }
		 return -1; */
	}

	private int findPointGlobal(point a1) throws PositionNotFoundException {
		boolean inNext = false, inPrev = false;
		int p1 = findPoint(a1);
		if (p1 < 0 && next != null) {
			p1 = next.findPoint(a1);
			if (p1 >= 0) {
				p1 += points.size();
				inNext = true;
			}
		}
		if (p1 < 0 && prev != null) {
			p1 = prev.findPoint(a1);
			if (p1 >= 0) {
				p1 -= prev.points.size();
				inPrev = true;
			}
		}
		if (p1 < 0 && !inPrev && !inNext) {
			throw new PositionNotFoundException();
		}
		return p1;
	}

	private int findPointNearby(point a1) throws PositionNotFoundException {
		int dist = Integer.MAX_VALUE;
		int pos = -1;
		int i = 0;
		for (point p : points) {
			int d = (int) Math.round(p.distance(a1));
			if (d < dist) {
				dist = d;
				pos = i;
			}
			++i;
		}
		if (pos < 0) {
			throw new PositionNotFoundException();
		}
		return pos;
	}

	private int findPointNearbyGlobal(point a1) throws PositionNotFoundException {
		int dist = Integer.MAX_VALUE;
		int pos = -1;
		int i = 0;
		boolean foundone = false;
		for (point p : points) {
			int d = (int) Math.round(p.distance(a1));
			if (d < dist) {
				dist = d;
				pos = i;
				foundone = true;
			}
			++i;
		}
		if (prev != null) {
			int pos2 = -1;
			i = 0;
			for (point p : prev.points) {
				int d = (int) Math.round(p.distance(a1));
				if (d < dist) {
					dist = d;
					pos2 = i;
				}
				++i;
			}
			if (pos2 >= 0) {
				if (prev.prev == this) {
					pos = -pos2;
					foundone = true;
				} else {
					pos = pos2 - prev.points.size() - 1;
					foundone = true;
				}
			}
		}
		if (next != null) {
			int pos2 = -1;
			i = 0;
			for (point p : next.points) {
				int d = (int) Math.round(p.distance(a1));
				if (d < dist) {
					dist = d;
					pos2 = i;
				}
				++i;
			}
			if (pos2 >= 0) {
				if (next.prev == this) {
					pos = points.size() + pos2;
					foundone = true;
				} else {
					pos = points.size() + (next.points.size() - pos2);
					foundone = true;
				}
			}
		}
		if (!foundone) {
			throw new PositionNotFoundException();
		}
		return pos;
	}

	/**
	 * checks if given track has same orientation, must be connected
	 *
	 * @param t other track
	 * @return true if same orientation
	 * @throws NotConnectedException if not connected
	 */
	public boolean hasSameOriantation(track t) throws NotConnectedException {
		if (t == this) {
			return true;
		}
		if (!isConnectedTo(t)) {
			throw new NotConnectedException(this, t);
		}
		if (prev == t) {
			if (t.prev == this) { // start//start
				return false;
			}
			return true;
		} else {
			if (t.prev == this) { // start//end
				return true;
			}
			return false;
		}
	}

	private trackindexpoint getIndexPoint(int i) {
		trackindexpoint ret = null;
		if (i < 0) {
			if (prev != null) {
				if (prev.next == this) {
					ret = prev.getIndexPoint(prev.points.size() + i);
				} else if (prev.prev == this) {
					ret = prev.getIndexPoint(-i - 1);
				}
			}
		} else if (i >= points.size()) {
			if (next != null) {
				if (next.prev == this) {
					ret = next.getIndexPoint(i - points.size());
				} else if (next.next == this) {
					ret = next.getIndexPoint(next.points.size() + i - points.size() - 1);
				}
			}
		} else {
			ret = new trackindexpoint(this, i, points.get(i));
		}
		return ret;
	}

	/**
	 * get track and index of this point (trackindexpoint)
	 * searches in track (this) and its neighbors (prev, next - switched junction path)
	 *
	 * @param x X
	 * @param y Y
	 * @return trackindexpoint
	 * @throws railsim.simplytrain.service.PositionNotFoundException if point is too far
	 */
	public trackindexpoint getTrackPoint(int x, int y) throws PositionNotFoundException {
		trackindexpoint ret = null;
		ret = getIndexPoint(findPointNearbyGlobal(new point(x, y)));
		return ret;
	}

	/**
	 * sets a track object at the specified position, returns true on success
	 * if position is already used and param to is not null, function has no success
	 *
	 * @param to trackobject or null to remove a to
	 * @param forward direction, true for forward direction
	 * @param index position inside track
	 * @return true: success
	 */
	public boolean setTrackObjectAt(trackObject to, boolean forward, int index) {
		if (forward) {
			if (to == null || trackObject_east.get(index) == null) {
				trackObject_east.set(index, to);
				if (to != null) {
					trackobjectpoint td = new trackobjectpoint(this, index, true, to);
					to.setTrackData(td);
					//System.out.println("F to: "+to.getName()+" R: "+to.getRegion()+" top: "+td+"//"+to.getTrackData());
				}
			} else {
				return false;
			}
		} else {
			if (to == null || trackObject_west.get(index) == null) {
				trackObject_west.set(index, to);
				if (to != null) {
					trackobjectpoint td = new trackobjectpoint(this, index, false, to);
					to.setTrackData(td);
					//System.out.println("F- to: "+to.getName()+" R: "+to.getRegion()+" top: "+td+"//"+to.getTrackData());
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Like setTrackObjectAt(trackObject to,boolean forward,int index) but tries other index point
	 * if place already in use
	 *
	 * @param to trackobject or null to remove a to
	 * @param forward direction, true for forward direction
	 * @param index position inside track
	 * @return true: success
	 * @see setTrackObjuectAt(trackObject,boolean,int)
	 */
	public boolean save_setTrackObjectAt(trackObject to, boolean forward, int index) {
		int p1 = index;
		int i = 0;
		do {
			trackindexpoint tp1 = getIndexPoint(p1 + i);
			if (tp1 != null && tp1.getTrack().setTrackObjectAt(to, forward, tp1.getIndex())) {
				return true;
			}
			trackindexpoint tp2 = getIndexPoint(p1 - i);
			if (tp2 != null && tp2.getTrack().setTrackObjectAt(to, forward, tp2.getIndex())) {
				return true;
			}
			if (tp1 == null && tp2 == null) {
				break;
			}
			++i;
		} while (true);
		return false;
	}

	/**
	 * sets a track object at the specified position, returns true on success
	 * if position is already used and param to is not null, function has no success
	 *
	 * @param to trackobject or null to remove a to
	 * @param forward direction, true for forward direction
	 * @param x X - must be on track
	 * @param y Y - must be on track
	 * @return true: success
	 * @see setTrackObjuectAt(trackObject,boolean,int)
	 */
	public boolean setTrackObjectAt(trackObject to, boolean forward, int x, int y) {
		trackindexpoint p = null;
		int p1;
		try {
			p1 = findPointNearby(new point(x, y));
		} catch (PositionNotFoundException e) {
			return false;
		}
		p = getIndexPoint(p1);

		return p.getTrack().setTrackObjectAt(to, forward, p.getIndex());
	}

	/**
	 * Like setTrackObjectAt(trackObject to,boolean forward,int x,int y) but tries other index point
	 * if place already in use
	 *
	 * @param to trackobject or null to remove a to
	 * @param forward direction, true for forward direction
	 * @param x X - must be on track
	 * @param y Y - must be on track
	 * @return true: success
	 * @see setTrackObjuectAt(trackObject,boolean,int)
	 * @see setTrackObjuectAt(trackObject,boolean,int,int)
	 */
	public boolean save_setTrackObjectAt(trackObject to, boolean forward, int x, int y) {
		int p1;
		try {
			p1 = findPointNearby(new point(x, y));
		} catch (PositionNotFoundException e) {
			dataCollector.collector.gotException(e);
			return false;
		}
		int i = 0;
		do {
			trackindexpoint tp1 = getIndexPoint(p1 + i);
			if (tp1 != null && tp1.getTrack().setTrackObjectAt(to, forward, tp1.getIndex())) {
				return true;
			}
			trackindexpoint tp2 = getIndexPoint(p1 - i);
			if (tp2 != null && tp2.getTrack().setTrackObjectAt(to, forward, tp2.getIndex())) {
				return true;
			}
			if (tp1 == null && tp2 == null) {
				break;
			}
			++i;
		} while (true);
		return false;
	}

	/**
	 * Get all trackObjects of track in forward or backwards direction
	 *
	 * @param forward forward direction (true) or backwards (false)
	 * @return list of trackObjects
	 */
	public java.util.List<trackObject> getTrackObjects(boolean forward) {
		if (forward) {
			return trackObject_east;
		} else {
			return trackObject_west;
		}
	}

	public boolean hasStopObject() {
		for (trackObject to : trackObject_east) {
			if (to != null && to instanceof stopObject) {
				return true;
			}
		}
		for (trackObject to : trackObject_west) {
			if (to != null && to instanceof stopObject) {
				return true;
			}
		}
		return false;
	}

	public boolean hasObject() {
		for (trackObject to : trackObject_east) {
			if (to != null) {
				return true;
			}
		}
		for (trackObject to : trackObject_west) {
			if (to != null) {
				return true;
			}
		}
		return false;
	}

	private stopObject trackObject(fulltrain tf, trackindexpoint p, boolean forward, int step) {
		stopObject ret = null;

		java.util.List<trackObject> tol;
		if (forward) {
			tol = trackObject_east;
		} else {
			tol = trackObject_west;
		}

		trackObject to;
		try {
			to = tol.get(p.getIndex());
		} catch (ArrayIndexOutOfBoundsException ex) {
			return null;
		}
		if (to != null) {
			to.updateTrainT(tf, step);
			if ((step & (trackObject.TRAINSTEP_START | trackObject.TRAINSTEP_PRERUNNER)) != 0 && to instanceof stopObject) {
				stopObject so = (stopObject) to;
				if (so.isStoppedT(tf, step)) {
					ret = so;
				} else {
					tf.passedStopObject(so, step);
				}
			}
		}
		return ret;
	}

	/**
	 * Get bounds of trackObject, translated (moved, rotated) to track position and rotation
	 *
	 * @param to trackObject
	 * @param forward direction
	 * @param index position
	 * @return bounds
	 */
	public rectangle translateTrackObjectBounds(trackObject to, boolean forward, int index) {
		try {
			rectangle ret = new rectangle(to.getBounds());
			rotatepoint xy = points.get(index);

			/*rotatepoint xy=points.get(i);
			 g.translate(xy.getX(),xy.getY());
			 g.rotate(xy.getRotate()+Math.PI/2.0); */

			ret.setLocation(xy.getX(), xy.getY());
			java.awt.geom.AffineTransform aff = new java.awt.geom.AffineTransform();
			aff.translate(xy.getX(), xy.getY());
			if (forward) {
				aff.rotate(xy.getRotate() + Math.PI / 2.0);
			} else {
				aff.rotate(Math.PI + xy.getRotate() + Math.PI / 2.0);
			}
			aff.translate(-xy.getX(), -xy.getY());
			aff.translate(0, -to.getBounds().height);

			ret.transform(aff);

			/*
			 ret.setLocation(xy.getX(),xy.getY());
			 if (forward)
			 ret.rotate(xy.getRotate()+Math.PI/2.0,xy.getX(),xy.getY());
			 else
			 ret.rotate(Math.PI+xy.getRotate()+Math.PI/2.0,xy.getX(),xy.getY());
			 */
			return ret;
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
			return null;
		}
	}

	/**
	 * Get bounds of trackObject, translated (moved, rotated) to track position and rotation
	 *
	 * @param to trackObject
	 * @return bounds
	 */
	static public rectangle translateTrackObjectBounds(trackObject to) {
		trackobjectpoint top = to.getTrackData();
		return top.getTrack().translateTrackObjectBounds(to, top.getForward(), top.getIndex());
	}

	/**
	 * nächster Punkt für 1. Wagenachse (a1) - only if fulltrain given track object handling and axis counting is done
	 *
	 * @return nächster Punkt für 1. Wagenachse oder null
	 * @param step part of train
	 * @param t fulltrain
	 * @param a1 1. Wagenachse
	 * @param a2 2. Wagenachse
	 * @throws railsim.simplytrain.service.TrainStoppedException train reached stop track object
	 */
	public trackpoint nextPoint1(fulltrain t, point a1, point a2, int step) throws TrainStoppedException {
		trackindexpoint p = null;
		int p1, p2;
		try {
			p1 = findPointGlobal(a1);
			p2 = findPointGlobal(a2);
		} catch (PositionNotFoundException e) {
			return null;
		}
		boolean forward = p1 > p2;
		if (forward) {
			++p1;
		} else {
			--p1;
		}

		p = getIndexPoint(p1);
		if (p != null && t != null && (step & ~trackObject.TRAINSTEP_END) != 0) {
			stopObject canmove = p.getTrack().trackObject(t, p, forward, step);
			if (canmove != null) {
				throw new TrainStoppedException(t, p.getTrack(), canmove);
			}
		}
		if (p != null && p.getTrack() != this && t != null && (step & trackObject.TRAINSTEP_PRERUNNER) == 0) {
			p.getTrack().addAxis();
			delAxis();
		}
		return p == null ? null : new trackpoint(p);
	}

	/**
	 * nächster Punkt für 2. Wagenachse (a2) - only if fulltrain given track object handling and axis counting is done
	 *
	 * @return nächster Punkt für 2. Wagenachse oder null
	 * @see nextPoint1(fulltrain,point,point)
	 * @param step part of train
	 * @param t fulltrain
	 * @param a1 1. Wagenachse
	 * @param a2 2. Wagenachse
	 * @throws railsim.simplytrain.service.TrainStoppedException train reached stop track object
	 */
	public trackpoint nextPoint2(fulltrain t, point a1, point a2, int step) throws TrainStoppedException {
		trackindexpoint p = null;
		int p1, p2;
		try {
			p1 = findPointGlobal(a1);
			p2 = findPointGlobal(a2);
		} catch (PositionNotFoundException e) {
			return null;
		}
		boolean forward = p1 > p2;
		if (forward) {
			++p2;
		} else {
			--p2;
		}

		p = getIndexPoint(p2);
		if (p != null && t != null && (step & trackObject.TRAINSTEP_END) != 0) {
			stopObject canmove = p.getTrack().trackObject(t, p, forward, step);
			if (canmove != null) {
				throw new TrainStoppedException(t, p.getTrack(), canmove);
			}
		}
		if (p.getTrack() != this && t != null && (step & trackObject.TRAINSTEP_PRERUNNER) == 0) {
			p.getTrack().addAxis();
			delAxis();
		}
		return p;
	}

	/**
	 * 1. Wagenachse aufs Gleis setzen
	 *
	 * @return Punkt
	 */
	public trackpoint onTrack() {
		return new trackpoint(this, points.get(0));
	}

	/**
	 * weitere Wagenachse aufs Gleis setzen, distance Punkte entfernt
	 *
	 * @return Punkt
	 * @param a1 vorherigen Punkt
	 * @param distance Entfernung (positiv rechts, negativ links)
	 */
	public trackpoint onTrack(point a1, int distance) {
		int p1 = findPoint(a1);
		if (p1 < 0) {
			return null;
		}
		p1 += distance;
		return getIndexPoint(p1);
	}

	/**
	 * distance between 2 points
	 *
	 * @param a1 point 1
	 * @param a2 point 2
	 * @throws org.railsim.service.exceptions.PositionNotFoundException
	 * @return distance a2-a1 - <0: a2 before a1, >0: a2 behind a1
	 */
	public int getPointDistance(trackpoint a1, trackpoint a2) throws PositionNotFoundException {
		int p1, p2;
		p1 = findPointGlobal(a1);
		p2 = findPointGlobal(a2);
		return p2 - p1;
	}

	/**
	 * level of track
	 *
	 * @param l new level
	 */
	public void setLevel(int l) {
		if (l >= MINLEVEL && l <= MAXLEVEL) {
			level = l;
		}
	}

	/**
	 * level of track
	 *
	 * @return level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * X/Y of next track
	 *
	 * @return point for next track
	 */
	public point getDestXY() {
		return new point(next_x2, next_y2);
	}

	/**
	 * Get track start X/Y
	 *
	 * @return point x/y
	 */
	public point getXY() {
		return new point(x1, y1);
	}

	public int getMinX() {
		return Math.min(x1, next_x2);
	}

	public int getMaxX() {
		return Math.max(x1, next_x2);
	}

	public int getMinY() {
		return Math.min(y1, next_y2);
	}

	public int getMaxY() {
		return Math.max(y1, next_y2);
	}

	/**
	 * Get point X/Y of track index part
	 *
	 * @param index index
	 * @return point X/Y
	 */
	public point getXY(int index) {
		if (index >= 0 && index < points.size()) {
			return points.get(index);
		} else {
			return null;
		}
	}

	/**
	 * direct length
	 *
	 * @return length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Distance, same as length if bowing is 0, else the length of the bow
	 *
	 * @return length of bow
	 */
	public double getDistance() {
		return lbogen;
	}

	/**
	 * get rotation of track
	 *
	 * @see getRotation
	 * @deprecated use getRotation()
	 * @return rotation in degree
	 */
	public double getStartArc() {
		return getRotation();
	}

	/**
	 * get rotation of track
	 *
	 * @return rotation in degree
	 */
	public double getRotation() {
		return winkel_s;
	}

	/**
	 * rotation of next connected track, depends on this rotation and bowing
	 *
	 * @return rotation in degree
	 */
	public double getNextRotation() {
		return next_winkel_s;
	}

	/**
	 * Angel (bowing) of track
	 *
	 * @return bowing in degree
	 * @deprecated use getBow()
	 */
	public double getAngel() {
		return winkel_a;
	}

	/**
	 * Bowing of track
	 *
	 * @return bowing in degree
	 */
	public double getBow() {
		return winkel_a;
	}

	/**
	 * Radius of circle of bowing track
	 *
	 * @return radius
	 */
	public double getRadius() {
		return l2;
	}

	/**
	 * Bounds of track
	 *
	 * @return bounds
	 */
	public rectangle getBounds() {
		return bounds;
	}

	/**
	 * Bounds of track including 20 pixel around if bounds are too small.
	 *
	 * @return bounds
	 */
	public rectangle getClickBounds() {
		return clickbounds;
	}

	/**
	 * Rectangle of the start area
	 *
	 * @return bounds
	 */
	public rectangle getStartArea() {
		return startarea;
	}

	/**
	 * Rectangle of the end area
	 *
	 * @return bounds
	 */
	public rectangle getEndArea() {
		return endarea;
	}

	/**
	 * Next connected track
	 *
	 * @return track or null
	 */
	public track getNext() {
		return next;
	}

	/**
	 * Number of possibly connected tracks on NEXT side
	 *
	 * @return number
	 */
	public int getJunctionCount() {
		return jnext.length;
	}

	/**
	 * Number of connected tracks on NEXT side, more than 1 means junction
	 *
	 * @return true: junction
	 */
	public boolean isJunction() {
		return jnext[1] != null;
	}

	/**
	 * get NEXT track at number param p, p >1 only usefull for junctions
	 *
	 * @param p number of next
	 * @return track or null
	 */
	public track getNext(int p) {
		return jnext[p];
	}

	/**
	 * get PREV track
	 *
	 * @return track or null
	 */
	public track getPrev() {
		return prev;
	}

	/**
	 * Number of which junction part is set
	 *
	 * @return Number
	 * @see getJunctionCount()
	 * @see getNext(int)
	 */
	public int getJunction() {
		int p = 0;
		for (int i = 0; i < jnext.length; ++i) {
			if (jnext[i] == next) {
				p = i;
				break;
			}
		}
		return p;
	}

	/**
	 * Paint data of track for saving
	 *
	 * @return Array of data
	 */
	public String[] getPainterData() {
		return tp.getPainterData();
	}

	/**
	 * Set paint data when loading
	 *
	 * @param v Paint data
	 */
	public void setPainterData(String v) {
		tp.setPainterData(v);
	}

	private double getWa(track ct) {
		if (prev == ct) {
			return winkel_a;
		} else {
			return -winkel_a;
		}
	}

	/**
	 * Meaning of junction settings (direction).
	 *
	 * @return int array, -2: right junction, -1: right, +1: left, +2: left junction, 0: *unused with 2 way junctions
	 *
	 * @see getJunction()
	 * @see setJunction()
	 */
	public int[] getJunctionMeaning() {
		if (last_junction_meaning == null) {
			int[] r = new int[jnext.length];
			int i, mini = 0, maxi = 0;
			for (i = 0; i < jnext.length; ++i) {
				r[i] = 0;
			}
			for (i = 0; i < jnext.length; ++i) {
				if (jnext[i] != null) {
					if (jnext[i].getWa(this) > jnext[maxi].getWa(this)) {
						maxi = i;
					}
					if (jnext[i].getWa(this) < jnext[mini].getWa(this)) {
						mini = i;
					}
				}
			}
			r[mini] = -1;
			r[maxi] = +1;
			if (Math.abs(jnext[mini].getWa(this)) > Math.abs(jnext[maxi].getWa(this))) {
				r[mini]--;
			} else {
				r[maxi]++;
			}
			if (jnext[mini].getWa(this) < -15 && r[mini] > -2) {
				r[mini]--;
			}
			if (jnext[maxi].getWa(this) > 15 && r[maxi] < 2) {
				r[maxi]++;
			}
			last_junction_meaning = r;
			return r;
		} else {
			return last_junction_meaning;
		}
	}

	/**
	 * remove all tracks entries in lists (crossing, junctions) and all its trackObjects
	 */
	public void remove() {
		setPrev(null);
		int c = getJunctionCount();
		for (int j = 0; j < c; ++j) {
			setNext(null, j);
		}
		setNext(null);
		clearAllCrossings();
		for (trackObject to : trackObject_east) {
			if (to != null) {
				to.remove();
			}
		}
		for (trackObject to : trackObject_west) {
			if (to != null) {
				to.remove();
			}
		}
		if (trg != null) {
			trg.remove(this);
		}
	}

	public boolean isInGroup() {
		return trg != null;
	}

	public trackGroup getGroup() {
		return trg;
	}

	public boolean add(trackGroup tg) {
		return addToGroup(tg);
	}

	public boolean addToGroup(trackGroup tg) {
		if (trg == null) {
			trg = tg;
			tg.add(this);
			return true;
		}
		return false;
	}

	public boolean remove(trackGroup tg) {
		return removeFromGroup(tg);
	}

	public boolean removeFromGroup(trackGroup tg) {
		if (trg == tg) {
			trg = null;
			tg.remove(this);
			return true;
		}
		return false;
	}

	public java.util.List<rotatepoint> getPoints() {
		return java.util.Collections.unmodifiableList(points);
	}
	public static final int GLTN_STOPOBJECT = 0x01;
	public static final int GLTN_PATHABLEOBJECT = 0x02;
	public static final int GLTN_DESTINATIONOBJECT = 0x04;
	public static final int GLTN_SIGNAL = 0x08;

	private void getListToNextStopObject(int start, boolean forward, int searchtype, LinkedList<Object> ret) {
		ret.add(this);

		if (forward) {
			if (start < -1) {
				start = 0;
			}
			for (int i = start; i < trackObject_east.size(); ++i) {
				trackObject to = trackObject_east.get(i);
				if (to != null) {
					ret.add(to);
				}
				if ((searchtype & GLTN_STOPOBJECT) != 0 && to instanceof stopObject) {
					return;
				}
				if ((searchtype & GLTN_SIGNAL) != 0 && to instanceof stopObject && !((stopObject) to).canGreen()) {
					return;
				}
				if ((searchtype & GLTN_SIGNAL) != 0 && to instanceof pathableObject) {
					return;
				}
				if ((searchtype & GLTN_PATHABLEOBJECT) != 0 && to instanceof pathableObject) {
					return;
				}
				if ((searchtype & GLTN_DESTINATIONOBJECT) != 0 && to instanceof destinationObject) {
					return;
				}
			}
		} else {
			if (start < -1) {
				start = trackObject_west.size() - 1;
			}
			for (int i = start; i >= 0; --i) {
				trackObject to = trackObject_west.get(i);
				if (to != null) {
					ret.add(to);
				}
				if ((searchtype & GLTN_STOPOBJECT) != 0 && to instanceof stopObject) {
					return;
				}
				if ((searchtype & GLTN_SIGNAL) != 0 && to instanceof stopObject && !((stopObject) to).canGreen()) {
					return;
				}
				if ((searchtype & GLTN_SIGNAL) != 0 && to instanceof pathableObject) {
					return;
				}
				if ((searchtype & GLTN_PATHABLEOBJECT) != 0 && to instanceof pathableObject) {
					return;
				}
				if ((searchtype & GLTN_DESTINATIONOBJECT) != 0 && to instanceof destinationObject) {
					return;
				}
			}
		}
		try {
			if (next != null && forward) {
				if (next.getProgress() > 0) {
					ret.add(new tracksearchinfo(tracksearchinfo.CODE_BUILDING));
				}
				if (!hasSameOriantation(next) && next.getNext() != this) {
					ret.add(new tracksearchinfo(tracksearchinfo.CODE_JUNCTIONWRONG));
				}
				next.getListToNextStopObject(-2, forward == hasSameOriantation(next), searchtype, ret);
			} else if (prev != null && !forward) {
				if (prev.getProgress() > 0) {
					ret.add(new tracksearchinfo(tracksearchinfo.CODE_BUILDING));
				}
				if (hasSameOriantation(prev) && prev.getNext() != this) {
					ret.add(new tracksearchinfo(tracksearchinfo.CODE_JUNCTIONWRONG));
				}
				prev.getListToNextStopObject(-2, forward == hasSameOriantation(prev), searchtype, ret);
			} else {
				ret.add(new tracksearchinfo(tracksearchinfo.CODE_ENDOFTRACK));
			}
		} catch (NotConnectedException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}

	/**
	 *
	 * @param tip
	 * @param forward
	 * @param searchtype
	 * @throws org.railsim.service.exceptions.NotThisTrackException
	 * @return
	 */
	public LinkedList<Object> getListToNextStopObject(trackindexpoint tip, boolean forward, int searchtype) throws NotThisTrackException {
		if (tip.getTrack() != this) {
			throw new NotThisTrackException(this, tip.getTrack());
		}

		LinkedList<Object> ret = new LinkedList<>();
		getListToNextStopObject(tip.getIndex() + (forward ? 1 : -1), forward, searchtype, ret);

		return ret;
	}

	/**
	 *
	 * @param tip
	 * @param searchtype
	 * @throws java.lang.IllegalArgumentException
	 * @return
	 */
	static public LinkedList<Object> getListToNextStopObject(trackobjectpoint tip, int searchtype) throws java.lang.IllegalArgumentException {
		if (tip.getTrack() == null) {
			throw new java.lang.IllegalArgumentException();
		}
		try {
			return tip.getTrack().getListToNextStopObject(tip, tip.getForward(), searchtype);
		} catch (NotThisTrackException ex) {
			dataCollector.collector.gotException(ex);
		}
		return null;
	}

	/**
	 * search starting from trackObject to next object specified by searchtype,
	 * returns a list of found objects:
	 *
	 * track: a track
	 * trackObject*: object on track
	 * tracksearchinfo: optional with special data for following object
	 * track: next track
	 * ...
	 *
	 * @param t
	 * @param searchtype
	 * @throws java.lang.IllegalArgumentException
	 * @return
	 */
	static public LinkedList<Object> getListToNextStopObject(trackObject t, int searchtype) throws java.lang.IllegalArgumentException {
		if (t.getTrackData() == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return t.getTrackData().getTrack().getListToNextStopObject(t.getTrackData(), searchtype);
	}
	private int axisC = 0;
	private int usageCounter = 0;
	private path reservedBy = null;

	public void addAxis() {
		++axisC;
	}

	public void delAxis() {
		if (axisC > 0) {
			--axisC;
		}
		if (axisC == 0) {
			//System.out.println("R gelöscht "+reservedBy);
			reservedBy = null;
		}
	}

	/**
	 * modify track reserved flag
	 *
	 * @param r new value
	 */
	public void setReserve(path p) {
		if (p != null) {
			reservedBy = p;
		}
	}

	/**
	 * modify track reserved flag
	 *
	 * @param r old value to verify
	 */
	public void unReserve(path p) {
		if (p == reservedBy) {
			reservedBy = null;
		}
	}

	/**
	 * is reserved flag set
	 *
	 * @return
	 */
	public boolean isReserved() {
		return reservedBy != null;
	}

	/**
	 * get reserved flag value
	 *
	 * @return value
	 */
	public path getReserved() {
		return reservedBy;
	}

	/**
	 * get combined reserved flag and axis counter
	 *
	 * @return true if track not reserved and no train axis on it
	 */
	public boolean isFree() {
		if (reservedBy == null && axisC == 0) {
			synchronized (crossing) {
				for (track t : crossing) {
					if (t.reservedBy != null || t.axisC > 0) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean isTrainOn() {
		return axisC > 0;
	}

	/**
	 * track is used ++
	 */
	public void incUsage() {
		usageCounter++;
	}

	/**
	 * track is used --
	 */
	public void decUsage() {
		usageCounter--;
	}

	/**
	 * is track used by other object (path)
	 *
	 * @return true: yes
	 */
	public boolean isUsed() {
		return usageCounter > 0;
	}

	/**
	 * Getter for property progress.
	 *
	 * @return Value of property progress.
	 */
	public int getProgress() {
		return this.progress;
	}

	/**
	 * Setter for property progress.
	 *
	 * @param progress New value of property progress.
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public static int maxProgress = 20;

	public void setProgressToWork() {
		progress = maxProgress;
	}

	public void paintWorker(Graphics2D g2) {
		if (progress > 0) {
			tp.paintWorker(g2, this, railNorth, railSouth, points, progress);
		}
	}

	public boolean build() {
		if (progress > 0) {
			--progress;
		}
		return progress == 0;
	}
}
