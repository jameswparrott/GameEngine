package main.game;

import main.engine.components.DirectionalLight;
import main.engine.components.MeshRenderer;
import main.engine.components.PointLight;
import main.engine.core.Game;
import main.engine.core.GameObject;
import main.engine.core.Quaternion;
import main.engine.core.Vector3D;
import main.engine.rendering.Material;
import main.engine.rendering.Mesh;
import main.engine.rendering.TextObject;
import main.engine.rendering.Texture;
import main.engine.rendering.meshLoading.Terrain;

public class TestGame extends Game {
	
	private Player player;
	
	private static Level level;
	
	public void init() {
		
		player = new Player(new Vector3D(0, 0.475f, 0));
		addObject(player);
		
//		level = new Level("level4.png", "wolf3D-64-full.png", 8, player);
//		addObject(level);
//		Enemy ss = new Enemy(new Vector3D(16, 0.35f, 16.5f), player);
//		addObject(ss);
		
		GameObject terrainObject = new GameObject();
		Terrain terrain = new Terrain(20, 10, 100);
		Material terrainMaterial = new Material(new Texture("tiledfloor.png"), 0.5f, 2f, new Texture("tiledfloor_normal.png"));
		MeshRenderer terrainRenderer = new MeshRenderer(terrain.getMesh(), terrainMaterial);
		terrainObject.addComponent(terrainRenderer);
		addObject(terrainObject);
		
//		GameObject planeObject = new GameObject();
//		Material planeMaterial = new Material(new Texture("008-brownstone.png"), 1, 8, new Texture("008-brownstone_normal.png"));
//		Material planeMaterial = new Material(new Texture("bricks2.jpg"), 1, 8, new Texture("bricks2_normal.jpg"));
//		PrimitiveModel2D plane = new PrimitiveModel2D(Primitive2D.rectangle, new Vector3D(16, 0, 16), 1f, 1f, true, false, true, false);
//		MeshRenderer planeRenderer = new MeshRenderer(plane.getMesh(), planeMaterial);
//		planeObject.addComponent(planeRenderer);
//		addObject(planeObject);
		
//		GameObject monkeyObject = new GameObject();
//		Mesh monkeyMesh = new Mesh("monkey1.obj");
//		Material monkeyMaterial = new Material(new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"));
//		MeshRenderer monkeyRenderer = new MeshRenderer(monkeyMesh, monkeyMaterial);
//		monkeyObject.addComponent(monkeyRenderer);
//		addObject(monkeyObject);
		
		GameObject pointLightObject = new GameObject();
		PointLight pointLight = new PointLight(new Vector3D(0, 1, 0), 0.01f, new Vector3D(0, 0, 0.5f));
		pointLightObject.addComponent(pointLight);
		pointLightObject.getTransform().setPos(0, 1.0f, 0);
		addObject(pointLightObject);
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3D(0.5f, 0.5f, 0.5f), 1f);
		directionalLightObject.addComponent(directionalLight);
		directionalLightObject.getTransform().setRot(new Quaternion(new Vector3D(1, 0, 0), (float) -Math.toRadians(45.0)));
		addObject(directionalLightObject);
		
//		GameObject playerSpot = new GameObject();
//		SpotLight spot = new SpotLight(new Vector3D(1, 1, 1), 0.1f, new Vector3D(0, 0, 0.1f), 0.9f);
//		playerSpot.addComponent(spot);
//		player.addChild(playerSpot);
		
		GameObject textObject = new TextObject("Bottom Text", "AdventurerFont.png", 16, 16);
		textObject.getTransform().setPos(400, 200, 0);
		addHudObject(textObject);
		
//		GameObject monkeyObject2 = new GameObject();
//		Mesh monkeyMesh2 = new Mesh("monkey1.obj");
//		Material monkeyMaterial2 = new Material(new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"));
//		MeshRenderer monkeyRenderer2 = new MeshRenderer(monkeyMesh2, monkeyMaterial2);
//		monkeyObject2.addComponent(monkeyRenderer2);
//		monkeyObject2.getTransform().setScale(100f, 100f, 100f);
//		monkeyObject2.getTransform().setPos(200, 200, -100);
//		monkeyObject2.getTransform().setRot(new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(180)));
//		addHudObject(monkeyObject2);
		
	}
	
	public static Level getLevel() {
		
		return level;
		
	}
	
}