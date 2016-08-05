/*
 * $Revision: 18 $
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
public abstract class hvsignal_base {

	public static final int ANIMSTEPS = 5;
	final static protected int scale = 3;
	public static final int ANIMSTARTCOUNTER = ANIMSTEPS * 4;
	final static protected int scale2 = scale / 2;
	final static protected int scale3 = scale / 3;
	static protected Color[] col_gruenanim;
	static protected Color[] col_yellowanim;
	static protected Color[] col_whiteanim;
	static protected Color[] col_redanim;

	static {
		int i;
		col_gruenanim = new Color[ANIMSTEPS];
		col_yellowanim = new Color[ANIMSTEPS];
		col_whiteanim = new Color[ANIMSTEPS];
		col_redanim = new Color[ANIMSTEPS];
		for (i = 0; i < ANIMSTEPS; ++i) {
			col_gruenanim[i] = new Color(0x11, 0xee - (i * (0xee - 0x33) / ANIMSTEPS), 0x11);
			col_yellowanim[i] = new Color(0xff - (i * (0xff - 0x33) / ANIMSTEPS), 0xff - (i * (0xff - 0x33) / ANIMSTEPS), 0x11);
			col_whiteanim[i] = new Color(0xff - (i * (0xff - 0x44) / ANIMSTEPS), 0xff - (i * (0xff - 0x44) / ANIMSTEPS), 0xff - (i * (0xff - 0x44) / ANIMSTEPS));
			col_redanim[i] = new Color(0xff - (i * (0xff - 0x44) / ANIMSTEPS), 0x11, 0x11);
		}
	}
	public static final int T_GREEN = 0;
	public static final int T_GREENYELLOW = 1;
	public static final int T_GREENYELLOWGREEN = 2;
	public static final int S_INIT = -2;
	public static final int S_OFF = -1;
	public static final int S_RED = 0;
	public static final int S_GREEN = 1;
	public static final int S_YELLOW = 2;
	protected int type = 0;
	protected int width = 0;
	protected int height = 0;

	/**
	 * Creates a new instance of hvsignal_main
	 */
	public hvsignal_base(int _type, int w, int h) {
		type = _type;
		width = w;
		height = h;
	}

	public abstract void paint(Graphics2D g);

	public abstract void paintAnim(Graphics2D g2, int count);

	public abstract void setState(int s);

	public void paintPylon(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(width / 2 - 1, height / 2, 2, 20);
		//g.fillRect(0, height/2+20, width, 4);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getHeightWithPylon() {
		return height / 2 + 20;
	}

	protected int calcIndex(boolean current, boolean dest, int count) {
		count /= 2;

		int n1 = ANIMSTEPS - 1; // on index
		int n2 = ANIMSTEPS - 1; // off index
		if (count > ANIMSTEPS) {
			n2 = ANIMSTEPS * 2 - count;   // fade off
		} else if (count < ANIMSTEPS) {
			n1 = count;   // fade on
		}
		if (current && !dest) {
			return n2;
		} else if (!current && dest) {
			return n1;
		} else if (current) {
			return 0;
		} else {
			return ANIMSTEPS - 1;
		}
	}
}
