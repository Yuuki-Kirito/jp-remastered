package server.system.autoshop;

import l1j.server.server.datatables.CharBuffTable;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1PcInstance;

public class AutoShopImpl implements AutoShop {
	private L1PcInstance shopCharacter;

	public AutoShopImpl(L1PcInstance pc) {
		shopCharacter = pc;
	}

	@Override
	public String getName() {
		return shopCharacter.getName();
	}

	@Override
	public void logout() {
		// character_buffに保存
		CharBuffTable.DeleteBuff(shopCharacter);
		CharBuffTable.SaveBuff(shopCharacter);
		shopCharacter.getSkillEffectTimerSet().clearSkillEffectTimer();

		L1PolyMorph.undoPoly(shopCharacter);
		shopCharacter.logout();
		shopCharacter = null;

	}

}
