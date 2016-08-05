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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.text.NumberFormat;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.railsim.*;
import org.railsim.editor.EditorEvent;
import org.railsim.event.AbstractEvent;
import org.railsim.event.AbstractListener;
import org.railsim.gui.minutesPicker;
import org.railsim.gui.renderer.genericPaintInterfaceComboBoxRenderer;
import org.railsim.gui.renderer.treeCellRenderer;
import org.railsim.service.route;
import org.railsim.service.statics;
import org.railsim.service.trackObjects.trackObject;
import org.railsim.service.trackObjects.trainStartObject;
import org.railsim.toolset.comparableDefaultMutableTreeNode;

/**
 *
 * @author js
 */
public class timetableEditor extends javax.swing.JDialog {

	static private timetableEditor tEd = null;
	private starttrain currentTrain = null;
	private trainStartObject currentStart = null;
	private comparableDefaultMutableTreeNode root = null;

	public static void openEditor(JPanel parent) {
		if (tEd == null) {
			tEd = new timetableEditor(javax.swing.SwingUtilities.getWindowAncestor(parent));
		}
		tEd.setVisible(true);
	}
	private boolean testMode = false;
	private DefaultTreeModel treeModel = null;
	private boolean myClick = false;
	private minutesPicker minP = null;

	/**
	 * Creates new form trainsetEditor
	 */
	public timetableEditor(java.awt.Window parent) {
		super(parent);

		initComponents();
		minP = new minutesPicker(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateSelectedStart();
			}
		});
		frequencePane.add(minP, BorderLayout.CENTER);
		pack();

		setSize(700, 450);
		setLocationRelativeTo(parent);

		trainsList.setModel(new DefaultListModel());
		trainsetsList.setModel(new DefaultListModel());

		updateData();
		updateSetlist();
		updateRoutes();

		dataCollector.collector.routeListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateRoutes();
			}
		});
		dataCollector.collector.editorEventListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				EditorEvent e2 = (EditorEvent) e;
				if (e2.getType() == EditorEvent.PATHEDIT_REGION_RENAMED || e2.getType() == EditorEvent.TRACKOBJECT_ADDEDREMOVED || e2.getType() == EditorEvent.TRACKOBJECT_MODIFIED) {
					if (e2.getTrackObject() == null || e2.getTrackObject() instanceof trainStartObject) {
						updateData();
					}
				} else if (e2.getType() == EditorEvent.TRAINSET_CHANGED) {
					updateSetlist();
				}
			}
		});
		dataCollector.collector.trackObjectListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				updateData();
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

	public timetableEditor(boolean m) {
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
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        trainsetsList = new javax.swing.JList();
        useButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        routeCB = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        vmaxField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        jScrollPane2 = new javax.swing.JScrollPane();
        trainsList = new javax.swing.JList();
        nameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        vmaxText = new javax.swing.JLabel();
        rotateButton = new javax.swing.JButton();
        loadedCB = new javax.swing.JCheckBox();
        frequencePane = new javax.swing.JPanel();
        restoreButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        startpointsTree = new javax.swing.JTree();
        copyButton = new javax.swing.JButton();
        emitNowButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();

        setTitle("Fahrplaneditor");
        setAlwaysOnTop(true);
        setIconImage(statics.loadGUIImage("timetable.png"));
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                formComponentShown(evt);
            }
        });

        trainsetsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        trainsetsList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                trainsetsListMousePressed(evt);
            }
        });

        jScrollPane3.setViewportView(trainsetsList);

        useButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/add.png")));
        useButton.setText("Zug \u00fcbernehmen");
        useButton.setEnabled(false);
        useButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                useButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(useButton, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useButton))
        );

        routeCB.setEnabled(false);
        routeCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                inputValueChanged(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Startroute");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Frequenz");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("vMax (km/h)");

        vmaxField.setEnabled(false);
        vmaxField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                inputValueChanged(evt);
            }
        });
        vmaxField.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                inputFocusLost(evt);
            }
        });

        trainsList.setBackground(javax.swing.UIManager.getDefaults().getColor("nb_workplace_fill"));
        trainsList.setCellRenderer(new genericPaintInterfaceComboBoxRenderer());
        trainsList.setEnabled(false);
        jScrollPane2.setViewportView(trainsList);

        nameField.setEnabled(false);
        nameField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                inputValueChanged(evt);
            }
        });
        nameField.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                inputFocusLost(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Name");

        vmaxText.setText("max 000");

        rotateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/rotate.png")));
        rotateButton.setText("zug umdrehen");
        rotateButton.setEnabled(false);
        rotateButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rotateButtonActionPerformed(evt);
            }
        });

        loadedCB.setText("beladen");
        loadedCB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        loadedCB.setEnabled(false);
        loadedCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        loadedCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                inputValueChanged(evt);
            }
        });

        frequencePane.setLayout(new java.awt.BorderLayout());

        restoreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/undo.png")));
        restoreButton.setToolTipText("alte Zeiten wieder herstellen");
        restoreButton.setEnabled(false);
        restoreButton.setFocusPainted(false);
        restoreButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        restoreButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                restoreButtonActionPerformed(evt);
            }
        });

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/trash.png")));
        clearButton.setToolTipText("alle Zeiten l\u00f6schen");
        clearButton.setEnabled(false);
        clearButton.setFocusPainted(false);
        clearButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        clearButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(restoreButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(frequencePane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(routeCB, javax.swing.GroupLayout.Alignment.TRAILING, 0, 235, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(vmaxField, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vmaxText)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                    .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotateButton)
                            .addComponent(loadedCB))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vmaxText)
                    .addComponent(jLabel1)
                    .addComponent(vmaxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clearButton)
                            .addComponent(restoreButton)))
                    .addComponent(frequencePane, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(routeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadedCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rotateButton))
        );

        startpointsTree.setCellRenderer(new treeCellRenderer());
        startpointsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
        {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt)
            {
                startpointsTreeValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(startpointsTree);

        copyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/clone.png")));
        copyButton.setText("Zug kopieren");
        copyButton.setEnabled(false);
        copyButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                copyButtonActionPerformed(evt);
            }
        });

        emitNowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/emitnow.png")));
        emitNowButton.setText("jetzt Zug starten");
        emitNowButton.setEnabled(false);
        emitNowButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                emitNowButtonActionPerformed(evt);
            }
        });

        delButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png")));
        delButton.setText("Zug l\u00f6schen");
        delButton.setEnabled(false);
        delButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                delButtonActionPerformed(evt);
            }
        });

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/new.png")));
        addButton.setText("neuer Zug");
        addButton.setEnabled(false);
        addButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
            .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
            .addComponent(delButton, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
            .addComponent(copyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
            .addComponent(emitNowButton, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(copyButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emitNowButton))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/ok.png")));
        closeButton.setText("Ok");
        closeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(closeButton)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(closeButton)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(31, 31, 31)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emitNowButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_emitNowButtonActionPerformed
    {//GEN-HEADEREND:event_emitNowButtonActionPerformed
		if (currentTrain != null && currentStart != null) {
			currentStart.addQueue(currentTrain);
		}
    }//GEN-LAST:event_emitNowButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_copyButtonActionPerformed
    {//GEN-HEADEREND:event_copyButtonActionPerformed
// TODO add your handling code here:
		JOptionPane.showMessageDialog(this, "Funktion fehlt noch.", "Fehler", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_copyButtonActionPerformed

    private void delButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_delButtonActionPerformed
    {//GEN-HEADEREND:event_delButtonActionPerformed
		if (currentTrain != null && currentStart != null) {
			currentStart.removeTrain(currentTrain);

			comparableDefaultMutableTreeNode obj = root.findObject(currentTrain);
			treeModel.removeNodeFromParent(obj);
			currentTrain = null;

			/*comparableDefaultMutableTreeNode p=root.findObject(currentStart);
			 TreeNode [] tn=p.getPath();
			 TreePath tp=new TreePath(tn);
			 startpointsTree.setSelectionPath(tp);
			 startpointsTree.makeVisible(tp);
			 startpointsTree.scrollPathToVisible(tp);*/
		}
    }//GEN-LAST:event_delButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearButtonActionPerformed
    {//GEN-HEADEREND:event_clearButtonActionPerformed
		minP.clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void restoreButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_restoreButtonActionPerformed
    {//GEN-HEADEREND:event_restoreButtonActionPerformed
		minP.restore();
    }//GEN-LAST:event_restoreButtonActionPerformed

    private void rotateButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rotateButtonActionPerformed
    {//GEN-HEADEREND:event_rotateButtonActionPerformed
		if (currentTrain != null) {
			currentTrain.rotate();
			updateStockView();
		}
    }//GEN-LAST:event_rotateButtonActionPerformed

    private void trainsetsListMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_trainsetsListMousePressed
    {//GEN-HEADEREND:event_trainsetsListMousePressed
		if (evt.getClickCount() == 2) {
			if (currentTrain != null) {
				trainset ts = (trainset) trainsetsList.getSelectedValue();
				if (ts != null) {
					currentTrain.setStocks(ts);
					currentTrain.setVMax(ts.getVmax());
					vmaxField.setValue(ts.getVmax());
					updateStockView();
				}
			}
		}
    }//GEN-LAST:event_trainsetsListMousePressed

    private void inputFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_inputFocusLost
    {//GEN-HEADEREND:event_inputFocusLost
		updateSelectedStart();
    }//GEN-LAST:event_inputFocusLost

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addButtonActionPerformed
    {//GEN-HEADEREND:event_addButtonActionPerformed
		updateSelectedStart();
		if (currentStart != null) {
			starttrain t = new starttrain("neu");
			currentStart.addTrain(t);

			comparableDefaultMutableTreeNode p = root.findObject(currentStart);
			currentTrain = null;
			comparableDefaultMutableTreeNode obj = new comparableDefaultMutableTreeNode(t);
			treeModel.insertNodeInto(obj, p, 0);

			TreeNode[] tn = obj.getPath();
			TreePath tp = new TreePath(tn);
			startpointsTree.setSelectionPath(tp);
			startpointsTree.makeVisible(tp);
			startpointsTree.scrollPathToVisible(tp);
		} else {
			System.out.println("no start!!");
		}
    }//GEN-LAST:event_addButtonActionPerformed

    private void useButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_useButtonActionPerformed
    {//GEN-HEADEREND:event_useButtonActionPerformed
		if (currentTrain != null) {
			trainset ts = (trainset) trainsetsList.getSelectedValue();
			if (ts != null) {
				currentTrain.setStocks(ts);
				currentTrain.setVMax(ts.getVmax());
				vmaxField.setValue(ts.getVmax());
				updateStockView();
			}
		}
    }//GEN-LAST:event_useButtonActionPerformed

    private void inputValueChanged(java.awt.event.ActionEvent evt)//GEN-FIRST:event_inputValueChanged
    {//GEN-HEADEREND:event_inputValueChanged
		if (!myClick) {
			updateSelectedStart();
		}
		if (currentTrain != null) {
			vmaxText.setText("max " + currentTrain.getMaxVMax());
		}
    }//GEN-LAST:event_inputValueChanged

    private void startpointsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_startpointsTreeValueChanged
    {//GEN-HEADEREND:event_startpointsTreeValueChanged
		TreePath t = startpointsTree.getSelectionPath();
		myClick = true;
		addButton.setEnabled(false);
		delButton.setEnabled(false);
		copyButton.setEnabled(false);
		emitNowButton.setEnabled(false);
		vmaxField.setEnabled(false);
		minP.setEnabled(false);
		routeCB.setEnabled(false);
		nameField.setEnabled(false);
		useButton.setEnabled(false);
		rotateButton.setEnabled(false);
		loadedCB.setEnabled(false);
		restoreButton.setEnabled(false);
		clearButton.setEnabled(false);
		((DefaultListModel) trainsList.getModel()).clear();
		if (t != null) {
			comparableDefaultMutableTreeNode cdmtn = (comparableDefaultMutableTreeNode) t.getLastPathComponent();
			Object o = cdmtn.getUserObject();
			if (o instanceof trainStartObject) {
				updateSelectedStart();

				addButton.setEnabled(true);
				currentStart = (trainStartObject) o;
				currentTrain = null;
				updateStockView();
			} else if (o instanceof starttrain) {
				updateSelectedStart();

				addButton.setEnabled(true);
				delButton.setEnabled(true);
				copyButton.setEnabled(true);
				emitNowButton.setEnabled(true);
				vmaxField.setEnabled(true);
				minP.setEnabled(true);
				routeCB.setEnabled(true);
				nameField.setEnabled(true);
				useButton.setEnabled(true);
				rotateButton.setEnabled(true);
				loadedCB.setEnabled(true);
				restoreButton.setEnabled(true);
				clearButton.setEnabled(true);

				currentTrain = (starttrain) o;

				comparableDefaultMutableTreeNode p = (comparableDefaultMutableTreeNode) cdmtn.getParent();
				currentStart = (trainStartObject) p.getUserObject();

				vmaxField.setValue(new Integer(currentTrain.getVMax()));
				minP.setValue(currentTrain.getFrequence());
				nameField.setText(currentTrain.getName());
				routeCB.setSelectedItem(currentTrain.getStartroute());
				loadedCB.setSelected(currentTrain.isTrainloaded());
				updateStockView();
			}
		}
		myClick = false;
    }//GEN-LAST:event_startpointsTreeValueChanged

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_closeButtonActionPerformed
    {//GEN-HEADEREND:event_closeButtonActionPerformed
		setVisible(false);
		formWindowClosing(null);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentShown
    {//GEN-HEADEREND:event_formComponentShown
		updateData();
		updateSetlist();
    }//GEN-LAST:event_formComponentShown

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
		if (testMode) {
			System.exit(0);
		}
    }//GEN-LAST:event_formWindowClosing

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				dataCollector.collector.setAllTrainData(new collection());
				dataCollector.collector.alltraindata.testIt();
				new timetableEditor(true).setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton copyButton;
    private javax.swing.JButton delButton;
    private javax.swing.JButton emitNowButton;
    private javax.swing.JPanel frequencePane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox loadedCB;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton restoreButton;
    private javax.swing.JButton rotateButton;
    private javax.swing.JComboBox routeCB;
    private javax.swing.JTree startpointsTree;
    private javax.swing.JList trainsList;
    private javax.swing.JList trainsetsList;
    private javax.swing.JButton useButton;
    private javax.swing.JFormattedTextField vmaxField;
    private javax.swing.JLabel vmaxText;
    // End of variables declaration//GEN-END:variables

	private int getVal(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof Long) {
			System.out.println("It happened again, Integer was Long!");
			return (int) (long) (Long) o;
		}
		if (o instanceof Integer) {
			return (int) (Integer) o;
		}
		System.out.println("It happened again, neither Integer nor Long!");
		return 0;
	}

	private void updateSelectedStart() {
		if (currentTrain != null) {
			int v = getVal(vmaxField.getValue());
			currentTrain.setVMax(v);
			currentTrain.setFrequence(minP.getValue());
			String n = nameField.getText();
			currentTrain.setName(n);
			route r = (route) routeCB.getSelectedItem();
			currentTrain.setStartroute(r);
			boolean l = loadedCB.isSelected();
			currentTrain.setTrainloaded(l);
			comparableDefaultMutableTreeNode cdmtn = root.findObject(currentTrain);
			if (cdmtn != null) {
				if (myClick) {
					treeModel.nodeChanged(cdmtn);
				} else {
					myClick = true;
					treeModel.nodeChanged(cdmtn);
					myClick = false;
				}
			}
		}
	}

	private void updateData() {
		root = new comparableDefaultMutableTreeNode("Startpunkte");

		if (treeModel == null) {
			treeModel = new DefaultTreeModel(root);
			startpointsTree.setModel(treeModel);
		}

		comparableDefaultMutableTreeNode parent = root;
		for (String r : trackObject.allregions) {
			comparableDefaultMutableTreeNode reg = new comparableDefaultMutableTreeNode(r);
			TreeSet<comparableDefaultMutableTreeNode> ts = new TreeSet<>();
			for (trackObject to : trackObject.allto.keySet()) {
				//System.out.println(to.getName()+":: tso: "+(to instanceof trainStartObject)+"//"+to.getGUIObjectName()+" r: "+(to.getRegion().compareTo(r))+" td: "+to.getTrackData());
				if (to instanceof trainStartObject && to.getRegion().compareTo(r) == 0 && to.getTrackData() != null) {
					comparableDefaultMutableTreeNode obj = new comparableDefaultMutableTreeNode(to);
					ts.add(obj);
					trainStartObject tso = (trainStartObject) to;
					for (starttrain st : tso.getTrains()) {
						comparableDefaultMutableTreeNode obj2 = new comparableDefaultMutableTreeNode(st);
						obj.add(obj2);
					}
				}
			}
			if (!ts.isEmpty()) {
				parent.add(reg);
				for (DefaultMutableTreeNode dmtn : ts) {
					reg.add(dmtn);
				}
			}
		}

		treeModel.setRoot(root);
	}

	private void updateSetlist() {
		DefaultListModel lm = (DefaultListModel) trainsetsList.getModel();
		lm.removeAllElements();
		for (trainset n : dataCollector.collector.getAllTrainData().trainsets.values()) {
			lm.addElement(n);
		}
	}

	private void updateStockView() {
		DefaultListModel lm = (DefaultListModel) trainsList.getModel();
		lm.removeAllElements();
		if (currentTrain != null) {
			for (stock s : currentTrain.getStocks()) {
				lm.addElement(s);
			}
			vmaxText.setText("max " + currentTrain.getMaxVMax());
		}
	}

	private void updateRoutes() {
		Object sel = routeCB.getSelectedItem();
		routeCB.removeAllItems();
		synchronized (route.allroutes) {
			for (route r : route.allroutes.values()) {
				routeCB.addItem(r);
			}
		}
		routeCB.setSelectedItem(sel);
	}
}
