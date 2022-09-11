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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.templates.L1Drop;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.SQLUtil;
import manager.LinAllManager;
import manager.SWTResourceManager;

public class DropEdit {

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

	//移動する座標情報
	private static L1Npc npc;
	//
	private static Connection con;
	public static Display display;

//	private final static Logger _log = Logger.getLogger(CharacterSlotItemTable.class.getName());

	static {
		normal = SWTResourceManager.getFont("Arial", 9, SWT.NORMAL);
		select = SWTResourceManager.getFont("Arial", 9, SWT.BOLD);
		title = "ドロップアイテムの修正";
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	static public void open(L1Npc npc) {
		DropEdit.npc = npc;

		shell = new Shell(LinAllManager.shlInbumserverManager, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		shell.setSize(950, 400);
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
		label_step1.setText("アイテムの指定");

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
		//以前の内容をすべて削除します。
		for (Control c : composite_controller.getChildren())
			c.dispose();

		selectStep(1);

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
		button_4.setText("\r\n" +
				"	private static void step1() {\r\n" +
				"		//以前の内容をすべて削除します。");

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
		group.setText("ドロップ"); // 追加する場所
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
						list_1.setItem(select, list_1.getItem(move_idx));
						list_1.setItem(move_idx, temp);
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
				list_1.remove(list_1.getSelectionIndex());
			}
		});
		list_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (list_1.getSelectionCount() <= 0)
					return;
				// 削除
				if (e.keyCode == SWT.DEL)
					list_1.remove(list_1.getSelectionIndex());
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
					LinAllManager.toMessageBox("ドロップリストを追加してください。");
					return;
				}
				Map<Integer, Object> list = new HashMap<Integer, Object>();
				for (int i = list_1.getItemCount() - 1; i >= 0; --i)
					list.put(Integer.valueOf(i), list_1.getData(String.valueOf(i)));
				step2(list_1.getItems(), list);
			}
		});

		ArrayList<L1Drop> dropList = DropTable.getInstance().getDropList(npc.get_npcId());
		if (dropList != null) {
			for (L1Drop drop : dropList) {
				L1Item item = ItemTable.getInstance().getTemplate(drop.getItemid());
				if (item != null) {
					list_1.add(item.getName());
					list_1.setData(item.getName(), item);
				}
			}
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

		// 前の履歴を削除
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

		// 登録されていない場合はガイドを表示
		if (list.getItemCount() <= 0)
			LinAllManager.toMessageBox(title, "一致するアイテムはありません。");

		// フォーカスをtextに？
		text.setFocus();
	}

	private static void step2(String[] inv_list, Map<Integer, Object> list) {
		// 以前の内容をすべて削除。
		for (Control c : composite_controller.getChildren())
			c.dispose();

		selectStep(2);

		GridLayout gl_composite_controller = new GridLayout(2, false);
		gl_composite_controller.verticalSpacing = 0;
		gl_composite_controller.horizontalSpacing = 2;
		composite_controller.setLayout(gl_composite_controller);
		composite_controller.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final Button btnNpcshop = new Button(composite_controller, SWT.CHECK);
		btnNpcshop.setSelection(true);
		btnNpcshop.setText("monster_drop 情報の更新");
		btnNpcshop.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		final Table table = new Table(composite_controller, SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		TableColumn tblclmnUid = new TableColumn(table, SWT.NONE);
		tblclmnUid.setWidth(100);
		tblclmnUid.setText("mobId");

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("mobname");

		TableColumn tblclmnItemname = new TableColumn(table, SWT.NONE);
		tblclmnItemname.setWidth(100);
		tblclmnItemname.setText("moblevel");

		TableColumn tblclmnItemcount = new TableColumn(table, SWT.NONE);
		tblclmnItemcount.setWidth(100);
		tblclmnItemcount.setText("itemId");

		TableColumn tblclmnItembress = new TableColumn(table, SWT.NONE);
		tblclmnItembress.setWidth(100);
		tblclmnItembress.setText("itemname");

		TableColumn tblclmnItemenlevel = new TableColumn(table, SWT.NONE);
		tblclmnItemenlevel.setWidth(100);
		tblclmnItemenlevel.setText("min");

		TableColumn tblclmnItementime = new TableColumn(table, SWT.NONE);
		tblclmnItementime.setWidth(100);
		tblclmnItementime.setText("max");

		TableColumn tblclmnItemennote = new TableColumn(table, SWT.NONE);
		tblclmnItemennote.setWidth(100);
		tblclmnItemennote.setText("chance");

		Button button_3 = new Button(composite_controller, SWT.NONE);
		GridData gd_button_3 = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_button_3.widthHint = 100;
		button_3.setLayoutData(gd_button_3);
		button_3.setText("以前");

		Button button_5 = new Button(composite_controller, SWT.NONE);
		GridData gd_button_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_5.widthHint = 100;
		button_5.setLayoutData(gd_button_5);
		button_5.setText("次");

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
					for (int i = 3; i < table.getColumnCount(); i++) {
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
				// 前
				step1();
			}
		});
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 次
				step3(table, btnNpcshop.getSelection());
			}
		});

		for (String s : inv_list) {

			L1ItemInstance ii = ItemTable.getInstance().createItem(s);

			String[] field = new String[8];

			if (DropTable.getInstance().isDropListItem(npc.get_npcId(), ii.getItemId())) {
				L1Drop drop = DropTable.getInstance().getDrop(npc.get_npcId(), ii.getItemId());
				field[0] = String.valueOf(npc.get_npcId());
				field[1] = String.valueOf(npc.get_name());
				field[2] = String.valueOf(npc.get_level());
				field[3] = String.valueOf(drop.getItemid());
				field[4] = String.valueOf(ii.getName());
				field[5] = String.valueOf(drop.getMin());
				field[6] = String.valueOf(drop.getMax());
				field[7] = String.valueOf(drop.getChance());
			} else {
				field[0] = String.valueOf(npc.get_npcId());
				field[1] = String.valueOf(npc.get_name());
				field[2] = String.valueOf(npc.get_level());
				field[3] = String.valueOf(ii.getItemId());
				field[4] = String.valueOf(ii.getName());
				field[5] = String.valueOf(1);
				field[6] = String.valueOf(1);
				field[7] = String.valueOf(1000);
			}
			new TableItem(table, SWT.NONE).setText(field);
		}
		composite_controller.layout();
	}

	private static void step3(Table table, boolean db) {
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM droplist WHERE mobid=?");
			pstm.setInt(1, npc.get_npcId());
			pstm.executeUpdate();
			pstm.close();

			for (TableItem ti : table.getItems()) {
				pstm2 = con.prepareStatement(
						"INSERT INTO droplist SET mobId=?, mobname=? , moblevel=?, itemId=?, itemname=?, min=?, max=?, chance=?");
				pstm2.setInt(1, Integer.valueOf(ti.getText(0)));
				pstm2.setString(2, ti.getText(1));
				pstm2.setInt(3, Integer.valueOf(ti.getText(2)));
				pstm2.setInt(4, Integer.valueOf(ti.getText(3)));
				pstm2.setString(5, ti.getText(4));
				pstm2.setInt(6, Integer.valueOf(ti.getText(5)));
				pstm2.setInt(7, Integer.valueOf(ti.getText(6)));
				pstm2.setInt(8, Integer.valueOf(ti.getText(7)));
				pstm2.execute();
			}
		} catch (SQLException e) {
			//_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(con);
			SQLUtil.close(pstm);
			SQLUtil.close(pstm2);
		}
		DropTable.reload();

		// 以前の内容をすべて削除します。
		for (Control c : composite_controller.getChildren())
			c.dispose();

		selectStep(3);

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
				// 次
				shell.dispose();
			}
		});

		// 処理 2.
		list_2.add("更新完了");

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
}
