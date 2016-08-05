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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author js
 */
public class DropDownToggleButton extends JToggleButton //implements PropertyChangeListener
{

	BasicButtonListener buttonListener;
	JLabel arrowDownLabel;
	JLabel textLabel = new JLabel();
	JPopupMenu popup = new JPopupMenu();
	boolean shouldDiscardRelease = false;
	JMenuItem currentVisible = null;
	ActionListener itemListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			currentVisible = (JMenuItem) e.getSource();
			updateObj();
			DropDownToggleButton.this.setSelected(true);
			ActionEvent e2 = new ActionEvent(e.getSource(), e.getID(), e.getActionCommand());
			DropDownToggleButton.this.fireActionPerformed(e2);
		}
	};

	public DropDownToggleButton() {
		super();

		BasicButtonUI bbui = (BasicButtonUI) getUI();
		buttonListener = (BasicButtonListener) getClientProperty(bbui);
		removeMouseListener(buttonListener);
		addMouseListener(new DropDownButtonMouseListener());
		//a.addPropertyChangeListener(this);
		configureObject();
	}

	public DropDownToggleButton(Action a) {
		super(a);

		BasicButtonUI bbui = (BasicButtonUI) getUI();
		buttonListener = (BasicButtonListener) getClientProperty(bbui);
		removeMouseListener(buttonListener);
		addMouseListener(new DropDownButtonMouseListener());
		//a.addPropertyChangeListener(this);
		configureObject();
	}

	private void configureObject() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		setText(null);
		setIcon(null);
		this.setMargin(new java.awt.Insets(2, 10, 2, 6));
		//add(new JLabel((Icon) a.getValue(Action.SMALL_ICON)));

		popup = new JPopupMenu();

		textLabel = new JLabel();
		add(textLabel);

		ImageIcon arrowDownIcon = new ImageIcon(getClass().getResource("/org/railsim/gui/resources/dropdown.gif"));
		arrowDownLabel = new JLabel(arrowDownIcon);
		add(arrowDownLabel);
	}

	private void updateObj() {
		String t;
		t = currentVisible.getText();
		textLabel.setText(" " + t + "  ");
		this.setActionCommand(currentVisible.getActionCommand());
	}

	private JMenuItem add(String t, String a) {
		JMenuItem jm = new JMenuItem(t);
		jm.setActionCommand(a);
		jm.addActionListener(itemListener);
		popup.add(jm);
		if (popup.getComponentCount() == 1) {
			currentVisible = jm;
			updateObj();
			arrowDownLabel.setVisible(false);
		} else {
			arrowDownLabel.setVisible(true);
		}
		return jm;
	}

	@Override
	public void setEnabled(boolean e) {
		super.setEnabled(e);
		textLabel.setEnabled(e);
		arrowDownLabel.setEnabled(e);
	}

	private class DropDownButtonMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (buttonListener != null) {
				buttonListener.mouseClicked(e);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//BasicButtonUI bbui = (BasicButtonUI) getUI();
			//buttonListener = (BasicButtonListener) getClientProperty(bbui);
			if (buttonListener != null) {
				buttonListener.mouseEntered(e);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (buttonListener != null) {
				buttonListener.mouseExited(e);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (buttonListener != null) {
				buttonListener.mousePressed(e);
			}

			if (isEnabled() && contains(e.getX(), e.getY())) {
				if (e.getX() > arrowDownLabel.getBounds().x && popup.getComponentCount() > 1) {
					//System.out.println("droppy");
					// clicked onto the DropDown Icon
					shouldDiscardRelease = true;

					// TODO
					//if (popup.getWidth()<getWidth())
					//    popup.setPopupSize(getWidth(),popup.getHeight());
					popup.show(DropDownToggleButton.this, 0, getHeight() - 2);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (shouldDiscardRelease) {
				getModel().setArmed(false);
				shouldDiscardRelease = false;
			}
			//else
			//currentVisible.doClick();
			if (buttonListener != null) {
				buttonListener.mouseReleased(e);
			}
		}
	}
	/**
	 * Holds value of property items.
	 */
	private LinkedHashMap<String, String> items = new LinkedHashMap<>();

	/**
	 * Indexed setter for property items.
	 *
	 * @param items show text
	 * @param actions action commands
	 */
	/*public void setItems(LinkedHashMap<String,String> i)
	 {
	 this.items=i;
	 popup.removeAll();
	 for(String a:this.items.keySet())
	 {
	 this.add(a,this.items.get(a));
	 }
	 }*/
	public void setItems(java.lang.String[] items) {
		this.items.clear();
		for (int i = 0; i < items.length; i++) {
			try {
				String[] k = items[i].split(":");
				;
				this.items.put(k[0], k[1]);
			} catch (Exception e) {
			}
		}
		popup.removeAll();
		for (String a : this.items.keySet()) {
			this.add(a, this.items.get(a));
		}
	}
}
