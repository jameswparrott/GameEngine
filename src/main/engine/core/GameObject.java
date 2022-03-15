package main.engine.core;

import java.util.ArrayList;

import main.engine.components.GameComponent;
import main.engine.rendering.RenderingEngine;
import main.engine.rendering.Shader;

public class GameObject {

    private ArrayList<GameObject> children;

    private ArrayList<GameComponent> components;

    private Transform transform;

    public GameObject() {

        children = new ArrayList<GameObject>();

        components = new ArrayList<GameComponent>();

        transform = new Transform();

    }

    public void addChild(GameObject child) {

        children.add(child);

        child.getTransform().setParent(transform);

    }

    public void removeChild(GameObject child) {

        if (children.contains(child)) {

            children.remove(child);

        }

    }

    public void clearChildren() {

        children.clear();

    }

    public GameObject addComponent(GameComponent component) {

        components.add(component);

        component.setParent(this);

        return this;

    }

    public void removeComponent(GameComponent component) {

        if (components.contains(component)) {

            components.remove(component);

        }

    }

    public void input(float delta) {

        transform.update();

        for (GameComponent component : components) {

            component.input(delta);

        }

        for (GameObject child : children) {

            child.input(delta);

        }

    }

    public void update(float delta) {

        for (GameComponent component : components) {

            component.update(delta);

        }

        for (GameObject child : children) {

            child.update(delta);

        }

    }

    public void render(Shader shader, RenderingEngine renderingEngine) {

        for (GameComponent component : components) {

            component.render(shader, renderingEngine);

        }

        for (GameObject child : children) {

            child.render(shader, renderingEngine);

        }

    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {

        for (GameComponent component : components) {

            component.addToRenderingEngine(renderingEngine);

        }

        for (GameObject child : children) {

            child.addToRenderingEngine(renderingEngine);

        }

    }

    public Transform getTransform() {

        return transform;

    }

}
