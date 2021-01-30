package main.engine.physics.BoundPrimitives;

import main.engine.core.Vector3D;

public class AABB {
	
	Vector3D center;
	
	float width, height, depth;
	
	public AABB(Vector3D center, float width, float height, float depth) {
		
		this.center = center;
		
		this.width = width;
		
		this.height = height;
		
		this.depth = depth;
		
	}
	
	

}
