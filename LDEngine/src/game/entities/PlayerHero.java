package game.entities;

import java.awt.event.KeyEvent;

import engine.input.Keyboard;
import engine.utils.math.Vector2f;
import game.Game;
import game.GameView;

public class PlayerHero extends Hero{
	
	float jumpHeight = 200;
	float G = -8*jumpHeight;
	float yVel = 0;
	private boolean jumped = false;
	private GameView view;

	public PlayerHero(Vector2f pos, GameView view) {
		super(pos);
		
		this.view = view;
	}
	
	public void tick() {
		float speed = 400;
		
		if(Keyboard.keys[KeyEvent.VK_LEFT]) {
			pos.x -= speed * Game.deltaTime;
			
			if(view.level.col(this)) {
				pos.x += speed * Game.deltaTime;
			}
			
			this.texture = tex[1];
		}
		
		if(Keyboard.keys[KeyEvent.VK_RIGHT]) {
			pos.x += speed * Game.deltaTime;
			
			if(view.level.col(this)) {
				pos.x -= speed * Game.deltaTime;
			}
			
			this.texture = tex[0];
		}
		
		yVel += G * Game.deltaTime;
		
		pos.y += yVel * Game.deltaTime;
		
		if(view.level.col(this)) {
			pos.y -= yVel * Game.deltaTime;
			yVel = 0;
		}
		
		if(Keyboard.keys[KeyEvent.VK_UP] && !jumped) {
			yVel = 4 * jumpHeight;
			jumped = true;
		}
		
		if(!Keyboard.keys[KeyEvent.VK_UP]) {
			jumped = false;
		}
	}
}