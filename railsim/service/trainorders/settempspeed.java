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
package org.railsim.service.trainorders;

import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class settempspeed extends trainorder {

	public settempspeed() {
	}

	/**
	 * Creates a new instance of setspeed
	 */
	public settempspeed(int _v, long _pos) {
		super(_v, _pos);
	}

	@Override
	public boolean doit() {
		if (ft.getTemporaryvMaxTicks() == fulltrain.NOTEMPVMAX || v < ft.getTemporaryvMaxTicks()) {
			ft.setTemporaryvMaxTicks(v);
		}
		return ft.getTemporaryvMaxTicks() == fulltrain.NOTEMPVMAX;
	}
}
