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
package org.railsim.editor;

import java.awt.*;
import java.awt.event.*;

import org.railsim.*;
import org.railsim.gui.cursorSerivce;
import org.railsim.service.*;

/**
 *
 * @author js
 */
public class trackgroupeditor extends editorbase {

	static final int CS_NONE = 0;
	static final int CS_DEFAULT = 0;
	static final int CS_MOVE = 1;
	static final int CS_ROTATE = 2;
	static final int CS_BOW = 3;
	static final int CS_LOCKED = 4;
	static final int CS_DESTINATION = 5;
	static final int CS_WAIT = 6;
	int curCursor = -1;
	int mousex, mousey;
	int nextLevel = 0;
	int startx, starty;
	int stopx, stopy;
	rectangle edRect;
	boolean editMode = false;
	boolean includeTouched = false;
	boolean moveMode = false;
	trackGroup editGroup = null;
	trackGroup tmpEditGroup = null;
	private boolean addedit = true;

	public trackgroupeditor() {
	}

	private void setCursor(int c, gamearea thegame) {
		if (c != curCursor) {
			switch (c) {
				case CS_DEFAULT:
					thegame.setCursor(dataCollector.collector.cursors.getCursor(cursorSerivce.DEFAULT));
					break;
				case CS_MOVE:
					thegame.setCursor(dataCollector.collector.cursors.getCursor(cursorSerivce.MOVE));
					break;
				case CS_ROTATE:
					thegame.setCursor(dataCollector.collector.cursors.getCursor(cursorSerivce.ROTATE));
					break;
				case CS_BOW:
					thegame.setCursor(dataCollector.collector.cursors.getCursor(cursorSerivce.BOW));
					break;
				case CS_LOCKED:
					thegame.setCursor(dataCollector.collector.cursors.getCursor(cursorSerivce.LOCKED));
					break;
				case CS_DESTINATION:
					thegame.setCursor(dataCollector.collector.cursors.getCursor(cursorSerivce.DESTINATION));
					break;
				case CS_WAIT:
					thegame.setCursor(dataCollector.collector.cursors.getCursor(cursorSerivce.WAIT));
					break;
				default:
					break;
			}
			curCursor = c;
		}
	}

	private point getXYbyCenter(track et, int x, int y) {
		point ret = et.getXY();
		rectangle rec = et.getClickBounds();
		int cx = (int) rec.getCenterX();
		int cy = (int) rec.getCenterY();
		ret = new point(x - (cx - ret.getX()), y - (cy - ret.getY()));
		return ret;
	}
	final static int PM_SELECTED = 1;
	final static int PM_ROLLOVER = 2;
	final static int PM_ROLLOVER2 = 3;
	final static int PM_ROLLOVER3 = 4;
	final static int PM_EDITING = 5;
	final static int PM_CONNECT = 6;

	private void paintGroupItems(trackGroup tg, painter thepainter, int pm, boolean clear) {
		Color c;
		switch (pm) {
			case PM_SELECTED:
				c = Color.BLUE;
				break;
			case PM_ROLLOVER:
				c = Color.GREEN;
				break;
			case PM_ROLLOVER2:
				c = Color.LIGHT_GRAY;
				break;
			case PM_ROLLOVER3:
				c = Color.DARK_GRAY;
				break;
			case PM_EDITING:
				c = Color.CYAN;
				break;
			case PM_CONNECT:
				c = Color.MAGENTA;
				break;
			default:
				c = Color.LIGHT_GRAY;
				break;
		}

		Graphics2D g;
		if (clear) {
			g = thepainter.clearDrawArea();
		} else {
			g = thepainter.getDrawArea();
		}
		if (tg != null) {
			Stroke os = g.getStroke();
			g.setColor(c);
			for (track t : tg) {
				rectangle rec = t.getClickBounds();
				//g.setStroke(new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,1, new float[]{ 4,2,1 }, 0));
				g.drawRect(rec.x, rec.y, rec.width, rec.height);
			}
			//g.setStroke(os);
		}
	}

	private void paintBox(trackGroup t, painter thepainter, int pm, boolean clear) {
		Color c;
		switch (pm) {
			case PM_SELECTED:
				c = Color.BLUE;
				break;
			case PM_ROLLOVER:
				c = Color.GREEN;
				break;
			case PM_ROLLOVER2:
				c = Color.LIGHT_GRAY;
				break;
			case PM_ROLLOVER3:
				c = Color.DARK_GRAY;
				break;
			case PM_EDITING:
				c = Color.MAGENTA;
				break;
			case PM_CONNECT:
				c = Color.CYAN;
				break;
			default:
				c = Color.LIGHT_GRAY;
				break;
		}

		Graphics2D g;
		if (clear) {
			g = thepainter.clearDrawArea();
		} else {
			g = thepainter.getDrawArea();
		}
		if (t != null) {
			rectangle rec = t.getClickBounds();
			Stroke os = g.getStroke();
			g.setColor(c);
			g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{4, 2, 1}, 0));
			g.drawRect(rec.x, rec.y, rec.width, rec.height);
			int cx = (int) rec.getCenterX();
			int cy = (int) rec.getCenterY();
			g.fillOval(cx - 5, cy - 5, 10, 10);
			g.setStroke(os);
		}
	}

	private void paintBox(int x1, int y1, int x2, int y2, boolean em, painter thepainter, int pm, boolean clear) {
		Color c;
		switch (pm) {
			case PM_SELECTED:
				c = Color.BLUE;
				break;
			case PM_ROLLOVER:
				c = Color.GREEN;
				break;
			case PM_ROLLOVER2:
				c = Color.LIGHT_GRAY;
				break;
			case PM_ROLLOVER3:
				c = Color.DARK_GRAY;
				break;
			case PM_EDITING:
				c = Color.MAGENTA;
				break;
			case PM_CONNECT:
				c = Color.CYAN;
				break;
			default:
				c = Color.LIGHT_GRAY;
				break;
		}

		Graphics2D g;
		if (clear) {
			g = thepainter.clearDrawArea();
		} else {
			g = thepainter.getDrawArea();
		}

		if (em) {
			Stroke os = g.getStroke();
			g.setColor(c);
			g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{4, 2, 1}, 0));
			g.drawRect(x1, y1, x2 - x1, y2 - y1);
			g.setStroke(os);
		}
	}

	private void clearTemporaryGroup() {
		if (tmpEditGroup != null) {
			tmpEditGroup.remove();
			tmpEditGroup = null;
		}
	}

	private trackGroup createTemporaryGroup(int x1, int y1, int x2, int y2) {
		clearTemporaryGroup();

		java.util.List<track> l = dataCollector.collector.thepainter.findTrackInArea(x1, y1, x2, y2, includeTouched);
		if (!l.isEmpty()) {
			trackGroup tg = new trackGroup();
			for (track t : l) {
				if (!t.isInGroup()) {
					tg.add(t);
				}
			}
			if (!tg.isEmpty()) {
				tmpEditGroup = tg;
			} else {
				tg.remove();
			}
		}
		return tmpEditGroup;
	}

	private trackGroup checkTrackPerm(painter thepainter, int tx, int ty, boolean inMove) {
		thepainter.clearDrawArea();
		return thepainter.findGroup(tx, ty, editGroup);
	}

	@Override
	public void mouseClicked(MouseEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void mousePressed(MouseEvent e, painter thepainter, gamearea thegame) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int tx = thepainter.translateS2T_X(x);
			int ty = thepainter.translateS2T_Y(y);
			mousex = tx;
			mousey = ty;
			moveMode = false;

			if (editMode && edRect != null && edRect.contains(tx, ty)) {
				if (tmpEditGroup.isFree() && !tmpEditGroup.isUsed()) {
					setCursor(CS_MOVE, thegame);
					moveMode = true;
				} else {
					dataCollector.collector.setStatus("Gleise sind Teil einer Fahrstraße!");
				}
			} else {
				trackGroup t = checkTrackPerm(thepainter, tx, ty, false);
				if (t == null) {
					startx = stopx = tx;
					starty = stopy = ty;
					editMode = true;
					editGroup = null;
					clearTemporaryGroup();
					dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_UNSELECTED);
				} else {
					clearTemporaryGroup();
					editMode = false;
					if (editGroup != null && editGroup == t) {
						// TODO move/rotate
					} else {
						editGroup = t;
						dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_SELECTED, editGroup);
					}
				}
			}
			paintBox(editGroup, thepainter, PM_SELECTED, true);
			thepainter.paintOffTrack();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e, painter thepainter, gamearea thegame) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int tx = thepainter.translateS2T_X(x);
			int ty = thepainter.translateS2T_Y(y);

			if (editGroup == null && editMode) {
				if (tmpEditGroup != null) {
					if (moveMode) {
						int dx = tx - mousex;
						int dy = ty - mousey;
						tmpEditGroup.moveBy(dx, dy);
						moveMode = false;
						startx += dx;
						starty += dy;
						stopx += dx;
						stopy += dy;
						edRect = new rectangle(startx, starty, stopx, stopy);
					}
				} else {
					stopx = tx;
					stopy = ty;
					edRect = new rectangle(startx, starty, stopx, stopy);
					createTemporaryGroup(startx, starty, stopx, stopy);
					if (tmpEditGroup != null) {
						dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_AREASELECTED, tmpEditGroup);
					} else {
						dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_UNSELECTED);
					}
				}
			}
			paintBox(editGroup, thepainter, PM_SELECTED, true);
			paintBox(startx, starty, stopx, stopy, editMode, thepainter, PM_SELECTED, false);
			paintGroupItems(tmpEditGroup, thepainter, PM_EDITING, false);
			thepainter.paintOffTrack();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void mouseExited(MouseEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void mouseDragged(MouseEvent e, painter thepainter, gamearea thegame) {
		int x = e.getX();
		int y = e.getY();
		int tx = thepainter.translateS2T_X(x);
		int ty = thepainter.translateS2T_Y(y);

		paintBox(editGroup, thepainter, PM_SELECTED, true);
		if (editGroup == null && editMode && tmpEditGroup == null) {
			stopx = tx;
			stopy = ty;
		} else if (editMode && tmpEditGroup != null) {
			if (moveMode) {
				int dx = tx - mousex;
				int dy = ty - mousey;
				paintBox(startx + dx, starty + dy, stopx + dx, stopy + dy, editMode, thepainter, PM_EDITING, false);
			}
		}
		paintBox(startx, starty, stopx, stopy, editMode, thepainter, PM_SELECTED, false);
		paintGroupItems(tmpEditGroup, thepainter, PM_EDITING, false);
		thepainter.paintOffTrack();
	}

	@Override
	public void mouseMoved(MouseEvent e, painter thepainter, gamearea thegame) {
		int x = e.getX();
		int y = e.getY();
		int tx = thepainter.translateS2T_X(x);
		int ty = thepainter.translateS2T_Y(y);

		setCursor(CS_DEFAULT, thegame);
		paintBox(editGroup, thepainter, PM_SELECTED, false);
		if (editMode && edRect != null && edRect.contains(tx, ty)) {
			setCursor(CS_MOVE, thegame);
		} else {
			trackGroup t = checkTrackPerm(thepainter, tx, ty, true);
			if (t != null) {
				if (t == tmpEditGroup || t == editGroup) {
					setCursor(CS_MOVE, thegame);
				} else {
					paintBox(editGroup, thepainter, PM_ROLLOVER, false);
				}
			}
		}
		paintBox(startx, starty, stopx, stopy, editMode, thepainter, PM_SELECTED, false);
		paintGroupItems(tmpEditGroup, thepainter, PM_EDITING, false);
		thepainter.paintOffTrack();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void setSelected(boolean b, painter thepainter, gamearea thegame) {
		if (b) {
			thepainter.clearDrawArea();
			clearTemporaryGroup();
			editGroup = null;
			editMode = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_UNSELECTED);
			thepainter.setShowHiddenTrackObjects(true);
		} else {
			thepainter.clearDrawArea();
			clearTemporaryGroup();
			editGroup = null;
			editMode = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_UNSELECTED);
			thepainter.setShowHiddenTrackObjects(false);
		}
	}

	@Override
	public void paint(painter thepainter, gamearea thegame) {
		paintBox(editGroup, thepainter, PM_SELECTED, false);
		paintBox(startx, starty, stopx, stopy, editMode, thepainter, PM_SELECTED, false);
		paintGroupItems(tmpEditGroup, thepainter, PM_EDITING, false);
	}

	@Override
	public void keyTyped(KeyEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public boolean runAction(EditorActionEvent action, painter thepainter, gamearea thegame) { // TODO: neue editMode Flags
		String a = action.getAction();

		if (a.compareTo("del") == 0) {
			if (tmpEditGroup != null) {
				if (tmpEditGroup.isFree() && !tmpEditGroup.isUsed()) {
					while (!tmpEditGroup.isEmpty()) {
						track t = tmpEditGroup.iterator().next();
						thepainter.delTrack(t);
					}
					thepainter.clearDrawArea();
					clearTemporaryGroup();
					editGroup = null;
					editMode = false;
					dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_UNSELECTED);
				} else {
					dataCollector.collector.setStatus("Gleise sind Teil einer Fahrstraße!");
				}
			}
		} else if (a.compareTo("touchedT") == 0) {
			includeTouched = (Boolean) action.getValue();
			if (editMode && tmpEditGroup != null) {
				createTemporaryGroup(startx, starty, stopx, stopy);
				if (tmpEditGroup != null) {
					dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_AREASELECTED, tmpEditGroup);
				} else {
					dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_UNSELECTED);
				}
				paintBox(editGroup, thepainter, PM_SELECTED, true);
				paintBox(startx, starty, stopx, stopy, editMode, thepainter, PM_SELECTED, false);
				paintGroupItems(tmpEditGroup, thepainter, PM_EDITING, false);
			}
		} else if (a.compareTo("clone") == 0) { // TODO
		} else if (a.compareTo("clearselection") == 0) {
			thepainter.clearDrawArea();
			clearTemporaryGroup();
			editGroup = null;
			editMode = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACKGROUP_UNSELECTED);
		}
		return true;
	}
}
