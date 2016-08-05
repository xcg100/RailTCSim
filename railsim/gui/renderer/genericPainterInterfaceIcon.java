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

/**
 *
 * @author js
 */
public class genericPainterInterfaceIcon implements Icon {

	private int width;
	private int height;
	private genericPaintInterface gpi;
	static final int DEFAULT_WIDTH = 40;
	static final int DEFAULT_HEIGHT = 15;

	public genericPainterInterfaceIcon() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public genericPainterInterfaceIcon(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public int getIconHeight() {
		return height;
	}

	@Override
	public int getIconWidth() {
		return width;
	}

	public void setGenericPainter(genericPaintInterface g) {
		gpi = g;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D bgfx = (Graphics2D) g.create(x, y, width, height);
		//bgfx.setBackground(Color.WHITE);
		bgfx.setBackground(c.getBackground());
		bgfx.clearRect(0, 0, width, height);
		int o = gpi.getPaintOrientation();
		switch (o) {
			case 0:
				bgfx.translate(width - 5, 0);
				bgfx.rotate(Math.PI / 2);
				break;
			case 2:
				bgfx.translate(0, height);
				bgfx.rotate(-Math.PI / 2);
				break;
			case 3:
				bgfx.translate(width - 5, height);
				bgfx.rotate(Math.PI);
				break;
			case 1:
			default:
				break;
		}
		gpi.paintIcon(bgfx);
	}
}
