package l1j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.GameSystem.INN.INN;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;

public class INN_Q implements Runnable {
	private final Queue<INN_IND> _queue;

	public INN_Q() {
		_queue = new ConcurrentLinkedQueue<INN_IND>();
		GeneralThreadPool.getInstance().execute(this);
	}

	public void requestWork(INN_IND _ind) {
		_queue.offer(_ind);
	}

	@SuppressWarnings("static-access")
	public void run() {
		while (true) {
			try {
				Thread.sleep(30L);
				synchronized (this) // タイミング問題で2回進入可能だ。 非常に低い確率ですが、.
									// 理論上
				{
					INN_IND _ind = _queue.peek();
					if (_ind == null) {
						continue;
					}
					final L1PcInstance player = L1World.getInstance()
							.getPlayer(_ind._pcname);
					if (player == null) {
						_queue.remove();
						continue;
					}

					if (player._ENTERING_LNDIA) {
						_queue.remove();
						continue;
					}

					player._ENTERING_LNDIA = true;
					GeneralThreadPool.getInstance().schedule(new Runnable() {
						public void run() {
							player._ENTERING_LNDIA = false;
							;
						}
					}, 3000);
					INN.getInstance().INNStart(player, _ind._objid,
							_ind._npcid, _ind._type, _ind._count);
					/*
					 * if(_ind._type == 0 ){//貸し出し
					 * 
					 * }else if(_ind._type == 1 || _ind._type == 2){//凍った }
					 */

					_queue.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}