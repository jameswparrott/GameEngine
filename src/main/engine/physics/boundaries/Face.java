package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

public class Face {

    private Vector3D a, b, c;
    
    private Vector3D n;
    
    private float distToOrigin;
    
    private Edge e, f, g;

    public Face(Vector3D a, Vector3D b, Vector3D c) {
        
        // CCW orientation
        this.a = a;
        
        this.b = b;
        
        this.c = c;
        
        n = a.calcNormal(b, c).normalize();
        
        distToOrigin = a.dot(n);
        
        e = new Edge(a, b);
        
        f = new Edge(b, c);
        
        g = new Edge(c, a);
        
    }
    
    public boolean canSee(Vector3D point) {
        
        return point.sub(a).dot(n) > 0;
        
    }
    
    public boolean contains(Vector3D point) {
        
        return point.equals(a) || point.equals(b) || point.equals(c);
        
    }
    
    public boolean sharesEdge(Edge edge) {
        
        return  getE().shared(edge) || getF().shared(edge) || getG().shared(edge);
        
    }
    
    public boolean sharesEdges(Face face) {
        
        return  getE().shared(face.getE()) || getE().shared(face.getF()) || getE().shared(face.getG()) ||
                getF().shared(face.getE()) || getF().shared(face.getF()) || getF().shared(face.getG()) ||
                getG().shared(face.getE()) || getG().shared(face.getF()) || getG().shared(face.getG());
        
    }
    
    public boolean equals(Face face) {
        
        return a.equals(face.getA()) && b.equals(face.getB()) && c.equals(face.getC());
        
    }
    
    public Vector3D[] unique(Face face) {
        
        Vector3D[] result = new Vector3D[4];
        
        result[0] = a;
        
        Vector3D vertex = face.contains(a) ? face.contains(b) ? face.getC() : face.getB() : face.getA();
        
        if (!contains(face.getA())) {
            
            //b and c are shared
            
            result[1] = b;
            
            result[2] = vertex;
            
            result[3] = c;
            
        } else if (!contains(face.getB())) {
            
            //c and a are shared
            
            result[1] = b;
            
            result[2] = c;
            
            result[3] = vertex;
            
        } else {
            
            //a and b are shared
            
            result[1] = vertex;
            
            result[2] = b;
            
            result[3] = c;
            
        }
        
        return result;
        
    }
    
    public Vector3D getA() {
        
        return a;
        
    }

    public Vector3D getB() {
        
        return b;
        
    }
    
    public Vector3D getC() {
        
        return c;
        
    }

    public Vector3D getNormal() {
        
        return n;
        
    }

    public float getDistToOrigin() {
        
        return distToOrigin;
        
    }
    
    public Edge getE() {
        
        return e;
        
    }

    public Edge getF() {
        
        return f;
        
    }
    
    public Edge getG() {
        
        return g;
        
    }
    
    @Override
    public String toString() {
        
        return "{" + a.toString() + ", " + b.toString() + ", " + c.toString() + "}";
        
    }
    
}
