/*
	혹시 앞으로 추가될 시스템에 대한 시간 값이나
	기타 같이 묶을수 있는 경우 넣을수 있도록...
 */

package l1j.server.server.templates;

import java.util.Calendar;

public class L1EvaSystem {

	public L1EvaSystem(int id) {
		_typeId = id;
	}

	private int _typeId;

	public int getSystemTypeId() {
		return _typeId;
	}

	private Calendar _time;
	private int _openLocation;
	private int _moveLocation;
	private int _openContinuation = 0;
	public long bossTime = 0;
	public long bosscheck = 0;

	public Calendar getEvaTime() {
		return _time;
	}

	public void setEvaTime(Calendar i) {
		_time = i;
	}

	/**
	 * 次元の裂け目の開いた場所
	 *
	 * @return 0~7
	 */
	public int getOpenLocation() {
		return _openLocation;
	}

	public void setOpenLocation(int i) {
		_openLocation = i;
	}

	/**
	 * 次元の裂け目の移動先
	 *
	 * @return 0: default 1:テーベ 2:ティカル
	 */
	public int getMoveLocation() {
		return _moveLocation;
	}

	public void setMoveLocation(int i) {
		_moveLocation = i;
	}

	/**
	 * 時間が伸びているか？
	 *
	 * @return 0: default 1: 延長
	 */
	public int getOpenContinuation() {
		return _openContinuation;
	}

	public void setOpenContinuation(int i) {
		_openContinuation = i;
	}
}