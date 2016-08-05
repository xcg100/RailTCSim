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
package org.railsim.train;

import java.awt.Graphics2D;
import java.awt.Image;

import org.railsim.service.genericPaintInterface;

/**
 *
 * @author js
 */
public class stock implements genericPaintInterface, Cloneable {

	public rollingstock s = null;
	public boolean forward = true;

	public stock(rollingstock _s, boolean f) {
		s = _s;
		forward = f;
	}

	@Override
	public void paint(Graphics2D g2) {
		s.paint(g2);
	}

	@Override
	public void paintIcon(Graphics2D g2) {
		if (!forward) {
			Graphics2D g3 = (Graphics2D) g2.create();
			Image i = s.getAppearance(270);
			g3.rotate(Math.PI, i.getWidth(null) / 2, i.getHeight(null) / 2);
			s.paintIcon(g3, forward);
		} else {
			s.paintIcon(g2, forward);
		}
	}

	@Override
	public String getLabel() {
		return s.getLabel();
	}

	@Override
	public Object clone() {
		return new stock(s, forward);
	}

	@Override
	public int getPaintOrientation() {
		return s.getPaintOrientation();
	}

	public rollingstock getRollingstock() {
		return s;
	}

	public void rotate() {
		forward = !forward;
	}
};
