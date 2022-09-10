package l1j.server.MJBookQuestSystem.Compensator;

import java.sql.ResultSet;

import l1j.server.server.model.Instance.L1PcInstance;

/** �ϳ��� ���� ������ �Ϲ�ȭ ������ �������̽�. **/
public interface QuestCompensator {
	/** �ν��Ͻ� ������ �ڵ�ȭ ���� �޼���  **/
	public void 	set(ResultSet rs) throws Exception;
	
	/** ������ �� ���ڵ� �׸��� ��ȯ **/
	public String	getLastRecord();
	
	/** ���̵��� ��ȯ�Ѵ�. **/
	public int getDifficulty();
	
	/** ������ �����Ѵ�. **/
	public void compensate(L1PcInstance pc);
}
