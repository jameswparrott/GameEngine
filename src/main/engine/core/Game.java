package main.engine.core;

import main.engine.audio.AudioEngine;
import main.engine.components.GameComponent;
import main.engine.physics.PhysicsEngine;
import main.engine.rendering.RenderingEngine;

public abstract class Game {

    private GameObject root;

    private GameObject hudRoot;

    private PhysicsEngine physicsEngine;

    private AudioEngine audioEngine;

    public Game() {

        root = new GameObject();

        hudRoot = new GameObject();

    }

    public void init() {

    }

    public void addPhysicsEngine(PhysicsEngine physicsEngine) {

        this.physicsEngine = physicsEngine;

    }

    public void addAudioEngine(AudioEngine audioEngine) {

        this.audioEngine = audioEngine;

    }

    public void input(float delta) {

        getRootObject().input(delta);

        getHudRoot().input(delta);

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

    public void removeObject(GameObject gameObject) {

        getRootObject().removeChild(gameObject);

    }

    public void addComponent(GameComponent component) {

        getRootObject().addComponent(component);

    }

    public void removeComponent(GameComponent component) {

        getRootObject().removeComponent(component);

    }

    public void addHudObject(GameObject hudObject) {

        getHudRoot().addChild(hudObject);

    }

    public void removeHudObject(GameObject hudObject) {

        getHudRoot().removeChild(hudObject);

    }

    public PhysicsEngine getPhysicsEngine() {

        return physicsEngine;

    }

    public AudioEngine getAudioEngine() {

        return audioEngine;

    }

    private GameObject getRootObject() {

        return root;

    }

    private GameObject getHudRoot() {

        return hudRoot;

    }

}
