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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.railsim.service.path;
import org.railsim.service.trackObjects.pathableObject;
import org.railsim.service.trackObjects.trackObject;

/**
 *
 * @author js
 */
public class treeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer {

	/**
	 * Creates a new instance of treeCellRenderer
	 */
	public treeCellRenderer() {
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
			if (u instanceof trackObject) {
				trackObject to = (trackObject) u;
				String s = to.getName();
				if (u instanceof pathableObject && !((pathableObject) u).isEnabled()) {
					s += " (abgeschaltet)";
				}
				v2 = s;
			} else if (u instanceof path) {
				String s = ((path) u).getName();
				if (!((path) u).isEnabled()) {
					s += " ABGESCHALTET";
				}
				if (((path) u).isAutomatic()) {
					s += " (automatisch erzeugt)";
				}
				v2 = s;
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
