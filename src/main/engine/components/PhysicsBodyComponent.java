package main.engine.components;

import main.engine.physics.PhysicsBody;

public class PhysicsBodyComponent extends GameComponent {

    private PhysicsBody physicsBody;

    /**
     * Constructs a physics body component allowing game objects to interact with
     * each other via the physics engine.
     * 
     * @param physicsBody physics body this component will use
     */
    public PhysicsBodyComponent(PhysicsBody physicsBody) {

        this.physicsBody = physicsBody;

    }

    @Override
    public void input(float delta) {

        // TODO: take some input

    }

    @Override
    public void update(float delta) {

        getTransform().setPos(physicsBody.getPos());
        
        getTransform().setRot(physicsBody.getRot());
        
        physicsBody.getBoundary().update(getTransform());

    }

    public PhysicsBody getPhysicsBody() {

        return this.physicsBody;

    }

}
