package server.message;

public class ServerMessage {
	public final static int LEAVE_CLAN = 178;// \f1%0%s %1 血盟を脱退しました。

	public final static int CANNOT_DROP_OR_TRADE = 210; // \f1%0 は捨てるか、または他人に譲渡できません。

	public final static int CANNOT_BREAK_CLAN = 302; // \ f1解散できません。

	public final static int DUPLICATED_IP_CONNECTION = 357;

	public final static int HAVING_NEST_OF_CLAN = 665; // \f1姓やアジトを所有した状態では血盟を解散できません。
	public final static int CANNOT_WAR_FROM_ALLIANCECLAN = 1205; //同盟血盟とは戦争をすることはできません。

	public final static int CANNOT_BREAK_CLAN_HAVING_ALLIANCE = 1235;
}
