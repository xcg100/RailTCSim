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

import java.util.ArrayList;
import java.util.Iterator;

import org.railsim.service.odsHashSet;
import org.railsim.service.trackObjects.*;
import org.railsim.service.trackObjects.objects.graphics.hvsignal_main;

/**
 *
 * @author js
 */
public class hvsignal2 extends hvsignal1 {

	boolean mixmode = false;

	/**
	 * Creates a new instance of testSignal
	 */
	public hvsignal2() {
		super();
		gfx = new hvsignal_main(hvsignal_main.T_GREENYELLOW);
	}

	@Override
	public trackObject clone() {
		return new hvsignal2();
	}

	@Override
	public void setState(int s) {
		if (!mixmode && s > 1) {
			s = 2;
		}
		super.setState(s);
	}

	@Override
	public odsHashSet getData() {
		odsHashSet h = super.getData();
		h.add(new objectDataStorage("mix", "Hp1/Hp2 erlauben", mixmode));
		return h;
	}

	@Override
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("mix") == 0) {
				mixmode = key.getBoolValue();

				if (mixmode) {
					gfx = new hvsignal_main(hvsignal_main.T_GREENYELLOWGREEN);
				} else {
					gfx = new hvsignal_main(hvsignal_main.T_GREENYELLOW);
				}
				gfx.setState(getState());
			}
		}
		super.setData(hm);
	}

	@Override
	public String getGUIObjectName() {
		return "H/V Hp1/Hp2";
	}

	@Override
	public ArrayList<stateText> getStateText() {
		ArrayList<stateText> t = new ArrayList<>();
		t.add(new stateText(0, "rot (Hp0)"));
		if (mixmode) {
			t.add(new stateText(1, "grün (Hp1)"));
		}
		t.add(new stateText(2, "gelb-grün (Hp2)"));
		return t;
	}
}
