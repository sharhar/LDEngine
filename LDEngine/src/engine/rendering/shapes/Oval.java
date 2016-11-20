package engine.rendering.shapes;

import java.awt.Color;

import engine.rendering.Renderer;
import engine.utils.math.Vector2f;
import engine.utils.math.Vector4f;

public class Oval extends Shape{
	public Color color;
	
	public Oval(Vector2f pos, Vector2f size, Color color) {
		super(new Vector4f(pos.x , pos.y, 0, 0), new Vector4f(size.x, size.y, 0, 0));
		this.color = color;
	}
	
	public void draw() {
		Renderer.imageBufferGraphics.setColor(color);
		Renderer.imageBufferGraphics.fillOval(
				(int)(pos.x - size.x/2), 
				(int)(pos.y - size.y/2), 
				(int)(size.x), 
				(int)(size.y));
		Renderer.imageBufferGraphics.setColor(Color.white);
	}
	
	public Vector4f[] getPoints() {
		
		return null;
	}
	
	public void updateCol() {
		
	}
}
