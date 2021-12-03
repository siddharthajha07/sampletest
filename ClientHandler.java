package com.chatRoom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

	public static ArrayList<ClientHandler> clients=new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String Username;
	
	public ClientHandler(Socket socket) throws IOException {
		try{
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//character stream(byte stream)
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.Username=bufferedReader.readLine();
			clients.add(this);
			broadcastMessage("--- Server: "+Username+" joined the chat. ---");
		}	
		catch(IOException e) {
			closeResources(socket, bufferedReader, bufferedWriter);
		}

	}

	//Broadcast the message to all other clients
	private void broadcastMessage(String messageToSend) throws IOException {

		for(ClientHandler clientHandler: clients)
		{
			try {
					if(!clientHandler.Username.equals(Username)) {
				    clientHandler.bufferedWriter.write(messageToSend);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();//empty the buffer
		}}
			catch(IOException e)
			{
				closeResources(socket, bufferedReader, bufferedWriter);
			}
		}
	}
	//When a user leaves, the object is removed from the list.
		public void removeClientHandler() throws IOException
		{
			clients.remove(this);
			broadcastMessage("--Server: " +Username+ " left the chat.--");
		}
	
		private void closeResources(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
		removeClientHandler();
		try{
			if(bufferedReader!=null)
		{
			bufferedReader.close();
		}
		if(bufferedWriter!=null)
		{
			bufferedWriter.close();
		}
		if(socket!=null)
		{
			socket.close();
		}
		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		}

	@Override
	public void run() {

		String messagefromclient;
		
		while(socket.isConnected())
			try {
				messagefromclient=bufferedReader.readLine();//runs on a separate thread as it is a blocking operation
				broadcastMessage(messagefromclient);
			}
		catch(IOException e) {
			try {
				closeResources(socket, bufferedReader, bufferedWriter);
				break;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
}
