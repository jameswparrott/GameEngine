package main.engine.core;

public class Matrix4x4 {

	private float[][] m;
	
	public Matrix4x4() {
		
		m = new float[4][4];
		
	}
	
	public Matrix4x4 initI() {
		
		for (byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
				
				if (i == j) {
					
					m[i][j] = 1;
					
				}
				
				else
					
					m[i][j] = 0;
				
			}
			
		}
		
		return this;
		
	}
	
	public Matrix4x4 initTranslation(float x, float y, float z) {
		
		for (byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
				
				if (i == j) {
					
					m[i][j] = 1;
					
				}
				
				else
					
					m[i][j] = 0;
				
			}
			
		}
		
		m[0][3] = x;
		
		m[1][3] = y;
		
		m[2][3] = z;
		
		return this;
		
	}
	
	public Matrix4x4 initRotation(float x, float y, float z) {
		
		Matrix4x4 rx = new Matrix4x4();
		
		Matrix4x4 ry = new Matrix4x4();
		
		Matrix4x4 rz = new Matrix4x4();
		
		x = (float) Math.toRadians(x);
		
		y = (float) Math.toRadians(y);
		
		z = (float) Math.toRadians(z);
		
		for (byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
				
				if (i == j) {
					
					rx.set(i, j, 1);
					
					ry.set(i, j, 1);
					
					rz.set(i, j, 1);
					
				}
				
				else {
					
					rx.set(i, j, 0);
				
					ry.set(i, j, 0);
					
					rz.set(i, j, 0);
					
				}
				
			}
			
		}
		
		rx.set(1, 1, (float) Math.cos(x));
		
		rx.set(1, 2, (float) -Math.sin(x));
		
		rx.set(2, 1, (float) Math.sin(x));
		
		rx.set(2, 2, (float) Math.cos(x));
		
		ry.set(0, 0, (float) Math.cos(y));
		
		ry.set(0, 2, (float) -Math.sin(y));
		
		ry.set(2, 0, (float) Math.sin(y));
		
		ry.set(2, 2, (float) Math.cos(y));
		
		rz.set(0, 0, (float) Math.cos(z));
		
		rz.set(0, 1, (float) -Math.sin(z));
		
		rz.set(1, 0, (float) Math.sin(z));
		
		rz.set(1, 1, (float) Math.cos(z));
		
		return rz.mult(ry.mult(rx));
		
	}
	
	public Matrix4x4 initScale(float x, float y, float z) {
		
		for (byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
				
					
					m[i][j] = 0;
				
			}
			
		}
		
		m[0][0] = x;
		
		m[1][1] = y;
		
		m[2][2] = z;
		
		m[3][3] = 1;
		
		return this;
		
	}
	
	public Matrix4x4 initPerspective(float fov, float aspect, float zNear, float zFar) {
		
		float tanHalfFOV = (float)Math.tan(fov/2);
		
		float zRange = zNear - zFar;
		
		for (byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
				
					
					m[i][j] = 0;
				
			}
			
		}
		
		m[0][0] = 1.0f/(tanHalfFOV * aspect);
		
		m[1][1] = 1.0f/tanHalfFOV;
		
		m[2][2] = (-zNear - zFar)/zRange;
		
		m[2][3] = 2 * zFar * zNear / zRange;
		
		m[3][2] = 1;
		
		return this;
		
	}
	
	public Matrix4x4 initOrthographic(float left, float right, float bottom, float top, float near, float far) {
		
		float width = right - left;
		
		float height = top - bottom;
		
		float depth = far - near;
		
		for (byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
					
					m[i][j] = 0;
				
			}
			
		}
		
		m[0][0] = 2/width;
		
		m[1][1] = 2/height;
		
		//Positive value because fuck screen coordinates
		
		m[2][2] = 2/depth;
		
		m[0][3] = -(right + left)/width;
		
		m[1][3] = -(top + bottom)/height;
		
		m[2][3] = -(far + near)/depth;
		
		m[3][3] = 1;
		
		return this;
		
	}
	
	public Matrix4x4 initOrthoScale(float left, float right, float bottom, float top, float near, float far) {
		
		float width = right - left;
		
		float height = top - bottom;
		
		float depth = far - near;
		
		for (byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
					
					m[i][j] = 0;
				
			}
			
		}
		
		m[0][0] = 2/width;
		
		m[1][1] = 2/height;
		
		m[2][2] = 2/depth;
		
		m[3][3] = 1;
		
		return this;
		
	}
	
	public Matrix4x4 initRotation(Vector3D forward, Vector3D up) {
		
		Vector3D f = forward.getNorm();
		
		Vector3D u = up.getNorm();
		
		Vector3D r = up.cross(f).getNorm();

		return initRotation(f, u, r);
		
	}
	
	public Matrix4x4 initRotation(Vector3D forward, Vector3D up, Vector3D right) {
		
		Vector3D f = forward;
		
		Vector3D r = right;
		
		Vector3D u = up;
		
		m[0][0] = r.getX();
		
		m[0][1] = r.getY();
		
		m[0][2] = r.getZ();
		
		m[0][3] = 0;
		
		m[1][0] = u.getX();
		
		m[1][1] = u.getY();
		
		m[1][2] = u.getZ();
		
		m[1][3] = 0;
		
		m[2][0] = f.getX();
		
		m[2][1] = f.getY();
		
		m[2][2] = f.getZ();
		
		m[2][3] = 0;
		
		m[3][0] = 0;
		
		m[3][1] = 0;
		
		m[3][2] = 0;
		
		m[3][3] = 1;
		
		return this;
		
	}
	
	public Vector3D transform(Vector3D v) {
		
		float x = m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3];
		
		float y = m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3];
		
		float z = m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3];
		
		return new Vector3D(x, y, z);
		
	}
	
	public Quaternion transform(Quaternion q) {
		
		float x = m[0][0] * q.getX() + m[0][1] * q.getY() + m[0][2] * q.getZ() + m[0][3] * q.getW();
		
		float y = m[1][0] * q.getX() + m[1][1] * q.getY() + m[1][2] * q.getZ() + m[1][3] * q.getW();
		
		float z = m[2][0] * q.getX() + m[2][1] * q.getY() + m[2][2] * q.getZ() + m[2][3] * q.getW();
		
		float w = m[2][0] * q.getX() + m[2][1] * q.getY() + m[2][2] * q.getZ() + m[2][3] * q.getW();
		
		return new Quaternion(x, y, z, w);
		
	}
	
	public Matrix4x4 mult(Matrix4x4 m2) {
		
		Matrix4x4 m3 = new Matrix4x4();
		
		for(byte i = 0; i < 4; i++) {
			
			for (byte j = 0; j < 4; j++) {
				
				float f = 0;
				
				for (byte k = 0; k < 4; k++) {
					
					f = f + m[i][k]*m2.get(k, j);
					
				}
				
				m3.set(i, j, f);
				
			}
			
		}
		
		return m3;
		
	}
	
	

	public float[][] getM() {
		
		return m;
		
	}
	
	public float get(int x, int y) {
		
		return m[x][y];
		
	}
	
	public void setM(float[][] m) {
		
		this.m = m;
		
	}
	
	public void set(int x, int y, float val) {
		
		m[x][y] = val;
		
	}
	
	public void print() {
		
		for (byte i = 0; i < 4; i++) {
			
			System.out.print("[");
			
			for (byte j = 0; j < 4; j++) {
					
					System.out.print(m[i][j]);
					
					if (j < 3)
						System.out.print(", ");
				
			}
			
			System.out.println("]");
			
		}
		
	}
	
}