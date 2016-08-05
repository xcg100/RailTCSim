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

import org.railsim.service.trainCommands.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public abstract class destinationObject extends stopObject {

	static int sc = 0;

	/**
	 * Creates a new instance of destinationObject
	 */
	protected destinationObject() {
		super("Ziel " + (sc++));
	}

	protected destinationObject(String n) {
		super(n);
	}

	protected destinationObject(String n, String r) {
		super(n, r);
	}

	protected destinationObject(String n, String r, int width, int height) {
		super(n, r, width, height);
	}

	@Override
	public int isStopped(fulltrain tf) {
		trainCommand c = tf.getMainExecutor().getCurrentCommand();
		if (c != null && c instanceof gotoDestination) {
			gotoDestination gd = (gotoDestination) c;
			if (gd.isDestination(tf.getMainExecutor(), this)) {
				return STOPPED_ONLYTRAIN;
			}
		}
		return 0;
	}

	@Override
	public int shouldStopped(fulltrain tf) {
		trainCommand c = tf.getPreExecutor().getCurrentCommand();
		if (c != null && c instanceof gotoDestination) {
			gotoDestination gd = (gotoDestination) c;
			if (gd.isDestination(tf.getPreExecutor(), this)) {
				//System.out.println("Bremsen merken");
				return STOPPED_ONLYTRAIN;
			}
		}
		return STOPPED_VALUENOTCHANGE;
	}

	@Override
	public boolean canGreen() {
		return true;
	}

	@Override
	public void connectToSignal(pathableObject p) {
	}

	@Override
	public boolean updateTrain(fulltrain ft, int step) {
		if ((step & trackObject.TRAINSTEP_START) != 0) {
			trainCommand c = ft.getMainExecutor().getCurrentCommand();
			if (c != null && c instanceof gotoDestination) {
				gotoDestination gd = (gotoDestination) c;
				if (gd.isDestination(ft.getMainExecutor(), this)) {
					ft.setTemporaryvMaxTicks(fulltrain.NOTEMPVMAX);
				}
			}
		}
		return false;
	}
}
