package main.game;

import main.engine.components.*;
import main.engine.core.CoreEngine;
import main.engine.core.Game;
import main.engine.core.GameObject;
import main.engine.core.Quaternion;
import main.engine.core.Vector3D;
import main.engine.physics.PhysicsBody;
import main.engine.physics.boundaries.Sphere;
import main.engine.physics.boundaries.AABB;
import main.engine.physics.boundaries.CMB;
import main.engine.physics.materials.PhysicsMaterial;
import main.engine.rendering.Material;
import main.engine.rendering.Mesh;
import main.engine.rendering.TextObject;
import main.engine.rendering.Texture;
import main.engine.rendering.meshLoading.Terrain;

public class TestGame2 extends Game {

    private Player player;

    private static Level level;

    public void init() {

        PhysicsEngineComponent   pec = new PhysicsEngineComponent(getPhysicsEngine());
        RenderingEngineComponent rec = new RenderingEngineComponent(getRenderingEngine());
        AudioEngineComponent     aec = new AudioEngineComponent(getAudioEngine());

        addComponent(pec);
        addComponent(aec);
        addComponent(rec);

        //player = new Player(new Vector3D(3, 6.475f, 5));
        
        player = new Player(Vector3D.ZERO);
        
        addObject(player);
        
        GameObject terrainObject = new GameObject();
        Terrain terrain = new Terrain(20);
        terrain.addPerlin(5);
        terrain.genMesh();
        Material terrainMaterial = new Material(new Texture("tiledfloor.png"), 0.5f, 1.0f,
                                                new Texture("tiledfloor_normal.png"));
        MeshRenderer terrainRenderer = new MeshRenderer(terrain.getMesh(), terrainMaterial);
        terrainObject.addComponent(terrainRenderer);
        addObject(terrainObject);

//      GameObject cube = new GameObject();
//      Mesh cubeMesh = new Mesh("Cube.obj");
//      Material cubeMaterial = new Material(new Texture("UV_Grid.png"), 1, 1);
//      MeshRenderer cubeRenderer = new MeshRenderer(cubeMesh, cubeMaterial);
//      cube.addComponent(cubeRenderer);
//      cube.getTransform().setPos(new Vector3D(7, 7, 7));
//      CMB cubeCMB = new CMB(new Vector3D(7, 7, 7), cubeMesh, false);
//      PhysicsMaterial cubePhysicsMaterial = new PhysicsMaterial(false, 1.0f);
//      PhysicsBody cubePhysics = new PhysicsBody(  10, 
//                                                  cubeCMB, 
//                                                  cubePhysicsMaterial, 
//                                                  new Vector3D(7, 7, 7),
//                                                  new Vector3D(0, 0.0f, 0.0f), 
//                                                  new Vector3D(0, 0, 0),
//                                                  new Vector3D(0, -0.3f, 0),
//                                                  new Vector3D(0, 0, 0));
//      PhysicsBodyComponent cubePhysicsComponent = new PhysicsBodyComponent(cubePhysics);
//      pec.add(cubePhysicsComponent);
//      cube.addComponent(cubePhysicsComponent);
//      addObject(cube);

//        GameObject monkeyObject = new GameObject();
//        Mesh monkeyMesh = new Mesh("monkey1.obj");
//        Material monkeyMaterial = new Material(new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"));
//        MeshRenderer monkeyRenderer = new MeshRenderer(monkeyMesh, monkeyMaterial);
//        Sphere aSphere = new Sphere(new Vector3D(5, 3, 0), monkeyMesh);
//        //AABB aAABB = new AABB(new Vector3D(0, 3, 0), new Vector3D(1, 1, 1));
//        //CMB aCMB = new CMB(new Vector3D(0, 3, 0), monkeyMesh, false);
//        PhysicsMaterial mMaterial = new PhysicsMaterial(false, 1.0f);
//        PhysicsBody monkeyPhysics = new PhysicsBody(10, aSphere, mMaterial, new Vector3D(0, 3, 0),
//                                                    new Vector3D(0.0f, 0.0f, 0.0f),
//                                                    new Vector3D(0, 0, 0),
//                                                    new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(0.0f)),
//                                                    new Vector3D(0.0f, 0.0f, 0.0f),
//                                                    new Vector3D(0, 0, 0));
//        PhysicsBodyComponent monkeyPhysicsComponent = new PhysicsBodyComponent(monkeyPhysics);
//        monkeyObject.addComponent(monkeyRenderer);
//        monkeyObject.addComponent(monkeyPhysicsComponent);
//        pec.add(monkeyPhysicsComponent);
//        addObject(monkeyObject);

        Mesh monkeyMesh = new Mesh("monkey1.obj");
        Material monkeyMaterial = new Material(new Texture("bricks.jpg"), 1, 8, new Texture("bricks_normal.jpg"));

        GameObject monkeyObject = new GameObject();
        MeshRenderer monkeyRenderer = new MeshRenderer(monkeyMesh, monkeyMaterial);
        Sphere monkeySphere = new Sphere(new Vector3D(5.0f, 3.0f, 0.0f), monkeyMesh);
        //AABB monkeyAABB2 = new AABB(new Vector3D(0, 3, 0), new Vector3D(1, 1, 1));
        //CMB monkeyCMB2 = new CMB(new Vector3D(0, 3, 0), monkeyMesh, false);
        PhysicsMaterial monkeyPhysicsMaterial = new PhysicsMaterial(false, 1.0f);
        PhysicsBody monkeyPhysicsBody = new PhysicsBody(10, monkeySphere, monkeyPhysicsMaterial,
                                                        new Vector3D(5.0f, 3.0f, 0.0f),
                                                        new Vector3D(0.0f, 0.0f, 0.0f),
                                                        new Vector3D(0, 0, 0),
                                                        new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(45.0f)),
                                                        new Vector3D(0.0f, 0.0f, 0.0f),
                                                        new Vector3D(0, 0, 0));
        PhysicsBodyComponent monkeyPhysicsComponent = new PhysicsBodyComponent(monkeyPhysicsBody);
        monkeyObject.addComponent(monkeyRenderer);
        monkeyObject.addComponent(monkeyPhysicsComponent);
        pec.add(monkeyPhysicsComponent);
        addObject(monkeyObject);

        GameObject monkeyObject2 = new GameObject();
        MeshRenderer monkeyRenderer2 = new MeshRenderer(monkeyMesh, monkeyMaterial);
        Sphere monkeySphere2 = new Sphere(new Vector3D(10.0f, 3.0f, 4.0f), monkeyMesh);
        //AABB monkeyAABB2 = new AABB(new Vector3D(0, 3, 10), new Vector3D(1, 1, 1));
        //CMB monkeyCMB2 = new CMB(new Vector3D(0, 3, 10), monkeyMesh, false);
        PhysicsMaterial monkeyPhysicsMaterial2 = new PhysicsMaterial(false, 1.0f);
        PhysicsBody monkeyPhysics2 = new PhysicsBody(10, monkeySphere2, monkeyPhysicsMaterial2,
                                                     new Vector3D(10.0f, 3.0f, 4.0f),
                                                     new Vector3D(0.0f, 0.0f, -0.2f),
                                                     new Vector3D(0, 0, 0),
                                                     new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(0.0f)),
                                                     new Vector3D(0, 0.0f, 0),
                                                     new Vector3D(0, 0, 0));
        PhysicsBodyComponent monkeyPhysicsComponent2 = new PhysicsBodyComponent(monkeyPhysics2);
        monkeyObject2.addComponent(monkeyRenderer2);
        monkeyObject2.addComponent(monkeyPhysicsComponent2);
        pec.add(monkeyPhysicsComponent2);
        addObject(monkeyObject2);

//        GameObject monkeyObject3 = new GameObject();
//        MeshRenderer monkeyRenderer3 = new MeshRenderer(monkeyMesh, monkeyMaterial);
//        //Sphere monkeySphere3 = new Sphere(new Vector3D(10.0f, 3.0f, 4.0f), monkeyMesh);
//        //AABB monkeyAABB2 = new AABB(new Vector3D(0, 3, 10), new Vector3D(1, 1, 1));
//        CMB monkeyCMB3 = new CMB(new Vector3D(0.0f, 6.0f, -1.0f), monkeyMesh, false);
//        PhysicsMaterial monkeyPhysicsMaterial3 = new PhysicsMaterial(false, 1.0f);
//        PhysicsBody monkeyPhysics3 = new PhysicsBody(10, monkeyCMB3, monkeyPhysicsMaterial3,
//                new Vector3D(0.0f, 6.0f, -1.0f),
//                new Vector3D(0.1f, 0.0f, 0.0f),
//                new Vector3D(0, 0, 0),
//                new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(0.0f)),
//                new Vector3D(0, 0.0f, 0),
//                new Vector3D(0, 0, 0));
//        PhysicsBodyComponent monkeyPhysicsComponent3 = new PhysicsBodyComponent(monkeyPhysics3);
//        monkeyObject3.addComponent(monkeyRenderer3);
//        monkeyObject3.addComponent(monkeyPhysicsComponent3);
//        pec.add(monkeyPhysicsComponent3);
//        addObject(monkeyObject3);
//
//        GameObject monkeyObject4 = new GameObject();
//        MeshRenderer monkeyRenderer4 = new MeshRenderer(monkeyMesh, monkeyMaterial);
//        //Sphere monkeySphere3 = new Sphere(new Vector3D(10.0f, 3.0f, 4.0f), monkeyMesh);
//        //AABB monkeyAABB2 = new AABB(new Vector3D(0, 3, 10), new Vector3D(1, 1, 1));
//        CMB monkeyCMB4 = new CMB(new Vector3D(10.0f, 6.0f, -1.0f), monkeyMesh, false);
//        PhysicsMaterial monkeyPhysicsMaterial4 = new PhysicsMaterial(false, 1.0f);
//        PhysicsBody monkeyPhysics4 = new PhysicsBody(10, monkeyCMB4, monkeyPhysicsMaterial4,
//                new Vector3D(10.0f, 6.0f, -1.0f),
//                new Vector3D(-0.1f, 0.0f, 0.0f),
//                new Vector3D(0, 0, 0),
//                new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(0.0f)),
//                new Vector3D(0, 0.0f, 0),
//                new Vector3D(0, 0, 0));
//        PhysicsBodyComponent monkeyPhysicsComponent4 = new PhysicsBodyComponent(monkeyPhysics4);
//        monkeyObject4.addComponent(monkeyRenderer4);
//        monkeyObject4.addComponent(monkeyPhysicsComponent4);
//        pec.add(monkeyPhysicsComponent4);
//        addObject(monkeyObject4);




        Mesh boxMesh = new Mesh("box1.obj");
        Material boxMaterial = new Material(new Texture("008-brownstone.png"), 1, 8, new Texture("008-brownstone_normal.png"));

        GameObject box1Object = new GameObject();
        MeshRenderer box1Renderer = new MeshRenderer(boxMesh, boxMaterial);
        AABB box1AABB = new AABB(new Vector3D(10.0f, 3.0f, 0.0f), new Vector3D(1.0f, 1.0f, 1.0f));
        //CMB aCMB = new CMB(new Vector3D(0, 3, 0), boxMesh, false);
        PhysicsMaterial box1PhysicsMaterial = new PhysicsMaterial(false, 1.0f);
        PhysicsBody box1Physics = new PhysicsBody(10, box1AABB, box1PhysicsMaterial,
                                                    new Vector3D(10.0f, 3.0f, 0.0f),
                                                    new Vector3D(0.0f, 0.0f, 0.0f),
                                                    new Vector3D(0, 0, 0),
                                                    new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(0.0f)),
                                                    new Vector3D(0.0f, 0.0f, 0.0f),
                                                    new Vector3D(0, 0, 0));
        PhysicsBodyComponent box1PhysicsComponent = new PhysicsBodyComponent(box1Physics);
        box1Object.addComponent(box1Renderer);
        box1Object.addComponent(box1PhysicsComponent);
        pec.add(box1PhysicsComponent);
        addObject(box1Object);

        GameObject box2Object = new GameObject();
        MeshRenderer box2Renderer = new MeshRenderer(boxMesh, boxMaterial);
        AABB box2AABB = new AABB(new Vector3D(5.1f, 3.0f, 4.0f), new Vector3D(1.0f, 1.0f, 1.0f));
        //CMB bCMB = new CMB(new Vector3D(0, 3, 10), boxMesh, false);
        PhysicsMaterial box2PhysicsMaterial = new PhysicsMaterial(false, 1.0f);
        PhysicsBody box2Physics = new PhysicsBody(10, box2AABB, box2PhysicsMaterial,
                                                    new Vector3D(5.1f, 3.0f, 4.0f),
                                                    new Vector3D(0.1f, 0.1f, -0.2f),
                                                    new Vector3D(0, 0, 0),
                                                    new Quaternion(new Vector3D(0, 1, 0), (float) -Math.toRadians(0.0f)),
                                                    new Vector3D(0, 0.0f, 0),
                                                    new Vector3D(0, 0, 0));
        PhysicsBodyComponent box2PhysicsComponent = new PhysicsBodyComponent(box2Physics);
        box2Object.addComponent(box2Renderer);
        box2Object.addComponent(box2PhysicsComponent);
        pec.add(box2PhysicsComponent);
        addObject(box2Object);
        
        GameObject pointLightObject = new GameObject();
        PointLight pointLight = new PointLight(new Vector3D(0, 1, 0), 0.0005f, new Vector3D(2.0f, 1.0f, 0.2f));
        pointLightObject.addComponent(pointLight);
        pointLightObject.getTransform().setPos(0, 0.4f, 0);
        addObject(pointLightObject);

        GameObject directionalLightObject = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(new Vector3D(0.5f, 0.5f, 0.5f), 0.1f);
        directionalLightObject.addComponent(directionalLight);
        directionalLightObject.getTransform()
                .setRot(new Quaternion(new Vector3D(1, 0, 0), (float) -Math.toRadians(45.0)));
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

    public void update() {

    }

}
