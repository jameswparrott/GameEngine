package main.engine.rendering.meshLoading;

import main.engine.core.Vector3D;

public class Simplex {
	
	private Vector3D a, b, c, d;
	
	private Sphere cSphere;
	
	public Simplex(Vector3D a, Vector3D b, Vector3D c, Vector3D d) {
		
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		
		this.cSphere = calcCSphere(calcMid(a, b, c, d), a);
		
	}
	
	private static Vector3D calcMid(Vector3D a, Vector3D b, Vector3D c, Vector3D d) {
		
		Vector3D u1 = b.sub(a);
		Vector3D u2 = c.sub(a);
		Vector3D u3 = d.sub(a);
		
		Vector3D dual = u2.cross(u3).scale(u1.lengthSq());
		
		dual = dual.add(u3.cross(u1).scale(u2.lengthSq()));
		
		dual = dual.add(u1.cross(u2).scale(u3.lengthSq()));
		
		float triProd =  1.0f / (2 * u1.dot(u2.cross(u3)));
		
		return a.add(dual.scale(triProd));
		
	}
	
	public static Sphere calcCSphere(Vector3D mid, Vector3D point) {
		
		return new Sphere(mid, point.sub(mid).lengthSq());
		
	}
	
	public void calcMesh() {
		
		
		
	}
	
	public void removeEdge() {
		
		
		
	}
	
	public void removeFace() {
		
		
		
	}
	
	public Sphere getCSphere() {
		
		return this.cSphere;
		
	}

}
