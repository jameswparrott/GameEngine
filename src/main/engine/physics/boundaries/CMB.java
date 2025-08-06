package main.engine.physics.boundaries;

import java.util.ArrayList;

import main.engine.core.Quaternion;
import main.engine.core.Transform;
import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;
import main.engine.rendering.Mesh;

public class CMB extends Boundary implements Support {

    private ArrayList<Vector3D> convexBoundary;

    private Quaternion rot;

    public CMB(Vector3D pos, Mesh mesh, boolean convex) {

        this(pos, mesh.getPositions(), convex);

    }

    /**
     * Constructs a Convex Mesh Boundary for a physics body to use in collision
     * detection.
     * 
     * @param pos      position of the center of the convex boundary
     * @param vertices arraylist of vertices defining the boundary
     * @param convex   boolean specifying if the vertices are already convex or not
     */
    public CMB(Vector3D pos, ArrayList<Vector3D> vertices, boolean convex) {

        super(boundaryType.TYPE_CMB, pos);

        this.rot = Quaternion.IDENTITY;

        if (convex) {

            convexBoundary = vertices;

        } else {

            //convexBoundary = quickHull(vertices);

        }

    }

    public void update(Transform transform) {

        setPos(transform.getPos());
        
        this.rot = transform.getRot();

    }

    @Override
    public IntersectData intersect(Boundary boundary) {

        return boundary.intersectWith(this);

    }

    @Override
    public IntersectData intersectWith(Sphere sphere) {

        return new IntersectData(false, 0, 0);

    }

    @Override
    public IntersectData intersectWith(AABB aabb) {

        return new IntersectData(false, 0, 0);

    }

    @Override
    public IntersectData intersectWith(CMB cmb) {

        float distanceToCenter = getPos().sub(cmb.getPos()).length();

        GJKResult gjkResult = GJK.intersects(this, cmb);

        boolean gjk = gjkResult.intersects();

        ArrayList<Vector3D> simplex = gjkResult.simplex();

        return new IntersectData(gjk, distanceToCenter, gjk ? EPA.distance(simplex, getOffsetBoundary(), cmb.getOffsetBoundary()) : 0);

    }

    @Override
    public Vector3D support(Vector3D dir) {

        return DirectionalSupport.dirMaxVec(getOffsetBoundary(), dir);

    }

    public ArrayList<Vector3D> getBoundary() {

        return convexBoundary;

    }

    public ArrayList<Vector3D> getOffsetBoundary() {

        ArrayList<Vector3D> newBoundary = new ArrayList<>();

        for (Vector3D vector3D : convexBoundary) {

            newBoundary.add(rot.toRotationMatrix().transform(vector3D).add(getPos()));

        }

        return newBoundary;

    }

    public void setBoundary(ArrayList<Vector3D> boundary) {

        this.convexBoundary = boundary;

    }

}
