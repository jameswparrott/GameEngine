package main.engine.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

public class Input {

    private static boolean hideCursor = false;

    private static boolean windowUpdate = false;

    private static int width, height;

    private static short NUM_KEYS = 348;

    private static byte NUM_BUTTONS = 5;

    private static Boolean[] keys = new Boolean[NUM_KEYS];

    private static Boolean[] buttons = new Boolean[NUM_BUTTONS];

    private static Boolean[] lastKeys = new Boolean[NUM_KEYS];

    private static Boolean[] lastButtons = new Boolean[NUM_BUTTONS];

    public static void update() {

        for (short i = 0; i < NUM_KEYS; i++) {

            lastKeys[i] = false;

            lastKeys[i] = keys[i];

            if (i < NUM_BUTTONS) {

                lastButtons[i] = false;

                lastButtons[i] = buttons[i];

            }

        }

    }

    // TODO: Sticky key support with glfwSetInputMode

    public static boolean getKey(int keyCode) {

        return keys[keyCode];

    }

    public static boolean getButton(int buttonCode) {

        return buttons[buttonCode];

    }

    public static void keyInput() {

        glfwSetKeyCallback(CoreEngine.getWindow(), (window, key, scancode, action, mods) -> {

            if (action == GLFW_PRESS) {

                keys[key] = true;

            }

            if (action == GLFW_RELEASE) {

                keys[key] = false;

            }

        });

        for (int i = 0; i < NUM_KEYS; i++) {

            lastKeys[i] = false;

            keys[i] = false;

        }

    }

    public static void mouseButtonInput() {

        glfwSetMouseButtonCallback(CoreEngine.getWindow(), (window, button, action, mods) -> {

            if (action == GLFW_PRESS) {

                buttons[button] = true;

            }

            if (action == GLFW_RELEASE) {

                buttons[button] = false;

            }

        });

        for (int i = 0; i < NUM_BUTTONS; i++) {

            lastButtons[i] = false;

            buttons[i] = false;

        }

    }

    public static void windowSizeInput() {

        glfwSetWindowSizeCallback(CoreEngine.getWindow(), (window, w, h) -> {

            if (CoreEngine.getWidth() != w || CoreEngine.getHeight() != h) {

                width = w;

                height = h;

                windowUpdate = true;

            }

        });

    }

    public static boolean getKeyPressed(int keyCode) {

        return keys[keyCode] && !lastKeys[keyCode];

    }

    public static boolean getKeyHeld(int keyCode) {

        return lastKeys[keyCode];

    }

    public static boolean getKeyReleased(int keyCode) {

        return !keys[keyCode] && lastKeys[keyCode];

    }

    public static boolean getButtonPressed(int buttonCode) {

        return buttons[buttonCode] && !lastButtons[buttonCode];

    }

    public static boolean getButtonHeld(int buttonCode) {

        return lastButtons[buttonCode];

    }

    public static boolean getButtonReleased(int buttonCode) {

        return !buttons[buttonCode] && lastButtons[buttonCode];

    }

    public static Vector2D getWindowSize() {

        return new Vector2D(width, height);

    }

    public static boolean shouldWindowUpdate() {

        if (windowUpdate) {

            windowUpdate = false;

            return true;

        }

        return windowUpdate;

    }

    public static Vector2D getCursorPos() {

        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(CoreEngine.getWindow(), x, y);

        x.rewind();
        y.rewind();

        return new Vector2D((float) x.get(), (float) y.get());

    }

    public static void setCursorPos(Vector2D pos) {

        glfwSetCursorPos(CoreEngine.getWindow(), pos.getX(), pos.getY());

    }

    // TODO: Clean up this cursor mess(ONE method for disabling the cursor, and ONE
    // method for enabling the cursor)

    public static void disableCursor(boolean enabled) {

        if (enabled) {

            glfwSetInputMode(CoreEngine.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

            hideCursor = true;

        }

        else {

            glfwSetInputMode(CoreEngine.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);

            setCursorPos(new Vector2D(CoreEngine.getWidth() / 2, CoreEngine.getHeight() / 2));

        }

    }

    public static void toggleCursor() {

        if (!hideCursor) {

            glfwSetInputMode(CoreEngine.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

            hideCursor = true;

        }

        else {

            glfwSetInputMode(CoreEngine.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);

            hideCursor = false;

        }

        System.out.println("Toggled cursor");

    }

    public static Vector2D getDelta(Vector2D center) {

        Vector2D delta = new Vector2D(getCursorPos().getX(), getCursorPos().getY()).sub(center);

        return delta;

    }

}