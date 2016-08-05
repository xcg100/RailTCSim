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
package org.railsim;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import org.railsim.editor.*;
import org.railsim.event.AbstractEvent;
import org.railsim.event.AbstractListener;

/**
 *
 * @author js
 */
//public class gamearea extends java.awt.Canvas implements MouseListener, MouseMotionListener, MouseWheelListener
public class gamearea extends javax.swing.JPanel implements MouseListener, MouseMotionListener, MouseWheelListener,
		KeyListener {

	public painter thepainter;
	private HashMap<String, editorbase> editors = new HashMap<>();
	private String oldselected = "";
	private boolean interfaceBlocked = false;

	/**
	 * Creates a new instance of gamearea
	 */
	public gamearea() {
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resize();
			}
		});
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		final gamearea g = this;
		dataCollector.collector.editModeListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				editorbase edO, edN;
				edO = editors.get(oldselected);
				oldselected = dataCollector.collector.getEditMode();
				edN = editors.get(oldselected);
				if (edO != null && edO != edN) {
					edO.setSelected(false, thepainter, g);
				}
				if (edN != null && edO != edN) {
					edN.setSelected(true, thepainter, g);
				}
			}
		});
		setFocusable(true);
	}

	public void addEditor(String name, editorbase ed) {
		editors.put(name, ed);
	}
	boolean active = false;

	@Override
	public void paint(Graphics g) {
		if (!active) {
			thepainter.paint(g);
		}
	}

	public void paintScreen() {
		if (!active) {
			repaint();
		} else {
			// actively render the buffer image to the screen
			Graphics g;
			try {
				g = this.getGraphics();
				if (g != null) {
					thepainter.paint(g);
				}
				g.dispose();
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
	}

	public void paintEditor() {
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (ed != null) {
			ed.paint(thepainter, this);
		}
	}

	@Override
	public void update(Graphics g) {
		if (isShowing()) {
			paint(g);
		}
	}

	private void resize() {
		thepainter.setSize(getWidth(), getHeight());
	}

	public boolean runAction(EditorActionEvent action) {
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (ed != null) {
			return ed.runAction(action, thepainter, this);
		}
		return true;
	}

	public boolean runAction(String destination, EditorActionEvent action) {
		editorbase ed = editors.get(destination);
		if (ed != null) {
			return ed.runAction(action, thepainter, this);
		}
		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) { // Invoked when the mouse button has been clicked (pressed and released) on a component.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mouseClicked(e, thepainter, this);
		}
		requestFocusInWindow();
	}

	@Override
	public void mouseDragged(MouseEvent e) { // Invoked when a mouse button is pressed on a component and then dragged.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mouseDragged(e, thepainter, this);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) { // Invoked when the mouse enters a component.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mouseEntered(e, thepainter, this);
		}
		requestFocusInWindow();
	}

	@Override
	public void mouseExited(MouseEvent e) { // Invoked when the mouse exits a component.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mouseExited(e, thepainter, this);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) { // Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mouseMoved(e, thepainter, this);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) { // Invoked when a mouse button has been pressed on a component.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mousePressed(e, thepainter, this);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) { // Invoked when a mouse button has been released on a component.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mouseReleased(e, thepainter, this);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) { // Invoked when the mouse wheel is rotated.
		editorbase ed = editors.get(dataCollector.collector.getEditMode());
		if (!interfaceBlocked && ed != null) {
			ed.mouseWheelMoved(e, thepainter, this);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		displayInfo(e, "KEY TYPED: ");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		displayInfo(e, "KEY PRESSED: ");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		displayInfo(e, "KEY RELEASED: ");
	}

	private void displayInfo(KeyEvent e, String keyStatus) {

		//You should only rely on the key char if the event
		//is a key typed event.
		int id = e.getID();
		String keyString;
		if (id == KeyEvent.KEY_TYPED) {
			char c = e.getKeyChar();
			keyString = "key character = '" + c + "'";
		} else {
			int keyCode = e.getKeyCode();
			keyString = "key code = " + keyCode + " (" + KeyEvent.getKeyText(keyCode) + ")";
		}

		int modifiersEx = e.getModifiersEx();
		String modString = "extended modifiers = " + modifiersEx;
		String tmpString = KeyEvent.getModifiersExText(modifiersEx);
		if (tmpString.length() > 0) {
			modString += " (" + tmpString + ")";
		} else {
			modString += " (no extended modifiers)";
		}

		String actionString = "action key? ";
		if (e.isActionKey()) {
			actionString += "YES";
		} else {
			actionString += "NO";
		}

		String locationString = "key location: ";
		int location = e.getKeyLocation();
		if (location == KeyEvent.KEY_LOCATION_STANDARD) {
			locationString += "standard";
		} else if (location == KeyEvent.KEY_LOCATION_LEFT) {
			locationString += "left";
		} else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
			locationString += "right";
		} else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
			locationString += "numpad";
		} else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
			locationString += "unknown";
		}
		System.out.println(keyString);
		System.out.println(actionString);
		System.out.println(locationString);
	}
}
