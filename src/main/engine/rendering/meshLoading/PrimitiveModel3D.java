package main.engine.rendering.meshLoading;

import java.util.ArrayList;

import main.engine.core.Util;
import main.engine.core.Vector2D;
import main.engine.core.Vector3D;
import main.engine.rendering.Mesh;
import main.engine.rendering.Vertex;

public class PrimitiveModel3D {

    public enum Primitive3D {

        tetrahedron,

        pyramid,

        box,

        cylinder,

        sphere

    };

    private Mesh mesh;

    private ArrayList<Vertex> vertices;

    private ArrayList<Integer> indices;

    public PrimitiveModel3D(Primitive3D primitive, float width, float height, float depth, float[] texCoords) {

        // TODO: Pass in texture coordinates

        super();

        vertices = new ArrayList<Vertex>();

        indices = new ArrayList<Integer>();

        mesh = generate3D(primitive, width, height, depth, texCoords);

    }

    private Mesh generate3D(Primitive3D primitive, float width, float height, float depth, float[] texCoords) {

        switch (primitive) {

        case tetrahedron:

            return generateTetra(width, texCoords);

        case pyramid:

            return generatePyr(width, height, depth, texCoords);

        case box:

            return generateBox(width, height, depth, texCoords);

        case cylinder:

        case sphere:

        default:

            System.err.println("Invalid 3D primitive type: " + primitive);

            return null;

        }

    }

    private Mesh generateTetra(float width, float[] texCoords) {

        // front face

        float sin60 = (float) Math.sqrt(3) / 2;

        vertices.add(new Vertex(new Vector3D(0, width / 2 * sin60 / 4, 0), new Vector2D(1, 1)));

        vertices.add(new Vertex(new Vector3D(0, -width / 2 * sin60 / 4, width / 2), new Vector2D(0, 0)));

        vertices.add(new Vertex(new Vector3D(width / 2 * sin60 / 2, -width / 2 * sin60 / 4, -width / 2 / 4),
                new Vector2D(0, 1)));

        vertices.add(new Vertex(new Vector3D(-width / 2 * sin60 / 2, -width / 2 * sin60 / 4, -width / 2 / 4),
                new Vector2D(1, 0)));

        for (int i = 0; i < 2; i++) {

            indices.add(0);

            indices.add(i + 1);

            indices.add(i + 2);

        }

        indices.add(0);

        indices.add(3);

        indices.add(1);

        indices.add(3);

        indices.add(2);

        indices.add(1);

        return new Mesh(getVertexArray(vertices), getIntArray(indices));

    }

    private Mesh generatePyr(float width, float height, float depth, float[] texCoords) {

        // front face

        vertices.add(new Vertex(new Vector3D(0, height / 2, 0), new Vector2D(0.5f, 1f)));

        vertices.add(new Vertex(new Vector3D(0, 0, 0), new Vector2D(0, 0)));

        vertices.add(new Vertex(new Vector3D(width / 2, 0, 0), new Vector2D(1f, 0)));

        vertices.add(new Vertex(new Vector3D(width / 2, 0, depth / 2), new Vector2D(0, 0)));

        vertices.add(new Vertex(new Vector3D(0, 0, depth / 2), new Vector2D(1f, 0)));

        for (int i = 0; i < 3; i++) {

            indices.add(0);

            indices.add(i + 2);

            indices.add(i + 1);

        }

        indices.add(0);

        indices.add(1);

        indices.add(4);

        indices.add(1);

        indices.add(2);

        indices.add(3);

        indices.add(1);

        indices.add(3);

        indices.add(4);

        return new Mesh(getVertexArray(vertices), getIntArray(indices));

    }

    private Mesh generateBox(float width, float height, float depth, float[] texCoords) {

        for (int i = 0; i < 24; i += 4) {

            indices.add(i);

            indices.add(i + 1);

            indices.add(i + 3);

            indices.add(i + 3);

            indices.add(i + 1);

            indices.add(i + 2);

        }

        // Front face

        vertices.add(new Vertex(new Vector3D(-width / 2, -height / 2, -depth / 2),
                new Vector2D(texCoords[0], texCoords[2])));

        vertices.add(
                new Vertex(new Vector3D(-width / 2, height / 2, -depth / 2), new Vector2D(texCoords[0], texCoords[3])));

        vertices.add(
                new Vertex(new Vector3D(width / 2, height / 2, -depth / 2), new Vector2D(texCoords[1], texCoords[3])));

        vertices.add(
                new Vertex(new Vector3D(width / 2, -height / 2, -depth / 2), new Vector2D(texCoords[1], texCoords[2])));

        // Right side

        vertices.add(
                new Vertex(new Vector3D(width / 2, -height / 2, -depth / 2), new Vector2D(texCoords[4], texCoords[6])));

        vertices.add(
                new Vertex(new Vector3D(width / 2, height / 2, -depth / 2), new Vector2D(texCoords[4], texCoords[7])));

        vertices.add(
                new Vertex(new Vector3D(width / 2, height / 2, depth / 2), new Vector2D(texCoords[5], texCoords[7])));

        vertices.add(
                new Vertex(new Vector3D(width / 2, -height / 2, depth / 2), new Vector2D(texCoords[5], texCoords[6])));

        // Back face

        vertices.add(
                new Vertex(new Vector3D(width / 2, -height / 2, depth / 2), new Vector2D(texCoords[8], texCoords[10])));

        vertices.add(
                new Vertex(new Vector3D(width / 2, height / 2, depth / 2), new Vector2D(texCoords[8], texCoords[11])));

        vertices.add(
                new Vertex(new Vector3D(-width / 2, height / 2, depth / 2), new Vector2D(texCoords[9], texCoords[11])));

        vertices.add(new Vertex(new Vector3D(-width / 2, -height / 2, depth / 2),
                new Vector2D(texCoords[9], texCoords[10])));

        // Left face

        vertices.add(new Vertex(new Vector3D(-width / 2, -height / 2, depth / 2),
                new Vector2D(texCoords[12], texCoords[14])));

        vertices.add(new Vertex(new Vector3D(-width / 2, height / 2, depth / 2),
                new Vector2D(texCoords[12], texCoords[15])));

        vertices.add(new Vertex(new Vector3D(-width / 2, height / 2, -depth / 2),
                new Vector2D(texCoords[13], texCoords[15])));

        vertices.add(new Vertex(new Vector3D(-width / 2, -height / 2, -depth / 2),
                new Vector2D(texCoords[13], texCoords[14])));

        // Top face

        vertices.add(new Vertex(new Vector3D(-width / 2, height / 2, -depth / 2),
                new Vector2D(texCoords[16], texCoords[18])));

        vertices.add(new Vertex(new Vector3D(-width / 2, height / 2, depth / 2),
                new Vector2D(texCoords[16], texCoords[19])));

        vertices.add(
                new Vertex(new Vector3D(width / 2, height / 2, depth / 2), new Vector2D(texCoords[17], texCoords[19])));

        vertices.add(new Vertex(new Vector3D(width / 2, height / 2, -depth / 2),
                new Vector2D(texCoords[17], texCoords[18])));

        // Bottom text

        vertices.add(new Vertex(new Vector3D(width / 2, -height / 2, -depth / 2),
                new Vector2D(texCoords[20], texCoords[22])));

        vertices.add(new Vertex(new Vector3D(width / 2, -height / 2, depth / 2),
                new Vector2D(texCoords[20], texCoords[23])));

        vertices.add(new Vertex(new Vector3D(-width / 2, -height / 2, depth / 2),
                new Vector2D(texCoords[21], texCoords[23])));

        vertices.add(new Vertex(new Vector3D(-width / 2, -height / 2, -depth / 2),
                new Vector2D(texCoords[21], texCoords[22])));

        return new Mesh(getVertexArray(vertices), getIntArray(indices));

    }

    private Vertex[] getVertexArray(ArrayList<Vertex> vertices) {

        Vertex[] result = new Vertex[vertices.size()];

        vertices.toArray(result);

        return result;

    }

    private int[] getIntArray(ArrayList<Integer> indices) {

        Integer[] integerArray = new Integer[indices.size()];

        int[] result = new int[indices.size()];

        indices.toArray(integerArray);

        result = Util.toIntArray(integerArray);

        return result;

    }

    public Mesh getMesh() {

        return mesh;

    }

}
