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
package org.railsim.service.trackObjects;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author js
 */
public class trackObjectAnim extends TimerTask {

	class animStore {

		public trackObject to = null;
		public String userdata = null;

		public animStore(trackObject _to) {
			to = _to;
		}

		public animStore(trackObject _to, String u) {
			to = _to;
			userdata = u;
		}
	}
	Timer tmr = new Timer();
	ConcurrentHashMap<animStore, Integer> counter = new ConcurrentHashMap<>();

	/**
	 * Creates a new instance of trackObjectAnim
	 */
	public trackObjectAnim() {
		tmr.scheduleAtFixedRate(this, 0, 1000 / 20);
	}

	/**
	 * Register trackObject for anim
	 *
	 * @param to TrackObject
	 * @param count number of anim steps
	 */
	public void registerAnim(trackObject to, int count) {
		animStore as = new animStore(to);
		counter.put(as, count);
	}

	public void registerAnim(trackObject to, String userdata, int count) {
		animStore as = new animStore(to, userdata);
		counter.put(as, count);
	}

	void removeAnim(animStore as) {
		counter.remove(as);
	}

	/**
	 * Run
	 */
	@Override
	public void run() {
		for (animStore as : counter.keySet()) {
			int cnt = counter.get(as) - 1;
			trackObject to = as.to;
			to.paintAnimTO(as.userdata, cnt);
			if (cnt > 0) {
				counter.put(as, cnt);
			} else {
				removeAnim(as);
			}
		}
	}

	/**
	 * get anim step counter value - or -1 if no anim currently registered
	 *
	 * @param to TrackObject
	 * @return current anim step or -1
	 */
	public int getAminState(trackObject to, String userdata) {
		animStore as = new animStore(to, userdata);
		if (counter.containsKey(as)) {
			return counter.get(as);
		}
		return -1;
	}

	public int getAminState(trackObject to) {
		return getAminState(to, null);
	}
}
