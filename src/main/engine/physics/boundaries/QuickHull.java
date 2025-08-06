package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

import java.util.ArrayList;

public class QuickHull {

    public ArrayList<Vector3D> quickHull(ArrayList<Vector3D> points) {

        if (points.size() < 4)
            return points;

        //find initial simplex;

        return null;

    }

    private ArrayList<Vector3D> initSimplex(ArrayList<Vector3D> points) {

        Vector3D[] directions = new Vector3D[] { Vector3D.UP, Vector3D.DOWN, Vector3D.LEFT, Vector3D.RIGHT, Vector3D.FORWARD, Vector3D.BACKWARD};

        float maxDistSquared = Float.NEGATIVE_INFINITY;

        float distSquared = 0;

        Vector3D[] maxPair = new Vector3D[2];

        Vector3D pointA = null;

        Vector3D pointB = null;

        Vector3D maxPointA = null;

        Vector3D maxPointB = null;

        for (Vector3D dir : directions) {

            pointA = DirectionalSupport.dirMaxVec(points, dir);

            pointB = DirectionalSupport.dirMaxVec(points, dir.getScaled(-1.0f));

            distSquared = maxPair[0].sub(maxPair[1]).lengthSq();

            if (distSquared > maxDistSquared) {

                maxDistSquared = distSquared;

                maxPointA = pointA;

                maxPointB = pointB;

            }

        }

        Vector3D ab = maxPointA.sub(maxPointB);

        float lineLenSquared = ab.lengthSq();

        Vector3D c = null;

        maxDistSquared = Float.NEGATIVE_INFINITY;

        distSquared = 0;

        return null;

    }

}
