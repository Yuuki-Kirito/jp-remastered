package manager.dialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;
import manager.LinAllManager;
import manager.SWTResourceManager;

public class PlayerInventory {

	private static Shell shell;

	// 各スタッフごとに変更される部分
	private static Composite composite_controller;

	// 左ボックスに表示されるラベル
	private static Label label_step1;
	private static Label label_step2;
	private static Label label_step3;

	// 左ボックスに表示される文字フォント情報
	private static Font normal;
	private static Font select;

	//そのウィンドウのタイトル名
	private static String title;
	//
	private static Connection con;
	//
	private static L1PcInstance pc;

	public static Display display;

	static {
		normal = SWTResourceManager.getFont("Arial", 9, SWT.NORMAL);
		select = SWTResourceManager.getFont("Arial", 9, SWT.BOLD);
		title = "ユーザーインベントリ";
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	static public void open(L1PcInstance pc) {
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
		} catch (Exception e) {
		}

		PlayerInventory.pc = pc;

		// 画面中央へ
		shell = new Shell(LinAllManager.shlInbumserverManager, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		shell.setSize(640, 480);
		shell.setText(title);
		display = Display.getDefault();
		shell.setBounds((display.getBounds().width / 2) - (shell.getBounds().width / 2),
				(display.getBounds().height / 2) - (shell.getBounds().height / 2), shell.getBounds().width,
				shell.getBounds().height);
		GridLayout gl_shell = new GridLayout(2, false);
		gl_shell.horizontalSpacing = 2;
		gl_shell.verticalSpacing = 0;
		gl_shell.marginHeight = 0;
		gl_shell.marginWidth = 0;
		shell.setLayout(gl_shell);

		Composite composite_status = new Composite(shell, SWT.NONE);
		composite_status.setLayout(new GridLayout(1, false));
		composite_status.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		label_step1 = new Label(composite_status, SWT.NONE);
		label_step1.setText("アイテムの表示");

		label_step2 = new Label(composite_status, SWT.NONE);
		label_step2.setText("情報の修正");

		label_step3 = new Label(composite_status, SWT.NONE);
		label_step3.setText("完了");

		composite_controller = new Composite(shell, SWT.NONE);

		step1();

		shell.open();
		shell.layout();
		while (!LinAllManager.shlInbumserverManager.isDisposed()) {
			if (!LinAllManager.display.readAndDispatch())
				LinAllManager.display.sleep();
		}

		composite_controller.dispose();
		label_step3.dispose();
		label_step2.dispose();
		label_step1.dispose();
		composite_status.dispose();

		SQLUtil.close(con);

	}

	private static void step1() {
		// 以前の内容をすべて削除します。
		for (Control c : composite_controller.getChildren())
			c.dispose();

		selectStep(1);
		if (checkBug())
			return;

		GridLayout gl_composite_controller = new GridLayout(3, false);
		gl_composite_controller.verticalSpacing = 0;
		gl_composite_controller.horizontalSpacing = 2;
		composite_controller.setLayout(gl_composite_controller);
		composite_controller.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite composite_1 = new Composite(composite_controller, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(2, false);
		gl_composite_1.verticalSpacing = 0;
		gl_composite_1.horizontalSpacing = 2;
		gl_composite_1.marginHeight = 0;
		gl_composite_1.marginWidth = 0;
		composite_1.setLayout(gl_composite_1);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		final Text text = new Text(composite_1, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button_4 = new Button(composite_1, SWT.NONE);
		button_4.setText("検索");

		Group group_1 = new Group(composite_controller, SWT.NONE);
		group_1.setText("アイテム");
		GridLayout gl_group_1 = new GridLayout(1, false);
		gl_group_1.verticalSpacing = 0;
		gl_group_1.horizontalSpacing = 0;
		gl_group_1.marginHeight = 0;
		gl_group_1.marginWidth = 0;
		group_1.setLayout(gl_group_1);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		final List list = new List(group_1, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_list.widthHint = 100;
		list.setLayoutData(gd_list);

		DragSource dragSource = new DragSource(list, DND.DROP_MOVE);
		dragSource.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		new Label(composite_controller, SWT.NONE);

		Group group = new Group(composite_controller, SWT.NONE);
		group.setText("在庫");
		GridLayout gl_group = new GridLayout(1, false);
		gl_group.verticalSpacing = 0;
		gl_group.horizontalSpacing = 0;
		gl_group.marginHeight = 0;
		gl_group.marginWidth = 0;
		group.setLayout(gl_group);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		final List list_1 = new List(group, SWT.BORDER | SWT.V_SCROLL);
		list_1.setData("down", false);
		GridData gd_list_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_list_1.widthHint = 100;
		list_1.setLayoutData(gd_list_1);

		DropTarget dropTarget = new DropTarget(list_1, DND.DROP_MOVE);
		dropTarget.setTransfer(new Transfer[] { TextTransfer.getInstance() });

		Button button_1 = new Button(composite_controller, SWT.NONE);
		button_1.setToolTipText("追加");
		button_1.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, true, 1, 1));
		button_1.setText("->");

		Button button_2 = new Button(composite_controller, SWT.NONE);
		button_2.setToolTipText("削除");
		button_2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		button_2.setText("<-");
		new Label(composite_controller, SWT.NONE);
		new Label(composite_controller, SWT.NONE);

		Button button = new Button(composite_controller, SWT.NONE);
		GridData gd_button = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 100;
		button.setLayoutData(gd_button);
		button.setText("次へ");

		// イベント登録
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296)
					// 検索
					toSearchItem(text, list);
			}
		});
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 検索
				toSearchItem(text, list);
			}
		});
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				list_1.setData("down", true);
				list_1.setData("select", list_1.getSelectionIndex());
			}

			@Override
			public void mouseUp(MouseEvent e) {
				list_1.setData("down", false);
			}
		});
		list_1.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				Boolean drag = (Boolean) list_1.getData("down");
				if (drag) {
					int select = (Integer) list_1.getData("select");
					int move_idx = list_1.getSelectionIndex();
					if (select != move_idx) {
						// 位置変更
						String temp = list_1.getItem(select);
						Object temp_o = list_1.getData(String.valueOf(select));
						list_1.setItem(select, list_1.getItem(move_idx));
						list_1.setData(String.valueOf(select), list_1.getData(String.valueOf(move_idx)));
						list_1.setItem(move_idx, temp);
						list_1.setData(String.valueOf(move_idx), temp_o);
						// 情報の変更
						list_1.setData("select", move_idx);
						list_1.select(move_idx);
					}
				}
			}
		});
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionCount() <= 0)
					return;
				// 追加
				for (String name : list.getSelection())
					list_1.add(name);
				list_1.setTopIndex(list_1.getVerticalBar().getMaximum());
			}
		});
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list_1.getSelectionCount() <= 0)
					return;
				// 削除
				int select = list_1.getSelectionIndex();
				list_1.setData(String.valueOf(select), null);
				list_1.remove(select);

				// 更新
				for (int i = select; i < list_1.getItemCount(); ++i) {
					// 前の名前の抽出
					Object o = list_1.getData(String.valueOf(i + 1));
					list_1.setData(String.valueOf(i), o);
				}
			}
		});
		list_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (list_1.getSelectionCount() <= 0)
					return;
				// 削除
				if (e.keyCode == SWT.DEL) {
					int select = list_1.getSelectionIndex();
					list_1.setData(String.valueOf(select), null);
					list_1.remove(select);

					// 更新
					for (int i = select; i < list_1.getItemCount(); ++i) {
						// 前の名前の抽出
						Object o = list_1.getData(String.valueOf(i + 1));
						list_1.setData(String.valueOf(i), o);
					}
				}
			}
		});
		dragSource.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = list.getSelection()[0];
			}
		});
		dropTarget.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (event.data instanceof String) {
					list_1.add((String) event.data);
					list_1.setTopIndex(list_1.getVerticalBar().getMaximum());
				}
			}
		});
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list_1.getItemCount() == 0) {
					LinAllManager.toMessageBox("在庫リストにアイテムを追加してください。");
					return;
				}
				Map<Integer, Object> list = new HashMap<Integer, Object>();
				for (int i = list_1.getItemCount() - 1; i >= 0; --i)
					list.put(Integer.valueOf(i), list_1.getData(String.valueOf(i)));
				step2(list_1.getItems(), list);
			}
		});

		try {
			int idx = 0;
			for (L1ItemInstance ii : pc.getInventory().getItems()) {
				list_1.add(ii.getItem().getName());
				list_1.setData(String.valueOf(idx++), ii);
			}
		} catch (Exception e) {

		}

		composite_controller.layout();
	}

	/**
	 * アイテム検索
	 *
	 * @param text
	 * @param list
	 */
	private static void toSearchItem(Text text, List list) {
		String name = text.getText().toLowerCase();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		// 以前の履歴を削除
		list.removeAll();

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM etcitem WHERE name like '%" + name + "%'");
			rs = pstm.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("name"));
			}

		} catch (SQLException e) {

		} finally {
			SQLUtil.close(rs, pstm, con);
		}

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM weapon WHERE name like '%" + name + "%'");
			rs = pstm.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("name"));
			}

		} catch (SQLException e) {

		} finally {
			SQLUtil.close(rs, pstm, con);
		}

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM armor WHERE name like '%" + name + "%'");
			rs = pstm.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (SQLException e) {

		} finally {
			SQLUtil.close(rs, pstm, con);
		}

		// 登録されていない場合はガイド
		if (list.getItemCount() <= 0)
			LinAllManager.toMessageBox(title, "一致するアイテムはありません。");

		// フォーカスをテキストに
		text.setFocus();
	}

	private static void step2(String[] inv_list, Map<Integer, Object> list) {
		// 以前の履歴を削除
		for (Control c : composite_controller.getChildren())
			c.dispose();

		selectStep(2);
		if (checkBug())
			return;

		GridLayout gl_composite_controller = new GridLayout(2, false);
		gl_composite_controller.verticalSpacing = 0;
		gl_composite_controller.horizontalSpacing = 2;
		composite_controller.setLayout(gl_composite_controller);
		composite_controller.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final Table table = new Table(composite_controller, SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		TableColumn tblclmnUid = new TableColumn(table, SWT.NONE);
		tblclmnUid.setWidth(40);
		tblclmnUid.setText("itemid");

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(65);
		tblclmnName.setText("cha_objId");

		TableColumn tblclmnItemname = new TableColumn(table, SWT.NONE);
		tblclmnItemname.setWidth(70);
		tblclmnItemname.setText("cha_name");

		TableColumn tblclmnItemcount = new TableColumn(table, SWT.NONE);
		tblclmnItemcount.setWidth(50);
		tblclmnItemcount.setText("name");

		TableColumn tblclmnItembress = new TableColumn(table, SWT.NONE);
		tblclmnItembress.setWidth(50);
		tblclmnItembress.setText("count");

		TableColumn tblclmnItemenlevel = new TableColumn(table, SWT.NONE);
		tblclmnItemenlevel.setWidth(60);
		tblclmnItemenlevel.setText("quantity");

		TableColumn tblclmnSell = new TableColumn(table, SWT.NONE);
		tblclmnSell.setWidth(30);
		tblclmnSell.setText("en");

		TableColumn tblclmnPrice = new TableColumn(table, SWT.NONE);
		tblclmnPrice.setWidth(40);
		tblclmnPrice.setText("bress");

		TableColumn tblclmnObj = new TableColumn(table, SWT.NONE);
		tblclmnObj.setWidth(40);
		tblclmnObj.setText("itemobjid");

		Button button_3 = new Button(composite_controller, SWT.NONE);
		GridData gd_button_3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_button_3.widthHint = 100;
		button_3.setLayoutData(gd_button_3);
		button_3.setText("以前");

		Button button_5 = new Button(composite_controller, SWT.NONE);
		GridData gd_button_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_5.widthHint = 100;
		button_5.setLayoutData(gd_button_5);
		button_5.setText("次へ");

		// イベント登録
		table.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					boolean visible = false;
					final TableItem item = table.getItem(index);
					for (int i = 4; i < table.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final Text text = new Text(table, SWT.NONE);
							Listener textListener = new Listener() {
								@Override
								public void handleEvent(final Event e) {
									switch (e.type) {
									case SWT.FocusOut:
										item.setText(column, text.getText());
										text.dispose();
										break;
									case SWT.Traverse:
										switch (e.detail) {
										case SWT.TRAVERSE_RETURN:
											item.setText(column, text.getText());
										case SWT.TRAVERSE_ESCAPE:
											text.dispose();
											e.doit = false;
										}
										break;
									}
								}
							};
							text.addListener(SWT.FocusOut, textListener);
							text.addListener(SWT.Traverse, textListener);
							editor.setEditor(text, item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							return;
						}
						if (!visible && rect.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible)
						return;
					index++;
				}
			}
		});
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 前へ
				step1();
			}
		});
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 次
				step3(table);
			}
		});
		// 情報の更新
		int idx = 0;
		for (String s : inv_list) {
			Object o = list.get(idx);
			L1ItemInstance ii = o == null ? null : (L1ItemInstance) o;

			if (ii == null) {
				ii = ItemTable.getInstance().createItem(inv_list[idx]);
			}

			if (ii != null && ii.getId() == 0)
				continue;

			String[] field = new String[9];
			field[0] = String.valueOf(ii.getItem().getItemId());
			field[1] = String.valueOf(pc.getId());
			field[2] = pc.getName();
			field[3] = s;
			field[4] = String.valueOf(ii == null ? 1 : ii.getCount());
			field[5] = String.valueOf(ii == null ? 0 : ii.getChargeCount());
			field[6] = String.valueOf(ii == null ? 0 : ii.getEnchantLevel());
			field[7] = String.valueOf(ii == null ? 1 : ii.getBless());
			field[8] = String.valueOf(ii.getId());
			idx++;
			new TableItem(table, SWT.NONE).setText(field);
		}
		composite_controller.layout();
	}

	private static void step3(Table table) {
		selectStep(3);
		if (checkBug())
			return;

		// 削除されたアイテムの抽出
		java.util.List<L1ItemInstance> list_remove = new ArrayList<L1ItemInstance>();
		for (L1ItemInstance ii : pc.getInventory().getItems()) {
			L1ItemInstance find_ii = null;
			// 処理リストで見てください？
			for (TableItem ti : table.getItems()) {
				if (ii.getId() == Integer.valueOf(ti.getText(8))) {
					find_ii = ii;
					break;
				}
			}
			//見つからない場合は、現在のアイテム削除リストに登録。
			if (find_ii == null)
				list_remove.add(ii);
		}
		// アイテム削除処理
		for (L1ItemInstance ii : list_remove) {
			pc.getInventory().removeItem(ii);
		}
		// 情報の修正と新しいアイテムの追加
		for (TableItem ti : table.getItems()) {
			int item_id = Integer.valueOf(ti.getText(0));
			int count = Integer.valueOf(ti.getText(4));
			int quantity = Integer.valueOf(ti.getText(5));
			int en = Integer.valueOf(ti.getText(6));
			int bress = Integer.valueOf(ti.getText(7));
			int item_objectid = Integer.valueOf(ti.getText(8));

			L1ItemInstance cc = pc.getInventory().findItemObjId(item_objectid);
			if (cc != null) {
				cc.setCount(count);
				cc.setChargeCount(quantity);
				cc.setEnchantLevel(en);
				cc.setBless(bress);
				cc.setIdentified(cc.isIdentified());
				pc.sendPackets(new S_SystemMessage("オペレーターが" + cc.getName() + "のアイテム情報を変更しました。"));
				pc.getInventory().updateItem(cc);
			} else {
				L1ItemInstance ii = ItemTable.getInstance().createItem(item_id);
				ii.setCount(count);
				ii.setChargeCount(quantity);
				ii.setEnchantLevel(en);
				ii.setBless(bress);
				ii.setId(item_objectid);
				ii.setIdentified(true);
				L1ItemInstance giveItem = pc.getInventory().storeItem(ii);
				pc.sendPackets(new S_SystemMessage(
						"オペレーターは" + giveItem.getName() + "(" + giveItem.getCount() + ") を付与しました。"));
			}
		}

		// 前の内容をすべて削除。
		for (Control c : composite_controller.getChildren())
			c.dispose();

		GridLayout gl_composite_controller = new GridLayout(1, false);
		gl_composite_controller.verticalSpacing = 0;
		gl_composite_controller.horizontalSpacing = 2;
		composite_controller.setLayout(gl_composite_controller);
		composite_controller.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		List list_2 = new List(composite_controller, SWT.BORDER | SWT.V_SCROLL);
		list_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Button button_6 = new Button(composite_controller, SWT.NONE);
		GridData gd_button_6 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_button_6.widthHint = 100;
		button_6.setLayoutData(gd_button_6);
		button_6.setText("完了");

		// イベント登録
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 次へ
				shell.dispose();
			}
		});

		// 処理2
		list_2.add("メモリ更新完了");

		composite_controller.layout();
	}

	/**
	 * スタッフ?に合わせて左の文字フォントを変更する。
	 *
	 * @param step
	 */
	private static void selectStep(int step) {
		label_step1.setForeground(step == 1 ? SWTResourceManager.getColor(SWT.COLOR_DARK_RED)
				: SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_step2.setForeground(step == 2 ? SWTResourceManager.getColor(SWT.COLOR_DARK_RED)
				: SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_step3.setForeground(step == 3 ? SWTResourceManager.getColor(SWT.COLOR_DARK_RED)
				: SWTResourceManager.getColor(SWT.COLOR_BLACK));

		label_step1.setFont(step == 1 ? select : normal);
		label_step2.setFont(step == 2 ? select : normal);
		label_step3.setFont(step == 3 ? select : normal);
	}

	private static boolean checkBug() {
		// デバッグ用
		L1PcInstance target = L1World.getInstance().getPlayer(pc.getName());
		if (target == null) {
			MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("選択されたユーザーがいません。");
			messageBox.open();
			return true;
		}

		if (pc.getOnlineStatus() == 0) {
			MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("選択されたユーザーがいません。");
			messageBox.open();
			return true;
		}
		return false;
	}
}
