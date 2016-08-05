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

import org.railsim.event.AbstractEvent;
import org.railsim.event.AbstractListener;

/**
 * track object with train stop options, like signals
 *
 * @author js
 */
public abstract class presignalObject extends trackObject implements AbstractListener {

	protected presignalObject(String n) {
		super(n);
	}

	protected presignalObject(String n, String r) {
		super(n, r);
	}

	protected presignalObject(String n, String r, int width, int height) {
		super(n, r, width, height);
	}
	pathableObject lastConnectedSignal = null;

	final protected void connectMeToSignal(pathableObject p) {
		if (lastConnectedSignal != null) {
			lastConnectedSignal.unconnectPresignal(this);
			action(new SignalStateEvent(lastConnectedSignal, 0));
		}
		if (p != null) {
			p.connectPresignal(this);
		}
		lastConnectedSignal = p;
	}

	final protected void unconnectMeFromSignal() {
		if (lastConnectedSignal != null) {
			lastConnectedSignal.unconnectPresignal(this);
			action(new SignalStateEvent(lastConnectedSignal, 0));
			lastConnectedSignal = null;
		}
	}

	abstract public void connectToSignal(pathableObject p);

	@Override
	public void action(AbstractEvent e) {
		SignalStateEvent e2 = (SignalStateEvent) e;
		if (e2.getState() == 0) {
			e2.getSignal().unconnectPresignal(this);
			lastConnectedSignal = null;
		}
	}

	@Override
	public int getRequirements() {
		return REQUIREMENT_NOTRAIN;
	}
}
