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
package org.railsim.service.trackObjects;

import java.util.*;

import org.railsim.dataCollector;
import org.railsim.event.ListenerList;
import org.railsim.service.odsHashSet;
import org.railsim.service.path;
import org.railsim.service.pathRequest;
import org.railsim.service.route;
import org.railsim.service.exceptions.ManualPathException;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public abstract class pathableObject extends stopObject {

	ListenerList presignalListeners = new ListenerList();
	LinkedList<path> paths = new LinkedList<>();
	private int cstate;
	private boolean enabled = false;

	/**
	 * Creates a new instance of pathableObject
	 */
	protected pathableObject(String n) {
		super(n);
	}

	protected pathableObject(String n, String r) {
		super(n, r);
	}

	/**
	 *
	 * @param n
	 * @param width
	 * @param height
	 */
	protected pathableObject(String n, String r, int width, int height) {
		super(n, r, width, height);
	}

	protected void connectPresignal(presignalObject pso) {
		if (pso != null) {
			presignalListeners.addListener(pso);
		}
	}

	protected void unconnectPresignal(presignalObject pso) {
		if (pso != null) {
			presignalListeners.removeListener(pso);
		}
	}

	/**
	 * set path for train (route)
	 *
	 * @param ft train
	 * @return false: no route found for train
	 */
	public boolean setPath(fulltrain ft) {
		route r = null;
		pathRequest pr = null;
		try {
			r = ft.getPreExecutor().getRoute(this);
			if (r == null) {
				return false;
			}
			pr = new pathRequest(ft, r);
			for (path p : paths) {
				if (p.hasRoute(r)) {
					pr.add(p);
				}
			}
		} catch (ManualPathException ex) {
			pr = new pathRequest(ft, null);
			pr.add(ex.getPath());
		}

		/* Zug-Ziel auslesen (Route) und passenden path(s) suchen
		 * alle passenden paths in eine Arbeitsqueue stellen
		 * return true wenn mindestens ein Pfad gefunden
		 */
		if (pr.isEmpty()) {
			return false;
		} else {
			dataCollector.collector.thepainter.addPath2Check(pr);
		}
		return true;
	}

	public boolean unsetPath() {
		for (path p : paths) {
			if (p.unset()) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param s state - 0 is red, any other is green for signal dependant lights
	 */
	public void setState(int s) {
		cstate = s;
		presignalListeners.fireEvent(new SignalStateEvent(this, s));
	}

	public int getState() {
		return cstate;
	}

	/**
	 * GUI Text for states, 0 is always RED!
	 *
	 * @return TreeMap of Strings and State-ID
	 */
	public abstract ArrayList<stateText> getStateText();

	public void setEnable(boolean b) {
		enabled = b;
		if (enabled) {
			pathRequest opr = dataCollector.collector.thepainter.pollPath2Check(this);
			if (opr != null) {
				setPath(opr.ft);
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public int isStopped(fulltrain tf) {
		return (cstate == 0) ? STOPPED_FULL : STOPPED_NO;
	}

	@Override
	public boolean canGreen() {
		return true;
	}

	@Override
	public boolean updateTrain(fulltrain ft, int step) {
		if (step == trackObject.TRAINSTEP_END) {
			setState(0);
		}
		return false;
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("cstate", "-aktuelles Signalbild", cstate));
		h.add(new objectDataStorage("enabled", "-Automatik Aktiv", enabled));
		return h;
	}

	@Override
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("cstate") == 0) {
				setState(key.getIntValue());
			} else if (key.getKey().compareTo("enabled") == 0) {
				setEnable(key.getBoolValue());
			}
		}
		super.setData(hm);
	}

	@Override
	public void connectToSignal(pathableObject p) {
	}

	public TreeSet<path> getAllPaths() {
		return new TreeSet<>(paths);
	}

	public int getPathCount() {
		return paths.size();
	}

	public boolean addPath(path p) {
		paths.add(p);
		dataCollector.collector.pathsChanged();
		return true;
	}

	public boolean delPath(path p) {
		paths.remove(p);
		p.remove();
		dataCollector.collector.pathsChanged();
		return true;
	}

	@Override
	public void remove() {
		for (path p : paths) {
			p.remove();
		}
		paths.clear();
		dataCollector.collector.pathsChanged();
		super.remove();
	}

	@Override
	public int getRequirements() {
		return 0;
	}
}
