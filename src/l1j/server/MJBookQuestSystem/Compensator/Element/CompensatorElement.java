package l1j.server.MJBookQuestSystem.Compensator.Element;

import l1j.server.server.model.Instance.L1PcInstance;

/**
 * Interface to execute rewards
 * **/
public interface CompensatorElement {
	
	/** execute compensation. **/
	public void compensate(L1PcInstance pc);
}
