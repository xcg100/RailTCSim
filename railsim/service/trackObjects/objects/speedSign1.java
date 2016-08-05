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
import java.util.Iterator;

import org.railsim.service.*;
import org.railsim.service.trackObjects.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class speedSign1 extends trackObject {

	static int sc = 0;
	static Color col_hgr = new Color(0xcc, 0xcc, 0x00);
	static Color col_paint = new Color(0x00, 0x00, 0x00);
	static Color col_pile = new Color(0x00, 0x00, 0x00);
	static int fscale = 8;
	static int TRACKDIST = 10 + track.RAILRADIUS;
	int speedlimit = 100;
	boolean enabled = true;

	/**
	 * Creates a new instance of speedSign1
	 */
	public speedSign1() {
		super("sign1_" + (sc++));
		setBounds(TRACKDIST + fscale * 2, fscale * 3);
	}

	@Override
	public boolean updateTrain(fulltrain ft, int step) {
		/* if ((step & trackObject.TRAINSTEP_PRERUNNER)!=0)
		 {
		 ft.setvMax(speedlimit,this,step);
		 }
		 else if ((step & trackObject.TRAINSTEP_END)!=0)
		 {
		 ft.setvMax(speedlimit,this,step);
		 } */
		return false;
	}

	@Override
	public void paint(Graphics2D g2) {
		paintSig(g2, TRACKDIST);
	}

	@Override
	public void paintIcon(Graphics2D g2) {
		paintSig(g2, 0);
	}

	private void paintSig(Graphics2D g2, int trackDist) {
		g2.setColor(col_pile);
		g2.fillRect(trackDist + fscale - 1, fscale, 2, fscale * 3);
		g2.setColor(col_hgr);
		Polygon p = new Polygon();
		p.addPoint(trackDist, 0);
		p.addPoint(trackDist + fscale * 2, 0);
		p.addPoint(trackDist + fscale, fscale * 2);
		g2.fillPolygon(p);

		g2.setColor(col_paint);
		g2.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawPolygon(p);


		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		String text = (speedlimit / 10) + "";
		Font font = new Font("SansSerif", Font.BOLD, 8);
		g2.setFont(font);
		FontMetrics metrics = g2.getFontMetrics(font);
		//int hgt = metrics.getHeight();
		int bl = metrics.getMaxAscent();
		int adv = metrics.stringWidth(text);

		g2.drawString(text, trackDist + (fscale * 2 - adv) / 2 - 1, bl + 0);
	}

	@Override
	public void paintAnim(Graphics2D g2, String userdata, int count) {
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("speedlimit", "Geschwindigkeit", speedlimit));
		h.add(new objectDataStorage("enabled", "aktiv", enabled));
		return h;
	}

	@Override
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("speedlimit") == 0) {
				speedlimit = key.getIntValue();
			} else if (key.getKey().compareTo("enabled") == 0) {
				enabled = key.getBoolValue();
			}
		}
		super.setData(hm);
	}

	@Override
	public trackObject clone() {
		speedSign1 t = new speedSign1();
		t.setData(getData());
		return t;
	}

	@Override
	public String getGUIObjectName() {
		return "V-Max Ankündigung";
	}
}
