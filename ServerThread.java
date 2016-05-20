import java.net.*;
import java.util.ArrayList;
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

		String receiver = "";
		try {
			clientName = input.readUTF();
			server.addClient(this);
			hasName = true;
			while (hasName) {
				lineInput = input.readUTF();
				switch (lineInput) {
				case "/list":
					output.writeUTF("Number of Clients " + server.getNumClients());
					ArrayList<ServerThread> clients = server.getClients();
					for (ServerThread thread : clients) {
						if(thread.isAlive())
							{
								output.writeUTF(thread.getClientName());
								output.flush();
							}	
					}
					break;

				case "/myname":
					output.writeUTF(clientName);
					output.flush();
					break;
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

				case "/newchat":
					output.writeUTF("Enter the user: ");
					receiver = input.readUTF();
					if (!server.containsClient(receiver)) {
						output.writeUTF("User is not connected to the server!");
					}
					break;
				default:
					sendMessage(lineInput, receiver);
				}
			}
		} catch (IOException e) {
			if(socket.isClosed())
			{
				System.out.println(clientName + " has disconnected from the server.");
			}
		}
	}

	public void sendMessage(String message, String receiver) {
		String sendMessage = getClientName() + ": " + message;
		server.sendMessage(sendMessage, receiver);
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
