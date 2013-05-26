package basata.seabattle.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import basata.seabattle.models.Field;



public class GameForm2 extends JFrame {

	
	private static final long serialVersionUID = -4465830825988397081L;
	public static final int fieldSize = 10;
	private JButton[][] myButtonField;
	private JButton[][] oponentButtonField;

	private Field myGameField;
	private Field oponentGameField;

	private JPanel myFieldPanel;
	private JPanel oponentFieldPanel;

	public GameForm2(Field myGameField, Field oponentGameField) {
		
		
		setTitle("BASATA SEA BATTLE");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(800, 500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (800 / 2); // Center horizontally.
		int Y = (screen.height / 2) - (500 / 2); // Center vertically.

		setLocation(X, Y);
		getContentPane().setLayout(null);
		setResizable(false);

		this.myGameField = myGameField;
		this.oponentGameField = oponentGameField;

		addComponents();
		this.update(getGraphics());
		this.setVisible(true);
	}

	private class Action implements MouseListener {
		private GameForm2 form;
		private Point p = new Point(0, 0);

		public Action(GameForm2 form, Point p) {
			this.form = form;
			this.p = p;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			String mes = "shot " + Integer.toString(p.x) + " "
					+ Integer.toString(p.y);

			boolean f = true;

			f = oponentGameField.doShot(p.x, p.y);
			
			if (f){
			// form.hits ++ ;
				oponentGameField.findShip(p.x, p.y);
			// setBigCells(game.getCplayer2().getField());
			   this.form.fillView(false);
			// this.form.setLabels();
			 if(oponentGameField.getShipsCount() == 0){
				 System.out.print("CONGRATULATIONS!!");
			 }
			return;
			 }
			// setBigCells(game.getCplayer2().getField());
			// form.water ++;
			// this.form.setLabels();
			this.form.fillView(false);

			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
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

		myFieldPanel = new JPanel();
		myFieldPanel.setBounds(350, 50, 240, 240);

		oponentFieldPanel = new JPanel();
		oponentFieldPanel.setBounds(50, 50, 240, 240);

		

		this.addFields();
		
		
	}

	private void addFields() {
		Border bor = BorderFactory.createBevelBorder(0);
		myButtonField = new JButton[fieldSize][fieldSize];
		oponentButtonField = new JButton[fieldSize][fieldSize];
		myFieldPanel.setLayout(new GridLayout(fieldSize, fieldSize));
		oponentFieldPanel.setLayout(new GridLayout(fieldSize, fieldSize));
		for (int i = 0; i < fieldSize; i++) {
			for (int j = 0; j < fieldSize; j++) {
				ImageIcon icon = new ImageIcon("res/1.png");
				oponentButtonField[i][j] = new JButton();
				oponentButtonField[i][j].setBorder(bor);
				oponentButtonField[i][j].setIcon(icon);
				oponentButtonField[i][j].addMouseListener(new Action(this,
						new Point(i, j)));
				oponentFieldPanel.add(oponentButtonField[i][j]);

			}
		}
		
		
		ImageIcon icon = new ImageIcon();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++)
            {
                if (myGameField.getElem(i, j) == Field.FREE_CELL){
                    icon = new ImageIcon("res/1.png");
                }

                if (myGameField.getElem(i, j) == Field.CELL_WITH_SHEEP){
                    icon = new ImageIcon("res/4.png");
                }


                myButtonField[i][j] = new JButton();
                myButtonField[i][j].setBorder(bor);
                myButtonField[i][j].setIcon(icon);
                myFieldPanel.add(myButtonField[i][j]);
            }
        }
		
		this.add(myFieldPanel);
		this.add(oponentFieldPanel);
		
		this.update(getGraphics());
	}

	public Field getMyGameField() {
		return myGameField;
	}

	public Field getOponentGameField() {
		return oponentGameField;
	}

	public void fillView(boolean my) {
		byte[][] paintField;
		ImageIcon icon0 = new ImageIcon("res/1.png");
		ImageIcon icon1 = new ImageIcon("res/2.png");
		ImageIcon icon2 = new ImageIcon("res/3.png");
		ImageIcon icon3 = new ImageIcon("res/4.png");

		if (my) {
			paintField = myGameField.getField();
			for (int i = 0; i < fieldSize; i++) {
				for (int j = 0; j < fieldSize; j++) {

					if (paintField[i][j] == Field.FREE_CELL)
						myButtonField[i][j].setIcon(icon0);
					if (paintField[i][j] == Field.CELL_WITH_SHEEP)
						myButtonField[i][j].setIcon(icon3);
					if (paintField[i][j] == Field.STRUCKED_FREE_CELL)
						myButtonField[i][j].setIcon(icon1);
					if (paintField[i][j] == Field.STRUCKED_CELL_WITH_SHEEP)
						myButtonField[i][j].setIcon(icon2);
				}
			}
		} else {
			paintField = oponentGameField.getField();
			for (int i = 0; i < fieldSize; i++) {
				for (int j = 0; j < fieldSize; j++) {
					if ((paintField[i][j] == Field.FREE_CELL)
							|| (paintField[i][j] == Field.CELL_WITH_SHEEP))
						oponentButtonField[i][j].setIcon(icon0);
					if (paintField[i][j] == Field.STRUCKED_FREE_CELL)
						oponentButtonField[i][j].setIcon(icon1);
					if (paintField[i][j] == Field.STRUCKED_CELL_WITH_SHEEP)
						oponentButtonField[i][j].setIcon(icon2);
				}
			}
		}
	}

	public static void main(String[] args) {
		Field my = new Field();
		my.SetShips();
		Field oponent = new Field();
		oponent.SetShips();
		new GameForm2(my, oponent);
	}

}
