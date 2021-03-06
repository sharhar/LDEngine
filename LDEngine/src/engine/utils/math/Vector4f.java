package engine.utils.math;

public class Vector4f extends Vector3f{
	public float w;
	
	public Vector4f(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}
	
	public Vector4f() {
		super();
		this.w = 1;
	}
	
	public void add(Vector4f other) {
		x += other.x;
		y += other.y;
		z += other.z;
		w += other.w;
	}
	
	public boolean equals(Vector4f other) {
		return this.x == other.x && this.y == other.y && this.z == other.z && this.w == other.w;
	}
	
	@Override
	public String toString() {
		return "[ " + x + " , " + y + " , " + z + " , " + w + " ]";
	}
	
	@Override
	public void mult(float alpha) {
		this.x *= alpha;
		this.y *= alpha;
		this.z *= alpha;
		this.w *= alpha;
	}
}
