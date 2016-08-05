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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import org.railsim.*;
import org.railsim.service.genericPaintInterface;
import org.railsim.service.track;

/**
 *
 * @author js
 */
public class rollingstock implements genericPaintInterface, Comparable {

	File file = null;
	idtype id = null;
	String name = "";
	TreeSet<String> types = new TreeSet<>();
	int axisdistance = 10;
	int frontoverlap = 3;
	int backoverlap = 3;
	int weight_empty = 1;
	int weight_full = 1;
	boolean coupling_front = true;
	boolean coupling_back = true;
	engine stockengine = null;
	int speed_empty = 1;
	int speed_full = 1;
	String filename = null;
	Image appearance0 = null;
	Image appearance90 = null;
	Image appearance180 = null;
	Image appearance270 = null;
	static GraphicsConfiguration gfxConf = null;

	/**
	 * Creates a new instance of rollingstock
	 */
	public rollingstock(idtype _id, String n, File f) {
		id = _id;
		name = n;
		file = f;
	}

	public void addType(String t) {
		types.add(t);
	}

	public void setAxisdistance(int a) {
		axisdistance = a;
	}

	public void setFrontoverlap(int a) {
		frontoverlap = a;
	}

	public void setBackoverlap(int a) {
		backoverlap = a;
	}

	public int getAxisdistance() {
		return axisdistance;
	}

	public int getBackoverlap() {
		return backoverlap;
	}

	public int getFrontoverlap() {
		return frontoverlap;
	}

	/**
	 * Getter for property weight_empty.
	 *
	 * @return Value of property weight_empty.
	 */
	public int getWeight_empty() {
		return this.weight_empty;
	}

	/**
	 * Setter for property weight_empty.
	 *
	 * @param weight_empty New value of property weight_empty.
	 */
	public void setWeight_empty(int weight_empty) {
		this.weight_empty = weight_empty;
	}

	/**
	 * Getter for property weight_full.
	 *
	 * @return Value of property weight_full.
	 */
	public int getWeight_full() {
		return this.weight_full;
	}

	/**
	 * Setter for property weight_full.
	 *
	 * @param weight_full New value of property weight_full.
	 */
	public void setWeight_full(int weight_full) {
		this.weight_full = weight_full;
	}

	/**
	 * Getter for property coupling_front.
	 *
	 * @return Value of property coupling_front.
	 */
	public boolean isCoupling_front() {
		return this.coupling_front;
	}

	/**
	 * Setter for property coupling_front.
	 *
	 * @param coupling_front New value of property coupling_front.
	 */
	public void setCoupling_front(boolean coupling_front) {
		this.coupling_front = coupling_front;
	}

	/**
	 * Getter for property coupling_back.
	 *
	 * @return Value of property coupling_back.
	 */
	public boolean isCoupling_back() {
		return this.coupling_back;
	}

	/**
	 * Setter for property coupling_back.
	 *
	 * @param coupling_back New value of property coupling_back.
	 */
	public void setCoupling_back(boolean coupling_back) {
		this.coupling_back = coupling_back;
	}

	/**
	 * Setter for property filename.
	 */
	public void drawElement(int arc, String data) {
		Image i = null;
		if (gfxConf == null) {
			gfxConf = dataCollector.collector.getDevice().getDefaultConfiguration();
		}

		BufferedImage bi = gfxConf.createCompatibleImage(axisdistance + backoverlap + frontoverlap, track.RAILRADIUS * 5, Transparency.BITMASK);
		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); // Test
		g.setBackground(new Color(0x00, 0x00, 0x00, 0x00));
		g.clearRect(0, 0, bi.getWidth(), bi.getHeight());

		g.setColor(Color.RED);
		Scanner s = new Scanner(data);
		while (s.hasNext()) {
			try {
				String c = s.next(); //System.out.println("cmd:"+c);
				if (c.compareTo("color") == 0) {
					int cr = s.nextInt();
					int cg = s.nextInt();
					int cb = s.nextInt();
					int ca = s.nextInt();
					//System.out.println("C:"+cr+"/"+cg+"/"+cb+"/"+ca);
					g.setColor(new Color(cr, cg, cb, ca));
				} else if (c.compareTo("line") == 0) {
					int x1 = s.nextInt();
					if (x1 < 0) {
						x1 = bi.getWidth() + x1;
					}
					int y1 = s.nextInt();
					if (y1 < 0) {
						y1 = bi.getHeight() + y1;
					}
					int x2 = s.nextInt();
					if (x2 < 0) {
						x2 = bi.getWidth() + x2;
					}
					int y2 = s.nextInt();
					if (y2 < 0) {
						y2 = bi.getHeight() + y2;
					}
					g.drawLine(x1, y1, x2, y2);
				} else if (c.compareTo("rect") == 0) {
					int x1 = s.nextInt();
					if (x1 < 0) {
						x1 = bi.getWidth() + x1;
					}
					int y1 = s.nextInt();
					if (y1 < 0) {
						y1 = bi.getHeight() + y1;
					}
					int x2 = s.nextInt();
					if (x2 < 0) {
						x2 = bi.getWidth() + x2;
					}
					int y2 = s.nextInt();
					if (y2 < 0) {
						y2 = bi.getHeight() + y2;
					}
					int w = x2 - x1;
					int h = y2 - y1;
					g.drawRect(x1, y1, w, h);
				} else if (c.compareTo("fillrect") == 0) {
					int x1 = s.nextInt();
					if (x1 < 0) {
						x1 = bi.getWidth() + x1;
					}
					int y1 = s.nextInt();
					if (y1 < 0) {
						y1 = bi.getHeight() + y1;
					}
					int x2 = s.nextInt();
					if (x2 < 0) {
						x2 = bi.getWidth() + x2;
					}
					int y2 = s.nextInt();
					if (y2 < 0) {
						y2 = bi.getHeight() + y2;
					}
					int w = x2 - x1;
					int h = y2 - y1;
					g.fillRect(x1, y1, w, h);
				}
			} catch (InputMismatchException ie) {
				dataCollector.collector.setStatus("Rollmaterial Datenfehler 1");
				//dataCollector.collector.gotException(ie);
			} catch (NoSuchElementException ne) {
				dataCollector.collector.setStatus("Rollmaterial Datenfehler 2");
				//dataCollector.collector.gotException(ne);
			} catch (IllegalStateException ise) {
				dataCollector.collector.setStatus("Rollmaterial Datenfehler 3");
				//dataCollector.collector.gotException(ise);
			} catch (Exception e) {
				dataCollector.collector.gotException(e);
			}
		}
		i = bi;

		if (arc < 45 || arc > 305) {
			this.appearance0 = i;
		} else if (arc < 135) {
			this.appearance90 = i;
		} else if (arc < 225) {
			this.appearance180 = i;
		} else {
			this.appearance270 = i;
		}
		if (appearance90 == null) {
			appearance90 = i;
		}
		if (appearance90 == null) {
			appearance90 = i;
		}
		if (appearance180 == null) {
			appearance180 = i;
		}
		if (appearance270 == null) {
			appearance270 = i;
		}
	}

	/**
	 * Setter for property filename.
	 *
	 * @param filename New value of property filename.
	 */
	public void setFilename(String filename, int arc) {
		this.filename = filename;
		try {
			Image i = null;
			if (filename.startsWith("classpath:")) {
				String f = filename.substring(filename.indexOf(":") + 1);
				URL u = getClass().getResource(collection.IMAGESPACK + "/" + f);
				if (u != null) {
					try {
						InputStream s = u.openStream();
						s.close();
						i = ImageIO.read(u);
					} catch (IOException e) {
					}
				}
			} else {
				File iname;
				if (file != null) {
					iname = new File(file.getCanonicalPath(), filename);
					if (iname.exists()) {
						i = ImageIO.read(iname);
					}
				}
				if (i == null) {
					iname = new File(collection.IMAGESDIR, filename);
					if (iname.exists()) {
						i = ImageIO.read(iname);
					}
				}
			}
			if (i == null) {
				if (gfxConf == null) {
					gfxConf = dataCollector.collector.getDevice().getDefaultConfiguration();
				}

				BufferedImage bi = gfxConf.createCompatibleImage(axisdistance + backoverlap + frontoverlap, track.RAILRADIUS * 4, Transparency.BITMASK);
				Graphics2D g = bi.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON); // Test
				g.setBackground(new Color(0x00, 0x00, 0x00, 0x00));
				g.clearRect(0, 0, bi.getWidth(), bi.getHeight());
				g.setColor(Color.GRAY);
				g.fillRect(2, 0, bi.getWidth() - 4, bi.getHeight());
				g.setColor(Color.RED);
				g.fillRect(4, 2, bi.getWidth() - 8, bi.getHeight() - 4);
				i = bi;
			}
			if (arc < 45 || arc > 305) {
				this.appearance0 = i;
			} else if (arc < 135) {
				this.appearance90 = i;
			} else if (arc < 225) {
				this.appearance180 = i;
			} else {
				this.appearance270 = i;
			}
			if (appearance90 == null) {
				appearance90 = i;
			}
			if (appearance90 == null) {
				appearance90 = i;
			}
			if (appearance180 == null) {
				appearance180 = i;
			}
			if (appearance270 == null) {
				appearance270 = i;
			}
		} catch (Exception e) {
			dataCollector.collector.gotException(e);
		}
	}

	/**
	 * Getter for property engine.
	 *
	 * @return Value of property engine.
	 */
	public engine getEngine() {
		return stockengine;
	}

	/**
	 * Setter for property engine.
	 *
	 * @param engine New value of property engine.
	 */
	public void setEngine(engine engine) {
		stockengine = engine;
	}

	/**
	 * Setter for property engine.
	 *
	 * @param engine New value of property engine.
	 */
	public void setEngine(idtype e) {
		stockengine = dataCollector.collector.alltraindata.engines.get(e);
	}

	/**
	 * Getter for property appearance.
	 *
	 * @return Value of property appearance.
	 */
	public Image getAppearance(int arc) {
		if (arc < 45 || arc > 305) {
			return this.appearance0;
		} else if (arc < 135) {
			return this.appearance90;
		} else if (arc < 225) {
			return this.appearance180;
		} else {
			return this.appearance270;
		}
	}

	/**
	 * Getter for property appearance.
	 *
	 * @return Value of property appearance.
	 */
	public Image getAppearance(double arc) {
		if (arc < 45 * Math.PI / 180 || arc > 305 * Math.PI / 180) {
			return this.appearance0;
		} else if (arc < 135 * Math.PI / 180) {
			return this.appearance90;
		} else if (arc < 225 * Math.PI / 180) {
			return this.appearance180;
		} else {
			return this.appearance270;
		}
	}

	/**
	 * Getter for property name.
	 *
	 * @return Value of property name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter for property id.
	 *
	 * @return Value of property id.
	 */
	public idtype getId() {
		return this.id;
	}

	public TreeSet<String> getTypes() {
		return types;
	}

	@Override
	public void paint(Graphics2D g2) {
	}

	@Override
	public void paintIcon(Graphics2D g2) {
		g2.drawImage(appearance90, 0, 0, null);
	}

	public void paintIcon(Graphics2D g2, boolean forward) {
		g2.drawImage(forward ? appearance90 : appearance270, 0, 0, null);
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Getter for property speed_empty.
	 *
	 * @return Value of property speed_empty.
	 */
	public int getSpeed_empty() {
		return this.speed_empty;
	}

	/**
	 * Setter for property speed_empty.
	 *
	 * @param speed_empty New value of property speed_empty.
	 */
	public void setSpeed_empty(int speed_empty) {
		this.speed_empty = speed_empty;
	}

	/**
	 * Getter for property speed_full.
	 *
	 * @return Value of property speed_full.
	 */
	public int getSpeed_full() {
		return this.speed_full;
	}

	/**
	 * Setter for property speed_full.
	 *
	 * @param speed_full New value of property speed_full.
	 */
	public void setSpeed_full(int speed_full) {
		this.speed_full = speed_full;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof rollingstock) {
			return compareTo(((rollingstock) o).getName());
		} else if (o instanceof String) {
			return name.compareTo((String) o);
		}
		return name.compareTo(o.toString());
	}

	@Override
	public int getPaintOrientation() {
		return 1;
	}
}
