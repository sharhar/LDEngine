package engine.utils.math;

public class Vector3f extends Vector2f{
	public float z;
	
	public Vector3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}
	
	public Vector3f() {
		super();
		this.z = 0;
	}
	
	public void add(Vector3f other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}

	
	public boolean equals(Vector3f other) {
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}
	
	@Override
	public String toString() {
		return "[ " + x + " , " + y + " , " + z + " ]";
	}
	
	@Override
	public void mult(float alpha) {
		this.x *= alpha;
		this.y *= alpha;
		this.z *= alpha;
	}
}
