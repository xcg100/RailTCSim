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

import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.railsim.*;
import org.railsim.editor.EditorActionEvent;
import org.railsim.editor.EditorEvent;
import org.railsim.event.*;
import org.railsim.service.track;

/**
 *
 * @author js
 */
public class hidepanel_edittrackgroup extends javax.swing.JPanel {

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
	HashMap<String, ArrayList<String>> ttypes = new HashMap<>();
	HashMap<String, JComboBox> ctypes = new HashMap<>();
	track workingTrack = null;
	boolean CBeventDisable = false;

	/**
	 * Creates new form mainpanel_file
	 */
	public hidepanel_edittrackgroup() {
		initComponents();

		dataCollector.collector.editorEventListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				trackEvent((EditorEvent) e);
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
        jLabel1 = new javax.swing.JLabel();
        module_cb = new javax.swing.JComboBox();
        add_Button = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        savemodule_Button = new javax.swing.JButton();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/module.png")));
        jLabel1.setText("Module");
        add(jLabel1);

        add(module_cb);

        add_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/add.png")));
        add_Button.setText("New Track");
        add_Button.setActionCommand("new track");
        add_Button.setEnabled(false);
        add_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        add_Button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                add_ButtonButtonActionPerformed(evt);
            }
        });

        add(add_Button);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(5, 32767));
        jSeparator1.setMinimumSize(new java.awt.Dimension(5, 25));
        jSeparator1.setPreferredSize(new java.awt.Dimension(5, 25));
        add(jSeparator1);

        savemodule_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/railsim/gui/resources/ok.png")));
        savemodule_Button.setText("Save Module...");
        savemodule_Button.setEnabled(false);
        savemodule_Button.setMargin(new java.awt.Insets(2, 5, 2, 5));
        add(savemodule_Button);

    }// </editor-fold>//GEN-END:initComponents

    private void add_ButtonButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_add_ButtonButtonActionPerformed
    {//GEN-HEADEREND:event_add_ButtonButtonActionPerformed
		dataCollector.collector.thegame.runAction(new EditorActionEvent(evt.getActionCommand()));
    }//GEN-LAST:event_add_ButtonButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_Button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox module_cb;
    private javax.swing.JButton savemodule_Button;
    // End of variables declaration//GEN-END:variables

	void configureCombo(String type, String[] items) {
		JComboBox cb = ctypes.get(type);
		cb.removeAllItems();
		ArrayList<String> al = new ArrayList<>();
		int i = 0;
		while (i < items.length) {
			String s = items[i];
			if ((i & 1) == 0) {
				cb.addItem(s);
			} else {
				al.add(s);
			}
			++i;
		}
		ttypes.put(type, al);
	}

	void trackEvent(EditorEvent e) {
		switch (e.getType()) {
			case EditorEvent.TRACK_SELECTED:
				break;
		}
	}
}
