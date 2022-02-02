package main.engine.physics;

import java.util.ArrayList;
import java.util.List;

import main.engine.core.Vector3D;

public class PhysicsEngine {
	
	private List<PhysicsBody> physicsBodies;
	
	//private List<IntersectData> physicsData;
	
	public PhysicsEngine() {
		
		physicsBodies = new ArrayList<PhysicsBody>();
		
		//physicsData = new ArrayList<IntersectData>();
		
	}
	
	public void add(PhysicsBody physicsBody) {
		
		physicsBodies.add(physicsBody);
		
	}
	
	public void simulate(float delta) {
		
		for (int i = 0; i < physicsBodies.size(); i ++) {
			
			physicsBodies.get(i).integrate(delta);
			
		}
		
	}
	
	public void handleCollisions() {
		
		for (int i = 0; i < physicsBodies.size(); i ++) {
			
			for (int j = i + 1; j < physicsBodies.size(); j ++) {
				
				IntersectData intersectData = physicsBodies.get(i).getBoundary().intersect(physicsBodies.get(j).getBoundary());
				
				if (!physicsBodies.get(i).getMaterial().getPermeable() && !physicsBodies.get(j).getMaterial().getPermeable()) {
					
					if (intersectData.getIntersect()) {
						
						CollisionData collision = new CollisionData(physicsBodies.get(i), physicsBodies.get(j));
						
						physicsBodies.get(i).setVelocity(collision.getV_a());
						
						physicsBodies.get(j).setVelocity(collision.getV_b());
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public PhysicsBody getBody(int index) {
		
		return physicsBodies.get(index);
		
	}
	
	public int getNumBodies() {
		
		return physicsBodies.size();
		
	}

}
