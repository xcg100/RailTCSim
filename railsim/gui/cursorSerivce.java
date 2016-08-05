/*
 * $Revision: 22 $
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
package org.railsim.gui;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author js
 */
public class cursorSerivce {

	final public static String DEFAULT = "DEFAULT";
	final public static String MOVE = "MOVE";
	final public static String ROTATE = "ROTATE";
	final public static String BOW = "BOW";
	final public static String LOCKED = "LOCKED";
	final public static String DESTINATION = "DESTINATION";
	final public static String WAIT = "WAIT";
	private java.util.concurrent.ConcurrentHashMap<String, Cursor> cursorList = new java.util.concurrent.ConcurrentHashMap<>();

	/**
	 * Creates a new instance of cursorSerivce
	 */
	public cursorSerivce() {
		cursorList.put("DEFAULT", new Cursor(Cursor.DEFAULT_CURSOR));
		cursorList.put("MOVE", new Cursor(Cursor.MOVE_CURSOR));
		cursorList.put("BOW", new Cursor(Cursor.W_RESIZE_CURSOR));
		cursorList.put("ROTATE", new Cursor(Cursor.E_RESIZE_CURSOR));
		cursorList.put("HAND", new Cursor(Cursor.HAND_CURSOR));
		cursorList.put("LOCKED", new Cursor(Cursor.HAND_CURSOR));
		cursorList.put("DESTINATION", new Cursor(Cursor.CROSSHAIR_CURSOR));
		cursorList.put("WAIT", new Cursor(Cursor.WAIT_CURSOR));

		loadCursor("MOVE", "move.png", new Point(15, 16));	    // 42x42
		loadCursor("BOW", "bend.png", new Point(12, 12));	    // 24x24
		loadCursor("ROTATE", "rotate.png", new Point(16, 16)); // 32x32
		loadCursor("LOCKED", "locked.png", new Point(5, 5));
	}

	public Cursor getCursor(String name) {
		if (cursorList.containsKey(name)) {
			return cursorList.get(name);
		} else {
			return Cursor.getDefaultCursor();
		}
	}

	private void loadCursor(String name, String filename, Point xy) {
		Image img;
		try {
			img = ImageIO.read(cursorSerivce.class.getResourceAsStream("/org/railsim/gui/resources/cursor/" + filename));
			Cursor c = java.awt.Toolkit.getDefaultToolkit().createCustomCursor(img, xy, name);
			cursorList.put(name, c);
		} catch (IOException | IndexOutOfBoundsException | HeadlessException ex) {
		}
	}
}
