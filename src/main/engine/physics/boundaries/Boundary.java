package main.engine.physics.boundaries;

import main.engine.core.Transform;
import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public abstract class Boundary {

    public enum boundaryType {

        TYPE_SPHERE,

        TYPE_AABB,

        TYPE_CMB

    }

    private final boundaryType type;

    private Vector3D pos;

    public Boundary(boundaryType type, Vector3D pos) {

        this.type = type;

        this.pos = pos;

    }

    public abstract IntersectData intersect(Boundary boundary);

    public abstract IntersectData intersectWith(Sphere sphere);

    public abstract IntersectData intersectWith(AABB aabb);

    public abstract IntersectData intersectWith(CMB cmb);

    public void update(Transform transform) {

        this.pos = transform.getTransformedPos();

    }

    public boundaryType getType() {

        return type;

    }

    public Vector3D getPos() {

        return pos;

    }

    public void setPos(Vector3D pos) {

        this.pos = pos;

    }

}
