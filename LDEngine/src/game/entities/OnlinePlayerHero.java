package game.entities;

import java.awt.event.KeyEvent;

import engine.input.Keyboard;
import engine.utils.math.Vector2f;
import game.GameView;
import game.OnlineGame;

public class OnlinePlayerHero extends PlayerHero{
	
	public Vector2f actPos;
	public Vector2f posStore;
	public int acttexID;
	public int storetexID;
	
	public OnlinePlayerHero(Vector2f pos, GameView view) {
		super(pos, view);
		actPos = new Vector2f(pos);
		posStore = new Vector2f(pos);
		acttexID = texID;
	}
	
	@Override
	public void tick() {
		posStore.x = pos.x;
		posStore.y = pos.y;
		
		pos.x = actPos.x;
		pos.y = actPos.y;
		
		storetexID = texID;
		texID = acttexID;
		
		super.tick();
		
		acttexID = texID;
		texID = storetexID;
		
		actPos.x = pos.x;
		actPos.y = pos.y;
		
		pos.x = posStore.x;
		pos.y = posStore.y;
	}
	
	@Override
	public void otherTick() {
		if(Keyboard.keys[KeyEvent.VK_Z] && !attacked) {
			OnlineGame online = (OnlineGame)this.view;
			online.attack();
			
			attacked = true;
		}
		
		if(!Keyboard.keys[KeyEvent.VK_Z]) {
			attacked = false;
		}
	}
}
