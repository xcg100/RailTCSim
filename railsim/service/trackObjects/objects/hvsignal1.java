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

import org.railsim.event.AbstractEvent;
import org.railsim.service.odsHashSet;
import org.railsim.service.track;
import org.railsim.service.trackObjects.*;
import org.railsim.service.trackObjects.objects.graphics.hvsignal_base;
import org.railsim.service.trackObjects.objects.graphics.hvsignal_main;
import org.railsim.service.trackObjects.objects.graphics.hvsignal_pre;

/**
 *
 * @author js
 */
public class hvsignal1 extends pathableObject {

	static int sc = 0;
	static protected int TRACKDIST = 8 + track.RAILRADIUS;
	protected hvsignal_main gfx;
	protected hvsignal_pre pregfx = new hvsignal_pre(hvsignal_pre.T_GREENYELLOWGREEN);
	int oldstate = -1;
	int oldprestate = hvsignal_base.S_INIT;
	int shouldprestate = 0;
	boolean presignal = false;

	void preanim(int newstate) {
		shouldprestate = newstate;
		int s = shouldprestate;
		if (getState() == hvsignal_base.S_RED) {
			s = hvsignal_base.S_OFF;
		}
		if (oldprestate != s) {
			pregfx.setState(s);
			oldprestate = s;
			registerAnim("presignal", pregfx.ANIMSTARTCOUNTER);
		}
	}

	void anim() {
		if (getState() != oldstate) {
			gfx.setState(getState());
			registerAnim("signal", gfx.ANIMSTARTCOUNTER);
		}
	}

	/**
	 * Creates a new instance of testSignal
	 */
	public hvsignal1() {
		super("hvsignal " + (sc++));
		gfx = new hvsignal_main(hvsignal_main.T_GREEN);
		pregfx.setState(hvsignal_base.S_OFF);
		setBounds(TRACKDIST + gfx.getWidth(), gfx.getHeightWithPylon());
	}

	@Override
	public void setState(int s) {
		super.setState(s);
		if (s == 0) {
			unconnectMeFromSignal();
		} else {
			if (oldprestate == hvsignal_base.S_OFF) {
				preanim(shouldprestate);
			}
		}
		anim();
	}

	@Override
	public void paint(Graphics2D g2) {
		if (g2 != null) {
			Graphics2D g3;
			if (presignal) {
				g3 = (Graphics2D) g2.create(TRACKDIST, -pregfx.getHeight(), gfx.getWidth() * 2, gfx.getHeight() * 2);
				gfx.paintPylon(g3);

				Graphics2D g4 = (Graphics2D) g2.create(TRACKDIST, 0, pregfx.getWidth() * 2, pregfx.getHeightWithPylon() * 2);
				pregfx.paintPylon(g4);
				pregfx.paint(g4);
			} else {
				g3 = (Graphics2D) g2.create(TRACKDIST, 0, gfx.getWidth() * 2, gfx.getHeightWithPylon() * 2);
				gfx.paintPylon(g3);
			}
			gfx.paint(g3);
		}
	}

	@Override
	public void paintAnim(Graphics2D g2, String userdata, int count) {
		if (g2 != null) {
			Graphics2D g3;
			if (presignal) {
				if (userdata.compareTo("presignal") == 0) {
					g3 = (Graphics2D) g2.create(TRACKDIST, 0, pregfx.getWidth() * 2, pregfx.getHeightWithPylon() * 2);
					pregfx.paintAnim(g3, count);
				}
				g3 = (Graphics2D) g2.create(TRACKDIST, -pregfx.getHeight(), gfx.getWidth() * 2, gfx.getHeight() * 2);
			} else {
				g3 = (Graphics2D) g2.create(TRACKDIST, 0, gfx.getWidth() * 2, gfx.getHeightWithPylon() * 2);
			}
			if (userdata.compareTo("signal") == 0) {
				gfx.paintAnim(g3, count);
			}
		}
		if (count == 0) {
			oldstate = getState();
			fireValueChanged();
		}
	}

	@Override
	public trackObject clone() {
		return new hvsignal1();
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("presignal", "mit Vorsignal", presignal));
		return h;
	}

	@Override
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("presignal") == 0) {
				presignal = key.getBoolValue();
				if (presignal) {
					setBounds(TRACKDIST + Math.max(pregfx.getWidth(), gfx.getWidth()), gfx.getHeightWithPylon());
				} else {
					setBounds(TRACKDIST + gfx.getWidth(), gfx.getHeightWithPylon());
				}
			}
		}
		super.setData(hm);
	}

	@Override
	public String getGUIObjectName() {
		return "H/V Hp1";
	}

	@Override
	public void paintIcon(Graphics2D g2) {
		gfx.paintPylon(g2);
		gfx.paint(g2);
	}

	@Override
	public void connectToSignal(pathableObject p) {
		connectMeToSignal(p);
	}

	@Override
	public void action(AbstractEvent e) {
		super.action(e);
		SignalStateEvent e2 = (SignalStateEvent) e;
		preanim(e2.getState());
	}

	@Override
	public ArrayList<stateText> getStateText() {
		ArrayList<stateText> t = new ArrayList<>();
		t.add(new stateText(0, "rot (Hp1)"));
		t.add(new stateText(1, "grün (Hp1)"));
		return t;
	}
}
