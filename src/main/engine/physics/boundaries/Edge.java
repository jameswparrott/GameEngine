package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

public class Edge {
    
    Vector3D a;
    
    Vector3D b;
    
    public Edge(Vector3D a, Vector3D b) {
        
        // CCW orientation
        this.a = a;
        
        this.b = b;
        
    }
    
    public boolean shared(Edge edge) {
        
        return a.equals(edge.getB()) && b.equals(edge.getA());
        
    }
    
    public boolean linkedTo(Edge edge) {
        
        return b.equals(edge.getA());
        
    }
    
    public String toString() {
        
        return "[" + a.toString() + ", " + b.toString() + "]";
        
    }

    public Vector3D getA() {
        return a;
    }

    public Vector3D getB() {
        return b;
    }

}
