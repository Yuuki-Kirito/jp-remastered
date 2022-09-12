package server.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Instance.L1NpcInstance;

public class NpcDeleteController implements Runnable {
	private static Logger _log = Logger.getLogger(NpcDeleteController.class
			.getName());

	private static NpcDeleteController _instance;

	private FastTable<L1NpcInstance> _list;

	public static NpcDeleteController getInstance() {
		if (_instance == null)
			_instance = new NpcDeleteController();
		return _instance;
	}

	public NpcDeleteController() {
		_list = new FastTable<L1NpcInstance>();
		GeneralThreadPool.getInstance().execute(this);
	}

	private FastTable<L1NpcInstance> li = null;

	public void run() {
		while (true) {
			try {
				li = _list;
				for (L1NpcInstance npc : li) {

					if (npc == null)
						continue;
					if (npc.NpcDeleteTime < System.currentTimeMillis()) {
						npc.NpcDeleteTime = 0;
						npc.deleteMe();
						removeNpcDelete(npc);

					}
				}
			} catch (Exception e) {
				_log.log(Level.SEVERE, "NpcDeleteController[]Error", e);

			} finally {
				try {
					li = null;
					Thread.sleep(250);
				} catch (Exception e) {
				}
			}
		}
	}

	public void addNpcDelete(L1NpcInstance npc) {
		if (!_list.contains(npc))
			_list.add(npc);
	}

	public void removeNpcDelete(L1NpcInstance npc) {
		if (_list.contains(npc))
			_list.remove(npc);
	}

	public int getSize() {
		return _list.size();
	}

}