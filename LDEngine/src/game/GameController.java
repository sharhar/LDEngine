package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import engine.utils.math.Vector2f;

public class GameController {
	
	public static void init() {
		Game.printFPS = true;
		Universe.init();
		
		Random random = new Random();
		for(int i = 0; i < 1500;i++) {
			Universe.bodies.add(
					new Body(
							new Vector2f(random.nextInt(Game.WIDTH - 10), random.nextInt(Game.HEIGHT - 10)), 
							Color.white, new Vector2f(10, 0), 2, 10, true));
		}
		
		Universe.bodies.add(new Body(new Vector2f(200, 500), Color.yellow, new Vector2f(), 4, 1000, false));
		Universe.bodies.add(new Body(new Vector2f(500, 200), Color.yellow, new Vector2f(), 4, 1000, false));
		Universe.bodies.add(new Body(new Vector2f(200, 200), Color.yellow, new Vector2f(), 4, 1000, false));
		Universe.bodies.add(new Body(new Vector2f(500, 500), Color.yellow, new Vector2f(), 4, 1000, false));
		
		Universe.bodies.add(new Body(new Vector2f( 700, 500), Color.yellow, new Vector2f(), 4, 1000, false));
		Universe.bodies.add(new Body(new Vector2f(1000, 200), Color.yellow, new Vector2f(), 4, 1000, false));
		Universe.bodies.add(new Body(new Vector2f( 700, 200), Color.yellow, new Vector2f(), 4, 1000, false));
		Universe.bodies.add(new Body(new Vector2f(1000, 500), Color.yellow, new Vector2f(), 4, 1000, false));
	}
	
	public static void render() {
		Universe.tick(Game.deltaTime);
		Universe.render();
	}
	
	public static void postRender(Graphics g) {
		
	}
}
