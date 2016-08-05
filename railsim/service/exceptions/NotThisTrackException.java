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
package org.railsim.service.exceptions;

import org.railsim.service.*;

/**
 *
 * @author js
 */
public class NotThisTrackException extends java.lang.Exception {

	/**
	 * Creates a new instance of
	 * <code>PositionNotFoundException</code> without detail message.
	 */
	public NotThisTrackException(track t1, track t2) {
		super("Not This Track Exception: " + t1 + " <-> " + t2);
	}

	/**
	 * Constructs an instance of
	 * <code>PositionNotFoundException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public NotThisTrackException(String msg) {
		super(msg);
	}
}
