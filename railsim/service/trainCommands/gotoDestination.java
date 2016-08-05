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
package org.railsim.service.trainCommands;

import java.util.Iterator;

import org.railsim.service.odsHashSet;
import org.railsim.service.trainCommandExecutor;
import org.railsim.service.trackObjects.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class gotoDestination extends trainCommand {

	destinationObject destination = null;

	/**
	 * Creates a new instance of gotoDestination
	 */
	public gotoDestination() {
		super("fahre zu");
	}

	@Override
	public Object clone() {
		return new gotoDestination();
	}

	public void setDestination(destinationObject d) {
		destination = d;
	}

	public destinationObject getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		if (destination != null) {
			return name + " " + destination.getRegion() + "/" + destination.getName();
		} else {
			return name;
		}
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("destination", "Ziel", destination != null ? destination.getTemporaryHash() : 0));
		return h;
	}

	/**
	 * set parameters of trackObject
	 *
	 * @param value todHashSet with parameters, missing parameters are not changed
	 * @abstract
	 */
	@Override
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("destination") == 0) {
				int h = key.getIntValue();
				for (trackObject to : trackObject.allto.keySet()) {
					if (to instanceof destinationObject && to.getTemporaryHash() == h) {
						destination = (destinationObject) to;
					}
				}

			}
		}
		super.setData(hm);
	}

	public boolean isDestination(trainCommandExecutor tce, destinationObject d) {
		boolean b = destination == d;
		if (b) {
			tce.storeLocalValue("matched", true + "");
		}
		return b;
	}

	public boolean isDestination(trainCommandExecutor tce, String d) {
		boolean b = destination.getName().equals(d);
		if (b) {
			tce.storeLocalValue("matched", true + "");
		}
		return b;
	}

	@Override
	public boolean finished(trainCommandExecutor tce) {
		String o = tce.getLocalValue("matched");
		if (o != null && Boolean.parseBoolean(o)) {
			tce.getTrain().setTemporaryvMaxTicks(fulltrain.NOTEMPVMAX);
			return true;
		}
		return false;
	}
}