/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.IndunSystem.MiniGame;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.Config;
import l1j.server.server.Announcements;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.RealTimeClock;
/*import l1j.server.server.serverpackets.S_HPMeter;*/
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;

public class BattleZone implements Runnable {
	protected final Random _random = new Random();

	private static BattleZone _instance;

	int on_time_2 = 3*3600;
	int on_time_5 = 6*3600;
	int on_time_8 = 9*3600;
	int on_time_11 = 12*3600;
	int on_time_14 = 15*3600;
	int on_time_17 = 18*3600;
	int on_time_20 = 21*3600;
	int on_time_23 = 24*3600;

	//デュアルスタートかどうか//
	private boolean _DuelStart;

	public boolean getDuelStart() {
		return _DuelStart;
	}

	public void setDuelStart(boolean duel) {
		_DuelStart = duel;
	}

	//デュアル入場可否//
	private boolean _DuelOpen;

	public boolean getDuelOpen() {
		return _DuelOpen;
	}

	public void setDuelOpen(boolean duel) {
		_DuelOpen = duel;
	}
	//デュアルスタートかどうか//
	private boolean is_PROGRESS;

	public boolean is_BATTLE_ZONE_PROGRESS() {
		return is_PROGRESS;
	}

	public void set_battle_zone_progress(boolean flag) {
		is_PROGRESS = flag;
	}


	private boolean is_END;

	public boolean is_BATTLE_ZONE_END() {
		return is_END;
	}

	public void set_battle_zone_ends(boolean flag) {
		is_END = flag;
	}
	//public int DuelCount;//

	private int enddueltime;

	private boolean Close;

	protected ArrayList<L1PcInstance> _battle_zone_user = new ArrayList<L1PcInstance>();
	public void addbattle_zone_user(L1PcInstance pc) 	{
		_battle_zone_user.add(pc);
	}
	public void removebattle_zone_user(L1PcInstance pc) 	{
		_battle_zone_user.remove(pc);
	}
	public void cear_battle_zone_user() 					{
		_battle_zone_user.clear();
	}
	public boolean is_BATTLE_ZONE_USER(L1PcInstance pc) 	{
		return _battle_zone_user.contains(pc);
	}
	public int get_battle_zone_usercount(){
		return _battle_zone_user.size();
	}

	private boolean GmStart = false;
	public void setGmStart(boolean ck){	GmStart = ck; }
	public boolean getGmStart(){	return GmStart;	}


	public L1PcInstance[] toArray_battlezone_user() {
		return _battle_zone_user.toArray(new L1PcInstance[_battle_zone_user.size()]);
	}
	public static BattleZone getInstance() {
		if (_instance == null) {
			_instance = new BattleZone();
		}
		return _instance;
	}


	@Override
	public void run() {
		try {
			while (true) {
				try{
					if(is_BATTLE_ZONE_END()== false){
						Thread.sleep(1000*60*60*2); //2時間待機時間//
						set_battle_zone_ends(false);
					}else{
						checkDuelTime(); // デュアル可能時間をチェック//
						if (is_BATTLE_ZONE_PROGRESS() == true)	{
							user_check();
						}
						Thread.sleep(1000);
					}
				}catch (Exception e) {}
			}
		} catch (Exception e1) {
		}
	}

	private void user_check() {
		L1PcInstance[] pc = toArray_battlezone_user();
		for (int i = 0; i < pc.length; i++) {
			if (pc[i] == null)
				continue;

			if (pc[i].getMapId() == 5001 || pc[i].getMapId() == 5153) {
				continue;
			} else {
				if (is_BATTLE_ZONE_USER(pc[i])) {
					removebattle_zone_user(pc[i]);
				}
				pc[i].set_DuelLine(0);
			}
		}
	}

	//デュアルタイムチェック//
	public void checkDuelTime() {
		//ゲーム時間を受け取る。//
		try{
			int servertime = RealTimeClock.getInstance().getRealTime().getSeconds();
			//現在時刻//
			int nowdueltime = servertime % 86400;
			int count1 = 0;
			int count2 = 0;
			int winLine = 4;
			if (getDuelStart() == false){
				if (  	nowdueltime >= on_time_2-31 && nowdueltime <= on_time_2+31 // /2時
						|| nowdueltime >= on_time_5-31 && nowdueltime <= on_time_5+31 ///5時
						|| nowdueltime >= on_time_8-31 && nowdueltime <= on_time_8+31 ////8時
						|| nowdueltime >= on_time_11-31 && nowdueltime <= on_time_11+31 //11時
						|| nowdueltime >= on_time_14-31 && nowdueltime <= on_time_14+31//14時
						|| nowdueltime >= on_time_17-31 && nowdueltime <= on_time_17+31//17時
						|| nowdueltime >= on_time_20-31 && nowdueltime <= on_time_20+31//20時
						|| nowdueltime >= on_time_23-31 && nowdueltime <= on_time_23+31//23時
						|| getGmStart())
				{
					setDuelOpen(true);
					setDuelStart(true);
					minute_wait_3();
				}
				if (is_BATTLE_ZONE_PROGRESS() == true)	{
					L1PcInstance[] c = toArray_battlezone_user();
					for (int i = 0; i < c.length; i++) {
						if(c[i].getMapId() == 5001){
							if(!c[i].isDead()){
								battle_zone_entry(c[i]);
							}
						}
					}
					setDuelStart(true);
					//終わる時間指定//
					enddueltime = nowdueltime + 600; //10分後終了終了時間定めるところ//

				}
			}else{
				//終了時間か強制終了の場合//
				if(nowdueltime >= enddueltime || Close == true){
					L1PcInstance[] c1 = toArray_battlezone_user();
					for (int i = 0; i < c1.length; i++) {
						if(c1[i].getMapId() == 5153){
							if(!c1[i].isDead()){
								if(c1[i].get_DuelLine() == 1){
									count1 += 1;
								}else{
									count2 += 1;
								}
							}
						}
					}
					//優勝チェック//
					String ment = null;
					if(count1 > count2){
						//1番ライン優勝//
						winLine = 1;
						ment = "プレミアムバトルゾーン「ダーク」ラインの勝利です。";
						L1World.getInstance().broadcastServerMessage("\\fW* バトルゾーン終了！ 「ダーク」ラインの勝利です。 *");
					}else if(count1 < count2){
						//2번라인 우승//
						winLine = 2;
						ment = "プレミアムバトルゾーン「シルバー」ラインの勝利です。";
						L1World.getInstance().broadcastServerMessage("\\fW* バトルゾーン終了！ 「シルバー」ラインの勝利です。 *");
					}else{
						winLine = 3;
						ment = "プレミアムバトルゾーン「ダーク」ラインと「シルバー」ラインが空いた。";
						L1World.getInstance().broadcastServerMessage("\\fW* バトルゾーン終了！ 「ダーク」ラインと「シルバー」ラインが同点です*");
					}

					L1PcInstance[] c2 = toArray_battlezone_user();
					for (int i = 0; i < c2.length; i++) {
						if(c2[i] == null) continue;
						if(c2[i].get_DuelLine() != 0){
							c2[i].sendPackets(new S_SystemMessage(ment));//コメント修正
							//勝ったラインにアイテムを支払う
							 if(c2[i].get_DuelLine() == winLine){
						    	 String[] itemIds = null;
							 		try{
							 			int idx = Config.BATTLE_ZONE_ITEMS.indexOf(",");
							 			// ,がある場合
							 			if(idx > -1){
							 				itemIds = Config.BATTLE_ZONE_ITEMS.split(",");
							 			}else{
							 				itemIds = new String[1];
							 				itemIds[0] = Config.BATTLE_ZONE_ITEMS;
							 			}
							 		}catch(Exception e){}
							 		// 支払うアイテムの数
							 		String[] counts = null;
							 		try{
							 			int idx = Config.NUMBER_OF_BATTLE_ZONE_ITEMS.indexOf(",");
							 			// ,がある場合
							 			if(idx > -1){
							 				counts = Config.NUMBER_OF_BATTLE_ZONE_ITEMS.split(",");
							 			}else{
							 				counts = new String[1];
							 				counts[0] = Config.NUMBER_OF_BATTLE_ZONE_ITEMS;
							 			}
							 		}catch(Exception e){}
							 		// アイテムIDやカウントがない場合//
							 		if (itemIds == null || counts == null)
							 			return;
							 		for (int j = 0; j < itemIds.length; j++) {
							 			int itemId = 0;
							 			int count = 0;
							 			itemId = Integer.parseInt(itemIds[j]);
							 			count = Integer.parseInt(counts[j]);
							 			if (itemId <= 0 || count <= 0)
							 				continue;
							 			L1ItemInstance item = c2[i].getInventory().storeItem(itemId, count);
							 			if (item != null)
							 				c2[i].sendPackets(new S_SystemMessage(item.getName() + " (" + count + ")を得ました."));
							 		}
							      c2[i].sendPackets(new S_SystemMessage("\\fU* 勝利チームにアイテムが支払われました *"));
							     }



							deleteMiniHp(c2[i]);
							c2[i].set_DuelLine(0);
							//バトルゾーンなら//
							if(c2[i].getMapId() == 5153 || c2[i].getMapId() == 5001){
								if(!c2[i].isDead()){
									L1Teleport.teleport(c2[i], 33090, 33402, (short) 4, 0, true);//
								}
							}
						}
					}
					ment = null;
					Announcements.getInstance().announceToAll("\\fW* プレミアムバトルゾーンが終了しました *");
					Announcements.getInstance().announceToAll("\\fW* バトルゾーンは3時間間隔で開きます *");
					set_battle_zone_ends(true);
					set_battle_zone_progress(false);
					setDuelStart(false);
					//	DuelCount = 0;
					Close = false;
					_battle_zone_user.clear();
					setGmStart(false);
				}else{
					//入場が終わったら//
					if(!getDuelOpen()){
						int count3 = 0;
						int count4 = 0;
						L1PcInstance[] c3 = toArray_battlezone_user();
						for (int i = 0; i < c3.length; i++) {
							if(c3[i] == null) continue;
							//バトルゾーンなら//
							if(c3[i].getMapId() == 5153){
								if(!c3[i].isDead()){//死なないユーザーチェック
									if(c3[i].get_DuelLine() == 1){
										count3 += 1;
									}else if(c3[i].get_DuelLine() == 2){
										count4 += 1;
									}else{
										removebattle_zone_user(c3[i]);
									}
								}
							}
						}

						//残りのユーザーが0人のときに強制終了を実行する<<//
						if(count3 == 0 || count4 == 0){
							Close = true;
						}
					}

				}

			}
		}catch(Exception e){}
	}

	private void createMiniHp(L1PcInstance pc) {
		// バトル時、互いにHPを表示させる//
		for (L1PcInstance member : BattleZone.getInstance().toArray_battlezone_user()) {
			// 同じラインにhpを表示//
			if (member != null) {
				if (pc.get_DuelLine() == member.get_DuelLine()) {
					/*member.sendPackets(new S_HPMeter(pc));
					pc.sendPackets(new S_HPMeter(member));*/
				}
			}
		}
	}

	////バトルゾーン変身////////
	private void battle_zone_transformation(L1PcInstance pc) {
		if (pc == null)
			return;
		int DuelLine = pc.get_DuelLine();
		int polyid = 0;
		int time = 1800;
		if (pc != null) {
			if (pc.isKnight() || pc.isCrown() || pc.isDarkelf() || pc.isDragonknight() || pc.isWarrior() || pc.isFencer()) {
				// 騎士君主ダークエルフ勇気士戦士//
				if (DuelLine == 1) {
					polyid = 13152;// <<1番ライン変身ダーク>
				} else {
					polyid = 13153;// 2番ラインアーク変身
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
			// 法師幻術師//
			if (pc.isWizard() || pc.isIllusionist()) {
				if (DuelLine == 1) {
					polyid = 13152;
				} else {
					polyid = 13153;
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
			// エルフ//
			if (pc.isElf()) {
				if (DuelLine == 1) {
					polyid = 13635;
				} else {
					polyid = 13631;
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
		}
	}


	private void battle_zone_entry(L1PcInstance pc) {
		try {
			battle_zone_transformation(pc);
			createMiniHp(pc);
			if (pc.get_DuelLine() == 1) {
				int ranx = 32628 + _random.nextInt(4);
				int rany = 32896 + _random.nextInt(5);
				L1Teleport.teleport(pc, ranx, rany, (short) 5153, 1, true);
			} else {
				int ranx2 = 32650 - _random.nextInt(4);
				int rany2 = 32893 + _random.nextInt(5);
				L1Teleport.teleport(pc, ranx2, rany2, (short) 5153, 5, true);
			}

			set_battle_zone_progress(false);
		} catch (Exception e) {
		}
	}


	public void minute_wait_3() {
		try {
			L1World.getInstance().broadcastPacketToAll(
					new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							"バトルゾーンが開かれました。 入場待ち時間は3分です."), true);

			Announcements.getInstance().announceToAll("3分後、団体展プレミアムバトルゾーンを開催します。");
			Announcements.getInstance().announceToAll("入場は先着順でギラン村でいただけます。");
			Announcements.getInstance().announceToAll("待合室入場後リースをすると参加できません。");
			try {
				Thread.sleep(1000 * 120);
			} catch (Exception e) {
			}
			Announcements.getInstance().announceToAll("1分後、プレミアムバトルゾーンの入場を締め切ります。");
			Announcements.getInstance().announceToAll("ギラン「プレミアムバトルゾーン」による入場が可能です。");
			try {
				Thread.sleep(1000 * 50);
			} catch (Exception e) {
			}
			Announcements.getInstance().announceToAll("プレミアムバトルゾーン入場締切10秒残りました。");
			try {
				Thread.sleep(1000 * 10);
			} catch (Exception e) {
			}
			if (getDuelOpen()) {
				setDuelOpen(false);
			}
			Announcements.getInstance().announceToAll("プレミアムバトルゾーンの入場を締め切りました。");
			try {
				Thread.sleep(1000 * 5);
			} catch (Exception e) {
			}
			set_battle_zone_progress(true);
			setGmStart(true);
		} catch (Exception e) {
		}
	}

	private void deleteMiniHp(L1PcInstance pc) {
		// バトル終了時、HPバーを削除する.//
		for (L1PcInstance member : pc.getKnownPlayers()){
			//同じラインにhpを表示//
			if(member != null){
				if(pc.get_DuelLine() == member.get_DuelLine()){
					/*pc.sendPackets(new S_HPMeter(member.getId(), 0xff, 0xff));
					member.sendPackets(new S_HPMeter(pc.getId(), 0xff, 0xff));*/
				}
			}
		}
	}


}
