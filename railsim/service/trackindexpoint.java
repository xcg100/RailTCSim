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

/**
 *
 * @author js
 */
public class trackindexpoint extends trackpoint {

	protected int index;

	/**
	 * Creates a new instance of trackindexpoint
	 */
	public trackindexpoint(track _t, int _index) {
		super(_t, _t.getXY(_index));
		index = _index;
	}

	public trackindexpoint(track _t, int _index, point _p) {
		super(_t, _p);
		index = _index;
	}

	@Override
	public int getX() {
		return t.getXY(index).getX();
	}

	@Override
	public int getY() {
		return t.getXY(index).getY();
	}

	@Override
	public boolean isPoint(int _x, int _y) {
		return t.getXY(index).isPoint(_x, _y);
	}

	@Override
	public boolean isPoint(point p) {
		return t.getXY(index).isPoint(p);
	}

	public int getIndex() {
		return index;
	}
}
