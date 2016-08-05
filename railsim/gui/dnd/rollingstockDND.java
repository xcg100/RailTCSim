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
package org.railsim.gui.dnd;

import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import org.railsim.dataCollector;
import org.railsim.train.rollingstock;
import org.railsim.train.stock;

/**
 *
 * @author js
 */
public class rollingstockDND extends TransferHandler {

	DataFlavor localArrayListFlavor = null, serialArrayListFlavor = null;
	String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType
			+ ";class=java.util.ArrayList";
	String serialArrayListType = DataFlavor.javaJVMLocalObjectMimeType
			+ ";class=" + rollingstock.class.getCanonicalName();
	JList source = null;
	JTree tsource = null;
	int[] indices = null;
	int addIndex = -1; //Location where items were added
	int addCount = 0; //Number of items added

	public rollingstockDND() {
		try {
			serialArrayListFlavor = new DataFlavor(serialArrayListType);
		} catch (ClassNotFoundException e) {
			System.out.println("rollingstockDND: unable to create data flavor");
		}
	}

	public rollingstockDND(JList lst) {
		try {
			localArrayListFlavor = new DataFlavor(localArrayListType);
			serialArrayListFlavor = new DataFlavor(serialArrayListType);
		} catch (ClassNotFoundException e) {
			System.out.println("rollingstockDND: unable to create data flavor");
		}
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop()) {
			return false;
		}

		if (info.getComponent() instanceof JList) {
			JList list = (JList) info.getComponent();
			DefaultListModel listModel = (DefaultListModel) list.getModel();
			JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
			int index = dl.getIndex();
			boolean insert = dl.isInsert();

			// Get the string that is being dropped.
			Transferable t = info.getTransferable();
			ArrayList<Integer> alist = null;
			rollingstock rs = null;

			try {
				if (hasLocalArrayListFlavor(t.getTransferDataFlavors())) {
					alist = (ArrayList<Integer>) t.getTransferData(localArrayListFlavor);
				} else if (hasSerialArrayListFlavor(t.getTransferDataFlavors())) {
					rs = (rollingstock) t.getTransferData(serialArrayListFlavor);
				} else {
					return false;
				}
			} catch (	UnsupportedFlavorException | IOException ufe) {
				dataCollector.collector.gotException(ufe);
				//System.out.println("importData: unsupported data flavor");
				return false;
			}

			int max = listModel.getSize();
			if (index < 0) {
				index = max;
			} else {
				//index++;
				if (index > max) {
					index = max;
				}
			}
			if (rs != null) {
				listModel.add(index++, new stock(rs, true));
			} else {
				addIndex = index;
				addCount = alist.size();
				Object[] olist = new Object[addCount];
				for (int i = 0; i < alist.size(); i++) {
					olist[i] = listModel.getElementAt(alist.get(i));
				}
				for (int i = 0; i < alist.size(); i++) {
					listModel.add(index++, olist[i]);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		if ((action == MOVE) && (indices != null)) {
			DefaultListModel model = (DefaultListModel) source.getModel();

			//If we are moving items around in the same list, we
			//need to adjust the indices accordingly since those
			//after the insertion point have moved.
			if (addCount > 0) {
				for (int i = 0; i < indices.length; i++) {
					if (indices[i] > addIndex) {
						indices[i] += addCount;
					}
				}
			}
			for (int i = indices.length - 1; i >= 0; i--) {
				model.remove(indices[i]);
			}
		}
		indices = null;
		addIndex = -1;
		addCount = 0;
	}

	private boolean hasLocalArrayListFlavor(DataFlavor[] flavors) {
		if (localArrayListFlavor == null) {
			return false;
		}

		for (int i = 0; i < flavors.length; i++) {
			if (flavors[i].equals(localArrayListFlavor)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasSerialArrayListFlavor(DataFlavor[] flavors) {
		if (serialArrayListFlavor == null) {
			return false;
		}

		for (int i = 0; i < flavors.length; i++) {
			if (flavors[i].equals(serialArrayListFlavor)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		if (c instanceof JTree) {
			return false;
		}
		if (hasLocalArrayListFlavor(flavors)) {
			return true;
		}
		if (hasSerialArrayListFlavor(flavors)) {
			return true;
		}
		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		if (c instanceof JList) {
			source = (JList) c;
			indices = source.getSelectedIndices();
			ArrayList<Integer> alist = new ArrayList<>(indices.length);
			for (int i = 0; i < indices.length; i++) {
				alist.add(indices[i]);
			}
			return new rollingstockTransferable(alist, localArrayListFlavor);
		}
		if (c instanceof JTree) {
			tsource = (JTree) c;
			rollingstock rs = null;

			Object o = ((DefaultMutableTreeNode) tsource.getSelectionPath().getLastPathComponent()).getUserObject();
			if (o instanceof rollingstock) {
				rs = (rollingstock) o;
				return new rollingstockTransferable(rs, serialArrayListFlavor);
			}
		}
		return null;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}
}
