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

import org.railsim.service.*;
import org.railsim.service.trackObjects.*;

/**
 *
 * @author js
 */
public class nextRoute extends trainCommand {

	route r = null;

	/**
	 * Creates a new instance of nextRoute
	 */
	public nextRoute() {
		super("nächste Route");
	}

	@Override
	public Object clone() {
		return new nextRoute();
	}

	public void setRoute(route _r) {
		if (r != _r) {
			_r.incUsage();
			if (r != null) {
				r.decUsage();
			}
		}
		r = _r;
	}

	public route getRoute() {
		return r;
	}

	@Override
	public String toString() {
		if (r != null) {
			return name + " " + r.getName();
		} else {
			return name;
		}
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("route", "Route", r != null ? r.getName() : ""));
		return h;
	}

	@Override
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("route") == 0) {
				String h = key.getValue();
				setRoute(route.findOrCreateRoute(h));
			}
		}
		super.setData(hm);
	}

	@Override
	public void remove() {
		if (r != null) {
			r.decUsage();
		}
	}

	@Override
	public boolean finished(trainCommandExecutor tce) {
		return true;
	}
}
