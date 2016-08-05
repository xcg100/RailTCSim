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

/**
 *
 * @author js
 */
public class objectDataStorage {

	static public final int TYPE_INT = 0;
	static public final int TYPE_FLOAT = 1;
	static public final int TYPE_STRING = 2;
	static public final int TYPE_BOOLEAN = 3;
	int valuetype = TYPE_INT;
	String key = "";
	int intvalue = 0;
	float floatvalue = 0;
	String stringvalue = "";
	boolean boolvalue = false;
	String description = "";
	boolean readonly = false;

	private void clear() {
		key = "";
		intvalue = 0;
		floatvalue = 0;
		stringvalue = "";
		boolvalue = false;
	}

	public objectDataStorage(int t, String d) {
		valuetype = t;
		clear();
		if (d.startsWith("-")) {
			description = d.substring(1);
			readonly = true;
		} else {
			description = d;
		}
	}

	public objectDataStorage(int t, String k, String d) {
		this(t, d);
		key = k;
	}

	public objectDataStorage(String k, String d, int v) {
		this(TYPE_INT, k, d);
		intvalue = v;
	}

	public objectDataStorage(String k, String d, float v) {
		this(TYPE_FLOAT, k, d);
		floatvalue = v;
	}

	public objectDataStorage(String k, String d, String v) {
		this(TYPE_STRING, k, d);
		stringvalue = v;
	}

	public objectDataStorage(String k, String d, boolean v) {
		this(TYPE_BOOLEAN, k, d);
		boolvalue = v;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof objectDataStorage) {
			return key.equalsIgnoreCase(((objectDataStorage) o).getKey());
		}
		return false;
	}

	public int getIntValue() {
		return intvalue;
	}

	public String getStringValue() {
		return stringvalue;
	}

	public float getFloatValue() {
		return floatvalue;
	}

	public boolean getBoolValue() {
		return boolvalue;
	}

	public String getValue() {
		switch (valuetype) {
			case TYPE_INT:
				return "" + intvalue;
			case TYPE_FLOAT:
				return "" + floatvalue;
			case TYPE_STRING:
				return stringvalue;
			case TYPE_BOOLEAN:
				return boolvalue ? "y" : "n";
		}
		return "";
	}

	public void setValue(String v) {
		if (valuetype == TYPE_STRING) {
			stringvalue = v;
		} else if (valuetype == TYPE_BOOLEAN) {
			setValue(v.startsWith("y"));
		} else if (valuetype == TYPE_INT) {
			setValue(Integer.parseInt(v));
		} else if (valuetype == TYPE_INT) {
			setValue(Float.parseFloat(v));
		}
	}

	public void setValue(int v) {
		if (valuetype == TYPE_INT) {
			intvalue = v;
		} else {
			setValue((float) v);
		}
	}

	public void setValue(float v) {
		if (valuetype == TYPE_FLOAT) {
			floatvalue = v;
		}
	}

	public void setValue(boolean v) {
		if (valuetype == TYPE_BOOLEAN) {
			boolvalue = v;
		}
	}

	public int getType() {
		return valuetype;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public boolean isReadonly() {
		return readonly;
	}
}
