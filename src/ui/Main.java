package ui;

import internal.ImportData;
import internal.Randomize;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Main {

	private JFrame frame;
	private JTable timeTable;
	private static ArrayList<internal.Class> classList;
	private static ArrayList<internal.Class> selectedList;
	private static ArrayList<String> departmentList;
	private static JComboBox comboBox;
	private static DefaultListModel<String> listModel;
	private JScrollPane scrollPane_selectTable;
	private JScrollPane scrollPane_timeTable;
	private JLabel label_1;
	private static Object[][] tableArray;
	private static JTable selectTable;
	private static SelectTableModel selectTableModel;
	private static DefaultTableModel timeTableModel;
	private JScrollPane scrollPane_selectedTable;
	private JScrollPane scrollPane_selectedTable_1;
	private JTable selectedTable;
	private static DefaultTableModel selectedTableModel;
	private JTable table_1;
	private JLabel lblNewLabel;
	private JTable magicTable;
	private static DefaultTableModel magicTableModel;
	private static ArrayList<ArrayList<internal.Class>> magicList;
	private static ArrayList<internal.Class> checkedList;
	private JLabel lblAk;

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException, URISyntaxException {
		checkedList = new ArrayList<internal.Class>();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ImportData importData = new ImportData();
		classList = importData.getClassList();
		departmentList = new ArrayList<String>();
		departmentList.add("수리정보과학부");
		departmentList.add("물리지구과학부");
		departmentList.add("화학생물학부");
		departmentList.add("인문예술학부");

	}

	public static boolean checkForCollision(ArrayList<internal.Class> selectedList, internal.Class newClass) {
		for (int i = 0; i < selectedList.size(); i++) {
			internal.Class nowClass = selectedList.get(i);
			for (int k = 0; k < nowClass.getClassTime().size(); k++) {
				for (int j = 0; j < newClass.getClassTime().size(); j++) {
					if (nowClass.getClassTime().get(k).equals(newClass.getClassTime().get(j)))
						return true;
				}
			}
		}
		return false;
	}

	public static void refreshMagicTable() {
		if (magicTableModel.getRowCount() > 0) {
			for (int i = magicTableModel.getRowCount() - 1; i > -1; i--) {
				magicTableModel.removeRow(i);
			}
		}

		int index = 1;

		for (ArrayList<internal.Class> selectedList : magicList) {
			int[] dayCost = getDayCost(selectedList);
			magicTableModel.addRow(new Object[] { index, dayCost[0] + "/" + dayCost[1], dayCost[2] + "/" + dayCost[3], dayCost[4] + "/" + dayCost[5],
					dayCost[6] + "/" + dayCost[7], dayCost[8] + "/" + dayCost[9], calculate1Class(selectedList) });
			index++;
		}
	}

	public static int[] getDayCost(ArrayList<internal.Class> selectedList) {
		int[] dayCost = new int[10];
		for (internal.Class selectedClass : selectedList) {
			for (String classTime : selectedClass.getClassTime()) {
				dayCost[(dayToInteger(classTime.substring(0, 1))-1) * 2 + getNoonValue(Integer.parseInt(classTime.substring(1)))]++;
			}
		}
		return dayCost;
	}

	public static int getNoonValue(int value) {
		if (value < 5)
			return 0;
		else
			return 1;
	}

	public static int calculate1Class(ArrayList<internal.Class> selectedList) {
		int check = 0;
		for (internal.Class selectedClass : selectedList) {
			for (String classTime : selectedClass.getClassTime()) {
				if (Integer.parseInt(classTime.substring(1)) == 1) {
					check++;
				}
			}
		}
		return check;
	}

	public static void refreshSelectTable() {
		switch (comboBox.getSelectedIndex()) {

		case 0:
			if (selectTableModel.getRowCount() > 0) {
				for (int i = selectTableModel.getRowCount() - 1; i > -1; i--) {
					selectTableModel.removeRow(i);
				}
			}
			selectTableModel.removeHightlight();
			for (int i = 0; i < classList.size(); i++) {

				internal.Class tempClass = classList.get(i);
				if (tempClass.getSubjectDepartment().equals("수리정보과학부") && !selectedList.contains(tempClass)) {
					selectTableModel.addRow(new String[] { tempClass.getSubjectName(), tempClass.getClassNumber() + "분반", tempClass.getTeacherName(),
							tempClass.getClassTimeText() });
					if (!checkForCollision(selectedList, tempClass)) {
						selectTableModel.setHighlight(selectTableModel.getRowCount() - 1);
					}

				}
			}

			break;
		case 1:
			if (selectTableModel.getRowCount() > 0) {
				for (int i = selectTableModel.getRowCount() - 1; i > -1; i--) {
					selectTableModel.removeRow(i);
				}
			}
			selectTableModel.removeHightlight();
			for (int i = 0; i < classList.size(); i++) {

				internal.Class tempClass = classList.get(i);
				if (tempClass.getSubjectDepartment().equals("물리지구과학부") && !selectedList.contains(tempClass)) {
					selectTableModel.addRow(new String[] { tempClass.getSubjectName(), tempClass.getClassNumber() + "분반", tempClass.getTeacherName(),
							tempClass.getClassTimeText() });
					if (!checkForCollision(selectedList, tempClass)) {
						selectTableModel.setHighlight(selectTableModel.getRowCount() - 1);
					}
				}
			}
			break;
		case 2:
			if (selectTableModel.getRowCount() > 0) {
				for (int i = selectTableModel.getRowCount() - 1; i > -1; i--) {
					selectTableModel.removeRow(i);
				}
			}
			selectTableModel.removeHightlight();
			for (int i = 0; i < classList.size(); i++) {

				internal.Class tempClass = classList.get(i);
				if (tempClass.getSubjectDepartment().equals("화학생물학부") && !selectedList.contains(tempClass)) {
					selectTableModel.addRow(new String[] { tempClass.getSubjectName(), tempClass.getClassNumber() + "분반", tempClass.getTeacherName(),
							tempClass.getClassTimeText() });
					if (!checkForCollision(selectedList, tempClass)) {
						selectTableModel.setHighlight(selectTableModel.getRowCount() - 1);
					}
				}
			}
			break;
		case 3:
			if (selectTableModel.getRowCount() > 0) {
				for (int i = selectTableModel.getRowCount() - 1; i > -1; i--) {
					selectTableModel.removeRow(i);
				}
			}
			selectTableModel.removeHightlight();
			for (int i = 0; i < classList.size(); i++) {

				internal.Class tempClass = classList.get(i);
				if (tempClass.getSubjectDepartment().equals("인문예술학부") && !selectedList.contains(tempClass)) {
					selectTableModel.addRow(new String[] { tempClass.getSubjectName(), tempClass.getClassNumber() + "분반", tempClass.getTeacherName(),
							tempClass.getClassTimeText() });
					if (!checkForCollision(selectedList, tempClass)) {
						selectTableModel.setHighlight(selectTableModel.getRowCount() - 1);
					}
				}
			}
			break;
		}

	}

	public static void refreshSelectedTable() {
		if (selectedTableModel.getRowCount() > 0) {
			for (int i = selectedTableModel.getRowCount() - 1; i > -1; i--) {
				selectedTableModel.removeRow(i);
			}
		}
		for (int i = 0; i < selectedList.size(); i++) {
			internal.Class tempClass = selectedList.get(i);
			selectedTableModel.addRow(new Object[] { checkedList.contains(tempClass), tempClass.getSubjectName(), tempClass.getClassNumber() + "분반",
					tempClass.getTeacherName(), tempClass.getClassTimeText() });
		}

	}

	public static internal.Class getClassFromInfo(String subjectName, String classNumber) {
		for (int i = 0; i < classList.size(); i++) {
			internal.Class tempClass = classList.get(i);
			if (subjectName.equals(tempClass.getSubjectName()) && Integer.parseInt(classNumber.substring(0, 1)) == tempClass.getClassNumber()) {
				return tempClass;
			}
		}
		return null;
	}

	public static void refreshTimeTable() {

		for (int i = 0; i < timeTableModel.getRowCount(); i++) {
			for (int j = 1; j < timeTableModel.getColumnCount(); j++) {
				timeTableModel.setValueAt(null, i, j);
			}
		}

		for (int i = 0; i < selectedList.size(); i++) {
			internal.Class tempClass = selectedList.get(i);
			for (int j = 0; j < tempClass.getClassTime().size(); j++) {
				String timeText = tempClass.getClassTime().get(j);
				timeTableModel.setValueAt(tempClass.getSubjectName(), Integer.parseInt(timeText.substring(1, timeText.length())) - 1,
						dayToInteger(timeText.substring(0, 1)));

			}

		}
	}

	public static void refreshScore(JLabel label) {
		int score = 0;
		for (int i = 0; i < selectedList.size(); i++) {
			internal.Class tempClass = selectedList.get(i);
			score += tempClass.getSubjectScore();
		}
		label.setText("총 학점 : " + score);
	}

	public static int dayToInteger(String day) {
		switch (day) {
		case "월":
			return 1;

		case "화":
			return 2;

		case "수":
			return 3;

		case "목":
			return 4;

		case "금":
			return 5;
		}
		return 0;
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		selectedList = new ArrayList<internal.Class>();
		frame = new JFrame();
		frame.setTitle("04해");
		frame.setBounds(100, 100, 1313, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		timeTableModel = new DefaultTableModel(13, 6) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		timeTableModel.setColumnCount(6);
		timeTableModel.setRowCount(13);
		System.out.println(timeTableModel.getRowCount());
		timeTableModel.setColumnIdentifiers(new String[] { "\uAD50\uC2DC", "\uC6D4", "\uD654", "\uC218", "\uBAA9", "\uAE08" });
		timeTable = new JTable();
		timeTable.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		timeTable.setFillsViewportHeight(true);
		timeTable.setModel(timeTableModel);
		timeTable.setValueAt("1교시", 0, 0);
		timeTable.setValueAt("2교시", 1, 0);
		timeTable.setValueAt("3교시", 2, 0);
		timeTable.setValueAt("4교시", 3, 0);
		timeTable.setValueAt("5교시", 4, 0);
		timeTable.setValueAt("6교시", 5, 0);
		timeTable.setValueAt("7교시", 6, 0);
		timeTable.setValueAt("8교시", 7, 0);
		timeTable.setValueAt("9교시", 8, 0);
		timeTable.setValueAt("10교시", 9, 0);
		timeTable.setValueAt("11교시", 10, 0);
		timeTable.setValueAt("12교시", 11, 0);
		timeTable.setValueAt("13교시", 12, 0);

		timeTable.getColumnModel().getColumn(0).setResizable(false);
		timeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		timeTable.getColumnModel().getColumn(1).setResizable(false);
		timeTable.getColumnModel().getColumn(2).setResizable(false);
		timeTable.getColumnModel().getColumn(3).setResizable(false);
		timeTable.getColumnModel().getColumn(4).setResizable(false);
		timeTable.getColumnModel().getColumn(5).setResizable(false);
		timeTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		timeTable.setBounds(12, 10, 600, 600);
		timeTable.setRowHeight(44);
		timeTable.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		timeTable.getTableHeader().setReorderingAllowed(false);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		timeTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		JLabel label = new JLabel("학부 선택");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label.setBounds(624, 10, 57, 15);
		frame.getContentPane().add(label);

		comboBox = new JComboBox();
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		comboBox.setBounds(624, 35, 150, 21);
		frame.getContentPane().add(comboBox);
		comboBox.addItem("수리정보과학부");
		comboBox.addItem("물리지구과학부");
		comboBox.addItem("화학생물학부");
		comboBox.addItem("인문예술학부");
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refreshSelectTable();
			}
		});

		listModel = new DefaultListModel<String>();

		// table_1_queue.add(empty);

		selectTableModel = new SelectTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}

		};
		selectTableModel.setColumnCount(4);
		selectTableModel.setColumnIdentifiers(new String[] { "\uACFC\uBAA9\uBA85", "\uBD84\uBC18", "\uAD50\uC0AC", "\uC2DC\uAC04" });

		scrollPane_timeTable = new JScrollPane();
		scrollPane_timeTable.setBounds(12, 10, 600, 600);
		scrollPane_timeTable.add(timeTable.getTableHeader());
		scrollPane_timeTable.setViewportView(timeTable);
		frame.getContentPane().add(scrollPane_timeTable);

		label_1 = new JLabel("등록된 과목");
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_1.setBounds(624, 295, 74, 15);
		frame.getContentPane().add(label_1);

		selectedTableModel = new DefaultTableModel(new String[] { "\uace0\uc815", "\uACFC\uBAA9\uBA85", "\uBD84\uBC18", "\uAD50\uC0AC",
				"\uC2DC\uAC04" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				if (column == 0)
					return true;
				return false;
			}
		};
		selectedTable = new JTable() {
			private static final long serialVersionUID = 1L;

			/*
			 * @Override public Class getColumnClass(int column) { return getValueAt(0, column).getClass(); }
			 */
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Boolean.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		selectedTable.setColumnSelectionAllowed(true);
		selectedTable.setCellSelectionEnabled(true);
		selectedTable.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		selectedTable.setModel(selectedTableModel);
		selectedTable.getColumnModel().getColumn(0).setResizable(false);
		selectedTable.getColumnModel().getColumn(1).setResizable(false);
		selectedTable.getColumnModel().getColumn(2).setResizable(false);
		selectedTable.getColumnModel().getColumn(3).setResizable(false);
		selectedTable.getColumnModel().getColumn(1).setResizable(false);
		selectedTable.getColumnModel().getColumn(2).setResizable(false);
		selectedTable.getColumnModel().getColumn(3).setResizable(false);
		selectedTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		selectedTable.getColumnModel().getColumn(1).setPreferredWidth(140);
		selectedTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		selectedTable.getColumnModel().getColumn(3).setPreferredWidth(80);
		selectedTable.getColumnModel().getColumn(4).setPreferredWidth(140);
		selectedTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		selectedTable.setBounds(624, 316, 448, 294);
		selectedTable.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		selectedTable.getTableHeader().setReorderingAllowed(false);
		selectedTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				int column = table.columnAtPoint(p);
				internal.Class clickedClass = getClassFromInfo((String) table.getValueAt(row, 1), (String) table.getValueAt(row, 2));

				if (me.getClickCount() == 2 && column > 0) {
					selectedList.remove(clickedClass);
					checkedList.remove(clickedClass);
					refreshSelectedTable();
					refreshSelectTable();
					refreshTimeTable();
					refreshScore(lblNewLabel);
				} else if (column == 0) {
					checkedList.add(clickedClass);
				}
			}
		});

		scrollPane_selectedTable = new JScrollPane();
		scrollPane_selectedTable.add(selectedTable.getTableHeader());
		scrollPane_selectedTable.setViewportView(selectedTable);
		scrollPane_selectedTable.setBounds(624, 316, 448, 261);
		frame.getContentPane().add(scrollPane_selectedTable);

		JPanel panel = new JPanel();
		panel.setBounds(624, 66, 448, 219);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		// scrollPane_selectTable = new JScrollPane();
		selectTable = new JTable();
		selectTable.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		selectTable.setModel(selectTableModel);
		selectTable.getColumnModel().getColumn(0).setResizable(false);
		selectTable.getColumnModel().getColumn(1).setResizable(false);
		selectTable.getColumnModel().getColumn(2).setResizable(false);
		selectTable.getColumnModel().getColumn(3).setResizable(false);
		selectTable.getColumnModel().getColumn(1).setResizable(false);
		selectTable.getColumnModel().getColumn(2).setResizable(false);
		selectTable.getColumnModel().getColumn(3).setResizable(false);
		selectTable.getColumnModel().getColumn(0).setPreferredWidth(140);
		selectTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		selectTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		selectTable.getColumnModel().getColumn(3).setPreferredWidth(140);
		selectTable.getColumnModel().getColumn(0).setCellRenderer(new SelectTableCellRenderer());
		selectTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		// selectTable.setBounds(624, 62, 348, 163);
		selectTable.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		selectTable.getTableHeader().setReorderingAllowed(false);
		selectTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					internal.Class clickedClass = getClassFromInfo((String) table.getValueAt(row, 0), (String) table.getValueAt(row, 1));
					selectedList.add(clickedClass);
					refreshSelectedTable();
					refreshSelectTable();
					refreshTimeTable();
					refreshScore(lblNewLabel);
				}
			}
		});
		panel.add(new JScrollPane(selectTable), BorderLayout.CENTER);
		panel.add(selectTable.getTableHeader(), BorderLayout.NORTH);

		lblNewLabel = new JLabel("총 학점 : 0");
		lblNewLabel.setBounds(998, 296, 74, 15);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(1084, 66, 201, 544);
		frame.getContentPane().add(scrollPane);

		magicTableModel = new DefaultTableModel(null, new String[] { "\uCC28\uB840", "\uC6D4", "\uD654", "\uC218", "\uBAA9", "\uAE08",
				"\u0031\uAD50\uC2DC" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		magicTable = new JTable();
		magicTable.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		magicTable.setModel(magicTableModel);
		magicTable.getColumnModel().getColumn(0).setResizable(false);
		magicTable.getColumnModel().getColumn(1).setResizable(false);
		magicTable.getColumnModel().getColumn(2).setResizable(false);
		magicTable.getColumnModel().getColumn(3).setResizable(false);
		magicTable.getColumnModel().getColumn(4).setResizable(false);
		magicTable.getColumnModel().getColumn(5).setResizable(false);
		magicTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		magicTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		magicTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		magicTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		magicTable.getColumnModel().getColumn(5).setPreferredWidth(40);
		magicTable.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		magicTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					selectedList = magicList.get(row);
					refreshSelectedTable();
					refreshSelectTable();
					refreshTimeTable();
					refreshScore(lblNewLabel);
				}
			}
		});
		scrollPane.setColumnHeaderView(magicTable.getTableHeader());
		scrollPane.setViewportView(magicTable);

		JButton btnMagicButton = new JButton("Do magic!");
		btnMagicButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btnMagicButton.setBounds(624, 587, 448, 23);
		frame.getContentPane().add(btnMagicButton);
		
		lblAk = new JLabel("마법 상자");
		lblAk.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblAk.setBounds(1084, 39, 57, 15);
		frame.getContentPane().add(lblAk);
		btnMagicButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				checkedList = new ArrayList<internal.Class>();
				for (int i = 0; i < selectedTableModel.getRowCount(); i++) {
					if ((boolean) selectedTableModel.getValueAt(i, 0)) {
						checkedList.add(selectedList.get(i));

					}
				}
				magicList = new Randomize(classList, selectedList, checkedList).getAvailable();
				refreshMagicTable();

			}
		});
		// scrollPane_selectTable.add(selectTable.getTableHeader());
		// scrollPane_selectTable.setViewportView(selectTable);

		refreshSelectTable();
	}

	static class SelectTableModel extends DefaultTableModel {
		ArrayList<Integer> highlightedRows = new ArrayList<Integer>();

		public void setHighlight(int row) {
			highlightedRows.add(row);
			fireTableRowsUpdated(row, row);
		}

		public void removeHightlight() {
			highlightedRows.clear();
		}
	}

	static class SelectTableCellRenderer extends DefaultTableCellRenderer {
		Color highlightColor = Color.BLUE;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			SelectTableModel model = (SelectTableModel) table.getModel();
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			c.setForeground(Color.GRAY);
			for (int i = 0; i < model.highlightedRows.size(); i++) {
				if (row == model.highlightedRows.get(i))
					c.setForeground(highlightColor);

			}
			return c;
		}

	}
}
