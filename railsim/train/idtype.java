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
public class idtype implements Comparable {

	public String id = "";

	/**
	 * Creates a new instance of idtype
	 */
	public idtype() {
	}

	public idtype(String i) {
		id = i;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof idtype) {
			return equals(((idtype) o).id);
		} else if (o instanceof String) {
			return id.equals((String) o);
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof idtype) {
			return compareTo(((idtype) o).id);
		} else if (o instanceof String) {
			return id.compareTo((String) o);
		}
		return id.compareTo(o.toString());
	}

	public String getID() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}
}
