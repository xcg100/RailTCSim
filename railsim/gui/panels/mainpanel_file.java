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

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.railsim.*;
import org.railsim.event.*;

/**
 *
 * @author js
 */
public class mainpanel_file extends javax.swing.JPanel {

	/**
	 * Creates new form mainpanel_file
	 */
	public mainpanel_file() {
		initComponents();
		dataCollector.collector.filenameListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				save_Button.setEnabled(dataCollector.collector.getFilename().length() > 0);
			}
		});
		KeyStroke ks = KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK);
		new_Button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks,
				"doKey");
		new_Button.getActionMap().put("doKey",
				new AbstractAction() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						new_ButtonActionPerformed(evt);
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
        new_Button = new javax.swing.JButton();
        load_Button = new javax.swing.JButton();
        save_Button = new javax.swing.JButton();
        saveas_Button = new javax.swing.JButton();
        exit_Button = new javax.swing.JButton();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        new_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/new.png")));
        new_Button.setText("<html>New...<b><i>Ctrl+N</i></b></html>");
        new_Button.setToolTipText("Ctrl+N");
        new_Button.setActionCommand("New...");
        new_Button.setFocusPainted(false);
        new_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        new_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                new_ButtonActionPerformed(evt);
            }
        });

        add(new_Button);

        load_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/load.png")));
        load_Button.setText("Load...");
        load_Button.setActionCommand("Load...");
        load_Button.setFocusPainted(false);
        load_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        load_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                load_ButtonActionPerformed(evt);
            }
        });

        add(load_Button);

        save_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/save.png")));
        save_Button.setText("Save");
        save_Button.setToolTipText("Ctrl+S");
        save_Button.setActionCommand("Save");
        save_Button.setEnabled(false);
        save_Button.setFocusPainted(false);
        save_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        save_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                save_ButtonActionPerformed(evt);
            }
        });

        add(save_Button);

        saveas_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/saveas.png")));
        saveas_Button.setText("Save as...");
        saveas_Button.setActionCommand("Save as...");
        saveas_Button.setFocusPainted(false);
        saveas_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        saveas_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveas_ButtonActionPerformed(evt);
            }
        });

        add(saveas_Button);

        exit_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/exit.png")));
        exit_Button.setText("Exit...");
        exit_Button.setActionCommand("Exit...");
        exit_Button.setFocusPainted(false);
        exit_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        exit_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exit_ButtonActionPerformed(evt);
            }
        });

        add(exit_Button);

    }// </editor-fold>//GEN-END:initComponents

    private void saveas_ButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveas_ButtonActionPerformed
    {//GEN-HEADEREND:event_saveas_ButtonActionPerformed
		dataCollector.collector.saveas();
    }//GEN-LAST:event_saveas_ButtonActionPerformed

    private void save_ButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_save_ButtonActionPerformed
    {//GEN-HEADEREND:event_save_ButtonActionPerformed
		dataCollector.collector.save();
    }//GEN-LAST:event_save_ButtonActionPerformed

    private void load_ButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_load_ButtonActionPerformed
    {//GEN-HEADEREND:event_load_ButtonActionPerformed
		dataCollector.collector.load();
    }//GEN-LAST:event_load_ButtonActionPerformed

    private void new_ButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_new_ButtonActionPerformed
    {//GEN-HEADEREND:event_new_ButtonActionPerformed
		dataCollector.collector.createnew();
    }//GEN-LAST:event_new_ButtonActionPerformed

    private void exit_ButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exit_ButtonActionPerformed
    {//GEN-HEADEREND:event_exit_ButtonActionPerformed
		dataCollector.collector.quitProgram();
    }//GEN-LAST:event_exit_ButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exit_Button;
    private javax.swing.JButton load_Button;
    private javax.swing.JButton new_Button;
    private javax.swing.JButton save_Button;
    private javax.swing.JButton saveas_Button;
    // End of variables declaration//GEN-END:variables
}