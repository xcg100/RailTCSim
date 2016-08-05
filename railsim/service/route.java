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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.railsim.dataCollector;
import org.railsim.service.exceptions.DuplicateEntryException;
import org.railsim.service.trainCommands.trainCommand;

/**
 *
 * @author js
 */
public class route {

	static public TreeMap<String, route> allroutes = new TreeMap<>();

	public static route findOrCreateRoute(String n) {
		route r = null;
		synchronized (allroutes) {
			r = allroutes.get(n);
			if (r == null) {
				try {
					r = new route(n);
				} catch (DuplicateEntryException ex) {
				}
			}
		}
		return r;
	}

	public static route findRoute(String n) {
		synchronized (allroutes) {
			return allroutes.get(n);
		}
	}
	final public static int PRIOMIN = 1;
	final public static int PRIOMAX = 9;
	private String name = null;
	private int usageCounter = 0;
	private boolean enabled = true;
	//public CopyOnWriteArrayList<trainCommand> commands=new CopyOnWriteArrayList<trainCommand>();
	public ArrayList<trainCommand> commands = new ArrayList<>();

	/**
	 * Creates a new instance of route
	 */
	public route(String n) throws DuplicateEntryException {
		synchronized (allroutes) {
			if (allroutes.containsKey(n)) {
				throw new DuplicateEntryException(name);
			}
			name = n;
			allroutes.put(name, this);
		}
		dataCollector.collector.routesChanged();
	}

	public String getName() {
		return name;
	}

	public void setName(String n) throws DuplicateEntryException {
		synchronized (allroutes) {
			if (allroutes.containsKey(n)) {
				if (allroutes.get(n) != this) {
					throw new DuplicateEntryException(name);
				}
			}
			allroutes.remove(name);
			name = n;
			allroutes.put(name, this);
		}
		dataCollector.collector.routesChanged();
	}

	public void remove() {
		synchronized (allroutes) {
			allroutes.remove(name);
		}
		for (trainCommand tc : commands) {
			tc.remove();
		}
		commands.clear();
		dataCollector.collector.routesChanged();
	}

	@Override
	public String toString() {
		return name;
	}

	public void incUsage() {
		usageCounter++;
	}

	public void decUsage() {
		usageCounter--;
	}

	public boolean isUsed() {
		return usageCounter > 0;
	}

	public List<trainCommand> getNewCommandContainer() {
		return new ArrayList<>();
	}

	public void setNewCommandContainer(List<trainCommand> c) {
		try {
			commands = (ArrayList<trainCommand>) c;
		} catch (ClassCastException e) {
			dataCollector.collector.gotException(e);
		}
	}

	public void setEnabled(boolean s) {
		enabled = s;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
