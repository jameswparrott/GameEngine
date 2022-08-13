package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;
import main.engine.rendering.Mesh;

public class Sphere extends Boundary {

    private float radius;

    public Sphere(Vector3D pos, Mesh mesh) {
        
        this(pos, getMaxRadius(mesh));
        
    }
    
    /**
     * Constructs a sphere for a physics body to use in collision detection.
     * 
     * @param pos    position of the center of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Vector3D pos, float radius) {

        super(boundaryType.TYPE_SPHERE, pos);

        this.radius = radius;

    }
    
    private static float getMaxRadius(Mesh mesh) {
        
        float result = 0.0f;
        
        for (int i = 0; i < mesh.getPositions().size(); i ++) {
            
            float dist = mesh.getPositions().get(i).lengthSq();
            
            if (dist > result) {
                
                result = dist;
                
            }
            
        }
        
        return (float) Math.sqrt(result);
        
    }

    public float getRadius() {

        return this.radius;

    }

    public void setRadius(float radius) {

        this.radius = radius;

    }

    public IntersectData intersect(Boundary boundary) {

        switch (boundary.getType()) {

        case TYPE_PLANE:

            return intersect((Plane) boundary);

        case TYPE_SPHERE:

            return intersect((Sphere) boundary);

        case TYPE_CMB:

            return intersect((CMB) boundary);

        case TYPE_AABB:

            return intersect((AABB) boundary);

        default:

            System.err.println("Sphere attempted to intersect with an undefined boundary");

            return new IntersectData(false, 0, 0);

        }

    }

    /**
     * Returns intersect data containing information about the intersection. If the
     * intersection occurs, the distance between the two centers, and the minimal
     * distance between the boundaries.
     * 
     * @param sphere sphere to test intersection against
     * @return intersect data
     */
    public IntersectData intersect(Sphere sphere) {

        float distanceToCenter = getPos().sub(sphere.getPos()).length();

        float distanceToBoundary = distanceToCenter - (getRadius() + sphere.getRadius());

        return new IntersectData(distanceToBoundary < 0, distanceToCenter, distanceToBoundary);

    }

    /**
     * Returns intersect data containing information about the intersection. If the
     * intersection occurs, the distance between the two centers, and the minimal
     * distance between the boundaries.
     * 
     * @param plane plane to test intersection against
     * @return intersect data
     */
    public IntersectData intersect(Plane plane) {

        return new IntersectData(false, 0, 0);

    }

    /**
     * Returns intersect data containing information about the intersection. If the
     * intersection occurs, the distance between the two centers, and the minimal
     * distance between the boundaries.
     * 
     * @param aabb aabb to test intersection against
     * @return intersect data
     */
    public IntersectData intersect(AABB aabb) {

        return new IntersectData(false, 0, 0);

    }

    /**
     * Returns intersect data containing information about the intersection. If the
     * intersection occurs, the distance between the two centers, and the minimal
     * distance between the boundaries.
     * 
     * @param cmb cmb to test intersection against
     * @return intersect data
     */
    public IntersectData intersect(CMB cmb) {

        return new IntersectData(false, 0, 0);

    }

}
