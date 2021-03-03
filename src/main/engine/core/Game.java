package main.engine.core;

import main.engine.rendering.RenderingEngine;
import main.game.Hud;

public abstract class Game {
	
	private GameObject root;
	
	private Hud hud = new Hud();
	
	public void init() {
		
		
		
	}
	
	public void input(float delta) {
		
		getRootObject().input(delta);
		
	}
	
	public void update(float delta) {
		
		getRootObject().update(delta);
		
	}
	
	public void render(RenderingEngine renderingEngine) {
		
		renderingEngine.render(getRootObject(), hud);
		
	}
	
	public void addObject(GameObject gameObject) {
		
		getRootObject().addChild(gameObject);
		
	}
	
	private GameObject getRootObject() {
		
		if(root == null)
			
			root = new GameObject();
		
		return root;
		
	}
	
}
