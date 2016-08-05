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

import org.railsim.service.*;
import org.railsim.service.trackObjects.*;

/**
 *
 * @author js
 */
public class trainExitPoint extends trainEndObject {

	static Color col_hgr = new Color(0x00, 0x00, 0xff);
	static Color col_paint = new Color(0xff, 0x00, 0x00);
	static int fscale = track.RAILRADIUS * 2;

	/**
	 * Creates a new instance of speedSign1
	 */
	public trainExitPoint() {
		super();
		setBounds(fscale * 2, fscale * 2);
		setPaintAlways(false);
	}

	@Override
	public void paint(Graphics2D g2) {
		paintSig(g2, 0);
	}

	@Override
	public void paintIcon(Graphics2D g2) {
		paintSig(g2, fscale);
	}

	private void paintSig(Graphics2D g2, int x) {
		g2.setColor(col_hgr);
		Polygon p = new Polygon();
		p.addPoint(x, fscale);
		p.addPoint(x + fscale, fscale);
		p.addPoint(x + fscale, 0);
		p.addPoint(x - fscale, 0);
		p.addPoint(x - fscale, fscale);
		p.addPoint(x, fscale);
		p.addPoint(x + fscale, fscale * 2);
		p.addPoint(x - fscale, fscale * 2);
		g2.fillPolygon(p);

		g2.setColor(col_paint);
		//g2.setStroke(new BasicStroke(2,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawPolygon(p);
	}

	@Override
	public void paintAnim(Graphics2D g2, String userdata, int count) {
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
	public trackObject clone() {
		trainExitPoint t = new trainExitPoint();
		t.setData(getData());
		return t;
	}

	@Override
	public String getGUIObjectName() {
		return "Endpunkt";
	}
}
