package manager.dialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;
import manager.LinAllManager;
import manager.SWTResourceManager;

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

public class PlayerInventory {

	static private Shell shell;
	// �� ���Ǹ��� ����� �κ�
	static private Composite composite_controller;
	// ���� �ڽ��� ǥ���� ��
	static private Label label_step1;
	static private Label label_step2;
	static private Label label_step3;
	// ���� �ڽ��� ǥ���� ���� ��Ʈ ����
	static private Font normal;
	static private Font select;
	// �ش� â�� Ÿ��Ʋ ��
	static private String title;
	//
	static private Connection con;
	//
	static private L1PcInstance pc;

	public static Display display;

	static {
		normal = SWTResourceManager.getFont("���� ���", 9, SWT.NORMAL);
		select = SWTResourceManager.getFont("���� ���", 9, SWT.BOLD);
		title = "����� �κ��丮";
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

		// ȭ���߾�����
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
		label_step1.setText("������ ����");

		label_step2 = new Label(composite_status, SWT.NONE);
		label_step2.setText("���� ����");

		label_step3 = new Label(composite_status, SWT.NONE);
		label_step3.setText("�Ϸ�");

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

	static private void step1() {
		// ���� ����� �� ����.
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
		button_4.setText("�˻�");

		Group group_1 = new Group(composite_controller, SWT.NONE);
		group_1.setText("������");
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
		group.setText("�κ��丮");
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
		button_1.setToolTipText("�߰�");
		button_1.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, true, 1, 1));
		button_1.setText("->");

		Button button_2 = new Button(composite_controller, SWT.NONE);
		button_2.setToolTipText("����");
		button_2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		button_2.setText("<-");
		new Label(composite_controller, SWT.NONE);
		new Label(composite_controller, SWT.NONE);

		Button button = new Button(composite_controller, SWT.NONE);
		GridData gd_button = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 100;
		button.setLayoutData(gd_button);
		button.setText("����");

		// �̺�Ʈ ���.
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296)
					// �˻�
					toSearchItem(text, list);
			}
		});
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// �˻�
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
						// ��ġ �ٲٱ�.
						String temp = list_1.getItem(select);
						Object temp_o = list_1.getData(String.valueOf(select));
						list_1.setItem(select, list_1.getItem(move_idx));
						list_1.setData(String.valueOf(select), list_1.getData(String.valueOf(move_idx)));
						list_1.setItem(move_idx, temp);
						list_1.setData(String.valueOf(move_idx), temp_o);
						// ���� ����.
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
				// �߰�
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
				// ����
				int select = list_1.getSelectionIndex();
				list_1.setData(String.valueOf(select), null);
				list_1.remove(select);

				// ����.
				for (int i = select; i < list_1.getItemCount(); ++i) {
					// �տ� �̸� ����.
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
				// ����
				if (e.keyCode == SWT.DEL) {
					int select = list_1.getSelectionIndex();
					list_1.setData(String.valueOf(select), null);
					list_1.remove(select);

					// ����.
					for (int i = select; i < list_1.getItemCount(); ++i) {
						// �տ� �̸� ����.
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
					LinAllManager.toMessageBox("�κ��丮��Ͽ� �������� �߰��Ͽ� �ֽʽÿ�.");
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
	 * ������ �˻�
	 * 
	 * @param text
	 * @param list
	 */
	static private void toSearchItem(Text text, List list) {
		String name = text.getText().toLowerCase();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		// ���� ��� ����
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

		// ��ϵȰ� ������� �ȳ� ��Ʈ.
		if (list.getItemCount() <= 0)
			LinAllManager.toMessageBox(title, "��ġ�ϴ� �������� �����ϴ�.");

		// ��Ŀ��.
		text.setFocus();
	}

	static private void step2(String[] inv_list, Map<Integer, Object> list) {
		// ���� ����� �� ����.
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
		button_3.setText("����");

		Button button_5 = new Button(composite_controller, SWT.NONE);
		GridData gd_button_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_5.widthHint = 100;
		button_5.setLayoutData(gd_button_5);
		button_5.setText("����");

		// �̺�Ʈ ���.
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
				// ����
				step1();
			}
		});
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// ����
				step3(table);
			}
		});
		// ���� ����
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

	static private void step3(Table table) {
		selectStep(3);
		if (checkBug())
			return;

		// ������ ������ ����.
		java.util.List<L1ItemInstance> list_remove = new ArrayList<L1ItemInstance>();
		for (L1ItemInstance ii : pc.getInventory().getItems()) {
			L1ItemInstance find_ii = null;
			// ó����Ͽ��� �ѷ�����.
			for (TableItem ti : table.getItems()) {
				if (ii.getId() == Integer.valueOf(ti.getText(8))) {
					find_ii = ii;
					break;
				}
			}
			// ��ã�Ҵٸ� ���� ������ ���Ÿ�Ͽ� ���.
			if (find_ii == null)
				list_remove.add(ii);
		}
		// ������ ���� ó��.
		for (L1ItemInstance ii : list_remove) {
			pc.getInventory().removeItem(ii);
		}
		// ���� ���� �� ���ο� ������ �߰�.
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
				pc.sendPackets(new S_SystemMessage("��ڴ��� " + cc.getName() + " ������ ������ �����Ͽ����ϴ�."));
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
						"��ڴ��� " + giveItem.getName() + "(" + giveItem.getCount() + ") ��(��) �����Ͽ����ϴ�."));
			}
		}

		// ���� ����� �� ����.
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
		button_6.setText("�Ϸ�");

		// �̺�Ʈ ���.
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// ����
				shell.dispose();
			}
		});

		// ó�� 2.
		list_2.add("�޸� ���� �Ϸ�.");

		composite_controller.layout();
	}

	/**
	 * ���ǿ� ���缭 ���� �۾� ��Ʈ �����ϱ�.
	 * 
	 * @param step
	 */
	static private void selectStep(int step) {
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

	static private boolean checkBug() {
		// ���� Ȯ��.
		L1PcInstance target = L1World.getInstance().getPlayer(pc.getName());
		if (target == null) {
			MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("���õ� ������ �����ϴ�.");
			messageBox.open();
			return true;
		}

		if (pc.getOnlineStatus() == 0) {
			MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("���õ� ������ �����ϴ�.");
			messageBox.open();
			return true;
		}
		return false;
	}
}
