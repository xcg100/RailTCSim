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

import java.util.LinkedList;
import java.util.List;

/**
 * route for path plus additional data like priority
 *
 * @author js
 */
public class pathroute implements Comparable, Cloneable {

	private route r = null;
	private int prio = 1;
	private LinkedList<path> mutex = new LinkedList<>();

	/**
	 * compare with other pathroute by priority and route name
	 *
	 * @param o other object
	 * @return
	 */
	@Override
	public int compareTo(Object o) {
		if (o instanceof pathroute) {
			pathroute p = (pathroute) o;
			if (p.prio == prio && r != null && p.r != null) {
				return r.getName().compareToIgnoreCase(p.r.getName());
			}
			return prio - p.prio;
		}
		return 0;
	}

	@Override
	public Object clone() {
		pathroute p = new pathroute();
		p.setRoute(r);
		p.setPriority(prio);
		for (path pa : mutex) {
			p.addMutex(pa);
		}
		return p;
	}

	/**
	 * set the route
	 *
	 * @param _r new route
	 */
	public void setRoute(route _r) {
		if (r != null) {
			r.decUsage();
		}
		r = _r;
		if (r != null) {
			r.incUsage();
		}
	}

	/**
	 * get route
	 *
	 * @return route
	 */
	public route getRoute() {
		return r;
	}

	/**
	 * set priority
	 *
	 * @param _p new priority
	 */
	public void setPriority(int _p) {
		prio = _p;
	}

	/**
	 * get priority
	 *
	 * @return priority
	 */
	public int getPriority() {
		return prio;
	}

	/**
	 * add path as mutex
	 *
	 * @param _p path
	 */
	public void addMutex(path _p) {
		_p.incUsage();
		mutex.add(_p);
	}

	/**
	 * remove path from mutex list
	 *
	 * @param _p path
	 */
	public void delMutex(path _p) {
		_p.decUsage();
		mutex.remove(_p);
	}

	/**
	 * remove path from mutex
	 *
	 * @param _p index of path in mutex list
	 */
	public void delMutex(int _p) {
		delMutex(mutex.get(_p));
	}

	/**
	 * get mutex list
	 *
	 * @return mutex list
	 */
	public List<path> getMutex() {
		return (List<path>) java.util.Collections.unmodifiableList(mutex);
	}

	/**
	 * set mutex list
	 *
	 * @param m mutex list
	 */
	public void setMutex(List<path> m) {
		for (path p : mutex) {
			p.decUsage();
		}
		mutex.clear();
		for (path p : m) {
			addMutex(p);
		}
	}

	/**
	 * remove object and all references
	 */
	public void remove() {
		if (r != null) {
			r.decUsage();
		}
		r = null;
		for (path p : mutex) {
			p.decUsage();
		}
		mutex.clear();
	}
}
