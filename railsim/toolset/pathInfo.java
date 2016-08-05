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

import org.railsim.service.trackObjects.pathableObject;

/**
 *
 * @author js
 */
public class pathInfo {

	static public final int CODE_SET = 1;
	static public final int CODE_NOTSET = 2;
	static public final int CODE_LONGNOTSET = 3;
	pathableObject p = null;
	String msg = null;
	int code = 0;

	/**
	 * Creates a new instance of pathInfo
	 */
	public pathInfo(pathableObject _p, String _msg, int _code) {
		p = _p;
		msg = _msg;
		code = _code;
	}

	public pathInfo(pathableObject _p, int _code, String _msg) {
		p = _p;
		msg = _msg;
		code = _code;
	}

	/**
	 *
	 * @return Signal, may be null!!
	 */
	public pathableObject getSignal() {
		return p;
	}

	public String getMessage() {
		return msg;
	}

	public int getCode() {
		return code;
	}
}
