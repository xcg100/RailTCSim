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
import java.util.*;

import org.railsim.*;
import org.railsim.event.*;
import org.railsim.service.*;
import org.railsim.service.trackObjects.*;
import org.railsim.toolset.LockedList;

/**
 *
 * @author js
 */
public class patheditor extends editorbase {

	int mousex;
	int mousey;
	AbstractListener l = null;

	class trackObjectStore {

		/**
		 * for editor use
		 */
		public track mytrack = null;
		public pathableObject to = null;
		rectangle bounds;

		public trackObjectStore(track _mytrack, pathableObject _to) {
			mytrack = _mytrack;
			to = _to;
			bounds = mytrack.translateTrackObjectBounds(to);
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
	pathableObject editTO = null;
	path editPath = null;

	public patheditor() {
		l = new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateTrackList();
			}
		};
	}

	private void setCursor(int c, gamearea thegame) {
	}
	final static int PM_SELECTED = 1;
	final static int PM_ROLLOVER = 2;

	private void paintBox(pathableObject tos, painter thepainter, int pm, boolean clear) {
		Color c;
		switch (pm) {
			case PM_SELECTED:
				c = Color.BLUE;
				break;
			case PM_ROLLOVER:
				c = Color.GREEN;
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
			rectangle rec = track.translateTrackObjectBounds(tos);
			Stroke os = g.getStroke();
			g.setColor(c);
			g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{4, 2, 1}, 0));
			g.drawRect(rec.x, rec.y, rec.width, rec.height);
			g.setStroke(os);
		}
	}

	private void paintTrack(Graphics2D g, track t) {
		int px = t.getXY().getX();
		int py = t.getXY().getY();
		java.util.List<rotatepoint> rl = t.getPoints();
		for (rotatepoint rp : rl) {
			g.drawLine(px, py, rp.getX(), rp.getY());
			px = rp.getX();
			py = rp.getY();
		}
	}

	private void paintPath(painter thepainter, path p) {
		Graphics2D g = thepainter.getDrawArea();
		java.util.List<pathtrack> tl = p.getTracks();
		Stroke os = g.getStroke();
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		for (pathtrack pt : tl) {
			track t = pt.getTrack();
			g.setColor(Color.YELLOW);
			for (track ct : t.getCrossings()) {
				paintTrack(g, ct);
			}
			g.setColor(Color.MAGENTA);
			paintTrack(g, t);
		}
		g.setStroke(os);
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
			trackObjectStore t = findTSO(tx, ty, editTO);
			if (t != null) {
				editTO = t.to;
			} else {
				editTO = null;
			}
			editPath = null;
			//thepainter.clearDrawArea();
			paint(thepainter, thegame);
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_UNSELECTED);
			if (editTO != null) {
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_SELECTED, editTO);
			} else {
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_UNSELECTED);
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
	}

	@Override
	public void mouseMoved(MouseEvent e, painter thepainter, gamearea thegame) {
		int x = e.getX();
		int y = e.getY();
		int tx = thepainter.translateS2T_X(x);
		int ty = thepainter.translateS2T_Y(y);
		thepainter.clearDrawArea();
		trackObjectStore t = findTSO(tx, ty, editTO);
		paint(thepainter, thegame);
		if (t != null && t.to != editTO) {
			paintBox(t.to, thepainter, PM_ROLLOVER, false);
		}
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
			editPath = null;
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_UNSELECTED);
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_UNSELECTED);
		} else {
			dataCollector.collector.sizeOrPosChangedListeners.removeListener(l);
			thepainter.clearDrawArea();
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_UNSELECTED);
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_UNSELECTED);
		}
	}

	@Override
	public void paint(painter thepainter, gamearea thegame) {
		if (editTO != null) {
			paintBox(editTO, thepainter, PM_SELECTED, true);
			if (editPath != null) {
				paintPath(thepainter, editPath);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public boolean runAction(EditorActionEvent action, painter thepainter, gamearea thegame) {
		String a = action.getAction();
		if (a.compareTo("selectsignal") == 0) {
			editTO = findTSO((pathableObject) action.getValue());
			thepainter.showTrackObject(editTO);
			editPath = null;
			thepainter.clearDrawArea();
			paint(thepainter, thegame);
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_UNSELECTED);
			if (editTO != null) {
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_SELECTED, editTO);
			} else {
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_UNSELECTED);
			}
		} else if (a.compareTo("selectpath") == 0) {
			editPath = (path) action.getValue();
			editTO = findTSO(editPath.getSignal());
			thepainter.showTrackObject(editTO);
			thepainter.clearDrawArea();
			searchPath(editPath);
			paint(thepainter, thegame);
			if (editTO != null) {
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_SELECTED, editTO);
				if (editPath != null) {
					dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_SELECTED, editPath);
					paintPath(thepainter, editPath);
				} else {
					dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_UNSELECTED);
				}
			} else {
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_UNSELECTED);
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_UNSELECTED);
			}
		} else if (a.compareTo("renamesignal") == 0) {
			EditorActionEvent2 e = (EditorActionEvent2) action;
			if (e.getValue() != null) {
				((trackObject) e.getValue()).setName((String) e.getValue2());
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_RENAMED, (trackObject) e.getValue());
			}
		} else if (a.compareTo("renamepath") == 0) {
			EditorActionEvent2 e = (EditorActionEvent2) action;
			if (e.getValue() != null) {
				((path) e.getValue()).setName((String) e.getValue2());
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_RENAMED, (path) e.getValue());
			}
		} else if (a.compareTo("renameregion") == 0) {
			EditorActionEvent2 e = (EditorActionEvent2) action;
			if (e.getValue() != null) {
				String oldr = (String) e.getValue();
				String newr = (String) e.getValue2();
				for (trackObject to : trackObject.allto.keySet()) {
					if (to.getRegion().compareTo(oldr) == 0) {
						to.setRegion(newr);
					}
				}
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_REGION_RENAMED, (String) e.getValue());
			}
		} else if (a.compareTo("newpath") == 0) {
			if (!editTO.isEnabled()) {
				editPath = new path(editTO);
				editTO.addPath(editPath);
				searchPath(editPath);

				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_ADDED, editPath);
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_SELECTED, editPath);
				paintPath(thepainter, editPath);
			}
		} else if (a.compareTo("copypath") == 0) {
			if (!editTO.isEnabled()) {
				editPath = (path) editPath.clone();
				editTO.addPath(editPath);
				searchPath(editPath);
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_ADDED, editPath);
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_SELECTED, editPath);
				paintPath(thepainter, editPath);
			}
		} else if (a.compareTo("delpath") == 0) {
			if (!editTO.isEnabled()) {
				if (!editPath.isUsed()) {
					editTO.delPath(editPath);
					dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_UNSELECTED);
					dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_DELETED, editPath);
					editPath = null;
				}
			}
		} else if (a.compareTo("delallpath") == 0) {
		} else if (a.compareTo("setpath") == 0) {
		} else if (a.compareTo("cancel_path") == 0) {
			editTO.unsetPath();
		} else if (a.compareTo("disablepath") == 0) {
			boolean en = !(Boolean) action.getValue();
			if (editPath.isFailure()) {
				en = false;
			}
			editPath.setEnabled(en);
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_STATECHANGED, editTO);
		} else if (a.compareTo("enablesignal") == 0) {
			boolean en = (Boolean) action.getValue();
			if (en) {
				if (editTO.getPathCount() == 0) { // search track for path or junction
					LinkedList<Object> items = null;
					try {
						items = track.getListToNextStopObject(editTO, track.GLTN_STOPOBJECT);
					} catch (IllegalArgumentException ex) {
						dataCollector.collector.gotException(ex);
					}
					if (items != null) {
						path p = new path(editTO);
						p.setAutomatic(true);

						int ecode = 0;
						boolean failure = false;
						for (Object o : items) {
							if (o instanceof tracksearchinfo) {
								ecode = ((tracksearchinfo) o).code;
								if (ecode == tracksearchinfo.CODE_ENDOFTRACK) {
									failure = true;
									break;
								}
							} else if (o instanceof track) {
								if (!failure) {
									track t = (track) o;
									if (t.isJunction()) {
										failure = true;
										break;
									} else {
										p.addTrack(t);
									}
								}
								if (ecode == tracksearchinfo.CODE_JUNCTIONWRONG) {
									failure = true;
									break;
								}
								if (ecode == tracksearchinfo.CODE_BUILDING) {
									failure = true;
									break;
								}
							}
						}
						if (!failure) {
							p.setName("AutoFS " + System.nanoTime());
							editTO.addPath(p);
							dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_ADDED, p);
						} else {
							en = false;
							dataCollector.collector.setStatus("Keine Fahrstraße ab Signal, keine autoimatische Fahrstraße möglich!");
						}
					}
				} else {
					for (path p : editTO.getAllPaths()) {
						if (p.isFailure() && p.isEnabled()) {
							en = false;
							dataCollector.collector.setStatus("Es sind Fehlerhafte Fahrstraßen enthalten!");
							break;
						}
					}
				}
			} else {
				if (editTO.getState() == 0) {
					if (editTO.getPathCount() == 1) {
						path p = editTO.getAllPaths().first();
						if (p.isAutomatic()) {
							editTO.delPath(p);
							dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_DELETED, p);
						}
					}
				} else {
					en = true;
					dataCollector.collector.setStatus("Signal ist in Nutzung!");
				}
			}
			editTO.setEnable(en);
			dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_STATECHANGED, editTO);
		} else if (a.compareTo("switchjunction") == 0) {
			if (!editTO.isEnabled()) {
				EditorActionEvent2 e = (EditorActionEvent2) action;
				track t = (track) e.getValue();
				int st = (Integer) e.getValue2();
				System.out.println("I:" + editPath.getTracks().indexOf(t));
				if (editPath.getTracks().indexOf(t) == 0 || t.isFree()) {
					t.setJunction(st);
				} else {
					dataCollector.collector.setStatus("Weiche belegt!");
				}

				searchPath(editPath, t);
				thepainter.clearDrawArea();
				paint(thepainter, thegame);
				dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATH_MODIFIED, editPath);
			}
		}

		return true;
	}

	private void addTO(boolean forward, track t) {
		java.util.List<trackObject> tol;
		tol = t.getTrackObjects(forward);
		int i = 0;
		for (trackObject to : tol) {
			if (to != null && to instanceof pathableObject) {
				trackObjectStore tos = new trackObjectStore(t, (pathableObject) to);
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

	private trackObjectStore findTSO(int x, int y, pathableObject prefered) {
		trackObjectStore ret = null;
		trackObjects.readLock();
		for (trackObjectStore t : trackObjects) {
			if (t.getBounds().contains(x, y)) {
				ret = t;
				if (prefered != null && t.to == prefered) {
					break;
				}
			}
		}
		trackObjects.readUnlock();
		return ret;
	}

	private pathableObject findTSO(pathableObject to) {
		if (trackObject.allto.containsKey(to)) {
			return to;
		}
		return null;
	}

	private void searchPath(path pa, track ignoreJunction) {
		pa.searchpath = null;
		if (pa.getSignal().isEnabled()) {
			return;
		}

		for (pathtrack pt : pa.getTracks()) {
			if (pt.isJunction() && pt.getTrack() != ignoreJunction) {
				pt.setJunction();
			}
		}
		pathableObject to = pa.getSignal();
		LinkedList<Object> items = null;
		try {
			items = track.getListToNextStopObject(to, track.GLTN_SIGNAL);
		} catch (IllegalArgumentException ex) {
			dataCollector.collector.gotException(ex);
		}
		if (items == null) {
			return;
		}
		pa.searchpath = items;
		pa.clearTracks();
		pa.setFailure(false);
		int ecode = 0;
		boolean endtracks = false;
		for (Object o : items) {
			if (o instanceof tracksearchinfo) {
				ecode = ((tracksearchinfo) o).code;
				if (ecode == tracksearchinfo.CODE_ENDOFTRACK) {
					endtracks = true;
					pa.setFailure(true);
				}
			} else if (o instanceof track) {
				if (!endtracks) {
					track t = (track) o;
					if (t.isJunction()) {
						pa.addTrack(t, t.getJunction());
					} else {
						pa.addTrack(t);
					}
				} else {
					break;
				}
				if (ecode == tracksearchinfo.CODE_JUNCTIONWRONG) {
					endtracks = true;
					pa.setFailure(true);
				}
				if (ecode == tracksearchinfo.CODE_BUILDING) {
					endtracks = true;
					pa.setFailure(true);
				}
			}
		}

	}

	private void searchPath(path ep) {
		searchPath(ep, null);
	}
}
