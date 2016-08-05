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

/**
 *
 * @author js
 */
public class testeditor extends editorbase {

	int x1 = -1, y1 = -1;

	@Override
	public void mouseClicked(MouseEvent e, painter thepainter, gamearea thegame) {
		int x2 = e.getX();
		int y2 = e.getY();
		if (x1 >= 0) {
			Graphics2D g = thepainter.getDrawArea();
			g.setColor(Color.BLACK);
			g.drawLine(x1, y1, x2, y2);
		}
		x1 = x2;
		y1 = y2;
	}

	@Override
	public void mousePressed(MouseEvent e, painter thepainter, gamearea thegame) {
	}

	@Override
	public void mouseReleased(MouseEvent e, painter thepainter, gamearea thegame) {
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
			x1 = -1;
			y1 = -1;
		} else {
			thepainter.clearDrawArea();
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
		return true;
	}
}
