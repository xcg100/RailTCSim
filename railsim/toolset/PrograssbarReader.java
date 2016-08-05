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

import java.io.*;

import org.railsim.gui.progressDialog;

/**
 *
 * @author js
 */
public class PrograssbarReader extends FilterReader {

	private long counter = 0;
	private progressDialog pd = null;

	/**
	 * Creates a new instance of CountInputStream
	 */
	public PrograssbarReader(Reader in, progressDialog _pd, int maxsize) {
		super(in);
		pd = _pd;
		pd.setMax(maxsize);
	}

	public PrograssbarReader(Reader in, progressDialog _pd) {
		super(in);
		pd = _pd;
	}

	public PrograssbarReader(Reader in) {
		super(in);
	}

	@Override
	public int read() throws IOException {
		int ret = this.in.read();
		if (ret >= 0) {
			counter++;
			if (pd != null) {
				pd.setValue(counter);
			}
		}
		return ret;
	}

	@Override
	public int read(char[] b, int off, int len) throws IOException {
		int ret = this.in.read(b, off, len);
		if (ret >= 0) {
			counter += ret;
			if (pd != null) {
				pd.setValue(counter);
			}
		}
		return ret;
	}

	public long getCount() {
		return counter;
	}
}
