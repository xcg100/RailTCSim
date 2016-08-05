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

import java.util.LinkedList;

import org.railsim.*;
import org.railsim.service.odsHashSet;
import org.railsim.train.fulltrain;
import org.railsim.train.starttrain;

/**
 * track object with train stop options, like signals
 *
 * @author js
 */
public abstract class trainStartObject extends trackObject {

	static int sc = 0;
	LinkedList<starttrain> trains = new LinkedList<>();
	java.util.concurrent.ConcurrentLinkedQueue<starttrain> runqueue = new java.util.concurrent.ConcurrentLinkedQueue<>();

	protected trainStartObject() {
		super("Einfahrt " + (sc++));
	}

	protected trainStartObject(String n) {
		super(n);
	}

	protected trainStartObject(String n, String r) {
		super(n, r);
	}

	protected trainStartObject(String n, String r, int width, int height) {
		super(n, r, width, height);
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

	private void nextTrain() {
		if (runqueue.isEmpty()) {
			return;
		}
		starttrain st = runqueue.poll();
		if (st != null) {
			fulltrain ft = st.makeTrain(); //System.out.println(st.getName()+" make train: "+ft.getName());
			if (ft != null) {
				ft.onTrack(this);
				dataCollector.collector.thepainter.addTrain(ft);
			}
		}
	}

	@Override
	public boolean updateTrain(fulltrain ft, int step) {
		if (step == trackObject.TRAINSTEP_PRERUNNER) {
		} else if (step == trackObject.TRAINSTEP_END) {
			nextTrain();
		}
		return false;
	}

	public void newTrain(long time) {
		// durch alle Züge "trains" gehen und nach Frequenz und Zeit starten (in runqueue schreiben)
		// Aufruf durch scheduler jede Minute
		synchronized (trains) {
			for (starttrain st : trains) {
				System.out.println(st.getName() + " should start?");
				if (st.shouldStart(time)) {
					System.out.println(st.getName() + " start!");
					runqueue.add(st);
				}
			}
		}
		nextTrain();
	}

	public void addTrain(starttrain s) {
		synchronized (trains) {
			trains.add(s);
		}
	}

	public void removeTrain(starttrain s) {
		synchronized (trains) {
			trains.remove(s);
		}
	}

	public void addQueue(starttrain s) {
		runqueue.add(s);
	}

	/**
	 * must be synchronized
	 *
	 * @return train list
	 */
	public LinkedList<starttrain> getTrains() {
		return trains;
	}

	public java.util.concurrent.ConcurrentLinkedQueue<starttrain> getQueue() {
		return runqueue;
	}

	@Override
	public int getRequirements() {
		return REQUIREMENT_NOBUILD | REQUIREMENT_NOTRAIN;
	}
}
