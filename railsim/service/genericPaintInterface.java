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
package org.railsim.service;

import java.awt.Graphics2D;

/**
 *
 * @author js
 */
public interface genericPaintInterface {

	public void paint(Graphics2D g2);

	public void paintIcon(Graphics2D g2);

	public String getLabel();

	public int getPaintOrientation(); // 0: up to down, 1: left to right, 2: down to up, 3: right to left
}
