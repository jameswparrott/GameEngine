package main.game;

import main.engine.components.AudioEngineComponent;
import main.engine.components.DirectionalLight;
import main.engine.components.MeshRenderer;
import main.engine.components.PhysicsBodyComponent;
import main.engine.components.PhysicsEngineComponent;
import main.engine.components.PointLight;
import main.engine.components.RenderingEngineComponent;
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

    private Level level;

    public TestGame() {

    }

    public void init() {

        PhysicsEngineComponent   pec = new PhysicsEngineComponent(getPhysicsEngine());
        RenderingEngineComponent rec = new RenderingEngineComponent(getRenderingEngine());
        AudioEngineComponent     aec = new AudioEngineComponent(getAudioEngine());

        addComponent(pec);
        addComponent(aec);
        addComponent(rec);
        
        rec.getRenderingEngine().setAmbientLight(new Vector3D(0.0001f, 0.0001f, 0.0001f));
        
        player = new Player(new Vector3D(2, 0.5f, 2));
        
        addObject(player);
        
        level = new Level("level4.png", "wolf3D-64-full.png", 8, player);
        
        addObject(level);
        
        GameObject pointLightObject = new GameObject();
        PointLight pointLight = new PointLight( new Vector3D(1, 1, 1), 
                                                0.01f, 
                                                new Vector3D(1.0f, 0.1f, 0.015f));
        pointLightObject.addComponent(pointLight);
        //pointLightObject.getTransform().setPos(0, 0.1f, 0);
        //addObject(pointLightObject);

        player.addChild(pointLightObject);
        
//        GameObject directionalLightObject = new GameObject();
//        DirectionalLight directionalLight = new DirectionalLight(new Vector3D(0.5f, 0.5f, 0.5f), 0.5f);
//        directionalLightObject.addComponent(directionalLight);
//        directionalLightObject.getTransform()
//                .setRot(new Quaternion(new Vector3D(1, 0, 0), (float) -Math.toRadians(45.0)));
//        addObject(directionalLightObject);

        GameObject textObject = new TextObject("Bottom Text", "AdventurerFont.png", 16, 16);
        textObject.getTransform().setPos(0, 0, 0);
        addHudObject(textObject);

        GameObject cross = new TextObject(".", "AdventurerFont.png", 16, 16);
        cross.getTransform().setScale(0.5f, 0.5f, 0.5f);
        cross.getTransform().setPos(CoreEngine.getCenter().getX(), CoreEngine.getCenter().getY(), -1.1f);
        addHudObject(cross);
        
//        GameObject terrainObject = new GameObject();
//        Terrain terrain = new Terrain(200);
//        terrain.addPerlin(6);
//        terrain.genMesh();
//        Material terrainMaterial = new Material(new Texture("tiledfloor.png"), 0.5f, 1.0f,
//                                                new Texture("tiledfloor_normal.png"));
//        MeshRenderer terrainRenderer = new MeshRenderer(terrain.getMesh(), terrainMaterial);
//        terrainObject.addComponent(terrainRenderer);
//        addObject(terrainObject);
//        
//        terrainObject.getTransform().setPos(-100, 0, -100);

    }

    public Level getLevel() {

        return level;

    }

}