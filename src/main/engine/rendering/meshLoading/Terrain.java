package main.engine.rendering.meshLoading;

import java.util.ArrayList;

import main.engine.core.Util;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Mesh;
import main.engine.rendering.Vertex;

public class Terrain {

    private Mesh mesh;

    private int mapWidth;
    private int subDivis;

    private float mapSteps;

    private float[][] heightMap;

    public Terrain(int mapWidth) {

        this(mapWidth, 5 * mapWidth);

    }

    // TODO: Generate flat terrain, then add to height map with addPerlin
    public Terrain(int mapWidth, int subDivis) {

        this.mapWidth = mapWidth;
        this.subDivis = subDivis;

        this.mapSteps = (float) mapWidth / (float) subDivis;

        this.heightMap = new float[subDivis][subDivis];

        init();

    }

    private void init() {

        for (int j = 0; j < subDivis; j++) {

            for (int i = 0; i < subDivis; i++) {

                heightMap[i][j] = 0.0f;

            }

        }

    }

    // Add a layer of noise
    public void addPerlin(int scale) {

        int numCells = mapWidth / scale + 1;

        int numNodes = numCells + 1;

        float dw = (scale * numCells - mapWidth) * 0.5f;

        float invScale = 1.0f / (float) scale;

        float[] cellCoords = new float[numCells];

        for (int i = 0; i < numCells; i++) {

            cellCoords[i] = ((i + 1) * scale - dw) / (float) mapSteps;

        }

        Vector2D[][] node = new Vector2D[numNodes][numNodes];

        for (int j = 0; j < numNodes; j++) {

            for (int i = 0; i < numNodes; i++) {

                node[i][j] = new Vector2D((float) (2 * Math.random() - 1), (float) (2 * Math.random() - 1));

                node[i][j].normalize();

            }

        }

        // Offset vectors
        Vector2D o1 = new Vector2D(0, 0);
        Vector2D o2 = new Vector2D(0, 0);
        Vector2D o3 = new Vector2D(0, 0);
        Vector2D o4 = new Vector2D(0, 0);

        // Dot product values
        float d1 = 0;
        float d2 = 0;
        float d3 = 0;
        float d4 = 0;

        // Final height
        float height;

        // Cell x and z values
        int a = 0;
        int b = 0;

        // Offset x and z values
        float x = 0;
        float y = 0;

        for (int j = 0; j < subDivis; j++) {

            for (int k = 0; k < numCells; k++) {

                if (j < cellCoords[k]) {

                    b = k;
                    break;

                }

            }

            for (int i = 0; i < subDivis; i++) {

                for (int k = 0; k < numCells; k++) {

                    if (i < cellCoords[k]) {

                        a = k;
                        break;

                    }

                }

                x = (i * mapSteps + dw) * invScale - a;
                y = (j * mapSteps + dw) * invScale - b;

                o1.set(x, y);
                o2.set((x - 1), y);
                o3.set((x - 1), (y - 1));
                o4.set(x, (y - 1));

                d1 = o1.dot(node[a][b]);
                d2 = o2.dot(node[a + 1][b]);
                d3 = o3.dot(node[a + 1][b + 1]);
                d4 = o4.dot(node[a][b + 1]);

                height = interpCub(interpCub(d1, d2, x), interpCub(d4, d3, x), y);

                heightMap[i][j] = height;

            }

        }

    }

    // Generate final mesh from height map
    public void genMesh() {

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Integer> indices = new ArrayList<Integer>();

        for (int j = 0; j < subDivis; j++) {

            for (int i = 0; i < subDivis; i++) {

                vertices.add(new Vertex(new Vector3D(i * mapSteps, heightMap[i][j], j * mapSteps),
                        new Vector2D(i * mapSteps, j * mapSteps)));

                if (j < subDivis - 1 && i < subDivis - 1) {

                    // Upper triangle
                    indices.add(i + j * subDivis + 1);
                    indices.add(i + j * subDivis);
                    indices.add(i + j * subDivis + subDivis);
                    // Lower triangle
                    indices.add(i + j * subDivis + subDivis);
                    indices.add(i + j * subDivis + subDivis + 1);
                    indices.add(i + j * subDivis + 1);

                }

            }

        }

        Vertex[] vertArray = new Vertex[vertices.size()];

        int[] intArray = new int[indices.size()];

        Integer[] integerArray = new Integer[indices.size()];

        vertices.toArray(vertArray);

        indices.toArray(integerArray);

        intArray = Util.toIntArray(integerArray);

        mesh = new Mesh(vertArray, intArray, true);

    }

    // Linear interpolation
    private static float interpLin(float a, float b, float s) {

        return a + (b - a) * s;

    }

    // Cubic interpolation
    private static float interpCub(float a, float b, float s) {

        return s * s * (a - b) * (2 * s - 3) + a;

    }

    // Quintic interpolation
    private static float interpQui(float a, float b, float s) {

        return s * s * s * (b - a) * (6 * s * s - 15 * s + 10) + a;

    }

    public Mesh getMesh() {

        return this.mesh;

    }

}
