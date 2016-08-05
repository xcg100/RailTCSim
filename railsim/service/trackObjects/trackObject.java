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

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.railsim.dataCollector;
import org.railsim.editor.EditorEvent;
import org.railsim.service.*;
import org.railsim.train.fulltrain;

/**
 * Any track object, signal, speed limit, etc
 *
 * @author js
 */
public abstract class trackObject implements Cloneable, genericPaintInterface, Comparable {

	protected abstract class runlater {

		protected fulltrain ft;

		public runlater(fulltrain f) {
			ft = f;
		}

		public abstract void run();
	}
	/**
	 * Train start
	 */
	public static final int TRAINSTEP_START = 0x01;
	/**
	 * Train part - may include start
	 */
	public static final int TRAINSTEP_PART = 0x02;
	/**
	 * Train end
	 */
	public static final int TRAINSTEP_END = 0x04;
	/**
	 * Train prerunner
	 */
	public static final int TRAINSTEP_PRERUNNER = 0x08;
	/**
	 * No requirement
	 */
	public static final int REQUIREMENT_NONE = 0x00;
	/**
	 * Build/remove only allowed if no train on track
	 */
	public static final int REQUIREMENT_NOTRAIN = 0x01;
	/**
	 * Build/remove only allowed if track not part of a path
	 */
	public static final int REQUIREMENT_NOPATH = 0x02;
	/**
	 * Build/remove only allowed if track not building
	 */
	public static final int REQUIREMENT_NOBUILD = 0x04;
	/**
	 * Build/remove only allowed if track has not path set
	 */
	public static final int REQUIREMENT_NOSETPATH = 0x08;
	/**
	 * trackObjectAnim
	 */
	static public trackObjectAnim toa = new trackObjectAnim();
	static public ConcurrentHashMap<trackObject, String> allto = new ConcurrentHashMap<>();
	static public TreeSet<String> allregions = new TreeSet<>();
	static private int tocounter = 0;
	static private java.util.concurrent.ConcurrentLinkedQueue<runlater> runlater = new java.util.concurrent.ConcurrentLinkedQueue<>();
	private boolean leftside = false;

	static public trackObject findTObyName(String name) {
		for (trackObject to : allto.keySet()) {
			if (to.getName().compareTo(name) == 0) {
				return to;
			}
		}
		return null;
	}
	private String name = "";
	private String region = "";
	private Graphics2D lastg2 = null;
	private int g2cycle = 0;
	private int temphash = 0;
	private boolean paintAlways = true;
	/**
	 * bounds
	 */
	protected rectangle bounds = null;

	protected trackObject() {
		name = "to." + tocounter;
		region = "undef";
		tocounter++;
		allto.put(this, name);
		allregions.add(region);
		temphash = hashCode();
	}

	protected trackObject(String n) {
		name = n;
		region = "undef";
		allto.put(this, name);
		allregions.add(region);
		temphash = hashCode();
	}

	protected trackObject(String n, String r) {
		name = n;
		if (r != null) {
			region = r;
		} else {
			r = "undef";
		}
		allto.put(this, name);
		allregions.add(region);
		temphash = hashCode();
	}

	/**
	 *
	 * @param width
	 * @param height
	 */
	protected trackObject(String n, String r, int width, int height) {
		this(n, r);
		setBounds(width, height);
	}

	/**
	 * call paintAnim() count times
	 *
	 * @param count number of steps
	 */
	public void registerAnim(String userdata, int count) {
		toa.registerAnim(this, userdata, count);
	}

	/**
	 * current step of registered anim
	 *
	 * @return current step
	 */
	public int getAnimState(String userdata) {
		return toa.getAminState(this, userdata);
	}

	/**
	 * paint track object
	 *
	 * @param g2 Graphics, 0/0 is inside track, X+ is always right of track, Y+ is always reverse direction
	 */
	final public void paintTO(Graphics2D g2) {
		if (leftside) {
			g2.translate(-getBounds().width, 0);
		}
		lastg2 = g2;
		g2cycle = dataCollector.collector.thepainter.getGraphicsCycle();
		paint(g2);
	}

	/**
	 * paint track object anim
	 *
	 * @param count current step
	 */
	final public void paintAnimTO(String userdata, int count) {
		paintAnim(g2cycle == dataCollector.collector.thepainter.getGraphicsCycle() ? lastg2 : null, userdata, count);
	}

	/**
	 * paint update of trackObject w/o repainting complete tracks
	 */
	final public void update() {
		if (lastg2 != null && g2cycle == dataCollector.collector.thepainter.getGraphicsCycle()) {
			paint(lastg2);
		}
	}

	/**
	 * fire VALUECHANGED Editor Event
	 */
	final public void fireValueChanged() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				fireValueChanged_run();
			}
		});
	}

	void fireValueChanged_run() {
		dataCollector.collector.editorEvent(EditorEvent.TRACKOBJECT_VALUECHANGED, this);
	}
	fulltrain lastTrain = null;

	final public boolean updateTrainT(fulltrain ft, int step) {
		if ((step & TRAINSTEP_PRERUNNER) != 0 || (step & TRAINSTEP_START) != 0) {
			lastTrain = ft;
		}
		boolean r = updateTrain(ft, step);
		if ((step & TRAINSTEP_END) != 0) {
			lastTrain = null;
		}
		return r;
	}

	/**
	 * called if train reaches object for this direction
	 *
	 * @return return true if train was modified - not yet used
	 * @param step part of train - TRAINSTEP_..., logical OR
	 * @param ft train
	 */
	abstract public boolean updateTrain(fulltrain ft, int step);

	/**
	 * paint track object
	 *
	 * @param g2 Graphics, 0/0 is inside track, X+ is always right of track, Y+ is always reverse direction
	 */
	@Override
	abstract public void paint(Graphics2D g2);

	/**
	 * paint track object
	 *
	 * @param g2 Graphics, 0/0 is inside track, X+ is always right of track, Y+ is always reverse direction
	 */
	@Override
	abstract public void paintIcon(Graphics2D g2);

	/**
	 * paint track object animated
	 *
	 * @param count step of anim
	 * @param g2 Graphics, 0/0 is inside track, X+ is always right of track, Y+ is always reverse direction - can be null
	 */
	abstract protected void paintAnim(Graphics2D g2, String userdata, int count);

	/**
	 * Bounds of object
	 *
	 * @return bounds
	 */
	public rectangle getBounds() {
		return bounds;
	}

	/**
	 * name of object
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set name
	 *
	 * @param n new name
	 */
	public void setName(String n) {
		name = n;
		allto.put(this, name);
		dataCollector.collector.trackObjectsChanged();
	}

	/**
	 * region of object
	 *
	 * @return region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * set region
	 *
	 * @param n new region
	 */
	public void setRegion(String n) {
		if (n == null) {
			n = "undef";
		}
		region = n;
		allregions.add(region);
	}

	/**
	 * get type name - java class name
	 *
	 * @return type name
	 */
	public String getTypeName() {
		return getClass().getSimpleName();
	}

	/**
	 * call if track object removed - removes it from internal lists like anim
	 */
	public void remove() {
		allto.remove(this);
		dataCollector.collector.trackObjectsChanged();
	}

	/**
	 * parameters of trackObject
	 *
	 * @return todHashSet with parameters
	 * @abstract
	 */
	public odsHashSet getData() {
		odsHashSet h = new odsHashSet();
		// TODO leftside: Bounds, Darstellung, ggf. Unterobjekt muß leftside beachten
		//h.add(new objectDataStorage("leftside","linksseitig",leftside));
		return h;
	}

	/**
	 * set parameters of trackObject
	 *
	 * @param value todHashSet with parameters, missing parameters are not changed
	 * @abstract
	 */
	public void setData(odsHashSet hm) {
		for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
			objectDataStorage key = it.next();
			if (key.getKey().compareTo("leftside") == 0) {
				leftside = key.getBoolValue();
			}
		}
	}

	/**
	 * set parameter of trackObject
	 *
	 * @param key key of parameter
	 * @param value value of parameter
	 */
	public void setData(String key, String value) {
		odsHashSet h = getData();
		objectDataStorage tod = h.get(key);
		if (tod != null) {
			tod.setValue(value);
			h.reduceTo(tod);
			setData(h);
		}
	}

	/**
	 *
	 * @param width
	 * @param height
	 */
	protected void setBounds(int width, int height) {
		bounds = new rectangle(0, 0, width, height);
	}

	/**
	 *
	 * @return
	 */
	@Override
	abstract public trackObject clone();

	/**
	 * GUI Name of Object Type
	 *
	 * @return GUI Name
	 */
	public abstract String getGUIObjectName();

	@Override
	public int getPaintOrientation() {
		return 0;
	}

	/**
	 * GUI Name of Object Type
	 *
	 * @return GUI Name
	 * @see getGUIObjectName()
	 */
	@Override
	public String getLabel() {
		return getGUIObjectName();
	}

	/**
	 * GUI Name of Object Type
	 *
	 * @return GUI Name
	 * @see getGUIObjectName()
	 */
	@Override
	public String toString() {
		return getGUIObjectName();
	}

	/**
	 * Compare 2 objects by trackObject NAME
	 *
	 * @param o other object
	 * @return
	 */
	@Override
	public int compareTo(Object o) {
		if (o instanceof trackObject) {
			trackObject o2 = (trackObject) o;
			return (getRegion() + getName()).compareToIgnoreCase(o2.getRegion() + o2.getName());
		} else if (o instanceof String) {
			String o2 = (String) o;
			return (getRegion() + getName()).compareToIgnoreCase(o2);
		}
		return 0;
	}

	/**
	 * Compare 2 objects by trackObject TYPE
	 *
	 * @param o other object
	 * @return true if other object is of same trackObject TYPE!
	 */
	@Override
	public boolean equals(Object o) {
		String cmp;
		if (o instanceof trackObject) {
			trackObject o2 = (trackObject) o;
			if (o2.top != null && top != null) {
				if ((o2.getRegion() + o2.getName()).compareTo(getRegion() + getName()) == 0) {
					return true;
				}
			} else {
				if (o2.getTypeName().compareTo(getTypeName()) == 0) {
					return true;
				}
			}
		} else if (o instanceof String) {
			String o2 = (String) o;
			if (top != null) {
				if (o2.compareTo(getRegion() + getName()) == 0) {
					return true;
				}
			} else {
				if (o2.compareTo(getTypeName()) == 0) {
					return true;
				}
			}
		}
		return false;
	}
	private trackobjectpoint top = null;

	final public void setTrackData(trackobjectpoint p) {
		top = p;
	}

	final public trackobjectpoint getTrackData() {
		return top;
	}

	final public void setTemporaryHash(int h) {
		temphash = h;
	}

	final public int getTemporaryHash() {
		return temphash;
	}

	final protected void setPaintAlways(boolean a) {
		paintAlways = a;
	}

	final public boolean getPaintAlways() {
		return paintAlways;
	}

	public int getRequirements() {
		return REQUIREMENT_NOBUILD;
	}

	protected void runLater(runlater r) {
		runlater.add(r);
	}

	static public void runLater() {
		while (!runlater.isEmpty()) {
			runlater r = runlater.poll();
			try {
				r.run();
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
	}
}
