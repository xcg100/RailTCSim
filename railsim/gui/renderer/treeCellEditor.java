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
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;

import org.railsim.service.trackObjects.trackObject;

/**
 *
 * @author js
 */
public class treeCellEditor extends javax.swing.tree.DefaultTreeCellEditor {

	public treeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
		super(tree, renderer);
	}

	public treeCellEditor(JTree tree, DefaultTreeCellRenderer renderer, TreeCellEditor editor) {
		super(tree, renderer, editor);
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row) {
		Object v2 = value;
		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) value;
			Object u = dmtn.getUserObject();
			if (u instanceof trackObject) {
				v2 = ((trackObject) u).getName();
			}
		}

		return super.getTreeCellEditorComponent(tree,
				v2,
				sel,
				expanded,
				leaf,
				row);
	}
}
