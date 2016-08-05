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
package org.railsim.gui;

import java.awt.Color;
import java.util.TimerTask;

import javax.swing.JLabel;

import org.railsim.*;
import org.railsim.event.*;
import org.railsim.toolset.pathInfo;
import org.railsim.toolset.statusEvent;

/**
 *
 * @author js
 */
public class StatusBar extends javax.swing.JPanel {

	//final static int CLEARDELAY = 1000 * 8;
	final static int CLEARDELAY = 1000 * 30;

	class invokeRun implements Runnable {

		public JLabel label;
		public Color col;
		public String msg;

		@Override
		public void run() {
			label.setBackground(oldColor);
			label.setText(msg);
		}
	}

	class clearTask extends TimerTask {

		String msg;

		public clearTask(String m) {
			super();
			msg = m;
		}

		@Override
		public void run() {
			leftStatus(msg, true, false);
			rightStatus(msg, true, false);
		}
	};
	java.util.Timer clearTimer = new java.util.Timer();
	Color oldColor = null;

	/**
	 * Creates new form StatusBar
	 */
	public StatusBar() {
		initComponents();
		oldColor = leftStatus.getBackground();
		dataCollector.collector.statusListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				leftStatus((statusEvent) e);
			}
		});
		dataCollector.collector.pathInfoListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				if (((pathInfo) e.getSource()).getCode() == pathInfo.CODE_SET) { // TODO: Code Nummern wählbar
					rightStatus(((pathInfo) e.getSource()).getMessage(), false, false);
				}
			}
		});
		dataCollector.collector.exceptionListeners.addListener(new AbstractListener() {
			@Override
			public void action(AbstractEvent e) {
				Exception ex = (Exception) e.getSource();
				System.out.println(ex.getMessage());
				leftStatus(ex.getMessage(), false, true);
			}
		});

		clearTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						timeLabel.setText(dataCollector.collector.formatDate(dataCollector.collector.getDate()));
					}
				});
			}
		}, 0, 1000);
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
        timeLabel = new javax.swing.JLabel();
        leftStatus = new javax.swing.JLabel();
        rightStatus = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setMaximumSize(new java.awt.Dimension(32767, 19));
        setPreferredSize(new java.awt.Dimension(0, 19));
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        timeLabel.setText("00:00:00");

        leftStatus.setMaximumSize(new java.awt.Dimension(1000, 19));
        leftStatus.setMinimumSize(new java.awt.Dimension(0, 19));
        leftStatus.setPreferredSize(new java.awt.Dimension(100, 19));

        rightStatus.setMaximumSize(new java.awt.Dimension(1000, 19));
        rightStatus.setMinimumSize(new java.awt.Dimension(10, 19));
        rightStatus.setPreferredSize(new java.awt.Dimension(100, 19));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leftStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rightStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(leftStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel leftStatus;
    private javax.swing.JLabel rightStatus;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
	private String lastStatusL = null;
	private String lastStatusR = null;

	private void leftStatus(statusEvent e) {
		leftStatus(e.getString(), e.isUnset(), false);
	}

	private void leftStatus(String msg, boolean unset, boolean alarm) {
		if (unset) {
			if (lastStatusL != null && lastStatusL.compareTo(msg) == 0) {
				msg = "";
			} else {
				return;
			}
		}
		lastStatusL = msg;

		invokeRun r = new invokeRun();
		r.label = leftStatus;
		r.msg = msg;
		r.col = alarm ? Color.RED : oldColor;
		javax.swing.SwingUtilities.invokeLater(r);
		if (!unset) {
			clearTask c = new clearTask(msg);
			clearTimer.schedule(c, CLEARDELAY);
		}
	}

	private void rightStatus(String msg, boolean unset, boolean alarm) {
		if (unset) {
			if (lastStatusR != null && lastStatusR.compareTo(msg) == 0) {
				msg = "";
			} else {
				return;
			}
		}
		lastStatusR = msg;

		invokeRun r = new invokeRun();
		r.label = rightStatus;
		r.msg = msg;
		r.col = alarm ? Color.RED : oldColor;
		javax.swing.SwingUtilities.invokeLater(r);
		if (!unset) {
			clearTask c = new clearTask(msg);
			clearTimer.schedule(c, CLEARDELAY);
		}
	}
}
