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
package org.railsim.service.trackObjects;

import org.railsim.service.statics;
import org.railsim.toolset.packageElementsLister;

/**
 *
 * @author js
 */
public class toList extends packageElementsLister {

	static String[] v = {"hvsignal1", "hvsignal2", "hvpresignal1", "testSignal", "speedSign1", "speedSign2", "destinationPoint", "trainStartPoint", "trainExitPoint"};

	/**
	 * Creates a new instance of additionalsList
	 */
	public toList() {
		super(null);

		for (int i = 0; i < v.length; ++i) {
			try {
				trackObject to = (trackObject) statics.loadClass(statics.TRACKOBJECTSPATH, v[i]);
				String n = to.getGUIObjectName();
				String[] v2 = new String[2];
				v2[0] = n;
				v2[1] = v[i];
				add(v2);
			} catch (ClassCastException e) {
			}
		}
	}

	static public trackObject load(String n) {
		trackObject to = (trackObject) statics.loadClass(statics.TRACKOBJECTSPATH, n);
		return to;
	}

	public int getIndex(String n) throws java.util.NoSuchElementException {
		int i = 0;
		for (String s : values()) {
			if (s.compareTo(n) == 0) {
				return i;
			}
			++i;
		}
		throw new java.util.NoSuchElementException(n);
	}
}
