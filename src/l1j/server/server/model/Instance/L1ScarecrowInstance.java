package l1j.server.server.model.Instance;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Attack;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.CalcExp;

public class L1ScarecrowInstance extends L1NpcInstance {

	private static final long serialVersionUID = 1L;
	private static Random _random = new Random(System.nanoTime());

	public L1ScarecrowInstance(L1Npc template) {
		super(template);
	}

	@Override
	public void onAction(L1PcInstance player) {
		L1Attack attack = new L1Attack(player, this);
		if (attack.calcHit()) {
			if(player.getLevel() < 5 && getNpcId() == 202064){
				player.setExp(ExpTable.getExpByLevel(player.getLevel()+1));
			}
			if (player.getLevel() < 5) { // ����ƺ� ����
				ArrayList<L1PcInstance> targetList = new ArrayList<L1PcInstance>();

				targetList.add(player);
				ArrayList<Integer> hateList = new ArrayList<Integer>();
				hateList.add(1);
				CalcExp.calcExp(player, getId(), targetList, hateList, getExp());
			}

			// ������ üũ ����ƺ�
			if(getNpcId() == 450001830) {
				int dmg = attack.calcDamage();
				String chatId = "������ >> " + dmg + "�Դϴ�.";
				// ����ƺ� �� �޼����� ǥ��(ä��â �α׷� ǥ�� ����) => ��ü ����
//				Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this, chatId, 0));
				// ���θ� ���̵���
				player.sendPackets(new S_SystemMessage(chatId));
			}

			int heading = getMoveState().getHeading();
			if (heading < 7) {
				heading++;
			} else {
				heading = 0;
			}

			getMoveState().setHeading(heading);
			S_ChangeHeading ch = new S_ChangeHeading(this);
			Broadcaster.broadcastPacket(this, ch, true);
		}
	/*	//����ƺ� ������
		if (this.getNpcId() == 45003 ){
			
			int chance = _random.nextInt(4000) + 1; 
			if (chance < 3998){ 
			;player.getInventory().storeItem(40308, 50); 
			} else if (chance > 2) {
			; 
			player.getInventory().storeItem(9, 1); 
			player.sendPackets(new S_SystemMessage("�����������ڸ� ȹ���Ͽ����ϴ�."));
			}
			}*/
		attack.action();
		attack = null;
	}
	

	@Override
	public void onTalkAction(L1PcInstance l1pcinstance) {
	}

	public void onFinalAction() {
	}

	public void doFinalAction() {
	}
}
