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
package org.railsim.service.trackObjects;

import org.railsim.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public abstract class trainEndObject extends destinationObject {

	static int sc = 0;

	/**
	 * Creates a new instance of trainEndObject
	 */
	public trainEndObject() {
		super("Ausfahrt " + (sc++));
	}

	@Override
	public boolean updateTrain(fulltrain ft, int step) {
		if (step != trackObject.TRAINSTEP_PRERUNNER) {
			// del 1st part in drive direction
			runLater(new runlater(ft) {
				@Override
				public void run() {
					boolean b = ft.removeFirstStock();
					if (b) {
						dataCollector.collector.thepainter.delTrain(ft);
					}
				}
			});
		}
		return false;
	}

	@Override
	public int isStopped(fulltrain tf) {
		return STOPPED_ONLYPRERUNNER;
	}

	@Override
	public int shouldStopped(fulltrain tf) {
		return STOPPED_ONLYPRERUNNER;
	}

	@Override
	public boolean canGreen() {
		return false;
	}

	@Override
	public int getRequirements() {
		return REQUIREMENT_NOBUILD | REQUIREMENT_NOPATH;
	}
}
