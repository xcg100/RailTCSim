/*
 * $Revision: 18 $
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

import org.railsim.service.statics;

/**
 *
 * @author js
 */
public class hidepanel_options extends javax.swing.JPanel {

	/**
	 * Creates new form hidepanel_file
	 */
	public hidepanel_options() {
		initComponents();
		String[] availablePlafs = statics.getLookAndFeels();
		for (int i = 0; i < availablePlafs.length; ++i) {
			plaf.addItem(availablePlafs[i]);
		}
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
        plaf = new javax.swing.JComboBox();
        plafbutton = new javax.swing.JButton();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Look & Feel");
        add(jLabel1);

        add(plaf);

        plafbutton.setText("change Look & Feel");
        plafbutton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                plafbuttonActionPerformed(evt);
            }
        });

        add(plafbutton);

    }// </editor-fold>//GEN-END:initComponents

    private void plafbuttonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_plafbuttonActionPerformed
    {//GEN-HEADEREND:event_plafbuttonActionPerformed
		if (plaf.getSelectedItem() != null) {
			statics.setLookAndFeel((String) plaf.getSelectedItem(), this);
		}
    }//GEN-LAST:event_plafbuttonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox plaf;
    private javax.swing.JButton plafbutton;
    // End of variables declaration//GEN-END:variables
}