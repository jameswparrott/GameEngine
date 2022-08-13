package main.engine.physics.boundaries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import main.engine.core.Vector3D;

public class Polytope {
    
    //private ArrayList<Vector3D> vertices;
    
    //private ArrayList<Integer> indices;
    
    private ArrayList<Face> faces;
    
    private ArrayList<Vector3D> a, b;
    
    public Polytope(ArrayList<Vector3D> vertices, ArrayList<Vector3D> a, ArrayList<Vector3D> b) {
        
        this(vertices, makeIndices(vertices), a, b);
        
    }
    
    public Polytope(ArrayList<Vector3D> vertices, ArrayList<Integer> indices, ArrayList<Vector3D> a, ArrayList<Vector3D> b) {
        
        this.faces = makeFaces(vertices, indices);
        
        this.a = a;
        
        this.b = b;
        
    }
    
    private static ArrayList<Integer> makeIndices(ArrayList<Vector3D> vertices){
        
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
    
    private static ArrayList<Face> makeFaces(ArrayList<Vector3D> vertices, ArrayList<Integer> indices){
        
        ArrayList<Face> result = new ArrayList<Face>();
        
        for (int i = 0; i < 4; i ++) {
            
            result.add(new Face(vertices.get(indices.get(3 * i)), vertices.get(indices.get(3 * i + 1)), vertices.get(indices.get(3 * i + 2))));
            
        }
        
        return result;
        
    }
    
    /**
     * The Expanding Polytope Algorithm is an algorithm used to calculate the distance 
     * between the boundaries of two intersecting convex hulls.
     * 
     * @return distance between boundaries of convex hulls
     */
    public float EPA() {
        
        int it = 0;
        
        while (true) {
            
            it ++;
            
            Face minFace = getMinFace();
            
            Vector3D normal = minFace.getNormal();
            
            Vector3D newPoint = dirMaxVec(a,normal).sub(dirMaxVec(b,normal.getScaled(-1.0f)));
            
            if (minFace.contains(newPoint)) {
                
                System.out.println("EPA distance found: " + minFace.getDistToOrigin());
                
                return minFace.getDistToOrigin();
                
            }
            
            printFaces("All Faces", faces);
            
            expand(newPoint);
            
            System.out.println("EPA iteration: " + it);
            
            if (it > 10)
                
                System.exit(1);
            
        }
        
    }
    
    public void expand(Vector3D newPoint) {
        
        //Find the indices of the faces seen by the new point
        ArrayList<Integer> indexSeen = indexSeen(newPoint);
        
        ArrayList<Face> removedFaces = new ArrayList<Face>();
        
        int numFaces = indexSeen.size();
        
        System.out.println("Num faces: " + numFaces);
        
        for (int i = 0; i < numFaces; i++) {
            
            removedFaces.add(faces.remove((int) indexSeen.get(numFaces - 1 - i)));
            
        }
        
        printFaces("  Removed Faces", removedFaces);
        
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
        
    }
    
    private void manyFaces(Vector3D newPoint, ArrayList<Face> faces) {
        
        ArrayList<Edge> edges = uniqueEdges(faces);
        
        for (int i = 0; i < edges.size(); i ++) {
            
            this.faces.add(new Face(newPoint, edges.get(i).getA(), edges.get(i).getB()));
            
        }
        
    }
    
    private static ArrayList<Edge> uniqueEdges(ArrayList<Face> faces) {
        
        ArrayList<Edge> edges = new ArrayList<Edge>();
        
        //Order faces
        
        //printFaces("  Into unique edges", faces);
        
        edges.add(getUniqueEdge(faces));
        
        //System.out.println();
        
        //System.out.println("  Unique edge: " + edges.get(0));
        
        int it = 0;
        
        while (!edges.get(it).linkedTo(edges.get(0))) {
            
            System.out.println();
            
            System.out.println("  Unique edges iteration: " + it);
            
            edges.add(getLinkedEdge(edges.get(it), faces));
            
            it ++;
            
        }
        
        System.out.println("  Num unique edges: " + edges.size());
        
        return edges;
        
    }
    
    private static Edge getUniqueEdge(ArrayList<Face> faces) {
        
        Face initialFace = faces.get(0);
        
        //System.out.println("  Unique edges:" + initialFace);
        
        Edge e = initialFace.getE();
        
        Edge f = initialFace.getF();
        
        Edge g = initialFace.getG();
        
        Edge result = e;
        
        for (int i = 1; i < faces.size(); i ++) {
            
            if (result == e && faces.get(i).sharesEdge(e)) {
                
                result = f;
                
            } else if (result == f && faces.get(i).sharesEdge(f)) {
                
                return g;
                
            }
            
        }
        
        return result;
        
    }
    
    private static Edge getLinkedEdge(Edge edge, ArrayList<Face> faces) {
        
        Face face = null;
        
        System.out.println("    Edge: " + edge);
        
        for (int i = 0; i < faces.size(); i ++) {
            
            System.out.println("    Get linked edge iteration: " + i);
            
            face = faces.get(i);
            
            if (edge.linkedTo(face.getE()) && isUnique(face.getE(), faces)) {
                
                System.out.println("      Linked to: " + face.getE().toString());
                
                return face.getE();
                
            } else if (edge.linkedTo(face.getF()) && isUnique(face.getF(), faces)) {
                
                System.out.println("      Linked to: " + face.getF().toString());
                
                return face.getF();
                
            } else if (edge.linkedTo(face.getG()) && isUnique(face.getG(), faces)) {
                
                System.out.println("      Linked to: " + face.getG().toString());
                
                return face.getG();
                
            }
            
        }
        
        System.err.println("      No linked edges found within list!");
        
        for (int i = 0; i < faces.size(); i ++) {

            System.out.println("        " + faces.get(i).getE() + " " + isUnique(face.getE(), faces));
            System.out.println("        " + faces.get(i).getF() + " " + isUnique(face.getF(), faces));
            System.out.println("        " + faces.get(i).getG() + " " + isUnique(face.getG(), faces));
        
        }
        
        System.exit(1);
        
        return null;
        
    }
    
    private static boolean isUnique(Edge edge, ArrayList<Face> faces) {
        
        for (int i = 0; i < faces.size(); i ++) {
            
            if ((edge.shared(faces.get(i).getE()) || 
                 edge.shared(faces.get(i).getF()) || 
                 edge.shared(faces.get(i).getG()))
                )
                return false;
            
        }
        
        return true;
        
    }
    
    private ArrayList<Integer> indexSeen(Vector3D point){
        
        ArrayList<Integer> result = new ArrayList<Integer>();
        
        for (int i = 0; i < faces.size(); i ++) {
            
            if(faces.get(i).canSee(point)) {
                
                result.add(i);
                
            }
            
        }
        
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
    
    private static void printFaces(String title, ArrayList<Face> faces) {
        
        System.out.println(title + ": ");
        
        for (int i = 0; i < faces.size(); i ++) {
            
            System.out.println("  " + faces.get(i).toString());
            
        }
        
    }

}
