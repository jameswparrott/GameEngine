package main.engine.rendering.geometry;

import main.engine.core.Vector3D;

public class Triangle {

    private Vector3D v1;

    private Vector3D v2;

    private Vector3D v3;

    private Vector3D normal;

    private Vector3D centroid;

    public Triangle(Vector3D v1, Vector3D v2, Vector3D v3) {

        this.v1 = v1;

        this.v2 = v2;

        this.v3 = v3;

        this.normal = calcNormal(v1, v2, v3);

        this.centroid = calcCentroid(v1, v2, v3);

    }

    private static Vector3D calcNormal(Vector3D v1, Vector3D v2, Vector3D v3) {

        return v1.sub(v2).cross(v3.sub(v2));

    }

    private static Vector3D calcCentroid(Vector3D v1, Vector3D v2, Vector3D v3) {

        return v1.add(v2).add(v3).getScaled(1.0f / 3.0f);

    }

    public boolean contains(Vector3D test) {

        return test.equals(v1) || test.equals(v2) || test.equals(v3);

    }

    public Vector3D getV1() {

        return v1;

    }

    public Vector3D getV2() {

        return v2;

    }

    public Vector3D getV3() {

        return v3;

    }

    public Vector3D getNormal() {

        return normal;

    }

    public Vector3D getCentroid() {

        return centroid;

    }

}
