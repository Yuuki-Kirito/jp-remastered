package manager.dialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.SQLUtil;
import manager.LinAllManager;

public class DropMonsterFind extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text_1;
	public static Display display;

	private static String title = "ドロップモンスターの検索";

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param style
	 */
	public DropMonsterFind(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 *
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {

		shell = new Shell(getParent(), getStyle());
		shell.setSize(270, 351);
		shell.setText(title);
		// 画面の中央へ
		display = Display.getDefault();
		shell.setBounds((display.getBounds().width / 2) - (shell.getBounds().width / 2),
				(display.getBounds().height / 2) - (shell.getBounds().height / 2), shell.getBounds().width,
				shell.getBounds().height);
		GridLayout gl_shell = new GridLayout(3, false);
		gl_shell.horizontalSpacing = 10;
		gl_shell.marginWidth = 10;
		gl_shell.marginHeight = 10;
		shell.setLayout(gl_shell);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setText("検索名");

		text_1 = new Text(shell, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_1.setEditable(true);

		Button lblNewButton = new Button(shell, SWT.PUSH);
		lblNewButton.setText("検索");

		List list = new List(shell, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE);
		GridData gd_list = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		list.setTouchEnabled(true);
		list.setItems(new String[] {});
		gd_list.heightHint = 272;
		list.setLayoutData(gd_list);

		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MouseDoubleClick:
					String _selectName = list.getItem(list.getSelectionIndex());
					String _name = _selectName.substring(_selectName.indexOf("[") + 1, _selectName.lastIndexOf("]"));
					L1Npc npc = NpcTable.getInstance().getTemplate(Integer.valueOf(_name));
					if (npc != null) {
						DropEdit.open(npc);
						close();
					} else {
						LinAllManager.toMessageBox(title, "存在しないエネミーです。");
						close();
					}
					break;
				}
			}
		};
		list.addListener(SWT.MouseDoubleClick, listener);

		// イベント登録
		text_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == 13 || e.keyCode == 16777296)
					// 検索
					toSearchItem(text_1, list);
			}
		});

		lblNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 検索
				toSearchItem(text_1, list);
			}
		});

		// モンスター情報の記録
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM npc WHERE impl like '%L1Monster%'");
			rs = pstm.executeQuery();
			while (rs.next()) {
				list.add("[" + rs.getInt("npcid") + "] " + rs.getString("name"));
			}

		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			SQLUtil.close(rs, pstm, con);
		}
	}

	private void close() {
		shell.dispose();
	}

	private static void toSearchItem(Text text, List list) {
		String name = text.getText().toLowerCase();

		// 前の履歴の削除
		list.removeAll();

		// 検索名がない場合はすべてをリスト化
		if (name == null || name.length() <= 0) {
			Connection con = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;

			try {
				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con.prepareStatement("SELECT * FROM npc WHERE impl like '%L1Monster%'");
				rs = pstm.executeQuery();
				while (rs.next()) {
					list.add("[" + rs.getInt("npcid") + "] " + rs.getString("name"));
				}

			} catch (SQLException e) {
				e.getStackTrace();
			} finally {
				SQLUtil.close(rs, pstm, con);
			}
			return;
		}

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM npc WHERE impl=? AND name like '%" + name + "%'");
			pstm.setString(1, "L1Monster");
			rs = pstm.executeQuery();
			while (rs.next()) {
				list.add("[" + rs.getInt("npcid") + "] " + rs.getString("name"));
			}

		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			SQLUtil.close(rs, pstm, con);
		}

		// 登録されてない場合
		if (list.getItemCount() <= 0)
			LinAllManager.toMessageBox(title, "一致するアイテムはありません。");

		// フォーカスをセット
		text.setFocus();
	}
}
