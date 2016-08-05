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
package org.railsim.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.railsim.*;
import org.railsim.service.trackObjects.objectDataStorage;
import org.railsim.service.trackObjects.pathableObject;
import org.railsim.service.trackObjects.trackObject;
import org.railsim.service.trackObjects.trainStartObject;
import org.railsim.service.trainCommands.trainCommand;
import org.railsim.train.fulltrain;
import org.railsim.train.idtype;
import org.railsim.train.rollingstock;
import org.railsim.train.starttrain;
import org.railsim.train.stock;
import org.railsim.xml.*;
import org.xml.sax.*;

import java.util.*;

public class GamestoreHandlerImpl implements GamestoreHandler {
	/*
	 * ---------------------------------------------------------------------------------------------
	 * Writer
	 * ---------------------------------------------------------------------------------------------
	 */

	static int storeHash = 0;

	static private void writeConnect(FileWriter w, int id, String type, track destT, boolean set) throws IOException {
		int destid = -1;
		for (int i = 0; i < dataCollector.collector.thepainter.tracks.size(); ++i) {
			track t = dataCollector.collector.thepainter.tracks.get(i);
			if (t.equals(destT)) {
				destid = i;
				break;
			}
		}
		if (destid >= 0) {
			w.write("  <connect ");
			w.write("id='" + id + "' ");
			w.write("type='" + type + "' ");
			w.write("destid='" + destid + "' ");
			if (set) {
				w.write("set='y' ");
			}
			w.write("/>\n");
		}
	}

	static private void writeTO(FileWriter w, java.util.List<trackObject> tol, boolean forward) throws IOException {
		int i = 0;
		for (trackObject to : tol) {
			if (to != null) {
				to.setTemporaryHash(++storeHash);
				w.write("   <trackobject index='" + i + "' forward='" + (forward ? "y" : "n") + "'");
				w.write(" name='" + to.getName() + "' region='" + to.getRegion() + "' ");
				w.write(" hash='" + to.getTemporaryHash() + "' ");
				w.write(" type='" + to.getTypeName() + "'>\n");
				odsHashSet hm = to.getData();
				if (hm != null) {
					for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
						objectDataStorage key = it.next();
						if (dataCollector.collector.testSaveGame) {
							if (key.getKey().compareTo("cstate") == 0) {
								w.write("    <data key='" + key.getKey() + "' value='0'/>\n");
							} else {
								w.write("    <data key='" + key.getKey() + "' value='" + key.getValue() + "'/>\n");
							}
						} else {
							w.write("    <data key='" + key.getKey() + "' value='" + key.getValue() + "'/>\n");
						}
					}
				}
				w.write("   </trackobject>\n");
			}
			++i;
		}
	}

	static public boolean save(String filename) {
		dataCollector.collector.stopAll();
		FileWriter w = null;
		File f = new File(filename);
		storeHash = 0;
		try {
			int tgc = 0;
			for (trackGroup t : dataCollector.collector.thepainter.trackgroups) {
				tgc++;
				t.setTemporaryHash(tgc);
			}
			w = new FileWriter(f);
			w.write("<?xml version='1.0' encoding='UTF-8'?>\n");
			//w.write("<!DOCTYPE SAX-bindings PUBLIC '-//XML Module//DTD SAX Bindings 1.0//EN' 'null'>\n");
			w.write("<savegame version='1.0' name='" + f.getName() + "'>\n");
			dataCollector.collector.thepainter.tracks.readLock();

			w.write(" <time");
			w.write(" time='" + dataCollector.collector.getTime() + "'");
			w.write(" lastemitter='" + dataCollector.collector.thepainter.getLastEmitterTime() + "'");
			w.write(" x='" + dataCollector.collector.thepainter.getX() + "'");
			w.write(" y='" + dataCollector.collector.thepainter.getY() + "'");
			w.write("/>\n");

			w.write(" <tracks x='" + dataCollector.collector.thepainter.getX() + "' y='" + dataCollector.collector.thepainter.getY() + "' scale='" + dataCollector.collector.thepainter.getScale() + "'>\n");
			for (track t : dataCollector.collector.thepainter.tracks) {
				w.write("  <track ");
				w.write("x='" + t.getXY().getX() + "' ");
				w.write("y='" + t.getXY().getY() + "' ");
				w.write("rotation='" + t.getRotation() + "' ");
				w.write("angel='" + t.getBow() + "' ");
				w.write("length='" + t.getLength() + "' ");
				w.write("level='" + t.getLevel() + "' ");
				if (t.getProgress() > 0) {
					w.write("progress='" + t.getProgress() + "' ");
				}
				if (t.isInGroup()) {
					w.write("group='" + t.getGroup().getTemporaryHash() + "' ");
				}
				w.write(">\n");
				String[] d = t.getPainterData();
				for (int i = 0; i < d.length; ++i) {
					if (d[i] != null && d[i].length() > 0) {
						w.write("   <data key='pd' value='" + d[i] + "'/>\n");
					}
				}
				java.util.List<trackObject> tol;
				tol = t.getTrackObjects(false);
				writeTO(w, tol, false);
				tol = t.getTrackObjects(true);
				writeTO(w, tol, true);
				w.write("  </track>\n");
			}
			for (int i = 0; i < dataCollector.collector.thepainter.tracks.size(); ++i) {
				track t = dataCollector.collector.thepainter.tracks.get(i);
				track t2;
				t2 = t.getPrev();
				if (t2 != null) {
					writeConnect(w, i, "prev", t2, false);
				}
				int c = t.getJunctionCount();
				int p = t.getJunction();
				for (int j = 0; j < c; ++j) {
					t2 = t.getNext(j);
					if (t2 != null) {
						writeConnect(w, i, "next" + j, t2, p == j);
					}
				}
			}
			w.write(" </tracks>\n");

			synchronized (route.allroutes) {
				w.write(" <routes>\n");
				for (route r : route.allroutes.values()) {
					w.write("  <route name='" + r.getName() + "'>\n");
					for (trainCommand tc : r.commands) {
						w.write("   <command name='" + tc.getName() + "' type='" + tc.getTypeName() + "'>\n");
						odsHashSet hm = tc.getData();
						if (hm != null) {
							for (Iterator<objectDataStorage> it = hm.iterator(); it.hasNext();) {
								objectDataStorage key = it.next();
								w.write("    <data key='" + key.getKey() + "' value='" + key.getValue() + "'/>\n");
							}
						}
						w.write("   </command>\n");
					}
					w.write("  </route>\n");
				}
				w.write(" </routes>\n");
			}

			for (trackObject to : trackObject.allto.keySet()) {
				if (to instanceof pathableObject) {
					if (!((pathableObject) to).getAllPaths().isEmpty()) {
						w.write(" <signaldata hash='" + to.getTemporaryHash() + "'>\n");
						for (path p : ((pathableObject) to).getAllPaths()) {
							w.write("  <path name='" + p.getName() + "' state='" + p.getStartState() + "' mode='" + (p.isAutomatic() ? "automatic" : "-") + "'");
							if (p.isFailure()) {
								w.write(" failure='y'");
							}
							w.write(" enabled='" + (p.isEnabled() ? "y" : "n") + "'");
							w.write(">\n");
							for (pathtrack pt : p.getTracks()) {
								int id = dataCollector.collector.thepainter.tracks.indexOf(pt.getTrack());
								w.write("   <pathelement id='" + id + "' ");
								if (pt.isJunction()) {
									w.write(" set='" + (pt.getJunctionstate() > 0 ? "y" : "n") + "'");
								}
								w.write(" cstate='" + (!dataCollector.collector.testSaveGame && pt.getTrack().getReserved() == p ? "set" : "-") + "' />\n");
							}
							for (pathroute pr : p.getRoutes()) {
								w.write("   <pathroute name='" + pr.getRoute().getName() + "' priority='" + pr.getPriority() + "'>\n");
								for (path p2 : pr.getMutex()) {
									w.write("    <mutex name='" + p.getName() + "' />\n");
								}
								w.write("   </pathroute>\n");
							}

							w.write("  </path>\n");
						}
						w.write(" </signaldata>\n");
					}
				} else if (to instanceof trainStartObject) {
					LinkedList<starttrain> stl = ((trainStartObject) to).getTrains();
					if (stl != null && stl.size() > 0) {
						w.write(" <trainemitter hash='" + to.getTemporaryHash() + "'>\n");
						for (starttrain st : stl) {
							w.write("  <emit name='" + st.getName() + "' "
									+ "vmax='" + st.getVMax() + "' "
									+ "loaded='" + (st.isTrainloaded() ? "y" : "n") + "' "
									+ "route='" + (st.getStartroute() != null ? st.getStartroute().getName() : "") + "'>\n");
							for (Integer fr : st.getFrequence()) {
								w.write("    <frequence t='" + fr + "'/>\n");
							}
							for (stock s : st.getStocks()) {
								w.write("    <epart id='" + s.getRollingstock().getId().getID() + "' forward='" + (s.forward ? "y" : "n") + "'/>\n");
							}
							w.write("  </emit>\n");
						}
						w.write("  <startqueue>\n");
						java.util.concurrent.ConcurrentLinkedQueue<starttrain> stq = ((trainStartObject) to).getQueue();
						for (starttrain st : stq) {
							w.write("   <emit name='" + st.getName() + "' "
									+ "vmax='" + st.getVMax() + "' "
									+ "loaded='" + (st.isTrainloaded() ? "y" : "n") + "' "
									+ "route='" + (st.getStartroute() != null ? st.getStartroute().getName() : "") + "'>\n");
							for (Integer fr : st.getFrequence()) {
								w.write("     <frequence t='" + fr + "'/>\n");
							}
							for (stock s : st.getStocks()) {
								w.write("     <epart id='" + s.getRollingstock().getId().getID() + "' forward='" + (s.forward ? "y" : "n") + "'/>\n");
							}
							w.write("   </emit>\n");
						}
						w.write("  </startqueue>\n");
						w.write(" </trainemitter>\n");
					}
				}
			}
			dataCollector.collector.thepainter.tracks.readUnlock();

			w.write(" <trains>\n");
			dataCollector.collector.thepainter.trains.readLock();
			for (fulltrain ft : dataCollector.collector.thepainter.trains) {
				w.write(ft.getSaveString());
			}
			dataCollector.collector.thepainter.trains.readUnlock();
			w.write(" </trains>\n");

			if (!dataCollector.collector.testSaveGame) {
				w.write(" <pathqueue>\n");
				for (pathRequest pr : dataCollector.collector.thepainter.getPathqueue()) {
					w.write("  <pathrequest " + (pr.r != null ? "route='" + pr.r.getName() + "' " : "") + "train='" + pr.ft.getName() + "' priocounter='" + pr.priocounter + "'>\n"); // TODO: train
					for (path p : pr) {
						w.write("  <requestedpath hash='" + p.getSignal().getTemporaryHash() + "' name='" + p.getName() + "' />\n");
					}
					w.write("  </pathrequest>\n");
				}
				w.write(" </pathqueue>\n");
			}

			w.write("</savegame>\n");
			w.close();
		} catch (IOException ex) {
			dataCollector.collector.setStatus("Fehler beim Speichern!");
			try {
				w.close();
			} catch (IOException e) {
				dataCollector.collector.gotException(e);
			}
			dataCollector.collector.startSim();
			return false;
		}
		dataCollector.collector.setStatus("Anlage gespeichert!");
		dataCollector.collector.startSim();
		return true;
	}
	/*
	 * ---------------------------------------------------------------------------------------------
	 * Loader
	 * ---------------------------------------------------------------------------------------------
	 */
	public static final boolean DEBUG = false;
	private painter thepainter = null;
	private track lastTrack = null;
	private trackObject lastTrackObject = null;
	private trainCommand lastCommand = null;
	private trainStartObject currentStart = null;
	private starttrain currentStarttrain = null;
	private boolean inStartqueue = false;
	private fulltrain currentFullTrain = null;

	public GamestoreHandlerImpl(painter p) {
		thepainter = p;
	}

	@Override
	public void handle_connect(final Attributes meta) throws SAXException {
		if (DEBUG) {
			System.err.println("handle_connect: " + meta);
		}
		thepainter.connectXML(meta);
	}

	@Override
	public void start_tracks(final Attributes meta) throws SAXException {
		if (DEBUG) {
			System.err.println("start_tracks: " + meta);
		}
		try {
			int x = Integer.parseInt(meta.getValue("x"));
			int y = Integer.parseInt(meta.getValue("y"));
			thepainter.setPos(x, y);
		} catch (Exception e) {
		}
		try {
			double s = Double.parseDouble(meta.getValue("scale"));
			thepainter.setScale(s);
		} catch (Exception e) {
		}
	}

	@Override
	public void end_tracks() throws SAXException {
		if (DEBUG) {
			System.err.println("end_tracks()");
		}
	}

	@Override
	public void start_savegame(final Attributes meta) throws SAXException {
		if (DEBUG) {
			System.err.println("start_savegame: " + meta);
		}
	}

	@Override
	public void end_savegame() throws SAXException {
		if (DEBUG) {
			System.err.println("end_savegame()");
		}
	}

	@Override
	public void handle_data(final Attributes meta) throws SAXException {
		if (lastTrackObject != null) {
			lastTrackObject.setData(meta.getValue("key"), meta.getValue("value"));
		} else if (lastTrack != null && (meta.getValue("key") != null && meta.getValue("key").compareTo("pd") == 0)) {
			lastTrack.setPainterData(meta.getValue("value"));
		} else if (lastCommand != null) {
			lastCommand.setData(meta.getValue("key"), meta.getValue("value"));
		}
	}

	@Override
	public void start_track(final Attributes meta) throws SAXException {
		lastTrack = thepainter.addTrackXML(meta);
	}

	@Override
	public void end_track() throws SAXException {
		lastTrack = null;
	}

	@Override
	public void start_trackobject(final Attributes meta) throws SAXException {
		lastTrackObject = thepainter.addTrackObjectXML(lastTrack, meta);
	}

	@Override
	public void end_trackobject() throws SAXException {
		lastTrackObject = null;
	}
	private pathableObject currentSignal = null;
	private path currentPath = null;

	@Override
	public void start_path(final Attributes meta) throws SAXException {
		if (currentSignal != null) {
			currentPath = new path(currentSignal, meta.getValue("name"));
			currentSignal.addPath(currentPath);

			int state = Integer.parseInt(meta.getValue("state"));
			currentPath.setStartState(state);
			String mode = meta.getValue("mode");
			if (mode != null && mode.compareTo("automatic") == 0) {
				currentPath.setAutomatic(true);
			}
			String enabled = meta.getValue("enabled");
			if (enabled != null && enabled.compareTo("n") == 0) {
				currentPath.setEnabled(false);
			} else {
				currentPath.setEnabled(true);
			}
			String failure = meta.getValue("failure");
			if (failure != null && enabled.compareTo("y") == 0) {
				currentPath.setFailure(true);
			} else {
				currentPath.setFailure(false);
			}
		} else {
			currentPath = null;
		}
	}

	@Override
	public void end_path() throws SAXException {
		currentPath = null;
	}

	@Override
	public void handle_pathelement(final Attributes meta) throws SAXException {
		if (currentPath != null) {
			int in = Integer.parseInt(meta.getValue("id"));
			track t = dataCollector.collector.thepainter.tracks.get(in);
			if (meta.getValue("set") != null) {
				currentPath.addTrack(t, meta.getValue("set").compareToIgnoreCase("y") == 0 ? 1 : 0);
			} else {
				currentPath.addTrack(t);
			}
			String cstate = meta.getValue("cstate");
			if (cstate != null && cstate.compareTo("set") == 0) {
				t.setReserve(currentPath);
			}
		}
	}

	@Override
	public void start_signaldata(final Attributes meta) throws SAXException {
		currentSignal = null;
		int h = Integer.parseInt(meta.getValue("hash"));
		for (trackObject to : trackObject.allto.keySet()) {
			if (to instanceof pathableObject && to.getTemporaryHash() == h) {
				currentSignal = (pathableObject) to;
			}
		}
	}

	@Override
	public void end_signaldata() throws SAXException {
		currentSignal = null;
	}
	private pathroute currentPathRoute = null;

	@Override
	public void start_pathroute(final Attributes meta) throws SAXException {
		if (currentPath != null) {
			currentPathRoute = null;
			try {
				String n = meta.getValue("name");
				route r = route.allroutes.get(n);
				currentPathRoute = new pathroute();
				currentPathRoute.setRoute(r);
				int p = Integer.parseInt(meta.getValue("priority"));
				currentPathRoute.setPriority(p);
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
				currentPathRoute = null;
			}
		}
	}

	@Override
	public void end_pathroute() throws SAXException {
		if (currentPath != null && currentPathRoute != null) {
			currentPath.addRoute(currentPathRoute);
			currentPathRoute = null;
		}
	}

	@Override
	public void handle_mutex(final Attributes meta) throws SAXException {
		if (currentPathRoute != null) {
			try {
				String pname = meta.getValue("name");

				for (trackObject to : trackObject.allto.keySet()) {
					if (to instanceof pathableObject && to.getTrackData() != null) {
						pathableObject po = (pathableObject) to;
						TreeSet<path> apath = po.getAllPaths();
						for (path p : apath) {
							if (p.getName().compareTo(pname) == 0) {
								currentPathRoute.addMutex(p);
							}
						}
					}
				}
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
	}

	@Override
	public void start_routes(final Attributes meta) throws SAXException {
	}

	@Override
	public void end_routes() throws SAXException {
	}
	private route currentRoute = null;

	@Override
	public void start_route(final Attributes meta) throws SAXException {
		currentRoute = route.findOrCreateRoute(meta.getValue("name"));
	}

	@Override
	public void end_route() throws SAXException {
		currentRoute = null;
	}
	pathRequest currentPathRequest = null;

	@Override
	public void start_pathqueue(final Attributes meta) throws SAXException {
	}

	@Override
	public void end_pathqueue() throws SAXException {
	}

	@Override
	public void start_pathrequest(final Attributes meta) throws SAXException {
		currentPathRequest = null;
		try {
			String r = meta.getValue("route");
			String ft = meta.getValue("train");
			int prioc = Integer.parseInt(meta.getValue("priocounter"));

			currentPathRequest = new pathRequest(ft, r);
			currentPathRequest.priocounter = prioc;
		} catch (Exception ex) {
			dataCollector.collector.gotException(ex);
			currentPathRequest = null;
		}
	}

	@Override
	public void end_pathrequest() throws SAXException {
		if (currentPathRequest != null) {
			dataCollector.collector.thepainter.addPath2Check(currentPathRequest);
		}
		currentPathRequest = null;
	}

	@Override
	public void handle_requestedpath(final Attributes meta) throws SAXException {
		if (currentPathRequest != null) {
			try {
				int h = Integer.parseInt(meta.getValue("hash"));
				String pname = meta.getValue("name");

				boolean found = false;
				for (trackObject to : trackObject.allto.keySet()) {
					if (to instanceof pathableObject && to.getTemporaryHash() == h) {
						pathableObject po = (pathableObject) to;
						TreeSet<path> apath = po.getAllPaths();
						for (path p : apath) {
							if (p.getName().compareTo(pname) == 0) {
								currentPathRequest.add(p);
								found = true;
								break;
							}
						}
						if (found) {
							break;
						}
					}
				}
			} catch (Exception ex) {
				dataCollector.collector.gotException(ex);
			}
		}
	}

	@Override
	public void start_command(final Attributes meta) throws SAXException {
		lastCommand = null;
		if (currentRoute != null) {
			try {
				String name = meta.getValue("name");
				String type = meta.getValue("type");
				trainCommand tc = trainCommand.load(type);
				if (tc != null) {
					tc.setName(name);
					lastCommand = tc;
					currentRoute.commands.add(tc);
				}
			} catch (Exception ex) {
				dataCollector.collector.gotException(ex);
			}
		}
	}

	@Override
	public void end_command() throws SAXException {
		lastCommand = null;
	}

	@Override
	public void handle_time(final Attributes meta) throws SAXException {
		try {
			dataCollector.collector.setTime(Long.parseLong(meta.getValue("time")));
		} catch (Exception ex) {
			dataCollector.collector.gotException(ex);
		}
		try {
			dataCollector.collector.thepainter.setLastEmitterTime(Long.parseLong(meta.getValue("lastemitter")));
		} catch (Exception ex) {
			dataCollector.collector.gotException(ex);
		}
		try {
			int x = Integer.parseInt(meta.getValue("x"));
			int y = Integer.parseInt(meta.getValue("y"));
			dataCollector.collector.thepainter.setPos(x, y);
		} catch (Exception ex) {
			dataCollector.collector.gotException(ex);
		}
	}

	@Override
	public void start_trainemitter(final Attributes meta) throws SAXException {
		currentStart = null;
		int h = Integer.parseInt(meta.getValue("hash"));
		for (trackObject to : trackObject.allto.keySet()) {
			if (to instanceof trainStartObject && to.getTemporaryHash() == h) {
				currentStart = (trainStartObject) to;
			}
		}
	}

	@Override
	public void end_trainemitter() throws SAXException {
		currentStart = null;
	}

	@Override
	public void start_emit(final Attributes meta) throws SAXException {
		currentStarttrain = null;
		if (currentStart != null) {
			try {
				String n = meta.getValue("name");
				int v = Integer.parseInt(meta.getValue("vmax"));
				route r = route.allroutes.get(meta.getValue("route"));
				boolean l = meta.getValue("loaded").compareToIgnoreCase("y") == 0;
				currentStarttrain = new starttrain(n);
				currentStarttrain.setVMax(v);
				currentStarttrain.setStartroute(r);
				currentStarttrain.setTrainloaded(l);
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
	}

	@Override
	public void handle_frequence(final Attributes meta) throws SAXException {
		if (currentStarttrain != null) {
			try {
				String f = meta.getValue("t");
				currentStarttrain.addFrequence(f);
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
	}

	@Override
	public void end_emit() throws SAXException {
		if (currentStarttrain != null && currentStart != null) {
			if (inStartqueue) {
				currentStart.addQueue(currentStarttrain);
			} else {
				currentStart.addTrain(currentStarttrain);
			}
		}
		currentStarttrain = null;
	}

	@Override
	public void handle_epart(final Attributes meta) throws SAXException {
		if (currentStarttrain != null) {
			try {
				idtype i = new idtype(meta.getValue("id"));
				boolean f = meta.getValue("forward").compareToIgnoreCase("y") == 0;
				rollingstock rs = dataCollector.collector.alltraindata.stocks.get(i);
				if (rs != null) {
					stock s = new stock(rs, f);
					currentStarttrain.addStock(s);
				}
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
	}

	@Override
	public void start_startqueue(final Attributes meta) throws SAXException {
		inStartqueue = true;
	}

	@Override
	public void end_startqueue() throws SAXException {
		inStartqueue = false;
	}

	@Override
	public void start_trains(final Attributes meta) throws SAXException {
	}

	@Override
	public void end_trains() throws SAXException {
	}

	@Override
	public void start_train(final Attributes meta) throws SAXException {
		currentFullTrain = null;
		try {
			currentFullTrain = new fulltrain(meta);
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}

	@Override
	public void end_train() throws SAXException {
		if (currentFullTrain != null) {
			currentFullTrain.handle_end_load();
			dataCollector.collector.thepainter.addTrain4Load(currentFullTrain);
		}
	}

	@Override
	public void start_tpart(final Attributes meta) throws SAXException {
		if (currentFullTrain != null) {
			currentFullTrain.handle_start_tpart(meta);
		}
	}

	@Override
	public void end_tpart() throws SAXException {
		if (currentFullTrain != null) {
			currentFullTrain.handle_end_tpart();
		}
	}

	@Override
	public void handle_point(final Attributes meta) throws SAXException {
		try {
			if (currentFullTrain != null) {
				currentFullTrain.handle_point(meta);
			}
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}

	@Override
	public void handle_tdata(final String data, final Attributes meta) throws SAXException {
		try {
			if (currentFullTrain != null) {
				currentFullTrain.handle_tdata(meta);
			}
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}
}
