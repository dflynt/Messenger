import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
	private Client client;
	private Socket sock;

	public ClientThread(Client client, Socket sock) {
		this.client = client;
		this.sock = sock;
	}

	public void run() {
		DataInputStream listener;
		try {
			listener = new DataInputStream(sock.getInputStream());

			String input = "";
			while (true) {
				input = listener.readUTF();
				client.listener(input);
			}
		} catch (IOException e) {
			if(!sock.isConnected())
			{
				client.listener("Connection to server has been lost.");
			}
		}
	}
}