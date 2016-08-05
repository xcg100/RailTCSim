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
package org.railsim.service.trackObjects.objects.graphics;

import java.awt.*;

/**
 *
 * @author js
 */
public class hvsignal_pre extends hvsignal_base {

	static Polygon border;
	static Polygon add;

	static {
		border = new Polygon();
		border.addPoint(scale * 4, 0);
		border.addPoint(0, scale * 4);
		border.addPoint(0, scale * 5);
		border.addPoint(scale, scale * 6);
		border.addPoint(scale * 3, scale * 6);
		border.addPoint(scale * 7, scale * 2);
		border.addPoint(scale * 7, scale * 1);
		border.addPoint(scale * 6, 0);

		add = new Polygon();
		add.addPoint(scale * 3, scale * 2);
		add.addPoint(scale, scale2);
		add.addPoint(scale2, scale);
		add.addPoint(scale * 1, scale * 3);
	}
	boolean tooff = false;
	boolean tored = true;
	boolean slow = false;
	boolean shortdist = false;
	boolean Cy1 = false;
	boolean Cy2 = false;
	boolean Cg1 = false;
	boolean Cg2 = false;

	/**
	 * Creates a new instance of hvsignal_main
	 */
	public hvsignal_pre(int _type) {
		super(_type, scale * 7 + scale2, scale * 6);
	}

	public void setRed(boolean b) {
		tored = b;
	}

	public void setSlow(boolean b) {
		slow = b;
	}

	public void setShort(boolean b) {
		shortdist = b;
	}

	public boolean getRed() {
		return tored;
	}

	public boolean getSlow() {
		return slow;
	}

	private void paintWhite(Graphics2D g, int a) {
		g.setColor(col_whiteanim[a]);
		g.fillOval(scale, scale, scale, scale);
	}

	private void paintGreen1(Graphics2D g, int a) {
		g.setColor(col_gruenanim[a]);
		g.fillOval(scale * 6 - scale2, scale * 1, scale + scale3, scale + scale3);
	}

	private void paintGreen2(Graphics2D g, int a) {
		g.setColor(col_gruenanim[a]);
		g.fillOval(scale * 3 - scale2, scale * 4, scale + scale3, scale + scale3);
	}

	private void paintYellow1(Graphics2D g, int a) {
		g.setColor(col_yellowanim[a]);
		g.fillOval(scale * 4 - scale2, scale * 1, scale + scale3, scale + scale3);
	}

	private void paintYellow2(Graphics2D g, int a) {
		g.setColor(col_yellowanim[a]);
		g.fillOval(scale * 1 - scale2, scale * 4, scale + scale3, scale + scale3);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.draw(border);
		g.fill(border);

		if (shortdist) {
			g.draw(add);
			g.fill(add);
		}

		paintAnim(g, 0);
	}

	@Override
	public void paintAnim(Graphics2D g2, int count) {
		if (g2 != null) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			boolean Ly1 = Cy1;
			boolean Ly2 = Cy2;
			boolean Lg1 = Cg1;
			boolean Lg2 = Cg2;

			if (tooff) {
				Ly1 = false;
				Ly2 = false;
				Lg1 = false;
				Lg2 = false;
			} else if (tored) {
				Ly1 = true;
				Ly2 = true;
				Lg1 = false;
				Lg2 = false;
			} else {
				switch (type) {
					case T_GREEN:
						Ly1 = false;
						Ly2 = false;
						Lg1 = true;
						Lg2 = true;
						break;
					case T_GREENYELLOW:
						Ly1 = false;
						Ly2 = true;
						Lg1 = true;
						Lg2 = false;
						break;
					case T_GREENYELLOWGREEN:
						Ly1 = false;
						Ly2 = slow;
						Lg1 = true;
						Lg2 = !slow;
						break;
				}
			}

			paintYellow1(g2, calcIndex(Cy1, Ly1, count));
			paintYellow2(g2, calcIndex(Cy2, Ly2, count));
			paintGreen1(g2, calcIndex(Cg1, Lg1, count));
			paintGreen2(g2, calcIndex(Cg2, Lg2, count));

			if (count == 0) {
				Cy1 = Ly1;
				Cy2 = Ly2;
				Cg1 = Lg1;
				Cg2 = Lg2;
			}
			if (shortdist) {
				paintWhite(g2, 0);
			}
		}
	}

	@Override
	public void setState(int s) {
		if (s == S_OFF) {
			tooff = true;
		}
		if (s == S_RED) {
			tored = true;
			tooff = false;
		} else if (s == S_GREEN) {
			tored = false;
			slow = false;
			tooff = false;
		} else if (s == S_YELLOW) {
			tored = false;
			slow = true;
			tooff = false;
		}
	}
}
