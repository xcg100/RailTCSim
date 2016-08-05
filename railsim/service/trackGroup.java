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

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

import org.railsim.dataCollector;

/**
 * collection of tracks
 *
 * @author js
 */
public class trackGroup extends HashSet<track> {

	private rectangle bounds = null;
	private rectangle clickbounds = null;

	/**
	 * Creates a new instance of trackGround
	 */
	public trackGroup() {
		dataCollector.collector.thepainter.trackgroups.writeLock();
		dataCollector.collector.thepainter.trackgroups.add(this);
		dataCollector.collector.thepainter.trackgroups.writeUnlock();
	}

	/**
	 *
	 * @param h hash for saving
	 */
	public trackGroup(int h) {
		this();
		setTemporaryHash(h);
	}

	/**
	 * remove group
	 */
	public void remove() {
		dataCollector.collector.thepainter.trackgroups.writeLock();
		dataCollector.collector.thepainter.trackgroups.remove(this);
		dataCollector.collector.thepainter.trackgroups.writeUnlock();
		while (size() > 0) {
			track t = this.iterator().next();
			remove(t);
		}
	}

	private void updateBounds() {
		Rectangle2D r = null;
		Rectangle2D cr = null;
		for (track t : this) {
			if (r == null) {
				r = t.getBounds();
			} else {
				r = r.createUnion(t.getBounds());
			}
			if (cr == null) {
				cr = t.getClickBounds();
			} else {
				cr = cr.createUnion(t.getClickBounds());
			}
		}
		if (r != null) {
			bounds = new rectangle((Rectangle) r);
		}
		if (cr != null) {
			clickbounds = new rectangle((Rectangle) cr);
		}
	}
	private int temphash = 0;

	/**
	 *
	 * @param h
	 */
	final public void setTemporaryHash(int h) {
		temphash = h;
	}

	/**
	 *
	 * @return
	 */
	final public int getTemporaryHash() {
		return temphash;
	}

	/**
	 * add track
	 *
	 * @param t
	 * @return
	 */
	@Override
	public boolean add(track t) {
		if (!contains(t)) {
			super.add(t);
			t.addToGroup(this);
			updateBounds();
			return true;
		}
		return false;
	}

	/**
	 * remove track
	 *
	 * @param t
	 */
	public void remove(track t) {
		if (contains(t)) {
			super.remove(t);
			t.removeFromGroup(this);
			updateBounds();
		}
	}

	/**
	 * Bounds of trackgroup
	 *
	 * @return bounds
	 */
	public rectangle getBounds() {
		return bounds;
	}

	/**
	 * Bounds of trackgroup including 20 pixel around if bounds are too small.
	 *
	 * @return bounds
	 */
	public rectangle getClickBounds() {
		return clickbounds;
	}

	/**
	 * all tracks free?
	 *
	 * @return
	 */
	public boolean isFree() {
		boolean isfree = true;
		for (track t : this) {
			isfree &= t.isFree();
		}
		return isfree;
	}

	/**
	 * any track used?
	 *
	 * @return
	 */
	public boolean isUsed() {
		boolean isused = false;
		for (track t : this) {
			isused |= t.isUsed();
		}
		return isused;
	}

	/**
	 * move by x/y pixels (>0 moved right/down)
	 *
	 * @param dx
	 * @param dy
	 */
	public void moveBy(int dx, int dy) {
		for (track t : this) {
			point p = t.getXY();
			p.moveBy(dx, dy);
			t.move(p);
		}
	}
}
