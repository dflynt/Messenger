import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Client implements ActionListener, KeyListener {

	String userName;
	JFrame chatFrame;
	JPanel chatPane;
	JTextArea receivedTA;
	JTextArea inputTA;
	DataOutputStream dout;
	JTextArea connectedUsersTA;
	
	public Client(String userName) {
		this.userName = userName;
		String servName = "localhost";
		int portNum = 3033;
		try {
			System.out.println("Attempting to connect to server . . .");
			Socket sock = new Socket(servName, portNum);
			System.out.println("Successfully Connected.");

			// creates a new client thread for listening for input
			Client client = new Client(userName);
			new ClientThread(client, sock).start();
			//creates a new thread for monitoring new connected users			
			dout = new DataOutputStream(sock.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			// System.out.println("Connected to " + servName);
			// System.out.println("Port Number: " + portNum);
			// System.out.println("Remote sock address: " +
			// sock.getRemoteSocketAddress());
			// System.out.println("Local Address: " + sock.getLocalAddress());
			// System.out.println("Local Socket Address: " +
			// sock.getLocalSocketAddress());

			System.out.println("\n\nVarious Input Commands");

			System.out.println("----------------------");
			System.out.println("/list returns the number of connected users and a list of their names.");
			System.out.println(
					"/disconnect disconnects you from the server and stops the client. Restart it to connect back to the server.");
			System.out.println("/newchat sends a message to a different user. It is possible to send messages to "
					+ "multiple users by leading with this message.");
			System.out.println("----------------------");

			chatFrame = new JFrame();
			chatPane = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			receivedTA = new JTextArea();
			c.gridx = 0;
			c.gridy = 0;
			c.ipady = 320;
			c.ipadx = 300;
			receivedTA.setVisible(true);
			receivedTA.setEditable(false);
			receivedTA.setText("Hello");
			JScrollPane receivedJP = new JScrollPane(receivedTA);
			chatPane.add(receivedJP, c);

			inputTA = new JTextArea();
			c.gridx = 0;
			c.gridy = 1;
			c.ipadx = 300;
			c.ipady = 15;
			inputTA.setEditable(true);
			inputTA.setLineWrap(true);
			inputTA.setVisible(true);
			inputTA.addKeyListener(this);
			JScrollPane inputJP = new JScrollPane(inputTA);
			chatPane.add(inputJP, c);

			connectedUsersTA = new JTextArea();
			c.gridx = 2;
			c.gridy = 0;
			c.ipadx = 30;
			c.ipady = 305;
			connectedUsersTA.setText(" Users Online");
			connectedUsersTA.append("\n ----------");
			connectedUsersTA.setEditable(false);
			chatPane.add(connectedUsersTA, c);

			JButton sendBtn = new JButton("Send");
			c.gridx = 2;
			c.gridy = 1;
			c.ipady = 10;
			sendBtn.addActionListener(this);
			
			chatPane.add(sendBtn, c);
			chatFrame.setSize(500, 425);
			chatFrame.add(chatPane);
			chatFrame.setVisible(true);
			
			dout.writeUTF(userName);
			dout.flush();
			String message;
			while (true) {
				message = inputTA.getText();
				dout.writeUTF(message);
				dout.flush();
			}
		} catch (IOException e) {
			JFrame popupFrame = new JFrame("Connection error");
			popupFrame.setSize(300,100);
			JPanel popupPane = new JPanel();
			JLabel popupLbl = new JLabel("Not connected to the server");
			popupPane.add(popupLbl);
			popupFrame.add(popupPane);
			popupFrame.setVisible(true);
		}
	}

	public void listener(String message) {
		if (message.equals("Connection Disrupted")) {
			System.exit(0);
		}
		
		//else bracket that removes "user: " when connected users are listed.
		//messages that contain "user: " are designated messages only meant to
		//be sent to the online users TextArea
		else if(message.contains("user: "))
		{
			message.replaceAll("user: ", "");
			connectedUsersTA.append(message);			
		}
		else
		{
			receivedTA.append(message);
		}
	}

	
	//send button clicked
	@Override
	public void actionPerformed(ActionEvent e) {
		if(inputTA.getText() != null)
		{
			try {
				dout.writeUTF(inputTA.getText());
				inputTA.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//enter key is hit
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
		if(inputTA.getText() != null)
		{
			try {
				dout.writeUTF(inputTA.getText());
				inputTA.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
		if(inputTA.getText() != null)
		{
			try {
				dout.writeUTF(inputTA.getText());
				inputTA.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
		if(inputTA.getText() != null)
		{
			try {
				dout.writeUTF(inputTA.getText());
				inputTA.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
		}
	}
	
}
