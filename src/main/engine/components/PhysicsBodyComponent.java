package main.engine.components;

import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.physics.PhysicsBody;
import main.game.Door;
import main.game.Level;

public class PhysicsBodyComponent extends GameComponent {
	
	private PhysicsBody physicsBody;
	
	/**
	 * Constructs a physics body component allowing game objects 
	 * to interact with each other via the physics engine.
	 * @param physicsBody physics body this component will use
	 */
	public PhysicsBodyComponent(PhysicsBody physicsBody) {
		
		this.physicsBody = physicsBody;
		
	}
	
	@Override
	public void input(float delta) {
		
		//TODO: take some input 
		
	}
	
	@Override
	public void update(float delta) {
		
		getTransform().setPos(physicsBody.getPos());
		
		getTransform().rotate(physicsBody.getAngularVelocity(), physicsBody.getAngularVelocity().length());
		
		physicsBody.getBoundary().update(getTransform());
		
	}
	
	public PhysicsBody getPhysicsBody() {
		
		return this.physicsBody;
		
	}
	
}
