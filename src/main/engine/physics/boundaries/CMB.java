package main.engine.physics.boundaries;

import java.util.ArrayList;

import main.engine.core.Quaternion;
import main.engine.core.Transform;
import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;
import main.engine.rendering.Mesh;

public class CMB extends Boundary {
    
    // TODO: Implement a way to return the convex hull of an object

    // TODO: Add option to return lengths from GJK algorithm

    // TODO: Create a library for convex object creation and analysis

    // TODO: Add a max length variable to the object, derived from the construction
    // of the convex boundary

    private ArrayList<Vector3D> convexBoundary;

    private Vector3D dir;

    private ArrayList<Vector3D> simplex;
    
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

        dir = new Vector3D(1, 1, 1);

        simplex = new ArrayList<Vector3D>(4);

        if (convex) {

            convexBoundary = vertices;

        } else {

            //convexBoundary = quickHull(vertices);

            convexBoundary = quickHull(vertices);
            
        }

    }

    public void update(Transform transform) {

        setPos(transform.getPos());
        
//        if (transform.hasRotChanged()) {
//
//            for (int i = 0; i < convexBoundary.size(); i++) {
//    
//                convexBoundary.set(i, transform.getRot().toRotationMatrix().transform(convexBoundary.get(i)));
//    
//            }
//        
//        }
        
        this.rot = transform.getRot();

    }
    
    private static ArrayList<Vector3D> quickHull(ArrayList<Vector3D> vertices) {
        
        //Create output polytope
        Polytope polytope = new Polytope(vertices);
        
        //Find maximal initial simplex
        //CREATE_SIMPLEX(polytope, vertices);
        
        //Divide pointcloud into four groups, allocating each group to a face if a point could be 
        //assigned to multiple faces, assign it to one randomly it does not matter
        //Any points inside the polytope will be removed
        //ADD_TO_OUTSIDE_SET(face, vertices);
        
        //While ∃ a face with outside vertices
        
            //Find the point furthest from the face in its outside set
            //CALCULATE_EYE_POINT(face, outsideSet);
        
            //Calculate the horizon which will produce a list of horizon edges, an ordered list of
            //edges from the view of the eye point, it will also mark the faces seen by the eye point
            //as no longer in the convex hull, then place all of their outside set points on the 
            //list of unclaimed vertices
            //CALCULATE_HORIZON(eyePoint, NULL, currFace, listHorizonEdges, listUnclaimedVertices);
        
            //Construct a cone from the eye point and all of the horizon edges
        
        //Return the polytope's vertices
        return polytope.getVertices();
        
    }

    /**
     * The Gilbert–Johnson–Keerthi algorithm is a method of determining the
     * intersection of two convex sets. Unlike many other distance algorithms, it
     * does not require that the geometry data be stored in any specific format, but
     * instead relies solely on a support function to iteratively generate closer
     * simplices to the correct answer using the Minkowski difference.
     * 
     * @param p An {@code ArrayList<Vector3D>} of vertices defining a convex hull.
     * @param q An {@code ArrayList<Vector3D>} of vertices defining a convex hull.
     * @return {@code true} if the convex hulls intersect, {@code false} otherwise.
     */
    public boolean GJK(ArrayList<Vector3D> p, ArrayList<Vector3D> q) {

        Vector3D initial = new Vector3D(1, 1, 1);

        Vector3D a = dirMaxVec(p, initial).sub(dirMaxVec(q, initial.getScaled(-1)));

        simplex.clear();

        simplex.add(a);

        dir = a.getScaled(-1);

        int it = 0;

        while (true) {

            a = dirMaxVec(p, dir).sub(dirMaxVec(q, dir.getScaled(-1)));

            if (a.dot(dir) <= 0) {

                return false;

            }

            simplex.add(a);

            if (calcSimplex()) {

                System.out.println("Number of iterations for GJK to converge: " + it);

                return true;

            }

            if (it > 20) {

                System.err.println("GJK did not converge, but instead exited after 21 iterations.");

                return false;

            }

            it++;

        }

    }

    private boolean calcSimplex() {

        switch (simplex.size()) {

        case 2:
            
            return line();

        case 3:
            
            return triangle();

        case 4:
            
            return tetrahedron();

        default:
            
            // Note: There should never be a case 1 or 5;

            System.err.println("Calc Simplex encountered a case outside of 2 to 4");

            return false;

        }

    }

    private boolean line() {

        Vector3D a = simplex.get(1);

        Vector3D b = simplex.get(0);

        Vector3D ao = a.getScaled(-1.0f);

        Vector3D ab = b.sub(a);

        if (ab.dot(ao) > 0) {

            dir = ab.cross(ao).cross(ab);

        } else {

            simplex.remove(0);

            dir = ao;

        }

        return false;

    }

    private boolean triangle() {

        Vector3D a = simplex.get(2);

        Vector3D b = simplex.get(1);

        Vector3D c = simplex.get(0);

        Vector3D ao = a.getScaled(-1.0f);

        Vector3D ab = b.sub(a);

        Vector3D ac = c.sub(a);

        Vector3D abc = ab.cross(ac);

        if (abc.cross(ac).dot(ao) > 0) {

            if (ac.dot(ao) > 0) {

                simplex.remove(1);

                dir = ac.cross(ao).cross(ac);

            } else {

                simplex.remove(0);

            }

        } else {

            if (ab.cross(abc).dot(ao) > 0) {

                simplex.remove(0);

            } else {

                if (abc.dot(ao) > 0) {

                    dir = abc;

                } else {

                    simplex.clear();

                    simplex.add(b);

                    simplex.add(c);

                    simplex.add(a);

                    dir = abc.getScaled(-1.0f);

                }

            }

        }

        return false;

    }

    private boolean tetrahedron() {

        Vector3D a = simplex.get(3);

        Vector3D b = simplex.get(2);

        Vector3D c = simplex.get(1);

        Vector3D d = simplex.get(0);

        Vector3D ao = a.getScaled(-1.0f);

        Vector3D ab = b.sub(a);

        Vector3D ac = c.sub(a);

        Vector3D ad = d.sub(a);

        Vector3D abc = ab.cross(ac);

        Vector3D acd = ac.cross(ad);

        Vector3D adb = ad.cross(ab);

        if (abc.dot(ao) > 0) {

            simplex.remove(0);

            return triangle();

        }

        if (acd.dot(ao) > 0) {

            simplex.remove(2);

            return triangle();

        }

        if (adb.dot(ao) > 0) {

            simplex.remove(1);

            return triangle();

        }

        return true;

    }

    private static Vector3D dirMaxVec(ArrayList<Vector3D> r, Vector3D v) {
        
        return r.get(dirMaxInt(r, v));

    }

    private static int dirMaxInt(ArrayList<Vector3D> r, Vector3D v) {

        float result = r.get(0).dot(v);

        float oldResult = r.get(0).dot(v);

        int answer = 0;

        for (int i = 1; i < r.size(); i++) {

            result = Math.max(result, r.get(i).dot(v));

            if (result > oldResult) {

                answer = i;

                oldResult = result;

            }

        }

        return answer;

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

            System.err.println("CMB attempted to intersect with an undefined boundary");

            return new IntersectData(false, 0, 0);

        }

    }

    public IntersectData intersect(CMB cmb) {

        float distanceToCenter = getPos().sub(cmb.getPos()).length();

        boolean gjk = GJK(getOffsetBoundary(), cmb.getOffsetBoundary());
        
        float distanceToBoundary = 0;

        if (gjk) {
            
            Polytope poly = new Polytope(simplex);
            
            distanceToBoundary = poly.EPA(getOffsetBoundary(), cmb.getOffsetBoundary());
            
        }

        return new IntersectData(gjk, distanceToCenter, distanceToBoundary);

    }

    public ArrayList<Vector3D> getBoundary() {

        return convexBoundary;

    }

    public ArrayList<Vector3D> getOffsetBoundary() {

        ArrayList<Vector3D> newBoundary = new ArrayList<Vector3D>();

        for (int i = 0; i < convexBoundary.size(); i++) {

            newBoundary.add(rot.toRotationMatrix().transform(convexBoundary.get(i)).add(getPos()));
            
        }
        
        //System.out.println(convexBoundary.get(0));
        
        //System.out.println(newBoundary.get(0));

        return newBoundary;

    }

    public void setBoundary(ArrayList<Vector3D> boundary) {

        this.convexBoundary = boundary;

    }

}
