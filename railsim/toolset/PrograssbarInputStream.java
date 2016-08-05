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

/**
 *
 * @author js
 */
public class PrograssbarInputStream extends FilterInputStream {

	private long counter = 0;

	/**
	 * Creates a new instance of CountInputStream
	 */
	public PrograssbarInputStream(InputStream in) {
		super(in);
	}

	@Override
	public int read() throws IOException {
		int ret = this.in.read();
		if (ret >= 0) {
			counter++;
		}
		return ret;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int ret = this.in.read(b);
		if (ret >= 0) {
			counter += ret;
		}
		return ret;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int ret = this.in.read(b, off, len);
		if (ret >= 0) {
			counter += ret;
		}
		return ret;
	}

	public long getCount() {
		return counter;
	}
}
