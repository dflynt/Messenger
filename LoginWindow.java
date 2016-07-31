import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends Frame {

	String userName;
	JFrame loginFrame;

	public static void main(String[] args) {
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setSize(500, 500);
	}

	public LoginWindow() {
		loginFrame = new JFrame("Login");
		loginFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		JPanel loginPane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel userLabel = new JLabel("Username:");
		c.ipadx = 30;
		c.gridx = 0;
		c.gridy = 0;
		loginPane.add(userLabel, c);

		JTextField userTF = new JTextField();
		userTF.setText(null);
		userTF.setEditable(true);
		userTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!userTF.getText().equals(""))
				{
					String userName = userTF.getText();
					loginFrame.dispose();
					new Client(userName);
				}
			}
		});
		c.ipadx = 100;
		c.gridx = 1;
		c.gridy = 0;
		loginPane.add(userTF, c);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!userTF.getText().equals(""))
				{
					System.out.println("Hello");
					String userName = userTF.getText();
					loginFrame.dispose();
					new Client(userName);
				}
			}
		});
		c.ipadx = 0;
		c.gridy = 1;
		loginPane.add(loginButton, c);

		loginFrame.setSize(250, 300);
		loginFrame.add(loginPane);
		loginFrame.setVisible(true);
	}
}
