/*
 * $Revision: 23 $
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

import javax.swing.JPanel;

import org.railsim.*;
import org.railsim.event.*;
import org.railsim.service.path;
import org.railsim.service.route;
import org.railsim.service.trainCommandExecutor_manual;
import org.railsim.service.trackObjects.pathableObject;

/**
 *
 * @author js
 */
public class manualControlInterface extends javax.swing.JDialog {

	static private manualControlInterface tEd = null;

	public static void openEditor(JPanel parent, fulltrain ft) {
		if (tEd == null || !tEd.isVisible()) {
			tEd = new manualControlInterface(javax.swing.SwingUtilities.getWindowAncestor(parent), ft);
			tEd.setVisible(true);
		} else if (tEd.isVisible()) {
		}
	}
	private fulltrain ft = null;
	private trainCommandExecutor_manual tce = null;
	private pathableObject po = null;
	private AbstractListener l_route = new AbstractListener() {
		@Override
		public void action(AbstractEvent e) {
			updateRoutes();
		}
	};
	private AbstractListener l_manual = new AbstractListener() {
		@Override
		public void action(AbstractEvent e) {
			trainAsking((ManualTrainEvent) e);
		}
	};

	/**
	 * Creates new form manualControlInterface
	 */
	public manualControlInterface(java.awt.Window parent, fulltrain _ft) {
		super(parent);
		ft = _ft;
		initComponents();

		//setSize(700,400);
		setLocationRelativeTo(parent);

		nameTF.setText(ft.getName());

		updateRoutes();
		nextRouteCB.setSelectedItem(ft.getMainExecutor().getRoute());
		dataCollector.collector.routeListeners.addListener(l_route);
		dataCollector.collector.manualTrainListeners.addListener(l_manual);
		dataCollector.collector.gamesetEventListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				setVisible(false);
				formWindowClosing(null);
			}
		});
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
        setBG = new javax.swing.ButtonGroup();
        nameTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        manualmodeTB = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        routeCB = new javax.swing.JComboBox();
        setButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        signalTF = new javax.swing.JTextField();
        showSignalButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        pathCB = new javax.swing.JComboBox();
        route_RB = new javax.swing.JRadioButton();
        path_RB = new javax.swing.JRadioButton();
        closeButton = new javax.swing.JButton();
        nextRouteCB = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        showTrainButton = new javax.swing.JButton();

        setTitle("Manuelle Zugsteuerung");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        nameTF.setEditable(false);

        jLabel1.setText("Zug");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Manuelle Zugsteuerung"));
        manualmodeTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/disable path.png")));
        manualmodeTB.setText("manuelle Steuerung");
        manualmodeTB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                manualmodeTBActionPerformed(evt);
            }
        });

        jLabel2.setText("Welche Route soll beim n\u00e4chsten Signal genommen werden?");

        setButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/pathroutes.png")));
        setButton.setText("setzen");
        setButton.setEnabled(false);
        setButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                setButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Signal");

        signalTF.setEditable(false);

        showSignalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/show.png")));
        showSignalButton.setToolTipText("Signal zeigen");
        showSignalButton.setEnabled(false);
        showSignalButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        showSignalButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                showSignalButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("<html>\nDie manuelle Steuerung muss aktiviert sein um einen Zug steuern zu k\u00f6nnen.\nEs kann nur ein Zug zeitgleich manuell gesteuert werden.\n</html>");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        messageLabel.setText("<html><br><br><br><br><br></html>");

        pathCB.setEnabled(false);

        setBG.add(route_RB);
        route_RB.setSelected(true);
        route_RB.setText("Route");
        route_RB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        route_RB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        route_RB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                route_RBActionPerformed(evt);
            }
        });

        setBG.add(path_RB);
        path_RB.setText("Fahrstra\u00dfe");
        path_RB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        path_RB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        path_RB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                path_RBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(manualmodeTB)
                .addGap(7, 7, 7)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(route_RB)
                            .addComponent(path_RB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(signalTF, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showSignalButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pathCB, 0, 344, Short.MAX_VALUE))
                                    .addComponent(routeCB, 0, 344, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(setButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(manualmodeTB)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(signalTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showSignalButton))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(routeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(route_RB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(path_RB)
                    .addComponent(pathCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setButton))
                .addGap(11, 11, 11)
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE))
        );

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/ok.png")));
        closeButton.setText("schlie\u00dfen");
        closeButton.setMargin(new java.awt.Insets(2, 7, 2, 7));
        closeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                closeButtonActionPerformed(evt);
            }
        });

        nextRouteCB.setEnabled(false);

        jLabel6.setText("Route nach Verlassen der manuellen Steuerung");

        showTrainButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/show.png")));
        showTrainButton.setToolTipText("Zug zeigen");
        showTrainButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        showTrainButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                showTrainButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTF, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showTrainButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextRouteCB, 0, 182, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(showTrainButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(jLabel6)
                    .addComponent(nextRouteCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void path_RBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_path_RBActionPerformed
    {//GEN-HEADEREND:event_path_RBActionPerformed
		routeCB.setEnabled(false);
		pathCB.setEnabled(true);
    }//GEN-LAST:event_path_RBActionPerformed

    private void route_RBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_route_RBActionPerformed
    {//GEN-HEADEREND:event_route_RBActionPerformed
		routeCB.setEnabled(true);
		pathCB.setEnabled(false);
    }//GEN-LAST:event_route_RBActionPerformed

    private void showTrainButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showTrainButtonActionPerformed
    {//GEN-HEADEREND:event_showTrainButtonActionPerformed
		//dataCollector.collector.thegame.runAction(new EditorActionEvent<fulltrain>("showTrain",ft));
		dataCollector.collector.thepainter.showTrain(ft);
    }//GEN-LAST:event_showTrainButtonActionPerformed

    private void showSignalButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showSignalButtonActionPerformed
    {//GEN-HEADEREND:event_showSignalButtonActionPerformed
		//dataCollector.collector.thegame.runAction(new EditorActionEvent<pathableObject>("showSignal",po));
		dataCollector.collector.thepainter.showTrackObject(po);
    }//GEN-LAST:event_showSignalButtonActionPerformed

    private void setButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_setButtonActionPerformed
    {//GEN-HEADEREND:event_setButtonActionPerformed
		if (tce != null) {
			tce.setManualRoute((route) routeCB.getSelectedItem());
			signalTF.setText("");
			showSignalButton.setEnabled(false);
			message(null);
			po = null;
			updatePaths();
		}
		setButton.setEnabled(false);
    }//GEN-LAST:event_setButtonActionPerformed

    private void manualmodeTBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_manualmodeTBActionPerformed
    {//GEN-HEADEREND:event_manualmodeTBActionPerformed
		if (manualmodeTB.isSelected()) {
			nextRouteCB.setSelectedItem(ft.getMainExecutor().getRoute());
			nextRouteCB.setEnabled(true);
			ft.setManuelMode(manualmodeTB.isSelected());
		} else {
			unmanualTrain();
		}
    }//GEN-LAST:event_manualmodeTBActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_closeButtonActionPerformed
    {//GEN-HEADEREND:event_closeButtonActionPerformed
		setVisible(false);
		formWindowClosing(null);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
		unmanualTrain();
		dataCollector.collector.routeListeners.removeListener(l_manual);
		dataCollector.collector.routeListeners.removeListener(l_route);
    }//GEN-LAST:event_formWindowClosing

	private void updateRoutes() {
		Object s1, s2;

		s1 = routeCB.getSelectedItem();
		s2 = nextRouteCB.getSelectedItem();

		routeCB.removeAllItems();
		nextRouteCB.removeAllItems();
		synchronized (route.allroutes) {
			for (route r : route.allroutes.values()) {
				routeCB.addItem(r);
				nextRouteCB.addItem(r);
			}
		}

		routeCB.setSelectedItem(s1);
		if (routeCB.getSelectedIndex() < 0) {
			routeCB.setSelectedIndex(0);
		}
		nextRouteCB.setSelectedItem(s2);
		if (nextRouteCB.getSelectedIndex() < 0) {
			nextRouteCB.setSelectedIndex(0);
		}
	}

	private void updatePaths() {
		pathCB.removeAllItems();
		if (po != null) {
			for (path p : po.getAllPaths()) {
				pathCB.addItem(p);
			}
			if (!po.getAllPaths().isEmpty()) {
				pathCB.setSelectedIndex(0);
			}
		}
	}

	private void trainAsking(ManualTrainEvent e) {
		if (!e.isRouteMsg()) {
			if (e.getTrain() == ft) {
				tce = e.getTCE();
				po = e.getSignal();
				message("Es kam eine manuelle Steuerungsaufforderung vom Zug <b>" + e.getTrain().getName() + "</b> bitte Route auswählen.");

				updatePaths();
				signalTF.setText(e.getSignal().getRegion() + " / " + e.getSignal().getName());
				showSignalButton.setEnabled(true);
				setButton.setEnabled(true);
				audio.manualTrain();
			} else {
				message("Es kam eine manuelle Steuerungsaufforderung vom Zug <b>" + e.getTrain().getName() + "</b>! Dieser Zug ist jedoch nicht im manuellen Modus!");
			}
		}
	}

	private void unmanualTrain() {
		if (ft.isManualMode()) {
			route r = (route) nextRouteCB.getSelectedItem();
			ft.setManuelMode(false);
			ft.setRoute(r);
			signalTF.setText("");
			showSignalButton.setEnabled(false);
			setButton.setEnabled(false);
			nextRouteCB.setEnabled(false);
			message(null);
		}
	}

	private void message(String t) {
		if (t != null) {
			messageLabel.setText("<html><i>" + dataCollector.collector.formatDate() + "</i>: " + t + "</html>");
		} else {
			messageLabel.setText("");
		}
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton manualmodeTB;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JComboBox nextRouteCB;
    private javax.swing.JComboBox pathCB;
    private javax.swing.JRadioButton path_RB;
    private javax.swing.JComboBox routeCB;
    private javax.swing.JRadioButton route_RB;
    private javax.swing.ButtonGroup setBG;
    private javax.swing.JButton setButton;
    private javax.swing.JButton showSignalButton;
    private javax.swing.JButton showTrainButton;
    private javax.swing.JTextField signalTF;
    // End of variables declaration//GEN-END:variables
}
