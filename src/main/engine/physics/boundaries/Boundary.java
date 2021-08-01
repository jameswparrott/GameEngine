package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public abstract class Boundary {
	
	public enum boundaryType{
		
		TYPE_PLANE,
		
		TYPE_SPHERE,
		
		TYPE_AABB,
		
		TYPE_CMB;
		
	};
	
	private boundaryType type;
	
	private Vector3D pos;
	
	public Boundary(boundaryType type, Vector3D pos) {
		
		this.type = type;
		
		this.pos = pos;
		
	}
	
	public IntersectData intersect(Boundary other) {
		
		switch(this.getType()) {
		
		case TYPE_SPHERE:
			
			Sphere sphere = (Sphere)this;
			
			switch(other.getType()) {
			
			case TYPE_SPHERE:
				
				Sphere otherSphere = (Sphere)other;
				
				return sphere.intersect(otherSphere);
				
			case TYPE_AABB:
				
				AABB otherAABB = (AABB) other;
				
				return sphere.intersect(otherAABB);
				
			case TYPE_PLANE:
				
				Plane otherPlane = (Plane) other;
				
				return sphere.intersect(otherPlane);
				
			case TYPE_CMB:
				
			default:
				
				System.err.println("Could not intersect specified boundaries. Second unknown!");
				
				break;
			
			}
			
		case TYPE_AABB:
			
			AABB aabb = (AABB)this;
			
			switch(other.getType()) {
			
			case TYPE_SPHERE:
				
				Sphere otherSphere = (Sphere)other;
				
				return aabb.intersect(otherSphere);
				
			case TYPE_AABB:
				
				AABB otherAABB = (AABB)other;
				
				return aabb.intersect(otherAABB);
				
			case TYPE_PLANE:
				
			case TYPE_CMB:
				
			default:
				
				System.err.println("Could not intersect specified boundaries. Second unknown!");
				
				break;
			
			}
			
		case TYPE_PLANE:
			
			Plane plane = (Plane)this;
			
			switch(other.getType()) {
			
			case TYPE_SPHERE:
				
				Sphere otherSphere = (Sphere)other;
				
				return plane.intersect(otherSphere);
				
			case TYPE_AABB:
				
			case TYPE_PLANE:
				
			case TYPE_CMB:
				
			default:
				
				System.err.println("Could not intersect specified boundaries. Second unknown!");
				
				break;
			
			}
			
		case TYPE_CMB:
			
			CMB cmb = (CMB)this;
			
			switch(other.getType()) {
			
			case TYPE_SPHERE:
				
				Sphere otherSphere = (Sphere)other;
				
				return cmb.intersect(otherSphere);
				
			case TYPE_AABB:
				
			case TYPE_PLANE:
				
			case TYPE_CMB:
				
			default:
				
				System.err.println("Could not intersect specified boundaries. Second unknown!");
				
				break;
			
			}
			
		default:
			
			System.err.println("Could not intersect specified boundaries. First unknown!");
			
			break;
		
		}
		
		return null;
		
	}
	
	public boundaryType getType() {
		
		return type;
		
	}
	
	public Vector3D getPos() {
		
		return pos;
		
	}
	
	public void setPos(Vector3D pos) {
		
		this.pos = pos;
		
	}
	
}
