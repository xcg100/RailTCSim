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
package org.railsim;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.*;

import javax.xml.parsers.ParserConfigurationException;

import org.railsim.event.*;
import org.railsim.gui.miniMap;
import org.railsim.gui.progressDialog;
import org.railsim.gui.progressDialogRunnable;
import org.railsim.service.*;
import org.railsim.service.trackObjects.pathableObject;
import org.railsim.service.trackObjects.toList;
import org.railsim.service.trackObjects.trackObject;
import org.railsim.service.trackObjects.trainStartObject;
import org.railsim.toolset.LockedList;
import org.railsim.toolset.PrograssbarReader;
import org.railsim.toolset.pathInfo;
import org.railsim.train.fulltrain;
import org.railsim.train.idtype;
import org.railsim.train.trainpart;
import org.railsim.xml.GamestoreParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author js
 */
public class painter implements Runnable {

	java.util.Timer thread_paint = null;
	java.util.Timer thread_train = null;
	java.util.Timer thread_prerunner = null;
	/**
	 * all tracks
	 */
	public LockedList<track> tracks = new LockedList<track>();
	/**
	 * all tracks
	 */
	public LockedList<trackGroup> trackgroups = new LockedList<trackGroup>();
	/**
	 * all trains
	 */
	public LockedList<fulltrain> trains = new LockedList<>();
	/**
	 * tracks visible on screen
	 */
	public LockedList<track> visibleTracks = new LockedList<>();
	ReentrantReadWriteLock rwlB = new ReentrantReadWriteLock();	// tracks image semaphore
	ReentrantReadWriteLock rwlD = new ReentrantReadWriteLock();	// trains image semaphore
	ReentrantReadWriteLock rwlE = new ReentrantReadWriteLock();	// editor image semaphore
	BufferedImage[][] imgTracks;	    // image for tracks
	//VolatileImage [][] imgTracks;	    // image for tracks
	Graphics2D[][] g2Tracks, g2Tracks_trans;    // graphics for tracks
	boolean[][] paintedOnTracks;
	static boolean useVolatile = false;
	Image[] imgDest = null;  // images for trains, double buffered
	BufferedImage[] imgDestBI = null;  // images for trains, double buffered
	VolatileImage[] imgDestVI = null;  // images for trains, double buffered
	Graphics2D[] g2Dest;	    // graphics for trains
	Graphics2D[] g2Dest_trans;	    // graphics for trains
	BufferedImage imgEditor;		    // image for editor
	Graphics2D g2Editor, g2Editor_trans;	    // graphics for editor
	Color transparentColor;
	volatile int paintTrainOnImg = 0;
	volatile int paintTrackOnImg = 0;
	rectangle totalbounds = null;
	int LEVELS = track.MAXLEVEL - track.MINLEVEL + 1;
	gamearea output = null;
	int x = 0;
	int y = 0;
	int width = -1;
	int height = -1;
	int new_width = 0;
	int new_height = 0;
	int new_x = 0;
	int old_x = 0;
	int new_y = 0;
	int minx = 0;
	int miny = 0;
	int maxx = 0;
	int maxy = 0;
	private double scalex = 1.0;
	private double scaley = 1.0;
	int old_y = 0;
	volatile int cycle = 0;
	long lastemitter = 0;
	int processorsNum = 1;
	boolean multicpu = false;
	boolean run_train = false;
	boolean run_paint = false;

	/**
	 *
	 * @param thegame
	 */
	public painter(gamearea thegame) {
		this(thegame, thegame.getWidth(), thegame.getHeight());
	}

	/**
	 *
	 * @param thegame
	 * @param w
	 * @param h
	 */
	public painter(gamearea thegame, int w, int h) {
		output = thegame;
		processorsNum = Runtime.getRuntime().availableProcessors();
		multicpu = processorsNum > 1;

		useVolatile = !RailGUI.isFullscreen();

		transparentColor = new Color(0x00, 0x00, 0x00, 0x00);
		new_width = w;
		new_height = h;
		setSizeImpl();

		dataCollector.collector.quitListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				stopPainter();
				stopSim();
			}
		});
	}
	private Timer size_timer = null;

	/**
	 * change visible area
	 *
	 * @param w width
	 * @param h height
	 */
	public void setSize(int w, int h) {
		new_width = w;
		new_height = h;
		sizetimer();
	}

	private void sizetimer() {
		if (size_timer != null) {
			size_timer.cancel();
		}
		size_timer = new Timer();
		size_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				setSizeImpl();
			}
		}, 800);
	}

	/**
	 * set position of visible area
	 *
	 * @param _x X
	 * @param _y Y
	 */
	public void setPos(int _x, int _y) {
		x = _x;
		y = _y;
		setPosImpl();
	}

	/**
	 * set X of visiable area
	 *
	 * @param _x X
	 */
	public void setX(int _x) {
		setPos(_x, y);
	}

	/**
	 * set Y of visiable area
	 *
	 * @param _y Y
	 */
	public void setY(int _y) {
		setPos(x, _y);
	}

	/**
	 * get X of visible area
	 *
	 * @return X
	 */
	public int getX() {
		return x;
	}

	/**
	 * get Y of visible area
	 *
	 * @return Y
	 */
	public int getY() {
		return y;
	}

	/**
	 * get width of visible area
	 *
	 * @return width
	 */
	public int getImgWidth() {
		return width;
	}

	/**
	 * get height of visible area
	 *
	 * @return height
	 */
	public int getImgHeight() {
		return height;
	}

	public int getMinX() {
		return minx;
	}

	public int getMinY() {
		return miny;
	}

	public int getMaxX() {
		return maxx;
	}

	public int getMaxY() {
		return maxy;
	}

	/**
	 * get total width of tracks
	 *
	 * @return width
	 */
	public int getTrackWidth() {
		return (int) totalbounds.getWidth();
	}

	/**
	 * get total height of tracks
	 *
	 * @return height
	 */
	public int getTrackHeight() {
		return (int) totalbounds.getHeight();
	}

	/**
	 * get horiz slider max value
	 *
	 * @return max value for horiz slider
	 */
	public int getHSliderMax() {
		return 3 * getTrackWidth() + (int) totalbounds.getX();
	}

	/**
	 * get vert slider max value
	 *
	 * @return max value for vert slider
	 */
	public int getVSliderMax() {
		return 3 * getTrackHeight() + (int) totalbounds.getY();
	}

	/**
	 * get horiz slider min value
	 *
	 * @return min value for horiz slider
	 */
	public int getHSliderMin() {
		return (int) totalbounds.getX() - getTrackWidth();
	}

	/**
	 * get vert slider min value
	 *
	 * @return min value for vert slider
	 */
	public int getVSliderMin() {
		return (int) totalbounds.getY() - getTrackHeight();
	}

	/**
	 * get horiz slider position
	 *
	 * @return position for horiz slider
	 */
	public int getHSliderPos() {
		return x;
	}

	/**
	 * get vert slider position
	 *
	 * @return position for vert slider
	 */
	public int getVSliderPos() {
		return y;
	}

	public double getScale() {
		return scalex;
	}

	public void setScale(double s) {
		int nx = x, ny = y;
		//nx=(int)Math.round(nx+width/2 - (width/2/(s-scalex+1)));
		//ny=(int)Math.round(ny+height/2 - (height/2/(s-scalex+1)));
		scalex = s;
		scaley = s;
		setPos(nx, ny);
	}

	public void scalePreview(double s) {
		Graphics2D g = clearDrawArea();
		g.setColor(Color.RED);
		if (s > scalex) {
			g.drawRect(x, y, (int) Math.round(width / s), (int) Math.round(height / s));
		} else {
			g.drawRect(x, y, (int) Math.round(width / scalex * s), (int) Math.round(height / scaley * s));
		}
	}

	private void setPosImpl() {
		rwlB.writeLock().lock();
		g2Tracks_trans = new Graphics2D[2][LEVELS];
		for (int l = 0; l < LEVELS; ++l) {
			for (int i = 0; i < 2; ++i) {
				g2Tracks_trans[i][l] = (Graphics2D) g2Tracks[i][l].create();
				g2Tracks_trans[i][l].scale(scalex, scaley);
				g2Tracks_trans[i][l].translate(-x, -y);
			}
		}
		new_x = (int) Math.round((old_x - x) * scalex);
		new_y = (int) Math.round((old_y - y) * scaley);
		paintOffTrackDelayed(true);
		rwlB.writeLock().unlock();

		rwlD.writeLock().lock();
		g2Dest_trans = new Graphics2D[2];
		for (int i = 0; i < 2; ++i) {
			g2Dest_trans[i] = (Graphics2D) g2Dest[i].create();
			g2Dest_trans[i].scale(scalex, scaley);
			g2Dest_trans[i].translate(-x, -y);
		}
		rwlD.writeLock().unlock();

		rwlE.writeLock().lock();
		g2Editor.clearRect(0, 0, width, height);
		g2Editor_trans = (Graphics2D) g2Editor.create();
		g2Editor_trans.scale(scalex, scaley);
		g2Editor_trans.translate(-x, -y);
		rwlE.writeLock().unlock();
		if (output != null) {
			output.paintEditor();
		}
		dataCollector.collector.trackViewChanged();
	}

	private void setSizeImpl() {
		if (imgEditor == null || new_width > imgEditor.getWidth() || new_height > imgEditor.getHeight()) {
			int w = new_width;
			int h = new_height;
			if (w < 5) {
				w = 5;
			}
			if (h < 5) {
				h = 5;
			}
			width = w;
			height = h;
			GraphicsConfiguration gfxConf = dataCollector.collector.getDevice().getDefaultConfiguration();

			rwlB.writeLock().lock();
			if (imgTracks != null) {
				for (int l = 0; l < LEVELS; ++l) {
					for (int i = 0; i < 2; ++i) {
						imgTracks[i][l].flush();
					}
				}
			}

			imgTracks = new BufferedImage[2][LEVELS];
			g2Tracks = new Graphics2D[2][LEVELS];
			g2Tracks_trans = new Graphics2D[2][LEVELS];
			paintedOnTracks = new boolean[2][LEVELS];
			try {
				for (int l = 0; l < LEVELS; ++l) {
					for (int i = 0; i < 2; ++i) {
						imgTracks[i][l] = gfxConf.createCompatibleImage(w, h, Transparency.BITMASK);
						g2Tracks[i][l] = imgTracks[i][l].createGraphics();
						g2Tracks[i][l].setBackground(transparentColor);
						g2Tracks_trans[i][l] = (Graphics2D) g2Tracks[i][l].create();
						g2Tracks_trans[i][l].scale(scalex, scaley);
						g2Tracks_trans[i][l].translate(-x, -y);
					}
				}
			} catch (java.lang.OutOfMemoryError e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			paintTrackOnImg = 0;
			paintOffTrack();
			rwlB.writeLock().unlock();

			rwlE.writeLock().lock();
			imgEditor = gfxConf.createCompatibleImage(w, h, Transparency.BITMASK);
			g2Editor = imgEditor.createGraphics();
			g2Editor.setBackground(transparentColor);
			g2Editor_trans = (Graphics2D) g2Editor.create();
			g2Editor_trans.scale(scalex, scaley);
			g2Editor_trans.translate(-x, -y);
			rwlE.writeLock().unlock();

			rwlD.writeLock().lock();
			if (useVolatile) {
				imgDestVI = new VolatileImage[2];
				g2Dest = new Graphics2D[2];
				g2Dest_trans = new Graphics2D[2];
				for (int i = 0; i < 2; ++i) {
					imgDestVI[i] = gfxConf.createCompatibleVolatileImage(w, h);
					g2Dest[i] = imgDestVI[i].createGraphics();
					g2Dest[i].setBackground(new Color(0, 0xaa, 0));
					g2Dest_trans[i] = (Graphics2D) g2Dest[i].create();
					g2Dest_trans[i].scale(scalex, scaley);
					g2Dest_trans[i].translate(-x, -y);
				}
				imgDest = imgDestVI;
			} else {
				imgDestBI = new BufferedImage[2];
				g2Dest = new Graphics2D[2];
				g2Dest_trans = new Graphics2D[2];
				for (int i = 0; i < 2; ++i) {
					imgDestBI[i] = gfxConf.createCompatibleImage(w, h);
					g2Dest[i] = imgDestBI[i].createGraphics();
					g2Dest[i].setBackground(new Color(0, 0xaa, 0));
					g2Dest_trans[i] = (Graphics2D) g2Dest[i].create();
					g2Dest_trans[i].scale(scalex, scaley);
					g2Dest_trans[i].translate(-x, -y);
				}
				imgDest = imgDestBI;
			}
			rwlD.writeLock().unlock();

			if (output != null) {
				output.paintEditor();
			}
		} else {
			int w = new_width;
			int h = new_height;
			if (w < 5) {
				w = 5;
			}
			if (h < 5) {
				h = 5;
			}
			width = w;
			height = h;
		}
		dataCollector.collector.trackViewChanged();
	}
	Timer delay_timer = null;

	/**
	 * spool paint of tracks on offscreen buffer
	 */
	private void paintOffTrackDelayed(boolean quick) {
		paintOffTrack(quick);
		if (quick) {
			paintOffTrackDelayed();
		}
	}

	public void paintOffTrackDelayed() {
		if (delay_timer != null) {
			delay_timer.cancel();
		}
		delay_timer = new Timer();
		delay_timer.schedule(new TimerTask() {
			@Override
			public void run() {
				delay_timer = null;
				paintOffTrack(false);
			}
		}, 400);
	}

	/**
	 * paint tracks on offscreen buffer
	 */
	public void paintOffTrack() {
		paintOffTrack(false);
	}

	private void paintOffTrack(boolean quick) {
		int i;
		rectangle bounds = new rectangle(x, y, x + (int) Math.round(width / scalex), y + (int) Math.round(height / scaley));
		rectangle _totalbounds = new rectangle(0, 0, width, height);
		rwlB.readLock().lock();
		LockedList<track>[] leveltrack = new LockedList[LEVELS];
		for (i = 0; i < LEVELS; ++i) {
			leveltrack[i] = new LockedList<>();
			g2Tracks[paintTrackOnImg][i].clearRect(0, 0, imgTracks[paintTrackOnImg][i].getWidth(), imgTracks[paintTrackOnImg][i].getHeight());
			paintedOnTracks[paintTrackOnImg][i] = false;
		}
		visibleTracks.writeLock();
		visibleTracks.clear();
		int nminx = Integer.MAX_VALUE, nminy = Integer.MAX_VALUE;
		int nmaxx = Integer.MIN_VALUE, nmaxy = Integer.MIN_VALUE;
		tracks.readLock();
		for (track t : tracks) {// System.out.println(bounds.intersects(t.getBounds())+"::"+bounds.toString()+"//"+t.getBounds().toString());
			if (bounds.intersects(t.getBounds())) {
				leveltrack[t.getLevel() - track.MINLEVEL].add(t);
				visibleTracks.add(t);
				paintedOnTracks[paintTrackOnImg][t.getLevel() - track.MINLEVEL] = true;
				if (t.getLevel() - track.MINLEVEL + 1 < LEVELS) {
					paintedOnTracks[paintTrackOnImg][t.getLevel() - track.MINLEVEL + 1] = true;
				}
			}
			nminx = Math.min(nminx, t.getMinX());
			nminy = Math.min(nminy, t.getMinY());
			nmaxx = Math.max(nmaxx, t.getMaxX());
			nmaxy = Math.max(nmaxy, t.getMaxY());
			_totalbounds = _totalbounds.union(t.getBounds());
		}
		tracks.readUnlock();
		minx = nminx;
		miny = nminy;
		maxx = nmaxx;
		maxy = nmaxy;
		visibleTracks.writeUnlock();
		++cycle;
		if (quick) {
			for (i = 0; i < LEVELS; ++i) {
				g2Tracks_trans[paintTrackOnImg][i].setColor(Color.BLACK);
				for (track t : leveltrack[i]) {
					t.paintMini(g2Tracks_trans[paintTrackOnImg][i]);
				}
			}
		} else {
			for (i = 0; i < LEVELS; ++i) {
				if (i > 0) {
					for (track t : leveltrack[i - 1]) {
						t.paintTrack(g2Tracks_trans[paintTrackOnImg][i], track.PART_ABOVE);
					}
				}
				for (track t : leveltrack[i]) {
					t.paintTrack(g2Tracks_trans[paintTrackOnImg][i], track.PART_ADDITIONAL);
				}
				for (track t : leveltrack[i]) {
					t.paintTrack(g2Tracks_trans[paintTrackOnImg][i], track.PART_SLEEPER);
				}
				for (track t : leveltrack[i]) {
					t.paintTrack(g2Tracks_trans[paintTrackOnImg][i], track.PART_RAIL);
				}
			}
		}

		paintTrackOnImg = 1 - paintTrackOnImg;
		new_x = 0;
		new_y = 0;
		old_x = x;
		old_y = y;
		rwlB.readLock().unlock();
		if (totalbounds == null || !totalbounds.equals(_totalbounds)) {
			totalbounds = _totalbounds;
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				dataCollector.collector.sizeOrPosChanged();
			}
		});
	}

	private void paintOffTrain() {
		rectangle bounds = new rectangle(x, y, x + (int) Math.round(width / scalex), y + (int) Math.round(height / scaley));

		rwlD.readLock().lock();
		g2Dest[paintTrainOnImg].clearRect(0, 0, imgDest[paintTrainOnImg].getWidth(null), imgDest[paintTrainOnImg].getHeight(null));
		trains.readLock();
		for (int l = 0; l < LEVELS; ++l) {
			if (paintedOnTracks[1 - paintTrackOnImg][l]) {
				rwlB.readLock().lock();
				g2Dest[paintTrainOnImg].drawImage(imgTracks[1 - paintTrackOnImg][l], new_x, new_y, null);
				rwlB.readLock().unlock();
				try {
					if (currentlyBuilding != null) {
						currentlyBuilding.paintWorker(g2Dest_trans[paintTrainOnImg]);
					}
				} catch (Exception e) {
				}
			}
			for (fulltrain t : trains) {
				if (t.getBounds() != null && bounds.intersects(t.getBounds())) {
					t.paint(g2Dest_trans[paintTrainOnImg], l + track.MINLEVEL);
				}
			}
		}
		trains.readUnlock();
		rwlE.readLock().lock();
		g2Dest[paintTrainOnImg].drawImage(imgEditor, 0, 0, null);
		rwlE.readLock().unlock();
		rwlD.readLock().unlock();
		paintTrainOnImg = 1 - paintTrainOnImg;
	}
	private boolean inPaint = false;

	/**
	 * paint ready complete off screen buffer to screen
	 *
	 * @param g graphics
	 */
	public void paint(Graphics g) {
		if (inPaint) {
			System.out.println("2x paint");
			return;
		}
		inPaint = true;
		rwlD.readLock().lock();
		g.drawImage(imgDest[1 - paintTrainOnImg], 0, 0, null);
		rwlD.readLock().unlock();
		inPaint = false;
	}

	/**
	 * get Graphics for editor layer, clear this before return
	 *
	 * @return Graphcis2D
	 */
	public Graphics2D clearDrawArea() {
		g2Editor.clearRect(0, 0, width, height);
		return g2Editor_trans;
	}

	/**
	 * get Graphics for editor layer, not cleared
	 *
	 * @return Graphics2D
	 */
	public Graphics2D getDrawArea() {
		return g2Editor_trans;
	}

	/**
	 * translate screen X (mouse) to track area X
	 *
	 * @param _x X on screen
	 * @return X on tracks
	 */
	public int translateS2T_X(int _x) {
		return (int) Math.round(_x / scalex) + x;
	}

	/**
	 * translate screen Y (mouse) to track area Y
	 *
	 * @param _y Y on screen
	 * @return Y on tracks
	 */
	public int translateS2T_Y(int _y) {
		return (int) Math.round(_y / scaley) + y;
	}

	/**
	 * translate track area X to screen X (mouse)
	 *
	 * @param _x X on track area
	 * @return X on screen
	 */
	public int translateT2S_X(int _x) {
		return (int) Math.round(_x * scalex) - x;
	}

	/**
	 * translate track area Y to screen Y (mouse)
	 *
	 * @param _y Y on track area
	 * @return Y on screen
	 */
	public int translateT2S_Y(int _y) {
		return (int) Math.round(_y * scaley) - y;
	}

	/**
	 * not used
	 *
	 * @deprecated not used
	 */
	@Override
	public void run() {
	}

	/**
	 * paint tracks on offscreen buffer
	 */
	public void run_paint() {
		if (run_paint) {
			long time1 = System.nanoTime();
			if (output != null) {
				output.paintScreen();
			}
			paintOffTrain();
			dataCollector.collector.paintThreadDuration = System.nanoTime() - time1;
		}
		if (!multicpu && run_train) {
			run_train();
		}
	}

	/**
	 * paint trains on offscreen buffer
	 */
	public void run_train() {
		long time1 = System.nanoTime();
		trains.readLock();
		for (fulltrain t : trains) {
			try {
				t.run();
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
		trains.readUnlock();
		trackObject.runLater();
		dataCollector.collector.trainThreadDuration = System.nanoTime() - time1;
		/*time1 = System.nanoTime();
		 paintOffTrain();
		 dataCollector.collector.paintThreadDuration=System.nanoTime()-time1;*/
	}

	/**
	 * update prerunners
	 */
	public void run_prerunner() {
		long time1 = System.nanoTime();
		trains.readLock();
		for (fulltrain t : trains) {
			try {
				t.prerunner();
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
		trains.readUnlock();
		dataCollector.collector.prerunnerThreadDuration = System.nanoTime() - time1;
	}
	ConcurrentLinkedQueue<track> crossingchecklist = new ConcurrentLinkedQueue<>();

	/**
	 * check queued tracks if they cross another track
	 *
	 * @see addTrack2Check()
	 */
	public void run_crossingcalculator() {
		if (!crossingchecklist.isEmpty()) {
			track ctrack = crossingchecklist.poll();
			if (ctrack == null) {
				return;
			}
			tracks.readLock();
			for (track t : tracks) {
				if (t != ctrack) {
					try {
						ctrack.calcCrossing(t);
						//tracks.readLock();
					} catch (Exception e) {
						dataCollector.collector.gotException(e);
					}
				}
			}
			tracks.readUnlock();
		}
	}

	public void run_trainstarter() {
		long t = gametime() / 1000 / 60;
		long te = lastemitter + 1;
		if (te <= t) {
			lastemitter = te;
			t = te * 1000 * 60;
			for (trackObject to : trackObject.allto.keySet()) {
				if (to instanceof trainStartObject) {
					try {
						((trainStartObject) to).newTrain(t);
					} catch (Exception e) {
						dataCollector.collector.gotException(e);
					}
				}
			}
		}
	}
	private track currentlyBuilding = null;

	public void run_buildtrack() {
		if (currentlyBuilding != null) {
			if (currentlyBuilding.build()) {
				currentlyBuilding = null;
				try {
					paintOffTrackDelayed();
				} catch (Exception e) {
					dataCollector.collector.gotException(e);
				}
			}
		} else {
			for (track t : tracks) {
				if (t.getProgress() > 0) {
					currentlyBuilding = t;
					break;
				}
			}
		}
	}

	/**
	 * add track to crossing check queue
	 *
	 * @param t track
	 * @see run_crossingcalculator()
	 */
	public void addTrack2Check(track t) {
		crossingchecklist.add(t);
	}
	ConcurrentLinkedQueue<pathRequest> pathqueue = new ConcurrentLinkedQueue<>();

	public ConcurrentLinkedQueue<pathRequest> getPathqueue() {
		return pathqueue;
	}

	public void run_miniMap() {
		miniMap.update();
	}

	/**
	 * check and handle queued pathrequests
	 *
	 * @see addPath2Check()
	 */
	public void run_pathqueue() {
		dataCollector.collector.pathQueueSize = pathqueue.size();
		if (!pathqueue.isEmpty()) {
			pathRequest pr = pathqueue.poll();
			pathableObject po = null;

			// Zug-Ziel auslesen (Route) und passenden path(s) suchen

			final int FACTOR = 5; // etwa Sekunden (800ms), wie lange jede Prio durchsucht wird
			boolean setPath = false;
			for (int i = route.PRIOMIN; i <= Math.min(pr.priocounter / FACTOR + 1, route.PRIOMAX) && !setPath; ++i) {
				for (path p : pr) {
					po = p.getSignal();
					if (p.isEnabled() && i == p.getPriorityOfRoute(pr.r) && p.isFree(pr.r)) {
						setPath = p.set();
						dataCollector.collector.setPathInfo(new pathInfo(p.getSignal(), pathInfo.CODE_SET, "setze Fahrstraße " + p.getName() + " wegen Prio " + i + (pr.r != null ? " Route " + pr.r.getName() : "") + " für " + pr.ft.getName()));
						break;
					}
				}
			}
			if (!setPath) {
				pr.priocounter++;
				pathqueue.add(pr);
				if (pr.priocounter % 10 == 0) {
					dataCollector.collector.setPathInfo(new pathInfo(po, pathInfo.CODE_LONGNOTSET, "bisher kein Fahrweg für " + pr.ft.getName()));
				} else {
					dataCollector.collector.setPathInfo(new pathInfo(po, pathInfo.CODE_NOTSET, "kein Fahrweg für " + pr.ft.getName()));
				}
			}
		}
	}

	/**
	 * add pathrequest to check queue
	 *
	 * @param t pathrequest
	 * @see run_pathqueue()
	 */
	public void addPath2Check(pathRequest t) {
		pathqueue.add(t);
	}

	/**
	 * update pathrequest in check queue
	 *
	 * @param po
	 * @return
	 * @see run_pathqueue()
	 */
	public pathRequest pollPath2Check(pathableObject po) {
		for (pathRequest pr : pathqueue) {
			if (pr.getSignal() == po) { // Gefehl Race Condition! runner könnte pathRequest gerade ausgenommen haben
				pathqueue.remove(pr);
				return pr;
			}
		}
		return null;
	}

	/**
	 * start simulator thread - train movement, paths
	 *
	 * @see run_train()
	 */
	public void startSim() {
		if (thread_train == null) {
			run_train = true;
			if (multicpu) {
				thread_train = new java.util.Timer();
				thread_train.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						run_train();
					}
				}, 0, statics.TICKDELAY);
			}
		}
		if (thread_prerunner == null) {
			thread_prerunner = new java.util.Timer();
			thread_prerunner.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					run_prerunner();
				}
			}, 0, statics.TICKSPRERUNNER);
			thread_prerunner.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					run_pathqueue();
				}
			}, 0, statics.TICKSPATHQUEUE);
			thread_prerunner.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					run_crossingcalculator();
				}
			}, 0, statics.TICKSPRERUNNER * 2);
			thread_prerunner.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					run_trainstarter();
				}
			}, 0, 1000 * 10);
			thread_prerunner.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					run_buildtrack();
				}
			}, 0, 1000);
			thread_prerunner.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					run_miniMap();
				}
			}, 0, 5000);
		}
	}

	/**
	 * start painter thread - paintOffXXXX() functions
	 *
	 * @see run_paint()
	 */
	public void startPainter() {
		paintOffTrack();
		if (thread_paint == null) {
			run_paint = true;
			thread_paint = new java.util.Timer();
			thread_paint.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					run_paint();
				}
			}, 0, statics.PAINTERTICKDELAY);
		}
	}

	/**
	 * stop simulator thread
	 *
	 * @see startSim()
	 */
	public void stopSim() {
		if (thread_train != null) {
			thread_train.cancel();
		}
		run_train = false;
		thread_train = null;

		if (thread_prerunner != null) {
			thread_prerunner.cancel();
		}
		thread_prerunner = null;
	}

	/**
	 * stop painter thread
	 *
	 * @see startPainter()
	 */
	public void stopPainter() {
		if (thread_paint != null) {
			thread_paint.cancel();
		}
		run_paint = false;
		thread_paint = null;
	}

	public void setShowHiddenTrackObjects(boolean b) {
		if (b != track.PAINTALLTRACKOBJECTS) {
			track.PAINTALLTRACKOBJECTS = b;
			paintOffTrackDelayed();
		}
	}

	// service functions
	private void clearAll() {
		pathqueue.clear();
		trains = new LockedList<>();
		for (track t : tracks) {
			t.remove();
		}
		synchronized (route.allroutes) {
			while (!route.allroutes.isEmpty()) {
				route r = route.allroutes.firstEntry().getValue();
				r.remove();
			}
		}
		crossingchecklist.clear();
		trackObject.allregions.clear();
		tracks = new LockedList<>();
		currentlyBuilding = null;
		dataCollector.collector.setTime(0);
		lastemitter = gametime() / 1000 / 60;
		dataCollector.testSaveGame = false;
	}

	/**
	 * clears all data, start a new build
	 *
	 * @return true: success
	 */
	public boolean createnew() {
		dataCollector.collector.stopSim();
		clearAll();
		paintOffTrack();
		setPos(0, 0);
		dataCollector.collector.startSim();
		return true;
	}

	private String load(Reader fr, long length) {
		dataCollector.collector.stopAll();
		clearAll();
		paintOffTrack();
		setPos(0, 0);
		final InputSource input;
		PrograssbarReader f = null;
		progressDialog pd = new progressDialog(dataCollector.collector.themain, "Laden...", length);
		f = new PrograssbarReader(fr, pd);
		input = new InputSource(f);

		final GamestoreParser saxParser = new GamestoreParser(new GamestoreHandlerImpl(this), null);
		try {
			progressDialogRunnable r = new progressDialogRunnable() {
				@Override
				public void run(progressDialog p) {
					try {
						saxParser.parse(input);
					} catch (			IOException | ParserConfigurationException | SAXException ex) {
						ex.printStackTrace();
					}
				}
			};
			pd.open(r);
			f.close();
			pd.close();
		} catch (Exception e) {
			pd.close();
			e.printStackTrace();
			try {
				f.close();
			} catch (IOException ex) {
			}
			dataCollector.collector.startSim();
			return e.getMessage();
			//throw new IOException(e.getMessage());
		}
		paintOffTrack();
		setPos(0, 0);
		pd = new progressDialog(dataCollector.collector.themain, "Berechnen...", crossingchecklist.size());
		progressDialogRunnable r = new progressDialogRunnable() {
			@Override
			public void run(progressDialog pd) {
				int v = 0;
				while (crossingchecklist.size() > 0) {
					pd.setValue(v++);
					run_crossingcalculator();
				}
			}
		};
		pd.open(r);
		pd.close();

		dataCollector.collector.startSim();
		return null;
	}

	public String load(String filename) {
		File fl = new File(filename);
		FileReader fr = null;
		long length = fl.length();
		try {
			fr = new FileReader(fl);
		} catch (FileNotFoundException ex) {
			dataCollector.collector.startSim();
			return "File not found!";
		}
		return load(fr, length);
	}

	public String loadFromJAR(String name) {
		InputStream is = painter.class.getResourceAsStream(name);
		if (is != null) {
			InputStreamReader r = new InputStreamReader(is);
			return load(r, 0);
		}
		return "Resource Stream Error";
	}

	public boolean save(String filename) {
		return GamestoreHandlerImpl.save(filename);
	}

	public void testIt() {
		/*	track t=new track(150,100,0,0,300);
		 track t1=t;
		 addTrack(t);
		 t=new track(t,-45.0,100);
		 addTrack(t);
		 t=new track(t,0.0,50);
		 addTrack(t);
		 t=new track(t,-45.0,100);
		 addTrack(t);
		 t=new track(t,0.0,300);
		 addTrack(t);
		 try
		 {
		 t=new track(t,-90.0,193,t1);
		 addTrack(t);
		 }
		 catch (DestinationNotReachableException e)
		 {
		 System.out.println("Not connected");
		 } */
		dataCollector.collector.setStatus("Testmodus aktiv!");

		if (loadFromJAR("/org/railsim/examples/starttrack1.strain") == null) {
			//dataCollector.collector.testSaveGame=true;

			trackObject t1 = trackObject.findTObyName("Start1");
			fulltrain train1 = new fulltrain(true, 0);
			train1.setName("Zug 1");
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testLok1")), true));
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.onTrack(t1);
			train1.setRoute(route.findOrCreateRoute("Beispiel-Ziel 1"));
			addTrain(train1);

			t1 = trackObject.findTObyName("Start2");
			train1 = new fulltrain(true, 0);
			train1.setName("Zug 2");
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.onTrack(t1);
			train1.setRoute(route.findOrCreateRoute("Beispiel-Ziel 2"));
			addTrain(train1);

			t1 = trackObject.findTObyName("Start3");
			train1 = new fulltrain(true, 0);
			train1.setName("Zug 3");
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testLok1")), true));
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.add(new trainpart(dataCollector.collector.alltraindata.stocks.get(new idtype("testWagen1")), true));
			train1.onTrack(t1);
			train1.setRoute(route.findOrCreateRoute("Beispiel-Ziel 2"));
			addTrain(train1);

			Timer t = new java.util.Timer();
			//t.scheduleAtFixedRate(
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					if (trains.size() > 0) {
						for (fulltrain t : trains) {
							t.setvMax((int) Math.round(40 + Math.random() * 200));
						}
					}
				}
			}, 500);
		}
	}

	public trackGroup findGroup(int tx, int ty, trackGroup prefered) {
		trackGroup ret = null;
		trackgroups.readLock();
		for (trackGroup t : trackgroups) {
			if (t.getClickBounds() != null && t.getClickBounds().contains(x, y)) {
				ret = t;
				if (t.equals(prefered)) {
					break;
				}
			}
		}
		trackgroups.readUnlock();
		return ret;
	}

	public java.util.List<track> findTrackInArea(int x1, int y1, int x2, int y2, boolean includeTouched) {
		LinkedList<track> l = new LinkedList<>();
		rectangle r = new rectangle(x1, y1, x2, y2);
		tracks.readLock();
		for (track t : tracks) {
			if (r.contains(t.getBounds()) || (includeTouched && r.intersects(t.getBounds()))) {
				l.add(t);
			}
		}
		tracks.readUnlock();
		return l;
	}

	public track findTrack(int x, int y) {
		track ret = null;
		tracks.readLock();
		for (track t : tracks) {
			if (t.getClickBounds().contains(x, y)) {
				ret = t;
				break;
			}
		}
		tracks.readUnlock();
		return ret;
	}

	public track findTrack(int x, int y, track prefered) {
		track ret = null;
		tracks.readLock();
		for (track t : tracks) {
			if (t.getClickBounds().contains(x, y)) {
				ret = t;
				if (t.equals(prefered)) {
					break;
				}
			}
		}
		tracks.readUnlock();
		return ret;
	}

	public boolean addTrack(track t, boolean repaint) {
		boolean ret = true;
		tracks.writeLock();
		tracks.add(t);
		tracks.writeUnlock();
		addTrack2Check(t);
		if (repaint) {
			paintOffTrack();
		}
		dataCollector.collector.trackAdded(t);
		return ret;
	}

	public boolean addTrack(track t) {
		return addTrack(t, true);
	}

	public track addTrackXML(final Attributes meta) {
		try {
			int x1 = Integer.parseInt(meta.getValue("x"));
			int y1 = Integer.parseInt(meta.getValue("y"));
			double winkel_s = Double.parseDouble(meta.getValue("rotation"));
			double winkel_a = Double.parseDouble(meta.getValue("angel"));
			double length = Double.parseDouble(meta.getValue("length"));
			int level = Integer.parseInt(meta.getValue("level"));
			track t = new track(x1, y1, winkel_s, winkel_a, length);
			t.setLevel(level);
			if (meta.getValue("progress") != null) {
				int progress = Integer.parseInt(meta.getValue("progress"));
				t.setProgress(progress);
			}
			if (meta.getValue("group") != null) {
				int hash = Integer.parseInt(meta.getValue("group"));
				boolean added = false;
				for (trackGroup tg : trackgroups) {
					if (tg.getTemporaryHash() == hash) {
						tg.add(t);
						added = true;
						break;
					}
				}
				if (!added) {
					trackGroup tg = new trackGroup(hash);
					tg.add(t);
				}
			}
			if (addTrack(t, false)) {
				return t;
			} else {
				return null;
			}
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
			return null;
		}
	}

	public boolean connectXML(final Attributes meta) {
		int id = Integer.parseInt(meta.getValue("id"));
		int destid = Integer.parseInt(meta.getValue("destid"));
		String type = meta.getValue("type");
		String set = meta.getValue("set");
		track idT, destidT;
		try {
			idT = tracks.get(id);
			destidT = tracks.get(destid);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		if (type.compareToIgnoreCase("prev") == 0) {
			idT.setPrev(destidT);
		} else if (type.startsWith("next")) {
			int p = Integer.parseInt(type.substring(4));
			idT.setNext(destidT, p);
			if (set != null && set.compareToIgnoreCase("y") == 0) {
				idT.setJunction(p);
			}
		}
		return true;
	}

	public boolean delTrack(track t) {
		boolean ret = true;
		tracks.writeLock();
		tracks.remove(t);
		t.remove();
		tracks.writeUnlock();
		paintOffTrack();
		dataCollector.collector.trackRemoved(t);
		return ret;
	}

	public trackObject addTrackObjectXML(track lastTrack, Attributes meta) {
		try {
			int index = Integer.parseInt(meta.getValue("index"));
			boolean forward = meta.getValue("forward").compareToIgnoreCase("y") == 0;
			String type = meta.getValue("type");
			String name = meta.getValue("name");
			String region = meta.getValue("region");
			int hash = Integer.parseInt(meta.getValue("hash"));
			trackObject t = toList.load(type);
			if (t != null) {
				t.setName(name);
				t.setRegion(region);
				t.setTemporaryHash(hash);

				if (lastTrack.setTrackObjectAt(t, forward, index)) {
					return t;
				} else {
					return null;
				}
			}
			return null;
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
			return null;
		}
	}

	public LockedList<track> getVisibleTracks() {
		return visibleTracks;
	}

	public boolean addTrain(fulltrain ft) {
		trains.writeLock();
		trains.add(ft);
		trains.writeUnlock();
		ft.setCreationGametime(gametime());
		dataCollector.collector.trainsListChanged(ft);
		return true;
	}

	public boolean addTrain4Load(fulltrain ft) {
		trains.writeLock();
		trains.add(ft);
		trains.writeUnlock();
		dataCollector.collector.trainsListChanged(ft);
		return true;
	}

	public boolean delTrain(fulltrain ft) {
		trains.writeLock();
		trains.remove(ft);
		trains.writeUnlock();
		dataCollector.collector.trainsListChanged(ft);
		return true;
	}

	public int getGraphicsCycle() {
		return cycle;
	}

	private void centerXY(int x, int y) {
		int nx, ny;
		nx = x - width / 2;
		ny = y - height / 2;
		setPos(nx, ny);
	}

	public void showTrack(track t) {
		visibleTracks.readLock();
		boolean c = visibleTracks.contains(t);
		visibleTracks.readUnlock();
		if (c) {
			rectangle bounds = new rectangle(x, y, x + width, y + height);
			if (!bounds.contains(t.getClickBounds())) { // move to center
				centerXY((int) t.getBounds().getCenterX(), (int) t.getBounds().getCenterY());
			}
		} else { // find it, move to center
			tracks.readLock();
			c = tracks.contains(t);
			tracks.readUnlock();
			if (c) {
				centerXY((int) t.getBounds().getCenterX(), (int) t.getBounds().getCenterY());
			}
		}
	}

	public void showTrackObject(trackObject to) {
		showTrack(to.getTrackData().getTrack());
	}

	public void showTrain(fulltrain ft) {
		try {
			showTrack(ft.getFirst().getA1().getTrack());
		} catch (Exception e) {
		}
	}

	private long gametime() {
		return dataCollector.collector.getTime();
	}

	public long getLastEmitterTime() {
		return lastemitter;
	}

	public void setLastEmitterTime(long e) {
		lastemitter = e;
	}
}
