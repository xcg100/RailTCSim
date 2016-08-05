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
public class point {

	private int x;
	private int y;

	/**
	 * Creates a new instance of point
	 */
	public point(int _x, int _y) {
		x = _x;
		y = _y;
	}

	/**
	 *
	 * @param p
	 */
	public point(point p) {
		x = p.getX();
		y = p.getY();
	}

	/**
	 *
	 * @param _x
	 * @param _y
	 */
	protected void setXY(int _x, int _y) {
		x = _x;
		y = _y;
	}

	/**
	 *
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 *
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 *
	 * @param _x
	 * @param _y
	 * @return
	 */
	public boolean isPoint(int _x, int _y) {
		return x == _x && y == _y;
	}

	/**
	 *
	 * @param p
	 * @return
	 */
	public boolean isPoint(point p) {
		return x == p.x && y == p.y;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return x + "/" + y;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public int hashCode() {
		return x * 1000 + y;
	}

	/**
	 *
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		try {
			return isPoint((point) o);
		} catch (ClassCastException e) {
			return false;
		}
	}

	/**
	 * distance between 2 points
	 *
	 * @param p other point
	 * @return distance
	 */
	public double distance(point p) {
		int px = p.x - x;
		int py = p.y - y;
		return Math.sqrt(py * py + px * px);
	}

	/**
	 * distance between point and 0/0 (length)
	 *
	 * @return distance
	 */
	public double length() {
		return Math.sqrt(y * y + x * x);
	}

	/**
	 * angle between 2 points ???
	 *
	 * @param p other point
	 * @return angel
	 */
	public double arc00(point p) {
		double h = (x * p.x + y * p.y) / (Math.sqrt(x * x + y * y) * Math.sqrt(p.x * p.x + p.y * p.y));
		return Math.acos(h);
	}

	/**
	 * angle between 2 points, setting this point as 0/0
	 *
	 * @param p other point
	 * @return angel
	 */
	public double arc(point p) {
		int px = p.x - x;
		int py = p.y - y;
		return Math.atan2(py, px);
	}

	/**
	 * point between this and 2nd point at half distance
	 *
	 * @param p other point
	 * @return new point
	 */
	public point halfWay(point p) {
		int px = p.x - x;
		int py = p.y - y;
		return new point(px / 2 + x, py / 2 + y);
	}

	/**
	 * move point by dx/dy pixels
	 *
	 * @param dx
	 * @param dy
	 */
	public void moveBy(int dx, int dy) {
		x += dx;
		y += dy;
	}
}
