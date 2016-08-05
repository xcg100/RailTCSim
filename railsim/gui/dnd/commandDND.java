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

import org.railsim.service.trainCommands.trainCommand;

/**
 *
 * @author js
 */
public class commandDND extends TransferHandler {

	DataFlavor localArrayListFlavor, serialArrayListFlavor;
	String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType
			+ ";class=java.util.ArrayList";
	JList source = null;
	int[] indices = null;
	int addIndex = -1; //Location where items were added
	int addCount = 0; //Number of items added

	public commandDND() {
		try {
			localArrayListFlavor = new DataFlavor(localArrayListType);
		} catch (ClassNotFoundException e) {
			System.out.println("routeCommandDND: unable to create data flavor");
		}
		serialArrayListFlavor = new DataFlavor(ArrayList.class, "ArrayList");
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop()) {
			return false;
		}
		ArrayList<trainCommand> alist = null;
		JList target = (JList) info.getComponent();
		DefaultListModel listModel = (DefaultListModel) target.getModel();
		JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
		int index = dl.getIndex();
		boolean insert = dl.isInsert();

		// Get the string that is being dropped.
		Transferable t = info.getTransferable();

		try {
			if (hasLocalArrayListFlavor(t.getTransferDataFlavors())) {
				alist = (ArrayList<trainCommand>) t.getTransferData(localArrayListFlavor);
			} else if (hasSerialArrayListFlavor(t.getTransferDataFlavors())) {
				alist = (ArrayList<trainCommand>) t.getTransferData(serialArrayListFlavor);
			} else {
				return false;
			}
		} catch (UnsupportedFlavorException ufe) {
			System.out.println("importData: unsupported data flavor");
			return false;
		} catch (IOException ioe) {
			System.out.println("importData: I/O exception");
			return false;
		}
		int max = listModel.getSize();
		if (index < 0) {
			index = max;
		} else {
			index++;
			if (index > max) {
				index = max;
			}
		}
		addIndex = index;
		addCount = alist.size();
		for (int i = 0; i < alist.size(); i++) {
			listModel.add(index++, alist.get(i).clone());
		}
		return true;
	}

	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		/*
		 if ((action == MOVE) && (indices != null))
		 {
		 DefaultListModel model = (DefaultListModel) source.getModel();

		 //If we are moving items around in the same list, we
		 //need to adjust the indices accordingly since those
		 //after the insertion point have moved.
		 if (addCount > 0)
		 {
		 for (int i = 0; i < indices.length; i++)
		 {
		 if (indices[i] > addIndex)
		 {
		 indices[i] += addCount;
		 }
		 }
		 }
		 for (int i = indices.length - 1; i >= 0; i--)
		 model.remove(indices[i]);
		 } */
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
		/*
		 if (hasLocalArrayListFlavor(flavors))
		 {
		 return true;
		 }
		 if (hasSerialArrayListFlavor(flavors))
		 {
		 return true;
		 } */
		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		if (c instanceof JList) {
			source = (JList) c;
			indices = source.getSelectedIndices();
			Object[] values = source.getSelectedValues();
			if (values == null || values.length == 0) {
				return null;
			}
			ArrayList<trainCommand> alist = new ArrayList<>(values.length);
			for (int i = 0; i < values.length; i++) {
				trainCommand o = (trainCommand) values[i];
				alist.add(o);
			}
			return new routeTransferable(alist, localArrayListFlavor, serialArrayListFlavor);
		}
		return null;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}
	/*
	 public Icon getVisualRepresentation(Transferable t)
	 { System.out.println("icon");
	 ArrayList<trainCommand> alist = null;
	 try
	 {
	 if (hasLocalArrayListFlavor(t.getTransferDataFlavors()))
	 {
	 alist = (ArrayList<trainCommand>) t.getTransferData(localArrayListFlavor);
	 }
	 else if (hasSerialArrayListFlavor(t.getTransferDataFlavors()))
	 {
	 alist = (ArrayList<trainCommand>) t.getTransferData(serialArrayListFlavor);
	 }
	 else
	 {
	 return null;
	 }
	 }
	 catch (UnsupportedFlavorException ufe)
	 {
	 System.out.println("importData: unsupported data flavor");
	 return null;
	 }
	 catch (IOException ioe)
	 {
	 System.out.println("importData: I/O exception");
	 return null;
	 }

	 if (alist.size()>0)
	 { System.out.println("icon!!!");
	 trainCommand tc=alist.get(0);
	 return new textIcon(tc.toString());
	 }


	 return null;
	 }
	 */
}
