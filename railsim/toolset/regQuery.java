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
public class regQuery {

	private static final String REGQUERY_UTIL = "reg query ";
	private static final String REGSTR_TOKEN = "REG_SZ";
	private static final String REGDWORD_TOKEN = "REG_DWORD";
	private static final String PERSONAL_FOLDER_CMD = REGQUERY_UTIL
			+ "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\" + "Explorer\\Shell Folders\" /v Personal";
	private static final String CPU_SPEED_CMD = REGQUERY_UTIL
			+ "\"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\"" + " /v ~MHz";
	private static final String CPU_NAME_CMD = REGQUERY_UTIL
			+ "\"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\"" + " /v ProcessorNameString";

	public static String getCurrentUserPersonalFolderPath() {
		try {
			Process process = Runtime.getRuntime().exec(PERSONAL_FOLDER_CMD);
			StreamReader reader = new StreamReader(process.getInputStream());

			reader.start();
			process.waitFor();
			reader.join();

			String result = reader.getResult();
			int p = result.indexOf(REGSTR_TOKEN);

			if (p == -1) {
				return null;
			}

			return result.substring(p + REGSTR_TOKEN.length()).trim();
		} catch (IOException | InterruptedException e) {
			return null;
		}
	}

	public static String getCPUSpeed() {
		try {
			Process process = Runtime.getRuntime().exec(CPU_SPEED_CMD);
			StreamReader reader = new StreamReader(process.getInputStream());

			reader.start();
			process.waitFor();
			reader.join();

			String result = reader.getResult();
			int p = result.indexOf(REGDWORD_TOKEN);

			if (p == -1) {
				return null;
			}

			// CPU speed in Mhz (minus 1) in HEX notation, convert it to DEC
			String temp = result.substring(p + REGDWORD_TOKEN.length()).trim();
			return Integer.toString((Integer.parseInt(temp.substring("0x".length()), 16) + 1));
		} catch (IOException | InterruptedException | NumberFormatException e) {
			return null;
		}
	}

	public static String getCPUName() {
		try {
			Process process = Runtime.getRuntime().exec(CPU_NAME_CMD);
			StreamReader reader = new StreamReader(process.getInputStream());

			reader.start();
			process.waitFor();
			reader.join();

			String result = reader.getResult();
			int p = result.indexOf(REGSTR_TOKEN);

			if (p == -1) {
				return null;
			}

			return result.substring(p + REGSTR_TOKEN.length()).trim();
		} catch (IOException | InterruptedException e) {
			return null;
		}
	}

	static class StreamReader extends Thread {

		private InputStream is;
		private StringWriter sw;

		StreamReader(InputStream is) {
			this.is = is;
			sw = new StringWriter();
		}

		@Override
		public void run() {
			try {
				int c;
				while ((c = is.read()) != -1) {
					sw.write(c);
				}
			} catch (IOException e) {
				;
			}
		}

		String getResult() {
			return sw.toString();
		}
	}
}