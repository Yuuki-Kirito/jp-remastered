package l1j.server.MJCTSystem;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
/** 
 * MJCTCharInfo
 * MJSoft Character TradeSystem - Character Info
 * made by わんわん, 2016.
 **/
public class MJCTCharInfo {
	private static final SimpleDateFormat _dateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.JAPAN);
	
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
		sb.append("name : ").append(name).append("\n");
		sb.append("level : ").append(level).append("\n");
		sb.append("class : ").append(toMoreClass()).append("\n");
		sb.append("clanName : ").append(clanName).append("\n");
		sb.append("Ain : ").append(einhasad).append("\n");		
		sb.append("STR : ").append(str).append("\n");
		sb.append("DEX : ").append(dex).append("\n");
		sb.append("CON : ").append(con).append("\n");
		sb.append("WIS : ").append(wis).append("\n");
		sb.append("INT : ").append(intel).append("\n");
		sb.append("CHA : ").append(cha).append("\n");
		sb.append("Elixir : ").append(elixir).append("개").append("\n");
		sb.append("HP : ").append(hp).append("\n");
		sb.append("MP : ").append(mp).append("\n");
		sb.append("AC : ").append(String.valueOf(ac)).append("\n");
		return sb.toString();
	}
	
	private String toMoreClass(){
		return String.format("%s(%s)", toClass(), sex == 0 ? "남" : "여");
	}
	
	private String toClass(){
		switch(type){
		case 0:
			return "crown";
		case 1:
			return "Knight";
		case 2:
			return "ELF";
		case 3:
			return "wizard";
		case 4:
			return "Dark Elf";
		case 5:
			return "Dragon Knight";
		case 6:
			return "Illusionist";
		case 7:
			return "Warrior";
		case 8:
			return "test"; //검사
		default:
			return "";
		}
	}
}
