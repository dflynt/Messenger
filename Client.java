import java.net.*;
import java.io.*;

public class Client {
	

	public static void main(String[] args) throws IOException {

		String servName = "71.76.242.130";
		int portNum = 3033;
		try {
			System.out.println("Attempting to connect to server . . .");
			Socket sock = new Socket(servName, portNum);
			System.out.println("Successfully Connected.");
		
		//creates a new client thread for listening for input
		Client client = new Client();
		new ClientThread(client, sock).start();
		DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//System.out.println("Connected to " + servName);
		//System.out.println("Port Number: " + portNum);
		//System.out.println("Remote sock address: " + sock.getRemoteSocketAddress());
		//System.out.println("Local Address: " + sock.getLocalAddress());
		//System.out.println("Local Socket Address: " + sock.getLocalSocketAddress());
		
		System.out.println("\n\nVarious Input Commands");
		
		System.out.println("----------------------");
		System.out.println("/list returns the number of connected users and a list of their names.");
		System.out.println("/disconnect disconnects you from the server and stops the client. Restart it to connect back to the server.");
		System.out.println("/newchat sends a message to a different user. It is possible to send messages to " +
				"multiple users by leading with this message.");
		System.out.println("----------------------");
		
		System.out.print("Enter your username: ");

		String newUser = br.readLine();
		dout.writeUTF(newUser);
		dout.flush();
		String input = "";
		while(true)
		{
			input = br.readLine();
			dout.writeUTF(input);
			dout.flush();
		}
		} catch (IOException e) {
			System.out.println("Unable to connect.");
			System.exit(0);
		}
	}
	public void listener(String message)
	{
		if(message.equals("Connection Disrupted"))
		{
			System.exit(1);
		}
		System.out.println(message);
	}
}