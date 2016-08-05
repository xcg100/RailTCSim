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
package org.railsim.gui;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerListModel;

public class CyclingSpinnerListModel extends SpinnerListModel {

	Object firstValue, lastValue;
	SpinnerModel linkedModel = null;

	public CyclingSpinnerListModel(Object[] values) {
		super(values);
		firstValue = values[0];
		lastValue = values[values.length - 1];
	}

	public void setLinkedModel(SpinnerModel linkedModel) {
		this.linkedModel = linkedModel;
	}

	@Override
	public Object getNextValue() {
		Object value = super.getNextValue();
		if (value == null) {
			value = firstValue;
			if (linkedModel != null) {
				linkedModel.setValue(linkedModel.getNextValue());
			}
		}
		return value;
	}

	@Override
	public Object getPreviousValue() {
		Object value = super.getPreviousValue();
		if (value == null) {
			value = lastValue;
			if (linkedModel != null) {
				linkedModel.setValue(linkedModel.getPreviousValue());
			}
		}
		return value;
	}
}
