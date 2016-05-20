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
		
		
		server.addClient(this);
		String receiver = "";
		try {
			clientName = input.readUTF();
			hasName = true;
			while (hasName) {
				lineInput = input.readUTF();

				switch (lineInput) {

				case "/list":
					output.writeUTF("Number of Clients " + server.getNumClients());
					ArrayList<ServerThread> clients = server.getClients();
					for(ServerThread thread : clients)
					{
						output.writeUTF(thread.getClientName());
						output.flush();
					}
					break;
					
				case "/myname":
					output.writeUTF(clientName);
					output.flush();
					break;
				case "/disconnect":
					try {
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
					//implement checking to see if the user is connected
					break;
				default:
					sendMessage(lineInput, receiver);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception");
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
