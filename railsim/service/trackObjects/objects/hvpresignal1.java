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

import org.railsim.event.AbstractEvent;
import org.railsim.service.odsHashSet;
import org.railsim.service.track;
import org.railsim.service.trackObjects.*;
import org.railsim.service.trackObjects.objects.graphics.hvsignal_pre;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class hvpresignal1 extends presignalObject {

	static int sc = 0;
	static protected int TRACKDIST = 8 + track.RAILRADIUS;
	protected hvsignal_pre gfx;

	/**
	 * Creates a new instance of testSignal
	 */
	public hvpresignal1() {
		super("hvpresignal " + (sc++));
		gfx = new hvsignal_pre(hvsignal_pre.T_GREENYELLOWGREEN);
		setBounds(TRACKDIST + gfx.getWidth(), gfx.getHeightWithPylon());
	}

	@Override
	public void paint(Graphics2D g2) {
		if (g2 != null) {
			Graphics2D g3 = (Graphics2D) g2.create(TRACKDIST, 0, gfx.getWidth() * 2, gfx.getHeightWithPylon() * 2);
			gfx.paintPylon(g3);
			gfx.paint(g3);
		}
	}

	@Override
	public void paintAnim(Graphics2D g2, String userdata, int count) {
		if (g2 != null) {
			Graphics2D g3 = (Graphics2D) g2.create(TRACKDIST, 0, gfx.getWidth() * 2, gfx.getHeightWithPylon() * 2);
			gfx.paintAnim(g3, count);
		}
	}

	@Override
	public trackObject clone() {
		return new hvpresignal1();
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		return h;
	}

	@Override
	public void setData(odsHashSet hm) {
		super.setData(hm);
	}

	@Override
	public String getGUIObjectName() {
		return "H/V Vorsignal";
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
	public boolean updateTrain(fulltrain ft, int step) {
		return false;
	}

	@Override
	public void action(AbstractEvent e) {
		super.action(e);
		SignalStateEvent e2 = (SignalStateEvent) e;
		gfx.setState(e2.getState());
		registerAnim(null, gfx.ANIMSTARTCOUNTER);
	}
}
