import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow extends Frame{

	public ChatWindow(String userName)
	{
		JFrame chatFrame = new JFrame();
		JPanel chatPane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JTextArea receivedTA = new JTextArea();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 320;
		c.ipadx = 285;
		receivedTA.setVisible(true);
		receivedTA.setEditable(false);
		receivedTA.setText("Hello");
		chatPane.add(receivedTA, c);
		
		JTextField input = new JTextField();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 310;
		c.ipady = 20;
		input.setVisible(true);
		chatPane.add(input, c);
		
		JTextArea connectedUsersLB = new JTextArea();
		
		c.gridx = 2;
		c.gridy = 0;
		c.ipadx = 30;
		c.ipady = 320;
		connectedUsersLB.setText(" Users Online");
		connectedUsersLB.append("\n ----------");

		connectedUsersLB.setEditable(false);
		chatPane.add(connectedUsersLB, c);

		connectedUsersLB.append("\n Danny");
		
		chatFrame.setSize(500, 400);
		chatFrame.add(chatPane);
		chatFrame.setVisible(true);
	}
}
