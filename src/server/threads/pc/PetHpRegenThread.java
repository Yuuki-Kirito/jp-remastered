package server.threads.pc;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.serverpackets.S_PetWindow;

public class PetHpRegenThread extends Thread {

	private static PetHpRegenThread _instance;
	
	public static PetHpRegenThread getInstance() {
		if (_instance == null) {
			_instance = new PetHpRegenThread();
			_instance.start();
		}
		return _instance;
	}

	public PetHpRegenThread() {
		super("server.threads.pc.PetHpRegenThread");
	}

	public void run() {
		while (true) {
			try {
				/** 1��ȸ �ѹ� ���� �Ǿ����� ���������� �������� �ƴ��� üũ */
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc instanceof L1RobotInstance) continue;
					if (pc == null || pc.getNetConnection() == null){
						continue;
					} else {
						L1PetInstance Pet = (L1PetInstance) pc.getPet();
						if(pc.isDead() || Pet == null) continue;	
						/** 3�ʿ� �ѹ��� ��ȸ�� */
						if(Pet.getTarget() == null){
							if(Pet.getHpcurPoint() >= 24){
								RegenHp(Pet);
								Pet.setHpcurPoint(0);
								pc.sendPackets(new S_PetWindow(S_PetWindow.PatHp, Pet), true);
							}else Pet.setHpcurPoint(Pet.getHpcurPoint() + 1);
						}else{
							if(Pet.getHpcurPoint() >= 10){
								RegenHp(Pet);
								Pet.setHpcurPoint(0);
								pc.sendPackets(new S_PetWindow(S_PetWindow.PatHp, Pet), true);
							}else Pet.setHpcurPoint(Pet.getHpcurPoint() + 1);
						}
						/** �� ����Ʈ ������ 1�п� �ѹ��� ���� ���� �ϵ��� ���� */
						if(Pet.getFightingPoint() >= 60){
							Pet.Fighting(10000);
							Pet.setFightingPoint(0);
						}else Pet.setFightingPoint(Pet.getFightingPoint() + 1);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void RegenHp(L1PetInstance Pet) {
		if (Pet.isDead()) return;
	
		int Bonus = 0;
		/** ���� üũ�ؼ� ������ ȸ������ �����̸� ���ʽ� ȸ�� */
		if (isPlayerInLifeStream(Pet)) {
			Bonus += 10;
		}
	
		/** �� ��ų�� ���� ������ �����ؼ� ��ȸ�� ���ݴ� ������ ���������ϵ��� ���� */
		Bonus += Pet.SkillsTable(L1PetInstance.���������);
		Bonus += Pet.SkillsTable(L1PetInstance.�̼������);
		int BonusHp = Pet.getSurvival() + Pet.getElixirSurvival();
		if(BonusHp >= 20) Bonus += (Bonus -  10) / 10;
		
		/** �ӽ÷� ���� ���� 5���ξ� ��ȸ�� �ǰ� ���� */
		int NewHp = Pet.getCurrentHp();
		NewHp += Bonus + (Pet.getMaxHp() * 1 / 100);
		if (NewHp >= Pet.getMaxHp()) NewHp = Pet.getMaxHp();
		Pet.setCurrentHp(Math.min(NewHp, Pet.getMaxHp()));
	}
	
	/** ���� üũ */
	private static boolean isPlayerInLifeStream(L1PetInstance Pet) {
		L1EffectInstance effect = null;
		for (L1Object object : Pet.getNearObjects().getKnownObjects()) {
			if (object instanceof L1EffectInstance == false) {
				continue;
			}
			effect = (L1EffectInstance) object;
			if (effect.getNpcId() == 81169 && effect.getLocation().getTileLineDistance(Pet.getLocation()) < 4) {
				return true;
			}
		}
		return false;
	}
}
