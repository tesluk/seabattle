package basata.seabattle.gui;



import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import basata.seabattle.models.Utils;

public class StartWindow extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JTextField loginField;
	private JPasswordField passwordField;
	private JTextField serverIPField;
	private JButton connectButton;
	
	
	public StartWindow() {

		setTitle("BASATA SEA BATTLE");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(300, 200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (300 / 2); // Center horizontally.
		int Y = (screen.height / 2) - (200 / 2); // Center vertically.

		setLocation(X, Y);
		getContentPane().setLayout(null);
		setResizable(false);

		addComponents();
		this.update(getGraphics());
		this.setVisible(true);

	}
	
	
	private void addComponents() {

		JLabel loginLabel = new JLabel("Login:");
		loginLabel.setBounds(10, 10, 350, 25);
		this.add(loginLabel);
		
		loginField = new JTextField();
		loginField.setBounds(120, 10, 150, 25);
		this.add(loginField);
		
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 40, 350, 25);
		this.add(passwordLabel);
		
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(120, 40, 150, 25);
		this.add(passwordField);
		
		
		
		JLabel serverIPLabel = new JLabel("Server IP:");
		serverIPLabel.setBounds(10, 70, 350, 25);
		this.add(serverIPLabel);
		
		serverIPField = new JTextField();
		serverIPField.setBounds(120, 70, 150, 25);
		this.add(serverIPField);
		
		
		connectButton = new JButton("Connect");
		connectButton.setBounds(110, 130, 100, 25);
		this.add(connectButton);
		
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = loginField.getText();
				char[] p = passwordField.getPassword();
				String passw = new String(p);
				String serverIP = serverIPField.getText();
				
				
				//System.out.println(login+" "+passw+" "+serverIP);
				
				if (!( (Utils.isAllowedStr(login)) && (Utils.isAllowedStr(passw)))){
					JOptionPane.showMessageDialog(null, "You must use alloved symbols: A-Za-z_0-9");
					return;
				}
				
				System.out.println("OK");
			}
		});
		
		
	}
	
	public static void main(String[] args) {
		new StartWindow();
	}
}
