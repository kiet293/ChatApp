package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketController {
	public String serverName;
	public int serverPort;
	ServerSocket serverSocket;
	public List<Client> connectedClient;
	public List<Room> allRooms;
	
	public void openSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
			connectedClient = new ArrayList<Client>();
			allRooms = new ArrayList<Room>();
			
			new Thread(() -> {
				try {
					do {
						System.out.println("Waiting for client");
						
						Socket clientSocket = serverSocket.accept();
						
						ClientCommunicateThread clientCommunicator = new ClientCommunicateThread(clientSocket);
						clientCommunicator.start();
						
					} while(serverSocket != null && !serverSocket.isClosed());
				} catch (IOException e) {
					System.out.println("Server or client socket closed");
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void CloserSocket() {
		try {
			for (Client client : connectedClient) 
				client.socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getThisIP() {
		String ip = "";
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("google.com", 80));
			ip = socket.getLocalAddress().getHostAddress();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ip;
	}
}
