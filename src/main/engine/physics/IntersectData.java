package main.engine.physics;

/**
 * Stores intersect data about two boundaries.
 *
 * @param intersect          true if the boundaries intersect, false otherwise
 * @param distanceToCenter   distance between the centers of the two boundaries
 * @param penetrationDepth   penetration depth, or overlap of the two boundaries
 */
public record IntersectData(boolean intersect, float distanceToCenter, float penetrationDepth)  {

}
