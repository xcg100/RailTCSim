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
package org.railsim.service;

import java.awt.Rectangle;
import java.awt.Shape;

/**
 *
 * @author js
 */
public class rectangle extends Rectangle {

	/**
	 * Creates a new instance of rectangle
	 */
	public rectangle(point p1, point p2) {
		this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public rectangle(int x1, int y1, int x2, int y2) {
		super(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1) + 1, Math.abs(y2 - y1) + 1);
	}

	public rectangle(Rectangle p) {
		super(p);
	}

	@Override
	public rectangle getBounds() {
		return new rectangle(this);
	}

	@Override
	public rectangle union(Rectangle r) {
		return new rectangle(super.union(r));
	}

	public void rotate(double theta) {
		rotate(theta, 0, 0);
	}

	public void rotate(double theta, int mx, int my) {
		int x1 = this.x - mx;
		int y1 = this.y - my;
		int x2 = this.width + this.x - mx;
		int y2 = this.height + this.y - my;
		int x11 = this.width + this.x - mx;
		int y11 = this.y - my;
		int x21 = this.x - mx;
		int y21 = this.height + this.y - my;

		if (x1 != 0 || y1 != 0) {
			double l = Math.sqrt(x1 * x1 + y1 * y1);
			x1 = (int) java.lang.Math.round(java.lang.Math.cos(theta) * l);
			y1 = (int) java.lang.Math.round(java.lang.Math.sin(theta) * l);
		}
		if (x2 != 0 || y2 != 0) {
			double l = Math.sqrt(x2 * x2 + y2 * y2);
			x2 = (int) java.lang.Math.round(java.lang.Math.cos(theta + Math.PI / 4) * l);
			y2 = (int) java.lang.Math.round(java.lang.Math.sin(theta + Math.PI / 4) * l);
		}
		if (x11 != 0 || y11 != 0) {
			double l = Math.sqrt(x11 * x11 + y11 * y11);
			x11 = (int) java.lang.Math.round(java.lang.Math.cos(theta) * l);
			y11 = (int) java.lang.Math.round(java.lang.Math.sin(theta) * l);
		}
		if (x21 != 0 || y21 != 0) {
			double l = Math.sqrt(x21 * x21 + y21 * y21);
			x21 = (int) java.lang.Math.round(java.lang.Math.cos(theta + Math.PI / 2) * l);
			y21 = (int) java.lang.Math.round(java.lang.Math.sin(theta + Math.PI / 2) * l);
		}
		this.x = Math.min(Math.min(Math.min(x1, x2), x11), x21) + mx;
		this.y = Math.min(Math.min(Math.min(y1, y2), y11), y21) + my;
		this.width = Math.max(Math.max(Math.max(x1, x2), x11), x21) + mx - this.x;
		this.height = Math.max(Math.max(Math.max(y1, y2), y11), y21) + my - this.y;
	}

	public void transform(java.awt.geom.AffineTransform aff) {
		Shape s = aff.createTransformedShape(this);
		Rectangle r = s.getBounds();

		x = r.x;
		y = r.y;
		width = r.width;
		height = r.height;

		/*

		 Point p1,p2,pd1,pd2;
	
		 p1=new Point(x,y);
		 p2=new Point(x+width,y+height);
		 pd1=new Point(x,y);
		 pd2=new Point(x+width,y+height);
	
		 aff.transform(p1,pd1);
		 aff.transform(p2,pd2);

		 x=Math.min(pd1.x,pd2.x);
		 y=Math.min(pd1.y,pd1.y);
		 width=Math.abs(pd2.x-pd1.x)+1;
		 height=Math.abs(pd2.y-pd1.y)+1;
		 */
	}
}
