package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

public interface Support {

    /**
     * Returns the furthest point on the shape in the given direction.
     * This is used by GJK to compute support points for Minkowski difference.
     *
     * @param dir the direction vector
     * @return the point on the shape's surface furthest in that direction
     */
    Vector3D support(Vector3D dir);

}
