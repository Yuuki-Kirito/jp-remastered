package l1j.server.MJCTSystem;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
/** 
 * MJCTCharInfo
 * MJSoft Character TradeSystem - Character Info
 * made by mjsoft, 2016.
 **/
public class MJCTCharInfo {
	private static final SimpleDateFormat _dateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.KOREA);
	
	public String 		name;
	public int			level;
	public int			type;
	public int			einhasad;
	public Timestamp 	tamEndTime;
	public int			sex;
	public String		clanName;
	public int 			str;
	public int 			dex;
	public int 			con;
	public int 			wis;
	public int 			intel;
	public int 			cha;
	public int			elixir;
	public int			hp;
	public int			mp;
	public int			ac;
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(256);
		sb.append("�̸� : ").append(name).append("\n");
		sb.append("���� : ").append(level).append("\n");
		sb.append("Ŭ���� : ").append(toMoreClass()).append("\n");
		sb.append("���� : ").append(clanName).append("\n");
		sb.append("���� : ").append(einhasad).append("\n");		
		sb.append("STR : ").append(str).append("\n");
		sb.append("DEX : ").append(dex).append("\n");
		sb.append("CON : ").append(con).append("\n");
		sb.append("WIS : ").append(wis).append("\n");
		sb.append("INT : ").append(intel).append("\n");
		sb.append("CHA : ").append(cha).append("\n");
		sb.append("������ : ").append(elixir).append("��").append("\n");
		sb.append("HP : ").append(hp).append("\n");
		sb.append("MP : ").append(mp).append("\n");
		sb.append("AC : ").append(String.valueOf(ac)).append("\n");
		return sb.toString();
	}
	
	private String toMoreClass(){
		return String.format("%s(%s)", toClass(), sex == 0 ? "��" : "��");
	}
	
	private String toClass(){
		switch(type){
		case 0:
			return "����";
		case 1:
			return "���";
		case 2:
			return "����";
		case 3:
			return "����";
		case 4:
			return "��ũ����";
		case 5:
			return "����";
		case 6:
			return "ȯ����";
		case 7:
			return "����";
		case 8:
			return "�˻�";
		default:
			return "";
		}
	}
}
