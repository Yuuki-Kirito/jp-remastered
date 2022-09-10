/*
\ * This program is free software; you can redistribute it and/or modify
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

/**
 * ��ų �������̳� ���� ����Ʈ�� ǥ�� �� ������ �뵵�� ���Ǵ� ��Ŷ�� Ŭ����
 */
public class S_SMPacketBox extends ServerBasePacket {
	private static final String S_SMPacketBox = "[S] S_SMPacketBox";

	private byte[] _byte = null;
	
	/**��ų ������ �ƿ�� ���� ���� by sm*/
	public static final int RMSkillIconAura = 22;
	
	/**���� ���ɷ� �ο� by sm*/
	public static final int RMSpiritAttr = 15;
	/** ����ġ���� ���޶��� �ڼ��� ������ **/
	public static final int EMERALD_ICON = 86;
	public static final int BUFFICON = 154;
    //79 16 72 00 80 02 b3 ff 	ff ff 
	
	
	public S_SMPacketBox(int subCode, int skillid, int time) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
			case RMSkillIconAura:
				writeC(skillid - 1);
				writeC(0x00); //�¿���?
				writeH(time);
				writeC(0x00);
				writeC(0x00);
				writeC(0x00);
				writeC(0x00);
			}
	}
	
	
//	public S_SMPacketBox() {
//		writeC(Opcodes.S_EVENT);
//			writeC(0x0a);
//			writeC(0x35); //�¿���?
//			writeC(0x02);
//			writeC(0x41);
//			writeC(0x07);
//			writeC(0xe5);
//			writeC(0x90);
//			writeC(0x19);
//			writeC(0x51);
//			writeC(0x64);
//	}
	
	public S_SMPacketBox(int subCode, int time, int gfxid, int type) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);	
		switch (subCode) {
			case BUFFICON:
				writeH(time); //�ð�
				writeD(gfxid); //������
				writeC(type); //Ÿ��
				writeC(0x00);
				break;
			case EMERALD_ICON:	//����ġ ����
				writeC(0x3e);
				writeC(type); //1:on 2:off
				writeH(time); //���ӽð�
				writeC(0x00);
				writeC(0x00);
				writeC(gfxid); // 1:�����ູ20%  2:�����ູ25%  3:�����ູ30%  6:������     9:�巡�Ｚ��     10:����������
				break;
		}
	}
	
	
	public S_SMPacketBox(int subCode, int attr) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
			case RMSpiritAttr:
				writeC(attr);
		}
	}


	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}

		return _byte;
	}

	
	
	@Override
	public String getType() {
		return S_SMPacketBox;
	}
	/*
	 * �̷��̹��� ��Ŷ 0e <- PacektBox 15 00 00 00 00 <- �� 1000�� �ϰ�� 1000 / 16 + 1
	 */
	
}
