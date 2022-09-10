package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.skill.L1SkillId;

public class S_CreateItem extends ServerBasePacket {

	private static final String S_CreateItem = "[S] S_CreateItem";
	private byte[] _byte = null;
	public static final int N_SHOP_BUFF = 0x6e;
	public static final int NEW_SKILL = 0x92;
	public static final int NEW_SKILLP = 0x91;
	public static final int CRAFT_ITEMLIST = 0x39;
	public static final int ����Ʈ�Ϸ� = 0x0d;
	public static final int ����Ʈ�ε� = 0x09;
	public static final int ����Ʈ���� = 0x07;
	public static final int ����Ʈ������ = 0x0b;

	public S_CreateItem(int type, int id, int d) {
		buildPacket(type, id, d);
	}

	public S_CreateItem(int skillId, long time) {
		buildPacket(skillId, time);
	}

	public S_CreateItem(L1NpcInstance npc) {
		buildPacket(npc);
	}

	private void buildPacket(L1NpcInstance npc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(CRAFT_ITEMLIST);
		writeH(0x08);
		int craftlist[] = null;
		try {
			switch (npc.getNpcId()) {
			/** ������� - ���� **/ //�Ϸ�
			case 70028:  craftlist = new int[] { 264,265,266,267,268,269,270 };  break;
			/** ������� - ���Ʈ **/ //�Ϸ� 
			case 70641: craftlist = new int[] { 2229,2230,3410,3411,3412,199,200,201,570,571 };  break;
			/** ������� - ���� **/ //�Ϸ�
			case 70642:  craftlist = new int[] { 3296,3045,3044,3043,518,519,1964,194,195,196,819,821,189,190,191,192,193,69,70,71,72,73,74,75,76,77,78 };  break;
			/** ������� - �ٹ�Ʈ **/ //�Ϸ�
			case 70690:  craftlist = new int[] {3419, 3388,3389,3390,3391,3392,2783,2784,2785,2786,2787,2862,232,233,234,255,256,257,258,259,260,261,262,263,820,481,482,483,484,485,486,487,488 };  break;
			/** ������� - �׼����� - �ݼ� - **/ //�Ϸ�
			case 101001:craftlist = new int[] { 3413,3414,3415,3416,3417,3418, 2859, 2860, 2763, 2764, 2765, 2766, 2767, 718,719,720,721,722,723,724,725,2760, 2761, 2762 }; break;
			/** ������� - �׼����� - ���� - **/ //�Ϸ�
			case 101000: craftlist = new int[] {3599,3456,3457,3458,3459,3460,3461,3462,3463,3464,3465,3466,3467,3468,3469,3470, 3393,3394,3395,3396,3397,3398,3399,3400,3401,3402,3403,3404,3405,3406,3407,2775,2776,2777,2778,2779,2780,2781,2782,3420,712,713,1729,1730,305,309,313,1734,1735,1736,1737}; break;
			/** �۷�����������̺� - **/ //�Ϸ�
			case 101050: craftlist = new int[] {3408,3409}; break;	
			
			/** ������� - �׼����� - õ - **/ //�Ϸ�
			case 101002: craftlist = new int[] { 2878,2863,702,703,704,705,708,709,714,715,716,717 }; break;
			/** ������� - �׼����� - ���� - **/ //�Ϸ�
			case 101003: craftlist = new int[] { 706,707,710,711 }; break;
			/** ������� - �׼����� - ��Ƽ�� - **/ //�Ϸ�
			case 101027: craftlist = new int[] { 928,929,930,931,932,933,934,935,936,937,938,939,940,941,942,943,944,945,1539,1540,1541,1542,1543,1544,2976,2977,2978,2979,2980,2981,2982,2983,2984,2985,2986,2987,2988,2989,2990,2991}; break; 
			/** ������� - �׼����� - ������ - **/ //�Ϸ�
			case 101028: craftlist = new int[] { 946,947,948,949,950,951,952,953,954,955,956,957,958,959,960,961,962,963,964,965,966,967,968,969,970,971,972,973,974,975,976,977,978,979,980,981, 982,983,984,985,986,987,2948,2949,2950,2951,2952,2953,2954,2955,2956,2957,2958,2959,2960,2961,2962,2963,2964,2965,2966,2967,2968,2969,2970,2971,2972,2973,2974,2975}; break;
			/** ������� - �׼����� - �巡���� ������ - **/ //
			case 101026: craftlist = new int[] { 496,497 }; break;
			/** ���� Ž�� - **/ //
			case 100778 : craftlist = new int[] {2388, 2389, 2390}; break;
	        /** ������� - ������ - **/ //�Ϸ�
			case 101035: craftlist = new int[] { 1043,1044,1045,1046,1047,1048 }; break;
	/****************************************************************************************************/		
			/** �������� - Į��� **/ //�Ϸ�
			case 100629: craftlist = new int[] { 157,158 }; break;
			/** �������� - �������� **/ //�Ϸ�
			case 4212002: craftlist = new int[] { 103,104,105,106,107,108,109,496,497 }; break;
			/** �������� - ������ ���� **/ //�Ϸ�
			case 4212003: craftlist = new int[] { 83,84,85,86,2583, 2584, 2585, 2586,2587, 2588, 2589, 2590,
					2591, 2592, 2593, 2594 }; break;
			/** �������� - ������ ����ȣ **/ //�Ϸ�
			case 4212004: craftlist = new int[] { 87,88,89,90,2595, 2596, 2597, 2598,2599, 2600, 2601, 2602,
					2603, 2604, 2605, 2606 }; break;
			/** �������� - ������ ���̿��� **/ //�Ϸ�
			case 4212005: craftlist = new int[] { 79,80,81,82, 2571,2572,2573,2574,2575,2576,2577,2578,
					2579,2580,2581,2582 }; break;
			/** �������� - ������ �ٿ��� **/ //�Ϸ�
			case 4212006: craftlist = new int[] { 91,92,93,94,2607, 2608, 2609, 2610,2611, 2612, 2613, 2614,
					2615, 2616, 2617, 2618 }; break;
			/** �������� - ������ �Ұ� **/ //�Ϸ� ������ ���������� ���۵ǰԲ� ���ļ���
			case 100029: craftlist = new int[] { 515,514,516,517,116,123,159,160,161,162,271,272,273,274,275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,423,424,425,2182,2183,2184,2227,2185 }; break;
			/** �ؼ� - ��Ű **/ //��Ű�Ϸ�
			case 7310086: craftlist = new int[] { 1771, 1772, 1773, 1774, 1775, 1776, 1777, 1778, }; break;
	/****************************************************************************************************/		
			/** ���縶�� - �������� ǻ�� **/ //�Ϸ�
			case 4218001: craftlist = new int[] { 62,65,63,64,69,70,71,72,73,74,75,76,77,78 }; break;
			/** ȯ���縶�� - �������� ��Ʈ�� **/ //�Ϸ� 
			case 4219001: craftlist = new int[] { 67,68, 69,70,71,72,73,74,75,76,77,78 }; break;
	/****************************************************************************************************/		
			/** ȭ���θ��� - ���̶� **/ //�Ϸ� 
			case 70811: craftlist = new int[] { 417,418,419,420 }; break;
	/****************************************************************************************************/			
			/** ��������3�� - Ÿ�� ������ **/ //�Ϸ�
			case 70763: craftlist = new int[] { 212,213,214,2057,2058,2059,2060,2061,2062,2064,2063,2065 }; break;
	/****************************************************************************************************/			
			/** �Ƶ����� - ����� **/ //�Ϸ� 
			case 6000101: craftlist = new int[] { 823,824,825,826,827,828,829,830,831,832,833,834,835,836,837,838,839,840,841,842,843,844,845,846,847,848,849,850,851,852 }; break;
	/****************************************************************************************************/			
//			/** ������ - �׸��� **/ //�Ϸ�
//			case 100878: craftlist = new int[] { 577,578,579,580,581 }; break;
	/****************************************************************************************************/			
			/** ��Ÿ���� **/ //�Ϸ� 
			case 71119: craftlist = new int[] { 56 }; break;
			/** �Ƶ����� **/ //�Ϸ�
			case 71125: craftlist = new int[] { 46,47,48,49,50,51,52,53,54,55 }; break;
			
			
			/** �𸮾� **/ //�Ϸ�
			case 70598: craftlist = new int[] { 198,1810, }; break;//198 ã�ƾ���
			/** ���� �ű� **/ //�Ϸ�
			case 11887: craftlist = new int[] {1771,1772,1773,1774,1775,1776,1777,1778,1763,1764,1765,1766,1767,1768,1769,1770 }; break;
			/** ���� ��ȭ ���� **/ //�����ָӴϻ���Ϸ�
			case 301028: craftlist = new int[] { 2174,2175,2873,2874,2875,2747,422, 3541,3542,3543,3544,3545,3546,3547,3548, 3549,3550,3551,3552,3553,3554,3555,3556, 3557,3558,3559,3560,3561,3562,3563,3564,  3566,3567,3568,3569,3570,3571,3572,3573, 3574,3575,3576,3577,3578,3579,3580,3581, 3582,3583,3584,3585,3586,3587,3588,3589, 3590,3591,3592,3593,3594,3595,3596,3597 }; break;
			/** �߰� ����**/			//�׷��� �Ϸ�
			case 70838: craftlist = new int[] { 2864,2857,2865,2861,2768,2769,2770,2771,2772,2773,2774,2759,173,183,184,185,211 }; break; //�׷���
			
			

			/** ���� ����(�߷�) **/
			case 80069: craftlist = new int[] { 35,36,37,38}; break; 
			/** �߷����� - �߷��� �н� **/
			case 80068: craftlist = new int[] { 39,40,41,42 }; break;
			/** ���� ���°� **/
			case 70652: craftlist = new int[] { 10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33}; break;
			/** ��Ʈ **/
			case 70848: craftlist = new int[] {188, 189, 190}; break;
			/** ������ **/
			case 70837:	craftlist = new int[] { 186 }; break;
			/** ��Ƽ�� (�ַκδ��̺�Ʈ����) **/
			case 1: craftlist = new int[] { 360, 361, 362, 363 }; break;
			/** ������ **/
			case 7000078: craftlist = new int[] { 159,160 }; break;
			/** ��� **/
			case 70841: craftlist = new int[] { 187,188 }; break;
			/** �ƶ�ũ�� **/
			case 70846: craftlist = new int[] { 163,164,165 }; break;
			/** �� **/
			case 70850: craftlist = new int[] { 166,167,168 }; break;
			/** �� **/
			case 70851: craftlist = new int[] { 169,170 }; break;
			/** �� ��**/
			case 70852: craftlist = new int[] { 169,170,171 }; break;
			/** �������� - ������ �������� **/
			case 80053: craftlist = new int[] { 149, 150, 151, 152, 153, 154, 155, 156 }; break;
			/** �������� - ������ **/
			case 80054: craftlist = new int[] { 7, 8, 9 }; break; 
			/** �������� - ���� **/
			case 80051: craftlist = new int[] { 6 }; break;
			/** �߷����� - �߷��� �������� **/
			case 80072: craftlist = new int[] { 141, 142, 143, 144, 145, 146, 147, 148 }; break;
			//�Ƴ�� �̺�Ʈ ���Ǿ�
			case 6 : craftlist = new int[] { 1629,1630,1631,1632,1633,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643,1644,1645,1646 }; break;
			case 8 : craftlist = new int[] { 1647,1648,1649,1650,1651,1652,1653,1654,1655 }; break;
			
			//�ٷ�Ʈ �Ϸ� 
			case 70520:	craftlist = new int[] { 3598,2528,1861,95,96,97,98,99,1960,1961 }; break;
			
			//��� ��ǰ ���ݼ���^�Ƹ�
			case 7210073:	craftlist = new int[] { 2652,2653 }; break;
			
			//��������_��� �Ϸ�
			case 70027:	craftlist = new int[] { 2739, 2792, 2731, 2732, 2733, 2734, 2735, 2736, 2737, 2738, 2793,3196, 2788, 2789, 2790, 2791 }; break;
			
			//���̿� //�Ϸ�
			case 70676:	craftlist = new int[] {238,241,240,239,232,233,234,237,236,235,228,230,231,229}; break;
					
			//��ť���� //�Ϸ�
			case 87000: craftlist = new int[] {2879, 2880}; break;
					
			//�̺��� �彽���� �Ϸ�
			case 70662: craftlist = new int[] {2876,2877,2858,203,204,205,206,207,208,209,210,
											2619,2620,2621,2622,2623,2624,2625,2626,2627,2628,2629,2630,2631,2632,2633,2634,2635,2636,2637,2638,2639,2640,2641,2642,2643,2644,2645,2646,2647,2648,2649,2650,2651 }; break;
			
			//��� ����
			case 70904:	craftlist = new int[] { 220, 226, 223, 219, 225, 222, 221, 224, 216, 215, 217, 218, 70, 71,72,73,74,75,76,77,78}; break;
			//��� �����׽�Ʈ
			case 709044: craftlist = new int[] {3598,3599}; break;
			//�����ǹ���
			case 100625:	craftlist = new int[] { 100,101,102,110,111,112,113,114,115,520 }; break;
			
			//3408,3409 ����� ������, ����Ǳݺ���
			}
			int num;
			for (int i = 0; i < craftlist.length; i++) {
				writeC(0x12);
				num = craftlist[i];
				if (num > 127) {
					writeC(0x07);
				} else {
					writeC(0x06);
				}
				writeC(0x08);
				write4bit(num);
				writeH(0x10);
				writeH(0x18);
			}
			writeH(0x00);

		} catch (Exception e) {
		}
	}

	private void buildPacket(int type, int id, int d) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {
		case NEW_SKILL:
			writeC(0x01);
			writeC(0x08);
			writeC(id);
			if (id == 5) {
				writeC(0x10);
				writeC(0x0a);
			}
			
			writeH(0x00);
			break;
		case NEW_SKILLP:
			writeC(0x01);
			writeC(0x0a);
			writeC((id != 5 && id != 38) ? 0x02 : 0x04);
			writeC(0x08);
			writeC(id);
			if (id == 5) {
				writeC(0x10);
				writeC(0x0a);
			}
			if(id == 38) {
				writeC(0x18);
				writeC(0x0);
			}
			writeH(0);
		}
	}
	
	
	
	/** ����Ʈ ���� ��Ŷ */
	public S_CreateItem(int type, int Ÿ��) {
		/**
		 * ù���� ��Ŷ 
		b4 07 02(��Ŷ��)
		0a 0f (���� ������)
		08 80 02 (������ 80 �������� 1�� ����)
		10 00 ��(ó���� �ȿ� �ι�°���� ���Žð����� �ȾƷ�����)��
		/** 10 f0 9b cd cb 05 �ֱ�.. �ð��� �ƴѵ�.. 
		22 06 (�ϴܰ�����)
		08 01 (������)
		10 00 (�������ھ�)
		18 01 (�Ϸὺ�ھ�)
		28 01 (������)
		8f 16 (�����°�)
		/** �Ϸ���Ŷ 
		b4 0d 02(��Ŷ��)
		08 00 
		10 81 02 
		69 90 (����)
		*/
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {
			case ����Ʈ����:
				writeC(0x02);
				writeC(0x0a);
				writeC(0x0f);
				writeC(0x08);
				writeC(Ÿ��);
				writeC(0x02);
				writeC(0x10);
				writeC(0x00);
				writeC(0x22);
				writeC(0x06);
				writeC(0x08);
				writeC(0x01);
				writeC(0x10);
				writeC(0x32);
				writeC(0x18);
				writeC(0x32);
				writeC(0x28);
				writeC(0x01);
				break;
				
			case ����Ʈ�ε�:
			case ����Ʈ�Ϸ�:
				writeC(0x02);
				writeC(0x08);
				writeC(0x00);
				writeC(0x10);
				writeC(Ÿ��);
				writeC(0x02);
		}
		writeH(0);
	}

	private void buildPacket(int skillId, long time) {
		int[] p = new int[3];
		switch (skillId) {
		case L1SkillId.�ۼַ�Ʈ���̵�:
			// 92 08 14984 9471
			p[0] = 92;
			p[1] = 14984;
			p[2] = 9471;
			break;
		case L1SkillId.�ҿ�踮��:
			// 391 240 14989 9600
			p[0] = 391;
			p[1] = 14987;
			p[2] = 9600;
			break;
		case L1SkillId.����¡:
			// 487 4832 15012 9604
			p[0] = 487;
			p[1] = 15012;
			p[2] = 9604;
			break;
		case L1SkillId.������:
			// 73 8 14991 9601
			p[0] = 73;
			p[1] = 14991;
			p[2] = 9601;
			break;
		case L1SkillId.��Ʈ����:
			// 452 30 15002 9603
			p[0] = 452;
			p[1] = 15002;
			p[2] = 9603;
			break;

		case L1SkillId.�׷��̽��ƹ�Ÿ:
			// 122 15 14979 9470
			p[0] = 122;
			p[1] = 14979;
			p[2] = 9470;
			break;
		/*case L1SkillId.��ؽ�:
			// 489 15 14996 9602
			p[0] = 489;
			p[1] = 14996;
			p[2] = 9602;
			break;*/
		/*case L1SkillId.����¡���Ǹ���:
			// 692 03 14999 9614
			p[0] = 692;
			p[1] = 14999;
			p[2] = 9614;
			break;*/
		case L1SkillId.����Ʈ:
			// 478 15 15008 9625
			p[0] = 478;
			p[1] = 15008;
			p[2] = 9625;
			break;

		}
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(N_SHOP_BUFF);
		writeC(0x08);
		writeC(0x02);

		writeC(0x10);
		if (p[0] <= 255)
			writeBit(p[0]);
		else
			writeH(p[0]);

		writeC(0x18);
		writeBit(time);

		writeC(0x20);
		writeC(0x09);

		writeC(0x28);
		writeH(p[1]);

		writeC(0x30);
		writeC(0x00);
		// if(skillId == L1SkillId.����¡���Ǹ�){
		// writeC(0x38);
		// writeC(0x00);
		// }

		writeC(0x40);
		writeH(p[2]);

		writeC(0x48);
		writeC(0x00);
		writeC(0x50);
		writeC(0x00);
		writeC(0x58);
		writeC(0x01);
		writeC(0x00);
		writeC(0x00);
	}
	
	/** �δ� �ý��ۿ� ���� �޼��� ��ũ�� ���� ���� */
	public static final int MESSAGE_BOX = 0x0244;
	
	public static final	int MESSAGE_OrimImage = 22906;
	public static final	int[] MESSAGE_OrimOpen = { 1996, 1997, 1998, 1999, 2000};
	public static final int[] MESSAGE_OrimEnd = { 2001, 2002, 2003, 2004};
	
	public S_CreateItem(boolean Setting) {
		buildPacket(Setting);
	}
	
	private void buildPacket(boolean Setting) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(MESSAGE_BOX);
		
		if(Setting){
			for (int i = 0; i < MESSAGE_OrimOpen.length; i++) {
				writeC(0x0a);
				writeC(5 + size7B(MESSAGE_OrimImage) + size7B(MESSAGE_OrimOpen[i]));
				
				writeC(0x08);
				writeBit(MESSAGE_OrimImage);
				
				writeC(0x10);
				writeBit(MESSAGE_OrimOpen[i]);
				
				writeC(0x20);
				writeBit(6000);
			}
		}else{
			for (int i = 0; i < MESSAGE_OrimEnd.length; i++) {
				writeC(0x0a);
				writeC(5 + size7B(MESSAGE_OrimImage) + size7B(MESSAGE_OrimEnd[i]));
				
				writeC(0x08);
				writeBit(MESSAGE_OrimImage);
				
				writeC(0x10);
				writeBit(MESSAGE_OrimEnd[i]);
				
				writeC(0x20);
				writeBit(6000);
			}
		}
		
		writeH(0x00);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_CreateItem;
	}
}

/*
 * 13 39 00 08 00 12 07 08 e3 0d 10 00 18 00 12 07 08 e4 0d 10 00 18 00 12 07 08
 * e5 0d 10 00 18 00 12 07 08 e6 0d 10 00 18 00 12 07 08 e7 0d 10 00 18 00 12 07
 * 08 e8 0d 10 00 18 00 12 07 08 e9 0d 10 00 18 00 12 07 08 ea 0d 10 00 18 00 00
 * 00
 */