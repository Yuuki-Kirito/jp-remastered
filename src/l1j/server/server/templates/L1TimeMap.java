/**
 * タイマー関連マップオブジェクト
 * 2008. 12. 04
 */

package l1j.server.server.templates;

public class L1TimeMap {

	private int _mapId;
	private int _time;
	private int _doorId;

	/**
	 * @param (int) id MAPID
	 * @param (int) time (s)
	 */
	public L1TimeMap(int id, int time) {
		this._mapId = id;
		this._time = time;
	}

	/**
	 * @param (int) id mapId
	 * @param (int) time (s)
	 * @param (int) DoorId
	 */
	public L1TimeMap(int id, int time, int DoorId) {
		this._mapId = id;
		this._time = time;
		this._doorId = DoorId;
	}

	/**
	 * マップIDを返す
	 *
	 * @return MapID
	 */
	public int getMapId() {
		return _mapId;
	}

	/**
	 * 時間を返す
	 *
	 * @return 時間(s)
	 */
	public int getTime() {
		return _time;
	}

	/**
	 * ドアのIDを返す
	 *
	 * @return doorId
	 */
	public int getDoor() {
		return _doorId;
	}

	/**
	 * 残り時間があるかを返す。
	 *
	 * @return 時間が終了した場合はtrue、残っている場合はfalse
	 */
	public boolean count() {
		return _time-- <= 0;
	}
}