/*
 * $Revision: 22 $
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
package org.railsim.train;

import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.railsim.dataCollector;
import org.railsim.gui.progressDialog;
import org.railsim.gui.progressDialogRunnable;
import org.railsim.toolset.locationManager;
import org.railsim.xml.EngineParser;
import org.railsim.xml.RollingstockParser;
import org.railsim.xml.TrainsetsParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author js
 */
public class collection {

	static final String SETSFILE = "sets.xml";
	public static final String IMAGESDIR = "rollingstocks";
	public static final String IMAGESPACK = locationManager.JARPACK;
	public HashMap<idtype, engine> engines = new HashMap<>();
	public HashMap<idtype, rollingstock> stocks = new HashMap<>();
	public TreeMap<String, trainset> trainsets = new TreeMap<>();

	/**
	 * Creates a new instance of collection
	 */
	public collection() {
	}

	private void readStock(File fl, InputSource input) {
		RollingstockParser saxParser = new RollingstockParser(new RollingstockHandlerImpl(fl), null);
		try {
			saxParser.parse(input);
		} catch (IOException | ParserConfigurationException | SAXException ex) {
			ex.printStackTrace();
		}
	}

	private void readStock(File fl) {
		FileReader fr;
		try {
			fr = new FileReader(fl);
			readStock(fl, new InputSource(fr));
		} catch (FileNotFoundException ex) {
			dataCollector.collector.setStatus(fl.getName() + " nicht gefunden");
		}
	}

	private void readStock(String fl) {
		String fl2 = fl.substring(fl.indexOf("/"));
		InputStream u = collection.class.getResourceAsStream(fl2);
		if (u != null) {
			readStock(null, new InputSource(u));
		}
	}

	private void readEngine(File fl, InputSource input) {
		EngineParser saxParser = new EngineParser(new EngineHandlerImpl(), null);
		try {
			saxParser.parse(input);
		} catch (IOException | ParserConfigurationException | SAXException ex) {
			ex.printStackTrace();
		}
	}

	private void readEngine(File fl) {
		FileReader fr;
		try {
			fr = new FileReader(fl);
			readEngine(fl, new InputSource(fr));
		} catch (FileNotFoundException ex) {
			dataCollector.collector.setStatus(fl.getName() + " nicht gefunden");
		}
	}

	private void readEngine(String fl) {
		String fl2 = fl.substring(fl.indexOf("/"));
		InputStream u = collection.class.getResourceAsStream(fl2);
		if (u != null) {
			readEngine(null, new InputSource(u));
		}
	}

	private void readSets(File fl) {
		FileReader fr;
		try {
			fr = new FileReader(fl);

			InputSource input;
			input = new InputSource(fr);

			TrainsetsParser saxParser = new TrainsetsParser(new TrainsetsHandlerImpl(), null);
			try {
				saxParser.parse(input);
			} catch (	IOException | ParserConfigurationException | SAXException ex) {
				ex.printStackTrace();
			}
		} catch (FileNotFoundException ex) {
			dataCollector.collector.setStatus(fl.getName() + " nicht gefunden");
		}
	}

	private void writeSets() {
		String f = locationManager.getTrainsetsSave() + "/" + SETSFILE;
		TrainsetsHandlerImpl.save(f);
	}

	private LinkedList<File> readDir(File f, String suffix, LinkedList<File> flst) {
		File[] l = f.listFiles();
		if (l != null) {
			for (int i = 0; i < l.length; ++i) {
				if (l[i].isFile()) {
					if (l[i].getName().endsWith(suffix)) {
						flst.add(l[i]);
					}
				} else if (l[i].isDirectory()) {
					flst = readDir(l[i], suffix, flst);
				}
			}
		}
		return flst;
	}

	private LinkedList<File> readDir(String dir, String suffix, LinkedList<File> flst) {
		File f = new File(dir);
		flst = readDir(f, suffix, flst);
		return flst;
	}

	/**
	 * called by swing thread!
	 */
	public void save() {
		writeSets();
	}

	/**
	 * called by swing thread!
	 */
	public void load() {
		progressDialog pd = new progressDialog(dataCollector.collector.themain, "Initiales laden...");
		try {
			progressDialogRunnable r = new progressDialogRunnable() {
				@Override
				public void run(progressDialog p) {
					LinkedList<File> l;
					String[] d;

					p.setText("Lade Motoren...");
					l = new LinkedList<>();
					d = locationManager.getEngine();
					for (int i = 0; i < d.length; ++i) {
						if (d[i].startsWith("classpath:")) {
							readEngine(d[i]);
						} else {
							l = readDir(d[i], ".xml", l);
						}
					}
					for (File f : l) {
						readEngine(f);
					}
					p.setText("Lade Rollmaterial...");
					l = new LinkedList<>();
					d = locationManager.getRollingstock();
					for (int i = 0; i < d.length; ++i) {
						if (d[i].startsWith("classpath:")) {
							readStock(d[i]);
						} else {
							l = readDir(d[i], ".xml", l);
						}
					}
					for (File f : l) {
						readStock(f);
					}
					p.setText("Lade Züge...");
					d = locationManager.getTrainsets();
					for (int i = 0; i < d.length; ++i) {
						readSets(new File(d[i], SETSFILE));
					}
				}
			};
			pd.open(r);
			pd.close();
		} catch (Exception e) {
			pd.close();
			e.printStackTrace();
		}
	}

	void testIt() {
		rollingstock rs;
		rs = new rollingstock(new idtype("1"), "Test1", null);
		rs.addType("Testwagen");
		stocks.put(rs.getId(), rs);
		rs = new rollingstock(new idtype("2"), "Test2", null);
		rs.addType("Testwagen");
		rs.addType("Tester");
		rs.setEngine(new engine(new idtype("1.1"), "EL"));
		stocks.put(rs.getId(), rs);
	}
}
