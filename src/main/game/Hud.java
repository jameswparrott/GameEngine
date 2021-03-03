package main.game;

import main.engine.core.GameObject;
import main.engine.rendering.IHud;

public class Hud implements IHud{
	
	private GameObject rootHudObject;
	
	public Hud() {
		
	}

	@Override
	public GameObject[] getHudObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObject getRootObject() {
		
		if (rootHudObject == null) {
			
			rootHudObject = new GameObject();
			
		}
		
		return rootHudObject;
	}

}
