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
public class railColorBase implements trackPainterInterface {

	@Override
	public void paint(simpleTrack s, track t, Graphics2D g2, List<point> railNorth, List<point> railSouth, List<rotatepoint> points) {
		g2.setColor(Color.black);
	}
}
