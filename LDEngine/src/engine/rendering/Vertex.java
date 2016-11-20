package engine.rendering;

import engine.utils.math.Color;
import engine.utils.math.Vector2f;
import engine.utils.math.Vector4f;

public class Vertex extends Vector4f{
	public Color color;
	public Vector2f uv;
	
	public Vertex() {
		super();
		this.color = null;
		this.uv = null;
	}
	
	public Vertex(Color color) {
		super();
		this.color = color;
		this.uv = null;
	}
	
	public Vertex(Vector2f uv) {
		super();
		this.color = null;
		this.uv = uv;
	}
	
	public Vertex(Vector2f uv, Color color) {
		super();
		this.color = color;
		this.uv = uv;
	}
	
	public Vertex(float x, float y, float z, float w, Color color) {
		super(x, y, z, w);
		this.color = color;
		this.uv = null;
	}
	
	public Vertex(float x, float y, float z, float w, Vector2f uv) {
		super(x, y, z, w);
		this.color = null;
		this.uv = uv;
	}
	
	public Vertex(float x, float y, float z, float w, Vector2f uv, Color color) {
		super(x, y, z, w);
		this.color = color;
		this.uv = uv;
	}
	
	public Vertex divideZ() {
		this.x = x/this.z;
		this.y = y/this.z;
		
		return this;
	}
}
