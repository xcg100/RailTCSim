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
package org.railsim.gui.renderer;

import java.awt.Component;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import org.railsim.service.genericPaintInterface;

/**
 *
 * @author js
 */
public class stocktreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer {

	JPanel panel = new JPanel();
	JLabel imgfield = new JLabel();
	JLabel txtfield = new JLabel();
	genericPainterInterfaceIcon iicn = new genericPainterInterfaceIcon();

	/**
	 * Creates a new instance of treeCellRenderer
	 */
	public stocktreeCellRenderer() {
		panel.setOpaque(true);
		imgfield.setOpaque(true);
		txtfield.setOpaque(true);
		panel.setLayout(new java.awt.BorderLayout());
		panel.add(imgfield, java.awt.BorderLayout.WEST);
		panel.add(txtfield, java.awt.BorderLayout.CENTER);
		txtfield.setHorizontalAlignment(JLabel.LEFT);
		txtfield.setVerticalAlignment(JLabel.CENTER);
		imgfield.setHorizontalAlignment(JLabel.CENTER);
		imgfield.setVerticalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus) {
		Object v2 = value;
		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) value;
			Object u = dmtn.getUserObject();
			if (u instanceof genericPaintInterface) {
				if (sel) {
					panel.setBackground(this.getBackgroundSelectionColor());
					txtfield.setBackground(this.getBackgroundSelectionColor());
					txtfield.setForeground(this.getTextSelectionColor());
					imgfield.setBackground(this.getBackgroundSelectionColor());
				} else {
					panel.setBackground(this.getBackgroundNonSelectionColor());
					txtfield.setBackground(this.getBackgroundNonSelectionColor());
					txtfield.setForeground(this.getTextNonSelectionColor());
					imgfield.setBackground(this.getBackgroundNonSelectionColor());
				}
				iicn.setGenericPainter((genericPaintInterface) u);
				imgfield.setIcon(iicn);
				txtfield.setText(((genericPaintInterface) u).getLabel());
				return panel;
			}
		}
		return super.getTreeCellRendererComponent(tree,
				v2,
				sel,
				expanded,
				leaf,
				row,
				hasFocus);
	}
}
