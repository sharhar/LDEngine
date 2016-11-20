package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import engine.input.Keyboard;
import engine.rendering.Texture;
import engine.rendering.shapes.Oval;
import engine.rendering.shapes.QuadAA;
import engine.sound.Sound;
import engine.utils.math.Vector2f;
import engine.utils.math.Vector4f;
import engine.utils.physics.Collision;

public class GameController {
	
	public static final float BALLSPEED = 600;
	
	public static Texture ballTexture;
	public static Texture paddleTexture;
	
	public static QuadAA paddle;
	public static QuadAA paddle2;
	public static Oval ball;
	
	public static Font font;
	
	public static int p1Score = 0;
	public static int p2Score = 0;
	
	public static Sound sound;
	
	private static Vector2f ballSpeed;
	
	public static void init() {
		BufferedImage imagePaddle = new BufferedImage(50, 200, BufferedImage.TYPE_INT_ARGB);
		Graphics g = imagePaddle.getGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 50, 200);
		
		g.dispose();
		
		ballTexture = new Texture("/Test.png");
		paddleTexture = new Texture(imagePaddle);
		
		ball = new Oval(new Vector2f(400, 300), new Vector2f(20, 20));
		paddle = new QuadAA(new Vector2f(50, 300), new Vector2f(25, 100), paddleTexture);
		paddle2 = new QuadAA(new Vector2f(750, 300), new Vector2f(25, 100), paddleTexture);
		
		font = new Font("Arial", Font.BOLD, 24);
		
		ballSpeed = new Vector2f((float) (((Math.random()/2 + 0.5)*BALLSPEED)*Math.signum(Math.random() - 0.5)), 
				(float) (((Math.random()/2 + 0.5)*BALLSPEED)*Math.signum(Math.random() - 0.5)));
		
		sound = new Sound("/Bounce.wav");
	}
	
	public static void render() {
		float speed = Game.deltaTime * 1200;
		
		if(Keyboard.keys[KeyEvent.VK_W]) {
			paddle.pos.y += speed;
		}
		if(Keyboard.keys[KeyEvent.VK_S]) {
			paddle.pos.y -= speed;
		}
		
		if(Keyboard.keys[KeyEvent.VK_UP]) {
			paddle2.pos.y += speed;
		}
		if(Keyboard.keys[KeyEvent.VK_DOWN]) {
			paddle2.pos.y -= speed;
		}
		
		paddle.pos.y = paddle.pos.y < paddle.size.y/2 ? paddle.size.y/2 : 
			(paddle.pos.y > Game.HEIGHT - paddle.size.y/2 ? Game.HEIGHT - paddle.size.y/2 : paddle.pos.y);
		paddle2.pos.y = paddle2.pos.y < paddle2.size.y/2 ? paddle2.size.y/2 : 
			(paddle2.pos.y > Game.HEIGHT - paddle2.size.y/2 ? Game.HEIGHT - paddle2.size.y/2 : paddle2.pos.y);
		
		if(Collision.qc(paddle, ball)) {
			ballSpeed.x = Math.abs(ballSpeed.x);
			sound.play();
		}
		
		if(Collision.qc(paddle2, ball)) {
			ballSpeed.x = -Math.abs(ballSpeed.x);
			sound.play();
		}
		
		if(ball.pos.y - ball.size.y / 2 < 0) {
			ballSpeed.y = Math.abs(ballSpeed.y);
			sound.play();
		}
		
		if(ball.pos.y + ball.size.y / 2 > Game.HEIGHT) {
			ballSpeed.y = -Math.abs(ballSpeed.y);
			sound.play();
		}
		
		ball.pos.x += ballSpeed.x * Game.deltaTime;
		ball.pos.y += ballSpeed.y * Game.deltaTime;
		
		if(ball.pos.x < 0) {
			p2Score++;
			ball.pos = new Vector4f(400, 300, 0, 0);
			ballSpeed = new Vector2f((float) (((Math.random()/2 + 0.5)*BALLSPEED)*Math.signum(Math.random() - 0.5)), 
					(float) (((Math.random()/2 + 0.5)*BALLSPEED)*Math.signum(Math.random() - 0.5)));
		}
		
		if(ball.pos.x > Game.WIDTH) {
			p1Score++;
			ball.pos = new Vector4f(400, 300, 0, 0);
			ballSpeed = new Vector2f((float) (((Math.random()/2 + 0.5)*BALLSPEED)*Math.signum(Math.random() - 0.5)), 
					(float) (((Math.random()/2 + 0.5)*BALLSPEED)*Math.signum(Math.random() - 0.5)));
		}
		
		ball.draw();
		paddle.draw();
		paddle2.draw();
	}
	
	public static void postRender(Graphics g) {
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(p1Score + " | " + p2Score, 375, 75);
	}
}