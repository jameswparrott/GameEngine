package main.engine.physics;

import java.util.ArrayList;
import java.util.List;

import main.engine.core.Vector3D;
import main.engine.profiling.Printer;

public class PhysicsEngine {

    private List<PhysicsBody> physicsBodies;

    public PhysicsEngine() {

        physicsBodies = new ArrayList<PhysicsBody>();

    }

    public void add(PhysicsBody physicsBody) {

        physicsBodies.add(physicsBody);

    }

    public void simulate(float delta) {

        for (int i = 0; i < physicsBodies.size(); i++) {

            physicsBodies.get(i).integrate(delta);

        }

    }

    public void handleCollisions(float delta) {

        PhysicsBody a, b;

        for (int i = 0; i < physicsBodies.size(); i++) {

            a = physicsBodies.get(i);

            for (int j = i + 1; j < physicsBodies.size(); j++) {

                b = physicsBodies.get(j);

                IntersectData intersectData = a.getBoundary().intersect(b.getBoundary());

                if (!a.getMaterial().getPermeable() && !b.getMaterial().getPermeable()) {

                    if (intersectData.getIntersect()) {

                        Printer printer = new Printer();

                        printer.say("Collision detected");

                        printer.print("PosA", a.getPos());

                        printer.print("PosB", b.getPos());

                        float del = getTimeOfCollision(a, b, intersectData.getDistanceToBoundary());

                        rollBack(a, b, del);

                        calcCollision(a, b);

                        rollBack(a, b, -del);

                    }

                }

            }

        }

    }

    private static float getTimeOfCollision(PhysicsBody a, PhysicsBody b, float distanceToBoundary) {

        return distanceToBoundary / (a.getVelocity().length() + b.getVelocity().length());

    }

    private static void rollBack(PhysicsBody a, PhysicsBody b, float del) {

        a.setPos(a.getPos().add(a.getVelocity().getScaled(del)));

        b.setPos(b.getPos().add(b.getVelocity().getScaled(del)));

    }

    private static void calcCollision(PhysicsBody a, PhysicsBody b) {

        Vector3D dx_i = a.getPos().sub(b.getPos());

        Vector3D dx_j = b.getPos().sub(a.getPos());

        Vector3D v_i = a.getVelocity();

        Vector3D v_j = b.getVelocity();

        Vector3D dv_i = v_i.sub(v_j);

        Vector3D dv_j = v_j.sub(v_i);

        // Average the coefficients of restitution
        float c_r = 0.5f * (a.getMaterial().getRestitution() + b.getMaterial().getRestitution());

        float m_i = (-2.0f * b.getMass()) / (a.getMass() + b.getMass());

        float m_j = (-2.0f * a.getMass()) / (a.getMass() + b.getMass());

        float dot_i = dv_i.dot(dx_i);

        float dot_j = dv_j.dot(dx_j);

        float len_sq = dx_i.lengthSq();

        float s_i = (m_i * dot_i * c_r) / len_sq;

        float s_j = (m_j * dot_j * c_r) / len_sq;

        Vector3D final_i = v_i.add(dx_i.getScaled(s_i));

        Vector3D final_j = v_j.add(dx_j.getScaled(s_j));

        a.setVelocity(final_i);

        b.setVelocity(final_j);

    }

    public PhysicsBody getBody(int index) {

        return physicsBodies.get(index);

    }

    public int getNumBodies() {

        return physicsBodies.size();

    }

}
