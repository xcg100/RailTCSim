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

import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.railsim.*;
import org.railsim.editor.EditorEvent;
import org.railsim.event.AbstractEvent;
import org.railsim.event.AbstractListener;
import org.railsim.gui.dnd.*;
import org.railsim.gui.renderer.genericPaintInterfaceComboBoxRenderer;
import org.railsim.gui.renderer.stocktreeCellRenderer;
import org.railsim.service.statics;

/**
 *
 * @author js
 */
public class trainsetEditor extends javax.swing.JDialog {

	static private trainsetEditor tEd = null;

	public static void openEditor(JPanel parent) {
		if (tEd == null) {
			tEd = new trainsetEditor(javax.swing.SwingUtilities.getWindowAncestor(parent));
		}
		tEd.setVisible(true);
	}
	private boolean testMode = false;
	private DefaultTreeModel treeModel = null;
	private trainset currentTrainset = null;
	;
    private boolean updatingSetlist = false;

	/**
	 * Creates new form trainsetEditor
	 */
	public trainsetEditor(java.awt.Window parent) {
		super(parent);

		initComponents();
		setSize(700, 400);
		setLocationRelativeTo(parent);

		setList.setModel(new DefaultListModel());
		DefaultListModel dlm = new DefaultListModel();
		dlm.addListDataListener(new ListDataListener() {
			@Override
			public void intervalAdded(ListDataEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						readStock();
					}
				});
			}

			@Override
			public void intervalRemoved(ListDataEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						readStock();
					}
				});
			}

			@Override
			public void contentsChanged(ListDataEvent e) {
			}
		});
		setStockList.setModel(dlm);

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
							/*
							 List files = (List) tr.getTransferData(flavors[i]);
							 // Wir setzen in das Label den Namen der ersten
							 // Datei
							 //delCmdButton.doClick();
							 for(Object o:files)
							 {
							 if (o instanceof rollingstock)
							 {
							 // TODO
							 }
							 } */
							e.dropComplete(true);
							updateSpeed();
							return;
						}
					}
				} catch (Throwable t) {
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
		DropTarget dropTarget = new DropTarget(delStockButton, dropTargetListener);

		stocksTree.setTransferHandler(new rollingstockDND());
		setStockList.setTransferHandler(new rollingstockDND(setStockList));

		updateSetlist();
		updateData();
		dataCollector.collector.gamesetEventListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				setVisible(false);
				formWindowClosing(null);
			}
		});
	}

	public trainsetEditor(boolean m) {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        setList = new javax.swing.JList();
        addSetButton = new javax.swing.JButton();
        delSetButton = new javax.swing.JButton();
        setNameField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        setStockList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        stocksTree = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        delStockButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        copySetButton = new javax.swing.JButton();
        vmaxField = new javax.swing.JTextField();
        accelField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        changeDirectionButton = new javax.swing.JButton();

        setTitle("Zugeditor");
        setAlwaysOnTop(true);
        setIconImage(statics.loadGUIImage("trainseteditor.png"));
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

        setList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        setList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                setListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(setList);

        addSetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/add.png"))); // NOI18N
        addSetButton.setText("neuen Zug");
        addSetButton.setMargin(new java.awt.Insets(2, 8, 2, 8));
        addSetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSetButtonActionPerformed(evt);
            }
        });

        delSetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png"))); // NOI18N
        delSetButton.setText("Zug löschen");
        delSetButton.setEnabled(false);
        delSetButton.setMargin(new java.awt.Insets(2, 8, 2, 8));
        delSetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delSetButtonActionPerformed(evt);
            }
        });

        setNameField.setEnabled(false);
        setNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setNameFieldActionPerformed(evt);
            }
        });
        setNameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                setNameFieldFocusLost(evt);
            }
        });

        setStockList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setStockList.setCellRenderer(new genericPaintInterfaceComboBoxRenderer());
        setStockList.setDragEnabled(true);
        setStockList.setDropMode(javax.swing.DropMode.INSERT);
        setStockList.setEnabled(false);
        jScrollPane2.setViewportView(setStockList);

        stocksTree.setCellRenderer(new stocktreeCellRenderer());
        stocksTree.setDragEnabled(true);
        stocksTree.setEnabled(false);
        stocksTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                stocksTreeMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(stocksTree);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        delStockButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/delete.png"))); // NOI18N
        delStockButton.setToolTipText("Wagen löschen");
        delStockButton.setEnabled(false);
        delStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delStockButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("<html>Rollmaterial aus der Baumansicht in die linke Liste schieben.</html>");

        copySetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/clone.png"))); // NOI18N
        copySetButton.setText("kopiere Zug");
        copySetButton.setEnabled(false);
        copySetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copySetButtonActionPerformed(evt);
            }
        });

        vmaxField.setEditable(false);

        accelField.setEditable(false);

        jLabel2.setText("m/a²");

        jLabel3.setText("km/h");

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/ok.png"))); // NOI18N
        closeButton.setText("Ok");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        changeDirectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/rotate.png"))); // NOI18N
        changeDirectionButton.setToolTipText("Wagen umdrehen");
        changeDirectionButton.setEnabled(false);
        changeDirectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeDirectionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(delStockButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changeDirectionButton))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, 0, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton)
                        .addGap(71, 71, 71)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delSetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copySetButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addSetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(setNameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(accelField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vmaxField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(setNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addSetButton)
                            .addComponent(vmaxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(copySetButton)
                            .addComponent(accelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delSetButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeButton)
                        .addComponent(delStockButton))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeDirectionButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void copySetButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_copySetButtonActionPerformed
    {//GEN-HEADEREND:event_copySetButtonActionPerformed
// TODO add your handling code here:
		JOptionPane.showMessageDialog(this, "Funktion fehlt noch.", "Fehler", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_copySetButtonActionPerformed

    private void changeDirectionButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_changeDirectionButtonActionPerformed
    {//GEN-HEADEREND:event_changeDirectionButtonActionPerformed
		DefaultListModel lm = (DefaultListModel) setStockList.getModel();

		Object[] s = setStockList.getSelectedValues();
		for (int i = 0; i < s.length; ++i) {
			((stock) s[i]).rotate();
		}
		setStockList.repaint();
    }//GEN-LAST:event_changeDirectionButtonActionPerformed

    private void stocksTreeMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_stocksTreeMousePressed
    {//GEN-HEADEREND:event_stocksTreeMousePressed
		if (evt.getClickCount() % 2 == 0) {
			TreePath t = stocksTree.getSelectionPath();
			if (t != null) {
				DefaultMutableTreeNode cdmtn = (DefaultMutableTreeNode) t.getLastPathComponent();
				Object o = cdmtn.getUserObject();
				if (o instanceof rollingstock) {
					((DefaultListModel) setStockList.getModel()).addElement(new stock((rollingstock) o, true));
				}
			}
		}
    }//GEN-LAST:event_stocksTreeMousePressed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_closeButtonActionPerformed
    {//GEN-HEADEREND:event_closeButtonActionPerformed
		setVisible(false);
		formWindowClosing(null);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void delStockButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_delStockButtonActionPerformed
    {//GEN-HEADEREND:event_delStockButtonActionPerformed
		DefaultListModel lm = (DefaultListModel) setStockList.getModel();

		Object[] s = setStockList.getSelectedValues();
		for (int i = 0; i < s.length; ++i) {
			lm.removeElement(s[i]);
		}
    }//GEN-LAST:event_delStockButtonActionPerformed

    private void setNameFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_setNameFieldActionPerformed
    {//GEN-HEADEREND:event_setNameFieldActionPerformed
		String n = setNameField.getText();
		if (currentTrainset != null && n.compareTo(currentTrainset.getName()) != 0) {
			if (dataCollector.collector.getAllTrainData().trainsets.containsKey(n)) {
				setNameField.setText(currentTrainset.getName());
				JOptionPane.showMessageDialog(this, "Zugaufbau mit dem Namen " + n + "\nexistiert bereits!", "Zug existiert bereits!", JOptionPane.ERROR_MESSAGE);
			} else {
				dataCollector.collector.getAllTrainData().trainsets.remove(currentTrainset.getName());
				currentTrainset.setName(n);
				dataCollector.collector.getAllTrainData().trainsets.put(n, currentTrainset);
				updateSetlist();
				dataCollector.collector.editorEvent(EditorEvent.TRAINSET_CHANGED);
			}
		}
    }//GEN-LAST:event_setNameFieldActionPerformed

    private void setNameFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_setNameFieldFocusLost
    {//GEN-HEADEREND:event_setNameFieldFocusLost
		setNameFieldActionPerformed(null);
    }//GEN-LAST:event_setNameFieldFocusLost

    private void delSetButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_delSetButtonActionPerformed
    {//GEN-HEADEREND:event_delSetButtonActionPerformed
		dataCollector.collector.getAllTrainData().trainsets.remove(currentTrainset.getName());
		currentTrainset = null;
		updateSetlist();
		dataCollector.collector.editorEvent(EditorEvent.TRAINSET_CHANGED);
    }//GEN-LAST:event_delSetButtonActionPerformed

    private void setListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_setListValueChanged
    {//GEN-HEADEREND:event_setListValueChanged
		if (updatingSetlist || evt.getValueIsAdjusting()) {
			return;
		}

		readStock();

		if (setList.getSelectedValue() == null) {
			currentTrainset = null;
		} else {
			currentTrainset = dataCollector.collector.getAllTrainData().trainsets.get((String) setList.getSelectedValue());
		}
		if (currentTrainset == null) {
			delSetButton.setEnabled(false);
			copySetButton.setEnabled(false);
			setNameField.setEnabled(false);
			delStockButton.setEnabled(false);
			changeDirectionButton.setEnabled(false);
			setStockList.setEnabled(false);
			stocksTree.setEnabled(false);
		} else {
			delSetButton.setEnabled(true);
			copySetButton.setEnabled(true);
			setNameField.setEnabled(true);
			setNameField.setText(currentTrainset.getName());
			delStockButton.setEnabled(true);
			changeDirectionButton.setEnabled(true);
			setStockList.setEnabled(true);
			stocksTree.setEnabled(true);
			updateStock();
		}
    }//GEN-LAST:event_setListValueChanged

    private void addSetButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addSetButtonActionPerformed
    {//GEN-HEADEREND:event_addSetButtonActionPerformed

		if (!dataCollector.collector.getAllTrainData().trainsets.containsKey("neu")) {
			readStock();
			trainset t = new trainset("neu");
			dataCollector.collector.getAllTrainData().trainsets.put("neu", t);
			currentTrainset = t;
			updateStock();
			updateSetlist();
			dataCollector.collector.editorEvent(EditorEvent.TRAINSET_CHANGED);
		}
    }//GEN-LAST:event_addSetButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentShown
    {//GEN-HEADEREND:event_formComponentShown
		//updateData();
    }//GEN-LAST:event_formComponentShown

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
		readStock();

		if (testMode) {
			System.exit(0);
		} else {
			dataCollector.collector.getAllTrainData().save();
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
				new trainsetEditor(true).setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accelField;
    private javax.swing.JButton addSetButton;
    private javax.swing.JButton changeDirectionButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton copySetButton;
    private javax.swing.JButton delSetButton;
    private javax.swing.JButton delStockButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JList setList;
    private javax.swing.JTextField setNameField;
    private javax.swing.JList setStockList;
    private javax.swing.JTree stocksTree;
    private javax.swing.JTextField vmaxField;
    // End of variables declaration//GEN-END:variables

	private void updateData() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Rollmaterial");

		if (treeModel == null) {
			treeModel = new DefaultTreeModel(root);
			stocksTree.setModel(treeModel);
		}

		HashMap<String, HashSet<rollingstock>> allsorted = new HashMap<>();
		HashSet<rollingstock> sorted = new HashSet<>();

		for (rollingstock rs : dataCollector.collector.getAllTrainData().stocks.values()) {
			sorted.add(rs);
			for (String t : rs.getTypes()) {
				HashSet<rollingstock> th;
				th = allsorted.get(t);
				if (th == null) {
					th = new HashSet<>();
					allsorted.put(t, th);
				}
				th.add(rs);
			}
		}

		DefaultMutableTreeNode list1 = new DefaultMutableTreeNode("alphabetisch");
		root.add(list1);
		for (rollingstock rs : sorted) {
			list1.add(new DefaultMutableTreeNode(rs));
		}
		list1 = new DefaultMutableTreeNode("mit Antrieb");
		root.add(list1);
		for (rollingstock rs : sorted) {
			if (rs.getEngine() != null) {
				list1.add(new DefaultMutableTreeNode(rs));
			}
		}
		list1 = new DefaultMutableTreeNode("ohne Antrieb");
		root.add(list1);
		for (rollingstock rs : sorted) {
			if (rs.getEngine() == null) {
				list1.add(new DefaultMutableTreeNode(rs));
			}
		}

		list1 = new DefaultMutableTreeNode("nach Typ");
		root.add(list1);
		for (String t : allsorted.keySet()) {
			DefaultMutableTreeNode list2 = new DefaultMutableTreeNode(t);
			list1.add(list2);
			DefaultMutableTreeNode welist = new DefaultMutableTreeNode("mit Antrieb");
			DefaultMutableTreeNode woelist = new DefaultMutableTreeNode("ohne Antrieb");

			list2.add(welist);
			list2.add(woelist);
			for (rollingstock rs : allsorted.get(t)) {
				if (rs.getEngine() == null) {
					woelist.add(new DefaultMutableTreeNode(rs));
				} else {
					welist.add(new DefaultMutableTreeNode(rs));
				}
			}
		}
		treeModel.setRoot(root);
	}

	private void updateSetlist() {
		if (currentTrainset != null) {
			updatingSetlist = true;
		}
		DefaultListModel lm = (DefaultListModel) setList.getModel();
		lm.removeAllElements();
		for (String n : dataCollector.collector.getAllTrainData().trainsets.keySet()) {
			lm.addElement(n);
		}
		updatingSetlist = false;
		if (currentTrainset != null) {
			setList.setSelectedValue(currentTrainset.getName(), true);
		}
	}

	private void readStock() {
		if (currentTrainset != null) {
			DefaultListModel lm = (DefaultListModel) setStockList.getModel();
			currentTrainset.clearStocks();
			Enumeration<stock> s = (Enumeration<stock>) lm.elements();
			while (s.hasMoreElements()) {
				stock t = s.nextElement();
				currentTrainset.addStock(t);
			}
			updateSpeed();
		}
	}

	private void updateStock() {
		DefaultListModel lm = (DefaultListModel) setStockList.getModel();
		lm.removeAllElements();
		if (currentTrainset != null) {
			for (stock n : currentTrainset.getStocks()) {
				lm.addElement(n);
			}
			updateSpeed();
		}
	}

	private void updateSpeed() {
		if (currentTrainset == null) {
			vmaxField.setText("");
			accelField.setText("");
		} else {
			vmaxField.setText(currentTrainset.getVmax() + "");
			accelField.setText(currentTrainset.getAccel() + "");
		}
	}
}
