package main.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import java.util.ArrayList;

import main.engine.components.MeshRenderer;
import main.engine.core.GameObject;
import main.engine.core.Input;
import main.engine.core.Quaternion;
import main.engine.core.Util;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Material;
import main.engine.rendering.Mesh;
import main.engine.rendering.Texture;
import main.engine.rendering.Vertex;
import main.engine.rendering.meshLoading.PrimitiveModel2D;
import main.engine.rendering.meshLoading.PrimitiveModel2D.Primitive2D;

public class Level extends GameObject {

    private int NUM_ROW;

    private int NUM_DIV;

    private static final int RED = 0xFF0000;

    private static final int GREEN = 0x00FF00;

    private static final int BLUE = 0x0000FF;

    private Bitmap level;

    private GameObject map;

    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

    private ArrayList<Integer> indices = new ArrayList<Integer>();

    private ArrayList<Door> doors = new ArrayList<Door>();

    private Player player;

    public Level(String mapFile, String texFile, int texPerRow, Player player) {

        NUM_ROW = texPerRow;

        NUM_DIV = 256 / (NUM_ROW * NUM_ROW);

        generateLevel(mapFile, texFile);

        this.player = player;

    }

    private void generateLevel(String mapFile, String texFile) {

        level = new Bitmap(mapFile).flipY();

        float[] texCoords = new float[4];

        Door door;

        Material material = new Material(new Texture(texFile), 1, 8);

//		material.addTexture("diffuse", new Texture(texFile));
//		
//		material.addFloat("specularIntensity", 1);
//		
//		material.addFloat("specularExponent", 8);
//		
//		material.addTexture("nMap", new Texture("default-normalMap.png"));

        PrimitiveModel2D wall;

        Mesh wallMesh;

        MeshRenderer wallRenderer;

        GameObject wallObject;

        for (int i = 0; i < level.getWidth(); i++) {

            for (int j = 0; j < level.getHeight(); j++) {

                if ((level.getPixel(i, j) & 0xFFFFFF) == 0) {

                    continue;

                }

                texCoords = texChannel(i, j, RED);

                // Floor generation

                addIndices(false);

                addVertices(i, j, true, false, true, 0, texCoords);

                // Ceiling generation

                addIndices(true);

                addVertices(i, j, true, false, true, 1, texCoords);

                // Wall generation

                texCoords = texChannel(i, j, GREEN);

                if ((level.getPixel(i, j - 1) & 0xFFFFFF) == 0) {

                    addIndices(true);

                    addVertices(i, j, true, true, false, 0, texCoords);

                    // Horribly inefficient

//					wall = new PrimitiveModel2D(Primitive2D.rectangle, 1, 1, texCoords);
//					
//					wallMesh = wall.getMesh();
//					
//					wallRenderer = new MeshRenderer(wallMesh, material);
//					
//					wallObject = new GameObject();
//					
//					wallObject.addComponent(wallRenderer);
//					
//					wallObject.getTransform().setPos(i + 0.5f, 0.5f, j);
//					
//					wallObject.getTransform().setRot(new Quaternion(new Vector3D(0, 1, 0), (float) - Math.toRadians(180)));
//					
//					addChild(wallObject);

                }

                if ((level.getPixel(i, j + 1) & 0xFFFFFF) == 0) {

                    addIndices(false);

                    addVertices(i, j, true, true, false, 1, texCoords);

                }

                if ((level.getPixel(i - 1, j) & 0xFFFFFF) == 0) {

                    addIndices(false);

                    addVertices(i, j, false, true, true, 0, texCoords);

                }

                if ((level.getPixel(i + 1, j) & 0xFFFFFF) == 0) {

                    addIndices(true);

                    addVertices(i, j, false, true, true, 1, texCoords);

                }

                texCoords = texChannel(i, j, BLUE);

                if ((level.getPixel(i, j) & 0x0000FF) == 16) {

                    boolean x = ((level.getPixel(i, j - 1) & 0xFFFFFF) == 0)
                            && ((level.getPixel(i, j + 1) & 0xFFFFFF) == 0);

                    boolean y = ((level.getPixel(i - 1, j) & 0xFFFFFF) == 0)
                            && ((level.getPixel(i + 1, j) & 0xFFFFFF) == 0);

                    if (x ^ y) {

                        if (x) {

                            door = new Door(material, true);

                            door.getTransform()
                                    .setRot(new Quaternion(new Vector3D(0, 1, 0), (float) Math.toRadians(90.0f)));

                            door.setOpenedPos(new Vector3D(i + 0.5f, 0.5f, j - 0.4f));

                        } else {

                            door = new Door(material, false);

                            door.setOpenedPos(new Vector3D(i + 1.4f, 0.5f, j + 0.5f));

                        }

                        door.getTransform().setPos(i + 0.5f, 0.5f, j + 0.5f);

                        door.setClosedPos(door.getTransform().getPos());

                        doors.add(door);

                    }

                }

            }

        }

        Vertex[] vertexArray = new Vertex[vertices.size()];

        Integer[] indexArray = new Integer[indices.size()];

        vertices.toArray(vertexArray);

        indices.toArray(indexArray);

        Mesh mesh = new Mesh(vertexArray, Util.toIntArray(indexArray), true);

        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

        addComponent(meshRenderer);

        for (Door d : doors) {

            addChild(d);

        }

    }

    private float[] texChannel(int i, int j, int channel) {

        float[] result = new float[4];

        int yTex = 0;

        if (channel == RED) {

            yTex = ((level.getPixel(i, j) & RED) >> 16) / NUM_DIV;

        }

        if (channel == GREEN) {

            yTex = ((level.getPixel(i, j) & GREEN) >> 8) / NUM_DIV;

        }

        if (channel == BLUE) {

            yTex = ((level.getPixel(i, j) & BLUE)) / NUM_DIV;

        }

        int xTex = yTex % NUM_ROW;

        yTex /= NUM_ROW;

        result[0] = (float) xTex / (float) NUM_ROW;

        result[1] = result[0] + 1f / (float) NUM_ROW;

        result[2] = 1f - (float) yTex / (float) NUM_ROW;

        result[3] = result[2] - 1f / (float) NUM_ROW;

        return result;

    }

    private void addIndices(boolean flip) {

        if (flip) {

            indices.add(vertices.size() + 1);

            indices.add(vertices.size() + 2);

            indices.add(vertices.size() + 0);

            indices.add(vertices.size() + 2);

            indices.add(vertices.size() + 3);

            indices.add(vertices.size() + 0);

            return;
        }

        indices.add(vertices.size() + 0);

        indices.add(vertices.size() + 2);

        indices.add(vertices.size() + 1);

        indices.add(vertices.size() + 0);

        indices.add(vertices.size() + 3);

        indices.add(vertices.size() + 2);

    }

    private void addVertices(int i, int j, boolean x, boolean y, boolean z, float offset, float[] texCoords) {

        if (x && z) {

            vertices.add(new Vertex(new Vector3D(i, offset, j), new Vector2D(texCoords[0], texCoords[3])));

            vertices.add(new Vertex(new Vector3D(i + 1, offset, j), new Vector2D(texCoords[1], texCoords[3])));

            vertices.add(new Vertex(new Vector3D(i + 1, offset, j + 1), new Vector2D(texCoords[1], texCoords[2])));

            vertices.add(new Vertex(new Vector3D(i, offset, j + 1), new Vector2D(texCoords[0], texCoords[2])));

            return;

        }

        if (x && y) {

            vertices.add(new Vertex(new Vector3D(i, 0, j + offset), new Vector2D(texCoords[0], texCoords[3])));

            vertices.add(new Vertex(new Vector3D(i + 1, 0, j + offset), new Vector2D(texCoords[1], texCoords[3])));

            vertices.add(new Vertex(new Vector3D(i + 1, 1, j + offset), new Vector2D(texCoords[1], texCoords[2])));

            vertices.add(new Vertex(new Vector3D(i, 1, j + offset), new Vector2D(texCoords[0], texCoords[2])));

            return;

        }

        if (y && z) {

            vertices.add(new Vertex(new Vector3D(i + offset, 0, j), new Vector2D(texCoords[0], texCoords[3])));

            vertices.add(new Vertex(new Vector3D(i + offset, 0, j + 1), new Vector2D(texCoords[1], texCoords[3])));

            vertices.add(new Vertex(new Vector3D(i + offset, 1, j + 1), new Vector2D(texCoords[1], texCoords[2])));

            vertices.add(new Vertex(new Vector3D(i + offset, 1, j), new Vector2D(texCoords[0], texCoords[2])));

            return;

        }

        System.err.println("Invalid faces given.");

    }

    public Vector3D collisionCheck(Vector3D oldPos, Vector3D newPos, float objectWidth, float objectLength) {

        Vector2D collisionVector = new Vector2D(1, 1);

        Vector2D blockSize = new Vector2D(1, 1);

        Vector2D objectSize = new Vector2D(objectWidth, objectLength);

        Vector2D oldPos0 = oldPos.getXZ();

        Vector2D newPos0 = newPos.getXZ();

        for (int i = 0; i < level.getWidth(); i++) {

            for (int j = 0; j < level.getHeight(); j++) {

                if ((level.getPixel(i, j) & 0xFFFFFF) == 0) {

                    collisionVector = collisionVector
                            .mul(rectangleCollide(oldPos0, newPos0, objectSize, new Vector2D(i, j), blockSize));

                }

            }

        }

        for (Door d : doors) {

            Vector2D doorPos = d.getTransform().getPos().getXZ();

            if (d.getRotated()) {

                doorPos = doorPos.sub(new Vector2D(0, 0.5f));

            } else {

                doorPos = doorPos.sub(new Vector2D(0.5f, 0));

            }

            collisionVector = collisionVector
                    .mul(rectangleCollide(oldPos0, newPos0, objectSize, doorPos, d.getOrientation()));

        }

        return new Vector3D(collisionVector.getX(), 0, collisionVector.getY());

    }

    private Vector2D rectangleCollide(Vector2D oldPos, Vector2D newPos, Vector2D size1, Vector2D pos2, Vector2D size2) {

        Vector2D result = new Vector2D(0, 0);

        if (newPos.getX() + size1.getX() < pos2.getX() ||

                newPos.getX() - size1.getX() > pos2.getX() + size2.getX() ||

                oldPos.getY() + size1.getY() < pos2.getY() ||

                oldPos.getY() - size1.getY() > pos2.getY() + size2.getY()) {

            result.setX(1);

        }

        if (oldPos.getX() + size1.getX() < pos2.getX() ||

                oldPos.getX() - size1.getX() > pos2.getX() + size2.getX() ||

                newPos.getY() + size1.getY() < pos2.getY() ||

                newPos.getY() - size1.getY() > pos2.getY() + size2.getY()) {

            result.setY(1);

        }

        return result;

    }

    public void openDoors(Vector3D pos) {

        for (Door door : doors) {

            if ((door.getTransform().getPos().sub(pos)).length() < 1.0f) {

                door.open();

            }

        }

    }

    @Override
    public void input(float delta) {

        if (Input.getKeyPressed(GLFW_KEY_SPACE)) {

            openDoors(player.getTransform().getPos());

        }

    }

    public ArrayList<Door> getDoors() {

        return doors;

    }

    public Bitmap getLevel() {

        return level;

    }

}
