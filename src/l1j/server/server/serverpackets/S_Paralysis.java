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

package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

public class S_Paralysis extends ServerBasePacket {
	public S_Paralysis(int type) {
		writeC(type);
		writeC(2);
		writeH(0);
	}

	public S_Paralysis(int type, boolean flag, int time) {
		writeC(Opcodes.S_PARALYSE);
		if (type == TYPE_PARALYSIS) // ü�� �����ϰ� ����Ǿ����ϴ�.
		{
			if (flag == true) {
				writeC(2);
			} else {
				writeC(3);
			}
		} else if (type == TYPE_PARALYSIS2) // ü�� �����ϰ� ����Ǿ����ϴ�.
		{
			if (flag == true) {
				writeC(4);
			} else {
				writeC(5);
			}
		} else if (type == TYPE_TELEPORT_UNLOCK) // �ڷ���Ʈ ��� ������ ����
		{
			writeC(7);
			// 8 ,9 �� ����? �ð� 288?
		} else if (type == TYPE_SLEEP) // ������ ������ ���� ��, �� ���Ƚ��ϴ�.
		{
			if (flag == true) {
				writeC(10);
			} else {
				writeC(11);
			}
		} else if (type == TYPE_FREEZE) // ü�� ������ϴ�.
		{
			if (flag == true) {
				writeC(12);
			} else {
				writeC(13);
			}
		} else if (type == TYPE_STUN) // ���� �����Դϴ�.
		{
			if (flag == true) {
				writeC(22);
			} else {
				writeC(23);
			}
		} else if (type == TYPE_BIND) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(24);
			} else {
				writeC(25);
			}

		} else if (type == TYPE_POWER_GRIP) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(26);
			} else {
				writeC(27);
			}
		} else if (type == test1) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(28);
			} else {
				writeC(29);
			}

		} else if (type == TYPE_DEATH_PERADO) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(30);
			} else {
				writeC(31);
			}
		} else if (type == test2) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(32);
			} else {
				writeC(33);
			}
		} else if (type == test3) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(34);
			} else {
				writeC(35);
			}
		} else if (type == test4) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(36);
			} else {
				writeC(37);
			}
		} else if (type == test5) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(38);
			} else {
				writeC(39);
			}
		} else if (type == test6) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(40);
			} else {
				writeC(41);
			}
		} else if (type == test7) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(42);
			} else {
				writeC(43);
			}
		} else if (type == test8) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(44);
			} else {
				writeC(45);
			}
		} else if (type == test9) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(46);
			} else {
				writeC(47);
			}
		} else if (type == test10) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(48);
			} else {
				writeC(49);
			}
		}
		writeH(time); // �ð�??
	}

	public S_Paralysis(int type, boolean flag) {
		writeC(Opcodes.S_PARALYSE);
		if (type == TYPE_PARALYSIS) // ü�� �����ϰ� ����Ǿ����ϴ�.
		{
			if (flag == true) {
				writeC(2);
			} else {
				writeC(3);
			}
		} else if (type == TYPE_PARALYSIS2) // ü�� �����ϰ� ����Ǿ����ϴ�.
		{
			if (flag == true) {
				writeC(4);
			} else {
				writeC(5);
			}
		} else if (type == TYPE_TELEPORT_UNLOCK) // �ڷ���Ʈ ��� ������ ����
		{
			writeC(7);
			// 8 ,9 �� ����? �ð� 288?
		} else if (type == TYPE_SLEEP) // ������ ������ ���� ��, �� ���Ƚ��ϴ�.
		{
			if (flag == true) {
				writeC(10);
			} else {
				writeC(11);
			}
		} else if (type == TYPE_FREEZE) // ü�� ������ϴ�.
		{
			if (flag == true) {
				writeC(12);
			} else {
				writeC(13);
			}
		} else if (type == TYPE_STUN) // ���� �����Դϴ�.
		{
			if (flag == true) {
				writeC(22);
			} else {
				writeC(23);
			}
		} else if (type == TYPE_BIND) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(24);
			} else {
				writeC(25);
			}

		} else if (type == TYPE_POWER_GRIP) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(26);
			} else {
				writeC(27);
			}
		} else if (type == test1) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(28);
			} else {
				writeC(29);
			}

		} else if (type == TYPE_DEATH_PERADO) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(30);
			} else {
				writeC(31);
			}
		} else if (type == test2) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(32);
			} else {
				writeC(33);
			}
		} else if (type == test3) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(34);
			} else {
				writeC(35);
			}
		} else if (type == test4) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(36);
			} else {
				writeC(37);
			}
		} else if (type == test5) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(38);
			} else {
				writeC(39);
			}
		} else if (type == test6) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(40);
			} else {
				writeC(41);
			}
		} else if (type == test7) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(42);
			} else {
				writeC(43);
			}
		} else if (type == test8) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(44);
			} else {
				writeC(45);
			}
		} else if (type == test9) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(46);
			} else {
				writeC(47);
			}
		} else if (type == test10) // �ٸ��� �ӹڵ� ��ó�� ������ �� �����ϴ�.
		{
			if (flag == true) {
				writeC(48);
			} else {
				writeC(49);
			}
		} else
			writeH(0x00); // �ð�??
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return _S__2F_PARALYSIS;
	}

	public static final int TYPE_PARALYSIS = 1;

	public static final int TYPE_PARALYSIS2 = 2;

	public static final int TYPE_SLEEP = 3;

	public static final int TYPE_FREEZE = 4;

	public static final int TYPE_STUN = 5;

	public static final int TYPE_BIND = 6;

	public static final int TYPE_TELEPORT_UNLOCK = 7;

	public static final int TYPE_POWER_GRIP = 8;
	public static final int TYPE_DEATH_PERADO = 9;
	
	public static final int test1 = 10;
	public static final int test2 = 11;
	public static final int test3 = 12;
	public static final int test4 = 13;
	public static final int test5 = 14;
	public static final int test6 = 15;
	public static final int test7 = 16;
	public static final int test8 = 17;
	public static final int test9 = 18;
	public static final int test10 = 19;

	private static final String _S__2F_PARALYSIS = "[S] S_Paralysis";
}
