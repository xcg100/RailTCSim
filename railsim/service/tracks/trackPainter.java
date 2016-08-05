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
package org.railsim.service.tracks;

import java.awt.Graphics2D;
import java.util.List;

import org.railsim.service.*;

/**
 *
 * @author js
 */
public interface trackPainter {

	void paint(track t, Graphics2D g2, int part, java.util.List<point> railNorth, java.util.List<point> railSouth, java.util.List<rotatepoint> points, int progress);

	void paintJunction(track t, Graphics2D g2, int direction);

	void paintJunction(track t, int direction);

	String[] getPainterData();

	void setPainterData(String p);

	trackPainter clone();

	void paintWorker(Graphics2D g2, track track, List<point> railNorth, List<point> railSouth, List<rotatepoint> points, int progress);
}
