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

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.TimeZone;

import org.railsim.dataCollector;
import org.railsim.service.odsHashSet;
import org.railsim.service.trainCommandExecutor;
import org.railsim.service.trackObjects.*;

/**
 *
 * @author js
 */
public class waitMinutes extends trainCommand {

	int delay = 0;
	int greendelay = 20;
	static SimpleDateFormat destTime = new SimpleDateFormat("mm:ss");

	static {
		destTime.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * Creates a new instance of waitMinutes
	 */
	public waitMinutes() {
		super("warten");
	}

	@Override
	public Object clone() {
		return new waitMinutes();
	}

	public void setDelay(int d) {
		delay = d;
	}

	public int getDelay() {
		return delay;
	}

	public void setGreenDelay(int d) {
		greendelay = d;
	}

	public int getGreenDelay() {
		return greendelay;
	}

	@Override
	public String toString() {
		if (delay != 0) {
			return "warte " + destTime.format(delay * 1000) + " Minuten";
		} else {
			return name;
		}
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("stop", "Aufenthalt", delay));
		h.add(new objectDataStorage("greenwait", "Grünwartezeit", greendelay));
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
			if (key.getKey().compareTo("stop") == 0) {
				delay = key.getIntValue();
			} else if (key.getKey().compareTo("greenwait") == 0) {
				greendelay = key.getIntValue();
			}
		}
		super.setData(hm);
	}

	@Override
	public void init(trainCommandExecutor tce) {
		tce.storeLocalValue("delay", dataCollector.collector.getTime() + "");
		if (!tce.isPrerunner()) {
			tce.storeGlobalValue("delay", dataCollector.collector.getTime() + "");
		}
	}

	@Override
	public boolean finished(trainCommandExecutor tce) {
		long d;
		long d2 = delay * 1000;
		long d3 = dataCollector.collector.getTime();
		if (!tce.isPrerunner()) {
			d = Long.parseLong(tce.getLocalValue("delay"));
			//System.out.println(tce.getTrain().getName()+"::: W:"+(d+d2)+"<"+d3+" R:"+!tce.getTrain().isNextSignalRed());
			// TODO: das klappt nicht wenn nach waitMinutes kein Fahrbefehl kommt!!!
			return (d + d2 < d3 && !tce.getTrain().isNextSignalRed());
		} else {
			if (tce.getGlobalValue("delay") != null) {
				Long ld = Long.parseLong(tce.getGlobalValue("delay"));
				d = ld;
				d2 -= greendelay * 1000;
				//System.out.println(tce.getTrain().getName()+"<-- W:"+(d+d2)+"<"+d3);
				if (d + d2 < d3) {
					tce.deleteGlobalValue("delay");
					return true;
				}
			}
			return false;
		}
	}
}