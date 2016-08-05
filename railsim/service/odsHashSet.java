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

import java.util.*;

import org.railsim.service.trackObjects.objectDataStorage;

/**
 *
 * @author js
 */
public class odsHashSet extends HashMap<String, objectDataStorage> implements Iterable {

	public void add(objectDataStorage d) {
		put(d.getKey(), d);
	}

	public objectDataStorage get(objectDataStorage k) {
		return get(k.getKey());
	}

	@Override
	public Iterator<objectDataStorage> iterator() {
		return values().iterator();
	}

	public void reduceTo(String key) {
		objectDataStorage tod = get(key);
		clear();
		add(tod);
	}

	public void reduceTo(objectDataStorage k) {
		reduceTo(k.getKey());
		add(k);
	}
}
