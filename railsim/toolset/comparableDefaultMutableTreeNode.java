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
package org.railsim.toolset;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author js
 */
public class comparableDefaultMutableTreeNode extends DefaultMutableTreeNode implements java.lang.Comparable {

	Object updatedUserObject = null;

	public comparableDefaultMutableTreeNode(Object o) {
		super(o);
		updatedUserObject = o;
	}

	public comparableDefaultMutableTreeNode(Object o, boolean b) {
		super(o, b);
		updatedUserObject = o;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof comparableDefaultMutableTreeNode) {
			comparableDefaultMutableTreeNode o2 = (comparableDefaultMutableTreeNode) o;
			try {
				return ((java.lang.Comparable) getUserObject()).compareTo((java.lang.Comparable) o2.getUserObject());
			} catch (ClassCastException e) {
				return 0;
			}
		}
		return 0;
	}

	@Override
	public void setUserObject(Object userObject) {
		updatedUserObject = userObject;
	}

	public void setUserObject_real(Object userObject) {
		super.setUserObject(userObject);
	}

	public Object getUpdatedUserObject() {
		return updatedUserObject;
	}

	public comparableDefaultMutableTreeNode findObject(Object u) {
		if (getUserObject() != null && u != null && getUserObject().equals(u)) {
			return this;
		}
		if (getUserObject() == u) {
			return this;
		}

		Enumeration e = children();
		while (e.hasMoreElements()) {
			comparableDefaultMutableTreeNode e2 = (comparableDefaultMutableTreeNode) e.nextElement();
			comparableDefaultMutableTreeNode ret = e2.findObject(u);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}
}
