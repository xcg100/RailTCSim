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
 * Rotatepoint, point with rotation angle - typically in radians
 *
 * @author js
 */
public class rotatepoint extends point {

	double rot;

	/**
	 * Create rotatepoint
	 *
	 * @param _x X
	 * @param _y Y
	 * @param r angle in radians
	 */
	public rotatepoint(int _x, int _y, double r) {
		super(_x, _y);
		rot = r;
	}

	/**
	 * Create rotatepoint
	 *
	 * @param p point
	 * @param r angle in radians
	 */
	public rotatepoint(point p, double r) {
		super(p);
		rot = r;
	}

	/**
	 * Copy rotatepoint
	 *
	 * @param _t Copy
	 */
	public rotatepoint(rotatepoint _t) {
		super(_t);
		rot = _t.rot;
	}

	/**
	 * Angle
	 *
	 * @return angle in radians!
	 */
	public double getRotate() {
		return rot;
	}
}
