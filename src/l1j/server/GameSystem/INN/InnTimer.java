package l1j.server.GameSystem.INN;

import java.util.Timer;
import java.util.TimerTask;

import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SabuTell;

public class InnTimer extends TimerTask {
	private int _keymap_number;
	private int _number_of_keys;
	private int _timeMillis;
private boolean exit = false;
	public InnTimer(int keymap_nember, int number_of_keys, int timeMillis) {
		_keymap_number = keymap_nember;
		_number_of_keys = number_of_keys;
		_timeMillis = timeMillis;
	}

	public synchronized void key_subtraction(int count) {
		try {
			int check = _number_of_keys - count;
			if (check <= 0) {
				run();
			} else {
				_number_of_keys = check;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void run() {
		try {
			if(exit)return;
			exit = true;
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getMapId() == _keymap_number) {
					pc.setTelType(5);
					S_SabuTell st = new S_SabuTell(pc);
					pc.sendPackets(st, true);
				}
			}
			//System.out.println("111111111111111111111111");
			INN.setINN(_keymap_number, false);
			INN.setInnTimer(_keymap_number, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void begin() {
		Timer timer = new Timer();
		timer.schedule(this, _timeMillis);
	}
}