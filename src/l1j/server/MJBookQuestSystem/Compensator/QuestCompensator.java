package l1j.server.MJBookQuestSystem.Compensator;

import java.sql.ResultSet;

import l1j.server.server.model.Instance.L1PcInstance;

/** An interface that will generalize a single reward bundle. **/
public interface QuestCompensator {
	/** Methods to automate instance setup  **/
	public void 	set(ResultSet rs) throws Exception;
	
	/** Returns the record item in error **/
	public String	getLastRecord();
	
	/** Returns the difficulty. **/
	public int getDifficulty();
	
	/** execute compensation. **/
	public void compensate(L1PcInstance pc);
}
