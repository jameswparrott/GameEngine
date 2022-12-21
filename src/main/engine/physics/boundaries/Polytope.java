package main.engine.physics.boundaries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import main.engine.core.Vector3D;

public class Polytope {
    
    private ArrayList<Vector3D> vertices;
    
    private ArrayList<Face> faces;
    
    public Polytope(ArrayList<Vector3D> vertices) {
        
        this(vertices, makeIndices(vertices));
        
    }
    
    public Polytope(ArrayList<Vector3D> vertices, ArrayList<Integer> indices) {
        
        this.vertices = vertices;
        
        this.faces = makeFaces(vertices, indices);
        
    }
    
    private static ArrayList<Integer> makeIndices(ArrayList<Vector3D> vertices) {
        
        Vector3D test = vertices.get(0).calcNormal(vertices.get(1), vertices.get(2));
        
        if (test.dot(vertices.get(0)) > 0) {
            
            return new ArrayList<Integer>(Arrays.asList(0, 1, 2, 0, 2, 3, 0, 3, 1, 1, 3, 2));
            
        }
            
        return new ArrayList<Integer>(Arrays.asList(0, 2, 1, 0, 3, 2, 0, 1, 3, 1, 2, 3));
        
    }
    
    private static ArrayList<Face> makeFaces(ArrayList<Vector3D> vertices, ArrayList<Integer> indices){
        
        ArrayList<Face> result = new ArrayList<Face>();
        
        for (int i = 0; i < 4; i ++) {
            
            result.add(new Face(vertices.get(indices.get(3 * i)), vertices.get(indices.get(3 * i + 1)), vertices.get(indices.get(3 * i + 2))));
            
        }
        
        return result;
        
    }
    
    public ArrayList<Vector3D> quickHull(ArrayList<Vector3D> points) {
        
        ArrayList<Vector3D> result    = new ArrayList<Vector3D>();
        
        ArrayList<Vector3D> newPoints = points;
        
        ArrayList<Integer> indices    = makeIndices(vertices);
        
        faces                         = makeFaces(vertices, indices);
        
        //Remove all points within the simplex 
        
        for (int i = newPoints.size() - 1; i > -1; i --) {
            
            boolean within = true;
            
            for (int j = 0; j < faces.size(); j ++) {
                
                if (faces.get(j).canSee(newPoints.get(i)))
                    
                    within = false;
                
            }
            
            if (within)
                
                newPoints.remove(i);
            
        }
        
        HashMap<Integer, Face> map = new HashMap<Integer, Face>();
        
        HashMap<Integer, Integer> mop = new HashMap<Integer, Integer>();
        
        //Map faces to points
        for (int i = 0; i < newPoints.size(); i ++) {
            
            //System.out.println("Point[" + i + "]: " + newPoints.get(i));
            
            for (int j = 0; j < faces.size(); j ++) {
                
                //System.out.println("Faces[" + j + "]: " + faces.get(j));
                
                if (faces.get(j).canSee(newPoints.get(i))) {
                    
                    //map.put(i, faces.get(j));
                    
                    //mop.put(i, j);
                    
                }
                
                map.put(1, faces.get(j));
                
                System.out.println("Map[" + i + "]: " + j);
                
            }
            
        }
        
        ArrayList<Face> mappedFaces = new ArrayList<Face>();
        
        for (int i = 0; i < newPoints.size(); i ++) {
            
            map.get(i);
            
            System.out.println("Map[" + i + "]: " + map.get(i));
            
//            System.out.println("p  : " + newPoints.get(i));
//            
//            System.out.println("i  : " + i);
//            
//            System.out.println("map: " + map.get(i));
//            
//            System.out.println("mop: " + mop.get(i));
            
        }
        
        System.out.println(map);
        
        System.exit(0);
        
        //Iterate through faces, adding new points, replacing faces
        
        Vector3D newPoint;
        
//        for (int i = 0; i < faces.size(); i ++) {
//            
//            newPoint = dirMaxVec(newPoints, faces.get(i).getNormal());
//            
//            if (faces.get(i).contains(newPoint)) {
//                
//                continue;
//                
//            } else {
//                
//                
//                
//            }
//            
//        }
        
        return result;
        
    }
    
    /**
     * The Expanding Polytope Algorithm is an algorithm used to calculate the penetration depth
     * between two convex hulls.
     * 
     * @return distance between boundaries of convex hulls
     */
    public float EPA(ArrayList<Vector3D> a, ArrayList<Vector3D> b) {
        
        int it = 0;
        
        while (true) {
            
            Face minFace = getMinFace();
            
            Vector3D normal = minFace.getNormal();
            
            Vector3D newPoint = dirMaxVec(a, normal).sub(dirMaxVec(b, normal.getScaled(-1)));
            
            System.out.println("Min face : " + minFace);
            
            System.out.println("Min norm : " + normal);
            
            System.out.println("New point: " + newPoint);
            
            System.out.println("Can see  : " + minFace.canSee(newPoint));
            
            if (minFace.contains(newPoint)) {
                
                System.out.println("EPA distance found: " + minFace.getDistToOrigin());
                
                System.out.println(); System.out.println(); System.out.println();
                
                return minFace.getDistToOrigin();
                
            }
            
            System.out.println("Iteration: " + it);
            
            it ++;
            
            for (int i = 0; i < faces.size(); i ++) {
                
                System.out.println("Faces  : " + faces.get(i));
                
                //System.out.println("Normals: " + faces.get(i).getNormal());
                
                //System.out.println("Check: " + (faces.get(i).getA().dot(faces.get(i).getNormal()) > 0));
                
            }
            
            System.out.println();
            
            if (!minFace.canSee(newPoint)) {
                
                System.exit(1);
                
            }
            
            expand(newPoint);
            
        }
        
    }
    
    public void expand(Vector3D newPoint) {
        
        //Find the indices of the faces seen by the new point
        ArrayList<Integer> indexSeen = indexSeen(newPoint);
        
        ArrayList<Face> removedFaces = new ArrayList<Face>();
        
        int numFaces = indexSeen.size();
        
        for (int i = 0; i < numFaces; i++) {
            
            removedFaces.add(faces.remove((int) indexSeen.get(numFaces - 1 - i)));
            
            System.out.println("  Removed faces: " + removedFaces.get(i));
            
        }
        
        System.out.println();
        
        for (int i = 0; i < faces.size(); i ++) {
            
            System.out.println("  Remaining faces: " + faces.get(i));
            
        }
        
        System.out.println();
        
        if (numFaces == 1)
            
            oneFace(newPoint, removedFaces.get(0));
        
        else {
            
            manyFaces(newPoint, removedFaces);
            
        }
        
    }
    
    private void oneFace(Vector3D newPoint, Face face) {
        
        faces.add(new Face(newPoint, face.getA(), face.getB()));
        faces.add(new Face(newPoint, face.getB(), face.getC()));
        faces.add(new Face(newPoint, face.getC(), face.getA()));
        
        System.out.println("        New faces: " + faces.get(faces.size() - 3));
        System.out.println("        New faces: " + faces.get(faces.size() - 2));
        System.out.println("        New faces: " + faces.get(faces.size() - 1));
        
        System.out.println();
        
    }
    
    private void manyFaces(Vector3D newPoint, ArrayList<Face> faces) {
        
        //ArrayList<Edge> edges = uniqueEdges(faces);
        
        ArrayList<Edge> edges = newUniqueEdges(faces);
        
        for (int i = 0; i < edges.size(); i ++) {
            
            System.out.println("        New point: " + newPoint);
            
            System.out.println("        New edges: " + edges.get(i));
            
            Face face = new Face(newPoint, edges.get(i).getA(), edges.get(i).getB());
            
            System.out.println("        New face: " + face);
            
            //this.faces.add(new Face(newPoint, edges.get(i).getA(), edges.get(i).getB()));
            this.faces.add(face);
            
        }
        
        System.out.println();
        
    }
    
    private static ArrayList<Edge> newUniqueEdges(ArrayList<Face> faces) {
        
        ArrayList<Edge> edges = new ArrayList<Edge>(3*faces.size());
        
        Face a, b;
        
        for (int i = 0; i < faces.size(); i ++) {
            
            a = faces.get(i);
            
            boolean x = true, y = true, z = true;
            
            System.out.println("    i: " + i);
            
            System.out.println("    Test edges: " + a.getE());
            System.out.println("    Test edges: " + a.getF());
            System.out.println("    Test edges: " + a.getG());
            
            for (int j = i + 1; j < faces.size(); j ++) {
             
                b = faces.get(j);
                
                System.out.println("      j: " + j);
                
                System.out.println("      Test face: " + b);
                
                if (x && b.sharesEdge(a.getE()))
                    x = false;
                if (y && b.sharesEdge(a.getF()))
                    y = false;
                if (z && b.sharesEdge(a.getG()))
                    z = false;
                
                System.out.println("      Contains E: " + x);
                System.out.println("      Contains F: " + y);
                System.out.println("      Contains G: " + z);
                
            }
            
            if (i == faces.size() - 1) {
                
                for (int j = 0; j < i; j ++) {
                    
                    b = faces.get(j);
                    
                    System.out.println("      j: " + j);
                    
                    System.out.println("      Test face: " + b);
                    
                    if (x && b.sharesEdge(a.getE()))
                        x = false;
                    if (y && b.sharesEdge(a.getF()))
                        y = false;
                    if (z && b.sharesEdge(a.getG()))
                        z = false;
                    
                    System.out.println("      Contains E: " + x);
                    System.out.println("      Contains F: " + y);
                    System.out.println("      Contains G: " + z);
                    
                }
                
            }
            
            if (x)
                edges.add(a.getE());
            if (y)
                edges.add(a.getF());
            if (z)
                edges.add(a.getG());
            
        }
        
        return edges;
        
    }
    
    private ArrayList<Integer> indexSeen(Vector3D point){
        
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        System.out.println("  New point: " + point);
        
        for (int i = 0; i < faces.size(); i ++) {
            
            System.out.println("  Faces: " + faces.get(i));
            
            if(faces.get(i).canSee(point)) {
                
                System.out.println("  Seen!");
                
                result.add(i);
                
            }
            
        }
        
        System.out.println();
        
        return result;
        
    }
    
    private Face getMinFace() {
        
        Face result = null;
        
        float dist = Float.MAX_VALUE;
        
        for (int i = 0; i < faces.size(); i ++) {
            
            if (dist > faces.get(i).getDistToOrigin()) {
                
                dist = faces.get(i).getDistToOrigin();
                
                result = faces.get(i);
                
            }
            
        }
        
        return result;
        
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
    
    public Face removeFace(int i) {
        
        return faces.remove(i);
        
    }
    
    public void addFace(Face face) {
        
        faces.add(face);
        
    }
    
    public ArrayList<Face> getFaces(){
        
        return faces;
        
    }
    
    public Face getFace(int i) {
        
        return faces.get(i);
        
    }
    
    public ArrayList<Vector3D> getVertices(){
        
        return vertices;
        
    }
    
    public Vector3D getVertex(int i) {
        
        return vertices.get(i);
        
    }

}
