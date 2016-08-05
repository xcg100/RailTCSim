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
package org.railsim.editor;

import org.railsim.event.AbstractEvent;
import org.railsim.service.path;
import org.railsim.service.track;
import org.railsim.service.trackGroup;
import org.railsim.service.trackObjects.trackObject;
import org.railsim.service.tracks.simpleTrack;

/**
 * Used to send events from editor to editor panels.
 *
 * @author js
 */
public class EditorEvent<T> extends AbstractEvent {

	public static final int GUIEVENTBASE = 0x10;
	public static final int GUI_SELECTED = 0x11;
	public static final int TRACKEVENTBASE = 0x100;
	public static final int TRACK_SELECTED = 0x101;
	public static final int TRACK_UNSELECTED = 0x102;
	public static final int TRACK_MOVED = 0x103;      // move, rotate and bow
	public static final int TRACK_ROTATED = 0x103;    // move, rotate and bow
	public static final int TRACK_BOWED = 0x103;      // move, rotate and bow
	public static final int TRACK_MOVING = 0x104;     // only moving
	public static final int TRACK_ROTATING = 0x105;   // only rotating
	public static final int TRACK_BOWING = 0x106;     // only bowing
	public static final int TRACK_VALUE_CHANGED = 0x107;
	public static final int TRACK_SIMPLEPAINTER = 0x108;
	public static final int TRACK_2TRACKACTIONFINISHED = 0x109;
	public static final int TRACKOBJECTEVENTBASE = 0x200;
	public static final int TRACKOBJECT_SELECTED = 0x201;
	public static final int TRACKOBJECT_UNSELECTED = 0x202;
	public static final int TRACKOBJECT_MOVED = 0x203;      // move, rotate
	public static final int TRACKOBJECT_ROTATED = 0x203;    // move, rotate
	public static final int TRACKOBJECT_MOVING = 0x204;     // only moving
	public static final int TRACKOBJECT_MOVEMODE = 0x205;
	public static final int TRACKOBJECT_VALUECHANGED = 0x206;
	public static final int TRACKOBJECT_GUITYPECHANGED = 0x207;
	public static final int TRACKOBJECT_ADDEDREMOVED = 0x208;
	public static final int TRACKOBJECT_MODIFIED = 0x209;
	public static final int PATHEDITEVENTBASE = 0x300;
	public static final int PATHEDIT_PATHABLE_SELECTED = 0x301;
	public static final int PATHEDIT_PATHABLE_UNSELECTED = 0x302;
	public static final int PATHEDIT_PATHABLE_RENAMED = 0x303;
	public static final int PATHEDIT_PATHABLE_STATECHANGED = 0x304;
	public static final int PATHEDIT_REGION_RENAMED = 0x305;
	public static final int PATHEDIT_PATH_SELECTED = 0x306;
	public static final int PATHEDIT_PATH_UNSELECTED = 0x307;
	public static final int PATHEDIT_PATH_RENAMED = 0x308;
	public static final int PATHEDIT_PATH_ADDED = 0x309;
	public static final int PATHEDIT_PATH_DELETED = 0x30a;
	public static final int PATHEDIT_PATH_MODIFIED = 0x30b;
	public static final int TRAINSETEVENTBASE = 0x400;
	public static final int TRAINSET_CHANGED = 0x401;
	public static final int TRACKGROUPEVENTBASE = 0x500;
	public static final int TRACKGROUP_UNSELECTED = 0x501;
	public static final int TRACKGROUP_SELECTED = 0x502;
	public static final int TRACKGROUP_AREASELECTED = 0x503;
	int type;
	T eventobj;
	double value = 0;

	/**
	 * Creates a new instance of EditorEvent
	 *
	 * @param _type Type
	 * @param t Track
	 */
	public EditorEvent(int _type, T t) {
		super(new Integer(_type));
		type = _type;
		eventobj = t;
	}

	/**
	 * Creates a new instance of EditorEvent
	 *
	 * @param _type Type
	 * @param t track
	 * @param _value value
	 */
	public EditorEvent(int _type, T t, double _value) {
		super(new Integer(_type));
		type = _type;
		eventobj = t;
		value = _value;
	}

	/**
	 * Type
	 *
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Track
	 *
	 * @return Track
	 */
	public T getObject() {
		return eventobj;
	}

	/**
	 * Track
	 *
	 * @return Track
	 */
	public track getTrack() {
		if (eventobj instanceof track) {
			return (track) eventobj;
		} else {
			return null;
		}
	}

	/**
	 * trackObject
	 *
	 * @return trackObject
	 */
	public trackObject getTrackObject() {
		if (eventobj instanceof trackObject) {
			return (trackObject) eventobj;
		} else {
			return null;
		}
	}

	/**
	 * simpleTrack
	 *
	 * @return simpleTrack
	 */
	public simpleTrack getSimpleTrack() {
		if (eventobj instanceof simpleTrack) {
			return (simpleTrack) eventobj;
		} else {
			return null;
		}
	}

	/**
	 * trackGroup
	 *
	 * @return trackGroup
	 */
	public trackGroup getTrackGroup() {
		if (eventobj instanceof trackGroup) {
			return (trackGroup) eventobj;
		} else {
			return null;
		}
	}

	/**
	 * Value
	 *
	 * @return value
	 */
	public double getValue() {
		return value;
	}

	public String getString() {
		if (eventobj instanceof String) {
			return (String) eventobj;
		} else {
			return eventobj.toString();
		}
	}

	/**
	 * path
	 *
	 * @return path
	 */
	public path getPath() {
		if (eventobj instanceof path) {
			return (path) eventobj;
		} else {
			return null;
		}
	}
}
