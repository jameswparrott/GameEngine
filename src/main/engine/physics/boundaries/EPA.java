package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

import java.util.ArrayList;
import java.util.Arrays;

public class EPA {

    public static float distance(ArrayList<Vector3D> simplex, ArrayList<Vector3D> a, ArrayList<Vector3D> b) {

        ArrayList<Face> faces = makeFaces(simplex, makeIndices(simplex));

        int it = 0;

        while (true) {

            it ++;

            Face minFace = getMinFace(faces);

            Vector3D normal = minFace.getNormal();

            Vector3D newPoint = dirMaxVec(a, normal).sub(dirMaxVec(b, normal.getScaled(-1)));

            if (minFace.convergesWith(newPoint, 1e-6f)) {

                return minFace.getDistToOrigin();

            }

            if (!minFace.canSee(newPoint)) {

                System.exit(1);

            }

            expand(newPoint, faces);

        }

    }

    private static ArrayList<Integer> makeIndices(ArrayList<Vector3D> vertices) {

        Vector3D test = vertices.get(0).calcNormal(vertices.get(1), vertices.get(2));

        if (test.dot(vertices.get(0)) > 0) {

            return new ArrayList<>(Arrays.asList(0, 1, 2, 0, 2, 3, 0, 3, 1, 1, 3, 2));

        }

        return new ArrayList<>(Arrays.asList(0, 2, 1, 0, 3, 2, 0, 1, 3, 1, 2, 3));

    }

    private static ArrayList<Face> makeFaces(ArrayList<Vector3D> vertices, ArrayList<Integer> indices){

        ArrayList<Face> result = new ArrayList<>();

        for (int i = 0; i < 4; i ++) {

            result.add(new Face(vertices.get(indices.get(3 * i)), vertices.get(indices.get(3 * i + 1)), vertices.get(indices.get(3 * i + 2))));

        }

        return result;

    }

    private static Face getMinFace(ArrayList<Face> faces) {

        Face result = null;

        float dist = Float.MAX_VALUE;

        for (int i = 0; i < faces.size(); i ++) {

            if (dist > faces.get(i).getDistToOrigin()) {

                dist = faces.get(i).getDistToOrigin();

                result = faces.get(i);

            }

        }

        return result;

    }

    public static void expand(Vector3D newPoint, ArrayList<Face> faces) {

        //Find the indices of the faces seen by the new point
        ArrayList<Integer> indexSeen = indexSeen(newPoint, faces);

        ArrayList<Face> removedFaces = new ArrayList<>();

        int numFaces = indexSeen.size();

        for (int i = 0; i < numFaces; i++) {

            removedFaces.add(faces.remove((int) indexSeen.get(numFaces - 1 - i)));

        }

        if (numFaces == 1)

            oneFace(newPoint, removedFaces.get(0), faces);

        else {

            manyFaces(newPoint, removedFaces);

        }

    }

    private static void oneFace(Vector3D newPoint, Face face, ArrayList<Face> faces) {

        faces.add(new Face(newPoint, face.getA(), face.getB()));
        faces.add(new Face(newPoint, face.getB(), face.getC()));
        faces.add(new Face(newPoint, face.getC(), face.getA()));

    }

    private static void manyFaces(Vector3D newPoint, ArrayList<Face> faces) {

        ArrayList<Edge> edges = newUniqueEdges(faces);

        for (int i = 0; i < edges.size(); i ++) {

            Face face = new Face(newPoint, edges.get(i).getA(), edges.get(i).getB());

            faces.add(face);

        }

    }

    private static ArrayList<Edge> newUniqueEdges(ArrayList<Face> faces) {

        ArrayList<Edge> edges = new ArrayList<>(3 * faces.size());

        Face a, b;

        for (int i = 0; i < faces.size(); i ++) {

            a = faces.get(i);

            boolean x = true, y = true, z = true;

            for (int j = i + 1; j < faces.size(); j ++) {

                b = faces.get(j);

                if (x && b.sharesEdge(a.getE()))
                    x = false;
                if (y && b.sharesEdge(a.getF()))
                    y = false;
                if (z && b.sharesEdge(a.getG()))
                    z = false;

            }

            if (i == faces.size() - 1) {

                for (int j = 0; j < i; j ++) {

                    b = faces.get(j);

                    if (x && b.sharesEdge(a.getE()))
                        x = false;
                    if (y && b.sharesEdge(a.getF()))
                        y = false;
                    if (z && b.sharesEdge(a.getG()))
                        z = false;

                }

            }

            if (x)
                edges.add(a.getE());
            if (y)
                edges.add(a.getF());
            if (z)
                edges.add(a.getG());

        }

        return edges;

    }


    private static ArrayList<Integer> indexSeen(Vector3D point, ArrayList<Face> faces){

        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < faces.size(); i ++) {

            if(faces.get(i).canSee(point)) {

                result.add(i);

            }

        }

        return result;

    }

    private static Vector3D dirMaxVec(ArrayList<Vector3D> r, Vector3D v) {

        return r.get(dirMaxInt(r, v));

    }

    private static int dirMaxInt(ArrayList<Vector3D> r, Vector3D v) {

        float result = r.get(0).dot(v);

        float oldResult = r.get(0).dot(v);

        int answer = 0;

        for (int i = 1; i < r.size(); i++) {

            result = Math.max(result, r.get(i).dot(v));

            if (result > oldResult) {

                answer = i;

                oldResult = result;

            }

        }

        return answer;

    }

}
