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
package org.railsim.gui.panels;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.*;
import javax.swing.tree.*;

import java.util.*;

import javax.swing.GroupLayout.*;

import org.railsim.*;
import org.railsim.editor.*;
import org.railsim.event.AbstractEvent;
import org.railsim.event.AbstractListener;
import org.railsim.gui.*;
import org.railsim.gui.renderer.*;
import org.railsim.service.*;
import org.railsim.service.trackObjects.*;
import org.railsim.toolset.*;

/**
 *
 * @author js
 */
public class sidepanel_editsignal extends javax.swing.JPanel {

	boolean isvisible = false;
	boolean myClick = false;
	boolean myEvent = false;
	boolean buildTree = false;
	boolean myNotTableClick = false;
	boolean updateMutex = false;
	boolean updateSignalState = false;
	boolean updatePopup = false;
	comparableDefaultMutableTreeNode root = null;
	DefaultTreeModel treeModel = null;
	pathableObject currentSignal = null;
	path currentPath = null;
	pathroute currentPathroute = null;
	LinkedList<pathroute> visiblePathRouteList = null;
	final static boolean CURRENTSIGNALDEBUG = false;
	private Color standardColor;

	class mutexPath implements Comparable {

		public path p;

		public mutexPath(path _p) {
			p = _p;
		}

		@Override
		public String toString() {
			return p.getName() + " (" + p.getSignal().getRegion() + ")";
		}

		@Override
		public int compareTo(Object o) {
			mutexPath pp = (mutexPath) o;
			int r = p.getSignal().getRegion().compareTo(pp.p.getSignal().getRegion());
			if (r == 0) {
				r = p.getName().compareTo(pp.p.getName());
			}
			return r;
		}
	}

	class edTableModel extends DefaultTableModel {

		Class[] types = new Class[]{
			route.class, java.lang.Integer.class
		};
		//HashMap<Integer,Boolean> edMode=new HashMap<Integer,Boolean>();

		public edTableModel() {
			super(new Object[][]{
						{null, null}
					},
					new String[]{
						"Route", "Priorität"
					});
		}

		public void setEditMode(int row, boolean m) {
			//edMode.put(row,m);
		}

		@Override
		public Class getColumnClass(int columnIndex) {
			return types[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}
	};
	edTableModel dataTabModel = new edTableModel();

	class pathTableModelType extends DefaultTableModel {

		Class[] types = new Class[]{
			String.class, String.class, java.lang.Boolean.class
		};
		//HashMap<Integer,Boolean> edMode=new HashMap<Integer,Boolean>();

		public pathTableModelType() {
			super(new Object[][]{
						{null, null, null}
					},
					new String[]{
						"Gleis", "Weiche", "Richtung"
					});
		}

		public void setEditMode(int row, boolean m) {
			//edMode.put(row,m);
		}

		@Override
		public Class getColumnClass(int columnIndex) {
			return types[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 2) {
				return getValueAt(rowIndex, columnIndex) != null;
			} else {
				return false;
			}
		}
	};
	pathTableModelType pathTableModel = new pathTableModelType();

	/**
	 * Creates new form sidepanel_editsignal
	 */
	public sidepanel_editsignal() {
		initComponents();
		signalsTree.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int x = e.getX();
					int y = e.getY();
					updatePopup = true;
					TreePath path = signalsTree.getPathForLocation(x, y);
					if (path != null) {
						signalsTree.setSelectionPath(path);
						addMI.setEnabled(false);
						copyMI.setEnabled(false);
						delMI.setEnabled(false);
						delallMI.setEnabled(false);
						manualMI.setEnabled(false);
						renameMI.setEnabled(false);
						penabledMI.setEnabled(false);
						penabledMI.setSelected(false);
						if (currentPath != null) {
							if (currentSignal.isEnabled()) {
								manualMI.setEnabled(true);
							}
							if (!currentPath.isAutomatic()) {
								if (!currentSignal.isEnabled()) {
									delMI.setEnabled(true);
									copyMI.setEnabled(true);
									addMI.setEnabled(true);
									delallMI.setEnabled(true);
								}
								renameMI.setEnabled(true);
								penabledMI.setEnabled(true);
								penabledMI.setSelected(!currentPath.isEnabled());
							}
						} else if (currentSignal != null) {
							addMI.setEnabled(true);
							delallMI.setEnabled(true);
							renameMI.setEnabled(true);
							penabledMI.setEnabled(true);
							penabledMI.setSelected(!currentSignal.isEnabled());
						} else if (path.getPathCount() > 1) {
							renameMI.setEnabled(true);
						}
						signalsTreePopup.show(signalsTree, x, y);
						//m_clickedPath = path;
					}
					updatePopup = false;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		addPropertyChangeListener(MainBar.PANELVISIBILITY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				visibilityChange((Boolean) evt.getNewValue());
			}
		});
		dataCollector.collector.editorEventListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				trackEvent((EditorEvent) e);
			}
		});
		dataCollector.collector.pathListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateMutexCB();
			}
		});

		integer_tableCellEditor itce = new integer_tableCellEditor(route.PRIOMIN, route.PRIOMAX);
		routeTable.setDefaultEditor(route.class, new combobox_tableCellEditor(new routeCBfiller()));
		routeTable.setDefaultEditor(Integer.class, itce);
		routeTable.setDefaultRenderer(Integer.class, itce);

		// void 	setDefaultRenderer(Class<?> columnClass, TableCellRenderer renderer)
		routeTable.setModel(dataTabModel);
		routeTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		ListSelectionModel rowSM = routeTable.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				int row = lsm.getMinSelectionIndex(); //System.out.println("R1:"+row);

				if (visiblePathRouteList != null && visiblePathRouteList.size() > row && row >= 0) {
					pathroute pr = visiblePathRouteList.get(row);
					//if (pr!=currentPathroute)
					{
						currentPathroute = pr;
						routeDelButton.setEnabled(true);
						updateMutex();
					}
				} else {
					currentPathroute = null;
					routeDelButton.setEnabled(false);
					updateMutex();
				}
			}
		});

		dataTabModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (myNotTableClick) {
					return;
				}
				if (e.getType() != TableModelEvent.UPDATE) {
					return;
				}
				int row = e.getFirstRow();  //System.out.println("R2:"+row);
				int column = e.getColumn();
				if (visiblePathRouteList != null && visiblePathRouteList.size() > row && row >= 0) {
					pathroute pr = visiblePathRouteList.get(row);
					if (pr != null) // if (pr==currentPathroute)
					{
						if (column < 0) {
							//System.out.println("TME: "+column+"/"+row+"::"+e.getType());
							//routeDelButton.setEnabled(true);
							//updateMutex();
						} else {
							//System.out.println("TME: "+column+"/"+row+"::"+e.getType()+" => "+dataTabModel.getValueAt(row,column));

							if (dataTabModel.getValueAt(row, 0) != null) {
								pr.setRoute((route) dataTabModel.getValueAt(row, 0));
							}
							if (dataTabModel.getValueAt(row, 1) != null) {
								pr.setPriority((Integer) dataTabModel.getValueAt(row, 1));
							}
						}
					}
					//else
					//System.out.println("nö");
				}
			}
		});
		dataCollector.collector.routeListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateRouteTable();
			}
		});

		junctionstate_tableCellEditor jtce = new junctionstate_tableCellEditor();
		pathTable.setDefaultEditor(Boolean.class, jtce);
		pathTable.setDefaultRenderer(Boolean.class, jtce);

		pathTable.setModel(pathTableModel);
		// junctionstate_tableCellEditor
		pathTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		pathTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() != TableModelEvent.UPDATE) {
					return;
				}
				if (myEvent) {
					return;
				}

				int row = e.getFirstRow();  //System.out.println("R2:"+row);
				int column = e.getColumn();

				int s = ((Boolean) pathTableModel.getValueAt(row, 2)) ? 1 : 0;
				try {
					track o = null;
					o = currentPath.getTracks().get(row).getTrack();
					dataCollector.collector.thegame.runAction(new EditorActionEvent2<>("switchjunction", o, s));
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("1:" + currentPath);
					System.out.println("2:" + currentPath.getTracks());
					System.out.println("3:" + currentPath.getTracks().get(row));
					System.out.println("4:" + currentPath.getTracks().get(row).getTrack());
				}
			}
		});
		standardColor = pathTable.getBackground();
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        signalsTreePopup = new javax.swing.JPopupMenu();
        renameMI = new javax.swing.JMenuItem();
        manualMI = new javax.swing.JMenuItem();
        penabledMI = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        addMI = new javax.swing.JMenuItem();
        copyMI = new javax.swing.JMenuItem();
        delMI = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        delallMI = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        signalsTree = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        delpath_Button = new javax.swing.JButton();
        manual_Button = new javax.swing.JButton();
        penabled_ToggleButton = new javax.swing.JToggleButton();
        newpath_Button = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        pathTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        routeTable = new javax.swing.JTable();
        routeAddButton = new javax.swing.JButton();
        routeDelButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        mutexPanel = new javax.swing.JPanel();
        mutexComboBox = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        signalstate_CB = new javax.swing.JComboBox();

        renameMI.setText("umbenennen");
        renameMI.setActionCommand("rename");
        renameMI.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                renameMIActionPerformed(evt);
            }
        });

        signalsTreePopup.add(renameMI);

        manualMI.setText("Fahrstra\u00dfe manuell");
        manualMI.setActionCommand("setpath");
        manualMI.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonActionPerformed(evt);
            }
        });

        signalsTreePopup.add(manualMI);

        penabledMI.setText("Fahrstra\u00dfe abschalten");
        penabledMI.setActionCommand("disablepath");
        penabledMI.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                penabledMIActionPerformed(evt);
            }
        });

        signalsTreePopup.add(penabledMI);

        signalsTreePopup.add(jSeparator2);

        addMI.setText("neue Fahrstra\u00dfe");
        addMI.setActionCommand("newpath");
        addMI.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonActionPerformed(evt);
            }
        });

        signalsTreePopup.add(addMI);

        copyMI.setText("Fahrstra\u00dfe kopieren");
        copyMI.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                copyMIActionPerformed(evt);
            }
        });

        signalsTreePopup.add(copyMI);

        delMI.setText("Fahrstra\u00dfe l\u00f6schen");
        delMI.setActionCommand("delpath");
        delMI.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonActionPerformed(evt);
            }
        });

        signalsTreePopup.add(delMI);

        signalsTreePopup.add(jSeparator1);

        delallMI.setText("alle Fahrstra\u00dfen l\u00f6schen");
        delallMI.setActionCommand("delallpath");
        delallMI.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonActionPerformed(evt);
            }
        });

        signalsTreePopup.add(delallMI);

        setPreferredSize(new java.awt.Dimension(310, 400));
        signalsTree.setCellEditor(new treeCellEditor(signalsTree,new treeCellRenderer()));
        signalsTree.setCellRenderer(new treeCellRenderer());
        signalsTree.setEditable(true);
        signalsTree.setLargeModel(true);
        signalsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt)
            {
                signalsTreeValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(signalsTree);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Fahrstra\u00dfe"));
        delpath_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png")));
        delpath_Button.setText("Delete");
        delpath_Button.setActionCommand("delpath");
        delpath_Button.setEnabled(false);
        delpath_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        delpath_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonActionPerformed(evt);
            }
        });

        manual_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/setpath.png")));
        manual_Button.setText("manuell setzen");
        manual_Button.setActionCommand("setpath");
        manual_Button.setEnabled(false);
        manual_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        manual_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonActionPerformed(evt);
            }
        });

        penabled_ToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/disable path.png")));
        penabled_ToggleButton.setText("abschalten");
        penabled_ToggleButton.setActionCommand("disablepath");
        penabled_ToggleButton.setEnabled(false);
        penabled_ToggleButton.setMargin(new java.awt.Insets(2, 5, 2, 5));
        penabled_ToggleButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                penabled_ToggleButtonActionPerformed(evt);
            }
        });

        newpath_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/add.png")));
        newpath_Button.setText("neu erzeugen");
        newpath_Button.setActionCommand("newpath");
        newpath_Button.setEnabled(false);
        newpath_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        newpath_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(newpath_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(delpath_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(penabled_ToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addComponent(manual_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manual_Button)
                    .addComponent(newpath_Button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delpath_Button)
                    .addComponent(penabled_ToggleButton)))
        );

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jScrollPane2.setEnabled(false);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(250, 140));
        pathTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        jScrollPane2.setViewportView(pathTable);

        jTabbedPane1.addTab("Weg", new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/pathway.png")), jScrollPane2);

        routeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {},
                {},
                {},
                {}
            },
            new String []
            {

            }
        ));
        routeTable.setEnabled(false);
        jScrollPane3.setViewportView(routeTable);

        routeAddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/add.png")));
        routeAddButton.setText("weitere Route");
        routeAddButton.setActionCommand("addRoute");
        routeAddButton.setEnabled(false);
        routeAddButton.setMargin(new java.awt.Insets(0, 3, 0, 3));
        routeAddButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                routeAddButtonActionPerformed(evt);
            }
        });

        routeDelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png")));
        routeDelButton.setText("Route entfernen");
        routeDelButton.setActionCommand("delRoute");
        routeDelButton.setEnabled(false);
        routeDelButton.setMargin(new java.awt.Insets(0, 3, 0, 3));
        routeDelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                routeDelButtonActionPerformed(evt);
            }
        });

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0), "Ausschlu\u00df"));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mutexPanel.setLayout(new java.awt.GridLayout(0, 1));

        mutexPanel.setToolTipText("Fahrstra\u00dfenausschluss: Wenn eine dieser Fahrstra\u00dfen anliegt, \nkann die gew\u00e4hlte Fahrstra\u00dfe nicht f\u00fcr diese Route gesetzt werden");
        jScrollPane4.setViewportView(mutexPanel);

        jPanel3.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        mutexComboBox.setModel(new javax.swing.DefaultComboBoxModel());
        mutexComboBox.setToolTipText("Fahrstra\u00dfe als Ausschlu\u00df hinzuf\u00fcgen");
        mutexComboBox.setEnabled(false);
        mutexComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mutexComboBoxActionPerformed(evt);
            }
        });

        jPanel3.add(mutexComboBox, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(routeAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(routeDelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(routeDelButton)
                    .addComponent(routeAddButton)))
        );
        jTabbedPane1.addTab("Routen", new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/pathroutes.png")), jPanel2);

        jLabel1.setText("Signalbild");

        signalstate_CB.setEnabled(false);
        signalstate_CB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                signalstate_CBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(signalstate_CB, 0, 237, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(signalstate_CB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("Optionen", new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/pathoption.png")), jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void penabledMIActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_penabledMIActionPerformed
    {//GEN-HEADEREND:event_penabledMIActionPerformed
		if (updatePopup) {
			return;
		}
		if (currentPath != null) {
			penabled_ToggleButton.setSelected(penabledMI.isSelected());
			dataCollector.collector.thegame.runAction(new EditorActionEvent<>("disablepath", penabledMI.isSelected()));
		} else if (currentSignal != null) {
			dataCollector.collector.thegame.runAction(new EditorActionEvent<>("enablesignal", !penabledMI.isSelected()));
		}
    }//GEN-LAST:event_penabledMIActionPerformed

    private void penabled_ToggleButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_penabled_ToggleButtonActionPerformed
    {//GEN-HEADEREND:event_penabled_ToggleButtonActionPerformed
		if (myClick || currentPath == null) {
			return;
		}
		dataCollector.collector.thegame.runAction(new EditorActionEvent<>("disablepath", penabled_ToggleButton.isSelected()));
    }//GEN-LAST:event_penabled_ToggleButtonActionPerformed

    private void signalstate_CBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_signalstate_CBActionPerformed
    {//GEN-HEADEREND:event_signalstate_CBActionPerformed
		if (updateSignalState || currentPath == null) {
			return;
		}
		currentPath.setStartState(((stateText) signalstate_CB.getSelectedItem()).id);
    }//GEN-LAST:event_signalstate_CBActionPerformed

    private void copyMIActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_copyMIActionPerformed
    {//GEN-HEADEREND:event_copyMIActionPerformed
		TreePath t = signalsTree.getSelectionPath();
		if (t != null) {
			comparableDefaultMutableTreeNode cdmtn = (comparableDefaultMutableTreeNode) t.getLastPathComponent();
			Object o = cdmtn.getUserObject();
			if (o instanceof path) {
				dataCollector.collector.thegame.runAction(new EditorActionEvent<>("copypath", (path) o));
			}
		}
    }//GEN-LAST:event_copyMIActionPerformed

    private void mutexComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mutexComboBoxActionPerformed
    {//GEN-HEADEREND:event_mutexComboBoxActionPerformed
		if (!updateMutex) {
			mutexPath o = (mutexPath) mutexComboBox.getSelectedItem();
			if (o != null && o.p != null) {
				currentPathroute.addMutex(o.p);
				updateMutex();
			}
			mutexComboBox.setSelectedIndex(-1);
		}
    }//GEN-LAST:event_mutexComboBoxActionPerformed

    private void routeDelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_routeDelButtonActionPerformed
    {//GEN-HEADEREND:event_routeDelButtonActionPerformed
		TableCellEditor ed = routeTable.getCellEditor();
		if (ed != null) {
			ed.stopCellEditing();
		}
		currentPath.delRoute(currentPathroute);
		visiblePathRouteList.remove(currentPathroute);
		currentPathroute = null;
		routeDelButton.setEnabled(false);
		updateMutex();
		dataTabModel.removeRow(routeTable.getSelectedRow());
    }//GEN-LAST:event_routeDelButtonActionPerformed

    private void routeAddButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_routeAddButtonActionPerformed
    {//GEN-HEADEREND:event_routeAddButtonActionPerformed
		currentPathroute = null;
		pathroute pr = new pathroute();
		currentPath.addRoute(pr);
		visiblePathRouteList.add(pr);
		dataTabModel.addRow(new Object[]{pr.getRoute(), pr.getPriority()});
		routeTable.changeSelection(dataTabModel.getRowCount() - 1, 0, false, false);
		//currentPathroute
    }//GEN-LAST:event_routeAddButtonActionPerformed

    private void ButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ButtonActionPerformed
    {//GEN-HEADEREND:event_ButtonActionPerformed
		dataCollector.collector.thegame.runAction(new EditorActionEvent(evt));
    }//GEN-LAST:event_ButtonActionPerformed

    private void renameMIActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_renameMIActionPerformed
    {//GEN-HEADEREND:event_renameMIActionPerformed
		signalsTree.startEditingAtPath(signalsTree.getSelectionPath());
    }//GEN-LAST:event_renameMIActionPerformed

    private void signalsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_signalsTreeValueChanged
    {//GEN-HEADEREND:event_signalsTreeValueChanged
		if (buildTree) {
			return;
		}
		TreePath t = signalsTree.getSelectionPath();
		myClick = true;

		currentSignal = null;
		currentPath = null;
		currentPathroute = null;
		if (t != null) {
			comparableDefaultMutableTreeNode cdmtn = (comparableDefaultMutableTreeNode) t.getLastPathComponent();
			Object o = cdmtn.getUserObject();
			if (o instanceof pathableObject) { // Signal
				currentSignal = (pathableObject) o;
				treeModel.nodeChanged(cdmtn);

				newpath_Button.setEnabled(!currentSignal.isEnabled());
				delpath_Button.setEnabled(false);
				penabled_ToggleButton.setEnabled(false);
				pathTable.setEnabled(!currentSignal.isEnabled());

				manual_Button.setEnabled(false);
				routeTable.setEnabled(false);
				routeAddButton.setEnabled(false);
				routeDelButton.setEnabled(false);
				signalstate_CB.setEnabled(false);
				if (!myEvent) {
					dataCollector.collector.thegame.runAction(new EditorActionEvent<>("selectsignal", (pathableObject) o));
				}
				updateRouteTable();
				updateMutex();
				updateSignalStates();
			} else if (o instanceof path) { // path
				currentPath = (path) o;
				treeModel.nodeChanged(cdmtn);

				manual_Button.setEnabled(false);

				routeTable.setEnabled(true);
				routeAddButton.setEnabled(!currentPath.isAutomatic());
				routeDelButton.setEnabled(false);
				signalstate_CB.setEnabled(!currentPath.isAutomatic());

				if (!myEvent) {
					dataCollector.collector.thegame.runAction(new EditorActionEvent<>("selectpath", (path) o));
				} else {
					comparableDefaultMutableTreeNode c = root.findObject(currentPath);
					comparableDefaultMutableTreeNode c2 = (comparableDefaultMutableTreeNode) c.getParent();
					currentSignal = (pathableObject) c2.getUserObject();
				}
				pathTable.setEnabled(!currentSignal.isEnabled());
				newpath_Button.setEnabled(!currentSignal.isEnabled());
				delpath_Button.setEnabled(!currentSignal.isEnabled());
				if (currentPath.isAutomatic()) {
					penabled_ToggleButton.setEnabled(false);
					penabled_ToggleButton.setSelected(!currentPath.isEnabled());
				} else {
					penabled_ToggleButton.setEnabled(true);
					penabled_ToggleButton.setSelected(!currentPath.isEnabled());
				}

				updateRouteTable();
				updateMutex();
				updateSignalStates();

				// JTree.startEditingAtPath(TreePath) <----
			} else {
				newpath_Button.setEnabled(false);
				delpath_Button.setEnabled(false);
				penabled_ToggleButton.setEnabled(false);
				manual_Button.setEnabled(false);

				manual_Button.setEnabled(false);
				routeTable.setEnabled(false);
				routeAddButton.setEnabled(false);
				routeDelButton.setEnabled(false);
				signalstate_CB.setEnabled(false);
				pathTable.setEnabled(false);
				updateRouteTable();
				updateMutex();
			}
		} else {
			newpath_Button.setEnabled(false);
			delpath_Button.setEnabled(false);
			penabled_ToggleButton.setEnabled(false);
			manual_Button.setEnabled(false);

			manual_Button.setEnabled(false);
			routeTable.setEnabled(false);
			routeAddButton.setEnabled(false);
			routeDelButton.setEnabled(false);
			signalstate_CB.setEnabled(false);
			pathTable.setEnabled(false);
			updateRouteTable();
			updateMutex();
		}

		myClick = false;
    }//GEN-LAST:event_signalsTreeValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addMI;
    private javax.swing.JMenuItem copyMI;
    private javax.swing.JMenuItem delMI;
    private javax.swing.JMenuItem delallMI;
    private javax.swing.JButton delpath_Button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem manualMI;
    private javax.swing.JButton manual_Button;
    private javax.swing.JComboBox mutexComboBox;
    private javax.swing.JPanel mutexPanel;
    private javax.swing.JButton newpath_Button;
    private javax.swing.JTable pathTable;
    private javax.swing.JCheckBoxMenuItem penabledMI;
    private javax.swing.JToggleButton penabled_ToggleButton;
    private javax.swing.JMenuItem renameMI;
    private javax.swing.JButton routeAddButton;
    private javax.swing.JButton routeDelButton;
    private javax.swing.JTable routeTable;
    private javax.swing.JTree signalsTree;
    private javax.swing.JPopupMenu signalsTreePopup;
    private javax.swing.JComboBox signalstate_CB;
    // End of variables declaration//GEN-END:variables

	void visibilityChange(boolean newval) {
		isvisible = newval;
		updateTree();
	}

	void updateTree() {
		newpath_Button.setEnabled(false);
		delpath_Button.setEnabled(false);
		penabled_ToggleButton.setEnabled(false);
		pathTable.setEnabled(false);
		root = new comparableDefaultMutableTreeNode("Fahrstraßen");
		if (treeModel == null) {
			treeModel = new DefaultTreeModel(root);
			signalsTree.setModel(treeModel);
			treeModel.addTreeModelListener(new TreeModelListener() {
				@Override
				public void treeNodesChanged(TreeModelEvent e) { // editor
					if (myClick) {
						return;
					}
					Object[] tp = e.getChildren();
					if (tp.length > 0) {
						comparableDefaultMutableTreeNode cdmtn = (comparableDefaultMutableTreeNode) tp[0];
						Object o = cdmtn.getUserObject();
						if (o instanceof pathableObject) { // Signal neuer Name
							//((trackObject)o).setName(cdmtn.getUpdatedUserObject().toString());
							dataCollector.collector.thegame.runAction(new EditorActionEvent2<>("renamesignal", (pathableObject) o, cdmtn.getUpdatedUserObject().toString()));
						} else if (o instanceof path) {
							//((path)o).setName(cdmtn.getUpdatedUserObject().toString());
							dataCollector.collector.thegame.runAction(new EditorActionEvent2<>("renamepath", (path) o, cdmtn.getUpdatedUserObject().toString()));
						} else { // region!
							dataCollector.collector.thegame.runAction(new EditorActionEvent2<>("renameregion", (String) o, cdmtn.getUpdatedUserObject().toString()));
						}
					}
				}

				@Override
				public void treeNodesInserted(TreeModelEvent e) {
				}

				@Override
				public void treeNodesRemoved(TreeModelEvent e) {
				}

				@Override
				public void treeStructureChanged(TreeModelEvent e) {
				}
			});
		}
		if (isvisible) {
			buildTree = true;
			comparableDefaultMutableTreeNode parent = root;
			comparableDefaultMutableTreeNode selected = null;
			for (String r : trackObject.allregions) {
				comparableDefaultMutableTreeNode reg = new comparableDefaultMutableTreeNode(r);
				parent.add(reg);
				TreeSet<comparableDefaultMutableTreeNode> ts = new TreeSet<>();
				for (trackObject to : trackObject.allto.keySet()) {
					if (to instanceof pathableObject && to.getRegion().compareTo(r) == 0 && to.getTrackData() != null) {
						comparableDefaultMutableTreeNode obj = new comparableDefaultMutableTreeNode(to, true);
						ts.add(obj);
						if (to == currentSignal) {
							selected = obj;
						}
						pathableObject po = (pathableObject) to;
						TreeSet<path> apath = po.getAllPaths();
						for (path p : apath) {
							comparableDefaultMutableTreeNode obj2 = new comparableDefaultMutableTreeNode(p, true);
							obj.add(obj2);
							if (p == currentPath) {
								selected = obj2;
							}
						}
					}
				}
				if (!ts.isEmpty()) {
					for (comparableDefaultMutableTreeNode dmtn : ts) {
						reg.add(dmtn);
					}
				}
			}
			treeModel.setRoot(root);
			buildTree = false;
			if (selected != null) {
				TreeNode[] tn = selected.getPath();
				TreePath tp = new TreePath(tn);
				signalsTree.setSelectionPath(tp);
				signalsTree.makeVisible(tp);
				signalsTree.scrollPathToVisible(tp);
			} else {
				currentSignal = null;
			}
		}
	}

	void trackEvent(EditorEvent e) {
		comparableDefaultMutableTreeNode c, c2;
		myEvent = true;

		comparableDefaultMutableTreeNode selected = null;
		Enumeration en;
		switch (e.getType()) {
			case EditorEvent.PATHEDIT_PATHABLE_SELECTED:
				currentSignal = (pathableObject) e.getTrackObject();
				selected = root.findObject(currentSignal);
				if (!myClick && selected != null) {
					TreeNode[] tn = selected.getPath();
					TreePath tp = new TreePath(tn);
					signalsTree.setSelectionPath(tp);
					signalsTree.makeVisible(tp);
					signalsTree.scrollPathToVisible(tp);
				}
				break;
			case EditorEvent.PATHEDIT_PATH_SELECTED:
				currentPath = (path) e.getPath();

				c = root.findObject(currentPath);
				if (c != null) {
					c2 = (comparableDefaultMutableTreeNode) c.getParent();
					selected = c;
					currentSignal = (pathableObject) c2.getUserObject();
				} else {
					currentPath = null;
				}
				if (!myClick && selected != null) {
					TreeNode[] tn = selected.getPath();
					TreePath tp = new TreePath(tn);
					signalsTree.setSelectionPath(tp);
					signalsTree.makeVisible(tp);
					signalsTree.scrollPathToVisible(tp);
				}
				updateMutexCB();
				updatePath();
				break;
			case EditorEvent.PATHEDIT_PATHABLE_UNSELECTED:
				if (!myClick) {
					if (currentSignal != null) {
						comparableDefaultMutableTreeNode t = root.findObject(currentSignal);
						if (t != null) {
							TreeNode[] tn = ((comparableDefaultMutableTreeNode) t.getParent()).getPath();
							TreePath tp = new TreePath(tn);
							signalsTree.setSelectionPath(tp);
							signalsTree.makeVisible(tp);
							signalsTree.scrollPathToVisible(tp);
						}
					} else {
						signalsTree.setSelectionPath(null);
					}
				}
				currentSignal = null;
				break;
			case EditorEvent.PATHEDIT_PATH_UNSELECTED:
				currentPath = null;
				if (!myClick) {
					if (currentSignal != null) {
						comparableDefaultMutableTreeNode t = root.findObject(currentSignal);
						if (t != null) {
							TreeNode[] tn = t.getPath();
							TreePath tp = new TreePath(tn);
							signalsTree.setSelectionPath(tp);
							signalsTree.makeVisible(tp);
							signalsTree.scrollPathToVisible(tp);
						} else {
							signalsTree.setSelectionPath(null);
						}
					} else {
						signalsTree.setSelectionPath(null);
					}
				}
				break;
			case EditorEvent.PATHEDIT_PATH_DELETED:
				currentPath = null;
				if (!myClick) {
					updateTree();
				}
				break;
			case EditorEvent.PATHEDIT_REGION_RENAMED:
				if (!myClick) {
					updateTree();
				}
				updateMutex();
				break;
			case EditorEvent.PATHEDIT_PATH_RENAMED:
				updateMutex();
				break;
			case EditorEvent.PATHEDIT_PATHABLE_RENAMED:
				break;
			case EditorEvent.PATHEDIT_PATHABLE_STATECHANGED:
				comparableDefaultMutableTreeNode cdmtn = root.findObject(e.getTrackObject());
				if (cdmtn != null) {
					myClick = true;
					treeModel.nodeChanged(cdmtn);
					myClick = false;
				}
				signalsTreeValueChanged(null);
				break;
			case EditorEvent.PATHEDIT_PATH_ADDED:
				currentPath = (path) e.getPath();
				updateTree();
				break;
			case EditorEvent.PATHEDIT_PATH_MODIFIED:
				if (currentPath == (path) e.getPath()) {
					updatePath();
				}
				break;
		}

		myEvent = false;
	}

	private void updateRouteTable() {
		TableCellEditor ed = routeTable.getCellEditor();
		if (ed != null) {
			ed.stopCellEditing();
		}
		dataTabModel.setRowCount(0);
		routeTable.setRowHeight(25);
		currentPathroute = null;
		if (currentPath != null) {
			visiblePathRouteList = currentPath.getRoutes();
			dataTabModel.setRowCount(visiblePathRouteList.size());
			int i = 0;
			for (pathroute pr : visiblePathRouteList) {
				dataTabModel.setValueAt(pr.getRoute(), i, 0);
				dataTabModel.setValueAt(pr.getPriority(), i, 1);
				++i;
			}
		}
	}
	ActionListener l = new ActionListener() {
		@Override
		public void actionPerformed(java.awt.event.ActionEvent ev) {
			int i = Integer.parseInt(ev.getActionCommand());
			currentPathroute.delMutex(i);
			updateMutex();
		}
	};

	private void updateMutex() {
		mutexPanel.removeAll();
		GroupLayout layout;
		layout = new GroupLayout(mutexPanel);
		mutexPanel.setLayout(layout);
		// Turn on automatically adding gaps between components
		layout.setAutoCreateGaps(true);

		// Turn on automatically creating gaps between components that touch
		// the edge of the container and the container.
		layout.setAutoCreateContainerGaps(true);

		if (currentPathroute == null) {
			mutexPanel.revalidate();
			mutexComboBox.setEnabled(false);
			return;
		}
		mutexComboBox.setEnabled(true);
		updateMutex = true;

		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

		GroupLayout.ParallelGroup hpg1 = layout.createParallelGroup();
		hGroup.addGroup(hpg1);
		GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup();
		hGroup.addGroup(hpg2);
		int i = 0;
		for (path p : currentPathroute.getMutex()) {
			//java.awt.FlowLayout fl=new java.awt.FlowLayout();
			//fl.setAlignment(java.awt.FlowLayout.LEFT);
			//JPanel panel=new JPanel(fl);
			//JButton button=new JButton("x");
			JButton button = new JButton();
			button.setFocusPainted(false);
			button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png")));
			button.setFocusable(false);
			button.setActionCommand(i + "");
			button.addActionListener(l);
			button.setToolTipText("lösche Ausschluss " + p.getName());
			button.setMargin(new java.awt.Insets(0, 1, 0, 1));
			JLabel lab = new JLabel(p.getName());
			//panel.add(button);
			//panel.add(lab);
			//mutexPanel.add(panel);

			hpg1.addComponent(button);
			hpg2.addComponent(lab);
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(button).addComponent(lab));
			i++;
		}
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
		mutexPanel.revalidate();
		updateMutexCB();
		updateMutex = false;
	}

	private void updateSignalStates() {
		updateSignalState = true;
		signalstate_CB.removeAllItems();

		ArrayList<stateText> t = currentSignal.getStateText();
		for (stateText st : t) {
			if (st.id != 0) {
				signalstate_CB.addItem(st);
			}
			if (currentPath != null && st.id == currentPath.getStartState()) {
				updateSignalState = false;
				signalstate_CB.setSelectedItem(st);
			}

		}
		updateSignalState = false;
	}

	private void updateMutexCB() {
		updateMutex = true;
		TreeSet<mutexPath> tm = new TreeSet<>();
		if (currentSignal != null) {
			for (trackObject to : trackObject.allto.keySet()) {
				if (to instanceof pathableObject && to.getTrackData() != null && to != currentSignal) {
					pathableObject po = (pathableObject) to;
					TreeSet<path> apath = po.getAllPaths();
					for (path p : apath) {
						if (!p.isAutomatic()) {
							tm.add(new mutexPath(p));
						}
					}
				}
			}
		}
		mutexComboBox.removeAllItems();
		mutexComboBox.setSelectedIndex(-1);

		for (mutexPath mp : tm) {
			mutexComboBox.addItem(mp);
		}
		mutexComboBox.setSelectedIndex(-1);
		updateMutex = false;
	}

	private void updatePath() {
		TableCellEditor ed = pathTable.getCellEditor();
		if (ed != null) {
			ed.stopCellEditing();
		}
		pathTableModel.setRowCount(0);

		if (currentPath == null) {
			return;
		}

		if (currentPath.isFailure()) {
			pathTable.setBackground(Color.red);
		} else {
			pathTable.setBackground(standardColor);
		}

		int i = 0, j = 0;
		for (pathtrack pt : currentPath.getTracks()) {
			i++;
			Object[] o = new Object[3];
			o[0] = "" + i;
			if (pt.getTrack().isJunction()) {
				j++;
				o[1] = "" + j;
				o[2] = new Boolean(pt.getJunctionstate() > 0);
			} else {
				o[1] = null;
				o[2] = null;
			}
			pathTableModel.addRow(o);
		}
		if (currentPath.searchpath != null) {
			if (currentPath.searchpath.getLast() instanceof pathableObject && !currentPath.isFailure()) {
				pathableObject po = (pathableObject) currentPath.searchpath.getLast();
				i++;
				Object[] o = new Object[3];
				o[0] = "" + i;
				o[1] = "Signal " + po.getName();
				o[2] = null;
				pathTableModel.addRow(o);
			}
		}
	}
}
