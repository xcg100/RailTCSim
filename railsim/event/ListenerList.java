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
package org.railsim.event;

/**
 *
 * @author js
 */
public class ListenerList extends javax.swing.event.EventListenerList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7804868892274748241L;

	/**
	 * Creates a new instance of EventListerList
	 */
	public ListenerList() {
	}

	public void addListener(AbstractListener l) {
		add(AbstractListener.class, l);
	}

	public void removeListener(AbstractListener l) {
		remove(AbstractListener.class, l);
	}

	// Notify all listeners that have registered interest for
	// notification on this event type.  The event instance
	// is lazily created using the parameters passed into
	// the fire method.
	public void fireEvent(AbstractEvent fooEvent) {
		// Guaranteed to return a non-null array
		Object[] listeners = getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == AbstractListener.class) {
				// Lazily create the event:
				((AbstractListener) listeners[i + 1]).action(fooEvent);
			}
		}
	}
}
