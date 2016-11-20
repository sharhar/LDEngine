package engine.rendering.shapes;

import engine.rendering.Renderer;
import engine.utils.math.Vector2f;
import engine.utils.math.Vector4f;

public class Oval extends Shape{
	
	public Oval(Vector2f pos, Vector2f size) {
		super(new Vector4f(pos.x , pos.y, 0, 0), new Vector4f(size.x, size.y, 0, 0));
	}
	
	public void draw() {
		Renderer.imageBufferGraphics.fillOval(
				(int)(pos.x - size.x/2), 
				(int)(pos.y - size.y/2), 
				(int)(size.x), 
				(int)(size.y));
	}
	
	public Vector4f[] getPoints() {
		
		return null;
	}
	
	public void updateCol() {
		
	}
}