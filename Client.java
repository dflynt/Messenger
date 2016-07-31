import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;


//Notes while making this and what I learned
/*
* buttons and text fields are better used with action listeners created in a fashion seen below
* it allows listener methods to be more component-specific
* 
* using getText() on a textfield, even if someone hasn't typed anything, still returns a string equal to ""
* to effectively check if someone has actually typed something, use if(!textField.getText().equals(""))
* 
*/
public class Client{

	String username;
	JFrame chatFrame;
	JPanel chatPane;
	JTextArea receivedTA;
	JTextField inputTF;
	DataOutputStream dout;
	JTextArea connectedUsersTA;
	JButton sendBtn;

	public Client(String userName) {
		this.username = userName;
		String servName = "localhost";
		int portNum = 3033;
		try {
			Socket sock = new Socket(servName, portNum);
			// creates a new client thread for listening for input
			new ClientThread(this, sock).start();
			dout = new DataOutputStream(sock.getOutputStream());
			//the first message sent after the client is connected to the server
			//is the username that the user chose there, the following line is required
			dout.writeUTF(userName);

			chatFrame = new JFrame("Chat");
			chatPane = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			receivedTA = new JTextArea();
			c.gridx = 0;
			c.gridy = 0;
			c.ipady = 320;
			c.ipadx = 300;
			receivedTA.setVisible(true);
			receivedTA.setEditable(false);
			JScrollPane receivedJP = new JScrollPane(receivedTA);
			chatPane.add(receivedJP, c);

			inputTF = new JTextField();
			c.gridx = 0;
			c.gridy = 1;
			c.ipadx = 300;
			c.ipady = 15;
			inputTF.setEditable(true);
			inputTF.setVisible(true);
			// action listener for when the user hits enter
			inputTF.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						dout.writeUTF(inputTF.getText());
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								inputTF.setText("");
							}
						});

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			JScrollPane inputJP = new JScrollPane(inputTF);
			chatPane.add(inputJP, c);

			connectedUsersTA = new JTextArea(20, 40);
			c.gridx = 2;
			c.gridy = 0;
			c.ipadx = 80;
			c.ipady = 320;
			connectedUsersTA.setText(" Users Online\n ----------\n");
			connectedUsersTA.setEditable(false);
			connectedUsersTA.setLineWrap(true);
			connectedUsersTA.setWrapStyleWord(true);
			JScrollPane connectedUsersJP = new JScrollPane(connectedUsersTA);
			chatPane.add(connectedUsersJP, c);

			sendBtn = new JButton("Send");
			c.gridx = 2;
			c.gridy = 1;
			c.ipady = 10;
			sendBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						dout.writeUTF(inputTF.getText());
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								inputTF.setText("");
							}
						});

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			chatPane.add(sendBtn, c);

			chatFrame.setSize(500, 425);
			chatFrame.add(chatPane);
			chatFrame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e)
				{
					try {
						dout.writeUTF("/disconnect");
						dout.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			});
			chatPane.setVisible(true);
			chatFrame.setVisible(true);

		} catch (IOException e) {
			JFrame popupFrame = new JFrame("Connection error");
			popupFrame.setSize(300, 100);
			JPanel popupPane = new JPanel();
			JLabel popupLbl = new JLabel("The server is offline");
			popupPane.add(popupLbl);
			popupFrame.add(popupPane);
			popupFrame.setVisible(true);
		}
	}

	public void listener(String message) {
		if (message.equals("Connection Disrupted")) {
			System.exit(0);
		}

		// else bracket that removes "user: "
		// messages that contain "user: " are designated messages only meant to
		// be sent to the online users TextArea to show who is connected to the
		// server
		else if (message.substring(0,5).equals("user:")) {
			String name = message.substring(5);			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(!connectedUsersTA.getText().contains(name))
					{
						connectedUsersTA.append(name + "\n");
					}
				}
			});
		}

		// else bracket that removes "ruser: "
		// messages that contain "ruser: " are designated messages only meant to
		// be sent to the online users TextArea to update who has just been
		// disconnected
		// Drawback to this: if there were multiple users with the same name,
		// they would all be removed
		else if (message.substring(0, 6).equals("ruser:")) {
			String removeUser = message.substring(6);
			String[] onlineUsers = connectedUsersTA.getText().split("\n");
			
			// finds string that matches the variable 'message'
			for (int i = 2; i < onlineUsers.length; i++) {
				if (onlineUsers[i].equals(removeUser)) {
					// remove it from the array
					onlineUsers[i] = null;
				}
			}
			
			connectedUsersTA.setText( "Users Online\n ----------\n");
			// adds the new list, without the removed user, to the textArea
			for (int k = 2; k < onlineUsers.length; k++) {
				if (onlineUsers[k] != null) {
					connectedUsersTA.append(onlineUsers[k]+"\n");
				}
			}
		} else {
			if (message != null) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						receivedTA.append(message + "\n\n");
					}
				});
			}
		}
	}
}
