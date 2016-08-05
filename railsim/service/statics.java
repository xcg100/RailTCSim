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

import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * Class with static functions and values
 *
 * @author js
 */
public class statics {

	/**
	 * Path to trackObjects in packages
	 */
	public static final String TRACKOBJECTSPATH = "org.railsim.service.trackObjects.objects";
	/**
	 * Path to trainOrders in packages
	 */
	public static final String TRAINORDERSPATH = "org.railsim.service.trainorders";
	/**
	 * Pixels per meter
	 */
	public static final int PIXELPERMETER = 2;
	//public static final int PIXELPERMETER=3;
	/**
	 * Delay in milliseconds
	 */
	public static final int TICKDELAY = 30;
	public static final int PAINTERTICKDELAY = 30;
	public static final int TICKSPERSECOND = 1000 / TICKDELAY;
	public static final int TICKSPERMOVE = 1000;
	public static final int TICKSPRERUNNER = 300;
	public static final int TICKSPATHQUEUE = 800;

	/**
	 * Creates a new instance of statics
	 */
	public statics() {
	}

	/**
	 * get plaf class of name
	 *
	 * @param n
	 * @return
	 */
	static public String getLookAndFeelClass(String n) {
		UIManager.LookAndFeelInfo[] plaf = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < plaf.length; ++i) {
			if (plaf[i].getName().compareTo(n) == 0) {
				return plaf[i].getClassName();
			}
		}
		return null;
	}

	/**
	 * getr all available plafs
	 *
	 * @return
	 */
	static public String[] getLookAndFeels() {
		UIManager.LookAndFeelInfo[] plaf = UIManager.getInstalledLookAndFeels();
		String[] ret = new String[plaf.length];
		for (int i = 0; i < plaf.length; ++i) {
			ret[i] = plaf[i].getName();
		}
		return ret;
	}

	/**
	 *
	 * @param cmd
	 * @param c
	 */
	static public void setLookAndFeel(String cmd, Component c) { //System.out.println("L&F: #"+cmd+"#");
		//PLAF-Klasse auswählen
		String plaf = UIManager.getSystemLookAndFeelClassName();
		switch (cmd) {
			case "Metal":
				plaf = "javax.swing.plaf.metal.MetalLookAndFeel";
				break;
			case "Motif":
				plaf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
				break;
			case "Windows":
				plaf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
				break;
			case "Liquid":
				plaf = "com.birosoft.liquid.LiquidLookAndFeel";
				break;
			case "NimROD":
				plaf = "com.nilo.plaf.nimrod.NimRODLookAndFeel";
				break;
			case "System":
				plaf = UIManager.getSystemLookAndFeelClassName();
				break;
			default:
				plaf = getLookAndFeelClass(cmd);
				break;
		}
		//System.out.println("PLAF: "+cmd+" -> "+plaf);
		//LAF umschalten
		try {
			UIManager.setLookAndFeel(plaf);
			Component rc = SwingUtilities.getRoot(c);
			if (rc != null) {
				SwingUtilities.updateComponentTreeUI(rc);
			} else {
				SwingUtilities.updateComponentTreeUI(c);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.err.println(e.toString());
			System.err.println("Style error! Using default.");
			if (!cmd.equals("System")) {
				setLookAndFeel("System", c);
			}
		}
	}

	/**
	 * load class and create instance
	 *
	 * @param base package base path
	 * @param type class name
	 * @return new instance
	 */
	public static Object loadClass(String base, String type) {
		Object t;
		Class c;
		try {
			c = Class.forName(base + "." + type);
			t = c.newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
			ex.printStackTrace();
			return null;
		}
		return t;
	}

	public static Image loadGUIImage(String n) {
		try {
			return ImageIO.read(statics.class.getResource("/org/railsim/gui/resources/" + n));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * km/h to train movement steps (including factor 100)
	 *
	 * @param kmh speed
	 * @return steps
	 */
	public static int speed2Steps(int kmh) {
		return 100 * kmh * TICKSPERMOVE * PIXELPERMETER / (360 * TICKSPERSECOND);
	}

	/**
	 * train movement steps (including factor 100) to km/h
	 *
	 * @param p steps
	 * @return km/h
	 */
	public static int steps2Speed(int p) {
		return p * 360 * TICKSPERSECOND / (100 * TICKSPERMOVE * PIXELPERMETER);
	}

	public static int steps2Speed(double p) {
		return (int) Math.round(p * 360 * TICKSPERSECOND / (100 * TICKSPERMOVE * PIXELPERMETER));
	}

	/**
	 * meters to pixel
	 *
	 * @param m meters
	 * @return pixel
	 */
	public static int meter2pixel(int m) {
		return PIXELPERMETER * m;
	}

	public static int meter2pixel(float m) {
		return Math.round(PIXELPERMETER * m);
	}

	/**
	 * pixel to meters
	 *
	 * @param p pixel
	 * @return meters
	 */
	public static int pixel2meter(int p) {
		return p / PIXELPERMETER;
	}
}
