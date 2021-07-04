package main.engine.physics.boundaries;

import main.engine.core.Transform;
import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public abstract class Boundary {
	
	static enum Type {
		
		Plane,
		
		Sphere,
		
		AABB,
		
		CMB
		
	}
	
	private Vector3D pos;
	
	private Type type;
	
	public Boundary(Vector3D pos, Type type) {
		
		this.pos = pos;
		
		this.type = type;
		
	}
	
	public IntersectData intersect(Boundary boundary) {
		
		return new IntersectData(false, 0, 0);
		
	}
	
	public void update(Transform transform) {
		
		this.pos = transform.getPos();
		
	}
	
	public Type getType() {
		
		return type;
		
	}
	
	public Vector3D getPos() {
		
		return pos;
		
	}
	
	public void setPos(Vector3D pos) {
		
		this.pos = pos;
		
	}
	
}
