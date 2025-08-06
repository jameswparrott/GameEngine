package main.engine.physics.boundaries;

import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;

public class AABB extends Boundary implements Support {

    private final Vector3D maxExtend;

    /**
     * Constructs an Axis-Aligned Bounding Box for a physics body to use in
     * collision detection.
     * 
     * @param pos       position of the center of the aabb
     * @param maxExtend from the center, the distances in the positive x y and z
     *                  directions
     */
    public AABB(Vector3D pos, Vector3D maxExtend) {

        super(boundaryType.TYPE_AABB, pos);

        this.maxExtend = maxExtend;

    }

    @Override
    public IntersectData intersect(Boundary boundary) {

        return boundary.intersectWith(this);

    }
    @Override
    public IntersectData intersectWith(Sphere sphere) {

        Vector3D closestPoint = sphere.getPos().clamp(getMin(), getMax());

        Vector3D delta = sphere.getPos().sub(closestPoint);

        float distance = delta.length();

        float penetration = sphere.getRadius() - distance;

        return new IntersectData(penetration > 0, sphere.getPos().sub(getPos()).length(), penetration);

    }

    @Override
    public IntersectData intersectWith(AABB aabb) {

        boolean overlapX = getMin().getX() <= aabb.getMax().getX() && getMax().getX() >= aabb.getMin().getX();
        boolean overlapY = getMin().getY() <= aabb.getMax().getY() && getMax().getY() >= aabb.getMin().getY();
        boolean overlapZ = getMin().getZ() <= aabb.getMax().getZ() && getMax().getZ() >= aabb.getMin().getZ();

        boolean overlap = overlapX && overlapY && overlapZ;

        float dx = Math.min(getMax().getX(), aabb.getMax().getX()) - Math.max(getMin().getX(), aabb.getMin().getX());
        float dy = Math.min(getMax().getY(), aabb.getMax().getY()) - Math.max(getMin().getY(), aabb.getMin().getY());
        float dz = Math.min(getMax().getZ(), aabb.getMax().getZ()) - Math.max(getMin().getZ(), aabb.getMin().getZ());

        float distanceToBoundary = Math.min(dx, Math.min(dy, dz));

        return new IntersectData(overlap, getPos().sub(aabb.getPos()).length(), distanceToBoundary);

    }

    @Override
    public IntersectData intersectWith(CMB cmb) {

        return cmb.intersectWith(this);

    }

    @Override
    public Vector3D support(Vector3D dir) {

        return new Vector3D(
                getPos().getX() + (dir.getX() >= 0 ? getMaxExtend().getX() : getMinExtend().getX()),
                getPos().getY() + (dir.getY() >= 0 ? getMaxExtend().getY() : getMinExtend().getY()),
                getPos().getZ() + (dir.getZ() >= 0 ? getMaxExtend().getZ() : getMinExtend().getZ())
        );

    }

    public Vector3D getMaxExtend() {

        return maxExtend;

    }

    public Vector3D getMinExtend() {

        return maxExtend.getScaled(-1.0f);

    }

    public Vector3D getMax() {

        return getPos().add(getMaxExtend());

    }

    public Vector3D getMin() {

        return getPos().add(getMinExtend());

    }

}
