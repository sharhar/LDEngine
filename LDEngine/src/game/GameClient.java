package game;

import net.ddns.sharhar.tcp.TcpClient;
import net.ddns.sharhar.tcp.TcpClientCallback;

public class GameClient implements TcpClientCallback{

	public TcpClient client;
	public String[] serverNames = null;
	public int[] serverPorts = null;
	public int serverIndex = -1;
	
	public GameClient(String server, int port) {
		client = new TcpClient(server, port, this);
		client.start();
	}
	
	public void connected() {
		
	}
	
	public void receivedData(String data) {
		String[] temp = data.split(",");
		
		serverNames = new String[temp.length];
		for(int i = 0;i < serverNames.length;i++) {
			serverNames[i] = temp[i].split("/")[0];
		}
		
		serverPorts = new int[temp.length];
		for(int i = 0;i < serverPorts.length;i++) {
			serverPorts[i] = Integer.parseInt(temp[i].split("/")[1]);
		}
	}

}