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

import java.awt.*;

import javax.swing.*;

import org.railsim.service.genericPaintInterface;
import org.railsim.service.trackObjects.trackObject;

/**
 *
 * @author js
 */
public class genericPaintInterfaceComboBoxRenderer extends JPanel implements ListCellRenderer {

	static final public int MD_STANDARD = 0;
	static final public int MD_TYPE = 1;
	static final public int MD_NAME = 2;
	static final int WIDTH = 40;
	static final int HEIGHT = 15;
	int mode = 0;
	JLabel imgfield = new JLabel();
	JLabel txtfield = new JLabel();
	genericPainterInterfaceIcon iicn = new genericPainterInterfaceIcon();

	public genericPaintInterfaceComboBoxRenderer() {
		setOpaque(true);
		imgfield.setOpaque(true);
		txtfield.setOpaque(true);
		setLayout(new java.awt.BorderLayout());
		add(imgfield, java.awt.BorderLayout.WEST);
		add(txtfield, java.awt.BorderLayout.CENTER);
		txtfield.setHorizontalAlignment(JLabel.LEFT);
		txtfield.setVerticalAlignment(JLabel.CENTER);
		imgfield.setHorizontalAlignment(JLabel.CENTER);
		imgfield.setVerticalAlignment(JLabel.CENTER);
	}

	public genericPaintInterfaceComboBoxRenderer(int m) {
		this();
		mode = m;
	}

	/*
	 * This method finds the image and text corresponding
	 * to the selected value and returns the label, set up
	 * to display the text and image.
	 */
	@Override
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		genericPaintInterface to = null;
		if (value instanceof genericPaintInterface) {
			to = (genericPaintInterface) value;
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
			txtfield.setBackground(list.getSelectionBackground());
			txtfield.setForeground(list.getSelectionForeground());
			imgfield.setBackground(list.getSelectionBackground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
			txtfield.setBackground(list.getBackground());
			txtfield.setForeground(list.getForeground());
			imgfield.setBackground(list.getBackground());
		}
		if (to != null) {
			iicn.setGenericPainter(to);
			imgfield.setIcon(iicn);
			if (to instanceof trackObject && mode == MD_NAME) {
				txtfield.setText(((trackObject) to).getRegion() + "/" + ((trackObject) to).getName());
			} else {
				txtfield.setText(to.getLabel());
			}
		} else {
			imgfield.setIcon(null);
			if (value != null) {
				txtfield.setText(value.toString());
			} else {
				txtfield.setText("");
			}
		}
		return this;
	}
}
