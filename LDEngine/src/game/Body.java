package game;

import java.awt.Color;
import java.util.ArrayList;

import engine.rendering.shapes.Oval;
import engine.utils.math.Vector2f;

public class Body extends Oval{
	public static ArrayList<Boolean> IDs = new ArrayList<Boolean>();
	
	public float r;
	public float m;
	public int ID;
	public Vector2f velocity;
	public boolean kinematic;
	
	public Body(Vector2f pos, Color color, Vector2f velocity, float r, float m, boolean kinematic) {
		super(pos, new Vector2f(r*2, r*2), color);
		this.r = r;
		this.m = m;
		this.velocity = velocity;
		this.kinematic = kinematic;
		
		boolean found = false;
		
		for(int i = 0;i < IDs.size();i++) {
			if(!IDs.get(i)) {
				ID = i;
				IDs.set(i, true);
				found = true;
			}
		}
		
		if(!found) {
			ID = IDs.size();
			IDs.add(true);
		}
	}
	
	public Vector2f getForce(Body other) {
		float d = (float)Math.sqrt(100 +
				(pos.x - other.pos.x)*(pos.x - other.pos.x) +
				(pos.y - other.pos.y)*(pos.y - other.pos.y));
		
		float force = Universe.G*other.m/(d*d);
		
		return new Vector2f(force*(other.pos.x - pos.x)/d, force*(other.pos.y - pos.y)/d);
	}
	
	public void tick(float time) {
		if(!kinematic) {
			return;
		}
		
		if(pos.x - r < 0) {
			velocity.x = Math.abs(velocity.x);
		}
		
		if(pos.x + r > Game.WIDTH) {
			velocity.x = -Math.abs(velocity.x);
		}
		
		if(pos.y - r < 0) {
			velocity.y = Math.abs(velocity.y);
		}
		
		if(pos.y + r > Game.HEIGHT) {
			velocity.y = -Math.abs(velocity.y);
		}
		
		Vector2f force = new Vector2f();
		
		for(Body b : Universe.bodies) {
			if(b.ID != ID) {
				force.add(getForce(b));
			}
		}
		
		force.mult(time);
		velocity.add(force);
		
		pos.add(new Vector2f(velocity.x * time, velocity.y * time));
	}
}
