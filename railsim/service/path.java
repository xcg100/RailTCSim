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

import java.util.*;

import org.railsim.dataCollector;
import org.railsim.service.trackObjects.*;

/**
 *
 * @author js
 *
 * Name
 * Tracks
 * Track:Weichenrichtung
 * Signal:Signalbild
 * Route:Priority:Mutex
 *
 */
public class path implements Comparable, Cloneable {

	static int fcnt = 0;
	String name = "";
	pathableObject parent;
	int usageCounter = 0;
	int sstate = 1;
	boolean automatic = false;
	boolean pathFailure = false;
	boolean enabled = true;
	LinkedList<pathroute> routes = new LinkedList<>();
	LinkedList<pathtrack> tracks = new LinkedList<>();
	/*
	 * Editor Parameter
	 */
	public LinkedList<Object> searchpath = null;

	/**
	 * Creates a new instance of path
	 *
	 * @param _parent pathableObject
	 */
	public path(pathableObject _parent) {
		this(_parent, "f" + fcnt);
		fcnt++;
	}

	/**
	 * Creates a new instance of path
	 *
	 * @param _parent pathableObject
	 * @param n name
	 */
	public path(pathableObject _parent, String n) {
		parent = _parent;
		name = n;
	}

	@Override
	public Object clone() {
		path p = new path(parent, name + " copy");
		p.sstate = sstate;

		for (pathroute pr : routes) {
			p.addRoute((pathroute) pr.clone());
		}
		for (pathtrack pt : tracks) {
			p.addTrack((pathtrack) pt.clone());
		}
		return p;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * set name
	 *
	 * @param n new name
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * get name
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set signal state (>0 for green!)
	 *
	 * @param state state
	 */
	public void setStartState(int state) {
		sstate = state;
	}

	/**
	 * get signal state (>0 for green!)
	 *
	 * @return state state
	 */
	public int getStartState() {
		return sstate;
	}

	public void setAutomatic(boolean a) {
		automatic = a;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	/**
	 * start signal (pathableObject) of path
	 *
	 * @return pathableObject (parent)
	 */
	public pathableObject getSignal() {
		return parent;
	}

	/**
	 * add route that uses path
	 *
	 * @param r pathroute (route+prio+addition data)
	 */
	public void addRoute(pathroute r) {
		if (!routes.contains(r)) {
			routes.add(r);
		}
	}

	/**
	 * remove route for path
	 *
	 * @param r pathroute (route+prio+addition data)
	 */
	public void delRoute(pathroute r) {
		routes.remove(r);
		r.remove();
	}

	/**
	 * get alle defined routes for path
	 *
	 * @return List of pathroute, readonly!
	 */
	public LinkedList<pathroute> getRoutes() {
		java.util.Collections.sort(routes);
		return new LinkedList<>(routes);
	}

	/**
	 * is route defined for path
	 *
	 * @param r route
	 * @return true: defined
	 */
	public boolean hasRoute(String r) {
		if (routes.isEmpty()) {
			return true;
		}
		for (pathroute pr : routes) {
			if (pr.getRoute().getName().compareTo(r) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRoute(route r) {
		if (routes.isEmpty()) {
			return true;
		}
		for (pathroute pr : routes) {
			if (pr.getRoute() == r) {
				return true;
			}
		}
		return false;
	}

	/**
	 * priority route
	 *
	 * @param r route, can be null
	 * @return int: priority, PRIOMAX if route==null
	 */
	public int getPriorityOfRoute(route r) {
		if (routes.isEmpty()) {
			return route.PRIOMIN;
		}
		for (pathroute pr : routes) {
			if (pr.getRoute() == r) {
				return pr.getPriority();
			}
		}
		return route.PRIOMAX;
	}

	/**
	 * is path free for route
	 *
	 * @param r route, can be null
	 * @return true: free
	 */
	public boolean isFree(route r) {
		if (!parent.isEnabled()) {
			return false;
		}

		// Gleise frei? Mutexe nicht gesetzt?

		for (pathroute pr : routes) {
			if (pr.getRoute() == r) {
				for (path mp : pr.getMutex()) {
					if (mp.isSet()) {
						return false;
					}
				}
			}
		}
		for (pathtrack pt : tracks) {
			if (pt.getTrack() != parent.getTrackData().getTrack()) {
				if (!pt.getTrack().isFree()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * set path
	 *
	 * @return true: path set, false: path not set - NOT SPOOLED!
	 */
	public boolean set() {
		if (!parent.isEnabled()) {
			return false;
		}

		for (pathtrack pt : tracks) {
			if (pt.isJunction()) {
				pt.getTrack().setJunction(pt.getJunctionstate());
			} else {
				pt.getTrack().setJunction(0);
			}
			if (pt.getTrack() != parent.getTrackData().getTrack()) {
				pt.getTrack().setReserve(this);
			}
		}

		// Signal, Vorsignale auf Weg
		LinkedList<Object> way = null;
		try {
			way = track.getListToNextStopObject(parent, track.GLTN_SIGNAL);
		} catch (IllegalArgumentException ex) {
			dataCollector.collector.gotException(ex);
		}
		if (way != null) {
			pathableObject destsignal = null;
			if (way.getLast() instanceof pathableObject) {
				destsignal = (pathableObject) way.getLast();
				parent.connectToSignal(destsignal);
				int ecode = 0;
				for (Object o : way) {
					if (o instanceof tracksearchinfo) {
						ecode = ((tracksearchinfo) o).code;
						if (ecode == tracksearchinfo.CODE_JUNCTIONWRONG) {
							break;
						} else if (ecode == tracksearchinfo.CODE_BUILDING) {
							break;
						}
					} else if (o instanceof presignalObject) {
						presignalObject po = (presignalObject) o;
						po.connectToSignal(destsignal);
					}
				}
			}
		}
		parent.setState(sstate);

		return true;
	}

	public boolean unset() {
		System.out.println("1");
		int i = 0;
		for (pathtrack pt : tracks) {
			if (i > 0 && pt.getTrack().getReserved() != this) {
				return false;
			}
			if (i > 0 && pt.getTrack().isTrainOn()) {
				return false;
			}
			i++;
		}

		// Signal rot, disconnect presignals
		parent.setState(0);

		LinkedList<Object> way = null;
		try {
			way = track.getListToNextStopObject(parent, track.GLTN_SIGNAL);
		} catch (IllegalArgumentException ex) {
			dataCollector.collector.gotException(ex);
		}
		if (way != null) {
			if (way.getLast() instanceof pathableObject) {
				parent.connectToSignal(null);
				int ecode = 0;
				for (Object o : way) {
					if (o instanceof tracksearchinfo) {
						ecode = ((tracksearchinfo) o).code;
						if (ecode == tracksearchinfo.CODE_JUNCTIONWRONG) {
							break;
						} else if (ecode == tracksearchinfo.CODE_BUILDING) {
							break;
						}
					} else if (o instanceof presignalObject) {
						presignalObject po = (presignalObject) o;
						po.connectToSignal(null);
					}
				}
			}
		}
		for (pathtrack pt : tracks) {
			pt.getTrack().unReserve(this);
		}
		return true;
	}

	public boolean isSet() {
		// check all own tracks if set by this path
		for (pathtrack pt : tracks) {
			if (pt.getTrack().getReserved() == this) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof path) {
			path p = (path) o;
			return name.compareToIgnoreCase(p.name);
		} else {
			return name.compareToIgnoreCase(o.toString());
		}
	}

	/**
	 * remove this object and all entries and references in routes, etc.
	 */
	public void remove() {
		unset();
		for (pathroute pr : routes) {
			pr.remove();
		}
		routes.clear();
		for (pathtrack pt : tracks) {
			pt.remove();
		}
		tracks.clear();
	}

	public void clearTracks() {
		for (pathtrack pt : tracks) {
			pt.remove();
		}
		tracks.clear();
	}

	/**
	 * path is used ++
	 */
	public void incUsage() {
		usageCounter++;
	}

	/**
	 * path is used --
	 */
	public void decUsage() {
		usageCounter--;
	}

	/**
	 * is path used by other object (mutex)
	 *
	 * @return true: yes
	 */
	public boolean isUsed() {
		return usageCounter > 0;
	}

	public java.util.List<pathtrack> getTracks() {
		return tracks;
	}
	/* @depecated */

	public void setTracks(java.util.List<pathtrack> newt) {
		// TODO: not used
	}

	/**
	 *
	 * @param t
	 */
	public void addTrack(track t) {
		tracks.add(new pathtrack(t));
	}

	/**
	 *
	 * @param t
	 * @param j
	 */
	public void addTrack(track t, int j) {
		tracks.add(new pathtrack(t, j));
	}

	public void addTrack(pathtrack t) {
		tracks.add(t);
	}

	public void setFailure(boolean f) {
		pathFailure = f;
	}

	public boolean isFailure() {
		return pathFailure;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean e) {
		enabled = e;
	}
}
