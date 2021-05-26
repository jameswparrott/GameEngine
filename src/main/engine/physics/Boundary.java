package main.engine.physics;

import java.util.ArrayList;

import main.engine.core.Vector3D;
import main.engine.rendering.Mesh;

public class Boundary {
	
	private Vector3D center;
	
	private ArrayList<Vector3D> convexBoundary;
	
	public Boundary(Vector3D center, Mesh mesh) {
		
		this.center = center;
		
		convexBoundary = computeConvexHull(mesh);
		
	}
	
	public Boundary(Vector3D center, float width, float height, float depth) {
		
		this.center = center;
		
		float dx = width * 0.5f;
		
		float dy = height * 0.5f;
		
		float dz = depth * 0.5f;
		
		convexBoundary = new ArrayList<Vector3D>();
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() + dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() + dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() + dy, center.getZ() - dz));
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() + dy, center.getZ() - dz));
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() - dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() - dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() - dy, center.getZ() - dz));
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() - dy, center.getZ() - dz));
		
	}
	
	public Boundary(Vector3D center, Vector3D corner) {
		
		this.center = center;
		
		float dx = corner.getX() - center.getX();
		
		float dy = corner.getY() - center.getY();
		
		float dz = corner.getZ() - center.getZ();
		
		convexBoundary = new ArrayList<Vector3D>();
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() + dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() + dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() + dy, center.getZ() - dz));
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() + dy, center.getZ() - dz));
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() - dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() - dy, center.getZ() + dz));
		
		convexBoundary.add(new Vector3D(center.getX() - dx, center.getY() - dy, center.getZ() - dz));
		
		convexBoundary.add(new Vector3D(center.getX() + dx, center.getY() - dy, center.getZ() - dz));
		
	}
	
	private static ArrayList<Vector3D> computeConvexHull(Mesh mesh) {
		
		//TODO: Compute the convex hull from a mesh
		
		return new ArrayList<Vector3D>();
		
	}
	
	public ArrayList<Vector3D> getBoundary(){
		
		return this.convexBoundary;
		
	}
	
	public void setBoundary(ArrayList<Vector3D> boundary) {
		
		this.convexBoundary = boundary;
		
	}
	
	public Vector3D getCenter() {
		
		return this.center;
		
	}

}
