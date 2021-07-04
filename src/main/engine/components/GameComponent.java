package main.engine.components;

import main.engine.core.GameObject;
import main.engine.core.Transform;
import main.engine.physics.PhysicsEngine;
import main.engine.rendering.RenderingEngine;
import main.engine.rendering.Shader;

public abstract class GameComponent {
	
	private GameObject parent;
	
	public void input(float delta) {
		
	}
	
	public void update(float delta) {
		
	}
	
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
	}
	
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		
	}
	
	public void addToPhysicsEngine(PhysicsEngine physicsEngine) {	
		
	}
	
	public void setParent(GameObject parent) {
		
		this.parent = parent;
		
	}
	
	public Transform getTransform() {
		
		return parent.getTransform();
		
	}
	
}
