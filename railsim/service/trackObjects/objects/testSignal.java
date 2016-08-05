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
package org.railsim.service.trackObjects.objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.railsim.service.odsHashSet;
import org.railsim.service.track;
import org.railsim.service.trackObjects.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class testSignal extends pathableObject {

	static final int ANIMSTEPS = 5;
	static int sc = 0;
	static Color col_schwarz = new Color(0x00, 0x00, 0x00);
	static Color col_rotein = new Color(0xff, 0x11, 0x11);
	static Color col_rotaus = new Color(0x66, 0x11, 0x11);
	static Color col_gruenein = new Color(0x11, 0xee, 0x11);
	static Color col_gruenaus = new Color(0x11, 0x55, 0x11);
	static Color[] col_gruenanim;
	static Color[] col_redanim;

	static {
		int i;
		col_gruenanim = new Color[ANIMSTEPS];
		col_redanim = new Color[ANIMSTEPS];
		for (i = 0; i < ANIMSTEPS; ++i) {
			col_gruenanim[i] = new Color(0x11, 0xee - (i * (0xee - 0x55) / ANIMSTEPS), 0x11);
			col_redanim[i] = new Color(0xff - (i * (0xff - 0x66) / ANIMSTEPS), 0x11, 0x11);
		}
	}
	static int fscale = 8;
	static int TRACKDIST = 10 + track.RAILRADIUS;
	boolean isred = true;
	boolean tored = true;
	boolean autored = true;

	void setRed(boolean r) {
		tored = r;
		if (tored != isred) {
			registerAnim(null, ANIMSTEPS * 4);
		}
		//update();

	}

	/**
	 * Creates a new instance of testSignal
	 */
	public testSignal() {
		super("signal" + (sc++));
		setBounds(TRACKDIST + fscale, fscale * 3 + 5);
	}

	@Override
	public int isStopped(fulltrain tf) {
		return isred ? stopObject.STOPPED_FULL : stopObject.STOPPED_NO;
	}

	@Override
	public boolean canGreen() {
		return true;
	}

	@Override
	public void setState(int s) {
		super.setState(s);
		if (s > 0) {
			setRed(false);
		} else {
			setRed(true);
		}
	}

	@Override
	public boolean updateTrain(fulltrain ft, int step) {
		if (step == trackObject.TRAINSTEP_END && autored) {
			setState(0);
		}
		return false;
	}

	@Override
	public void paint(Graphics2D g2) {
		if (isred) {
			paintSig(g2, col_schwarz, col_rotein, col_gruenaus, TRACKDIST);
		} else {
			paintSig(g2, col_schwarz, col_rotaus, col_gruenein, TRACKDIST);
		}
	}

	@Override
	public void paintAnim(Graphics2D g2, String userdata, int count) {
		count /= 2;
		int n1 = ANIMSTEPS - 1, n2 = ANIMSTEPS - 1;
		if (tored) {
			if (count > ANIMSTEPS) {
				n1 = ANIMSTEPS - 1;
				n2 = ANIMSTEPS * 2 - count;
			} else if (count < ANIMSTEPS) {
				n1 = count;
				n2 = ANIMSTEPS - 1;
			}
		} else {
			if (count > ANIMSTEPS) {
				n2 = ANIMSTEPS - 1;
				n1 = ANIMSTEPS * 2 - count;
			} else if (count < ANIMSTEPS) {
				n2 = count;
				n1 = ANIMSTEPS - 1;
			}
		}
		if (g2 != null) {
			paintSig(g2, col_schwarz, col_redanim[n1], col_gruenanim[n2], TRACKDIST);
		}
		if (count == 0) {
			isred = tored;
			fireValueChanged();
		}
	}

	private void paintSig(Graphics2D g2, Color col_schwarz, Color col_rot, Color col_gruen, int trackDist) {
		g2.setColor(col_schwarz);
		g2.fillRoundRect(trackDist, 0, fscale, 2 * fscale, 5, 5);
		g2.fillRect(trackDist + fscale / 2 - 1, fscale, 2, fscale * 2);
		g2.fillRect(trackDist, fscale * 3, fscale, 4);


		/*	g2.fillRect(1, fscale-6, fscale-2, 5); // TODO: Zs1 schöner
		 g2.setColor(col_zs1);
		 g2.fillRect(2, fscale-5, fscale-4, 3); // TODO: Zs1 */

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(col_gruen);
		g2.fillOval(trackDist + 1, 1, fscale - 2, fscale - 2);

		g2.setColor(col_rot);
		g2.fillOval(trackDist + 1, fscale + 1, fscale - 2, fscale - 2);
	}

	@Override
	public trackObject clone() {
		return new testSignal();
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("red", "-Rot", isred));
		h.add(new objectDataStorage("autored", "-nach Zugende rot", autored));
		return h;
	}

	@Override
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("autored") == 0) {
				autored = key.getBoolValue();
			}
		}
		super.setData(hm);
	}

	@Override
	public String getGUIObjectName() {
		return "Test Signal";
	}

	@Override
	public void paintIcon(Graphics2D g2) {
		paintSig(g2, col_schwarz, col_rotaus, col_gruenein, 0);
	}

	@Override
	public ArrayList<stateText> getStateText() {
		ArrayList<stateText> t = new ArrayList<>();
		t.add(new stateText(0, "rot"));
		t.add(new stateText(1, "grün"));
		return t;
	}
}
