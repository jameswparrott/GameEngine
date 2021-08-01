package main.engine.physics;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;

import main.engine.components.PhysicsBody;
import main.engine.core.Input;

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
				
				if (Input.getKeyPressed(GLFW_KEY_I)) {
					
					System.out.println("A pos: " + physicsBodies.get(i).getPos().toString());
					
					System.out.println("B pos: " + physicsBodies.get(j).getPos().toString());
					
				}
				
				if (intersectData.getIntersect()) {
					
					System.out.println("Collision!");
					
					System.out.println("Distance to center: " + intersectData.getDistanceToCenter());
					
					System.out.println("Distance to boundary: " + intersectData.getDistanceToBoundary());
					
					//System.out.println("Object 1 position: " + physicsBodies.get(i).getPos().toString());
					
					//System.out.println("Object 2 position: " + physicsBodies.get(j).getPos().toString());
					
					physicsBodies.get(i).setVelocity(physicsBodies.get(i).getVelocity().getScaled(-1.0f));
					
					physicsBodies.get(j).setVelocity(physicsBodies.get(j).getVelocity().getScaled(-1.0f));
					
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
