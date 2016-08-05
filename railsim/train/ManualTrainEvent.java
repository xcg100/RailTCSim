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

import org.railsim.event.AbstractEvent;
import org.railsim.service.route;
import org.railsim.service.trainCommandExecutor_manual;
import org.railsim.service.trackObjects.pathableObject;

/**
 * Used to send events from editor to editor panels.
 *
 * @author js
 */
public class ManualTrainEvent extends AbstractEvent {

	private fulltrain ft = null;
	private pathableObject po = null;
	private route r = null;
	private trainCommandExecutor_manual tce = null;
	private boolean rtype = false;

	/**
	 * Creates a new instance of ManualTrainEvent
	 */
	public ManualTrainEvent(fulltrain _ft, pathableObject _po, trainCommandExecutor_manual _tce) {
		super("");
		ft = _ft;
		po = _po;
		tce = _tce;
		rtype = false;
	}

	public ManualTrainEvent(fulltrain _ft, route _r) {
		super("");
		ft = _ft;
		r = _r;
		rtype = true;
	}

	public pathableObject getSignal() {
		return po;
	}

	public fulltrain getTrain() {
		return ft;
	}

	public route getRoute() {
		return r;
	}

	public trainCommandExecutor_manual getTCE() {
		return tce;
	}

	public boolean isRouteMsg() {
		return rtype;
	}
}
