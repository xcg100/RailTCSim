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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.railsim.*;
import org.railsim.service.track;

/**
 *
 * @author js
 */
public class mapPanel extends javax.swing.JPanel implements MouseListener {

	private static final int SCALE = 4;
	private int x = 0;
	private int y = 0;
	private int width = 1;
	private int height = 1;
	/**
	 * Creates new form mapPanel
	 */
	private static final double SCALEMUL = 1.0 / SCALE;

	public mapPanel() {
		initComponents();
		setSize(100, 100);
		addMouseListener(this);
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

        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentHidden(java.awt.event.ComponentEvent evt)
            {
                formComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                formComponentShown(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentHidden(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentHidden
    {//GEN-HEADEREND:event_formComponentHidden
// TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void formComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentShown
    {//GEN-HEADEREND:event_formComponentShown
// TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create(0, 0, width, height);
		g2.translate(-x, -y);
		g2.setBackground(Color.BLACK);
		g2.clearRect(x, y, width, height);
		g2.setColor(Color.GREEN);
		g2.scale(SCALEMUL, SCALEMUL);
		dataCollector.collector.thepainter.tracks.readLock();
		for (track t : dataCollector.collector.thepainter.tracks) {
			t.paintMini(g2);
		}
		dataCollector.collector.thepainter.tracks.readUnlock();
		g2.setColor(Color.RED);
		g2.drawRect(dataCollector.collector.thepainter.getX() / SCALE, dataCollector.collector.thepainter.getX() / SCALE,
				dataCollector.collector.thepainter.getImgWidth(), dataCollector.collector.thepainter.getImgHeight());
	}

	void setDimensions(int minx, int miny, int maxx, int maxy) {
		x = minx / SCALE;
		y = miny / SCALE;
		int w = (maxx - minx) / SCALE;
		int h = (maxy - miny) / SCALE;
		if (w != width || h != height) {
			width = w;
			height = h;
			//setSize(width,height);

			Dimension preferredSize = new Dimension(width, height);
			setPreferredSize(preferredSize);
			revalidate();
			validate();
		}
	}
}