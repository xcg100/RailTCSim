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

import java.awt.event.*;

import org.railsim.*;
import org.railsim.service.trackObjects.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class traineditor extends editorbase {

	int mousex;
	int mousey;

	public traineditor() {
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
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void setSelected(boolean b, painter thepainter, gamearea thegame) {
		if (b) {
		} else {
		}
	}

	@Override
	public void paint(painter thepainter, gamearea thegame) {
	}

	@Override
	public void keyTyped(KeyEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public boolean runAction(EditorActionEvent action, painter thepainter, gamearea thegame) {
		String a = action.getAction();
		if (a.compareTo("renamefulltrain") == 0) {
			EditorActionEvent2 e = (EditorActionEvent2) action;
			if (e.getValue() != null) {
				//((trackObject)e.getValue()).setName((String) e.getValue2());
				//dataCollector.collector.editorEvent(EditorEvent.PATHEDIT_PATHABLE_RENAMED,(trackObject)e.getValue());
			}
		} else if (a.compareTo("showTrain") == 0) {
			fulltrain ft = (fulltrain) action.getValue();
			thepainter.showTrain(ft);
		} else if (a.compareTo("showSignal") == 0) {
			pathableObject po = (pathableObject) action.getValue();
			thepainter.showTrackObject(po);
		}

		return true;
	}
}
