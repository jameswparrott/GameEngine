package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

import java.util.ArrayList;

/**
 * A result wrapper for the GJK algorithm.
 *
 * @param intersects {@code true} if the two convex shapes intersect, {@code false} otherwise
 * @param simplex the final simplex used during GJK convergence (may be reused for EPA if intersecting)
 */
public record GJKResult(boolean intersects, ArrayList<Vector3D> simplex) {

}
