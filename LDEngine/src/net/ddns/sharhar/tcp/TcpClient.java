package net.ddns.sharhar.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient implements Runnable{
	Socket socket;
	Thread thread;
	boolean running = false;
	PrintWriter out;
	BufferedReader in;
	TcpClientCallback callback;
	public String address;
	public int port;
	private boolean connected = false;
	
	public TcpClient(String address, int port, TcpClientCallback callback) {
		this.callback = callback;
		this.address = address;
		this.port = port;
		thread = new Thread(this);
	}
	
	public boolean start() {
		running = true;
		
		connected = true;
		
		try {
			socket = new Socket(address, port);
			
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new
		            InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
		}
		if(connected) {
			thread.start();
		} else {
			close();
		}
		
		return connected;
	}
	
	public void close() {
		running = false;
		
		try {
			socket.close();
			thread.join();
			in.close();
			out.close();
		} catch (Exception e) {}
		
		connected = false;
	}
	
	public void sendData(String data) {
		out.println(data);
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void run() {
		callback.connected();
			
		while(running) {
			try {
				String input = in.readLine();
				callback.receivedData(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}