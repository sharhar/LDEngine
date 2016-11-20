package engine.rendering.shapes;

import engine.rendering.Renderer;
import engine.rendering.Texture;
import engine.utils.math.Vector2f;
import engine.utils.math.Vector4f;

public class QuadAA extends Shape{
	public Texture texture;
	
	public QuadAA(Vector2f pos, Vector2f size, Texture texture) {
		super(new Vector4f(pos.x , pos.y, 0, 0), new Vector4f(size.x, size.y, 0, 0));
		this.texture = texture;
		colVerts = new Vector4f[4];
		
		for(int i = 0; i < colVerts.length;i++) {
			colVerts[i] = new Vector4f();
		}
	}
	
	public void updateCol() {
		colVerts[0].x = -0.5f * size.x + pos.x;
		colVerts[0].y = -0.5f * size.y + pos.y;
		
		colVerts[1].x = -0.5f * size.x + pos.x;
		colVerts[1].y =  0.5f * size.y + pos.y;
		
		colVerts[2].x =  0.5f * size.x + pos.x;
		colVerts[2].y =  0.5f * size.y + pos.y;
		
		colVerts[3].x =  0.5f * size.x + pos.x;
		colVerts[3].y = -0.5f * size.y + pos.y;
	}
	
	public void draw() {
		Renderer.imageBufferGraphics.drawImage(texture.bufferedImage, 
				(int)(pos.x - size.x/2.0f), 
				(int)(pos.y + size.y/2.0f), (int)size.x, -(int)size.y, 
				null);
	}
	
	public Vector4f[] getPoints() {
		return colVerts;
	}
}
