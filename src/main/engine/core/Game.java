package main.engine.core;

import main.engine.rendering.RenderingEngine;

public abstract class Game {
	
	private GameObject root;
	
	private GameObject hudRoot;
	
	public void init() {
		
	}
	
	public void input(float delta) {
		
		getRootObject().input(delta);
		
		getHudRoot().update(delta);
		
	}
	
	public void update(float delta) {
		
		getRootObject().update(delta);
		
		getHudRoot().update(delta);
		
	}
	
	public void render(RenderingEngine renderingEngine) {

		renderingEngine.render(getRootObject(), getHudRoot());
		
	}
	
	public void addObject(GameObject gameObject) {
		
		getRootObject().addChild(gameObject);
		
	}
	
	public void addHudObject(GameObject hudObject) {
		
		getHudRoot().addChild(hudObject);
		
	}
	
	private GameObject getRootObject() {
		
		if(root == null)
			
			root = new GameObject();
		
		return root;
		
	}
	
	private GameObject getHudRoot() {
		
		if(hudRoot == null)
			
			root = new GameObject();
		
		return hudRoot;
		
	}
	
}
