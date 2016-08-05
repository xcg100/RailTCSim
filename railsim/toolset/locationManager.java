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
package org.railsim.toolset;

import java.io.File;
import java.net.URL;

/**
 *
 * @author js
 */
public class locationManager {

	static final String ENGINEDIR = "engines";
	static final String STOCKSDIR = "rollingstocks";
	static final String SETSSDIR = "set";
	static final String SAVEDIR = "tracks";
	static final public String JARPACK = "/org/railsim/train/include";
	static final String ENGINEPACK = "classpath:" + JARPACK + "/engines.xml";
	static final String STOCKPACK = "classpath:" + JARPACK + "/stocks.xml";
	static String programdir = null;
	static String userdir = null;

	static {
		if (System.getProperty("os.name").startsWith("Windows")) {
			// regQuery.getCurrentUserPersonalFolderPath()
			userdir = regQuery.getCurrentUserPersonalFolderPath();
			userdir = new File(userdir, ".SimplyTrain").getPath();
		} else {
			userdir = System.getProperty("user.home");
			userdir = new File(userdir, ".SimplyTrain").getPath();
		}

		URL url = ClassLoader.getSystemResource("."); // TODO
		if (url != null) {
			programdir = url.getFile();
		} else {
			programdir = System.getProperty("user.dir");
		}

		File fl1;
		fl1 = new File(userdir, ENGINEDIR);
		fl1.mkdirs();
		fl1 = new File(userdir, STOCKSDIR);
		fl1.mkdirs();
		fl1 = new File(userdir, SETSSDIR);
		fl1.mkdirs();
		fl1 = new File(userdir, SAVEDIR);
		fl1.mkdirs();
	}

	static public String[] getEngine() {
		return new String[]{
					ENGINEPACK, programdir + "/" + ENGINEDIR, userdir + "/" + ENGINEDIR
				};
	}

	static public String[] getRollingstock() {
		return new String[]{
					STOCKPACK, programdir + "/" + STOCKSDIR, userdir + "/" + STOCKSDIR
				};
	}

	static public String[] getTrainsets() {
		return new String[]{
					userdir + "/" + SETSSDIR
				};
	}

	static public String getTrainsetsSave() {
		return userdir + "/" + SETSSDIR;
	}

	static public String[] getSavegame() {
		return new String[]{
					userdir + "/" + SAVEDIR
				};
	}
}
