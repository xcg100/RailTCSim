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

import org.railsim.train.fulltrain;

/**
 * track object with train stop options, like signals
 *
 * @author js
 */
public abstract class stopObject extends presignalObject {

	protected stopObject(String n) {
		super(n);
	}

	protected stopObject(String n, String r) {
		super(n, r);
	}

	protected stopObject(String n, String r, int width, int height) {
		super(n, r, width, height);
	}

	public boolean isStoppedT(fulltrain tf, int step) {
		int b = 0;
		if ((step & TRAINSTEP_PRERUNNER) != 0) {
			b = shouldStopped(tf);
			if (b > 0) {
				tf.setTemporaryvMax(1, this, step);
				/*if (this instanceof destinationObject)
				 System.out.println("Bremsen");*/
			} else if (b == STOPPED_NO) {
				tf.setTemporaryvMax(fulltrain.NOTEMPVMAX, this, step);
				/*if (this instanceof destinationObject)
				 System.out.println("nicht mehr bremsen");*/
			}
			return b == STOPPED_FULL || b == STOPPED_ONLYPRERUNNER;
		} else {
			b = isStopped(tf);
		}
		return b > STOPPED_NO;
	}
	final static public int STOPPED_NO = 0;
	final static public int STOPPED_VALUENOTCHANGE = -1;
	final static public int STOPPED_FULL = 1;
	final static public int STOPPED_ONLYTRAIN = 2;
	final static public int STOPPED_ONLYPRERUNNER = -2;

	/**
	 * Called if train reached object with correct direction
	 *
	 * @param tf train
	 * @return return true if train must stop
	 */
	abstract public int isStopped(fulltrain tf);

	public int shouldStopped(fulltrain tf) {
		return isStopped(tf);
	}

	abstract public boolean canGreen();

	@Override
	public int getRequirements() {
		return REQUIREMENT_NOBUILD | REQUIREMENT_NOPATH;
	}
}
