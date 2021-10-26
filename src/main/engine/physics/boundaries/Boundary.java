package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.CollisionData;
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
	
	
	public abstract IntersectData intersect(Boundary boundary);
	
	public abstract CollisionData collide(Boundary boundary);
	
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
