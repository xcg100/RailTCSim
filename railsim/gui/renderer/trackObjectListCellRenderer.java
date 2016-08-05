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

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.railsim.service.trackObjects.trackObject;

/**
 *
 * @author js
 * @deprecated use genericaPainterInterfaceComboBoxRenderer
 *
 */
public class trackObjectListCellRenderer extends BasicComboBoxRenderer {

	static final public int MD_TYPE = 0;
	static final public int MD_NAME = 1;
	int mode = 0;

	/**
	 * Creates a new instance of trackObjectListCellRenderer
	 */
	public trackObjectListCellRenderer(int m) {
		mode = m;
	}

	@Override
	public Component getListCellRendererComponent(JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		Object v = value;
		Component c;
		if (v instanceof trackObject) {
			if (mode == MD_NAME) {
				v = ((trackObject) v).getRegion() + "/" + ((trackObject) v).getName();
			}
		}
		c = super.getListCellRendererComponent(list, v, index, isSelected, cellHasFocus);
		return c;
	}
}
