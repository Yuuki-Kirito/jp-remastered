package server;

import java.io.IOException;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.AccountAlreadyLoginException;
import l1j.server.server.GameServerFullException;
import l1j.server.server.clientpackets.C_NoticeClick;
import l1j.server.server.serverpackets.S_CharPass;
import l1j.server.server.serverpackets.S_LoginResult;
import l1j.server.server.serverpackets.S_Notice;

public class Authorization {
	private static Authorization uniqueInstance = null;
	private static Logger _log = Logger.getLogger(Authorization.class.getName());

	public static Authorization getInstance() {
		if (uniqueInstance == null) {
			synchronized (Authorization.class) {
				if (uniqueInstance == null)
					uniqueInstance = new Authorization();
			}
		}

		return uniqueInstance;
	}

	private Authorization() {

	}

	public void auth(LineageClient client, String accountName, String password, String ip, String host) throws IOException {
		/** ������ ����, ���ڷ� �Ǿ��ִ��� üũ **/
		int length = accountName.length();
		char chr;
		for (int i = 0; i < length; i++) {
			chr = accountName.charAt(i);
			if (Character.UnicodeBlock.of(chr) != Character.UnicodeBlock.BASIC_LATIN) {
				if (client.getActiveChar() == null) {
					client.sendPacket(new S_LoginResult(client.getAccount(), 26), true);
				}
				return;
			}
		}

		int accountLength = accountName.getBytes().length;
		int passwordLength = password.getBytes().length;
		if (accountLength > 12 || accountLength < 4) {
			_log.info("������ ���� [���� ���� " + accountName + "] ");
			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_WRONG_ACCOUNT));
			return;
		} else if (passwordLength > 12 || passwordLength < 4) {
			// �н����� ���� (eva �� �Ŵ��� Ŭ������)
			_log.info("�н����� ���� ���� [�н����� ���� " + passwordLength + "] ");
			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_WRONG_PASSWORD));
			return;
		}

		Account account = Account.load(accountName);
		if (account == null) {
			if (Config.AUTO_CREATE_ACCOUNTS) {

				if (Account.checkLoginIP(ip)) {
					S_Notice sn2 = new S_Notice("IP�� 2���� ������ ����մϴ�.");
					client.sendPacket(sn2);
					sn2.clear();
					sn2 = null;
					try {
						Thread.sleep(1500);
						client.kick();
						client.close();
					} catch (Exception e1) {
					}
				} else {
					account = Account.create(accountName, password, ip, host);
					account = Account.load(accountName);
				}
			} else {
				_log.warning("account missing for user " + accountName);
			}
		}

		if (account == null || !account.validatePassword(accountName, password)) {
			int lfc = client.getLoginFailedCount();
			client.setLoginFailedCount(lfc + 1);
			if (lfc > 2) {
				disconnect(client);
			} else {
				client.sendPacket(new S_LoginResult(client.getAccount(), 26), true);
			}

			return;
		}

		if (account.isBanned()) {
			_log.info("BAN ������ �α����� �ź��߽��ϴ�. account=" + accountName + " host=" + host);
			client.sendPacket(new S_Notice("�������� �����Ͻ� �����Դϴ�. ��ڿ��� ���� �Ͻñ� �ٶ��ϴ�"), true);
			disconnect(client);
			return;
		}

		try {
			LoginController.getInstance().login(client, account);
			Account.updateLastActive(account, ip); // ���� �α������� �����Ѵ�
			client.setAccount(account);
			client.sendPacket(new S_LoginResult());
			client.sendPacket(new S_CharPass(), true);
			sendNotice(client);
		} catch (GameServerFullException e) {
			client.sendPacket(new S_Notice("���� �����ο��� ���� ������ �����ǰ��ֽ��ϴ�.\n \n ����Ŀ� �ٽ� ������ �õ����ֽñ�ٶ��ϴ�."), true);
			disconnect(client);
			_log.info("���� �ο����� �ʰ��Ͽ����ϴ�. (" + client.getHostname() + ")�� ���� �õ��� ���� �����߽��ϴ�.");
			return;
		} catch (AccountAlreadyLoginException e) { // by ���������
			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_ACCOUNT_IN_USE), true);
			disconnect(client);
			return;
		}

	}

	public void sendNotice(LineageClient client) {
		String accountName = client.getAccountName();

		// �о���� ������ �ִ��� üũ
		if (S_Notice.NoticeCount(accountName) > 0) {
			client.sendPacket(new S_Notice(accountName, client), true);
		} else {
			new C_NoticeClick(client);
		}
	}

	private void disconnect(LineageClient client) throws IOException {
		client.kick();
		client.close();
	}
}
