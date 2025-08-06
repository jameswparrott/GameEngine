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
        
        return (a.epsilonEquals(edge.getB()) && b.epsilonEquals(edge.getA())) || (a.epsilonEquals(edge.getA()) && b.epsilonEquals(edge.getB()));
        
    }
    
    public boolean linkedTo(Edge edge) {
        
        return b.epsilonEquals(edge.getA());
        
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
