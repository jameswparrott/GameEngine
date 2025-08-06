package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

import java.util.ArrayList;

public class DirectionalSupport {

    public static Vector3D dirMaxVec(ArrayList<Vector3D> r, Vector3D v) {

        return r.get(dirMaxInt(r, v));

    }

    private static int dirMaxInt(ArrayList<Vector3D> r, Vector3D v) {

        int start = (int) (Math.random() * r.size());

        int bestIndex = start;

        float maxDot = r.get(bestIndex).dot(v);

        for (int i = 1; i <= r.size(); i++) {

            int idx = (start + i) % r.size();

            float dot = r.get(idx).dot(v);

            if (dot > maxDot) {

                maxDot = dot;

                bestIndex = idx;

            }

        }

        return bestIndex;

    }

}
