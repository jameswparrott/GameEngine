package main.engine.physics.materials;

public class PhysicsMaterial {

    private boolean permeable;

    private float restitution;

    /**
     * The type of material a physics body is made of.
     * 
     * @param permeable   whether the physics body is permeable or not
     * @param restitution the coefficient of restitution this physics body will
     *                    experience
     */
    public PhysicsMaterial(boolean permeable, float restitution) {

        this.permeable = permeable;

        this.restitution = restitution;

    }

    public boolean getPermeable() {

        return permeable;

    }

    public float getRestitution() {

        return restitution;

    }

}
