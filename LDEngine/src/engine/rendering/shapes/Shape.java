package engine.rendering.shapes;

import engine.utils.math.Vector4f;

abstract public class Shape implements Polygon{
	public Vector4f pos;
	public Vector4f size;
	public Vector4f[] colVerts;
	
	public Shape(Vector4f pos, Vector4f size) {
		this.pos = pos;
		this.size = size;
	}
	
	abstract public void updateCol();
	abstract public void draw();
}
