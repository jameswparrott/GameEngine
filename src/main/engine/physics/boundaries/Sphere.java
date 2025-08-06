package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;
import main.engine.rendering.Mesh;

public class Sphere extends Boundary implements Support {

    private final float radius;

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

    @Override
    public IntersectData intersect(Boundary boundary) {

        return boundary.intersectWith(this);

    }

    @Override
    public IntersectData intersectWith(Sphere sphere) {

        float distanceToCenter = getPos().sub(sphere.getPos()).length();

        float distanceToBoundary = distanceToCenter - (getRadius() + sphere.getRadius());

        return new IntersectData(distanceToBoundary < 0, distanceToCenter, distanceToBoundary);

    }

    @Override
    public IntersectData intersectWith(AABB aabb) {

        return aabb.intersectWith(this);

    }

    @Override
    public IntersectData intersectWith(CMB cmb) {

        return cmb.intersectWith(this);

    }

    @Override
    public Vector3D support(Vector3D dir) {

        return getPos().add(dir.getNormal().getScaled(radius));

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

}
