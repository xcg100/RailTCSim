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

import org.railsim.event.AbstractEvent;

/**
 *
 * @author js
 */
public class statusEvent extends AbstractEvent {

	boolean unset = false;

	/**
	 * Creates a new instance of statusEvent
	 */
	public statusEvent(boolean set, String msg) {
		super(msg);
		unset = !set;
	}

	public statusEvent(String msg) {
		super(msg);
		unset = false;
	}

	public boolean isUnset() {
		return unset;
	}

	public String getString() {
		return (String) getSource();
	}
}
