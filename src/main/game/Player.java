package main.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import main.engine.audio.AudioEngine;
import main.engine.components.Camera;
import main.engine.components.Listener;
import main.engine.components.SpotLight;
import main.engine.core.CoreEngine;
import main.engine.core.GameObject;
import main.engine.core.Input;
import main.engine.core.Quaternion;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;

public class Player extends GameObject {

    private Vector3D movement;

    private Vector3D forward;

    Vector2D center = CoreEngine.getCenter();

    float sensitivity;

    float moveAmount;

    private boolean lock = false;

    private GameObject cameraObject;

    private Camera camera;

    private Listener listener;

    public Player(Vector3D pos) {

        camera = new Camera();

//		cameraObject = new GameObject();
//		
//		cameraObject.addComponent(camera);
//		
//		addChild(cameraObject);

        addComponent(camera);

        listener = new Listener(getTransform().getPos());

        addComponent(listener);

        //Source of infinite problems, you're changing the reference to a zero vector
        //getTransform().getPos().set(pos);

        getTransform().setPos(pos);
        
        movement = new Vector3D(0, 0, 0);

        forward = new Vector3D(0, 0, 1);

    }

    public GameObject getCamera() {

        return this.cameraObject;

    }

    @Override
    public void input(float delta) {

        sensitivity = (float) (10 * delta);

        moveAmount = (float) (2 * delta);

        movement.scale(0);

        forward.setX(getTransform().getRot().getForward().getX());

        forward.setZ(getTransform().getRot().getForward().getZ());

        forward.normalize();

        if (Input.getKeyHeld(GLFW_KEY_T)) {

            // test sound

            if (!AudioEngine.getSource("bounce.ogg").isPlaying()) {

                AudioEngine.getSource("bounce.ogg").play();

            }

        }

        if (Input.getKeyHeld(GLFW_KEY_Q)) {

            if (lock) {

                Input.toggleCursor();

                lock = false;

            }

        }

        if (Input.getKeyPressed(GLFW_KEY_ESCAPE)) {

            glfwSetWindowShouldClose(CoreEngine.getWindow(), true);

        }

        if (Input.getKeyHeld(GLFW_KEY_W)) {

            movement = movement.add(forward);
        }

        if (Input.getKeyHeld(GLFW_KEY_S)) {

            movement = movement.sub(forward);

            moveAmount *= 0.5f;

        }

        if (Input.getKeyHeld(GLFW_KEY_A)) {

            movement = movement.add(getTransform().getRot().getLeft());

            moveAmount *= 0.75f;

        }

        if (Input.getKeyHeld(GLFW_KEY_D)) {

            movement = movement.add(getTransform().getRot().getRight());

            moveAmount *= 0.75f;

        }

        if (Input.getKeyHeld(GLFW_KEY_LEFT_SHIFT)) {

            movement = movement.sub(Vector3D.UP);

            moveAmount *= 0.75f;

        }

        if (Input.getKeyHeld(GLFW_KEY_SPACE)) {

            movement = movement.add(Vector3D.UP);

            moveAmount *= 0.75f;

        }

        if (Input.getButtonPressed(GLFW_MOUSE_BUTTON_1) && lock == true) {

            System.out.println("Pew");

        }

        if (Input.getButtonPressed(GLFW_MOUSE_BUTTON_1) && lock == false) {

            lock = true;

            Input.disableCursor(lock);

            Input.setCursorPos(center);

        }

        listener.input(delta);

    }

    @Override
    public void update(float delta) {

        center = CoreEngine.getCenter();

        if (movement.length() > 0) {

            movement.normalize();

            Vector3D oldPos = this.getTransform().getPos();

            Vector3D newPos = oldPos.add(movement.getScaled(moveAmount));

//			Vector3D collision = TestGame.getLevel().collisionCheck(oldPos, newPos, 0.15f, 0.15f);
//			
//			movement = movement.mul(collision);

            move(movement, moveAmount);

        }

        if (lock) {

            Vector2D mouseDelta = Input.getDelta(center);

            float dy = mouseDelta.getY();

            float dx = mouseDelta.getX();

            boolean xrot = dy == 0;

            boolean yrot = dx == 0;

            if (!xrot) {

                getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(dy * sensitivity));

            }

            if (!yrot) {

                getTransform().rotate(Vector3D.UP, (float) Math.toRadians(dx * sensitivity));

            }

            if (!xrot || !yrot) {

                Input.setCursorPos(center);

            }

        }

        listener.update(delta);

    }

    private void move(Vector3D dir, float amount) {

        getTransform().setPos(getTransform().getPos().add(dir.getScaled(amount)));

    }

}
