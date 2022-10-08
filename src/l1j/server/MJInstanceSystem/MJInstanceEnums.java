package l1j.server.MJInstanceSystem;

import java.util.ArrayList;

import l1j.server.server.clientpackets.C_Attr;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.ServerBasePacket;

public class MJInstanceEnums {
	/** Indicates the user's instance space status.**/
	public enum InstStatus{
		INST_USERSTATUS_NONE(1),	// instance space에 when not
		INST_USERSTATUS_LFCREADY(2),
		INST_USERSTATUS_LFCINREADY(4),
		INST_USERSTATUS_LFC(8);		
		
		private int _status;
		InstStatus(int i){
			_status = i;
		}
	}
	
	public enum InstSpcMessages{
		INSTANCE_SPACE_FULL("インスタンスダンジョンがいっぱいです。 しばらくしてからもう一度お試しください。");
		
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
	
	/** Registration Related Messages **/
	public enum LFCMessages{	
		REGIST_SUCCESS("登録が完了しました。"),
		REGIST_ERR_ININST("インスタンス状態では使用できません。"),
		REGIST_ERR_NOADENA("必要アイテムが不足しています。"),
		REGIST_ERR_INTHEMILL("まだ準備中です。"),
		REGIST_ERR_ADENA("賭け金額が間違っています。"),
		REGIST_ERR_LEVEL("レベル条件が満たされません。 "),
		CREATE_ERR_TARGET_CANNOT("相手（チーム）は現在参加できない状態です。"),
		CREATE_ERR_CANNOT_INPARTYPLAY("パーティー中は個人展の申請ができません。"),
		CREATE_ERR_RVR("RVRコンテンツはパーティーコンテンツです。"),
		CREATE_ERR_PVP("PVPコンテンツはノンパーティーコンテンツです。"),
		CREATE_ERR_ONLYLEADER("パーティー会場のみ申請できます。"),
		CREATE_ERR_PARTYMEMBER("参加不可状態のパーティーメンバーがいます。"),
		CREATE_ERR_PARTYMAXSIZE("最大パーティーの人数を超えました。"),
		CREATE_ERR_PARTYMINSIZE("最小会合パーティーの人数を記入できませんでした。"),
		CREATE_NOTIFY_CANCEL_INPARTYPLAY("コロシアム申請が入ってきましたが、パーティー中（個人展）/パーティー中ではない（団体展）のためキャンセルされました。"),
		CREATE_SUBSCRIBE("コロシアム申請が入ってきました。 15秒以内に受け入れない場合はキャンセルとみなします。"),
		CREATE_SUCCESS("コロシアム申請が完了しました。 相手が15秒以内に受け入れない場合はキャンセルされます。"),
		CREATE_CANCEL_OWNERUSER("相手が申請を拒否しました。"),
		CREATE_CANCEL("試合がキャンセルされました。"),
		INGAME_CLOSE("試合が終了しました。 しばらくすると、結果判定後に村に移動します。"),
		INGAME_CLOSE_FORGM("GMによって強制的にゲームが終了しました。"),
		INGAME_NOTIFY_WINNER("勝利しました。 勝利報酬アイテムがすぐに支払われます。"),
		INGAME_NOTIFY_LOSER("あなたはLFC戦で敗北しました。"),
		INGAME_NOTIFY_READY("[試合準備]"),
		INGAME_NOTIFY_START("Start!"),
		INGAME_NOTIFY_CLOSETIME("[終了差し迫った] "),
		INGAME_NOTIFY_LOTTO("おめでとうございます。 ランダム報酬に当選しました。");
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
