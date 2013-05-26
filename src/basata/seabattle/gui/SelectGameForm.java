package basata.seabattle.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;



public class SelectGameForm extends JFrame {
	
	public final static int ID_COLUMN = 0;
	public final static int NAME_COLUMN = 1;
	
	private JTable table;

	public SelectGameForm() {
		
		setTitle("BASATA SEA BATTLE");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(500, 500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (500 / 2); // Center horizontally.
		int Y = (screen.height / 2) - (500 / 2); // Center vertically.

		setLocation(X, Y);
		getContentPane().setLayout(null);
		setResizable(false);

		
		addComponents();
		
		
		this.update(getGraphics());
		this.setVisible(true);
		
	}
	
	
	private void addComponents() {
		
		
		createTable();
		
	}
	
	
	private void createTable(){
		ArrayList<String> names = new ArrayList<>();
		names.add("=WINNER=");
		names.add("Join me!");
		names.add("Arnold");
		
		
		Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		if (!names.isEmpty()){
			for(int i = 0; i < names.size(); i++){
				Object[] data = { i, names.get(i) };
				Vector<Object> colData = new Vector<Object>(Arrays.asList(data));
				rowData.add(colData);
				
			}
		}
		
		String[] columnNames = { "ID", "Creator"};
		Vector<String> columnNamesV = new Vector<String>(Arrays.asList(columnNames));
		
		table = new JTable(rowData, columnNamesV) {
			@Override
			// Set cells not editable
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table =(JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				int col = table.columnAtPoint(p);
				if ( me.getClickCount() ==2 ) {
					System.out.println(" row " + row + " column " + col);
				
				}
			}
		});
		
		table.setRowHeight(35);

		table.setShowVerticalLines(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setFocusable(false);
		
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setFillsViewportHeight(true);
		table.setBounds(50, 50, 400, 300);
		table.setAutoCreateRowSorter(true);

		// Make the table vertically scrollable
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 50, 400, 300);
		this.add(scrollPane);
		
		
	}
	
	
	
	public static void main(String[] args) {
		new SelectGameForm();
	}
	

}
