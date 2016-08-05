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

import org.railsim.*;
import org.xml.sax.Attributes;

/**
 *
 * @author js
 */
public class trackpoint extends point {

	protected track t;

	/**
	 * Creates a new instance of trackpoint
	 */
	public trackpoint(track _t, int _x, int _y) {
		super(_x, _y);
		t = _t;
	}

	public trackpoint(track _t, point p) {
		super(p);
		t = _t;
	}

	public trackpoint(trackpoint _t) {
		super(_t);
		t = _t.t;
	}

	public track getTrack() {
		return t;
	}

	public trackpoint(Attributes meta) {
		super(0, 0);
		int _x = Integer.parseInt(meta.getValue("x"));
		int _y = Integer.parseInt(meta.getValue("y"));
		setXY(_x, _y);
		t = dataCollector.collector.thepainter.tracks.get(Integer.parseInt(meta.getValue("id")));
	}

	public static String getType(Attributes meta) {
		return meta.getValue("type");
	}

	public String getSaveString(String type) {
		StringBuffer w = new StringBuffer();
		w.append("<point ");
		w.append("type='" + type + "' ");
		w.append("id='" + dataCollector.collector.thepainter.tracks.indexOf(t) + "' ");
		w.append("x='" + getX() + "' ");
		w.append("y='" + getY() + "' ");
		w.append("/>\n");
		/*
		 type                CDATA    #REQUIRED
		 id                  CDATA   #REQUIRED
		 x                   CDATA   #REQUIRED
		 y                   CDATA   #REQUIRED
		 */
		return w.toString();
	}
}
