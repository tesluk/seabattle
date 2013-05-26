package basata.seabattle.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import basata.seabattle.models.Field;
import basata.seabattle.models.Ship;

public class ShipPlaceForm extends JFrame {

	public int clickCount = 0;
	public int shipCount = 0;
	private Point lastPressed = new Point(0, 0);
	private Point currentPressed = new Point(0, 0);
	private HashMap<Integer, Integer> ships;
	private static final long serialVersionUID = -4465830825988397081L;
	public static final int fieldSize = 10;
	private JButton[][] buttonField;
	private Field gameField;
	private JPanel fieldPanel;
	private Ship[] shipsArray; 
	
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	
	private JButton startButton;

	public ShipPlaceForm() {

		setTitle("BASATA SEA BATTLE");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(500, 500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (500 / 2); // Center horizontally.
		int Y = (screen.height / 2) - (500 / 2); // Center vertically.

		setLocation(X, Y);
		getContentPane().setLayout(null);
		setResizable(false);

		gameField = new Field();
		ships = new HashMap<Integer, Integer>();
		shipsArray = new Ship[10];
		ships.put(1, 4);
		ships.put(2, 3);
		ships.put(3, 2);
		ships.put(4, 1);

		addComponents();
		
		this.setLabels();
		this.update(getGraphics());
		this.setVisible(true);
	}

	private class Action implements MouseListener {
		private ShipPlaceForm form;
		private Point p = new Point(0, 0);

		public Action(ShipPlaceForm form, Point p) {
			this.form = form;
			this.p = p;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

			//String mes = "Pressed " + Integer.toString(p.x) + " "
			//		+ Integer.toString(p.y);
			//System.out.println(mes);

			lastPressed = currentPressed;
			currentPressed = p;

			clickCount++;
			//System.out.println("Clicks: " + clickCount);

			if (shipCount == 10){
				System.out.println("=======OK===========");
				startButton.setEnabled(true);
				gameField.setShipsArray(shipsArray);
			}
			
			if ((clickCount % 2) == 0) {
				this.form.tryAddShip(lastPressed, currentPressed);
				//gameField.show();
				this.form.fillView();
			}
			
			

		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	private void addComponents() {

		fieldPanel = new JPanel();
		fieldPanel.setBounds(50, 50, 240, 240);

		this.addField();
		
		jLabel1 = new JLabel();
		jLabel1.setBounds(350, 70, 100, 20);
		
		jLabel2 = new JLabel();
		jLabel2.setBounds(350, 100, 100, 20);
		
		jLabel3 = new JLabel();
		jLabel3.setBounds(350, 130, 100, 20);
		
		jLabel4 = new JLabel();
		jLabel4.setBounds(350, 160, 100, 20);
		
		this.add(jLabel1);
		this.add(jLabel2);
		this.add(jLabel3);
		this.add(jLabel4);
		
		startButton = new JButton("START");
		startButton.setBounds(200, 350, 100, 35);
		startButton.setEnabled(false);
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Field oponent = new Field();
				oponent.SetShips();
				closeWnd();
				new GameForm2(gameField, oponent);
			}
		});
		
		this.add(startButton);

	}

	private void addField() {
		Border bor = BorderFactory.createBevelBorder(0);
		buttonField = new JButton[fieldSize][fieldSize];
		fieldPanel.setLayout(new GridLayout(fieldSize, fieldSize));

		for (int i = 0; i < fieldSize; i++) {
			for (int j = 0; j < fieldSize; j++) {
				ImageIcon icon = new ImageIcon("res/1.png");
				buttonField[i][j] = new JButton();
				buttonField[i][j].setBorder(bor);
				buttonField[i][j].setIcon(icon);
				buttonField[i][j].addMouseListener(new Action(this, new Point(
						i, j)));
				fieldPanel.add(buttonField[i][j]);

			}
		}

		this.add(fieldPanel);

		this.update(getGraphics());
	}

	public Field getGameField() {
		return gameField;
	}

	public void tryAddShip(Point pl, Point pc) {
		int len = this.getShipLen(pl, pc);
		//System.out.println(len);
		//System.out.println("Ship count : " + shipCount );
		if ((len <= 0) || (len >= 5)) {
			return;
		}

		int aviableShips = ships.get(len);
		if (aviableShips > 0) {
			Ship sh = new Ship(gameField, len); 
		
			if (pl.x == pc.x) {
				int min = (pl.y < pc.y) ? pl.y : pc.y;
				if (sh.CheckPlace(pl.x, min, len, 0)){
					shipCount ++;
					shipsArray[shipCount - 1] = sh;
					sh.SetShip(pl.x, min, len, 0);
					gameField = sh.getField();
					ships.put(len, aviableShips - 1);
					this.setLabels();
				}
				
			} else {
				int min = (pl.x < pc.x) ? pl.x : pc.x;
				
				if (sh.CheckPlace(min, pl.y,len, 1)){
					shipCount ++;
					shipsArray[shipCount - 1] = sh;
					sh.SetShip(min, pl.y, len, 1);
					gameField = sh.getField();
					ships.put(len, aviableShips - 1);
					this.setLabels();
				}
			}
			
			//sh.getField().show();
		}
		
		if (shipCount == 10){
			startButton.setEnabled(true);
			gameField.setShipsArray(shipsArray);
		}

	}

	public int getShipLen(Point pl, Point pc) {

		//System.out.println("previous " + pl.x + "  " + pl.y);
		//System.out.println("current " + pc.x + "  " + pc.y);
		if (pl.x == pc.x) {
			return Math.abs(pl.y - pc.y) + 1;
		}

		if (pl.y == pc.y) {
			return Math.abs(pl.x - pc.x) + 1;
		}

		return -1;
	}

	public void fillView() {
		byte[][] paintField;
		ImageIcon icon0 = new ImageIcon("res/1.png");
		ImageIcon icon1 = new ImageIcon("res/2.png");
		ImageIcon icon2 = new ImageIcon("res/3.png");
		ImageIcon icon3 = new ImageIcon("res/4.png");

		paintField = gameField.getField();
		for (int i = 0; i < fieldSize; i++) {
			for (int j = 0; j < fieldSize; j++) {

				if (paintField[i][j] == Field.FREE_CELL)
					buttonField[i][j].setIcon(icon0);
				if (paintField[i][j] == Field.CELL_WITH_SHEEP)
					buttonField[i][j].setIcon(icon3);
				if (paintField[i][j] == Field.STRUCKED_FREE_CELL)
					buttonField[i][j].setIcon(icon1);
				if (paintField[i][j] == Field.STRUCKED_CELL_WITH_SHEEP)
					buttonField[i][j].setIcon(icon2);
			}
		}

	}
	
	
	 private void setLabels(){
	        this.jLabel1.setText("4 Deck: " + Integer.toString(ships.get(4)));
	        this.jLabel2.setText("3 Deck: " + Integer.toString(ships.get(3)));
	        this.jLabel3.setText("2 Deck: " + Integer.toString(ships.get(2)));
	        this.jLabel4.setText("1 Deck: " + Integer.toString(ships.get(1)));
	 }
	
	 
	private void closeWnd() {
		this.dispose();
	} 
	
	public static void main(String[] args) {

		new ShipPlaceForm();
	}

}
