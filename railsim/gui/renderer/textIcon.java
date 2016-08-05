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
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

/**
 *
 * @author js
 */
public class textIcon implements Icon {

	String text;
	Font font;
	int width = 0;
	int height = 0;

	public textIcon(String text) {
		this.text = text;
		font = new Font("Serif", Font.PLAIN, 12);
		width = text.length() * 10;
		height = 12;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D b = font.getStringBounds(text, frc);
		width = (int) b.getWidth();
		height = (int) b.getHeight();
		LineMetrics lm = font.getLineMetrics(text, frc);
		y += (int) lm.getAscent();
		g2.drawString(text, x, y);
	}

	@Override
	public int getIconWidth() {
		return width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}
}