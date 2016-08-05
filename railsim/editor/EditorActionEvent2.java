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

/**
 *
 * @author js
 */
public class EditorActionEvent2<T, T2> extends EditorActionEvent<T> {

	T2 value2 = null;

	/**
	 * Creates a new instance of EditorActionEvent2
	 */
	public EditorActionEvent2(String a, T v1, T2 v2) {
		super(a, v1);
		value2 = v2;
	}

	/**
	 * Creates a new instance of TrackEditorEvent
	 *
	 * @param a ActionEvent
	 */
	public EditorActionEvent2(java.awt.event.ActionEvent a, T v1, T2 v2) {
		this(a.getActionCommand(), v1, v2);
	}

	/**
	 * Value
	 *
	 * @return value
	 */
	public T2 getValue2() {
		return value2;
	}
}
