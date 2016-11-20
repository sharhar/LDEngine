package engine.utils.physics;

import engine.rendering.shapes.Shape;

public class Collision {
	
	public static boolean qq(Shape s1, Shape s2) {
		float s1x = s1.pos.x - s1.size.x / 2;
		float s1y = s1.pos.y - s1.size.y / 2;
		float s2x = s2.pos.x - s2.size.x / 2;
		float s2y = s2.pos.y - s2.size.y / 2;
		
		if(s1x + s1.size.x < s2x || s2x + s2.size.x < s1x) {
			return false;
		}
		
		if(s1y + s1.size.y < s2y || s2y + s2.size.y < s1y) {
			return false;
		}
		
		return true;
	}
	
	public static boolean cc(Shape s1, Shape s2) {
		float r1 = s1.size.x/2;
		float r2 = s2.size.x/2;
		
		float d = (float) Math.sqrt(
				(s1.pos.x - s2.pos.x)*(s1.pos.x - s2.pos.x) +
				(s1.pos.y - s2.pos.y)*(s1.pos.y - s2.pos.y));
		
		return r1 + r2 >= d;
	}
	
	public static boolean qc(Shape q, Shape c) {
		float x = c.pos.x >= q.pos.x - q.size.x / 2 ? (c.pos.x <= q.pos.x + q.size.x / 2 ? c.pos.x : q.pos.x + q.size.x / 2) : q.pos.x - q.size.x / 2;
		float y = c.pos.y >= q.pos.y - q.size.y / 2 ? (c.pos.y <= q.pos.y + q.size.y / 2 ? c.pos.y : q.pos.y + q.size.y / 2) : q.pos.y - q.size.y / 2;
		
		float d = (float) Math.sqrt(
				(c.pos.x - x)*(c.pos.x - x) +
				(c.pos.y - y)*(c.pos.y - y));
		
		return c.size.x/2 >= d;
	}
}
