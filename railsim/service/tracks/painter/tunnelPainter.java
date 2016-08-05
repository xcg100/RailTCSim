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
package org.railsim.service.tracks.painter;

import java.awt.*;
import java.util.List;

import org.railsim.service.*;
import org.railsim.service.tracks.*;

/**
 *
 * @author js
 */
public class tunnelPainter extends abovePainterBase {

	@Override
	public void paint(simpleTrack s, track t, Graphics2D g2, List<point> railNorth, List<point> railSouth, List<rotatepoint> points) {
		point xy1 = null, xy2 = null;
		int i = 0;
		for (point p : railNorth) {
			point xy = railSouth.get(i);
			if (xy1 != null) {
				int x1 = p.getX();
				int y1 = p.getY();

				int vx = x1 - xy.getX();
				int vy = y1 - xy.getY();
				vx = vx / simpleTrack.SLEEPEROVERLAY;
				vy = vy / simpleTrack.SLEEPEROVERLAY;

				Polygon po = new Polygon();
				po.addPoint(x1 + vx, y1 + vy);
				po.addPoint(xy.getX() - vx, xy.getY() - vy);

				vx = xy1.getX() - xy2.getX();
				vy = xy1.getY() - xy2.getY();
				vx = vx / simpleTrack.SLEEPEROVERLAY;
				vy = vy / simpleTrack.SLEEPEROVERLAY;
				po.addPoint(xy1.getX() + vx, xy1.getY() + vy);
				po.addPoint(xy2.getX() - vx, xy2.getY() - vy);

				g2.drawPolygon(po);
				g2.fillPolygon(po);
			}
			xy1 = p;
			xy2 = xy;
			++i;
		}
	}
}
