package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import engine.rendering.Texture;
import engine.rendering.shapes.QuadAA;
import engine.utils.math.Vector2f;
import game.entities.Hero;
import game.entities.Level;
import game.entities.OnlinePlayerHero;
import net.ddns.sharhar.tcp.TcpClient;
import net.ddns.sharhar.tcp.TcpClientCallback;
import net.ddns.sharhar.udp.UdpClient;
import net.ddns.sharhar.udp.UdpClientCallback;
import utils.ByteUtils;

public class OnlineGame extends GameView implements TcpClientCallback, UdpClientCallback{
	
	public TcpClient login;
	public UdpClient client;
	
	volatile private Hero[] heros = null;
	int id = -1;
	private float[] fd = new float[2];
	private float[] poss = null;
	
	public OnlineGame(int port) {
		login = new TcpClient(GameController.client.client.address, port, this);
		login.start();
		
		client = new UdpClient(GameController.client.client.address, port+1, this);
		client.start();
		
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 1, 1);
		g.dispose();
		
		Texture tex = new Texture(img);
		
		QuadAA[] quads = new QuadAA[4];
		quads[0] = new QuadAA(new Vector2f(Game.WIDTH/2, 100), new Vector2f(400, 40), tex);
		quads[1] = new QuadAA(new Vector2f(Game.WIDTH/2 - 200, 250), new Vector2f(200, 40), tex);
		quads[2] = new QuadAA(new Vector2f(Game.WIDTH/2 + 200, 250), new Vector2f(200, 40), tex);
		quads[3] = new QuadAA(new Vector2f(Game.WIDTH/2, 400), new Vector2f(400, 40), tex);
		
		Vector2f[] spawns = new Vector2f[1];
		spawns[0] = new Vector2f(Game.WIDTH/2, 150);
		
		level = new Level(quads, spawns);
		
		new Thread(() -> {
			while(true) {
				if(heros != null) {
					OnlinePlayerHero hr = (OnlinePlayerHero)heros[0];
					
					fd[0] = hr.actPos.x;
					fd[1] = hr.actPos.y;
					
					client.sendData(ByteUtils.float2Byte(fd));
					
					if(poss != null) {
						heros[0].pos.x = poss[id*2+0];
						heros[0].pos.y = poss[id*2+1];
					}
				}
				
				try {
					Thread.sleep(8);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void connected() {
		
	}

	public void receivedData(String data) {
		if(data.startsWith("NM ")) {
			id = Integer.parseInt(data.split(" ")[1]);
		} else if(data.startsWith("PN ")) {
			int num = Integer.parseInt(data.split(" ")[1]);
			
			Hero[] temp = new Hero[num];
			
			temp[0] = new OnlinePlayerHero(level.spawns[0], this);
			
			for(int i = 1;i < temp.length;i++) {
				//heros[i] = new AIHero(level.spawns[i%level.spawns.length]);
			}
			
			heros = temp;
		}
	}
	
	public void render() {
		level.render();
		
		if(heros != null) {
			for(Hero h:heros) {
				h.tick();
			}
			
			for(Hero h:heros) {
				h.draw();
			}			
		}
	}

	public void postRender(Graphics g) {
		
	}
	
	public void receivedData(byte[] data) {
		poss = ByteUtils.byte2Float(data, heros.length*2);
	}
}