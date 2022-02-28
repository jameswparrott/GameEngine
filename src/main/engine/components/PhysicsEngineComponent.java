package main.engine.components;

import main.engine.physics.PhysicsEngine;

public class PhysicsEngineComponent extends GameComponent {

	private PhysicsEngine physicsEngine;
	
	public PhysicsEngineComponent(PhysicsEngine physicsEngine) {
		
		this.physicsEngine = physicsEngine;
		
	}
	
	@Override
	public void update(float delta) {
		
		//handle collisions from previous step
		
		physicsEngine.handleCollisions(delta);
		
		//simulate next step
		
		physicsEngine.simulate(delta);
		
		//boundary positions are updated after
		
	}
	
	public PhysicsEngine getPhysicsEngine() {
		
		return physicsEngine;
		
	}
	
	public void add(PhysicsBodyComponent physicsBodyComponent) {
		
		physicsEngine.add(physicsBodyComponent.getPhysicsBody());
		
	}
	
}
