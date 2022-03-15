package main.engine.rendering;

import main.engine.core.GameObject;

public interface IHud {

    GameObject[] getHudObjects();

    GameObject getRootObject();

    default void cleanUp() {

        GameObject[] hudObjects = getHudObjects();

        for (GameObject hudObject : hudObjects) {

            // TODO: Introduce cleanup method for all hud objects!

            // hudObject.cleanUp();

        }

    }

}
