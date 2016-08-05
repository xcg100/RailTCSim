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
package org.railsim.service.tracks;

import java.awt.*;
import java.util.List;
import java.util.StringTokenizer;

import org.railsim.*;
import org.railsim.service.point;
import org.railsim.service.rotatepoint;
import org.railsim.service.track;
import org.railsim.service.tracks.painter.*;

/**
 *
 * @author js
 */
public class simpleTrack implements trackPainter {

	static final public int SLEEPEROVERLAY = 2;
	private additionalColorBase acb = null;
	private additionalPainterBase apb = null;
	private sleeperColorBase scb = null;
	private sleeperPainterBase spb = null;
	private railColorBase rcb = null;
	private railPainterBase rpb = null;
	private aboveColorBase abcb = null;
	private abovePainterBase abpb = null;
	private sleeperColorBase p_scb = new progress_sleeperColorBase();
	private sleeperPainterBase p_spb = new sleeperPainterBase();      // needed?
	private railColorBase p_rcb = new progress_railColorBase();
	private railPainterBase p_rpb = new railPainterBase();            // needed?

	/**
	 * Creates a new instance of simpleTrack
	 */
	public simpleTrack() {
		setPainter(new additionalColorBase());
		setPainter(new additionalPainterBase());
		setPainter(new aboveColorBase());
		setPainter(new abovePainterBase());
		setPainter(new sleeperColorBase());
		setPainter(new sleeperPainterBase());
		setPainter(new railColorBase());
		setPainter(new railPainterBase());
	}

	public simpleTrack(String painters) {
		setPainters(painters);
	}

	protected boolean setPainter(String name) {
		Class c;
		Object o = null;
		try {
			c = getClass().forName("org.railsim.service.tracks.painter." + name);
			o = c.newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
			ex.printStackTrace();
			return false;
		}
		return setPainter(o);
	}

	protected boolean setPainter(Object o) {
		if (o instanceof additionalColorBase) {
			try {
				acb = (additionalColorBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}
		if (o instanceof additionalPainterBase) {
			try {
				apb = (additionalPainterBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}

		if (o instanceof aboveColorBase) {
			try {
				abcb = (aboveColorBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}
		if (o instanceof abovePainterBase) {
			try {
				abpb = (abovePainterBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}

		if (o instanceof sleeperColorBase) {
			try {
				scb = (sleeperColorBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}
		if (o instanceof sleeperPainterBase) {
			try {
				spb = (sleeperPainterBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}

		if (o instanceof railColorBase) {
			try {
				rcb = (railColorBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}
		if (o instanceof railPainterBase) {
			try {
				rpb = (railPainterBase) o;
			} catch (ClassCastException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	protected String getNameOf(trackPainterInterface t) {
		return t.getClass().getSimpleName();
	}

	protected String getPainters() {
		String ret = "";
		if (acb != null) {
			ret += "," + getNameOf(acb);
		}
		if (apb != null) {
			ret += "," + getNameOf(apb);
		}
		if (scb != null) {
			ret += "," + getNameOf(scb);
		}
		if (spb != null) {
			ret += "," + getNameOf(spb);
		}
		if (rcb != null) {
			ret += "," + getNameOf(rcb);
		}
		if (rpb != null) {
			ret += "," + getNameOf(rpb);
		}
		if (abcb != null) {
			ret += "," + getNameOf(abcb);
		}
		if (abpb != null) {
			ret += "," + getNameOf(abpb);
		}

		return ret;
	}

	protected void setPainters(String p) {
		StringTokenizer st = new StringTokenizer(p, ", \t\n\r\f");
		while (st.hasMoreTokens()) {
			String t = st.nextToken();
			if (t.length() > 0) {
				setPainter(t);
			}
		}
	}

	@Override
	public String[] getPainterData() {
		StringTokenizer st = new StringTokenizer(getPainters(), ", \t\n\r\f");
		String[] ret = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			ret[i++] = st.nextToken();
		}
		return ret;
	}

	@Override
	public void setPainterData(String v) {
		setPainter(v);
	}
	private Graphics2D lastg2_paint = null;
	private int cycle_paint = 0;

	@Override
	public void paint(track t, Graphics2D g2, int part, List<point> railNorth, List<point> railSouth, List<rotatepoint> points, int progress) {
		cycle_paint = dataCollector.collector.thepainter.getGraphicsCycle();
		lastg2_paint = g2;
		if (progress > 0) {
			// addi color class
			if (acb != null && (part & track.PART_ADDITIONAL) != 0) {
				acb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// addi painter class
			if (apb != null && (part & track.PART_ADDITIONAL) != 0) {
				apb.paint(this, t, g2, railNorth, railSouth, points);
			}

			// sleeper color class
			if (p_rcb != null) {
				p_rcb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// sleeper painter class
			if (spb != null && (part & track.PART_SLEEPER) != 0) {
				spb.paint(this, t, g2, railNorth, railSouth, points);
			}

			// rail color class
			if (p_rcb != null) {
				p_rcb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// rail painter class
			if (rpb != null && (part & track.PART_RAIL) != 0) {
				rpb.paint(this, t, g2, railNorth, railSouth, points);
			}

			// above color class
			if (abcb != null && (part & track.PART_ABOVE) != 0) {
				abcb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// above painter class
			if (abpb != null && (part & track.PART_ABOVE) != 0) {
				abpb.paint(this, t, g2, railNorth, railSouth, points);
			}
		} else {
			// addi color class
			if (acb != null && (part & track.PART_ADDITIONAL) != 0) {
				acb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// addi painter class
			if (apb != null && (part & track.PART_ADDITIONAL) != 0) {
				apb.paint(this, t, g2, railNorth, railSouth, points);
			}

			// sleeper color class
			if (scb != null && (part & track.PART_SLEEPER) != 0) {
				scb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// sleeper painter class
			if (spb != null && (part & track.PART_SLEEPER) != 0) {
				spb.paint(this, t, g2, railNorth, railSouth, points);
			}

			// rail color class
			if (rcb != null && (part & track.PART_RAIL) != 0) {
				rcb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// rail painter class
			if (rpb != null && (part & track.PART_RAIL) != 0) {
				rpb.paint(this, t, g2, railNorth, railSouth, points);
			}

			// above color class
			if (abcb != null && (part & track.PART_ABOVE) != 0) {
				abcb.paint(this, t, g2, railNorth, railSouth, points);
			}
			// above painter class
			if (abpb != null && (part & track.PART_ABOVE) != 0) {
				abpb.paint(this, t, g2, railNorth, railSouth, points);
			}
		}
	}
	private Graphics2D lastg2_junction = null;
	private int cycle_junction = 0;

	@Override
	public void paintJunction(track t, Graphics2D g, int direction) {
		cycle_junction = dataCollector.collector.thepainter.getGraphicsCycle();
		lastg2_junction = g;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 10, 10);
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		switch (direction) {
			case -1:
			// right
			case 1:
				// left
				g.drawLine(5, 2, 5, 8);
				break;
			case -2:
				// right
				g.drawLine(8, 2, 2, 8);
				break;
			case 2:
				// left
				g.drawLine(2, 2, 8, 8);
				break;
			case 0:
				// unused
				break;
			default:
				break;
		}
	}

	@Override
	public void paintJunction(track t, int direction) {
		if (lastg2_junction != null && cycle_junction == dataCollector.collector.thepainter.getGraphicsCycle()) {
			paintJunction(t, lastg2_junction, direction);
		}
	}
	static private int workerAnim = 0;

	@Override
	public void paintWorker(Graphics2D g, track t, List<point> railNorth, List<point> railSouth, List<rotatepoint> points, int progress) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(Color.YELLOW);
		int l = points.size();
		int i = l * (t.maxProgress - progress) / t.maxProgress;

		point s = points.get(0);
		point p = railNorth.get(i);
		int x1 = p.getX();
		int y1 = p.getY();
		point xy = railSouth.get(i);
		int vx = x1 - xy.getX();
		int vy = y1 - xy.getY();
		vx = vx / (simpleTrack.SLEEPEROVERLAY * 2 + workerAnim);
		vy = vy / (simpleTrack.SLEEPEROVERLAY * 2 + workerAnim);

		g2.drawLine(x1 + vx, y1 + vy, xy.getX() - vx, xy.getY() - vy);
		g2.drawLine(s.getX(), s.getY(), xy.getX() - vx, xy.getY() - vy);
		g2.drawLine(s.getX(), s.getY(), x1 + vx, y1 + vy);
		workerAnim++;
		if (workerAnim > 100) {
			workerAnim = 0;
		}
	}

	@Override
	public trackPainter clone() {
		simpleTrack s = new simpleTrack();
		s.setPainters(getPainters());
		return s;
	}
}
