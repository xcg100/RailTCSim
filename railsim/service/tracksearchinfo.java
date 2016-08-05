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
package org.railsim.service;

/**
 *
 * @author js
 */
public class tracksearchinfo {

	static final public int CODE_OK = 0;
	static final public int CODE_JUNCTIONWRONG = 1;
	static final public int CODE_BUILDING = 2;
	static final public int CODE_ENDOFTRACK = 3;
	public int code = 0;

	public tracksearchinfo(int c) {
		code = c;
	}
}
