import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends Frame implements WindowListener, ActionListener {

	String userName;
	JFrame loginFrame;
	public static void main(String[] args) {
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setSize(500, 500);
	}

	public LoginWindow() {
		loginFrame = new JFrame("Login");
		JPanel loginPane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel userLabel = new JLabel("Username:");
		c.ipadx = 30;
		c.gridx = 0;
		c.gridy = 0;
		loginPane.add(userLabel, c);

		JTextField userTF = new JTextField();
		userTF.setEditable(true);
		c.ipadx = 100;
		c.gridx = 1;
		c.gridy = 0;
		String userName = userTF.getText();
		setUserName(userName);
		loginPane.add(userTF, c);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		c.ipadx = 0;
		c.gridy = 1;
		loginPane.add(loginButton, c);
		
		loginFrame.setSize(250,300);
		loginFrame.add(loginPane);
		loginFrame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(getUserName().trim() != null )
		{	
			loginFrame.dispose();
			new Client(getUserName());	
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}

	public void setUserName(String name) {
		userName = name;
	}
	public String getUserName()
	{
		return userName;
	}

}