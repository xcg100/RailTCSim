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
package org.railsim.service;

import org.railsim.service.trackObjects.trackObject;

/**
 *
 * @author js
 */
public class trackobjectpoint extends trackindexpoint {

	boolean forward = false;  // ==east
	trackObject to = null;

	/**
	 * Creates a new instance of trackobjectpoint
	 */
	public trackobjectpoint(track _t, int _index, boolean _forward, trackObject _to) {
		super(_t, _index);
		forward = _forward;
		to = _to;
	}

	public trackobjectpoint(track _t, int _index, point _p, boolean _forward, trackObject _to) {
		super(_t, _index, _p);
		forward = _forward;
		to = _to;
	}

	public boolean getForward() {
		return forward;
	}

	public boolean isWest() {
		return !forward;
	}

	public boolean isEast() {
		return forward;
	}

	public trackObject getTrackObject() {
		return to;
	}
}
