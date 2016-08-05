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
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * integer_tableCellEditor
 *
 * @author js
 */
public class integer_tableCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer //extends JComboBox implements TableCellEditor
{

	JSpinner spn_w;
	JSpinner spn_r;
	int min = 0;
	int max = 10;

	/**
	 * Creates a new instance of route_tableCellRenderer
	 */
	public integer_tableCellEditor(int _min, int _max) {
		min = _min;
		max = _max;
		spn_w = new JSpinner(new SpinnerNumberModel(min, //initial value
				min, max, 1));
		spn_r = new JSpinner(new SpinnerNumberModel(min, //initial value
				min, max, 1));
		spn_w.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				stopCellEditing();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof Integer) {
			spn_w.setValue(value);
		} else {
			spn_w.setValue(min);
		}

		if (isSelected) {
			spn_w.setBackground(table.getSelectionBackground());
			spn_w.setForeground(table.getSelectionForeground());
		} else {
			spn_w.setBackground(table.getBackground());
			spn_w.setForeground(table.getForeground());
		}
		//table.setRowHeight(row,22);
		return spn_w;
	}

	@Override
	public Object getCellEditorValue() {
		return spn_w.getValue();
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
		if (value instanceof Integer) {
			spn_r.setValue(value);
		} else {
			spn_r.setValue(min);
		}

		if (isSelected) {
			spn_r.setBackground(table.getSelectionBackground());
			spn_r.setForeground(table.getSelectionForeground());
		} else {
			spn_r.setBackground(table.getBackground());
			spn_r.setForeground(table.getForeground());
		}
		//table.setRowHeight(row,22);
		return spn_r;
	}
}
