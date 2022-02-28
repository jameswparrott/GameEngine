package main.game;

import main.engine.components.AudioEngineComponent;
import main.engine.components.DirectionalLight;
import main.engine.components.MeshRenderer;
import main.engine.components.PhysicsBodyComponent;
import main.engine.components.PhysicsEngineComponent;
import main.engine.components.PointLight;
import main.engine.core.CoreEngine;
import main.engine.core.Game;
import main.engine.core.GameObject;
import main.engine.core.Quaternion;
import main.engine.core.Vector3D;
import main.engine.physics.PhysicsBody;
import main.engine.physics.boundaries.AABB;
import main.engine.physics.boundaries.CMB;
import main.engine.physics.boundaries.Sphere;
import main.engine.physics.materials.PhysicsMaterial;
import main.engine.rendering.Material;
import main.engine.rendering.Mesh;
import main.engine.rendering.TextObject;
import main.engine.rendering.Texture;
import main.engine.rendering.meshLoading.Terrain;

public class TestGame extends Game {
	
	private Player player;
	
	private static Level level;
	
	public TestGame() {
		
	}
	
	public void init() {
		
		PhysicsEngineComponent pec = new PhysicsEngineComponent(getPhysicsEngine());
		AudioEngineComponent aec = new AudioEngineComponent(getAudioEngine());
		
		addComponent(pec);
		addComponent(aec);
		
		player = new Player(new Vector3D(3, 6.475f, 5));
		addObject(player);
		
		GameObject terrainObject = new GameObject();
		Terrain terrain = new Terrain(20);
		terrain.addPerlin(5);
		terrain.genMesh();
		Material terrainMaterial = new Material(new Texture("tiledfloor.png"), 0.5f, 1.0f, new Texture("tiledfloor_normal.png"));
		MeshRenderer terrainRenderer = new MeshRenderer(terrain.getMesh(), terrainMaterial);
		terrainObject.addComponent(terrainRenderer);
		addObject(terrainObject);
		
//		GameObject cube = new GameObject();
//		Mesh cubeMesh = new Mesh("Cube.obj");
//		Material cubeMaterial = new Material(new Texture("UV_Grid.png"), 1, 1);
//		MeshRenderer cubeRenderer = new MeshRenderer(cubeMesh, cubeMaterial);
//		cube.addComponent(cubeRenderer);
//		cube.getTransform().setPos(new Vector3D(7, 7, 7));
//		CMB cubeCMB = new CMB(new Vector3D(7, 7, 7), cubeMesh, false);
//		PhysicsMaterial cubePhysicsMaterial = new PhysicsMaterial(false, 1.0f);
//		PhysicsBody cubePhysics = new PhysicsBody(	10, 
//													cubeCMB, 
//													cubePhysicsMaterial, 
//													new Vector3D(7, 7, 7),
//													new Vector3D(0, 0.0f, 0.0f), 
//													new Vector3D(0, 0, 0),
//													new Vector3D(0, -0.3f, 0),
//													new Vector3D(0, 0, 0));
//		PhysicsBodyComponent cubePhysicsComponent = new PhysicsBodyComponent(cubePhysics);
//		pec.add(cubePhysicsComponent);
//		cube.addComponent(cubePhysicsComponent);
//		addObject(cube);
		
		GameObject monkeyObject = new GameObject();
		Mesh monkeyMesh = new Mesh("monkey1.obj");
		Material monkeyMaterial = new Material(new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"));
		MeshRenderer monkeyRenderer = new MeshRenderer(monkeyMesh, monkeyMaterial);
		Sphere aSphere = new Sphere(new Vector3D(0, 3, 0), 1.0f);
		AABB aAABB = new AABB(new Vector3D(0, 3, 0), new Vector3D(1, 1, 1));
		CMB aCMB = new CMB(new Vector3D(0, 3, 0), monkeyMesh, false);
		PhysicsMaterial aMaterial = new PhysicsMaterial(false, 1.0f);
		PhysicsBody monkeyPhysics = new PhysicsBody(	10, 
														aCMB, 
														aMaterial, 
														new Vector3D(0, 3, 0),
														new Vector3D(0.0f, 0.0f, 0.5f), 
														new Vector3D(0, 0, 0),
														new Vector3D(0.2f, 0.13f, 0.13f),
														new Vector3D(0, 0, 0));
		PhysicsBodyComponent monkeyPhysicsComponent = new PhysicsBodyComponent(monkeyPhysics);
		monkeyObject.addComponent(monkeyRenderer);
		monkeyObject.addComponent(monkeyPhysicsComponent);
		pec.add(monkeyPhysicsComponent);
		addObject(monkeyObject);
		
		monkeyObject.getTransform().rotate(new Vector3D(0, 1, 0), (float) -Math.toRadians(180));
		
		GameObject monkeyObject2 = new GameObject();
		MeshRenderer monkeyRenderer2 = new MeshRenderer(monkeyMesh, monkeyMaterial);
		Sphere bSphere = new Sphere(new Vector3D(0, 3, 10), 1.0f);
		AABB bAABB = new AABB(new Vector3D(0, 3, 10), new Vector3D(1, 1, 1));
		CMB bCMB = new CMB(new Vector3D(0, 3, 10), monkeyMesh, false);
		PhysicsMaterial bMaterial = new PhysicsMaterial(false, 1.0f);
		PhysicsBody monkeyPhysics2 = new PhysicsBody(	10, 
														bCMB, 
														bMaterial, 
														new Vector3D(0, 3, 10), 
														new Vector3D(0, 0.0f, -0.5f), 
														new Vector3D(0, 0, 0),
														new Vector3D(0, 0.0f, 0),
														new Vector3D(0, 0, 0));
		PhysicsBodyComponent monkeyPhysicsComponent2 = new PhysicsBodyComponent(monkeyPhysics2);
		monkeyObject2.addComponent(monkeyRenderer2);
		monkeyObject2.addComponent(monkeyPhysicsComponent2);
		pec.add(monkeyPhysicsComponent2);
		addObject(monkeyObject2);
		
		monkeyObject2.getTransform().rotate(new Vector3D(0, 1, 0), (float) -Math.toRadians(0));
		
//		GameObject monkeyObject3 = new GameObject();
//		MeshRenderer monkeyRenderer3 = new MeshRenderer(monkeyMesh, monkeyMaterial);
//		Sphere cSphere = new Sphere(new Vector3D(10, 3, 0), 1.0f);
//		AABB cAABB = new AABB(new Vector3D(10, 3, 0), new Vector3D(1, 1, 1));
//		CMB cCMB = new CMB(new Vector3D(10, 3, 0), monkeyMesh, false);
//		PhysicsMaterial cMaterial = new PhysicsMaterial(false, 1.0f);
//		PhysicsBody monkeyPhysics3 = new PhysicsBody(	10, 
//														cCMB, 
//														cMaterial, 
//														new Vector3D(10, 3, 0), 
//														new Vector3D(-0.5f, 0.0f, 0), 
//														new Vector3D(0, 0, 0),
//														new Vector3D(0.0f, 0.0f, 0.0f),
//														new Vector3D(0, 0, 0));
//		PhysicsBodyComponent monkeyPhysicsComponent3 = new PhysicsBodyComponent(monkeyPhysics3);
//		monkeyObject3.addComponent(monkeyRenderer3);
//		monkeyObject3.addComponent(monkeyPhysicsComponent3);
//		//monkeyObject3.getTransform().rotate(new Vector3D(0, 1, 0), (float) -Math.toRadians(90));
//		pec.add(monkeyPhysicsComponent3);
//		addObject(monkeyObject3);
		
		GameObject pointLightObject = new GameObject();
		PointLight pointLight = new PointLight(new Vector3D(0, 1, 0), 0.01f, new Vector3D(0.0f, 2.0f, 1.5f));
		pointLightObject.addComponent(pointLight);
		pointLightObject.getTransform().setPos(0, 0.1f, 0);
		addObject(pointLightObject);
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3D(0.5f, 0.5f, 0.5f), 0.5f);
		directionalLightObject.addComponent(directionalLight);
		directionalLightObject.getTransform().setRot(new Quaternion(new Vector3D(1, 0, 0), (float) -Math.toRadians(45.0)));
		addObject(directionalLightObject);
		
		GameObject textObject = new TextObject("Bottom Text", "AdventurerFont.png", 16, 16);
		textObject.getTransform().setPos(0, 0, 0);
		addHudObject(textObject);
		
		GameObject cross = new TextObject(".", "AdventurerFont.png", 16, 16);
		cross.getTransform().setScale(0.5f, 0.5f, 0.5f);
		cross.getTransform().setPos(CoreEngine.getCenter().getX(), CoreEngine.getCenter().getY(), 0);
		addHudObject(cross);
		
	}
	
	public static Level getLevel() {
		
		return level;
		
	}
	
}