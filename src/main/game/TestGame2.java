package main.game;

import main.engine.core.Game;
import main.engine.core.Vector3D;

public class TestGame2 extends Game {

    private Player player;

    private static Level level;

    public void init() {

        player = new Player(new Vector3D(16, 0.475f, 16));

        addObject(player);

        level = new Level("level4.png", "wolf3D-64-full.png", 8, player);

        addObject(level);

        // Camera mainCamera = new Camera((float) Math.toRadians(70.0f), (float)
        // CoreEngine.getWidth() / (float) CoreEngine.getHeight(), 0.01f, 1000.0f);

        // GameObject cameraObject = new GameObject();

        // cameraObject.addComponent(mainCamera);

        // cameraObject.addChild(spotLightObject0);

        // addObject(cameraObject);

        // spotLightObject0.getTransform().getPos().set(0.4f, -0.5f, 0.1f);

//		addObject(new GameObject().addComponent(new Camera(
//				
//				(float) Math.toRadians(70.0f), (float) CoreEngine.getWidth() / (float) CoreEngine.getHeight(), 0.01f, 1000.0f)));

    }

    public static Level getLevel() {

        return level;

    }

    public void update() {

    }

}
