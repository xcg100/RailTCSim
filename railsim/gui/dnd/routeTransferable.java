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
import java.util.ArrayList;

import org.railsim.service.trainCommands.trainCommand;

/**
 *
 * @author js
 */
public class routeTransferable implements Transferable {

	ArrayList<trainCommand> data;
	DataFlavor localArrayListFlavor, serialArrayListFlavor;

	public routeTransferable(ArrayList alist, DataFlavor _localArrayListFlavor, DataFlavor _serialArrayListFlavor) {
		data = alist;
		localArrayListFlavor = _localArrayListFlavor;
		serialArrayListFlavor = _serialArrayListFlavor;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
		if (!isDataFlavorSupported(flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return data;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{localArrayListFlavor,
					serialArrayListFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (localArrayListFlavor.equals(flavor)) {
			return true;
		}
		if (serialArrayListFlavor.equals(flavor)) {
			return true;
		}
		return false;
	}
}