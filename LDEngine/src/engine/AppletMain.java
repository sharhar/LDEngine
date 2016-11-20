package engine;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import engine.input.Keyboard;
import engine.input.Mouse;
import game.Game;

public class AppletMain extends Applet{
	private static final long serialVersionUID = -1574341085759898747L;
	
	private static BufferedImage offScreenBuffer;
	private static Graphics offScreenGraphics;
	
	public void init() {
		super.init();
		this.setSize(Game.WIDTH, Game.HEIGHT);
		
		Game.init();
		
		Mouse.init();
		Mouse mouse = new Mouse();
		
		Keyboard.init();
		Keyboard keyboard = new Keyboard();
		
		super.addMouseListener(mouse);
		super.addMouseMotionListener(mouse);
		super.addKeyListener(keyboard);
		
		offScreenBuffer = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		offScreenGraphics = offScreenBuffer.getGraphics();
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		Game.render(offScreenGraphics);
		g.drawImage(offScreenBuffer,0,0,this); 
		repaint();
	}
}
