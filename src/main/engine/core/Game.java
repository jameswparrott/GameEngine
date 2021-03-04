package main.engine.core;

import main.engine.rendering.RenderingEngine;
import main.game.Hud;

public abstract class Game {
	
	private GameObject root;
	
	//Probably not a good way of doing this...
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
		
		//Yuck, but what to do when there are other things to render besides the gameworld..?
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
