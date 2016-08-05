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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author js
 */
public class combobox_tableCellEditor extends AbstractCellEditor implements TableCellEditor //extends JComboBox implements TableCellEditor
{

	JComboBox cbox = new JComboBox();
	comboboxeditorfiller filler = null;

	/**
	 * Creates a new instance of route_tableCellRenderer
	 */
	public combobox_tableCellEditor(comboboxeditorfiller f) {
		filler = f;
		cbox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		cbox.setBorder(BorderFactory.createEmptyBorder());
		cbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbox.getSelectedItem() != null) {
					stopCellEditing();
				}
			}
		});
		cbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbox.getSelectedItem() != null) {
					stopCellEditing();
				}
			}
		});

	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		cbox.removeAllItems();
		filler.fill(cbox);

		cbox.setSelectedItem(value);
		if (value != null) {
			cbox.setToolTipText(value.toString());
		}
		if (isSelected) {
			cbox.setBackground(table.getSelectionBackground());
			cbox.setForeground(table.getSelectionForeground());
		} else {
			cbox.setBackground(table.getBackground());
			cbox.setForeground(table.getForeground());
			cancelCellEditing();
		}
		return cbox;
	}

	@Override
	public Object getCellEditorValue() {
		return cbox.getSelectedItem();
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}
}
