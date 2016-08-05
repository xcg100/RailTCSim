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
package org.railsim.gui;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.BevelBorder;

/**
 *
 * @author js
 */
public class MainBarItemBorder extends BevelBorder {

	/**
	 * Creates a bevel border with the specified type and whose
	 * colors will be derived from the background color of the
	 * component passed into the paintBorder method.
	 *
	 * @param bevelType the type of bevel for the border
	 */
	public MainBarItemBorder(int bevelType) {
		super(bevelType);
	}

	/**
	 * Creates a bevel border with the specified type, highlight and
	 * shadow colors.
	 *
	 * @param bevelType the type of bevel for the border
	 * @param highlight the color to use for the bevel highlight
	 * @param shadow the color to use for the bevel shadow
	 */
	public MainBarItemBorder(int bevelType, Color highlight, Color shadow) {
		super(bevelType, highlight, shadow);
	}

	/**
	 * Creates a bevel border with the specified type, highlight
	 * shadow colors.
	 *
	 * @param bevelType the type of bevel for the border
	 * @param highlightOuterColor the color to use for the bevel outer highlight
	 * @param highlightInnerColor the color to use for the bevel inner highlight
	 * @param shadowOuterColor the color to use for the bevel outer shadow
	 * @param shadowInnerColor the color to use for the bevel inner shadow
	 */
	public MainBarItemBorder(int bevelType, Color highlightOuterColor,
			Color highlightInnerColor, Color shadowOuterColor,
			Color shadowInnerColor) {
		super(bevelType, highlightOuterColor, highlightInnerColor,
				shadowOuterColor, shadowInnerColor);
	}

	/**
	 * Paints the border for the specified component with the specified
	 * position and size.
	 *
	 * @param c the component for which this border is being painted
	 * @param g the paint graphics
	 * @param x the x position of the painted border
	 * @param y the y position of the painted border
	 * @param width the width of the painted border
	 * @param height the height of the painted border
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Color oldColor = g.getColor();
		g.translate(x, y);

		if (bevelType == RAISED) {
			g.setColor(getHighlightOuterColor(c));
			g.drawLine(0, 0, width - 2, 0);
			g.drawLine(0, 0, 0, height - 2);
			g.drawLine(1, 1, 1, 1);

			g.setColor(getHighlightInnerColor(c));
			g.drawLine(2, 1, width - 2, 1);
			g.drawLine(1, 2, 1, height - 2);
			g.drawLine(2, 2, 2, 2);
			//g.drawLine(0, height-1, 0, height-2);
			g.drawLine(width - 1, 0, width - 1, 0);

			g.setColor(getShadowOuterColor(c));
			//g.drawLine(2, height-1, width-1, height-1);
			g.drawLine(width - 1, 2, width - 1, height - 1);

			//g.setColor(getShadowInnerColor(c));
			//g.drawLine(width-2, height-2, width-2, height-2);


		} else if (bevelType == LOWERED) {
			g.setColor(getShadowOuterColor(c));
			g.drawLine(0, 0, width - 2, 0);
			g.drawLine(0, 0, 0, height - 2);
			g.drawLine(1, 1, 1, 1);

			g.setColor(getShadowInnerColor(c));
			g.drawLine(2, 1, width - 2, 1);
			g.drawLine(1, 2, 1, height - 2);
			g.drawLine(2, 2, 2, 2);
			g.drawLine(0, height - 1, 0, height - 2);
			g.drawLine(width - 1, 0, width - 1, 0);

			g.setColor(getHighlightOuterColor(c));
			g.drawLine(2, height - 1, width - 1, height - 1);
			g.drawLine(width - 1, 2, width - 1, height - 1);

			g.setColor(getHighlightInnerColor(c));
			g.drawLine(width - 2, height - 2, width - 2, height - 2);
		}
		g.translate(-x, -y);
		g.setColor(oldColor);
	}

	/**
	 * Returns the insets of the border.
	 *
	 * @param c the component for which this border insets value applies
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		return getBorderInsets(c, new Insets(0, 0, 0, 0));
	}

	/**
	 * Reinitialize the insets parameter with this Border's current Insets.
	 *
	 * @param c the component for which this border insets value applies
	 * @param insets the object to be reinitialized
	 */
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.top = insets.left = insets.bottom = insets.right = 3;
		return insets;
	}

	/**
	 * Returns whether or not the border is opaque.
	 */
	@Override
	public boolean isBorderOpaque() {
		return false;
	}
}
