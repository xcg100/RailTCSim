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

import org.railsim.service.odsHashSet;
import org.railsim.service.statics;
import org.railsim.service.trainCommandExecutor;
import org.railsim.service.trackObjects.objectDataStorage;

/**
 *
 * @author js
 */
public abstract class trainCommand implements Cloneable {

	String name = "";

	/**
	 * Creates a new instance of trainCommand
	 */
	protected trainCommand(String n) {
		name = n;
	}

	@Override
	public abstract Object clone();

	@Override
	public String toString() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return getClass().getSimpleName();
	}

	static public trainCommand load(String n) {
		return (trainCommand) statics.loadClass("org.railsim.service.trainCommands", n);
	}

	public odsHashSet getData() {
		odsHashSet h = new odsHashSet();
		return h;
	}

	/**
	 * set parameters of trackObject
	 *
	 * @param value todHashSet with parameters, missing parameters are not changed
	 * @abstract
	 */
	public void setData(odsHashSet value) {
	}

	/**
	 * set parameter of trackObject
	 *
	 * @param key key of parameter
	 * @param value value of parameter
	 */
	public void setData(String key, String value) {
		odsHashSet h = getData();
		objectDataStorage tod = h.get(key);
		if (tod != null) {
			tod.setValue(value);
			h.reduceTo(tod);
			setData(h);
		}
	}

	public void remove() {
	}

	public void init(trainCommandExecutor tce) {
	}

	abstract public boolean finished(trainCommandExecutor tce);
}
