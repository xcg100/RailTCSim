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
package org.railsim.service;

import org.railsim.service.exceptions.ManualPathException;
import org.railsim.service.trackObjects.pathableObject;
import org.railsim.service.trainCommands.*;
import org.railsim.train.fulltrain;

/**
 *
 * @author js
 */
public class trainCommandExecutor {

	public class stackEntry {

		public route r = null;
		public trainCommand c = null;
	};
	fulltrain ft = null;
	boolean isprerunner = false;
	trainCommandExecutor other = null;
	route currentroute = null;        // save(X)
	trainCommand currentcmd = null;   // save(X)
	java.util.concurrent.LinkedBlockingDeque<stackEntry> stack = new java.util.concurrent.LinkedBlockingDeque<>();          // save
	java.util.concurrent.ConcurrentHashMap<String, String> localstore = new java.util.concurrent.ConcurrentHashMap<>();   // save(X)
	volatile trainCommand waiting4 = null;    // save(X)
	volatile route waiting4route = null;      // save(X)
	volatile boolean waitingResult = false;   // save(X)
	volatile trainCommand[] lastCmds = null; // save(X)
	volatile route[] lastCmdRoutes = null;   // save(X)
	volatile int lc = 0;                      // save(X)

	/**
	 * Creates a new instance of trainCommandExecutor
	 */
	public trainCommandExecutor(fulltrain t, boolean _isprerunner) {
		ft = t;
		isprerunner = _isprerunner;
		lastCmds = new trainCommand[2];
		lastCmdRoutes = new route[2];
	}

	public trainCommandExecutor(fulltrain t, boolean _isprerunner, trainCommandExecutor _other) {
		ft = t;
		isprerunner = _isprerunner;
		other = _other;
		lastCmds = new trainCommand[2];
		lastCmdRoutes = new route[2];
	}

	public void storeLocalValue(String key, String value) {
		localstore.put(key, value);
	}

	public void storeGlobalValue(String key, String value) {
		ft.store.put(currentcmd.getName() + "." + key, value);
	}

	public void deleteGlobalValue(String key) {
		ft.store.remove(currentcmd.getName() + "." + key);
	}

	public String getLocalValue(String key) {
		return localstore.get(key);
	}

	public String getGlobalValue(String key) {
		return ft.store.get(currentcmd.getName() + "." + key);
	}

	public boolean isPrerunner() {
		return isprerunner;
	}

	public fulltrain getTrain() {
		return ft;
	}

	/**
	 *
	 * @return true: command is a drive command
	 */
	public boolean nextCommand() {
		return nextCommand(0);
	}

	private boolean nextCommand(int cnt) {
		/*if (isprerunner)
		 System.out.println("PPPPPP: "+ft.getName()+":"+cnt);
		 else
		 System.out.println("------: "+ft.getName()+":"+cnt);*/
		if (currentroute == null) {
			return true;
		}

		if (currentcmd == null || currentcmd.finished(this)) {
			if (!currentroute.isEnabled()) {
				return false;
			}

			/*
			 * Route: Befehle für Zug (fahre zu Ziel, warte, umdrehen, nächste Route, Unterroute, kuppeln, abkuppeln, ...)
			 * train.run -> Prozesscounter und Route, route.get(PC).run(train) - ruft train Befehle für Routen-Befehl auf,
			 * Unterroute: PC und alte Route auf Stack
			 * route.get(PC)==null: Werte von Stack
			 * Befehle zählen selbst PC hoch, wenn nötig
			 */
			if (currentcmd != null) {
				if (waiting4 == currentcmd) {
					waitingResult = true;
				}
				lastCmds[lc] = currentcmd;
				lastCmdRoutes[lc] = currentroute;
				lc = 1 - lc;
				int i = currentroute.commands.indexOf(currentcmd);
				i++;
				if (i >= currentroute.commands.size()) {
					i = 0;
				}
				trainCommand tc;
				try {
					tc = currentroute.commands.get(i);
				} catch (IndexOutOfBoundsException e) {
					currentcmd = null;
					return false;
				} catch (Exception e) {
					currentcmd = null;
					return false;
				}

				currentcmd = tc;
				localstore.clear();
				currentcmd.init(this);
				if (tc instanceof repeatRoute) {
					currentcmd = null;
				} else if (tc instanceof nextRoute) {
					currentroute = ((nextRoute) tc).getRoute();
					try {
						currentcmd = currentroute.commands.get(0);
					} catch (IndexOutOfBoundsException e) {
						currentcmd = null;
						return false;
					} catch (Exception e) {
						currentcmd = null;
						return false;
					}
					localstore.clear();
					currentcmd.init(this);
				} else if (tc instanceof subRoute) {
					stackEntry s = new stackEntry();
					s.r = currentroute;
					s.c = currentcmd;
					stack.push(s);
					currentroute = ((subRoute) tc).getRoute();
					try {
						currentcmd = currentroute.commands.get(0);
					} catch (IndexOutOfBoundsException e) {
						currentcmd = null;
						return nextCommand(cnt + 1);
					} catch (Exception e) {
						currentcmd = null;
						return false;
					}
					localstore.clear();
					currentcmd.init(this);
				}
			} else {
				if (stack.isEmpty()) {
					try {
						currentcmd = currentroute.commands.get(0);
						localstore.clear();
						currentcmd.init(this);
						if (currentcmd instanceof nextRoute) {
							currentroute = ((nextRoute) currentcmd).getRoute();
							try {
								currentcmd = currentroute.commands.get(0);
							} catch (IndexOutOfBoundsException e) {
								currentcmd = null;
								return false;
							} catch (Exception e) {
								currentcmd = null;
								return false;
							}
							localstore.clear();
							currentcmd.init(this);
						} else if (currentcmd instanceof subRoute) {
							stackEntry s = new stackEntry();
							s.r = currentroute;
							s.c = currentcmd;
							stack.push(s);
							currentroute = ((subRoute) currentcmd).getRoute();
							try {
								currentcmd = currentroute.commands.get(0);
							} catch (IndexOutOfBoundsException e) {
								currentcmd = null;
								return nextCommand(cnt + 1);
							} catch (Exception e) {
								currentcmd = null;
								return false;
							}
							localstore.clear();
							currentcmd.init(this);
						}
					} catch (IndexOutOfBoundsException e) {
						currentcmd = null;
						return false;
					} catch (Exception e) {
						currentcmd = null;
						return false;
					}
				} else {
					stackEntry s = stack.pop();
					currentroute = s.r;
					currentcmd = s.c;
				}
			}
			//if (!isprerunner)
			//    System.out.println(ft.getName()+" CMD:"+currentcmd.toString());
			return nextCommand(cnt + 1);
		}
		if (currentcmd == null) {
			return true;
		}
		// System.out.println("Ret: "+(currentcmd instanceof gotoDestination));
		return currentcmd instanceof gotoDestination;
	}

	public trainCommand getCurrentCommand() {
		return currentcmd;
	}

	public route getRoute() {
		if (currentcmd == null || currentroute == null) {
			return route.allroutes.firstEntry().getValue(); // Test
		}
		return currentroute;
	}

	public route getRoute(pathableObject po) throws ManualPathException {
		return getRoute();
	}

	public void setRoute(route r) {
		currentroute = r;
		currentcmd = null;
		stack.clear();
	}

	/**
	 * Wait/register wait until currentcmd is finished on other trainCommandExecutor: main-runner
	 *
	 * @return true if other has finished currentcmd
	 */
	public boolean waitForMain() {
		if (other == null) {
			return true;
		}
		return other.waitFor(currentroute, currentcmd);
	}

	private boolean waitFor(route r, trainCommand c) {
		if (waiting4 != c) {
			if (lastCmds[0] == c || lastCmds[1] == c) {
				return true;
			}
			waitingResult = false;
			waiting4 = c;
			waiting4route = r;
			return false;
		}
		waiting4 = null;
		waiting4route = null;
		return waitingResult;
	}

	public void tdata(String type, String key, String value) {
		if (type.endsWith(":store")) {
			localstore.put(key, value);
		} else {
			if (key.compareTo("route") == 0) {
				currentroute = route.findRoute(value);
			} else if (key.compareTo("cmd") == 0) {
				if (currentroute != null) {
					currentcmd = currentroute.commands.get(Integer.parseInt(value));
				}
			} else if (key.compareTo("wait4route") == 0) {
				waiting4route = route.findRoute(value);
			} else if (key.compareTo("wait4") == 0) {
				if (waiting4route != null) {
					waiting4 = waiting4route.commands.get(Integer.parseInt(value));
				}
			} else if (key.compareTo("waitresult") == 0) {
				waitingResult = value.compareTo("y") == 0;
			} else if (key.compareTo("lc") == 0) {
				lc = Integer.parseInt(value);
			} else if (key.compareTo("lcmd0r") == 0) {
				lastCmdRoutes[0] = route.findRoute(value);
			} else if (key.compareTo("lcmd1r") == 0) {
				lastCmdRoutes[1] = route.findRoute(value);
			} else if (key.compareTo("lcmd0") == 0) {
				if (lastCmdRoutes[0] != null) {
					lastCmds[0] = lastCmdRoutes[0].commands.get(Integer.parseInt(value));
				}
			} else if (key.compareTo("lcmd1") == 0) {
				if (lastCmdRoutes[1] != null) {
					lastCmds[1] = lastCmdRoutes[1].commands.get(Integer.parseInt(value));
				}
			} else if (key.compareTo("stack:r") == 0) {
				stackEntry se = new stackEntry();
				se.r = route.findRoute(value);
				stack.add(se);
			} else if (key.compareTo("stack:c") == 0) {
				stackEntry se = stack.getLast();
				se.c = se.r.commands.get(Integer.parseInt(value));
			}
		}
	}

	public String getSaveString(String type) {
		StringBuffer w = new StringBuffer();

		if (currentroute != null) {
			w.append("<tdata type='" + type + "' key='route' value='" + currentroute.getName() + "'/>\n");
			if (currentcmd != null) {
				w.append("<tdata type='" + type + "' key='cmd' value='" + currentroute.commands.indexOf(currentcmd) + "'/>\n");
			}
			if (waiting4route != null && waiting4 != null) {
				w.append("<tdata type='" + type + "' key='wait4route' value='" + waiting4route.getName() + "'/>\n");
				w.append("<tdata type='" + type + "' key='wait4' value='" + waiting4route.commands.indexOf(waiting4) + "'/>\n");
			}
		}
		w.append("<tdata type='" + type + "' key='waitresult' value='" + (waitingResult ? "y" : "n") + "'/>\n");
		w.append("<tdata type='" + type + "' key='lc' value='" + lc + "'/>\n");

		for (int i = 0; i < 2; ++i) {
			if (lastCmdRoutes[i] != null && lastCmds[i] != null) {
				w.append("<tdata type='" + type + "' key='lcmd" + i + "r' value='" + lastCmdRoutes[i].getName() + "'/>\n");
				w.append("<tdata type='" + type + "' key='lcmd" + i + "' value='" + lastCmdRoutes[i].commands.indexOf(lastCmds[i]) + "'/>\n");
			}
		}

		for (String sto : localstore.keySet()) {
			w.append("<tdata type='" + type + ":store' key='" + sto + "' value='" + localstore.get(sto) + "'/>\n");
		}
		for (stackEntry se : stack) {
			if (se.r != null) {
				w.append("<tdata type='" + type + "' key='stack:r' value='" + se.r.getName() + "'/>\n");
				if (se.c != null) {
					w.append("<tdata type='" + type + "' key='stack:c' value='" + se.r.commands.indexOf(se.c) + "'/>\n");
				}
			}
		}
		return w.toString();
	}
}
