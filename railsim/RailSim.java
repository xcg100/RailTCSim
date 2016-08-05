package org.railsim;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

import org.railsim.RailGUI;

public class RailSim {
	
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					RailSpace.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
