package net.ddns.sharhar.tcp;

import net.ddns.sharhar.tcp.TcpServer.TcpServerClient;

public interface TcpServerCallback {
	public void receivedData(int id, TcpServerClient client, String data);
	public void connected(int id, TcpServerClient client);
	public void disconnected(int id, TcpServerClient client);
}
