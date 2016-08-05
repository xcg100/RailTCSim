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
package org.railsim.toolset;

import java.text.Collator;
import java.util.*;

/**
 *
 * @author js
 */
public abstract class packageElementsLister extends TreeMap<String, String> {

	/**
	 * Creates a new instance of packageElementsLister
	 */
	public packageElementsLister(String[] v) {
		super(Collator.getInstance());
		add(v);
	}

	public void add(String[] v) {
		if (v != null) {
			int i = 0;
			while (i < v.length - 1) {
				String s1 = v[i++];
				String s2 = v[i++];
				put(s1, s2);
			}
		}
	}
}
