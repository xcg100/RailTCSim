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

import java.util.LinkedList;

import org.railsim.*;
import org.railsim.service.trackObjects.pathableObject;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class pathRequest extends LinkedList<path> {

	public fulltrain ft;
	public route r;
	public int priocounter = 1;

	/**
	 * Creates a new instance of pathRequest
	 */
	public pathRequest(fulltrain _t, route _r) {
		ft = _t;
		r = _r;
	}

	public pathRequest(String _t, String _r) {
		if (_r != null) {
			r = route.allroutes.get(_r);
		} else {
			r = null;
		}
		dataCollector.collector.thepainter.trains.readLock();
		for (fulltrain f : dataCollector.collector.thepainter.trains) {
			if (f.getName().compareTo(_t) == 0) {
				ft = f;
				break;
			}
		}
		dataCollector.collector.thepainter.trains.readUnlock();
	}

	public pathableObject getSignal() {
		if (this.isEmpty()) {
			return null;
		}
		return this.getFirst().getSignal();
	}
}
