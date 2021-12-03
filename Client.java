package com.chatRoom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private  String name;
	
	public Client(Socket socket, String name)
	{
		try {
		this.socket=socket;
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.name=name;
	}
	catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter your name for groupchat:");
		String name=scanner.nextLine();
		Socket socket=new Socket("localhost", 9999);
		Client client= new Client(socket,name);
		client.listenforMessage();
		client.sendMessage();
	}
	
	public void sendMessage()
	{
		try {
			bufferedWriter.write(name);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner scanner=new Scanner(System.in);
			while(socket.isConnected())
			{
				String message=scanner.nextLine();
				bufferedWriter.write(name+ " : "+message);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		}
		catch (IOException e) {
			closeResources(socket, bufferedReader, bufferedWriter);
		}
		}
	
	private void closeResources(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
	
	public void listenforMessage()
	{
		Thread t=new Thread(new Runnable(){
			@Override
			public void run() {
				String msgfromgroupchat;
				
				while(socket.isConnected()) {
					try {
						msgfromgroupchat=bufferedReader.readLine();
						System.out.println(msgfromgroupchat);
					}
					catch (IOException e) {
						closeResources(socket, bufferedReader, bufferedWriter);
					}
				}
			}
			});
			t.start();
	}	
}
