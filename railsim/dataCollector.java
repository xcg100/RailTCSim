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
package org.railsim;

import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.io.File;
import java.text.DateFormat;
import java.util.*;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.railsim.editor.*;
import org.railsim.event.*;
import org.railsim.gui.cursorSerivce;
import org.railsim.service.*;
import org.railsim.service.trackObjects.*;
import org.railsim.service.tracks.simpleTrack;
import org.railsim.toolset.editorTransferObject;
import org.railsim.toolset.locationManager;
import org.railsim.toolset.pathInfo;
import org.railsim.toolset.statusEvent;
import org.railsim.train.ManualTrainEvent;
import org.railsim.train.collection;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class dataCollector extends Hashtable<String, Object> {

	public static dataCollector collector = new dataCollector();
	public static final String EXTENSION_SAVEGAME = "strain";
	public static final String EXTENSION_ROLLINGSTOCK = "stroll";
	public static final String EXTENSION_ENGINE = "stengine";
	public gamearea thegame = null;
	public painter thepainter = null;
	public RailGUI themain = null;
	public collection alltraindata = null;
	String currentEditMode = "";
	public toList tol = new toList();
	volatile public long trainThreadDuration = 0;
	volatile public long paintThreadDuration = 0;
	volatile public long prerunnerThreadDuration = 0;
	volatile public long pathQueueSize = 0;
	public static boolean testSaveGame = false;
	public Preferences prefs_totd = Preferences.userRoot().node("/org/js-home/simplytrain/totd");
	public Preferences prefs_main = Preferences.userRoot().node("/org/js-home/simplytrain/main");
	public cursorSerivce cursors = new cursorSerivce();

	private class SAVEGAMEfilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			String extension = getExtension(f);
			if (extension != null) {
				if (extension.equals(EXTENSION_SAVEGAME)) {
					return true;
				} else {
					return false;
				}
			}

			return false;
		}

		@Override
		public String getDescription() {
			return "Simply Train Savegame";
		}
	};
	JFileChooser filefc;
	private DateFormat df;

	/**
	 * Creates a new instance of dataCollector
	 */
	public dataCollector() {
		filefc = new JFileChooser(locationManager.getSavegame()[0]);
		filefc.setFileFilter(new SAVEGAMEfilter());
		df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public GraphicsDevice getDevice() {
		return themain.getDevice();
	}

	public void setGame(gamearea g) {
		thegame = g;
	}

	public gamearea getGame() {
		return thegame;
	}

	public void setPainter(painter p) {
		thepainter = p;
	}

	public painter getPainter() {
		return thepainter;
	}

	public void setAllTrainData(collection _alltraindata) {
		alltraindata = _alltraindata;
	}

	public collection getAllTrainData() {
		return alltraindata;
	}

	public void setMain(RailGUI p) {
		themain = p;
	}

	public RailGUI getMain() {
		return themain;
	}

	public void waitCursor() {
		themain.setCursor(Cursor.WAIT_CURSOR);
	}

	public void defaultCursor() {
		themain.setCursor(Cursor.DEFAULT_CURSOR);
	}
	public ListenerList filenameListeners = new ListenerList();

	public void setFilename(String f) {
		if (f == null) {
			f = "";
		}
		put("filename", f);
		filenameListeners.fireEvent(new AbstractEvent(f));
	}

	public String getFilename() {
		String f = (String) get("filename");
		return f == null ? "" : f;
	}
	public ListenerList sizeOrPosChangedListeners = new ListenerList();

	public void sizeOrPosChanged() {
		sizeOrPosChangedListeners.fireEvent(new AbstractEvent(thepainter));
	}
	public ListenerList quitListeners = new ListenerList();

	public void quitProgram() {
		stopAll();
		gamesetEvent();
		int returnVal = JOptionPane.showConfirmDialog(thegame, "Programm beenden?", "Ende?", JOptionPane.YES_NO_OPTION);
		if (returnVal == JOptionPane.YES_OPTION) {
			quitListeners.fireEvent(new AbstractEvent(this));
			System.exit(0);
		} else {
			startSim();
		}
	}
	public ListenerList editModeListeners = new ListenerList();

	public void setEditMode(String m) {
		currentEditMode = m;
		editModeListeners.fireEvent(new AbstractEvent(m));
	}

	public String getEditMode() {
		return currentEditMode;
	}
	public ListenerList statusListeners = new ListenerList();

	public void setStatus(String m) {
		statusListeners.fireEvent(new statusEvent(m));
	}

	public void unsetStatus(String m) {
		statusListeners.fireEvent(new statusEvent(false, m));
	}
	public ListenerList pathInfoListeners = new ListenerList();

	public void setPathInfo(pathInfo m) {
		pathInfoListeners.fireEvent(new AbstractEvent(m));
	}
	public ListenerList exceptionListeners = new ListenerList();

	public void gotException(Exception e) {
		exceptionListeners.fireEvent(new AbstractEvent(e));
		e.printStackTrace();
	}
	public ListenerList simRunningChangeListeners = new ListenerList();

	public void startSim() {
		thepainter.startPainter();
		thepainter.startSim();
		simRunningChangeListeners.fireEvent(new AbstractEvent(new Boolean(true)));
	}

	public void stopSim() {
		thepainter.stopSim();
		simRunningChangeListeners.fireEvent(new AbstractEvent(new Boolean(false)));
	}

	public void stopAll() {
		thepainter.stopSim();
		thepainter.stopPainter();
		simRunningChangeListeners.fireEvent(new AbstractEvent(new Boolean(false)));
	}

	public boolean save() {
		stopAll();
		waitCursor();
		boolean ret = thepainter.save(getFilename());
		if (!ret) {
			JOptionPane.showMessageDialog(thegame, "Fehler beim Speichern der Datei!", "Dateifehler", JOptionPane.ERROR_MESSAGE);
		}
		defaultCursor();
		startSim();
		return ret;
	}

	public boolean saveas() {
		stopAll();
		int returnVal = filefc.showSaveDialog(thegame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			setFilename(addExtension(filefc.getSelectedFile().getAbsolutePath(), EXTENSION_SAVEGAME));
			waitCursor();
			boolean ret = save();
			defaultCursor();
			return ret;
		}
		startSim();
		return false;
	}

	public boolean load() {
		stopAll();
		int returnVal = filefc.showOpenDialog(thegame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			gamesetEvent();
			setFilename(filefc.getSelectedFile().getAbsolutePath());
			waitCursor();
			String ret = thepainter.load(getFilename());
			if (ret != null) {
				setFilename(null);
				JOptionPane.showMessageDialog(thegame, "Fehler beim Laden der Datei!\n" + ret, "Ladefehler", JOptionPane.ERROR_MESSAGE);
			}
			defaultCursor();
			return ret == null;
		}
		startSim();
		return false;
	}

	public boolean createnew() {
		stopAll();
		gamesetEvent();
		int returnVal = JOptionPane.showConfirmDialog(thegame, "Wirklich alles löschen?", "Neu?", JOptionPane.YES_NO_OPTION);
		if (returnVal == JOptionPane.YES_OPTION) {
			setFilename(null);
			boolean b = thepainter.createnew();
			return b;
		}
		startSim();
		return false;
	}

	public static String getExtension(File f) {
		return getExtension(f.getName());
	}

	public static String getExtension(String f) {
		String ext = null;
		String s = f;
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static String addExtension(String f, String ext) {
		String e = getExtension(f);
		if (!ext.equals(e)) {
			f += "." + ext;
		}
		return f;
	}
	public ListenerList gamesetEventListeners = new ListenerList();

	public void gamesetEvent() {
		gamesetEventListeners.fireEvent(new AbstractEvent(this));
	}
	public ListenerList editorEventListeners = new ListenerList();

	public void editorEvent(int type) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, null));
	}

	public void editorEvent(int type, track t) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, t));
	}

	public void editorEvent(int type, trackObject t) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, t));
	}

	public void editorEvent(int type, simpleTrack t) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, t));
	}

	public void editorEvent(int type, path t) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, t));
	}

	public void editorEvent(int type, String s) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, s));
	}

	public void editorEvent(int type, editorTransferObject v) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, v));
	}

	public void editorEvent(int type, trackGroup v) {
		editorEventListeners.fireEvent(new EditorEvent<>(type, v));
	}
	public ListenerList routeListeners = new ListenerList();

	public void routesChanged() {
		routeListeners.fireEvent(new AbstractEvent(route.allroutes));
	}
	public ListenerList pathListeners = new ListenerList();

	public void pathsChanged() {
		pathListeners.fireEvent(new AbstractEvent(trackObject.allto));
	}
	public ListenerList trackObjectListeners = new ListenerList();

	public void trackObjectsChanged() {
		trackObjectListeners.fireEvent(new AbstractEvent(trackObject.allto));
	}
	public ListenerList trainsListListeners = new ListenerList();

	public void trainsListChanged(fulltrain ft) {
		trainsListListeners.fireEvent(new AbstractEvent(ft));
	}
	public ListenerList manualTrainListeners = new ListenerList();

	public void manualTrain(fulltrain ft, pathableObject po, trainCommandExecutor_manual tce) {
		manualTrainListeners.fireEvent(new ManualTrainEvent(ft, po, tce));
	}

	public void manualTrain(fulltrain ft, route r) {
		manualTrainListeners.fireEvent(new ManualTrainEvent(ft, r));
	}
	private long time = 0;
	private long reftime = 0;

	public long getTime() {
		long t2 = System.currentTimeMillis() - reftime + time;
		// we run infinite! t2=t2%(60*60*24*1000);
		return t2;
	}

	static public long getTimeOfDay(long t) {
		long t2 = t % (60 * 60 * 24 * 1000);
		return t2;
	}

	static public int getMinuteOfHour(long t) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(t);
		return c.get(Calendar.MINUTE);
	}

	static public int getHourOfDay(long t) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(t);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public Date getDate() {
		return new Date(getTime());
	}

	public void setTime(long t) {
		time = t;
		reftime = System.currentTimeMillis();
	}

	public String formatDate(Date d) {
		return df.format(d);
	}

	public String formatDate() {
		return df.format(getDate());
	}
	private volatile boolean trackChanged = false;

	public void trackMoved(track t) {
		trackChanged = true;
	}

	public void trackModified(track t) {
		trackChanged = true;
	}

	public void trackAdded(track t) {
		trackChanged = true;
	}

	public void trackRemoved(track t) {
		trackChanged = true;
	}

	public void trackViewChanged() {
		trackChanged = true;
	}

	public boolean isTrackChanged() {
		boolean r = trackChanged;
		trackChanged = false;
		return r;
	}
}
