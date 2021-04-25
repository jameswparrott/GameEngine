package main.engine.rendering.meshLoading;

import main.engine.core.Vector3D;

public class Edge {
	
	private Vector3D start;
	
	private Vector3D end;
	
	private Vector3D mid;
	
	private float length;
	
	public Edge(Vector3D start, Vector3D end) {
		
		this.start = start;
		
		this.end = end;
		
		this.mid = end.sub(start).scale(0.5f).add(start);
		
		this.length = end.sub(start).length();
		
	}
	
	public void flip() {
		
		Vector3D temp = end;
		
		this.end = this.start;
		
		this.start = temp;
		
	}

}
