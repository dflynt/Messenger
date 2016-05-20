import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server {

	public ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
	static DataInputStream din = null;
	static DataOutputStream dout = null;

	public static void main(String[] args) throws IOException {

		System.out.println("Server Program");
		ServerSocket servSock = new ServerSocket(3033);
		Server myServer = new Server();
		while (true) {
			Socket sock = servSock.accept();
			new ServerThread(myServer, sock).start();
		}
	}

	public void sendMessage(String message, String client) {
		if(clients.size() == 1)
		{
			clients.get(0).printMessage("You're the only one connected.");
			return;
		}
		System.out.println("Hey we skipped over the if statement brody");
		for (ServerThread thread : clients) {
			if (thread.getClientName().equals(client)) {
				thread.printMessage(message);
			}
		}
	}

	public synchronized void addClient(ServerThread thread) {
		clients.add(thread);
	}

	public int getNumClients() {
		return clients.size();
	}

	public ArrayList<ServerThread> getClients() throws IOException {
		return clients;
	}
	//implement method to see if username entered from /newchat command is connected to the server
}
