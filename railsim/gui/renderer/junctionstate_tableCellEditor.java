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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * integer_tableCellEditor
 *
 * @author js
 */
public class junctionstate_tableCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer //extends JComboBox implements TableCellEditor
{

	JToggleButton cb_w;
	JToggleButton cb_r;
	JLabel lb_r;
	int min = 0;
	int max = 10;

	/**
	 * Creates a new instance of route_tableCellRenderer
	 */
	public junctionstate_tableCellEditor() {
		cb_w = new JCheckBox();
		cb_r = new JCheckBox();
		lb_r = new JLabel();
		cb_w.setText("abzweigen");
		cb_r.setText("abzweigen");
		cb_w.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopCellEditing();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Component r = null;
		if (value != null && value instanceof Boolean) {
			cb_w.setSelected((Boolean) value);
			r = cb_w;
		} else {
			r = lb_r;
		}

		if (isSelected) {
			r.setBackground(table.getSelectionBackground());
			r.setForeground(table.getSelectionForeground());
		} else {
			r.setBackground(table.getBackground());
			r.setForeground(table.getForeground());
		}
		//table.setRowHeight(row,(int)r.getMinimumSize().getHeight());
		return r;
	}

	@Override
	public Object getCellEditorValue() {
		return cb_w.isSelected();
	}

	@Override
	public boolean isCellEditable(EventObject evt) {
		/*if (evt instanceof MouseEvent)
		 {
		 return ((MouseEvent)evt).getClickCount() >= 2;
		 }*/
		return true;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component r = null;
		if (value != null && value instanceof Boolean) {
			cb_r.setSelected((Boolean) value);
			r = cb_r;
		} else {
			r = lb_r;
		}

		if (isSelected) {
			r.setBackground(table.getSelectionBackground());
			r.setForeground(table.getSelectionForeground());
		} else {
			r.setBackground(table.getBackground());
			r.setForeground(table.getForeground());
		}
		//table.setRowHeight(row,(int)r.getMinimumSize().getHeight());
		return r;
	}
}
