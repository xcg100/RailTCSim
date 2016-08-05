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
package org.railsim.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;
import javax.swing.*;

/**
 *
 * @author js
 * @deprecated
 */
@Deprecated
public class listDropListener implements DropTargetListener {

	private Component oldGlassPane;
	private Point from, to;
	private DropTarget oldDropTarget = null;

	public listDropListener(DropTarget d) {
		oldDropTarget = d;
	}

	public listDropListener() {
	}
	// glasspane on which visual clues are drawn
	JPanel glassPane = new JPanel() {
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.red);
			if (from == null || to == null) {
				return;
			}
			int x1 = from.x;
			int x2 = to.x;
			int y1 = from.y;

			// line
			g.drawLine(x1 + 2, y1, x2 - 2, y1);
			g.drawLine(x1 + 2, y1 + 1, x2 - 2, y1 + 1);

			// right
			g.drawLine(x1, y1 - 2, x1, y1 + 3);
			g.drawLine(x1 + 1, y1 - 1, x1 + 1, y1 + 2);

			// left
			g.drawLine(x2, y1 - 2, x2, y1 + 3);
			g.drawLine(x2 - 1, y1 - 1, x2 - 1, y1 + 2);
		}
	};
	// size of hotspot used to find
	// the whether user wants to insert element
	private int hotspot = 5;
	// dropindex - subclasses can access this in to accept/reject drop
	protected int listIndex = -1;
	// null means replace element at listIndex
	// true means insert element before listIndex
	// false means insert element after listIndex
	// subclasses can access this in drop
	protected Boolean before = null;

	public int getInsertIndex() {
		return listIndex;
	}

	public boolean getInsertBefore() {
		return before == null ? true : before;
	}

	private void updateLine(JList list, Point pt) {
		listIndex = pt != null ? list.locationToIndex(pt) : -1;
		if (listIndex == -1) {
			from = to = null;
			before = null;
			list.clearSelection();
		} else {
			Rectangle bounds = list.getCellBounds(listIndex, listIndex);
			if (pt.y <= bounds.y + hotspot) {
				from = bounds.getLocation();
				to = new Point(list.getWidth(), from.y);
				before = Boolean.TRUE;
			} else if (pt.y >= bounds.y + bounds.height - hotspot) {
				from = new Point(bounds.x, bounds.y + bounds.height);
				to = new Point(list.getWidth(), from.y);
				before = Boolean.FALSE;
			} else {
				from = to = null;
				before = null;
			}
			if (from != null && to != null) {
				from = SwingUtilities.convertPoint(list, from, glassPane);
				to = SwingUtilities.convertPoint(list, to, glassPane);
				list.clearSelection();
			} else {
				list.setSelectedIndex(listIndex);
			}
		}
		glassPane.getRootPane().repaint();
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		System.out.println("dragEnter");
		JList list = (JList) dtde.getDropTargetContext().getComponent();
		Point location = dtde.getLocation();

		JRootPane rootPane = list.getRootPane();
		oldGlassPane = rootPane.getGlassPane();
		rootPane.setGlassPane(glassPane);
		glassPane.setOpaque(false);
		glassPane.setVisible(true);

		updateLine(list, location);

		if (oldDropTarget != null) {
			oldDropTarget.dragEnter(dtde);
		}
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		System.out.println("dragOver");
		JList list = (JList) dtde.getDropTargetContext().getComponent();
		Point location = dtde.getLocation();
		updateLine(list, location);

		if (oldDropTarget != null) {
			oldDropTarget.dragOver(dtde);
		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		System.out.println("dropActionChanged");
		if (oldDropTarget != null) {
			oldDropTarget.dropActionChanged(dtde);
		}
	}

	private void resetGlassPane(DropTargetEvent dte) {
		JList list = (JList) dte.getDropTargetContext().getComponent();

		JRootPane rootPane = list.getRootPane();
		rootPane.setGlassPane(oldGlassPane);
		oldGlassPane.setVisible(false);
		rootPane.repaint();
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		System.out.println("dragExit");
		resetGlassPane(dte);

		if (oldDropTarget != null) {
			oldDropTarget.dragExit(dte);
		}
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		System.out.println("drop");
		resetGlassPane(dtde);

		if (oldDropTarget != null) {
			oldDropTarget.drop(dtde);
		}
	}
}
