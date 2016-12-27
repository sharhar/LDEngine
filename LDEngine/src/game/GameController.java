package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.rendering.Texture;
import engine.rendering.shapes.QuadAA;
import engine.ui.Button;
import engine.ui.View;
import engine.utils.math.Vector2f;
import game.ui.ConnectionPrompt;
import game.ui.GameSelect;

public class GameController {
	
	private static QuadAA bg;
	private static Font connectionFont;
	private static Font titleFont;
	public static boolean connected = false;
	public static boolean connecting = false;
	public static GameClient client = null;
	
	public static Button connectButton;
	public static Button SPButton;
	public static Button MPButton;
	
	public static View view = null;
	
	public static void init() {
		Game.printFPS = false;
		
		connectionFont = new Font("Arial", Font.PLAIN, 20);
		titleFont = new Font("Arial", Font.BOLD, 54);
		
		BufferedImage img = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(new Color(0.1f, 0.35f, 0.9f));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.dispose();
		
		bg = new QuadAA(new Vector2f(Game.WIDTH/2, Game.HEIGHT/2), new Vector2f(Game.WIDTH, Game.HEIGHT), new Texture(img));
		
		SPButton = new Button(new Rectangle(Game.WIDTH/2 - 100, 200, 200, 60), "Practice", () -> {
			view = new PracticeGame(1);
		});
		
		MPButton = new Button(new Rectangle(Game.WIDTH/2 - 100, 300, 200, 60), "Online", () -> {
			new GameSelect(client);
			connecting = true;
		});
		
		connectButton = new Button(new Rectangle(Game.WIDTH/2 - 100, 400, 200, 60), "Connect", ()-> {
			new ConnectionPrompt();
		});
	}
	
	public static void render() {
		bg.draw();
		
		if(view != null) {
			view.render();
			return;
		}
	}
	
	public static void postRender(Graphics g) {
		if(view != null) {
			view.postRender(g);
			return;
		}
		
		if(connecting && client.serverIndex != -1) {
			connecting = false;
			view = new OnlineGame(client.serverPorts[client.serverIndex]);
			return;
		}
		
		MPButton.greyedOut = !connected;
		
		g.setColor(Color.BLACK);
		g.fillRect(5, 8, 80, 30);
		
		if(connected) {
			g.setColor(Color.GREEN);
			g.setFont(connectionFont);
			g.drawString("Online", 10, 30);
		} else {
			g.setColor(Color.RED);
			g.setFont(connectionFont);
			g.drawString("Offline", 10, 30);
		}
		
		if(!connecting) {
			SPButton.tick();
			MPButton.tick();
			connectButton.tick();
		}
		SPButton.render(g);
		MPButton.render(g);
		connectButton.render(g);
		
		g.setFont(titleFont);
		g.drawString("[Title]", Game.WIDTH/2 - 75, 100);
	}
}
