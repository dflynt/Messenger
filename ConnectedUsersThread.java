import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//This thread is only used to monitor new connections to the server 
//and send out updates to every client

public class ConnectedUsersThread extends Thread{
	private Server server;
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;

	public ConnectedUsersThread(Server mainServer, Socket clientSock) {
		this.server = mainServer;
		this.socket = clientSock;
	}

	public void run() {
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			while (true) {
				
			}
		} catch (IOException e) {
			if(!socket.isConnected())
			{
				System.out.println("Connection to server has been lost.");
			}
		}
	}
}
