package engine.utils.math;

import engine.rendering.Vertex;

public class Matrix4f {
	public float[][] data;
	
	public Matrix4f() {
		data = new float[4][4];
		
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j < 4;j++) {
				data[i][j] = i == j ? 1 : 0;
			}
		}
	}
	
	public Matrix4f(float[][] data) {
		this.data = data;
	}
	
	public Matrix4f mult(Matrix4f other) {
		float[][] dat = new float[4][4];
		
		float acc = 0;
		for(int i = 0;i < 4;i++) {
			for(int j = 0;j < 4;j++) {
				acc = 0;
				for(int k = 0;k < 4;k++) {
					acc += data[i][k] * other.data[k][j];
				}
				dat[i][j] = acc;
			}
		}
		
		return new Matrix4f(dat);
	}
	
	public void move(Vector3f offset) {
		Matrix4f temp = new Matrix4f();
		
		temp.data[0][3] = offset.x;
		temp.data[1][3] = offset.y;
		temp.data[2][3] = offset.z;
		
		this.data = this.mult(temp).data;
	}
	
	public void scale(Vector3f offset) {
		Matrix4f temp = new Matrix4f();
		
		temp.data[0][0] = offset.x;
		temp.data[1][1] = offset.y;
		temp.data[2][2] = offset.z;
		
		this.data = this.mult(temp).data;
	}
	
	public void rotateX(float angle) {
		Matrix4f temp = new Matrix4f();
		
		temp.data[1][1] = (float)Math.cos(angle);
        temp.data[1][2] = (float)-Math.sin(angle);
        temp.data[2][1] = (float)Math.sin(angle);
        temp.data[2][2] = (float)Math.cos(angle);
		
		this.data = this.mult(temp).data;
	}
	
	public void rotateY(float angle) {
		Matrix4f temp = new Matrix4f();
		
		temp.data[2][2] = (float)Math.cos(angle);
        temp.data[2][0] = (float)-Math.sin(angle);
        temp.data[0][2] = (float)Math.sin(angle);
        temp.data[0][0] = (float)Math.cos(angle);
		
		this.data = this.mult(temp).data;
	}
	
	public void rotateZ(float angle) {
		Matrix4f temp = new Matrix4f();
		
		temp.data[0][0] = (float)Math.cos(angle);
        temp.data[0][1] = (float)-Math.sin(angle);
        temp.data[1][0] = (float)Math.sin(angle);
        temp.data[1][1] = (float)Math.cos(angle);
		
		this.data = this.mult(temp).data;
	}
	
	public Vector4f mult(Vector4f v) {
		Vector4f result = new Vector4f();
		
		result.x = v.x*data[0][0] + v.y*data[0][1] + v.z*data[0][2] + v.w*data[0][3];
		result.y = v.x*data[1][0] + v.y*data[1][1] + v.z*data[1][2] + v.w*data[1][3];
		result.z = v.x*data[2][0] + v.y*data[2][1] + v.z*data[2][2] + v.w*data[2][3];
		result.w = v.x*data[3][0] + v.y*data[3][1] + v.z*data[3][2] + v.w*data[3][3];
		
		return result;
	}
	
	public Vertex mult(Vertex v) {
		Vertex result = new Vertex(v.uv, v.color);
		
		result.x = v.x*data[0][0] + v.y*data[0][1] + v.z*data[0][2] + v.w*data[0][3];
		result.y = v.x*data[1][0] + v.y*data[1][1] + v.z*data[1][2] + v.w*data[1][3];
		result.z = v.x*data[2][0] + v.y*data[2][1] + v.z*data[2][2] + v.w*data[2][3];
		result.w = v.x*data[3][0] + v.y*data[3][1] + v.z*data[3][2] + v.w*data[3][3];
		
		return result;
	}
	
	public String toString() {
		String result = "";
		
		result += "[ " + data[0][0] + " , " + data[0][1] + " , " + data[0][2] + " , " + data[0][3] + " ]\n";
		result += "[ " + data[1][0] + " , " + data[1][1] + " , " + data[1][2] + " , " + data[1][3] + " ]\n";
		result += "[ " + data[2][0] + " , " + data[2][1] + " , " + data[2][2] + " , " + data[2][3] + " ]\n";
		result += "[ " + data[3][0] + " , " + data[3][1] + " , " + data[3][2] + " , " + data[3][3] + " ]\n";
		
		return result;
	}
}
