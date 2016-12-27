package engine.rendering.shapes;

import engine.rendering.Renderer;
import engine.rendering.Texture;
import engine.rendering.Vertex;
import engine.utils.math.Matrix4f;
import engine.utils.math.Vector2f;
import engine.utils.math.Vector3f;
import engine.utils.math.Vector4f;

public class Quad extends QuadAA{
	public static Vertex[] sverts = new Vertex[4];
	
	static {
		sverts[0] = new Vertex( -0.5f, -0.5f, 0, 1, new Vector2f(0, 1));
		sverts[1] = new Vertex( -0.5f,  0.5f, 0, 1, new Vector2f(0, 0));
		sverts[2] = new Vertex(  0.5f,  0.5f, 0, 1, new Vector2f(1, 0));
		sverts[3] = new Vertex(  0.5f, -0.5f, 0, 1, new Vector2f(1, 1));
	}
	
	public static Vertex[] vverts = new Vertex[4];
	
	public float r;
	private Vertex[] psverts;
	
	public Quad(Vector2f pos, Vector2f size, Texture texture, float r, Vertex[] va) {
		super(pos, size, texture);
		this.r = r;
		
		psverts = va;
	}
	
	public Quad(Vector2f pos, Vector2f size, Texture texture, float r) {
		super(pos, size, texture);
		this.r = r;
		
		psverts = sverts;
	}
	
	public void draw() {
		Matrix4f transformation = new Matrix4f();
		transformation.move(new Vector3f(pos.x, pos.y, 0));
		transformation.rotateZ(r);
		transformation.scale(new Vector3f(size.x, size.y, 1));
			
		Matrix4f mat = Renderer.projection.mult(transformation);
			
		vverts[0] = mat.mult(psverts[0]);
		vverts[1] = mat.mult(psverts[1]);
		vverts[2] = mat.mult(psverts[2]);
		vverts[3] = mat.mult(psverts[3]);
			
		Renderer.drawTriSmartTextured(vverts[0], vverts[1], vverts[2], texture);
		Renderer.drawTriSmartTextured(vverts[0], vverts[3], vverts[2], texture);
	}
	
	@Override
	public void updateCol() {
		Matrix4f transformation = new Matrix4f();
		transformation.move(new Vector3f(pos.x, pos.y, 0));
		transformation.rotateZ(r);
		transformation.scale(new Vector3f(size.x, size.y, 1));
			
		Matrix4f mat = Renderer.projection.mult(transformation);
		
		colVerts[0] = mat.mult(psverts[0]);
		colVerts[1] = mat.mult(psverts[1]);
		colVerts[2] = mat.mult(psverts[2]);
		colVerts[3] = mat.mult(psverts[3]);
	}
	
	@Override
	public Vector4f[] getPoints() {
		return colVerts;
	}
}
