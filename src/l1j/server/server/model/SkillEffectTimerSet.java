package l1j.server.server.model;

import java.util.HashMap;
import java.util.Map;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillTimer;
import l1j.server.server.model.skill.L1SkillTimerCreator;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_OwnCharStatus2;

public class SkillEffectTimerSet {
	private final Map<Integer, L1SkillTimer> _skillEffect = new HashMap<Integer, L1SkillTimer>();
	// private final FastMap<Integer, L1SkillTimer> _skillEffect = new
	// FastMap<Integer, L1SkillTimer>();
	private L1Character cha;

	public SkillEffectTimerSet(L1Character cha) {
		this.cha = cha;
	}

	/**
	 * ĳ���Ϳ�, ���Ӱ� ��ų ȿ���� �߰��Ѵ�.
	 * 
	 * @param skillId �߰��ϴ� ȿ���� ��ų ID.
	 * @param timeMillis �߰��ϴ� ȿ���� ���� �ð�. ������ ���� 0.
	 */
	private void addSkillEffect(int skillId, int timeMillis) {
		L1SkillTimer timer = null;
		if (0 < timeMillis) {
			timer = L1SkillTimerCreator.create(cha, skillId, timeMillis);
			timer.begin();
		}
		_skillEffect.put(skillId, timer);
	}

	/**
	 * ĳ���Ϳ�, ��ų ȿ���� �����Ѵ�. <br>
	 * �ߺ� �ϴ� ��ų�� ���� ����, ���Ӱ� ��ų ȿ���� �߰��Ѵ�. <br>
	 * �ߺ� �ϴ� ��ų�� �ִ� ����, ������ ȿ�� �ð��� �Ķ������ ȿ�� �ð��� �� (��)���� �켱�� �����Ѵ�.
	 * 
	 * @param skillId �����ϴ� ȿ���� ��ų ID.
	 * @param timeMillis �����ϴ� ȿ���� ���� �ð�. ������ ���� 0.
	 */
	public void setSkillEffect(int skillId, int timeMillis) {
		// �̹� ��ų�� ����� �����̸�
		if (hasSkillEffect(skillId)) {
			// �������� ��ų ���� �ð� Ȯ��
			int remainingTimeMills = getSkillEffectTimeSec(skillId) * 1000;

			if (remainingTimeMills >= 0 && (remainingTimeMills < timeMillis || timeMillis == 0)) {
				// ���� ��ų Ÿ�̸� ����
				killSkillEffectTimer(skillId);
				// ���ο� ���� �ð����� ����
				addSkillEffect(skillId, timeMillis);
			}
		} else {
			// ��ų�� ����� ���°� �ƴϸ�
			addSkillEffect(skillId, timeMillis);
		}
	}

	/**
	 * ĳ���ͷκ���, ��ų ȿ���� �����Ѵ�.
	 * 
	 * @param skillId �����ϴ� ȿ���� ��ų ID
	 */
	public void removeSkillEffect(int skillId) {
		try {
			L1SkillTimer timer = _skillEffect.remove(skillId);
			if (timer != null) {
				timer.end();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ĳ���ͷκ���, ��ų ȿ���� Ÿ�̸Ӹ� �����Ѵ�. ��ų ȿ���� �������� �ʴ´�.
	 * 
	 * @param skillId �����ϴ� Ÿ�̸��� ��ų ID
	 */
	public void killSkillEffectTimer(int skillId) {
		L1SkillTimer timer = _skillEffect.remove(skillId);
		if (timer != null) {
			timer.kill();
		}
	}

	/**
	 * ĳ���ͷκ���, ��� ��ų ȿ�� Ÿ�̸Ӹ� �����Ѵ�. ��ų ȿ���� �������� �ʴ´�.
	 */
	public void clearSkillEffectTimer() {
		for (L1SkillTimer timer : _skillEffect.values()) {
			if (timer != null) {
				timer.kill();
			}
		}
		_skillEffect.clear();
	}

	public void clearRemoveSkillEffectTimer() {
		try {
			for (L1SkillTimer timer : _skillEffect.values()) {
				if (timer != null) {
					timer.end();
				}
			}
			_skillEffect.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ĳ���Ϳ�, �ش� ��ų ȿ���� �ɷ��ִ��� �˷���
	 * 
	 * @param skillId ��ų ID
	 * @return ���� ȿ���� ������ true, ������ false.
	 */
	public boolean hasSkillEffect(int skillId) {
		return _skillEffect.containsKey(skillId);
	}
	
	
	public int getSkillCount(int skillId) {
		int skillcounter = 0;
		return skillcounter + 1;
	}


	/**
	 * ĳ������ ��ų ȿ���� ���� �ð��� �����ش�.
	 * 
	 * @param skillId
	 *            �����ϴ� ȿ���� ��ų ID
	 * @return ��ų ȿ���� ���� �ð�(��). ��ų�� �ɸ��� ������ ȿ�� �ð��� ������ ���,-1.
	 */
	public int getSkillEffectTimeSec(int skillId) {
		L1SkillTimer timer = _skillEffect.get(skillId);
		if (timer == null) {
			return -1;
		}
		return timer.getRemainingTime();
	}

	public int setSkillEffectTimeSec(int skillId, int sec) {
		L1SkillTimer timer = _skillEffect.get(skillId);
		if (timer == null) {
			return -1;
		}
		return timer.setRemainingTime(sec);
	}

	
	/** ������ ���� �� �����ΰ� üũ�ϴºκ� �ϴ� üũ �س��� ���߿� ���� �Ҽ������� �ؼ��ϴ� ���� �ִ´�. */
	private boolean DominationSkill; 
	
	public boolean GetDominationSkill() {
		return DominationSkill;
	}
	
	/** ���̸� ��ų ������ �����̸� ��ų ���� ���� �� */
	public void SetDominationSkill(boolean DominationSkill) {
		L1PcInstance Pc = (L1PcInstance)cha;
		if(DominationSkill){
			Pc.addMaxHp(200);
			Pc.sendPackets(new S_HPUpdate(Pc.getCurrentHp(), Pc.getMaxHp()),true);
			Pc.getAbility().addAddedStr((byte) 1);
			Pc.getAbility().addAddedDex((byte) 1);
			Pc.getAbility().addAddedCon((byte) 1);
			Pc.getAbility().addAddedInt((byte) 1);
			Pc.getAbility().addAddedWis((byte) 1);
			Pc.getAbility().addAddedCha((byte) 1);
			Pc.sendPackets(new S_OwnCharStatus2(Pc), true);
		}else{
			Pc.addMaxHp(-200);
			Pc.sendPackets(new S_HPUpdate(Pc.getCurrentHp(), Pc.getMaxHp()),true);
			Pc.getAbility().addAddedStr((byte) -1);
			Pc.getAbility().addAddedDex((byte) -1);
			Pc.getAbility().addAddedCon((byte) -1);
			Pc.getAbility().addAddedInt((byte) -1);
			Pc.getAbility().addAddedWis((byte) -1);
			Pc.getAbility().addAddedCha((byte) -1);
			Pc.sendPackets(new S_OwnCharStatus2(Pc), true);
		}
		this.DominationSkill = DominationSkill;
	}
}
