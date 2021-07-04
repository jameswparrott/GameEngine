package main.engine.physics;

import java.util.ArrayList;
import java.util.List;

import main.engine.components.PhysicsBody;

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
			
			//for (int j = i + 1; j < physicsBodies.size(); j ++) {
				
				//data = physicsBodies.get(i);
				
				//physicsData = physicsBodies.get(i).getBoundary();
				
			//}
			
		}
		
	}

}
