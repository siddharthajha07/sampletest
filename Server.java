import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	private ServerSocket serverSocket;//responsible for listening connections or clients to communicate with them

	public Server(ServerSocket serverSocket) {
				this.serverSocket = serverSocket;
	}
	
	public static void main(String[] args) throws IOException {
		
        ServerSocket serverSocket=new ServerSocket(9999);
		System.out.println("Server is running! Waiting for Clients");
		Server server= new Server(serverSocket);
		server.runServer();
		
	}
	
    // Method for starting the server and handle multiple clients
	public void runServer()
	{
		try {
			
			while (serverSocket.isClosed() == false) {
				Socket socket=serverSocket.accept();
				System.out.println("--- A new user has connected to the Chatroom. ---");
				ClientHandler clientHandler=new ClientHandler(socket);
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeServerSocket() {
		try {
			if(serverSocket != null)
			{
				serverSocket.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
