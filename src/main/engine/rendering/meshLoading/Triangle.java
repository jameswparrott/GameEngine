package main.engine.rendering.meshLoading;

import main.engine.core.Vector3D;

public class Triangle {
	
	private Vector3D a, b, c, normal;
	
	private int i, j, k;
	
	private Vector3D mid;
	
	public Triangle(Vector3D a, Vector3D b, Vector3D c, int i, int j, int k) {
		
		this.a = a;
		this.b = b;
		this.c = c;
		
	}
	
	public void flip() {
		
		Vector3D tempVec = this.a;
		
		int tempInt = this.i;
		
		this.a = this.c;
		
		this.i = this.k;
		
		this.c = tempVec;
		
		this.k = tempInt;
		
	}

}
