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
package org.railsim.editor;

import org.railsim.event.AbstractEvent;

/**
 * Used to send events from GUI (editor panels) to editors
 *
 * @author js
 */
public class EditorActionEvent<T> extends AbstractEvent {

	String action;

	/**
	 * Creates a new instance of TrackEditorEvent
	 *
	 * @param a Action
	 */
	public EditorActionEvent(String a) {
		super(a);
		action = a;
	}

	/**
	 * Creates a new instance of TrackEditorEvent
	 *
	 * @param a ActionEvent
	 */
	public EditorActionEvent(java.awt.event.ActionEvent a) {
		this(a.getActionCommand());
	}
	T value = null;

	/**
	 * Creates a new instance of TrackEditorEvent
	 *
	 * @param a Action
	 * @param _value Value
	 */
	public EditorActionEvent(String a, T _value) {
		this(a);
		value = _value;
	}

	/**
	 * Creates a new instance of TrackEditorEvent
	 *
	 * @param a ActionEvent
	 * @param _value Value
	 */
	public EditorActionEvent(java.awt.event.ActionEvent a, T _value) {
		this(a.getActionCommand(), _value);
	}

	/**
	 * Action
	 *
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Value
	 *
	 * @return value
	 */
	public T getValue() {
		return value;
	}
}
