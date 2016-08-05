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

import java.awt.event.*;

import org.railsim.*;

/**
 *
 * @author js
 */
public abstract class editorbase {

	abstract public void mouseClicked(MouseEvent e, painter thepainter, gamearea thegame);

	abstract public void mousePressed(MouseEvent e, painter thepainter, gamearea thegame);

	abstract public void mouseReleased(MouseEvent e, painter thepainter, gamearea thegame);

	abstract public void mouseEntered(MouseEvent e, painter thepainter, gamearea thegame);

	abstract public void mouseExited(MouseEvent e, painter thepainter, gamearea thegame);

	abstract public void mouseDragged(MouseEvent e, painter thepainter, gamearea thegame);

	abstract public void mouseMoved(MouseEvent e, painter thepainter, gamearea thegame);

	abstract public void mouseWheelMoved(MouseWheelEvent e, painter thepainter, gamearea thegame);

	abstract public void keyTyped(KeyEvent e, painter thepainter, gamearea thegame);

	abstract public void setSelected(boolean b, painter thepainter, gamearea thegame);

	abstract public void paint(painter thepainter, gamearea thegame);

	abstract public boolean runAction(EditorActionEvent action, painter thepainter, gamearea thegame);
}
