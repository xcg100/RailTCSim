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

import org.railsim.dataCollector;
import org.railsim.service.exceptions.ManualPathException;
import org.railsim.service.trackObjects.pathableObject;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class trainCommandExecutor_manual extends trainCommandExecutor {

	volatile private route selectedroute = null;
	volatile private pathableObject lastpo = null;

	/**
	 * Creates a new instance of trainCommandExecutor_manual
	 */
	public trainCommandExecutor_manual(fulltrain t, boolean _isprerunner) {
		super(t, _isprerunner);
	}

	public trainCommandExecutor_manual(fulltrain t, boolean _isprerunner, trainCommandExecutor _other) {
		super(t, _isprerunner, _other);
	}

	@Override
	public boolean nextCommand() {
		return true;
	}

	@Override
	public route getRoute(pathableObject po) throws ManualPathException {
		if (isprerunner && selectedroute == null && lastpo != po) {
			lastpo = po;
			dataCollector.collector.manualTrain(ft, po, this);
		}
		route ret = selectedroute;
		selectedroute = null;
		return ret;
	}

	public void setManualRoute(route r) {
		selectedroute = r;
		lastpo = null;
	}
}
