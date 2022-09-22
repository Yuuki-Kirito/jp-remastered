package l1j.server.GameSystem.Astar;

import java.util.ArrayList;
import java.util.List;

import javolution.util.FastTable;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1TrapInstance;

public class AStar {

	// オープンノード、クローズノードリスト
	Node OpenNode, ClosedNode;
	private L1NpcInstance _npc = null;

	public void setnpc(L1NpcInstance npc) {
		_npc = npc;
	}

	// Maximum number of loops
	static final int LIMIT_LOOP = 200;
	// private List<Node> pool;
	// private List<Node> sabu;
	private FastTable<Node> pool;
	private FastTable<Node> sabu;

	private Node getPool() {
		Node node;
		if (pool.size() > 0) {
			node = pool.get(0);
			pool.remove(0);
		} else {
			node = new Node();
		}
		return node;
	}

	private void setPool(Node node) {
		if (node != null) {
			node.close();
			if (isPoolAppend(pool, node))
				pool.add(node);
		}
	}

	// *************************************************************************
	// Name : AStar()
	// Desc : constructor
	// *************************************************************************
	public AStar() {
		// sabu = new ArrayList<Node>();
		sabu = new FastTable<Node>();
		OpenNode = null;
		ClosedNode = null;
		// pool = new ArrayList<Node>();
		pool = new FastTable<Node>();
	}

	public void clear() {
		for (Node s : sabu) {
			try {
				s.close();
			} catch (Exception e) {
			}
			s.clear();
		}
		for (Node s2 : pool) {
			try {
				s2.close();
			} catch (Exception e) {
			}
			s2.clear();
		}
		OpenNode = null;
		ClosedNode = null;
		sabu.clear();
		pool.clear();
		sabu = null;
		pool = null;
	}

	// *************************************************************************
	// Name : ResetPath()
	// Desc : Remove previously created path
	// *************************************************************************
	public void cleanTail() {
		Node tmp;
		int cnt = 0;
		while (OpenNode != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			tmp = OpenNode.next;
			setPool(OpenNode);
			OpenNode = tmp;
		}
		cnt = 0;
		while (ClosedNode != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					ClosedNode = null;
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			tmp = ClosedNode.next;
			setPool(ClosedNode);
			ClosedNode = tmp;
		}

		/*
		 * if(cnt > 5000){
		 * System.out.println("insert name "+_npc.getName()+" x:"+_npc
		 * .getX()+" y:"+_npc.getY()+" m:"+_npc.getMapId());
		 * System.out.println(_npc.isDead()); L1PcInstance[] gm =
		 * Config.toArrayConnection chat monitor(); gm[0].dx= _npc.getX(); gm[0].dy=
		 * _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
	}

	// *************************************************************************
	// Name : FindPath()
	// Desc : Returns a list of path nodes by inputting the starting position and the target position.
	// *************************************************************************
	// モンスター座標 sx, xy
	// 移動する座標 tx, ty
	public Node searchTail(L1Object o, int tx, int ty, int m, boolean obj) {
		int calcx = o.getX() - tx;
		int calcy = o.getY() - ty;
		if (o instanceof L1RobotInstance) {
			if (o.getMapId() != m || Math.abs(calcx) > 40
					|| Math.abs(calcy) > 40) {
				return null;
			}
		} else if (o.getMapId() != m || Math.abs(calcx) > 30
				|| Math.abs(calcy) > 30) {
			/*
			 * if(o instanceof L1NpcInstance){ L1NpcInstance npp =
			 * (L1NpcInstance)o; if(npp.getNpcId() >=100750 && npp.getNpcId() <=
			 * 100757){
			 * 
			 * }else{ return null; } }
			 */

		}
		Node src, best = null;
		int count = 0;
		int sx = o.getX();
		int sy = o.getY();

		// 初めて起動ノードを作成
		src = getPool();
		src.g = 0;
		src.h = (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy);
		src.f = src.h;
		src.x = sx;
		src.y = sy;

		// 開始ノードを開いているノードリストに追加
		OpenNode = src;

		// ルート検索メインループ
		// 最大繰り返し回数を超えるとルートを停止
		while (count < LIMIT_LOOP) {
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				}
			}
			// オープンノードがない場合は、すべてのノードを検索したため、ルートを停止
			if (OpenNode == null) {
				// System.out.println("開いた場所がありません。");
				return null;
			}

			// オープンノードの最初のノードを取得し、オープンノードから削除
			best = OpenNode;
			OpenNode = best.next;

			// インポートしたノードを閉じたノードに追加
			best.next = ClosedNode;
			ClosedNode = best;

			// 現在インポートされているノードがターゲットノードである場合は、ルート検索に成功
			if (best.x == tx && best.y == ty) {
				return best;
			}

			// 現在のノードに隣接するノードに展開してオープンノードとして追加
			if (MakeChild(o, best, tx, ty, obj) == 0 && count == 0) {
				// System.out.println("詰まっています..");
				return null;
			}

			count++;
		}

		return null;
	}

	// *************************************************************************
	// Name : MakeChild()
	// Desc : Extend to adjacent nodes of the input node
	// *************************************************************************
	// リネージュ環境に合わせて再修正 by sabu
	private char makechild(L1Object o, Node node, int tx, int ty, boolean obj) {
		int x, y;
		char flag = 0;

		x = node.x;
		y = node.y;
		boolean ckckck = false;
		/*
		 * if(o instanceof L1NpcInstance){ L1NpcInstance npp = (L1NpcInstance)o;
		 * if(npp.getNpcId() >=100750 && npp.getNpcId() <= 100757){ ckckck =
		 * true; } }
		 */
		// 隣接ノードに移動可能かどうかを確認する
		for (int i = 0; i < 8; ++i) {
			if (ckckck || World.isThroughObject(x, y, o.getMapId(), i)) {
				int nx = x + getXY(i, true);
				int ny = y + getXY(i, false);
				boolean ck = true;
				// ゴールポイントの座標は検索する必要はありません。
				if (tx != nx || ty != ny) {
					if (obj) {
						if (o instanceof L1DollInstance) {
							ck = true;
						} else if (World.door_to_door(x, y, o.getMapId(), i) == true) {
							if (o instanceof L1MonsterInstance) {
								L1MonsterInstance Mon = (L1MonsterInstance) o;
								if(!(Mon.getNpcId() >= 46410 && Mon.getNpcId() <= 46483)){
									ck = false;
								}
							}else ck = false;
						} else {
							ck = World.isMapdynamic(nx, ny, o.getMapId()) == false;
						}
						if (ck && o instanceof L1RobotInstance) {
							// ck = L1World.getInstance().getVisiblePoint(new
							// L1Location(nx, ny, o.getMapId()), 0).size() == 0;
							try {
								ArrayList<L1Object> list = L1World
										.getInstance().getVisiblePoint(
												new L1Location(nx, ny,
														o.getMapId()), 0);
								if (list.size() > 0) {
									for (L1Object temp_obj : list) {
										if (temp_obj instanceof L1DollInstance
												|| temp_obj instanceof L1Inventory
												|| temp_obj instanceof L1TrapInstance) {
										} else {
											ck = false;
											break;
										}
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (ck) {
					MakeChildSub(node, nx, ny, o.getMapId(), tx, ty);
					flag = 1;
				} else if (tx != nx || ty != ny) {
					sabu.add(node);
				}
			} else {

			}
		}
		return flag;
	}

	// *************************************************************************
	// Name : FindPath()
	// Desc : Find a location close by.. fuck you
	// *************************************************************************
	// モンスター座標 sx, xy
	// 移動する座標 tx, ty
	public Node close_up_search(L1Object o, int tx, int ty, int m, boolean obj) {
		int calcx = o.getX() - tx;
		int calcy = o.getY() - ty;
		if (o instanceof L1RobotInstance) {
			if (o.getMapId() != m || Math.abs(calcx) > 40
					|| Math.abs(calcy) > 40) {
				return null;
			}
		} else if (o.getMapId() != m || Math.abs(calcx) > 30
				|| Math.abs(calcy) > 30) {
			/*
			 * if(o instanceof L1NpcInstance){ L1NpcInstance npp =
			 * (L1NpcInstance)o; if(npp.getNpcId() >=100750 && npp.getNpcId() <=
			 * 100757){ //Broadcaster.broadcastPacket(npp, new
			 * S_NpcChatPacket(npp, "1111111111111111111", 0)); }else{ return
			 * null; } }
			 */

		}

		Node src, best = null;
		int count = 0;
		int sx = o.getX();
		int sy = o.getY();

		// 初めて起動ノードを作成
		src = getPool();
		src.g = 0;
		src.h = (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy);
		src.f = src.h;
		src.x = sx;
		src.y = sy;

		// 開始ノードを開いているノードリストに追加
		OpenNode = src;

		// ルート検索メインループ
		// 最大繰り返し回数を超えるとルートを停止
		while (count < LIMIT_LOOP) {
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				}
			}
			// オープンノードがない場合は、すべてのノードを検索したため、ルートを停止
			if (OpenNode == null) {
				// System.out.println("開いた場所がありません。");
				return null;
			}

			// オープンノードの最初のノードを取得し、オープンノードから削除
			best = OpenNode;
			OpenNode = best.next;

			// インポートしたノードを閉じたノードに追加
			best.next = ClosedNode;
			ClosedNode = best;

			// 現在インポートされているノードがターゲットノードである場合は、ルート検索に成功
			if (best.x == tx && best.y == ty) {
				return best;
			}

			// 現在のノードに隣接するノードに展開してオープンノードとして追加
			if (makechild(o, best, tx, ty, obj) == 0 && count == 0) {
				// System.out.println("막혀있어..");
				return null;
			}

			count++;
		}
		int tmpdis = 0;
		for (Node saNode : sabu) {
			int x = saNode.x;
			int y = saNode.y;
			saNode.h = (tx - x) * (tx - x) + (ty - y) * (ty - y);
			if (tmpdis == 0) {
				best = saNode;
				tmpdis = saNode.h;
			}
			if (tmpdis > saNode.h) {
				best = saNode;
				tmpdis = saNode.h;
			}
		}

		if (best == null
				|| best.h >= (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy)) {
			return null;
		}
		if (sabu.size() > 0)
			sabu.clear();
		return best;
	}

	// *************************************************************************
	// Name : MakeChild()
	// Desc : Extend to adjacent nodes of the input node
	// *************************************************************************
	// リネージュ環境に合わせて再修正 by sabu

	private char MakeChild(L1Object o, Node node, int tx, int ty, boolean obj) {
		int x, y;
		char flag = 0;

		x = node.x;
		y = node.y;
		boolean ckckck = false;
		/*
		 * if(o instanceof L1NpcInstance){ L1NpcInstance npp = (L1NpcInstance)o;
		 * if(npp.getNpcId() >=100750 && npp.getNpcId() <= 100757){ ckckck =
		 * true; //Broadcaster.broadcastPacket(npp, new S_NpcChatPacket(npp,
		 * "33333333333", 0)); } }
		 */
		// 인접한 노드로 이동가능한지 검사

		for (int i = 0; i < 8; ++i) {
			if (ckckck || World.isThroughObject(x, y, o.getMapId(), i)) {
				int nx = x + getXY(i, true);
				int ny = y + getXY(i, false);
				boolean ck = true;
				// ゴールポイントの座標は検索する必要はありません.
				if (tx != nx || ty != ny) {
					if (obj) {
						if (o instanceof L1DollInstance) {
							ck = true;
						} else if (World.door_to_door(x, y, o.getMapId(), i) == true) {
							if (o instanceof L1MonsterInstance) {
								L1MonsterInstance Mon = (L1MonsterInstance) o;
								if(!(Mon.getNpcId() >= 46410 && Mon.getNpcId() <= 46483)){
									ck = false;
								}
							}else ck = false;
						} else {
							ck = World.isMapdynamic(nx, ny, o.getMapId()) == false;
						}
						if (ck && o instanceof L1RobotInstance) {
							if (o.getMap().isCombatZone(nx, ny))
								continue;
							// ck = L1World.getInstance().getVisiblePoint(new
							// L1Location(nx, ny, o.getMapId()), 0).size() == 0;
							ArrayList<L1Object> list = L1World
									.getInstance()
									.getVisiblePoint(
											new L1Location(nx, ny, o.getMapId()),
											0);
							if (list.size() > 0) {
								for (L1Object temp_obj : list) {
									if (temp_obj instanceof L1DollInstance
											|| temp_obj instanceof L1Inventory
											|| temp_obj instanceof L1TrapInstance) {
									} else {
										ck = false;
										break;
									}
								}
							}
						}
					}
				}
				if (ck) {
					MakeChildSub(node, nx, ny, o.getMapId(), tx, ty);
					flag = 1;
				}
			} else {

			}
		}

		return flag;
	}

	// *************************************************************************
	// Name : MakeChildSub()
	// Desc : create a node. If the node already exists in an open node or a closed node
	// Correct information if f is smaller compared to previous value
	// If it is in a closed node, the information of all nodes connected to it is also modified.
	// *************************************************************************
	void MakeChildSub(Node node, int x, int y, int m, int tx, int ty) {
		Node old = null, child = null;
		int g = node.g + 1;
		// Modify information if current node is in open node and f is smaller
		if ((old = IsOpen(x, y, m)) != null) {
			if (g < old.g) {
				old.prev = node;
				old.g = g;
				old.f = old.h + old.g;
			}

			// 現在のノードが閉じたノードにあり、fが小さい場合、情報を修正する
		} else if ((old = IsClosed(x, y, m)) != null) {
			if (g < old.g) {
				old.prev = node;
				old.g = g;
				old.f = old.h + old.g;
			}
			// 新しいノードであれば、ノード情報を作成して開いたノードに追加
		} else {
			try {
				// 新しいノードの作成
				child = getPool();

				child.prev = node;
				child.g = g;
				child.h = (x - tx) * (x - tx) + (y - ty) * (y - ty);
				child.f = child.h + child.g;
				child.x = x;
				child.y = y;

				// 新しいノードを開いたノードに追加
				InsertNode(child);
			} catch (Exception e) {
			}
		}
	}

	// *************************************************************************
	// Name : IsOpen()
	// Desc : Check if the input node is an open node
	// *************************************************************************
	private Node IsOpen(int x, int y, int mapid) {
		Node tmp = OpenNode;
		int cnt = 0;
		while (tmp != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				} else if (cnt > 10000) {
					return null;
				}
			}
			// cnt++;
			if (tmp.x == x && tmp.y == y) {
				return tmp;
			}
			tmp = tmp.next;
		}

		/*
		 * if(cnt > 5000){
		 * System.out.println(cnt+" open x :"+x+" y :"+y+" m :"+mapid);
		 * System.out.
		 * println(" 이름"+_npc.getName()+" x:"+_npc.getX()+" y:"+_npc.getY
		 * ()+" m:"+_npc.getMapId()); System.out.println(_npc.isDead());
		 * L1PcInstance[] gm = Config.toArrayConnection chat monitor(); gm[0].dx= _npc.getX();
		 * gm[0].dy= _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
		return null;
	}

	// *************************************************************************
	// Name : IsClosed()
	// Desc : Check if the input node is a closed node
	// *************************************************************************
	private Node IsClosed(int x, int y, int mapid) {
		Node tmp = ClosedNode;
		int cnt = 0;
		while (tmp != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				} else if (cnt > 10000) {
					return null;
				}
			}
			// cnt ++;
			if (tmp.x == x && tmp.y == y) {
				return tmp;
			}
			tmp = tmp.next;
		}
		/*
		 * if(cnt > 5000){
		 * System.out.println(cnt+" close x :"+x+" y :"+y+" m :"+mapid);
		 * System.out
		 * .println(" name"+_npc.getName()+" x:"+_npc.getX()+" y:"+_npc.getY
		 * ()+" m:"+_npc.getMapId()); System.out.println(_npc.isDead());
		 * L1PcInstance[] gm = Config.toArrayConnection chat monitor(); gm[0].dx= _npc.getX();
		 * gm[0].dy= _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
		return null;
	}

	// *************************************************************************
	// Name : InsertNode()
	// Desc : The input node is added to the open node by sorting it according to the f value.
	// -> Optimal node so that the one with the highest f-value is at the top
	// *************************************************************************
	private void InsertNode(Node src) {
		Node old = null, tmp = null;
		int cnt = 0;
		if (OpenNode == null) {
			OpenNode = src;
			return;
		}
		tmp = OpenNode;
		while (tmp != null && (tmp.f < src.f)) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			old = tmp;
			tmp = tmp.next;
		}
		if (old != null) {
			src.next = tmp;
			old.next = src;
		} else {
			src.next = tmp;
			OpenNode = src;
		}
		/*
		 * if(cnt > 100000){
		 * System.out.println("insert name "+_npc.getName()+" x:"+_npc
		 * .getX()+" y:"+_npc.getY()+" m:"+_npc.getMapId());
		 * System.out.println(_npc.isDead()); L1PcInstance[] gm =
		 * Config.toArrayConnection chat monitor(); gm[0].dx= _npc.getX(); gm[0].dy=
		 * _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
	}

	/**
	 * A function that checks if it is OK to add it to the pool. : If too many registrations are made, it will be a problem, so Kaba in the proper line.. :
	 * java.lang.OutOfMemoryError: Java heap space
	 * 
	 * @param c
	 * @return
	 */
	private boolean isPoolAppend(List<?> pool, Object c) {
		// 全本数でチェック。
		return pool.size() < 200;
	}

	/**
	 * 方向と種類に応じて適切に座標値設定を返す
	 * 
	 * @param h
	 *            : 方向
	 * @param type
	 *            : true ? x : y
	 * @return
	 */
	public int getXY(final int h, final boolean type) {
		int loc = 0;
		switch (h) {
		case 0:
			if (!type)
				loc -= 1;
			break;
		case 1:
			if (type)
				loc += 1;
			else
				loc -= 1;
			break;
		case 2:
			if (type)
				loc += 1;
			break;
		case 3:
			loc += 1;
			break;
		case 4:
			if (!type)
				loc += 1;
			break;
		case 5:
			if (type)
				loc -= 1;
			else
				loc += 1;
			break;
		case 6:
			if (type)
				loc -= 1;
			break;
		case 7:
			loc -= 1;
			break;
		}
		return loc;
	}

	public int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}

}