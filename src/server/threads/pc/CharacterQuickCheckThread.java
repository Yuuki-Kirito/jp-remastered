package server.threads.pc;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import server.LoginController;

public class CharacterQuickCheckThread extends Thread {

	private static CharacterQuickCheckThread _instance;

	public static CharacterQuickCheckThread getInstance() {
		if (_instance == null) {
			_instance = new CharacterQuickCheckThread();
			_instance.start();
		}
		return _instance;
	}

	public CharacterQuickCheckThread() {
		super("server.threads.pc.CharacterQuickCheckThread");
	}

	public void run() {
		while (true) {
			try {
				for (L1PcInstance _client : L1World.getInstance().getAllPlayers()) {
					if (_client instanceof L1RobotInstance) continue;
					if (_client instanceof L1RobotInstance) continue;
					if (_client.isPrivateShop() || _client.getNetConnection() == null) continue;		
					if (_client.�����) continue;
					/** Ŭ���̾�Ʈ�� �����ִµ� �ɸ��� �����ֵ��� */
					if (_client.getNetConnection().isClosed()) {
						/** ������ �����ֵ��� ��Ƽ���¶�� ��Ƽ ���ֵ��� ���� */
						if (_client.isInParty()) // ��Ƽ��
							_client.getParty().leaveMember(_client);
						if (_client.isInChatParty()) // ä����Ƽ��
							_client.getChatParty().leaveMember(_client);
						/** ��� ���� ������ ���� ������������ ���� */
						_client.getNetConnection().quitGame(_client);
						synchronized (_client) {
							LoginController.getInstance().logout(_client.getAccountName());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
