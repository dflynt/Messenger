import java.net.*;
import java.io.*;

public class ServerThread extends Thread {

	private Socket socket;
	private Server server;
	private DataInputStream input;
	private DataOutputStream output;
	private String lineInput;
	private String clientName;

	public ServerThread(Server mainServer, Socket clientSocket) {
		this.server = mainServer;
		this.socket = clientSocket;
	}

	public void run() {
		boolean hasName = false;
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			System.out.println("Input/Output data stream unavailable.");
		}

		try {
			clientName = input.readUTF();
			if(clientName != null)
			{
				server.addClient(this);
			}
			hasName = true;
			while (hasName) {
				lineInput = input.readUTF();
				switch (lineInput) {
				case "/disconnect":
					try {
						server.removeClient(this);
						output.writeUTF("Connection Disconnected.");
						output.flush();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				default:
					sendMessage(lineInput);
				}
			}
		} catch (IOException e) {
			if(socket.isClosed())
			{
				System.out.println(clientName + " has disconnected from the server.");
			}
		}
	}

	public void sendMessage(String message) {
		String sendMessage = getClientName() + ": " + message;
		server.sendMessage(sendMessage);
	}

	public void printMessage(String message) {
		try {
			output.writeUTF(message);
		} catch (IOException e) {
			System.out.println("User unavailable.");
		}
	}

	public String getClientName() {
		return clientName;
	}

}
