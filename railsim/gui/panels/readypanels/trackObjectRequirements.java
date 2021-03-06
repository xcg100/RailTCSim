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
package org.railsim.gui.panels.readypanels;

import org.railsim.service.trackObjects.trackObject;

/**
 *
 * @author js
 */
public class trackObjectRequirements extends javax.swing.JPanel {

	/**
	 * Creates new form trackObjectRequirements
	 */
	public trackObjectRequirements(String t) {
		initComponents();
		// Bau nicht auf Gleis mit
		setBorderTitle(t);
	}

	public trackObjectRequirements() {
		this("setze nicht auf Gleis mit");
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
        l_NOTRAIN = new javax.swing.JLabel();
        l_NOBUILD = new javax.swing.JLabel();
        l_NOPATHSET = new javax.swing.JLabel();
        l_NOPATH = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("."));
        l_NOTRAIN.setFont(l_NOTRAIN.getFont().deriveFont(l_NOTRAIN.getFont().getSize()-2f));
        l_NOTRAIN.setText("Zug darauf");

        l_NOBUILD.setFont(l_NOBUILD.getFont().deriveFont(l_NOBUILD.getFont().getSize()-2f));
        l_NOBUILD.setText("im Bau befindlichend");

        l_NOPATHSET.setFont(l_NOPATHSET.getFont().deriveFont(l_NOPATHSET.getFont().getSize()-2f));
        l_NOPATHSET.setText("gesetzter Fahrstra\u00dfe");

        l_NOPATH.setFont(l_NOPATH.getFont().deriveFont(l_NOPATH.getFont().getSize()-2f));
        l_NOPATH.setText("definierter Fahrstra\u00dfe");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_NOPATH)
                    .addComponent(l_NOTRAIN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_NOPATHSET)
                    .addComponent(l_NOBUILD)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_NOTRAIN)
                    .addComponent(l_NOPATHSET))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_NOPATH)
                    .addComponent(l_NOBUILD)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel l_NOBUILD;
    private javax.swing.JLabel l_NOPATH;
    private javax.swing.JLabel l_NOPATHSET;
    private javax.swing.JLabel l_NOTRAIN;
    // End of variables declaration//GEN-END:variables

	public void setBorderTitle(String t) {
		setBorder(javax.swing.BorderFactory.createTitledBorder(t));
	}

	public void setRequirements(trackObject to) {
		/*
		 public static final int REQUIREMENT_NOTRAIN=0x01;
		 public static final int REQUIREMENT_NOPATH=0x02;
		 public static final int REQUIREMENT_NOBUILD=0x04;
		 public static final int REQUIREMENT_NOSETPATH=0x08;
		 */
		if (to == null) {
			l_NOPATH.setEnabled(false);
			l_NOTRAIN.setEnabled(false);
			l_NOBUILD.setEnabled(false);
			l_NOPATHSET.setEnabled(false);
		} else {
			l_NOPATH.setEnabled((to.getRequirements() & trackObject.REQUIREMENT_NOPATH) != 0);
			l_NOTRAIN.setEnabled((to.getRequirements() & trackObject.REQUIREMENT_NOTRAIN) != 0);
			l_NOBUILD.setEnabled((to.getRequirements() & trackObject.REQUIREMENT_NOBUILD) != 0);
			l_NOPATHSET.setEnabled((to.getRequirements() & trackObject.REQUIREMENT_NOSETPATH) != 0);
		}
	}
}
