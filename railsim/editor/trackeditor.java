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
import org.railsim.gui.progressDialog;
import org.railsim.gui.progressDialogRunnable;
import org.railsim.service.*;
import org.railsim.service.tracks.*;

/**
 *
 * @author js
 */
public class trackeditor extends editorbase {

	track editTrack = null;
	track editTrack2 = null;
	track connectTrack1 = null;
	int connectInfo1 = 0;
	track connectTrack2 = null;
	int connectInfo2 = 0;
	boolean newTrack = false;
	static final int CS_NONE = 0;
	static final int CS_DEFAULT = 0;
	static final int CS_MOVE = 1;
	static final int CS_ROTATE = 2;
	static final int CS_BOW = 3;
	static final int CS_LOCKED = 4;
	static final int CS_DESTINATION = 5;
	static final int CS_WAIT = 6;
	static final int EM_SECONDTRACK = 0x100;
	static final int EM_FINDTRACK = 0x200;
	static final int EM_ADDTRACK = 0x400;
	static final int EM_MARKTRACK = 0x800;
	static final int EM_MARKSELECTEDTRACK = 0x1000;
	static final int EM_UNUSEDTRACK = 0x2000;
	static final int EM_FREETRACK = 0x4000;
	static final int EM_UNCONNECTED1TRACK = 0x8000;
	static final int EM_UNCONNECTED2TRACK = 0x10000;
	static final int EM_UNCONNECTEDENDTRACK = 0x20000;
	static final int EM_SELECTSAMETRACK = 0x40000;
	static final int EM_MODEBMR = 0x80000;        // bow, move, rotate
	static final int EM_FINDTRACKMOVE = 0x100000;
	static final int EM_MOVE = 0x1;
	static final int EM_ROTATE = 0x2;
	static final int EM_BOW = 0x4;
	static final int EM_LOCKED = 0x8;
	static final int EM_CONNECT2 = 0x10;
	static final int EM_PARALLEL2 = 0x20;
	static final int EDM_DEFAULT = (EM_FINDTRACKMOVE | EM_MODEBMR | EM_SELECTSAMETRACK | EM_FREETRACK | EM_MARKTRACK | EM_MARKSELECTEDTRACK | EM_FINDTRACK);
	static final int EDM_DEFAULTADD = (EM_FINDTRACKMOVE | EM_ADDTRACK | EM_MODEBMR | EM_SELECTSAMETRACK | EM_FREETRACK | EM_MARKTRACK | EM_MARKSELECTEDTRACK | EM_FINDTRACK);
	static final int EDM_CONNECT2 = (EM_CONNECT2 | EM_FINDTRACKMOVE | EM_FINDTRACK | EM_SECONDTRACK | EM_UNCONNECTEDENDTRACK | EM_UNUSEDTRACK | EM_MARKSELECTEDTRACK | EM_MARKTRACK);
	int curCursor = -1;
	int editMode = -1;
	int mousex, mousey;
	int nextLevel = 0;
	private simpleTrack next_tp = new simpleTrack();
	private boolean addedit = true;

	public trackeditor() {
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

	private void paintBox(track t, painter thepainter, int pm, boolean clear) {
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
			g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
			g.drawLine(cx, cy, t.getDestXY().getX(), t.getDestXY().getY());
			g.setStroke(os);
		}
	}

	private void paintLine(track t, painter thepainter, int tx, int ty) {
		Graphics2D g;
		g = thepainter.getDrawArea();
		if (t != null) {
			rectangle rec = t.getClickBounds();
			Stroke os = g.getStroke();
			g.setColor(Color.RED);
			int cx = (int) rec.getCenterX();
			int cy = (int) rec.getCenterY();
			g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
			g.drawLine(cx, cy, tx, ty);
			g.setStroke(os);
		}
	}

	private track[] checkTrackPerm(painter thepainter, int tx, int ty, boolean inMove) {
		thepainter.clearDrawArea();
		track[] t = new track[2];;
		if ((((editMode & EM_FINDTRACKMOVE) != 0) && inMove)
				|| ((editMode & EM_FINDTRACK) != 0 && !inMove)) {
			t[0] = t[1] = thepainter.findTrack(tx, ty, editTrack);

			if (t[0] != null) {
				int c = PM_ROLLOVER;
				if (t[0].isInGroup()) {
					dataCollector.collector.setStatus("Gleis Teil eines Moduls!");
					t[0] = null;
					c = PM_ROLLOVER3;
				} else if (!t[0].isFree()) {
					dataCollector.collector.setStatus("Gleis für Zug reserviert!");
					if ((editMode & EM_FREETRACK) != 0) {
						t[0] = null;
					}
					c = PM_ROLLOVER3;
				} else if (t[0].isUsed()) {
					dataCollector.collector.setStatus("Gleis ist Teil einer Fahrstraße!");
					if ((editMode & EM_UNUSEDTRACK) != 0) {
						t[0] = null;
					}
					c = PM_ROLLOVER2;
				}
				if (t[0] != null && (editMode & EM_UNCONNECTED1TRACK) != 0) {
					if (!(t[0].isStartFree() || t[0].isEndFree())) {
						t[0] = null;
						c = PM_ROLLOVER3;
					}
				}
				if (t[0] != null && (editMode & EM_UNCONNECTED2TRACK) != 0) {
					if (!(t[0].isStartFree() && t[0].isEndFree())) {
						t[0] = null;
						c = PM_ROLLOVER3;
					}
				}
				if (t[0] != null && (editMode & EM_UNCONNECTEDENDTRACK) != 0) {
					if (!t[0].isEndFree()) {
						t[0] = null;
						c = PM_ROLLOVER3;
					}
				}
				if (!inMove) {
					if (t[0] != null) {
						newTrack = true;
					}
					if (t[0] != null && (editMode & EM_SELECTSAMETRACK) != 0 && editTrack == t[0]) {
						newTrack = false;
					} else if (t[0] == editTrack) {
						t[0] = null;
					}
				}
				if (t[1] != null && (editMode & EM_MARKTRACK) != 0) {
					paintBox(t[1], thepainter, c, false);
				}
			}
		}
		return t;
	}

	private void addEditMode(int flag) {
		editMode |= flag;
	}

	private void delEditMode(int flag) {
		editMode &= ~flag;
	}

	private void defaultEditMode() {
		if (addedit) {
			editMode = EDM_DEFAULTADD;
		} else {
			editMode = EDM_DEFAULT;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void mousePressed(MouseEvent e, painter thepainter, gamearea thegame) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			connectTrack1 = null;
			connectTrack2 = null;
			newTrack = true;
			int x = e.getX();
			int y = e.getY();
			int tx = thepainter.translateS2T_X(x);
			int ty = thepainter.translateS2T_Y(y);
			mousex = tx;
			mousey = ty;
			track[] t = checkTrackPerm(thepainter, tx, ty, false);

			if ((editMode & EM_SECONDTRACK) != 0) {
				editTrack2 = t[0];
				dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			} else if (t[0] != null) {
				editTrack = t[0];
				dataCollector.collector.editorEvent(EditorEvent.TRACK_SELECTED, editTrack);
				trackPainter tp = editTrack.getPainter();
				if (tp instanceof simpleTrack) {
					next_tp = (simpleTrack) tp;
					dataCollector.collector.editorEvent(EditorEvent.TRACK_SIMPLEPAINTER, next_tp);
				}
			} else {
				if (t[1] == null && addedit && (editMode & EM_ADDTRACK) != 0) { // new track
					editTrack = new track(tx - track.STARTLENGTH / 2, ty, 0, 0, track.STARTLENGTH);
					editTrack.setLevel(nextLevel);
					editTrack.setPainter(next_tp.clone());
					editTrack.setProgressToWork();
					thepainter.addTrack(editTrack);
					dataCollector.collector.editorEvent(EditorEvent.TRACK_SELECTED, editTrack);
				} else {
					editTrack = null;
					dataCollector.collector.editorEvent(EditorEvent.TRACK_UNSELECTED);
				}
			}

			if (editTrack != null && ((editMode & EM_MODEBMR) != 0) && !editTrack.isUsed()) {
				defaultEditMode();
				rectangle sr = editTrack.getStartArea();
				rectangle er = editTrack.getEndArea();
				if (sr.contains(tx, ty)) {
					setCursor(CS_ROTATE, thegame);
					addEditMode(EM_ROTATE);
				} else if (er.contains(tx, ty)) {
					setCursor(CS_BOW, thegame);
					addEditMode(EM_BOW);
				} else {
					setCursor(CS_MOVE, thegame);
					addEditMode(EM_MOVE);
				}
			} else if (editTrack2 != null && (editMode & EM_CONNECT2) != 0) {
				setCursor(CS_WAIT, thegame);
				point p1 = editTrack.getXY();
				point p2 = editTrack2.getXY();
				double d = p1.distance(p2);
				if (d > track.MAXLENGTH * 10) {
					dataCollector.collector.setStatus("Entfernung zum Startgleis zu groß, verbinden nicht möglich!");
				} else {
					dataCollector.collector.setStatus("Verbindung wird berechnet...");

					progressDialog pd = new progressDialog(dataCollector.collector.themain, "berechnen...");
					pd.open(new progressDialogRunnable() {
						@Override
						public void run(progressDialog pd) {
							setCursor(CS_WAIT, dataCollector.collector.thegame);
							track et = editTrack;
							int r;
							int c = 0;
							do {
								++c;
								pd.setText("berechne Durchlauf " + c);
								r = et.connectByCalc(editTrack2);
								if (r == 2) {
									track ot = et;
									et = new track(et);
									trackPainter tp = ot.getPainter();
									if (tp instanceof simpleTrack) {
										et.setPainter(tp.clone());
									} else {
										et.setPainter(next_tp.clone());
									}
									et.moveNext(ot);
									dataCollector.collector.thepainter.addTrack(et);
									dataCollector.collector.thepainter.paintOffTrackDelayed();
								}
							} while (r > 1);
							pd.close();
							if (r == 0) {
								dataCollector.collector.setStatus("Verbindung nicht möglich!");
							} else {
								dataCollector.collector.thepainter.paintOffTrack();
							}
						}
					});
				}
				dataCollector.collector.trackModified(editTrack);
				defaultEditMode();
				setCursor(CS_DEFAULT, thegame);
			}
			if ((editMode & EM_MARKSELECTEDTRACK) != 0) {
				paintBox(editTrack, thepainter, PM_SELECTED, false);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e, painter thepainter, gamearea thegame) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (editTrack != null && !newTrack) {
				int x = e.getX();
				int y = e.getY();
				int tx = thepainter.translateS2T_X(x);
				int ty = thepainter.translateS2T_Y(y);
				if ((editMode & EM_MOVE) != 0) {
					point xy = getXYbyCenter(editTrack, tx, ty);
					editTrack.move(xy);
					if (connectTrack1 != null) {
						switch (connectInfo1) {
							case 1:
								editTrack.movePrev(connectTrack1);
								thepainter.addTrack2Check(connectTrack1);
								break;
							case 2:
								editTrack.moveNext(connectTrack1);
								thepainter.addTrack2Check(connectTrack1);
								break;
						}
						connectTrack1 = null;
					}
					if (connectTrack2 != null) {
						switch (connectInfo2) {
							case 3:
								editTrack.connectPrev(connectTrack2);
								thepainter.addTrack2Check(connectTrack2);
								break;
							case 4:
								editTrack.connectNext(connectTrack2);
								thepainter.addTrack2Check(connectTrack2);
								break;
						}
						connectTrack2 = null;
					}
					editTrack.setProgressToWork();
					thepainter.addTrack2Check(editTrack);
					dataCollector.collector.trackMoved(editTrack);
					defaultEditMode();
					dataCollector.collector.editorEvent(EditorEvent.TRACK_MOVED, editTrack);
				} else if ((editMode & EM_ROTATE) != 0) {
					double rot = editTrack.getRotation() + (mousex - tx);
					editTrack.rotate(rot);
					editTrack.setProgressToWork();
					thepainter.addTrack2Check(editTrack);
					dataCollector.collector.trackModified(editTrack);
					defaultEditMode();
					dataCollector.collector.editorEvent(EditorEvent.TRACK_MOVED, editTrack);
				} else if ((editMode & EM_BOW) != 0) {
					editTrack.bowresize(tx, ty);
					if (connectTrack2 != null) {
						switch (connectInfo2) {
							case 3:
								editTrack.connectPrev(connectTrack2);
								thepainter.addTrack2Check(connectTrack2);
								break;
							case 4:
								editTrack.connectNext(connectTrack2);
								thepainter.addTrack2Check(connectTrack2);
								break;
						}
						connectTrack2 = null;
					}
					editTrack.setProgressToWork();
					thepainter.addTrack2Check(editTrack);
					dataCollector.collector.trackMoved(editTrack);
					defaultEditMode();
					dataCollector.collector.editorEvent(EditorEvent.TRACK_MOVED, editTrack);
				}

				paintBox(editTrack, thepainter, PM_SELECTED, true);
				thepainter.paintOffTrack();
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
		if (editTrack != null && !newTrack) {
			int x = e.getX();
			int y = e.getY();
			int tx = thepainter.translateS2T_X(x);
			int ty = thepainter.translateS2T_Y(y);

			if ((editMode & EM_MARKSELECTEDTRACK) != 0) {
				paintBox(editTrack, thepainter, PM_SELECTED, true);
			} else {
				thepainter.clearDrawArea();
			}

			if ((editMode & EM_MOVE) != 0) {
				point xy = getXYbyCenter(editTrack, tx, ty);
				track et = new track(editTrack, xy);
				et.paintTrack(thepainter.getDrawArea());
				paintBox(et, thepainter, PM_EDITING, false);

				connectInfo1 = 0;
				connectInfo2 = 0;
				thepainter.tracks.readLock();
				for (track t : thepainter.tracks) {
					if (t != editTrack) {
						if (t.isEndFree() && et.getStartArea().intersects(t.getEndArea()) && !t.isConnectedTo(connectTrack2) && t.canEndConnectStart(et)) {
							connectInfo1 = 2;
							connectTrack1 = t;
							paintBox(t, thepainter, PM_CONNECT, false);
							continue;
						} else if (t.isStartFree() && et.getStartArea().intersects(t.getStartArea()) && !t.isConnectedTo(connectTrack2) && t.canStartConnectStart(et)) {
							connectInfo1 = 1;
							connectTrack1 = t;
							paintBox(t, thepainter, PM_CONNECT, false);
							continue;
						}
						if (t.isStartFree() && et.getEndArea().intersects(t.getStartArea()) && !t.isConnectedTo(connectTrack1) && t.canStartConnectEnd(et)) {
							connectInfo2 = 3;
							connectTrack2 = t;
							paintBox(t, thepainter, PM_CONNECT, false);
							continue;
						} else if (t.isEndFree() && et.getEndArea().intersects(t.getEndArea()) && !t.isConnectedTo(connectTrack1) && t.canEndConnectEnd(et)) {
							connectInfo2 = 4;
							connectTrack2 = t;
							paintBox(t, thepainter, PM_CONNECT, false);
							continue;
						}
					}
				}
				thepainter.tracks.readUnlock();
				dataCollector.collector.editorEvent(EditorEvent.TRACK_MOVING, et);
				et.remove();
			} else if ((editMode & EM_ROTATE) != 0) {
				double rot = editTrack.getRotation() + (mousex - tx);

				track et = new track(editTrack, rot);
				et.paintTrack(thepainter.getDrawArea());
				//paintBox(et,thepainter,PM_EDITING,false);
				dataCollector.collector.editorEvent(EditorEvent.TRACK_ROTATING, et);
				et.remove();
			} else if ((editMode & EM_BOW) != 0) {
				track et = new track(editTrack);
				et.bowresize(tx, ty);
				et.paintTrack(thepainter.getDrawArea());

				connectInfo2 = 0;
				thepainter.tracks.readLock();
				for (track t : thepainter.tracks) {
					if (t != editTrack) {
						if (t.isStartFree() && et.getEndArea().intersects(t.getStartArea()) && !t.isConnectedTo(connectTrack1) && t.canStartConnectEnd(et)) {
							connectInfo2 = 3;
							connectTrack2 = t;
							paintBox(t, thepainter, PM_CONNECT, false);
							continue;
						} else if (t.isEndFree() && et.getEndArea().intersects(t.getEndArea()) && !t.isConnectedTo(connectTrack1) && t.canEndConnectEnd(et)) {
							connectInfo2 = 4;
							connectTrack2 = t;
							paintBox(t, thepainter, PM_CONNECT, false);
							continue;
						}
					}
				}
				thepainter.tracks.readUnlock();
				dataCollector.collector.editorEvent(EditorEvent.TRACK_BOWING, et);
				et.remove();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e, painter thepainter, gamearea thegame) {
		int x = e.getX();
		int y = e.getY();
		int tx = thepainter.translateS2T_X(x);
		int ty = thepainter.translateS2T_Y(y);

		track[] t = checkTrackPerm(thepainter, tx, ty, true);
		if ((editMode & EM_MARKSELECTEDTRACK) != 0) {
			paintBox(editTrack, thepainter, PM_SELECTED, false);
		}

		if (t[0] != null) {
			if (editTrack == t[0] && (editMode & EM_MODEBMR) != 0 && (editMode & EM_SECONDTRACK) == 0) {
				rectangle sr = editTrack.getStartArea();
				rectangle er = editTrack.getEndArea();
				if (sr.contains(tx, ty)) {
					setCursor(CS_ROTATE, thegame);
				} else if (er.contains(tx, ty)) {
					setCursor(CS_BOW, thegame);
				} else {
					setCursor(CS_MOVE, thegame);
				}
			} else if (t[0] != null && t[0] != editTrack) {
				if ((editMode & EM_SECONDTRACK) != 0) {
					setCursor(CS_DESTINATION, thegame);
				} else {
					setCursor(CS_DEFAULT, thegame);
				}
			} else {
				if ((editMode & EM_SECONDTRACK) != 0) {
					setCursor(CS_DESTINATION, thegame);
				} else {
					setCursor(CS_DEFAULT, thegame);
				}
			}
		} else {
			setCursor(CS_DEFAULT, thegame);
		}

		if ((editMode & EM_CONNECT2) != 0) {
			paintLine(editTrack, thepainter, tx, ty);
			if (t[0] != null) {
				point p1 = editTrack.getXY();
				point p2 = t[0].getXY();
				double d = p1.distance(p2);
				if (d > track.MAXLENGTH * 10) {
					dataCollector.collector.setStatus("Entfernung zum Startgleis zu groß, verbinden nicht möglich!");
				} else {
					dataCollector.collector.unsetStatus("Entfernung zum Startgleis zu groß, verbinden nicht möglich!");
				}
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void setSelected(boolean b, painter thepainter, gamearea thegame) {
		if (b) {
			thepainter.clearDrawArea();
			editTrack = null;
			newTrack = false;
			dataCollector.collector.editorEvent(EditorEvent.TRACK_UNSELECTED);
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			thepainter.setShowHiddenTrackObjects(true);
		} else {
			thepainter.clearDrawArea();
			dataCollector.collector.editorEvent(EditorEvent.TRACK_UNSELECTED);
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			thepainter.setShowHiddenTrackObjects(false);
		}
	}

	@Override
	public void paint(painter thepainter, gamearea thegame) {
		if (editTrack != null) {
			paintBox(editTrack, thepainter, PM_SELECTED, false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public boolean runAction(EditorActionEvent action, painter thepainter, gamearea thegame) { // TODO: neue editMode Flags
		String a = action.getAction();

		if (a.compareTo("del") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			if (editTrack.isUsed()) {
				dataCollector.collector.setStatus("Gleis ist Teil einer Fahrstraße!");
			} else {
				track t = editTrack.getPrev();
				if (t == null) {
					t = editTrack.getNext();
				}
				thepainter.delTrack(editTrack);
				editTrack = t;
				thepainter.clearDrawArea();
				if (editTrack == null) {
					dataCollector.collector.editorEvent(EditorEvent.TRACK_UNSELECTED);
				} else {
					dataCollector.collector.editorEvent(EditorEvent.TRACK_SELECTED, editTrack);
					paint(thepainter, thegame);
				}
			}
		} else if (a.compareTo("append") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			if (editTrack.isUsed()) {
				dataCollector.collector.setStatus("Gleis ist Teil einer Fahrstraße!");
			} else {
				if (editTrack.isEndFree()) {
					track t = new track(editTrack);
					t.setPainter(next_tp.clone());
					t.moveNext(editTrack);
					t.setProgressToWork();
					thepainter.addTrack(t);
					editTrack = t;
					dataCollector.collector.editorEvent(EditorEvent.TRACK_SELECTED, editTrack);
					thepainter.clearDrawArea();
					paint(thepainter, thegame);
				}
			}
		} else if (a.startsWith("copyleft")
				|| a.startsWith("copyright")) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			if (!editTrack.isFree()) {
				dataCollector.collector.setStatus("Gleis ist für einen Zug reserviert.");
			} else {
				int sign = a.startsWith("copyleft") ? -1 : 1;
				float v = 4.5f;
				if (a.endsWith("45")) {
					v = 4.5f;
				} else if (a.endsWith("50")) {
					v = 5.0f;
				} else if (a.endsWith("40")) {
					v = 4.0f;
				}

				track t = new track(editTrack);
				t.setPainter(next_tp.clone());
				t.transformRelativTo(editTrack, sign * (statics.meter2pixel(v) + 2 * track.RAILRADIUS));
				t.setProgressToWork();
				connectDirect(t);
				thepainter.addTrack(t);
				editTrack = t;
				dataCollector.collector.editorEvent(EditorEvent.TRACK_SELECTED, editTrack);
				thepainter.clearDrawArea();
				paint(thepainter, thegame);
			}
		} else if (a.compareTo("junction") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			if (!editTrack.isFree()) {
				dataCollector.collector.setStatus("Gleis ist für einen Zug reserviert.");
			} else if (editTrack.hasObject()) {
				dataCollector.collector.setStatus("Gleisobjekt auf dem Gleis, Weiche nicht empfohlen!");
			} else {
				int c = editTrack.getJunctionCount();
				int p = editTrack.getJunction();
				int fc = 0;
				for (int j = 0; j < c; ++j) {
					if (editTrack.getNext(j) == null) {
						++fc;
					}
				}
				if (fc > 0) {
					double w = editTrack.getNext(0).getBow();
					if (w < 0 && -w + 18 < 90) {
						w = -w + 18;
					} else if (-w - 18 > -90) {
						w = -w - 18;
					} else {
						w = 0;
					}
					track t = new track(editTrack, w, track.STARTLENGTH);
					t.setPainter(next_tp.clone());
					t.setLevel(nextLevel);
					t.setProgressToWork();

					thepainter.addTrack(t);
					editTrack = t;
					dataCollector.collector.editorEvent(EditorEvent.TRACK_SELECTED, editTrack);
					thepainter.clearDrawArea();
					paint(thepainter, thegame);
				}
			}
		} else if (a.compareTo("switch") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			if (editTrack.isUsed()) {
				dataCollector.collector.setStatus("Gleis ist Teil einer Fahrstraße!");
			} else {
				int p = editTrack.getJunction() + 1;
				if (p >= editTrack.getJunctionCount()) {
					p = 0;
				}
				editTrack.setJunction(p);
				//thepainter.paintOffTrack();
				dataCollector.collector.editorEvent(EditorEvent.TRACK_VALUE_CHANGED, editTrack);
				//System.out.println(editTrack.dumpString());
			}
		} else if (a.compareTo("turnaround") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			if (!editTrack.isFree()) {
				dataCollector.collector.setStatus("Gleis ist für einen Zug reserviert.");
			} else {
				editTrack.turnAround();
				thepainter.paintOffTrackDelayed();
				thepainter.clearDrawArea();
				paint(thepainter, thegame);
				dataCollector.collector.editorEvent(EditorEvent.TRACK_VALUE_CHANGED, editTrack);
			}
		} else if (a.compareTo("level") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			nextLevel = (Integer) action.getValue();
			if (editTrack != null) {
				editTrack.setLevel(nextLevel);
				thepainter.paintOffTrackDelayed();
				dataCollector.collector.editorEvent(EditorEvent.TRACK_VALUE_CHANGED, editTrack);
			}
		} else if (a.compareTo("edit-only") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			addedit = false;
			defaultEditMode();
		} else if (a.compareTo("add+edit") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			addedit = true;
			defaultEditMode();
		} else if (a.compareTo("new track") == 0) { // new track
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			int tx, ty;
			tx = thepainter.translateS2T_X(thepainter.getImgWidth() / 2);
			ty = thepainter.translateS2T_Y(thepainter.getImgHeight() / 2);
			editTrack = new track(tx - track.STARTLENGTH / 2, ty, 0, 0, track.STARTLENGTH);
			editTrack.setLevel(nextLevel);
			editTrack.setPainter(next_tp.clone());
			editTrack.setProgressToWork();
			thepainter.addTrack(editTrack);

			dataCollector.collector.editorEvent(EditorEvent.TRACK_SELECTED, editTrack);
			thepainter.clearDrawArea();
			paint(thepainter, thegame);
		} else if (a.compareTo("embankment") == 0 || a.compareTo("sleeper") == 0 || a.compareTo("rail") == 0 || a.compareTo("above") == 0) {
			dataCollector.collector.editorEvent(EditorEvent.TRACK_2TRACKACTIONFINISHED);
			defaultEditMode();
			setPainter((String) action.getValue());
			thepainter.paintOffTrack();
		} else if (a.compareTo("connect2") == 0) {
			if ((Boolean) action.getValue()) {
				editMode = EDM_CONNECT2;
				dataCollector.collector.setStatus("Bitte 2. Gleis auswählen.");
			} else {
				defaultEditMode();
			}
		}
		return true;
	}

	private void setPainter(String v) {
		String[] p = v.split(",");
		if (editTrack != null) {
			trackPainter tp = editTrack.getPainter();
			if (tp instanceof simpleTrack) {
				next_tp = (simpleTrack) tp;
			}
			int i;
			for (i = 0; i < p.length; ++i) {
				next_tp.setPainterData(p[i]);
			}
			editTrack.setPainter(next_tp.clone());
			dataCollector.collector.editorEvent(EditorEvent.TRACK_SIMPLEPAINTER, next_tp);
		} else {
			int i;
			for (i = 0; i < p.length; ++i) {
				next_tp.setPainterData(p[i]);
			}
			dataCollector.collector.editorEvent(EditorEvent.TRACK_SIMPLEPAINTER, next_tp);
		}
	}

	private int connectDirect(track et) {
		int ret = 0;
		for (track t : dataCollector.collector.thepainter.tracks) {
			if (t != et) {
				if (t.canEndConnectStart_strict(et)) {
					t.connectN2P(et);
					ret++;
				} else if (t.canStartConnectStart_strict(et)) {
					t.connectP2P(et);
					ret++;
				} else if (t.canStartConnectEnd_strict(et)) {
					t.connectP2N(et);
					ret++;
				} else if (t.canEndConnectEnd_strict(et)) {
					t.connectN2N(et);
					ret++;
				}
			}
		}
		return ret;
	}
}
