package main.engine.rendering.meshLoading;

import java.util.ArrayList;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Mesh;
import main.engine.rendering.Vertex;

public class FibonacciSphere {

	private int n;
	
	private float radius;
	
	private static final float PHI = 1.61803398874f;
	
	private static final float invPHI = 0.61803398875f;
	
	private static final float PI = 3.14159265358f;
	
	private Vertex[] vertices;
	
	private int[] indices;
	
	private Mesh mesh;
	
	public FibonacciSphere(int n, float radius) {
		
		this.n = n;
		
		this.radius = radius;
		
		vertices = new Vertex[n];
		
		indices = new int[n * 6];
		
		init();
		
	}
	
	public void init() {
		
		float a, b, theta, phi, x, y, z;
		
		for (int i = 0; i < n; i ++) {
			
			a = (i * invPHI) % 1;
			b = i / (float) n;
			
			System.out.println("i: " + i  + ",  a: " + a + ",  b: " + b);
			
			theta = 2 * PI * (a);
			phi = (float) Math.acos(1 - 2 * b);
			
			System.out.println(" theta: " + theta + ",  psi: " + phi);
			
			x = (float) (Math.cos(theta) * Math.sin(phi));
			y = (float) (Math.sin(theta) * Math.sin(phi));
			z = (float) (Math.cos(phi));
			
			System.out.println("x: " + x + ",  y: " + y + ",  z: " + z);
			
			vertices[i] = new Vertex(	new Vector3D(x, y, z), 
										new Vector2D(a, b));
			
			//new Vector2D(theta / (float) 360, psi / (float) 180)
			
			//System.out.println(vertices[i].getPos().toString());
			
		}
		
		indices[0] = 0;
		indices[1] = 2;
		indices[2] = 1;
		
		indices[3] = 0;
		indices[4] = 3;
		indices[5] = 2;
		
		indices[6] = 0;
		indices[7] = 1;
		indices[8] = 3;
		
		indices[9] = 1;
		indices[10] = 2;
		indices[11] = 3;
		
	}
	
	public void addNoise() {
		
		
		
	}
	
	public void genMesh() {
		
		mesh =  new Mesh(vertices, indices, true);
		
	}
	
	private void delaunayTriangulation() {
		
		
		
	}
	
	public float getRadius() {
		
		return this.radius;
		
	}
	
	public Mesh getMesh() {
		
		return this.mesh;
		
	}
	
}
