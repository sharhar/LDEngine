package server;

import net.ddns.sharhar.tcp.TcpServer;
import net.ddns.sharhar.tcp.TcpServer.TcpServerClient;
import net.ddns.sharhar.tcp.TcpServerCallback;
import net.ddns.sharhar.udp.UdpServer;
import net.ddns.sharhar.udp.UdpServerCallback;
import utils.ByteUtils;

public class GameServer implements TcpServerCallback, UdpServerCallback {
	
	public TcpServer tcpServer;
	public UdpServer udpServer;
	public String name;
	public int port;
	public int playerNum = 2;
	public float[] poss;
	
	public GameServer(int port) {
		tcpServer = new TcpServer(port, this);
		tcpServer.start();
		
		udpServer = new UdpServer(port+1, this);
		udpServer.start();
		
		this.port = port;
		name = "Game " + port;
		
		poss = new float[3*playerNum];
		
		new Thread(() -> {
			while(true) {
				udpServer.sendAll(ByteUtils.float2Byte(poss));
				try {
					Thread.sleep(4);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void receivedData(int id, TcpServerClient client, String data) {
		
	}

	public void connected(int id, TcpServerClient client) {
		client.sendData("NM " + id);
		client.sendData("PN " + playerNum);
	}

	public void disconnected(int id, TcpServerClient client) {
			
	}
	
	public void receivedData(int id, byte[] data) {
		float[] pos = ByteUtils.byte2Float(data, 3);
		
		if(pos[0] == Float.MAX_VALUE) {
			data[4] = (byte)id;
			udpServer.sendAll(data);
		} else {			
			poss[id*3+0] = pos[0];
			poss[id*3+1] = pos[1];
			poss[id*3+2] = pos[2];
		}
	}
}
