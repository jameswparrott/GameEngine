package main.engine.physics.boundaries;

import main.engine.core.Vector3D;

import java.util.ArrayList;

public class GJK {

    /**
     * Determines whether two convex shapes intersect using the Gilbert–Johnson–Keerthi (GJK) algorithm.
     * <p>
     * GJK is a robust method for detecting the intersection of two convex sets. Unlike many other
     * geometric algorithms, it does not require a specific representation of the shapes themselves.
     * Instead, it relies on each shape providing a {@code support} function, which is used to compute
     * the Minkowski difference and iteratively converge toward a simplex that contains the origin.
     * </p>
     *
     * @param p a convex {@code Support} shape
     * @param q another convex {@code Support} shape
     * @return {@code GJKResult} wrapper for GJK data.
     */
    public static GJKResult intersects(Support p, Support q) {

        ArrayList<Vector3D> simplex = new ArrayList<Vector3D>(4);

        Vector3D dir = new Vector3D(1, 1, 1);

        Vector3D a = p.support(dir).sub(q.support(dir.getScaled(-1)));

        simplex.add(a);

        dir = a.getScaled(-1);

        int it = 0;

        while (true) {

            it++;

            a = p.support(dir).sub(q.support(dir.getScaled(-1)));

            if (a.dot(dir) <= 0) {

                return new GJKResult(false, simplex);

            }

            simplex.add(a);

            if (calcSimplex(simplex, dir)) {

                System.out.println("Number of iterations for GJK to converge: " + it);

                return new GJKResult(true, simplex);

            }

            if (it > 20) {

                System.err.println("GJK did not converge, but instead exited after 21 iterations.");

                return new GJKResult(false, simplex);

            }

        }

    }

    private static boolean calcSimplex(ArrayList<Vector3D> simplex, Vector3D dir) {

        switch (simplex.size()) {

            case 2:

                return line(simplex, dir);

            case 3:

                return triangle(simplex, dir);

            case 4:

                return tetrahedron(simplex, dir);

            default:

                System.err.println("Calc Simplex encountered a case outside of 2 to 4");

                return false;

        }

    }

    private static boolean line(ArrayList<Vector3D> simplex, Vector3D dir) {

        Vector3D a = simplex.get(1);

        Vector3D b = simplex.get(0);

        Vector3D ao = a.getScaled(-1.0f);

        Vector3D ab = b.sub(a);

        if (ab.dot(ao) > 0) {

            dir.set(ab.cross(ao).cross(ab));

        } else {

            simplex.remove(0);

            dir.set(ao);

        }

        return false;

    }

    private static boolean triangle(ArrayList<Vector3D> simplex, Vector3D dir) {

        Vector3D a = simplex.get(2);

        Vector3D b = simplex.get(1);

        Vector3D c = simplex.get(0);

        Vector3D ao = a.getScaled(-1.0f);

        Vector3D ab = b.sub(a);

        Vector3D ac = c.sub(a);

        Vector3D abc = ab.cross(ac);

        if (abc.cross(ac).dot(ao) > 0) {

            if (ac.dot(ao) > 0) {

                simplex.remove(1);

                dir.set(ac.cross(ao).cross(ac));

            } else {

                simplex.remove(0);

            }

        } else {

            if (ab.cross(abc).dot(ao) > 0) {

                simplex.remove(0);

            } else {

                if (abc.dot(ao) > 0) {

                    dir.set(abc);

                } else {

                    simplex.clear();

                    simplex.add(b);

                    simplex.add(c);

                    simplex.add(a);

                    dir.set(abc.getScaled(-1.0f));

                }

            }

        }

        return false;

    }

    private static boolean tetrahedron(ArrayList<Vector3D> simplex, Vector3D dir) {

        Vector3D a = simplex.get(3);

        Vector3D b = simplex.get(2);

        Vector3D c = simplex.get(1);

        Vector3D d = simplex.get(0);

        Vector3D ao = a.getScaled(-1.0f);

        Vector3D ab = b.sub(a);

        Vector3D ac = c.sub(a);

        Vector3D ad = d.sub(a);

        Vector3D abc = ab.cross(ac);

        Vector3D acd = ac.cross(ad);

        Vector3D adb = ad.cross(ab);

        if (abc.dot(ao) > 0) {

            simplex.remove(0);

            return triangle(simplex, dir);

        }

        if (acd.dot(ao) > 0) {

            simplex.remove(2);

            return triangle(simplex, dir);

        }

        if (adb.dot(ao) > 0) {

            simplex.remove(1);

            return triangle(simplex, dir);

        }

        return true;

    }

}
