package main.engine.rendering.meshLoading;

import main.engine.core.Vector3D;

public class Simplex {
	
	private Vector3D a, b, c, d;
	
	private int i, j, k, l;
	
	private Vector3D mid;
	
	private Sphere circumScribeSphere;
	
	public Simplex(Vector3D a, Vector3D b, Vector3D c, Vector3D d, int i, int j, int k, int l) {
		
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		
		this.i = i;
		this.j = j;
		this.k = k;
		this.l = l;
		
	}
	
	public void calcSphere() {
		
		this.circumScribeSphere = new Sphere(this.mid, a.sub(mid).lengthSq());
		
	}

}
