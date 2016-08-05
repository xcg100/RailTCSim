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

/**
 * route for path plus additional data like priority
 *
 * @author js
 */
public class pathtrack implements Cloneable {

	private track t = null;
	private int junctionstate = -1;

	public pathtrack(track _t) {
		t = _t;
		if (t != null) {
			t.incUsage();
		}
	}

	public pathtrack(track _t, int j) {
		this(_t);
		junctionstate = j;
	}

	@Override
	public Object clone() {
		pathtrack p = new pathtrack(t, junctionstate);
		return p;
	}

	/**
	 * set the track
	 *
	 * @param _t new track
	 */
	public void setTrack(track _t) {
		if (t != null) {
			t.decUsage();
		}
		t = _t;
		if (t != null) {
			t.incUsage();
		}
	}

	public track getTrack() {
		return t;
	}

	public void setJunctionstate(int _s) {
		junctionstate = _s;
	}

	public int getJunctionstate() {
		return junctionstate;
	}

	public boolean isJunction() {
		return junctionstate >= 0;
	}

	public boolean setJunction() {
		if (junctionstate >= 0 && t.isFree()) {
			t.setJunction(junctionstate);
			return true;
		}
		return false;
	}

	/**
	 * remove object and all references
	 */
	public void remove() {
		if (t != null) {
			t.decUsage();
		}
		t = null;
	}
}
