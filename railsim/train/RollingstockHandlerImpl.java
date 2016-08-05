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

import java.io.File;

import org.railsim.*;
import org.railsim.xml.*;
import org.xml.sax.*;

public class RollingstockHandlerImpl implements RollingstockHandler {

	rollingstock currentStock = null;
	File f;

	public RollingstockHandlerImpl(File f) {
		this.f = f;
	}

	@Override
	public void start_rollingstock(final Attributes meta) throws SAXException {
		currentStock = new rollingstock(new idtype(meta.getValue("id")), meta.getValue("name"), f);
	}

	@Override
	public void end_rollingstock() throws SAXException {
		if (currentStock != null) {
			dataCollector.collector.alltraindata.stocks.put(currentStock.getId(), currentStock);
			currentStock = null;
		}
	}

	@Override
	public void handle_appearance(final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.setFilename(meta.getValue("filename"), Integer.parseInt(meta.getValue("angle")));
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}

	@Override
	public void handle_weight(final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.setWeight_empty(Integer.parseInt(meta.getValue("empty")));
				currentStock.setWeight_full(Integer.parseInt(meta.getValue("full")));
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}

	@Override
	public void handle_measure(final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.setAxisdistance(Integer.parseInt(meta.getValue("axisdistance")));
				currentStock.setFrontoverlap(Integer.parseInt(meta.getValue("frontoverlap")));
				currentStock.setBackoverlap(Integer.parseInt(meta.getValue("backoverlap")));
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}

	@Override
	public void handle_engine(final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.setEngine(new idtype(meta.getValue("id")));
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}

	@Override
	public void handle_speed(final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.setSpeed_empty(Integer.parseInt(meta.getValue("empty")));
				currentStock.setSpeed_full(Integer.parseInt(meta.getValue("full")));
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}

	@Override
	public void handle_coupling(final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.setCoupling_front(meta.getValue("font").startsWith("y"));
				currentStock.setCoupling_back(meta.getValue("back").startsWith("y"));
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}

	@Override
	public void handle_type(final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.addType(meta.getValue("name"));
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}

	@Override
	public void handle_drawappearance(final String data, final Attributes meta) throws SAXException {
		if (currentStock != null) {
			try {
				currentStock.drawElement(Integer.parseInt(meta.getValue("angle")), data);
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentStock = null;
			}
		}
	}
}