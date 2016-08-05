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
package org.railsim.train;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author js
 */
public class trainset {

	private String name = "";
	private LinkedList<stock> stocks = new LinkedList<>();
	private trainvalues tv = new trainvalues();

	/**
	 * Creates a new instance of trainset
	 */
	public trainset(String n) {
		name = n;
	}

	/**
	 * Getter for property name.
	 *
	 * @return Value of property name.
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String n) {
		this.name = n;
	}

	@Override
	public String toString() {
		return name;
	}

	static private int getMaxVMax(stock s, boolean trainloaded) {
		if (trainloaded) {
			return s.getRollingstock().getSpeed_full();
		} else {
			return s.getRollingstock().getSpeed_empty();
		}
	}

	static private int getWeight(stock s, boolean trainloaded) {
		if (trainloaded) {
			return s.getRollingstock().getWeight_full();
		} else {
			return s.getRollingstock().getWeight_empty();
		}
	}

	static public trainvalues getValues(List<stock> stocks, boolean trainloaded) {
		trainvalues tv = new trainvalues();
		synchronized (stocks) {
			if (!stocks.isEmpty()) {
				for (stock s : stocks) {
					if (tv.maxvmax == 0) {
						tv.maxvmax = getMaxVMax(s, trainloaded);
					} else {
						tv.maxvmax = Math.min(tv.maxvmax, getMaxVMax(s, trainloaded));
					}
					tv.weight += getWeight(s, trainloaded);
					engine e = s.getRollingstock().getEngine();
					if (e != null) {
						tv.power += e.getPower();
						tv.deceleration = Math.max(tv.deceleration, e.getDeceleration());
						tv.maxacceleration = Math.max(tv.maxacceleration, e.getAcceleration());

						/*
						 *  P: Leistung (kW)
						 *  F: Kraft
						 *  m: Masse
						 *  a: Beschleunigung
						 *  P=F*a ; F=m*a ; a=v/t;  => F=P/a; P/a=m*a => P/a=m*a => P/m=a² => a=sqrt(P/m)
						 */
					}
				}
				tv.acceleration = Math.min((float) Math.sqrt(tv.power * 1000 / tv.weight), tv.maxacceleration);
			}
		}
		return tv;
	}

	private void updateValues() {
		tv = trainset.getValues(stocks, false);
	}

	public void addStock(rollingstock s, boolean f) {
		stocks.addLast(new stock(s, f));
		updateValues();
	}

	public void addStock(stock s) {
		stocks.addLast(s);
		updateValues();
	}

	public LinkedList<stock> getStocks() {
		return stocks;
	}

	public void clearStocks() {
		stocks.clear();
		tv = new trainvalues();
	}

	public int getVmax() {
		return tv.maxvmax;
	}

	public float getAccel() {
		return tv.acceleration;
	}
}
