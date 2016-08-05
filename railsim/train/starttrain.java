/*
 * $Revision: 23 $
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
import java.util.TreeSet;

import org.railsim.dataCollector;
import org.railsim.service.route;

/**
 *
 * @author js
 */
public class starttrain extends reducedstarttrain {

	private TreeSet<Integer> frequence = new TreeSet<>();
	private int vmax = 50;
	private route startroute = null;
	private boolean trainloaded = false;
	private LinkedList<stock> stocks = new LinkedList<>();   // copy of (clone) trainset stocks list!!
	private trainvalues tv = new trainvalues();

	/**
	 * Creates a new instance of starttrain
	 */
	public starttrain(String n) {
		super(n);
	}

	@Override
	public String toString() {
		if (startroute != null) {
			return name + ": alle " + frequence + " nach " + startroute.getName();
		} else {
			return name + ": alle " + frequence + " nach ???";
		}
	}

	private void updateValues() {
		tv = trainset.getValues(stocks, trainloaded);
	}

	public void setStocks(trainset ts) {
		synchronized (stocks) {
			stocks.clear();
			for (stock s : ts.getStocks()) {
				stocks.add((stock) s.clone());
			}
			updateValues();
		}
	}

	public void addStock(stock s) {
		synchronized (stocks) {
			stocks.add(s);
			updateValues();
		}
	}

	public void setFrequence(TreeSet<Integer> f) {
		frequence = f;
	}

	public void setVMax(int v) {
		vmax = v;
	}

	/**
	 * Getter for property VMax.
	 *
	 * @return Value of property VMax.
	 */
	public int getVMax() {
		return vmax;
	}

	/**
	 * Getter for property frequence.
	 *
	 * @return Value of property frequence.
	 */
	public TreeSet<Integer> getFrequence() {
		return frequence;
	}

	public void addFrequence(String f) {
		try {
			int v = Integer.parseInt(f);
			frequence.add(v);
		} catch (Exception ex) {
			dataCollector.collector.gotException(ex);
		}
	}

	public List<stock> getStocks() {
		return java.util.Collections.unmodifiableList(stocks);
	}

	/**
	 * Getter for property startroute.
	 *
	 * @return Value of property startroute.
	 */
	public route getStartroute() {
		return this.startroute;
	}

	/**
	 * Setter for property startroute.
	 *
	 * @param startroute New value of property startroute.
	 */
	public void setStartroute(route startroute) {
		this.startroute = startroute;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof reducedstarttrain) {
			return super.equals(o);
		} else if (o instanceof starttrain) {
			starttrain st = (starttrain) o;
			if (name == null) {
				return false;
			}

			return name.equals(st.name) && frequence == st.frequence && vmax == st.vmax && ((startroute == null && st.startroute == null) || (startroute != null && startroute.equals(st.startroute)));
		} else {
			return super.equals(o);
		}
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof reducedstarttrain) {
			return name.compareTo(((starttrain) o).name);
		} else if (o instanceof starttrain) {
			starttrain st = (starttrain) o;
			int r = 0;
			r = name.compareTo(st.name);
			//if (r==0)
			//  r=st.frequence.size()-frequence.size();
			if (r == 0) {
				r = st.vmax - vmax;
			}
			if (r == 0) {
				if (startroute != null && st.startroute == null) {
					r = 1;
				} else if (startroute == null && st.startroute != null) {
					r = -1;
				} else if (startroute != null && st.startroute != null) {
					r = startroute.getName().compareTo(st.startroute.getName());
				}
			}
			return r;
		} else {
			return name.compareTo((String) o);
		}
	}

	int getMaxVMax() {
		return tv.maxvmax;
	}

	int getWeight() {
		return tv.weight;
	}

	/**
	 * Getter for property trainloaded.
	 *
	 * @return Value of property trainloaded.
	 */
	public boolean isTrainloaded() {
		return this.trainloaded;
	}

	/**
	 * Setter for property trainloaded.
	 *
	 * @param trainloaded New value of property trainloaded.
	 */
	public void setTrainloaded(boolean trainloaded) {
		this.trainloaded = trainloaded;
		updateValues();
	}

	public boolean shouldStart(long time) {
		int m = dataCollector.getMinuteOfHour(time);
		return frequence.contains(m);
	}

	public fulltrain makeTrain() {
		if (startroute != null) {
			updateValues();
			fulltrain f = new fulltrain(this, "Z" + System.currentTimeMillis(), vmax, tv.acceleration, tv.deceleration, tv.weight);
			f.setRoute(startroute);
			synchronized (stocks) {
				for (stock s : stocks) {
					f.add(new trainpart(s.getRollingstock(), s.forward));
				}
			}
			return f;
		} else {
			return null;
		}
	}

	public void rotate() {
		synchronized (stocks) {
			LinkedList<stock> nstocks = new LinkedList<>();
			for (stock s : stocks) {
				s.rotate();
				nstocks.addFirst(s);
			}
			stocks.clear();
			stocks = nstocks;
			updateValues();
		}
	}
}
