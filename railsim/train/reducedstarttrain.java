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
package org.railsim.train;

/**
 *
 * @author js
 */
public class reducedstarttrain implements Comparable {

	protected String name = "";

	/**
	 * Creates a new instance of reducedstarttrain
	 */
	public reducedstarttrain(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (name == null && o == null) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (o instanceof starttrain) {
			starttrain st = (starttrain) o;

			if (name == null && st.name == null) {
				return true;
			}

			if (name == null) {
				return false;
			}

			return name.equals(st.name);
		} else {
			if (name == null && o == null) {
				return true;
			}

			if (name == null) {
				return false;
			}

			return name.equals(o);
		}
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof starttrain) {
			starttrain st = (starttrain) o;
			int r = 0;
			r = name.compareTo(st.name);
			return r;
		} else {
			return name.compareTo((String) o);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
