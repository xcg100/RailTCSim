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
package org.railsim.gui;

import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.railsim.*;
import org.railsim.event.AbstractEvent;
import org.railsim.event.AbstractListener;
import org.railsim.gui.dnd.*;
import org.railsim.gui.renderer.genericPaintInterfaceComboBoxRenderer;
import org.railsim.service.route;
import org.railsim.service.statics;
import org.railsim.service.exceptions.DuplicateEntryException;
import org.railsim.service.trackObjects.*;
import org.railsim.service.trainCommands.*;

/**
 *
 * @author js
 */
public class routesEditor extends javax.swing.JDialog {

	static private routesEditor tEd = null;

	public static void openEditor(JPanel parent) {
		if (tEd == null) {
			tEd = new routesEditor(javax.swing.SwingUtilities.getWindowAncestor(parent));
		}
		tEd.setVisible(true);
	}

	private class FormattedTextFieldVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent input) {
			if (input instanceof JFormattedTextField) {
				JFormattedTextField ftf = (JFormattedTextField) input;
				AbstractFormatter formatter = ftf.getFormatter();
				if (formatter != null) {
					String text = ftf.getText();
					try {
						formatter.stringToValue(text);
						return true;
					} catch (ParseException pe) {
						return false;
					}
				}
			}
			return true;
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			return verify(input);
		}
	};
	route selectedRoute = null;
	trainCommand selectedCommand = null;
	boolean updatingRoutes = false;
	boolean updatingCommands = false;
	boolean updatingDestinations = false;
	boolean testMode = false;
	SimpleDateFormat destTime;

	/**
	 * Creates new form routeEditor
	 */
	public routesEditor(java.awt.Window parent) {
		super(parent);

		destTime = new SimpleDateFormat("mm:ss");
		destTime.setTimeZone(TimeZone.getTimeZone("GMT"));

		initComponents();
		setLocationRelativeTo(parent);
		routesList.setModel(new DefaultListModel());
		routeCommandList.setModel(new DefaultListModel());
		commandList.setModel(new DefaultListModel());

		routeCommandList.setTransferHandler(new routeCommandDND(routeCommandList));
		commandList.setTransferHandler(new commandDND());

		DropTargetListener dropTargetListener = new DropTargetListener() {
			// Die Maus betritt die Komponente mit
			// einem Objekt
			@Override
			public void dragEnter(DropTargetDragEvent e) {
			}
			// Die Komponente wird verlassen

			@Override
			public void dragExit(DropTargetEvent e) {
			}
			// Die Maus bewegt sich über die Komponente

			@Override
			public void dragOver(DropTargetDragEvent e) {
			}

			@Override
			public void drop(DropTargetDropEvent e) {
				try {
					Transferable tr = e.getTransferable();
					DataFlavor[] flavors = tr.getTransferDataFlavors();
					for (int i = 0; i < flavors.length; i++) {
						if (1 == 1 || flavors[i].isFlavorJavaFileListType()) {
							// Zunächst annehmen
							e.acceptDrop(e.getDropAction());
							List files = (List) tr.getTransferData(flavors[i]);
							// Wir setzen in das Label den Namen der ersten
							// Datei
							//delCmdButton.doClick();
							for (Object o : files) {
								if (o instanceof trainCommand) {
									((trainCommand) o).remove();
								}
							}
							e.dropComplete(true);
							return;
						}
					}
				} catch (		UnsupportedFlavorException | IOException t) {
					t.printStackTrace();
				}
				// Ein Problem ist aufgetreten
				e.rejectDrop();
			}

			// Jemand hat die Art des Drops (Move, Copy, Link)
			// geändert
			@Override
			public void dropActionChanged(DropTargetDragEvent e) {
			}
		};
		DropTarget dropTarget = new DropTarget(delCmdButton, dropTargetListener);

		DefaultListModel cldlm = (DefaultListModel) commandList.getModel();
		cldlm.addElement(new org.railsim.service.trainCommands.waitMinutes());
		cldlm.addElement(new org.railsim.service.trainCommands.gotoDestination());
		cldlm.addElement(new org.railsim.service.trainCommands.changeDirection());
		cldlm.addElement(new org.railsim.service.trainCommands.repeatRoute());
		cldlm.addElement(new org.railsim.service.trainCommands.nextRoute());
		cldlm.addElement(new org.railsim.service.trainCommands.subRoute());

		routeCommandList.getModel().addListDataListener(new ListDataListener() {
			@Override
			public void intervalAdded(ListDataEvent e) {
				routeCommandListValueChanged();
				final int sel = e.getIndex0();
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						routeCommandList.setSelectedIndex(sel);
					}
				});
			}

			@Override
			public void intervalRemoved(ListDataEvent e) {
				routeCommandListValueChanged();
			}

			@Override
			public void contentsChanged(ListDataEvent e) {
				routeCommandListValueChanged();
			}
		});

		updateRoutes();
		updateDestinations();
		dataCollector.collector.routeListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateRoutes();
			}
		});
		dataCollector.collector.trackObjectListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				//updateDestinations();
			}
		});
		dataCollector.collector.gamesetEventListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				setVisible(false);
				formWindowClosing(null);
			}
		});
	}

	public routesEditor(boolean m) {
		this(new Frame());
		statics.setLookAndFeel("System", this);
		testMode = m;
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        routesList = new javax.swing.JList();
        addButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        nameTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        commandList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        routeCommandList = new javax.swing.JList();
        delCmdButton = new javax.swing.JButton();
        dataPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        gotoDestination_CB = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        nextRoute_CB = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        subRoute_CB = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        gotoDestination_time = new JFormattedTextField(destTime);
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gotoDestination_greentime = new JFormattedTextField(destTime);
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        stopUsersTB = new javax.swing.JToggleButton();
        closeButton = new javax.swing.JButton();

        setTitle("Zugrouten Befehle");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(statics.loadGUIImage("routeeditor.png"));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        routesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        routesList.setVisibleRowCount(4);
        routesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                routesListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(routesList);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/add.png"))); // NOI18N
        addButton.setText("neue Route");
        addButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        delButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png"))); // NOI18N
        delButton.setText("lösche Route");
        delButton.setEnabled(false);
        delButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        delButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delButtonActionPerformed(evt);
            }
        });

        nameTextField.setEnabled(false);
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delButton, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
        );

        jScrollPane2.setToolTipText("");

        commandList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        commandList.setToolTipText("verfügbare Befehle, per ziehen in die Befehlsfolge aufnehmen");
        commandList.setDragEnabled(true);
        commandList.setEnabled(false);
        jScrollPane2.setViewportView(commandList);

        jScrollPane3.setToolTipText("");
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        routeCommandList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        routeCommandList.setToolTipText("Befehlsfolge, per ziehen änderbar");
        routeCommandList.setDragEnabled(true);
        routeCommandList.setDropMode(javax.swing.DropMode.INSERT);
        routeCommandList.setEnabled(false);
        routeCommandList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                routeCommandListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(routeCommandList);

        delCmdButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png"))); // NOI18N
        delCmdButton.setText("löschen");
        delCmdButton.setToolTipText("Befehl löschen - klick oder einfach Befehl drüberziehen");
        delCmdButton.setEnabled(false);
        delCmdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delCmdButtonActionPerformed(evt);
            }
        });

        dataPanel.setLayout(new java.awt.CardLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel5.setText("<html>\nUm Befehle in die Liste links zu bekommen einfach einen der Befehle in der\nrechten Liste auswählen und mit gedrücken Mausknopf in die linke Liste ziehen und den Knopf\nloslassen. Zum Verschieben der Reihenfolge der Befehle in der linken Liste einen oder mehrere\nBefehle auswählen und bei gedrücktem Mausknopf verschieben.\n</html>");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataPanel.add(jPanel6, "empty");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Fahre zu"));

        jLabel4.setText("Ziel");

        gotoDestination_CB.setRenderer(new genericPaintInterfaceComboBoxRenderer(genericPaintInterfaceComboBoxRenderer.MD_NAME));
        gotoDestination_CB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoDestination_CBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gotoDestination_CB, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gotoDestination_CB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(145, 145, 145))
        );

        dataPanel.add(jPanel1, "gotoDestination");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Richtungswechsel"));

        jLabel2.setText("<html>Fahrtrichtung des Zuges ändern.<br><br>\nDer Zug wird <b>nicht</b> umgedreht, nur die Fahrtrichtung wird geändert.\n</html>");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        dataPanel.add(jPanel3, "changeDirection");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("nächste Route"));

        nextRoute_CB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextRoute_CBActionPerformed(evt);
            }
        });

        jLabel8.setText("<html>\nEs wird die angegebene Route abgearbeitet, alle hier folgenden Befehle werden ignoriert!\n</html>");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nextRoute_CB, 0, 209, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(nextRoute_CB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        dataPanel.add(jPanel4, "nextRoute");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Unterroute"));

        subRoute_CB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subRoute_CBActionPerformed(evt);
            }
        });

        jLabel7.setText("<html>\nDiese Unterroute wird abgearbeitet und nach deren Ende wird hier weitergemacht.<br><br>\nAchtung: Springt diese Unterroute zu anderen Routen, wird dieser Sprung ausgeführt, \nendet die Unterroute nicht ohne Wiederholungsbefehl, wird nie zurückgekehrt.\n</html>");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(subRoute_CB, javax.swing.GroupLayout.Alignment.LEADING, 0, 209, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(subRoute_CB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataPanel.add(jPanel5, "subRoute");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("warten"));

        jLabel6.setText("Aufenthaltsdauer");

        gotoDestination_time.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gotoDestination_time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoDestination_timeActionPerformed(evt);
            }
        });

        jLabel9.setText("(min:sec)");

        jLabel10.setText("Fahrstraße anlegen vor Abfahrt");

        gotoDestination_greentime.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gotoDestination_greentime.setToolTipText("Fahrstraße wird versucht die angegebene Zeit vor Ablauf der Aufenthaltszeit zu setzen");
        gotoDestination_greentime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoDestination_greentimeActionPerformed(evt);
            }
        });

        jLabel11.setText("(min:sec)");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(gotoDestination_time, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel9))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addComponent(gotoDestination_greentime, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(1, 1, 1))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(gotoDestination_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gotoDestination_greentime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        dataPanel.add(jPanel7, "waitMinutes");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Route wiederholen/Subroute Ende"));

        jLabel3.setText("<html>Diese Route von Vorne starten oder wenn sie per Subroute aufgerufen wurde\nzurück zur aufrufenden Route.<br><br>\nAlle Befehle danach werden <b>nicht</b> mehr ausgeführt!</html>");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        dataPanel.add(jPanel8, "repeatRoute");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Befehle aus der Befehlsliste rechts in die Ablaufliste links ziehen oder Reihenfolge ändern");

        stopUsersTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/disable path.png"))); // NOI18N
        stopUsersTB.setText("Züge, die Route nutzen, anhalten");
        stopUsersTB.setToolTipText("Züge, die diese Route benutzen, anhalten, damit Arbeiten ohne Probleme möglich sind");
        stopUsersTB.setEnabled(false);
        stopUsersTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopUsersTBActionPerformed(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/ok.png"))); // NOI18N
        closeButton.setText("Ok");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(delCmdButton)
                                        .addGap(93, 93, 93)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                    .addComponent(stopUsersTB))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(closeButton))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delCmdButton)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dataPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stopUsersTB)
                            .addComponent(closeButton))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_closeButtonActionPerformed
    {//GEN-HEADEREND:event_closeButtonActionPerformed
		setVisible(false);
		formWindowClosing(null);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void gotoDestination_greentimeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_gotoDestination_greentimeActionPerformed
    {//GEN-HEADEREND:event_gotoDestination_greentimeActionPerformed
		if (selectedCommand instanceof waitMinutes) {
			((waitMinutes) selectedCommand).setGreenDelay((int) (((Date) gotoDestination_greentime.getValue()).getTime()) / 1000);
			routeCommandList.repaint();
		}
    }//GEN-LAST:event_gotoDestination_greentimeActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentShown
    {//GEN-HEADEREND:event_formComponentShown
		updateDestinations();
    }//GEN-LAST:event_formComponentShown

    private void subRoute_CBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_subRoute_CBActionPerformed
    {//GEN-HEADEREND:event_subRoute_CBActionPerformed
		if (selectedCommand instanceof subRoute && !updatingRoutes) {
			((subRoute) selectedCommand).setRoute((route) subRoute_CB.getSelectedItem());
			routeCommandList.repaint();
		}
    }//GEN-LAST:event_subRoute_CBActionPerformed

    private void nextRoute_CBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nextRoute_CBActionPerformed
    {//GEN-HEADEREND:event_nextRoute_CBActionPerformed
		if (selectedCommand instanceof nextRoute && !updatingRoutes) {
			((nextRoute) selectedCommand).setRoute((route) nextRoute_CB.getSelectedItem());
			routeCommandList.repaint();
		}
    }//GEN-LAST:event_nextRoute_CBActionPerformed

    private void gotoDestination_timeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_gotoDestination_timeActionPerformed
    {//GEN-HEADEREND:event_gotoDestination_timeActionPerformed
		if (selectedCommand instanceof waitMinutes) {
			((waitMinutes) selectedCommand).setDelay((int) (((Date) gotoDestination_time.getValue()).getTime()) / 1000);
			routeCommandList.repaint();
		}
    }//GEN-LAST:event_gotoDestination_timeActionPerformed

    private void gotoDestination_CBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_gotoDestination_CBActionPerformed
    {//GEN-HEADEREND:event_gotoDestination_CBActionPerformed
		if (selectedCommand instanceof gotoDestination && !updatingDestinations) {
			((gotoDestination) selectedCommand).setDestination((destinationObject) gotoDestination_CB.getSelectedItem());
			routeCommandList.repaint();
		}
    }//GEN-LAST:event_gotoDestination_CBActionPerformed

    private void stopUsersTBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stopUsersTBActionPerformed
    {//GEN-HEADEREND:event_stopUsersTBActionPerformed
		checkDisabledRoute();
    }//GEN-LAST:event_stopUsersTBActionPerformed

    private void delCmdButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_delCmdButtonActionPerformed
    {//GEN-HEADEREND:event_delCmdButtonActionPerformed
		DefaultListModel lm = (DefaultListModel) routeCommandList.getModel();

		Object[] s = routeCommandList.getSelectedValues();
		for (int i = 0; i < s.length; ++i) {
			lm.removeElement(s[i]);
			((trainCommand) s[i]).remove();
		}
    }//GEN-LAST:event_delCmdButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
		stopUsersTB.setSelected(false);
		checkDisabledRoute();
		routesList.setSelectedValue(null, true);
		if (testMode) {
			System.exit(0);
		}
    }//GEN-LAST:event_formWindowClosing

    private void routeCommandListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_routeCommandListValueChanged
    {//GEN-HEADEREND:event_routeCommandListValueChanged
		if (evt.getValueIsAdjusting() || updatingCommands) {
			return;
		}

		selectedCommand = (trainCommand) routeCommandList.getSelectedValue();
		if (selectedCommand != null) {
			((CardLayout) dataPanel.getLayout()).show(dataPanel, selectedCommand.getTypeName());
			updateCommandData();
			delCmdButton.setEnabled(true);
		} else {
			((CardLayout) dataPanel.getLayout()).show(dataPanel, "empty");
		}
    }//GEN-LAST:event_routeCommandListValueChanged

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_nameTextFieldFocusLost
    {//GEN-HEADEREND:event_nameTextFieldFocusLost
		if (selectedRoute != null) {
			try {
				selectedRoute.setName(nameTextField.getText());
				routesList.setSelectedValue(selectedRoute, true);
			} catch (DuplicateEntryException ex) {
				JOptionPane.showMessageDialog(this, "Route mit dem Namen " + nameTextField.getText() + "\nexistiert bereits!", "Route existiert!", JOptionPane.ERROR_MESSAGE);
			}
		}
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTextFieldActionPerformed
    {//GEN-HEADEREND:event_nameTextFieldActionPerformed
		try {
			selectedRoute.setName(nameTextField.getText());
			routesList.setSelectedValue(selectedRoute, true);
		} catch (DuplicateEntryException ex) {
			JOptionPane.showMessageDialog(this, "Route mit dem Namen " + nameTextField.getText() + "\nexistiert bereits!", "Route existiert!", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void delButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_delButtonActionPerformed
    {//GEN-HEADEREND:event_delButtonActionPerformed
		if (selectedRoute.isUsed()) {
			JOptionPane.showMessageDialog(this, "Route mit dem Namen " + selectedRoute.getName() + "\nwird noch benutzt!", "Route benutzt!", JOptionPane.ERROR_MESSAGE);
		} else {
			selectedRoute.remove();
			selectedRoute = null;
			delCmdButton.setEnabled(false);
			stopUsersTB.setSelected(false);
			stopUsersTB.setEnabled(false);
			((CardLayout) dataPanel.getLayout()).show(dataPanel, "empty");
			checkDisabledRoute();
		}
    }//GEN-LAST:event_delButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addButtonActionPerformed
    {//GEN-HEADEREND:event_addButtonActionPerformed
		try {
			route r = new route("neu");
			selectedRoute = r;
		} catch (DuplicateEntryException ex) {
			selectedRoute = route.allroutes.get("neu");
		}
		routesList.setSelectedValue(selectedRoute, true);
    }//GEN-LAST:event_addButtonActionPerformed

    private void routesListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_routesListValueChanged
    {//GEN-HEADEREND:event_routesListValueChanged
		route r = (route) routesList.getSelectedValue();
		if (r != null) {
			delButton.setEnabled(true);
			nameTextField.setEnabled(true);
			nameTextField.setText(r.getName());
			commandList.setEnabled(true);
			routeCommandList.setEnabled(true);
			delCmdButton.setEnabled(false);
			stopUsersTB.setSelected(false);
			stopUsersTB.setEnabled(true);
			//((CardLayout)dataPanel.getLayout()).show(dataPanel,"empty");
		} else {
			delButton.setEnabled(false);
			nameTextField.setEnabled(false);
			commandList.setEnabled(false);
			routeCommandList.setEnabled(false);
			delCmdButton.setEnabled(false);
			stopUsersTB.setSelected(false);
			stopUsersTB.setEnabled(false);
			//((CardLayout)dataPanel.getLayout()).show(dataPanel,"empty");
		}
		checkDisabledRoute();
		if (!updatingRoutes) {
			selectedRoute = r;
			showCommands();
		} else {
			((CardLayout) dataPanel.getLayout()).show(dataPanel, "empty");
		}
    }//GEN-LAST:event_routesListValueChanged
	private void routeCommandListValueChanged() {
		if (updatingCommands) {
			return;
		}

		DefaultListModel lm = (DefaultListModel) routeCommandList.getModel();
		Enumeration<trainCommand> e = (Enumeration<trainCommand>) lm.elements();
		List<trainCommand> c = selectedRoute.getNewCommandContainer();
		while (e.hasMoreElements()) {
			trainCommand tc = e.nextElement();
			c.add(tc);
		}
		selectedRoute.setNewCommandContainer(c);
	}

	void updateRoutes() {
		updatingRoutes = true;
		DefaultListModel lm = (DefaultListModel) routesList.getModel();
		lm.removeAllElements();
		subRoute_CB.removeAllItems();
		nextRoute_CB.removeAllItems();
		synchronized (route.allroutes) {
			for (route r : route.allroutes.values()) {
				lm.addElement(r);
				subRoute_CB.addItem(r);
				nextRoute_CB.addItem(r);
			}
		}
		updatingRoutes = false;
		routesList.setSelectedValue(selectedRoute, true);
	}

	void showCommands() {
		updatingCommands = true;
		DefaultListModel lm = (DefaultListModel) routeCommandList.getModel();
		lm.removeAllElements();
		if (selectedRoute != null) {
			for (trainCommand tc : selectedRoute.commands) {
				lm.addElement(tc);
			}
		}
		updatingCommands = false;
	}

	void updateDestinations() {
		updatingDestinations = true;
		TreeSet<destinationObject> alldests = new TreeSet<>();
		for (trackObject to : trackObject.allto.keySet()) {
			if (to instanceof destinationObject && to.getTrackData() != null) {
				alldests.add((destinationObject) to);
			}
		}
		Object osel = gotoDestination_CB.getSelectedItem();
		gotoDestination_CB.removeAllItems();
		for (destinationObject d : alldests) {
			gotoDestination_CB.addItem(d);
		}
		gotoDestination_CB.setSelectedItem(osel);
		updatingDestinations = false;
	}

	void updateCommandData() {
		updatingDestinations = true;
		if (selectedCommand instanceof gotoDestination) {
			gotoDestination_CB.setSelectedItem(((gotoDestination) selectedCommand).getDestination());
		} else if (selectedCommand instanceof waitMinutes) {
			gotoDestination_time.setValue(new Date(((waitMinutes) selectedCommand).getDelay() * 1000));
			gotoDestination_greentime.setValue(new Date(((waitMinutes) selectedCommand).getGreenDelay() * 1000));
		} else if (selectedCommand instanceof nextRoute) {
			nextRoute_CB.setSelectedItem(((nextRoute) selectedCommand).getRoute());
		} else if (selectedCommand instanceof subRoute) {
			subRoute_CB.setSelectedItem(((subRoute) selectedCommand).getRoute());
		}
		updatingDestinations = false;
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new routesEditor(true).setVisible(true);
			}
		});
	}
	route disabledRoute = null;

	private void checkDisabledRoute() {
		if (disabledRoute != null && !stopUsersTB.isSelected()) {
			disabledRoute.setEnabled(true);
			disabledRoute = null;
		} else if (stopUsersTB.isSelected()) {
			disabledRoute = selectedRoute;
			disabledRoute.setEnabled(false);
		}
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JList commandList;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JButton delButton;
    private javax.swing.JButton delCmdButton;
    private javax.swing.JComboBox gotoDestination_CB;
    private javax.swing.JFormattedTextField gotoDestination_greentime;
    private javax.swing.JFormattedTextField gotoDestination_time;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JComboBox nextRoute_CB;
    private javax.swing.JList routeCommandList;
    private javax.swing.JList routesList;
    private javax.swing.JToggleButton stopUsersTB;
    private javax.swing.JComboBox subRoute_CB;
    // End of variables declaration//GEN-END:variables
}
