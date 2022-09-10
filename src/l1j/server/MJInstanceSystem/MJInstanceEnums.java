package l1j.server.MJInstanceSystem;

import java.util.ArrayList;
import l1j.server.server.clientpackets.C_Attr;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.ServerBasePacket;

public class MJInstanceEnums {
	/** ������ instance space ���¸� ��Ÿ����. **/
	public enum InstStatus{
		INST_USERSTATUS_NONE(1),	// instance space�� ���� ���� ��
		INST_USERSTATUS_LFCREADY(2),
		INST_USERSTATUS_LFCINREADY(4),
		INST_USERSTATUS_LFC(8);		
		
		private int _status;
		InstStatus(int i){
			_status = i;
		}
	}
	
	public enum InstSpcMessages{
		INSTANCE_SPACE_FULL("�ν��Ͻ� ������ �� á���ϴ�. ��� �� �ٽ� �õ����ּ���.");
		
		private String _msg;		
		InstSpcMessages(String msg){
			_msg = msg;
		}
		
		public String get(){
			return _msg;
		}
		public void sendSystemMsg(L1PcInstance pc){
			pc.sendPackets(new S_SystemMessage(get()));
		}
		public void sendSystemMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_SystemMessage(new StringBuilder(get()).append(" ").append(msg).toString()));			
		}
		public void sendGreenMsg(L1PcInstance pc){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, get()));
		}
		public void sendGreenMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, new StringBuilder(get()).append(" ").append(msg).toString()));
		}
	}
	
	/** ��� ���� �޽��� **/
	public enum LFCMessages{	
		REGIST_SUCCESS("����� �Ϸ�Ǿ����ϴ�."),
		REGIST_ERR_ININST("�ν��Ͻ� ���¿����� ����� �� �����ϴ�."),
		REGIST_ERR_NOADENA("�ʿ� �������� �����մϴ�."),
		REGIST_ERR_INTHEMILL("���� �غ����Դϴ�."),
		REGIST_ERR_ADENA("���� �ݾ��� �߸��ƽ��ϴ�."),
		REGIST_ERR_LEVEL("���� ������ �������� �ʽ��ϴ�. "),
		CREATE_ERR_TARGET_CANNOT("����(��)�� ���� ������ �� ���� �����Դϴ�."),
		CREATE_ERR_CANNOT_INPARTYPLAY("��Ƽ�߿��� ������ ��û�� �Ұ����մϴ�."),
		CREATE_ERR_RVR("RVR �������� ��Ƽ �������Դϴ�."),
		CREATE_ERR_PVP("PVP �������� �� ��Ƽ �������Դϴ�."),
		CREATE_ERR_ONLYLEADER("��Ƽ�常 ��û�� �� �ֽ��ϴ�."),
		CREATE_ERR_PARTYMEMBER("���� �Ұ� ������ ��Ƽ���� �ֽ��ϴ�."),
		CREATE_ERR_PARTYMAXSIZE("�ִ� ��Ƽ �ο��� �Ѿ����ϴ�."),
		CREATE_ERR_PARTYMINSIZE("�ּ� ���� ��Ƽ�ο��� ä���� ���߽��ϴ�."),
		CREATE_NOTIFY_CANCEL_INPARTYPLAY("�ݷμ��� ��û�� �������� ��Ƽ��(������)/��Ƽ���� �ƴ�(��ü��)���� ���� ��ҵǾ����ϴ�."),
		CREATE_SUBSCRIBE("�ݷμ��� ��û�� ���Խ��ϴ�. 15�� ���� �������� ������ ��ҷ� �����մϴ�."),
		CREATE_SUCCESS("�ݷμ��� ��û�� �Ϸ�Ǿ����ϴ�. ������ 15�� ���� �������� ������ ��ҵ˴ϴ�."),
		CREATE_CANCEL_OWNERUSER("������ ��û�� �����߽��ϴ�."),
		CREATE_CANCEL("��Ⱑ ��ҵǾ����ϴ�."),
		INGAME_CLOSE("��Ⱑ ����Ǿ����ϴ�. ��� �� ��� ���� �� ������ �̵��մϴ�."),
		INGAME_CLOSE_FORGM("GM�� ���� ������ ������ ����Ǿ����ϴ�."),
		INGAME_NOTIFY_WINNER("�¸��߽��ϴ�. �¸� ���� �������� �� ���޵˴ϴ�."),
		INGAME_NOTIFY_LOSER("����� LFC������ �й��߽��ϴ�."),
		INGAME_NOTIFY_READY("[����غ�] "),
		INGAME_NOTIFY_START("Start!"),
		INGAME_NOTIFY_CLOSETIME("[�����ӹ�] "),
		INGAME_NOTIFY_LOTTO("�����մϴ�. �������� ��÷�Ǿ����ϴ�.");
		private String _msg;		
		LFCMessages(String msg){
			_msg = msg;
		}
		
		public String get(){
			return _msg;
		}
		public void sendSystemMsg(L1PcInstance pc){
			pc.sendPackets(new S_SystemMessage(get()));
		}
		public void sendSystemMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_SystemMessage(new StringBuilder(get()).append(" ").append(msg).toString()));			
		}
		public void sendSystemMsgToList(ArrayList<L1PcInstance> pcs){
			sendList(pcs, new S_SystemMessage(get()));
		}
		public void sendSystemMsgToList(ArrayList<L1PcInstance> pcs, String msg){
			sendList(pcs, new S_SystemMessage(new StringBuilder(get()).append(" ").append(msg).toString()));
		}
		public void sendGreenMsg(L1PcInstance pc){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, get()));
		}
		public void sendGreenMsg(L1PcInstance pc, String msg){
			pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, new StringBuilder(get()).append(" ").append(msg).toString()));
		}
		public void sendGreenMsgToList(ArrayList<L1PcInstance> pcs){
			sendList(pcs, new S_PacketBox(S_PacketBox.GREEN_MESSAGE, get()));
		}
		public void sendGreenMsgToList(ArrayList<L1PcInstance> pcs, String msg){			
			sendList(pcs, new S_PacketBox(S_PacketBox.GREEN_MESSAGE, new StringBuilder(get()).append(" ").append(msg).toString()));
		}
		
		private void sendList(ArrayList<L1PcInstance> pcs, ServerBasePacket pck){
			int size 		= pcs.size();
			for(int i=0; i<size; i++)
				pcs.get(i).sendPackets(pck, false);
			pck.clear();
		}
		
		public void sendSurvey(L1PcInstance pc){
			pc.sendPackets(new S_Message_YN(C_Attr.MSGCODE_622_LFC, 622, get()));
		}
	}
}
