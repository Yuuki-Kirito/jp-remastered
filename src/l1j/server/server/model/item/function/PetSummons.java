package l1j.server.server.model.item.function;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.datatables.PetsSkillsTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_PetWindow;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;

@SuppressWarnings("serial")
public class PetSummons extends L1ItemInstance {

	public PetSummons(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			UsePetSummons(pc, this.getId());
		}
	}

	/** �� ���տ� ������ �ϴ� üũ */
	private void UsePetSummons(L1PcInstance pc, int itemObjectId) {
		try{
			if (pc.isInvisble()) return;
			boolean isPet = true;
			/** �� ����� 0�̻����� üũ�� ���������� �ƴ��� üũ */
			L1PetInstance Pet = null;
			if(pc.getPet() != null){
				Pet = (L1PetInstance)pc.getPet();
				if (Pet.getItemObjId() == itemObjectId){ 
					isPet = false;
				}else{
					/** �� ��ȯ ���Դµ� ���� �̹� �������ִٸ� ���� */
					pc.sendPackets(new S_SystemMessage("�̹� �ٸ����� ��ȯ�Ǿ��ֽ��ϴ�. "), true);
					return;
				}
			}
			
			L1ItemInstance useItem = pc.getInventory().getItem(itemObjectId);
			if (isPet) {
				/** ���⼭ ���� ����Ҽ� �����ϴ� */
				if (!pc.getMap().isTakePets()) {
					pc.sendPackets(new S_ServerMessage(563), true);
					return;
				}
				
				L1Pet L1pet = PetTable.getTemplate(itemObjectId);
				if(L1pet == null) return;
				L1Npc NpcTemp = NpcTable.getInstance().getTemplate(L1pet.getNpcId());
				
				if(L1pet.isPetDead()){
					/** �� ��Ȱ�� �ʿ��� ����ü 1000�� �ִ°�*/
					/** ����ü�� �����մϴ�.*/
					if(!pc.getInventory().consumeItem(41246, 1000)){
						pc.sendPackets(new S_SystemMessage("����ü�� �����Ͽ� ��Ȱ�Ҽ������ϴ�. "), true);
						return;
					}
					
					Pet = new L1PetInstance(NpcTemp, pc, L1pet);
					pc.sendPackets(new S_SkillSound(Pet.getId(), 5935), true);
					Broadcaster.broadcastPacket(pc, new S_SkillSound(Pet.getId(), 5935), true);
					pc.sendPackets(new S_ServerMessage(5274, Pet.getName()), true);
					
					if(Pet.SkillCheck(L1SkillId.DogBlood)){
						for (L1PcInstance Use : L1World.getInstance().getRecognizePlayer(Pet)) {
							Use.sendPackets(new S_PetWindow(S_PetWindow.DogBlood, Pet), true);
						}
					}
					
					/** ��ų ���� �ҷ��� */
					pc.sendPackets(new S_PetWindow(Pet.ArrayPetSkills()), true);
					
					/** ����� ���� ó�� */
					pc.sendPackets(new S_PetWindow(Pet, false), true);
					
					/** �ϴ� ��Ȱ ó�� ���� �ϵ��� �ϰ� �״��� �Ʒ����� ��ȯ ó�� �� */
					pc.sendPackets(new S_PetWindow(useItem, Pet, true, true, false, false, false), true);
					
					/** �� ��Ȱ��� �⺻������ �� ���� ������Ʈ  */
					PetTable.UpDatePet(Pet);
				/** ���� �ִٸ� ������Ŷ ó�� */
				}else{ 
					/** �� ��ȯ�ϱ� ���� ����ü�� �ִ��� ���� üũ ����ü�� �����մϴ�.*/
					if(!pc.getInventory().consumeItem(41246, 50)){
						pc.sendPackets(new S_SystemMessage("����ü�� �����Ͽ� ��ȯ�Ҽ� �����ϴ�. "), true);
						return;
					}
					
					/** ���� ���� ���¶�� ��� ���� �ʱ�ȭ ���� �ϵ��� ���� */
					Pet = new L1PetInstance(NpcTemp, pc, L1pet);
					if(L1pet.isProduct()){
						Pet.setProduct(false);
						/** �޼��� ��ũ */
						pc.sendPackets(new S_ServerMessage(5314), true);
						/** �⺻ ��ų ��������� */
						Pet.addPetSkills(Pet.getPetType().getSkillOneStep());
						pc.sendPackets(new S_PetWindow(Pet.getPetType().getSkillOneStep()), true);
						/** ��� ����t �ý���  */
						PetsSkillsTable.SaveSkills(Pet, false);
						
						/** ��Ŷ ó�� 1�� �¿��� 2�� ���� 3�� �̸� 4�� ������ */
						pc.sendPackets(new S_PetWindow(useItem, Pet, false, false, false, false, true), true);
						
						/** �� ��Ȱ��� �⺻������ �� ���� ������Ʈ  */
						PetTable.UpDatePet(Pet);
					}else pc.sendPackets(new S_PetWindow(Pet.ArrayPetSkills()), true);
					
					if(Pet.SkillCheck(L1SkillId.DogBlood)){
						for (L1PcInstance Use : L1World.getInstance().getRecognizePlayer(Pet)) {
							Use.sendPackets(new S_PetWindow(S_PetWindow.DogBlood, Pet), true);
						}
					}
					
					/** ����� ���� ó�� */
					pc.sendPackets(new S_PetWindow(Pet, false), true);
					
					pc.sendPackets(new S_SkillSound(Pet.getId(), 5935), true);
					Broadcaster.broadcastPacket(pc, new S_SkillSound(Pet.getId(), 5935), true);
					pc.sendPackets(new S_ServerMessage(5274, Pet.getName()), true);
					
					/** ��Ŷ ó�� 1�� �¿��� 2�� ���� 3�� �̸� 4�� ������ */
					pc.sendPackets(new S_PetWindow(useItem, Pet, true, false, false, false, false), true);
				}
			} else {
				/** �����߿��� ���� �Ҽ������ϴ�. */
				if(Pet.getTarget() != null){
					pc.sendPackets(new S_SystemMessage("�����߿��� ���� �Ҽ������ϴ�. "), true);
					return;
				}
				
				/** ���� ���� ���� ���¶�� �����ȵ� */
				if(Pet.isDead()){
					pc.sendPackets(new S_SystemMessage("�������¿����� �� ������ �Ұ��� �մϴ�. "), true);
					return;
				}
				
				Pet.deletePet();
				/** ��Ŷ ó�� 1�� �¿��� 2�� ���� 3�� �̸� 4�� ������ */
				pc.sendPackets(new S_PetWindow(useItem, Pet, true, false, false, false, false), true);			
			}
			L1ItemDelay.onItemUse(pc, useItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
