package main.engine.rendering;

import java.util.HashMap;

import main.engine.core.Vector3D;

public class Material {

    private HashMap<String, Texture> textureMap;

    private HashMap<String, Vector3D> vectorMap;

    private HashMap<String, Float> floatMap;

    public Material(Texture texture, float intensity, float exponent) {

        this(texture, intensity, exponent, new Texture("default-normalMap.png"));

    }

    public Material(Texture texture, float intensity, float exponent, Texture normalMap) {

        this();

        this.textureMap.put("diffuse", texture);

        this.floatMap.put("specularIntensity", intensity);

        this.floatMap.put("specularExponent", exponent);

        this.textureMap.put("nMap", normalMap);

    }

    public Material() {

        textureMap = new HashMap<String, Texture>();

        vectorMap = new HashMap<String, Vector3D>();

        floatMap = new HashMap<String, Float>();

    }

    public void addTexture(String name, Texture texture) {

        textureMap.put(name, texture);

    }

    public void addVector(String name, Vector3D vector) {

        vectorMap.put(name, vector);

    }

    public void addFloat(String name, float val) {

        floatMap.put(name, val);

    }

    public Texture getTexture(String name) {

        if (textureMap.containsKey(name)) {

            return textureMap.get(name);

        }

        return new Texture("test.png");

    }

    public Vector3D getVector(String name) {

        if (vectorMap.containsKey(name)) {

            return vectorMap.get(name);

        }

        return new Vector3D(0, 0, 0);

    }

    public float getFloat(String name) {

        if (floatMap.containsKey(name)) {

            return floatMap.get(name);

        }

        return 0;

    }

}
