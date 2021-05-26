package main.engine.rendering.meshLoading;

import java.util.List;

import main.engine.core.Vector3D;

public class Face {
	
	private Vector3D a, b, c, normal, mid;
	
	private int i, j, k;
	
	private List<Vector3D> vertices;
	
	private List<Integer> indices;
	
	public Face(Vector3D a, Vector3D b, Vector3D c, int i, int j, int k) {
		
		this.a = a;
		this.b = b;
		this.c = c;
		
		indices.add(i);
		indices.add(j);
		indices.add(k);
		
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		
	}
	
	public void flip() {
		
		Vector3D tempVec = this.a;
		this.a = this.c;
		this.c = tempVec;
		
		int tempInt = this.i;
		this.i = this.k;
		this.k = tempInt;
		
	}
	
	public void rot() {
		
		Vector3D tempVec = this.a;
		this.a = this.c;
		this.c = this.b;
		this.b = tempVec;
		
		int tempInt = this.i;
		this.i = this.k;
		this.k = this.j;
		this.j = tempInt;
		
	}
	
	public boolean equals(Face face) {
		
		return 	(a == face.a && b == face.b && c == face.c) ||
				(a == face.a && b == face.c && c == face.b) ||
				(a == face.b && b == face.c && c == face.a) ||
				(a == face.b && b == face.a && c == face.c) ||
				(a == face.c && b == face.a && c == face.b) ||
				(a == face.c && b == face.b && c == face.a);
		
	}
	
	public List<Vector3D> getVertices() {
		
		return vertices;
		
	}
	
	public List<Integer> getIndices() {
		
		return indices;
		
	}

}
