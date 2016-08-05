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
/*
 * @author  js
 * @version generated by NetBeans XML module
 */
package org.railsim.train;

import java.io.*;

import org.railsim.*;
import org.railsim.xml.*;
import org.xml.sax.*;

public class TrainsetsHandlerImpl implements TrainsetsHandler {

	static public boolean save(String filename) {
		FileWriter w = null;
		try {
			File f = new File(filename);
			w = new FileWriter(f);
			w.write("<?xml version='1.0' encoding='UTF-8'?>\n");
			//w.write("<!DOCTYPE SAX-bindings PUBLIC '-//XML Module//DTD SAX Bindings 1.0//EN' 'null'>\n");
			w.write("<trainsets version='1.0'>\n");

			for (trainset ts : dataCollector.collector.alltraindata.trainsets.values()) {
				w.write(" <set name='" + ts.getName() + "'>\n");
				for (stock s : ts.getStocks()) {
					w.write("  <stock id='" + s.getRollingstock().getId().getID() + "' forward='" + (s.forward ? "y" : "n") + "'/>\n");
				}
				w.write(" </set>\n");
			}
			w.write("</trainsets>\n");
			w.close();
		} catch (IOException ex) {
			dataCollector.collector.setStatus("Fehler beim Speichern des Züge!");
			try {
				w.close();
			} catch (IOException e) {
				dataCollector.collector.gotException(e);
			}
			return false;
		}
		return true;
	}
	public static final boolean DEBUG = false;
	private trainset currentSet = null;

	@Override
	public void start_trainsets(final Attributes meta) throws SAXException {
	}

	@Override
	public void end_trainsets() throws SAXException {
	}

	@Override
	public void handle_stock(final Attributes meta) throws SAXException {
		if (currentSet != null) {
			try {
				idtype id = new idtype(meta.getValue("id"));
				boolean f = meta.getValue("forward").compareTo("y") == 0;
				rollingstock rs = dataCollector.collector.getAllTrainData().stocks.get(id);
				if (rs != null) {
					currentSet.addStock(rs, f);
				}
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
	}

	@Override
	public void start_set(final Attributes meta) throws SAXException {
		currentSet = null;
		try {
			currentSet = new trainset(meta.getValue("name"));
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}

	@Override
	public void end_set() throws SAXException {
		if (currentSet != null) {
			dataCollector.collector.getAllTrainData().trainsets.put(currentSet.getName(), currentSet);
		}
		currentSet = null;
	}
}