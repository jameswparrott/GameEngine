package main.engine.physics;

import main.engine.core.Quaternion;
import main.engine.core.Vector3D;
import main.engine.physics.boundaries.Boundary;
import main.engine.physics.materials.PhysicsMaterial;

public class PhysicsBody {

    private float mass;

    private Boundary boundary;

    private PhysicsMaterial material;

    private Vector3D acceleration;

    private Vector3D velocity;

    private Vector3D pos;
    
    private Quaternion rot;

    private Vector3D angularAcceleration;

    private Vector3D angularVelocity;

    /**
     * Constructs a physics body.
     * 
     * @param mass
     * @param boundary
     * @param material physics material
     * @param pos      initial position
     */
    public PhysicsBody(float mass, Boundary boundary, PhysicsMaterial material, Vector3D pos) {

        this(mass, boundary, material, pos, Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO);

    }

    /**
     * Constructs a physics body.
     * 
     * @param mass
     * @param boundary
     * @param material     physics material
     * @param pos          initial position
     * @param velocity     initial velocity
     * @param acceleration initial acceleration
     */
    public PhysicsBody(float mass, Boundary boundary, PhysicsMaterial material, Vector3D pos, Vector3D velocity,
            Vector3D acceleration) {

        this(mass, boundary, material, pos, velocity, acceleration, Vector3D.ZERO, Vector3D.ZERO);

    }

    /**
     * Constructs a physics body.
     * 
     * @param mass
     * @param boundary
     * @param material            physics material
     * @param pos                 initial position
     * @param velocity            initial velocity
     * @param acceleration        initial acceleration
     * @param angularVelocity     initial angular velocity
     * @param angularAcceleration initial angular acceleration
     */
    public PhysicsBody(float mass, Boundary boundary, PhysicsMaterial material, Vector3D pos, Vector3D velocity,
            Vector3D acceleration, Vector3D angularVelocity, Vector3D angularAcceleration) {

        this.mass = mass;

        this.boundary = boundary;

        this.material = material;

        this.pos = pos;

        this.velocity = velocity;

        this.acceleration = acceleration;

        this.angularVelocity = angularVelocity;

        this.angularAcceleration = angularAcceleration;

    }

    public void integrate(float delta) {

        // Velocity changes linearly wrt time
        velocity = velocity.add(acceleration.getScaled(delta));

        // Angular velocity changes linearly wrt time
        angularVelocity = angularVelocity.add(angularAcceleration.getScaled(delta));

        pos = pos.add(velocity.getScaled(delta));

    }

    public float getMass() {

        return mass;

    }

    public void setMass(float mass) {

        this.mass = mass;

    }

    public Vector3D getPos() {

        return pos;

    }

    public void setPos(Vector3D pos) {

        this.pos = pos;

    }

    public Vector3D getVelocity() {

        return velocity;

    }

    public void setVelocity(Vector3D velocity) {

        this.velocity = velocity;

    }

    public Vector3D getAcceleration() {

        return acceleration;

    }

    public void setAcceleration(Vector3D acceleration) {

        this.acceleration = acceleration;

    }

    public Vector3D getAngularVelocity() {

        return angularVelocity;

    }

    public void setAngularVelocity(Vector3D angularVelocity) {

        this.angularVelocity = angularVelocity;

    }

    public Vector3D getAngularAcceleration() {

        return angularAcceleration;

    }

    public void setAngularAcceleration(Vector3D angularAcceleration) {

        this.angularAcceleration = angularAcceleration;

    }

    public Boundary getBoundary() {

        return boundary;

    }

    public PhysicsMaterial getMaterial() {

        return material;

    }
}
