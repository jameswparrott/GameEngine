package main.engine.rendering;

import main.engine.core.GameObject;

public interface Hud {

	GameObject[] getHudObjects();
	
	default void cleanUp() {
		
		GameObject[] hudObjects = getHudObjects();
		
		for(GameObject hudObject : hudObjects) {
			
			//hudObject.cleanUp();
			
		}
		
	}
	
}
