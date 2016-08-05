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
import org.railsim.event.*;
import org.railsim.gui.cursorSerivce;
import org.railsim.service.*;
import org.railsim.service.exceptions.NotConnectedException;
import org.railsim.service.exceptions.PositionNotFoundException;
import org.railsim.service.trackObjects.*;
import org.railsim.toolset.LockedList;

/**
 *
 * @author js
 */
public class trackobjectseditor extends editorbase {

	private static final int CS_DEFAULT = 0;
	private static final int CS_MOVE = 1;
	private int curCursor = -1;
	private int editMode = -1;
	private int mousex, mousey;
	private boolean addTO = false;
	private String nextTO = "";
	private int nextRequirements = 0;
	AbstractListener l = null;

	class trackObjectStore {

		/**
		 * for editor use
		 */
		public track mytrack = null;
		/**
		 * for editor use
		 */
		public boolean forward = false;
		/**
		 * for editor use
		 */
		public int index = 0;
		public trackObject to = null;
		rectangle bounds;

		public trackObjectStore(track _mytrack, boolean _forward, int _index, trackObject _to) {
			mytrack = _mytrack;
			forward = _forward;
			index = _index;
			to = _to;
			bounds = mytrack.translateTrackObjectBounds(to, forward, index);
		}

		public rectangle getBounds() {
			return bounds;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof trackObjectStore) {
				trackObjectStore t = (trackObjectStore) o;
				return to.compareTo(t.to) == 0;
			}
			if (o instanceof trackObject) {
				trackObject t = (trackObject) o;
				return to.compareTo(t) == 0;
			}
			return false;
		}
	}
	LockedList<trackObjectStore> trackObjects = new LockedList<>();
	trackObjectStore editTO = null;
	trackindexpoint moveTIP = null;
	boolean moveFORWARD = false;

	public trackobjectseditor() {
		l = new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateTrackList();
			}
		};

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
				default:
					break;
			}
			curCursor = c;
		}
	}
	final static int PM_SELECTED = 1;
	final static int PM_ROLLOVER = 2;
	final static int PM_EDITING = 3;
	final static int PM_CONNECT = 4;

	private void paintBox(trackObjectStore tos, painter thepainter, int pm, boolean clear) {
		Color c;
		switch (pm) {
			case PM_SELECTED:
				c = Color.BLUE;
				break;
			case PM_ROLLOVER:
				c = Color.GREEN;
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
		if (tos != null) {
			rectangle rec = tos.getBounds();
			Stroke os = g.getStroke();
			g.setColor(c);
			g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{4, 2, 1}, 0));
			g.drawRect(rec.x, rec.y, rec.width, rec.height);
			g.setStroke(os);
		}
	}

	private void paintBox(track t, painter thepainter, int pm, boolean clear) {
		Color c;
		switch (pm) {
			case PM_SELECTED:
				c = Color.BLUE;
				break;
			case PM_ROLLOVER:
				c = Color.GREEN;
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
			g.setStroke(os);
		}
	}

	private void paintAllTo() {
		for (trackObjectStore tos : trackObjects) {
			paintBox(tos, dataCollector.collector.thepainter, PM_CONNECT, false);
		}
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
			moveTIP = null;
			if (addTO) {
			} else {
				trackObjectStore t = findTSO(tx, ty, editTO);
				editTO = t;
				thepainter.clearDrawArea();
				paint(thepainter, thegame);
				if (editTO != null) {
					moveFORWARD = editTO.forward;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e, painter thepainter, gamearea thegame) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int tx = thepainter.translateS2T_X(x);
			int ty = thepainter.translateS2T_Y(y);

			if (addTO) {
				track t = thepainter.findTrack(tx, ty);
				if (t != null) {
					if ((nextRequirements & trackObject.REQUIREMENT_NOBUILD) != 0 && t.getProgress() > 0) {
						if (editTO != null) {
							dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
						}
						editTO = null;
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf im Bau befindliche Gleise");
					} else if ((nextRequirements & trackObject.REQUIREMENT_NOTRAIN) != 0 && t.isTrainOn()) {
						if (editTO != null) {
							dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
						}
						editTO = null;
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
					} else if ((nextRequirements & trackObject.REQUIREMENT_NOSETPATH) != 0 && t.isFree()) {
						if (editTO != null) {
							dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
						}
						editTO = null;
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
					} else if ((nextRequirements & trackObject.REQUIREMENT_NOPATH) != 0 && t.isUsed()) {
						if (editTO != null) {
							dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
						}
						editTO = null;
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Fahrstraße");
					} else if (t.isJunction()) {
						if (editTO != null) {
							dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
						}
						editTO = null;
						dataCollector.collector.setStatus("Gleisobjekt auf Weiche nicht empfohlen!");
					} else {
						trackObject to = dataCollector.collector.tol.load(nextTO);
						if (to != null) {
							boolean ret = t.save_setTrackObjectAt(to, true, tx, ty);
							thepainter.paintOffTrack();
							updateTrackList();
							editTO = findTSO(to);
						} else {
							if (editTO != null) {
								dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
							}
							editTO = null;
						}
						thepainter.clearDrawArea();
						paint(thepainter, thegame);
						if (editTO != null) {
							dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_SELECTED, editTO.to);
							dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_ADDEDREMOVED, editTO.to);
							if (!(editTO.to instanceof pathableObject) || !((pathableObject) editTO.to).isEnabled()) {
								setCursor(CS_MOVE, thegame);
							}
						}
					}
				}
				addTO = false;
				dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_MOVEMODE);
			} else {
				if (moveTIP != null) {
					if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOBUILD) != 0 && moveTIP.getTrack().getProgress() > 0) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf im Bau befindliche Gleise");
					} else if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOTRAIN) != 0 && moveTIP.getTrack().isTrainOn()) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
					} else if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOSETPATH) != 0 && moveTIP.getTrack().isFree()) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
					} else if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOPATH) != 0 && moveTIP.getTrack().isUsed()) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Fahrstraße");
					} else if (moveTIP.getTrack().isJunction()) {
						dataCollector.collector.setStatus("Gleisobjekt auf Weiche nicht empfohlen!");
					} else {
						editTO.mytrack.setTrackObjectAt(null, editTO.forward, editTO.index);
						moveTIP.getTrack().save_setTrackObjectAt(editTO.to, moveFORWARD, moveTIP.getIndex());
					}
					updateTrackList();
					editTO = findTSO(editTO.to);

					dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_MOVED, editTO.to);
					setCursor(CS_MOVE, thegame);

					thepainter.paintOffTrack();
				} else {
					trackObjectStore t = findTSO(tx, ty, editTO);
					editTO = t;
					thepainter.clearDrawArea();
					paint(thepainter, thegame);
					if (t != null) {
						dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_SELECTED, t.to);
						setCursor(CS_MOVE, thegame);
					} else {
						dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
					}
				}
				moveTIP = null;
			}
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
		if (editTO != null) {
			if (editTO.to instanceof pathableObject && ((pathableObject) editTO.to).isEnabled()) {
				return;
			}
			int x = e.getX();
			int y = e.getY();
			int tx = thepainter.translateS2T_X(x);
			int ty = thepainter.translateS2T_Y(y);
			try {
				trackindexpoint tp = null;
				boolean fw = editTO.forward;
				if (moveTIP != null) {
					tp = moveTIP.getTrack().getTrackPoint(tx, ty);
					fw = moveFORWARD;
					try {
						if (tp != null && !tp.getTrack().hasSameOriantation(moveTIP.getTrack())) {
							fw = !fw;
						}
					} catch (NotConnectedException ex) {
						dataCollector.collector.gotException(ex);
					}
				} else {
					tp = editTO.mytrack.getTrackPoint(tx, ty);
					fw = editTO.forward;
					try {
						if (tp != null && !tp.getTrack().hasSameOriantation(editTO.mytrack)) {
							fw = !fw;
						}
					} catch (NotConnectedException ex) {
						dataCollector.collector.gotException(ex);
					}
				}

				if (tp != null && tp.getTrack() != null) {
					track t = new track(tp.getTrack());
					trackObject to2 = editTO.to.clone();

					if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOBUILD) != 0 && tp.getTrack().getProgress() > 0) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf im Bau befindliche Gleise");
					} else if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOTRAIN) != 0 && tp.getTrack().isTrainOn()) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
					} else if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOSETPATH) != 0 && tp.getTrack().isFree()) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
					} else if ((editTO.to.getRequirements() & trackObject.REQUIREMENT_NOPATH) != 0 && tp.getTrack().isUsed()) {
						dataCollector.collector.setStatus("dieses Gleisobjekt kann nicht auf Gleise mit Fahrstraße");
					} else if (tp.getTrack().isJunction()) {
						dataCollector.collector.setStatus("Gleisobjekt auf Weiche nicht empfohlen!");
					} else {
						dataCollector.collector.unsetStatus("dieses Gleisobjekt kann nicht auf im Bau befindliche Gleise");
						dataCollector.collector.unsetStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
						dataCollector.collector.unsetStatus("dieses Gleisobjekt kann nicht auf Gleise mit Zug");
						dataCollector.collector.unsetStatus("dieses Gleisobjekt kann nicht auf Gleise mit Fahrstraße");
						dataCollector.collector.unsetStatus("Gleisobjekt auf Weiche nicht empfohlen!");
					}

					t.save_setTrackObjectAt(to2, fw, tp.getIndex());
					thepainter.clearDrawArea();
					t.paintTrack(thepainter.getDrawArea());
					t.remove();
					moveTIP = tp;
					moveFORWARD = fw;
					dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_MOVING, to2);
					paint(thepainter, thegame);
				}
			} catch (PositionNotFoundException ex) {
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e, painter thepainter, gamearea thegame) {
		int x = e.getX();
		int y = e.getY();
		int tx = thepainter.translateS2T_X(x);
		int ty = thepainter.translateS2T_Y(y);
		thepainter.clearDrawArea();
		if (addTO) {
			track t = thepainter.findTrack(tx, ty);
			if (t != null) {
				paintBox(t, thepainter, PM_ROLLOVER, false);
			}
		} else {
			trackObjectStore t = findTSO(tx, ty, editTO);
			if (t != null && t != editTO) {
				paintBox(t, thepainter, PM_ROLLOVER, false);
				setCursor(CS_DEFAULT, thegame);
			} else if (t != null && t == editTO) {
				if (!(t.to instanceof pathableObject) || !((pathableObject) t.to).isEnabled()) {
					setCursor(CS_MOVE, thegame);
				}
			} else {
				setCursor(CS_DEFAULT, thegame);
			}
		}
		paint(thepainter, thegame);
		//paintAllTo();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void setSelected(boolean b, painter thepainter, gamearea thegame) {
		if (b) {
			dataCollector.collector.sizeOrPosChangedListeners.addListener(l);
			updateTrackList();
			thepainter.clearDrawArea();
			editTO = null;
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
			addTO = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_MOVEMODE);
			thepainter.setShowHiddenTrackObjects(true);
		} else {
			dataCollector.collector.sizeOrPosChangedListeners.removeListener(l);
			thepainter.clearDrawArea();
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
			addTO = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_MOVEMODE);
			thepainter.setShowHiddenTrackObjects(false);
		}
	}

	@Override
	public void paint(painter thepainter, gamearea thegame) {
		if (editTO != null) {
			paintBox(editTO, thepainter, PM_SELECTED, false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public boolean runAction(EditorActionEvent action, painter thepainter, gamearea thegame) {
		String a = action.getAction();
		if (a.compareTo("del") == 0) {
			if (editTO.to instanceof pathableObject && ((pathableObject) editTO.to).isEnabled()) {
				return false;
			}

			track et = editTO.mytrack;
			trackObject oldEditTO = editTO.to;
			et.setTrackObjectAt(null, editTO.forward, editTO.index);
			editTO.to.remove();
			thepainter.clearDrawArea();
			editTO = null;
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
			addTO = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_MOVEMODE);
			thepainter.paintOffTrack();
			updateTrackList();
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_ADDEDREMOVED, oldEditTO);
		} else if (a.compareTo("add") == 0) {
			if ((Boolean) action.getValue()) {
				addTO = true;
			} else {
				addTO = false;
			}
			editTO = null;
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_UNSELECTED);
		} else if (a.compareTo("rotate") == 0) {
			if (editTO.to instanceof pathableObject && ((pathableObject) editTO.to).isEnabled()) {
				return false;
			}

			track et = editTO.mytrack;
			et.setTrackObjectAt(null, editTO.forward, editTO.index);
			et.save_setTrackObjectAt(editTO.to, !editTO.forward, editTO.index);
			thepainter.clearDrawArea();
			addTO = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_MOVEMODE);
			thepainter.paintOffTrack();
			updateTrackList();
			editTO = findTSO(editTO.to);
			if (editTO != null) {
				dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_SELECTED, editTO.to);
			}
			paint(thepainter, thegame);
		} else if (a.compareTo("trackobjectselect") == 0) {
			nextTO = (String) action.getValue();
			trackObject to = dataCollector.collector.tol.load(nextTO);
			if (to != null) {
				nextRequirements = to.getRequirements();
			}
			dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_GUITYPECHANGED, nextTO);
		}
		return true;
	}

	private void addTO(boolean forward, track t) {
		java.util.List<trackObject> tol;
		tol = t.getTrackObjects(forward);
		int i = 0;
		for (trackObject to : tol) {
			if (to != null) {
				trackObjectStore tos = new trackObjectStore(t, forward, i, to);
				trackObjects.add(tos);
			}
			++i;
		}
	}

	private void updateTrackList() {
		LockedList<track> vt = dataCollector.collector.thepainter.getVisibleTracks();

		trackObject to;
		vt.readLock();
		trackObjects.writeLock();
		trackObjects.clear();
		for (track t : vt) {
			addTO(false, t);
			addTO(true, t);
		}
		trackObjects.writeUnlock();
		vt.readUnlock();
	}

	private trackObjectStore findTSO(int x, int y, trackObjectStore prefered) {
		trackObjectStore ret = null;
		trackObjects.readLock();
		for (trackObjectStore t : trackObjects) {
			if (t.getBounds().contains(x, y)) {
				ret = t;
				if (prefered != null && t.equals(prefered)) {
					break;
				}
			}
		}
		trackObjects.readUnlock();
		return ret;
	}

	private trackObjectStore findTSO(trackObject to) {
		trackObjectStore ret = null;
		trackObjects.readLock();
		for (trackObjectStore t : trackObjects) {
			if (t.equals(to)) {
				ret = t;
				break;
			}
		}
		trackObjects.readUnlock();
		return ret;
	}
}
