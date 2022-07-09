package main.engine.physics.boundaries;

import java.util.ArrayList;
import java.util.Arrays;

import main.engine.core.Transform;
import main.engine.core.Vector3D;
import main.engine.physics.IntersectData;
import main.engine.rendering.Mesh;

public class CMB extends Boundary {

    // TODO: Add GJK optimizations if possible (not important)

    // TODO: Add option to return lengths from GJK algorithm

    // TODO: Create a library for convex object creation and analysis

    // TODO: Add a max length variable to the object, derived from the construction
    // of the convex boundary

    private ArrayList<Vector3D> convexBoundary;

    private Vector3D dir;

    private ArrayList<Vector3D> simplex;

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

        dir = new Vector3D(1, 1, 1);

        simplex = new ArrayList<Vector3D>(4);

        if (convex) {

            convexBoundary = vertices;

        } else {

            convexBoundary = quickHull(vertices);

        }

    }

    public void update(Transform transform) {

        setPos(transform.getPos());

        for (int i = 0; i < convexBoundary.size(); i++) {

            convexBoundary.set(i, transform.getRot().toRotationMatrix().transform(convexBoundary.get(i)));

        }

    }

    private static ArrayList<Vector3D> quickHull(ArrayList<Vector3D> vertices) {

        ArrayList<Vector3D> verts = vertices;

        Vector3D[] baseA = new Vector3D[3];

        Vector3D[] baseB = new Vector3D[3];

        ArrayList<Vector3D> result = new ArrayList<Vector3D>();

        ArrayList<Vector3D> setA = new ArrayList<Vector3D>();

        ArrayList<Vector3D> setB = new ArrayList<Vector3D>();

        int index = 0;

        // Find the index of the vertex furthest in the (0,1,0) direction
        index = dirMaxInt(verts, new Vector3D(0, 1, 0));

        // Add this vertex to the result (i.e. vertex in convex hull)
        result.add(verts.get(index));

        // Add this vertex to the base triangles 
        baseA[0] = verts.get(index);

        baseB[2] = verts.get(index);

        // Remove vertex from list of possible vertices (no need to keep checking it)
        verts.remove(index);

        index = dirMaxInt(verts, new Vector3D(1, -1, 1));

        result.add(verts.get(index));

        baseA[1] = verts.get(index);

        baseB[1] = verts.get(index);

        verts.remove(index);

        index = dirMaxInt(verts, new Vector3D(-1, -1, -1));

        result.add(verts.get(index));

        baseA[2] = verts.get(index);

        baseB[0] = verts.get(index);

        verts.remove(index);

        // Point on the plane, a vertex of the triangle
        Vector3D p_point = result.get(0);

        // Normal of the plane
        Vector3D p_normal = result.get(2).sub(result.get(1)).cross(result.get(0).sub(result.get(1)));

        for (int i = 0; i < verts.size(); i++) {

            // Which side of the plane is this vertex on?
            if (p_normal.dot(verts.get(i).sub(p_point)) > 0) {

                setA.add(verts.get(i));

            } else if (p_normal.dot(verts.get(i).sub(p_point)) < 0) {

                setB.add(verts.get(i));

            }

        }

        findHull(result, setA, baseA, p_normal);

        findHull(result, setB, baseB, p_normal.getScaled(-1.0f));

        return result;

    }

    private static void findHull(ArrayList<Vector3D> result, ArrayList<Vector3D> subset, Vector3D[] base,
            Vector3D norm) {

        if (subset.isEmpty()) {

            return;

        }

        ArrayList<Vector3D> setA = new ArrayList<Vector3D>();

        ArrayList<Vector3D> setB = new ArrayList<Vector3D>();

        ArrayList<Vector3D> setC = new ArrayList<Vector3D>();

        Vector3D[] a_base = new Vector3D[3];

        Vector3D[] b_base = new Vector3D[3];

        Vector3D[] c_base = new Vector3D[3];

        int index = 0;

        index = dirMaxInt(subset, norm);

        result.add(subset.get(index));

        a_base[0] = subset.get(index);

        a_base[1] = base[0];

        a_base[2] = base[1];

        Vector3D p_normal = a_base[0].sub(a_base[1]).cross(a_base[2].sub(a_base[1]));

        for (int i = 0; i < subset.size(); i++) {

            if (p_normal.dot(subset.get(i).sub(a_base[0])) > 0) {

                setA.add(subset.get(i));

            }

        }

        findHull(result, setA, a_base, p_normal);

        b_base[0] = subset.get(index);

        b_base[1] = base[1];

        b_base[2] = base[2];

        p_normal = b_base[0].sub(b_base[1]).cross(b_base[2].sub(b_base[1]));

        for (int i = 0; i < subset.size(); i++) {

            if (p_normal.dot(subset.get(i).sub(b_base[0])) > 0) {

                setB.add(subset.get(i));

            }

        }

        findHull(result, setB, b_base, p_normal);

        c_base[0] = subset.get(index);

        c_base[1] = base[2];

        c_base[2] = base[0];

        p_normal = c_base[0].sub(c_base[1]).cross(c_base[2].sub(c_base[1]));

        for (int i = 0; i < subset.size(); i++) {

            if (p_normal.dot(subset.get(i).sub(c_base[0])) > 0) {

                setC.add(subset.get(i));

            }

        }

        findHull(result, setC, c_base, p_normal);

        subset.remove(index);

        return;

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

        // Vector3D initial = p.get(0).sub(q.get(0));

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

                System.out.println("Number of iterations: " + it);

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

        // Note: There will never be a case 1 or a case 5;

        case 2:

            // Case 2, a line
            return line();

        case 3:

            // Case 3, a triangle
            return triangle();

        case 4:

            // Case 4, a tetrahedron
            return tetrahedron();

        default:

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

    /**
     * The Expanding Polytope Algorithm is an algorithm used to calculate the distance 
     * between the boundaries of two intersecting convex hulls.
     * 
     * @return distance between boundaries of convex hulls
     */
    private float EPA(ArrayList<Vector3D> a, ArrayList<Vector3D> b) {

        float minDistance = Float.MAX_VALUE;
        
        float minSurfaceDistance = Float.MAX_VALUE;
        
        int minFace = 0;
        
        ArrayList<Vector3D> vertices    = new ArrayList<Vector3D>(simplex);
        
        ArrayList<Integer>  indices;
        
        ArrayList<Vector3D> normals     = new ArrayList<Vector3D>();
        
        ArrayList<Boolean>  surfaces    = new ArrayList<Boolean>();
        
        ArrayList<Vector3D> points      = new ArrayList<Vector3D>();
        
        ArrayList<Float>    distances   = new ArrayList<Float>();
        
        indices = initIndices(vertices);
        
        for (int i = 0; i < 4; i ++) {
            
            //Normal of the corresponding face
            normals.add(vertices.get(indices.get(3*i)).calcNormal(  vertices.get(indices.get(3*i + 1)), 
                                                                    vertices.get(indices.get(3*i + 2))));
            
            //Get the point furthest in the direction of the face normal on the Minkowski difference
            points.add(dirMaxVec(a,normals.get(i)).sub(dirMaxVec(b,normals.get(i).getScaled(-1.0f))));
            
            //Is the face on the surface of the Minkowski difference (is it already in the face)
            surfaces.add(   points.get(i).equals(vertices.get(indices.get(3*i       ))) || 
                            points.get(i).equals(vertices.get(indices.get(3*i + 1   ))) || 
                            points.get(i).equals(vertices.get(indices.get(3*i + 2   ))));
            
            //Get the temporary distance, distance from the origin to the face
            distances.add(distanceToFace(vertices.get(indices.get(3*i)), normals.get(i)));
            
            if (distances.get(i) < minDistance) {
                
                minDistance = distances.get(i);
                
                minFace = i;
                
                if (surfaces.get(i)) {
                    
                    minSurfaceDistance = minDistance;
                    
                }
                
            }
            
        }
        
        while (minDistance < minSurfaceDistance) {
            
            Vector3D newPoint = points.get(minFace);
            
            int numSeenFaces = 0;
            
            int seenFaceA = -1;
            
            int seenFaceB = -1;
            
            for (int i = 0; i < normals.size(); i++) {
                
                if (canSeeFace(newPoint, vertices.get(indices.get(3*i)), normals.get(i))) {
                    
                    numSeenFaces ++;
                    
                    /*
                     * I postulate, but have yet to prove, that the maximum number of faces
                     * seen by an additional point on the Minkowski difference is two. The 
                     * result appears obvious, I will leave the proof as an exercie for the
                     * reader.
                     */
                    if (numSeenFaces == 0) {
                        
                        seenFaceA = i;
                        
                    } else {
                        
                        //seenFaceB > seenFaceA
                        seenFaceB = i;
                        
                    }
                    
                }
                
            }
            
            //Add in the new vertex
            vertices.add(newPoint);
            
            if (numSeenFaces == 1) {
                
                //Remove a single face
                
                    //vertices indices normals surfaces points distances minface mindistance minsurfacedistance
                
                //No need to remove any vertices, vertices will only be added
                
                //Remove the indices for the removed face (in reverse order)
                indices.remove(3 * seenFaceA + 2);
                indices.remove(3 * seenFaceA + 1);
                indices.remove(3 * seenFaceA);
                
                //Remove the normal of the corresponding face
                normals.remove(vertices.get(seenFaceA));
                
                //Remove the corresponding surface on the minkowski difference
                surfaces.remove(seenFaceA);
                
                //Remove any calculated points
                points.remove(seenFaceA);
                
                //Remove the corresponding distance associated with the face
                distances.remove(seenFaceA);
                
                //Reconstruct new faces
                indices.add(3 * seenFaceA);
                indices.add(3 * seenFaceA + 1);
                indices.add(vertices.size() - 1); //Last vertex
                
                indices.add(3 * seenFaceA + 1);
                indices.add(3 * seenFaceA + 2);
                indices.add(vertices.size() - 1);
                
                indices.add(3 * seenFaceA + 2);
                indices.add(3 * seenFaceA);
                indices.add(vertices.size() - 1);
                
                //normals.add(vertices.get(indices.get(3 * seenFaceA)).calcNormal(a, b));
                //normals.add();
                //normals.add();
                
                //surfaces.add();
                //surfaces.add();
                //surfaces.add();
                
                //points.add();
                //points.add();
                //points.add();
                
                //distances.add();
                //distances.add();
                //distances.add();
                
            } else if (numSeenFaces == 2) {
                
                //Using the four unique points, delete both faces
                
                    //vertices indices normals surfaces points distances minface mindistance minsurfacedistance
                
                //No need to remove any vertices, vertices will only be added
                
                //Remove the normal of the corresponding face
                normals.remove(vertices.get(seenFaceB));
                normals.remove(vertices.get(seenFaceA));
                
                //Remove any calculated points
                points.remove(seenFaceB);
                points.remove(seenFaceA);
                
                //Remove the corresponding surface on the minkowski difference
                surfaces.remove(seenFaceB);
                surfaces.remove(seenFaceA);
                
                //Remove the corresponding distance associated with the face
                distances.remove(seenFaceB);
                distances.remove(seenFaceA);
                
                //Remove the indices for the removed face (in reverse order)
                indices.remove(3 * seenFaceB + 2);
                indices.remove(3 * seenFaceB + 1);
                indices.remove(3 * seenFaceB);
                
                indices.remove(3 * seenFaceA + 2);
                indices.remove(3 * seenFaceA + 1);
                indices.remove(3 * seenFaceA);
                
                //Reconstruct new faces
                
            } else {
                
                //Should never happen "in theory", throw some kind of error
                System.err.println("EPA encountered an unaccounted number of faces: " + numSeenFaces );
                
            }
            
            //Both branches lead to a net addition of two faces
            
            minDistance = 1;
            
            minSurfaceDistance = 0;
            
        }

        return minSurfaceDistance;

    }
    
    private ArrayList<Integer> initIndices(ArrayList<Vector3D> vertices) {
        
        Vector3D test = vertices.get(0).calcNormal(vertices.get(1), vertices.get(2));
        
        if (test.dot(vertices.get(0)) > 0) {
            
            return new ArrayList<Integer>(Arrays.asList(    0, 1, 2, 
                                                            0, 2, 3, 
                                                            0, 3, 1, 
                                                            1, 3, 2));
            
        } else {
            
            return new ArrayList<Integer>(Arrays.asList(    0, 2, 1, 
                                                            0, 3, 2, 
                                                            0, 1, 3, 
                                                            1, 2, 3));
            
        }
        
    }
    
    
    private void reconstruct(   ArrayList<Vector3D> vertices, 
                                ArrayList<Integer>  indices, 
                                ArrayList<Vector3D> normals,
                                ArrayList<Boolean>  surfaces,
                                ArrayList<Vector3D> points,
                                ArrayList<Float>    distances,
                                int minFace) {
        
        Vector3D newPoint = points.get(minFace);
        
        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        
        for (int i = 0; i < normals.size(); i ++) {
            
            if (canSeeFace(newPoint, vertices.get(indices.get(3*i)), normals.get(i))) {
                
                toRemove.add(i);
                
            }
            
        }
        
        System.out.println("Number of faces facing towards the new point: " + toRemove.size());
        
        vertices.add(newPoint);
        
        if (toRemove.size() > 1) {
            
            //case 2
            int indexa = indices.get(3 * toRemove.get(0));
            
        } else if (toRemove.size() == 1){
            
            //case 1
            indices.add(3 * toRemove.get(0));
            indices.add(3 * toRemove.get(0) + 1);
            indices.add(vertices.size() - 1);
            
            indices.add(3 * toRemove.get(0) + 1);
            indices.add(3 * toRemove.get(0) + 2);
            indices.add(vertices.size() - 1);
            
            indices.add(3 * toRemove.get(0) + 2);
            indices.add(3 * toRemove.get(0));
            indices.add(vertices.size() - 1);
            
        }
        
        for (int i = toRemove.size() - 1; i >= 0; i ++) {
            
            indices.remove(3*i + 2);
            indices.remove(3*i + 1);
            indices.remove(3*i);
            
            normals.remove(i);
            surfaces.remove(i);
            points.remove(i);
            distances.remove(i);
            
        }
        
    }
    
    
    private boolean onSurface(Vector3D newPoint, ArrayList<Vector3D> vertices) {
        
        for (int i = 0; i < vertices.size(); i ++) {
            
            if (newPoint.equals(vertices.get(i))) {
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
    
    private boolean canSeeFace(Vector3D newPoint, Vector3D pointOnTriangle, Vector3D normal) {
        
        return newPoint.sub(pointOnTriangle).dot(normal) > 0;
        
    }
    
    
    private float distanceToFace(Vector3D pointOnTriangle, Vector3D normal) {
        
        return pointOnTriangle.dot(normal.getNorm());
        
    }
    

    //If you subtract each element of a dirMaxTri array you will still get points on a Minkowski 
    //  difference but they may not be unique.
    @SuppressWarnings("unused")
    private static Vector3D[] dirMaxTri(ArrayList<Vector3D> r, Vector3D v) {

        Vector3D[] answer = new Vector3D[3];

        float[] result = new float[3];

        float[] oldResult = new float[3];

        answer[0] = r.get(0);
        answer[1] = r.get(1);
        answer[2] = r.get(2);

        result[0] = r.get(0).dot(v);
        result[1] = r.get(1).dot(v);
        result[2] = r.get(2).dot(v);

        oldResult[0] = r.get(0).dot(v);
        oldResult[1] = r.get(1).dot(v);
        oldResult[2] = r.get(2).dot(v);

        for (int i = 2; i < r.size(); i++) {

            result[0] = Math.max(result[0], r.get(i).dot(v));

            result[1] = Math.max(result[1], r.get(i).dot(v));

            result[2] = Math.max(result[0], r.get(i).dot(v));

            if (result[0] > oldResult[0]) {

                answer[0] = r.get(i);

                oldResult[0] = result[0];

            } else if (result[1] > oldResult[1]) {

                answer[1] = r.get(i);

                oldResult[1] = result[1];

            } else if (result[2] > oldResult[2]) {

                answer[2] = r.get(i);

                oldResult[2] = result[2];

            }

        }

        return answer;

    }
    

    private static Vector3D dirMaxVec(ArrayList<Vector3D> r, Vector3D v) {

        float result = r.get(0).dot(v);

        float oldResult = r.get(0).dot(v);

        Vector3D answer = r.get(0);

        for (int i = 1; i < r.size(); i++) {

            result = Math.max(result, r.get(i).dot(v));

            if (result > oldResult) {

                answer = r.get(i);

                oldResult = result;

            }

        }

        return answer;

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

        Vector3D dirA = cmb.getPos().sub(getPos());

        Vector3D dirB = getPos().sub(cmb.getPos());

        Vector3D furthestA = dirMaxVec(getOffsetBoundary(), dirA);

        Vector3D furthestB = dirMaxVec(cmb.getOffsetBoundary(), dirB);

        float distanceToBoundaryA = getPos().sub(furthestA).length();

        float distanceToBoundaryB = cmb.getPos().sub(furthestB).length();

        float distanceToBoundary = distanceToCenter - distanceToBoundaryA - distanceToBoundaryB;

        if (gjk) {
            
            EPA(getOffsetBoundary(), cmb.getOffsetBoundary());
            
        }

        return new IntersectData(gjk, distanceToCenter, distanceToBoundary);

    }
    

    public ArrayList<Vector3D> getBoundary() {

        return convexBoundary;

    }
    

    public ArrayList<Vector3D> getOffsetBoundary() {

        ArrayList<Vector3D> newBoundary = new ArrayList<Vector3D>();

        for (int i = 0; i < convexBoundary.size(); i++) {

            newBoundary.add(convexBoundary.get(i).add(getPos()));

        }

        return newBoundary;

    }
    

    public void setBoundary(ArrayList<Vector3D> boundary) {

        this.convexBoundary = boundary;

    }
    

}
