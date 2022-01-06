package main.engine.rendering.meshLoading;

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
		
		delaunayTri();
		
	}
	
	public void init() {
		
		float a, b, theta, phi, x, y, z;
		
		for (int i = 0; i < n; i ++) {
			
			a = (i * invPHI) % 1;
			b = i / (float) n;
			
			theta = 2 * PI * (a);
			phi = (float) Math.acos(1 - 2 * b);
			
			x = (float) (Math.cos(theta) * Math.sin(phi));
			y = (float) (Math.sin(theta) * Math.sin(phi));
			z = (float) (Math.cos(phi));
			
			vertices[i] = new Vertex(	new Vector3D(theta, 0, phi), 
										new Vector2D(a, b));
			
		}
		
		indices[0] = 0;
		indices[1] = 2;
		indices[2] = 1;
		
//		indices[3] = 0;
//		indices[4] = 3;
//		indices[5] = 2;
//		
//		indices[6] = 0;
//		indices[7] = 1;
//		indices[8] = 3;
//		
//		indices[9] = 1;
//		indices[10] = 2;
//		indices[11] = 3;
		
	}
	
	public void addNoise() {
		
		
		
	}
	
	public void genMesh() {
		
		mesh =  new Mesh(vertices, indices, true);
		
	}
	
	private void delaunayTri() {
		
//		List<Simplex> delaunaySimplices = new ArrayList<Simplex>();
//		
//		Simplex test = new Simplex(vertices[0].getPos(), vertices[1].getPos(), vertices[2].getPos(), vertices[3].getPos());
//		
//		System.out.println("Center: " + test.getCSphere().getCenter());
//		
//		System.out.println("Radius Squared: " + test.getCSphere().getRadiusSq());
//		
//		for (int i = 0; i < n; i ++) {
//			
//			float diff = (test.getCSphere().getCenter().sub(vertices[i].getPos()).lengthSq()) - test.getCSphere().getRadiusSq();
//			
//			System.out.println("Diff[" + i + "]: " + diff);
//			
//		}
		
//		for (int i = 5; i < n; i ++) {
//			
//			if (test.getCSphere().getCenter().sub(vertices[i].getPos()).lengthSq() < test.getCSphere().getRadiusSq()) {
//				
//				System.out.println("Invalid delaunay simplex!");
//				
//				System.out.println("i: " + i);
//				
//				System.out.println("Sphere center: " + test.getCSphere().getCenter().toString());
//				
//				System.out.println("Sphere radius squared: " + test.getCSphere().getRadiusSq());
//				
//				System.out.println("Vertex: " + vertices[i].getPos().toString());
//				
//			}
//			
//		}
		
	}
	
	public float getRadius() {
		
		return this.radius;
		
	}
	
	public Mesh getMesh() {
		
		return this.mesh;
		
	}
	
}
