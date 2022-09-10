package l1j.server.MJDShopSystem;

import java.sql.ResultSet;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1ShopItem;

/** 
 * MJDShopItem
 * Dynamic Shop Item.
 * made by mjsoft, 2016.
 *  **/
/** �� �����ۿ� ���� ������ ������. **/
public class MJDShopItem {
	public int 		objId;		// ������Ʈ ID
	public int 		itemId;		// ������ ID
	public String	name;
	public int 		invId;		// �κ��丮 ID
	public int 		count;		// ����
	public int 		price;		// ����
	public int		iden;		// �������� ����
	public int 		enchant;	// ��æƮ ��ġ
	public int 		attr;		// �Ӽ�
	public boolean 	isPurchase;	// �Ǹ�(false), ����(true)
	
	public static MJDShopItem create(L1ShopItem sitem, int i, boolean isPurchase){
		L1Item item			= ItemTable.getInstance().getTemplate(sitem.getItemId());
		MJDShopItem ditem	= new MJDShopItem();
		ditem.objId			= i;
		ditem.itemId		= sitem.getItemId();
		ditem.name			= item.getName();
		ditem.invId			= item.getGfxId();
		ditem.count			= sitem.getCount();
		ditem.price			= sitem.getPrice();
		ditem.iden			= item.getBless();
		ditem.enchant		= sitem.getEnchant();
		ditem.attr			= 0;
		ditem.isPurchase	= isPurchase;
		return ditem;
	}
	
	public static MJDShopItem create(L1ItemInstance item, int count, int price, boolean isPurchase){
		MJDShopItem ditem	= new MJDShopItem();
		ditem.objId			= item.getId();
		ditem.itemId		= item.getItemId();
		ditem.name			= item.getName();
		ditem.invId			= item.get_gfxid();
		ditem.count			= count;
		ditem.price			= price;
		if(!item.isIdentified())	ditem.iden	= -1;
		else						ditem.iden	= item.getBless();
		ditem.enchant		= item.getEnchantLevel();
		ditem.attr			= item.getAttrEnchantLevel();
		ditem.isPurchase	= isPurchase;
		return ditem;
	}
	
	public static MJDShopItem create(ResultSet rs) throws Exception{
		MJDShopItem ditem	= new MJDShopItem();
		ditem.objId 	= rs.getInt("item_objid");
		ditem.itemId	= rs.getInt("item_id");
		ditem.name		= rs.getString("Item_name");
		ditem.invId		= rs.getInt("invgfx");
		ditem.count		= rs.getInt("count");
		ditem.price		= rs.getInt("price");
		ditem.iden		= MJDShopStorage.getAppIden2PackIden(rs.getInt("iden"));
		ditem.enchant	= rs.getInt("enchant");
		ditem.attr		= rs.getInt("attr");
		ditem.isPurchase= rs.getBoolean("type");
		return ditem;
	}
}
