package main.engine.physics;

import java.util.ArrayList;

import main.engine.core.Vector3D;

public class Collision {
	
	private static ArrayList<Vector3D> simplex;

	private static Vector3D dir;
	
	public static boolean collisionGJK(ArrayList<Vector3D> p, ArrayList<Vector3D> q) {
		
		return collisionGJK(p, q, new Vector3D(1, 1, 1));
		
	}
	
	public static boolean collisionGJK(ArrayList<Vector3D> p, ArrayList<Vector3D> q, Vector3D initial) {

		Vector3D a = directionalMax(p, initial).sub(directionalMax(q, initial.getScaled(-1)));
		
		simplex = new ArrayList<Vector3D>();

		simplex.add(a);

		dir = a.getScaled(-1);

		while (true) {

			a = directionalMax(p, dir).sub(directionalMax(q, dir.getScaled(-1)));

			if (a.dot(dir) < 0) {

				return false;

			}

			simplex.add(a);

			if (calcSimplex()) {

				return true;

			}

		}

	}
	
	private static boolean calcSimplex() {

		switch (simplex.size()) {

		case 2:

			Vector3D ao = simplex.get(1).getScaled(-1);

			Vector3D ab = simplex.get(0).sub(simplex.get(1));

			dir = ab.cross(ao).cross(ab);

			break;

		case 3:

			ao = simplex.get(2).getScaled(-1);

			ab = simplex.get(1).sub(simplex.get(2));

			Vector3D ac = simplex.get(0).sub(simplex.get(2));

			Vector3D abc = ac.cross(ab);
			
			if(ac.cross(abc).dot(ao) > 0) {
				
				dir = ac.cross(ao).cross(ac);
				
				simplex.remove(1);
				
			}else if(abc.cross(ab).dot(ao) > 0) {
				
				dir = ab.cross(ao).cross(ab);
				
				simplex.remove(0);
				
			} else {
				
				if(abc.dot(ao) > 0) {
					
					dir = abc;
					
				}else {
					
					dir = abc.getScaled(-1);
					
					simplex.add(simplex.get(1));

					simplex.add(simplex.get(0));

					simplex.add(simplex.get(2));

					simplex.remove(0);

					simplex.remove(0);

					simplex.remove(0);
					
				}
				
			}

			break;

		case 4:

			ao = simplex.get(3).getScaled(-1);

			ab = simplex.get(2).sub(simplex.get(3));

			ac = simplex.get(1).sub(simplex.get(3));

			Vector3D ad = simplex.get(0).sub(simplex.get(3));

			abc = ac.cross(ab);

			Vector3D acd = ad.cross(ac);

			Vector3D abd = ab.cross(ad);

			if (abc.dot(ao) > 0) {

				if (acd.dot(ao) > 0) {

					dir = ac.cross(ao).cross(ac);

					simplex.remove(2);

					simplex.remove(0);

				} else if (abd.dot(ao) > 0) {

					dir = ab.cross(ao).cross(ab);

					simplex.remove(1);

					simplex.remove(0);

				} else {

					dir = abc;

					simplex.remove(0);

				}

			} else {

				if (acd.dot(ao) > 0) {

					if (abd.dot(ao) > 0) {

						dir = ad.cross(ao).cross(ad);

						simplex.remove(2);

						simplex.remove(1);

					} else {

						dir = acd;

						simplex.remove(2);

					}

				} else {

					if (abd.dot(ao) > 0) {

						dir = abd;

						simplex.remove(1);

					} else {

						return true;

					}

				}

			}

			break;

		}

		return false;

	}

	private static Vector3D directionalMax(ArrayList<Vector3D> r, Vector3D v) {

		float result = r.get(0).dot(v);

		float oldResult = r.get(0).dot(v);

		Vector3D answer = r.get(0);

		for (int i = 1; i < r.size(); i++) {

			result = Math.max(result, r.get(i).dot(v));

			if (result > oldResult) {

				answer = r.get(i);

				oldResult = result;

			}

		}

		return answer;

	}
	
}
