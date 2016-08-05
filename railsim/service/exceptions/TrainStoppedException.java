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
package org.railsim.service.exceptions;

import org.railsim.service.*;
import org.railsim.service.trackObjects.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class TrainStoppedException extends java.lang.Exception {

	stopObject so;
	fulltrain ft;
	track tr;
	int distance = 0;

	/**
	 * Creates a new instance of
	 * <code>TrainStoppedException</code> without detail message.
	 */
	public TrainStoppedException(fulltrain t1, track t2, stopObject s) {
		super("Train Stopped Exception: " + t1 + " <-> " + t2);
		ft = t1;
		tr = t2;
		so = s;
	}

	public TrainStoppedException(TrainStoppedException tse, int d) {
		super(tse);
		ft = tse.ft;
		tr = tse.tr;
		so = tse.so;
		distance = d;
	}

	public int getDistance() {
		return distance;
	}

	public stopObject getStopObject() {
		return so;
	}
}
