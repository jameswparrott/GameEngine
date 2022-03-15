package main.game;

import main.engine.components.MeshRenderer;
import main.engine.components.Source;
import main.engine.core.GameObject;
import main.engine.core.Quaternion;
import main.engine.core.Vector3D;
import main.engine.rendering.Material;
import main.engine.rendering.Mesh;
import main.engine.rendering.Texture;
import main.engine.rendering.meshLoading.PrimitiveModel2D;

public class Enemy extends GameObject {

    private static Mesh mesh;

    private static Material material;

    private float[] texCoords = new float[4];

    private GameObject target;

    private Source source;

    private static final float moveSpeed = 1.0f;

    public static enum State {

        idle,

        chase,

        attack,

        flee,

        dying,

        dead,

    };

    private State state;

    public Enemy(Vector3D pos, GameObject target) {

        if (mesh == null) {

            texCoords[0] = 0;

            texCoords[1] = 0.125f;

            texCoords[2] = 6f / 7f;

            texCoords[3] = 1;

            PrimitiveModel2D primitive = new PrimitiveModel2D(0.5f, 0.7f, texCoords);

            mesh = primitive.getMesh();

        }

        if (material == null) {

            material = new Material();

            material.addTexture("diffuse", new Texture("SS-sprite.png"));

            material.addFloat("specularIntensity", 1);

            material.addFloat("specularExponent", 8);

            material.addTexture("nMap", new Texture("default-normalMap.png"));

        }

        MeshRenderer renderer = new MeshRenderer(mesh, material);

        addComponent(renderer);

        state = State.chase;

        this.target = target;

        getTransform().setPos(pos);

        source = new Source("bounce.ogg", pos);

        addComponent(source);

    }

    private void idleUpdate() {

    }

    private void chaseUpdate(Vector3D direction, float delta) {

        // getTransform().getPos().add(direction.getNorm().scale(moveSpeed * delta));

        if (direction.length() > 1) {

            direction.normalize().scale(-1).setY(0);

            Vector3D oldPos = getTransform().getPos();

            Vector3D newPos = getTransform().getPos().add(direction.getScaled(moveSpeed * delta));

            Vector3D collision = TestGame.getLevel().collisionCheck(oldPos, newPos, 0.15f, 0.15f);

            Vector3D movement = direction.scale(moveSpeed * delta);

            movement = movement.mul(collision);

            // System.out.println(collision.toString());

            if (movement.sub(direction).length() != 0) {

                TestGame.getLevel().openDoors(oldPos);

            }

            getTransform().setPos(getTransform().getPos().add(movement));

        }

    }

    private void attackUpdate() {

    }

    private void fleeUpdate() {

    }

    private void dyingUpdate() {

    }

    private void facing(Vector3D direction) {

        float angle = (float) Math.atan(-direction.getZ() / direction.getX()) + (float) Math.PI / 2;

        if (direction.getX() < 0) {

            angle += (float) Math.PI;

        }

        getTransform().setRot(new Quaternion(new Vector3D(0, 1, 0), angle));

    }

    @Override
    public void update(float delta) {

        Vector3D direction = getTransform().getPos().sub(target.getTransform().getPos());

        // Vector3D direction =
        // target.getTransform().getPos().sub(getTransform().getPos());

        facing(direction);

        switch (state) {

        case idle:

            idleUpdate();

            break;

        case chase:

            chaseUpdate(direction, delta);

            break;

        case attack:

            attackUpdate();

            break;

        case flee:

            fleeUpdate();

            break;

        case dying:

            dyingUpdate();

            break;

        case dead:

        default:

            break;

        }

        source.update(delta);

    }

    public void animationFrame(Vector3D dir, int i) {

        // TODO: Based on direction and looping integer value update texCoords

    }

    public void setTarget(GameObject target) {

        this.target = target;

    }

}
