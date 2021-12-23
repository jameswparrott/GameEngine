package main.engine.physics.boundaries;

import java.util.ArrayList;

import main.engine.core.Vector3D;
import main.engine.physics.CollisionData;
import main.engine.physics.IntersectData;
import main.engine.rendering.Mesh;

public class CMB extends Boundary{
	
	private ArrayList<Vector3D> convexBoundary;
	
	//private ArrayList<Vector3D> simplex;
	
	private Vector3D dir;
	
	public CMB(Vector3D pos, Mesh mesh, boolean convex) {
		
		this(pos, mesh.getPositions(), convex);
		
	}
	
	public CMB(Vector3D pos, ArrayList<Vector3D> vertices, boolean convex) {
		
		super(boundaryType.TYPE_CMB, pos);
		
		dir = new Vector3D(1, 1, 1);
		
		if (convex) {
			
			convexBoundary = vertices;
			
		} else {
			
			convexBoundary = quickHull(vertices);
			
		}
		
	}
	
	private static ArrayList<Vector3D> quickHull(ArrayList<Vector3D> vertices) {
		
		ArrayList<Vector3D> verts = vertices;
		
		Vector3D[] baseA = new Vector3D[3];
		
		Vector3D[] baseB = new Vector3D[3];
		
		ArrayList<Vector3D> result = new ArrayList<Vector3D>();

		ArrayList<Vector3D> setA = new ArrayList<Vector3D>();
		
		ArrayList<Vector3D> setB = new ArrayList<Vector3D>();
		
		int index = 0;
		
		//Find the index of the vertex furthest in the (0,1,0) direction
		index = dirMaxInt(verts, new Vector3D( 0, 1, 0));
		
		//Add this vertex to the result (i.e. vertex in convex hull)
		result.add(verts.get(index));
		
		//Add this vertex to the base triangles (i.e. the base for the additional points)
		baseA[0] = verts.get(index);
		
		baseB[2] = verts.get(index);
		
		//Remove vertex from list of possible vertices (no need to keep checking for it)
		verts.remove(index);
		
		index = dirMaxInt(verts, new Vector3D( 1,-1, 1));
		
		result.add(verts.get(index));
		
		baseA[1] = verts.get(index);
		
		baseB[1] = verts.get(index);
		
		verts.remove(index);
		
		index = dirMaxInt(verts, new Vector3D(-1,-1,-1));
		
		result.add(verts.get(index));
		
		baseA[2] = verts.get(index);
		
		baseB[0] = verts.get(index);
		
		verts.remove(index);
		
		//Point on the plane, a vertex of the triangle
		Vector3D p_point = result.get(0);
		
		//Normal of the plane
		Vector3D p_normal = result.get(2).sub(result.get(1)).cross(result.get(0).sub(result.get(1)));
		
		for (int i = 0; i < verts.size(); i ++) {
			
			//Which side of the plane is this vertex on?
			if (p_normal.dot(verts.get(i).sub(p_point)) > 0) {
				
				setA.add(verts.get(i));
				
			} else if (p_normal.dot(verts.get(i).sub(p_point)) < 0) {
				
				setB.add(verts.get(i));
				
			}
			
		}
		
		findHull(result, setA, baseA, p_normal);
		
		findHull(result, setB, baseB, p_normal.getScaled(-1.0f));
		
		return result;
		
	}
	
	private static void findHull(ArrayList<Vector3D> result, ArrayList<Vector3D> subset, Vector3D[] base, Vector3D norm) {
		
		if (subset.isEmpty()) {
			
			return;
			
		}
		
		ArrayList<Vector3D> setA = new ArrayList<Vector3D>();
		
		ArrayList<Vector3D> setB = new ArrayList<Vector3D>();
		
		ArrayList<Vector3D> setC = new ArrayList<Vector3D>();
		
		Vector3D[] a_base = new Vector3D[3];
		
		Vector3D[] b_base = new Vector3D[3];
		
		Vector3D[] c_base = new Vector3D[3];
		
		int index = 0;
		
		index = dirMaxInt(subset, norm);
		
		result.add(subset.get(index));
		
		a_base[0] = subset.get(index);
		
		a_base[1] = base[0];
		
		a_base[2] = base[1];
		
		Vector3D p_normal = a_base[0].sub(a_base[1]).cross(a_base[2].sub(a_base[1]));
		
		for (int i = 0; i < subset.size(); i ++) {
			
			if (p_normal.dot(subset.get(i).sub(a_base[0])) > 0) {
				
				setA.add(subset.get(i));
				
			}
			
		}
		
		findHull(result, setA, a_base, p_normal);
		
		b_base[0] = subset.get(index);
		
		b_base[1] = base[1];
		
		b_base[2] = base[2];
		
		p_normal = b_base[0].sub(b_base[1]).cross(b_base[2].sub(b_base[1]));
		
		for (int i = 0; i < subset.size(); i ++) {
			
			if (p_normal.dot(subset.get(i).sub(b_base[0])) > 0) {
				
				setB.add(subset.get(i));
				
			}
			
		}
		
		findHull(result, setB, b_base, p_normal);
		
		c_base[0] = subset.get(index);
		
		c_base[1] = base[2];
		
		c_base[2] = base[0];
		
		p_normal = c_base[0].sub(c_base[1]).cross(c_base[2].sub(c_base[1]));
		
		for (int i = 0; i < subset.size(); i ++) {
			
			if (p_normal.dot(subset.get(i).sub(c_base[0])) > 0) {
				
				setC.add(subset.get(i));
				
			}
			
		}
		
		findHull(result, setC, c_base, p_normal);
		
		subset.remove(index);
		
		return;
		
	}
	
	/**
	 * @param p The first set of vertices.
	 * @param q The second set of vertices.
	 * @param initial Any random initial direction.
	 * @return True if the convex hulls intersect, false otherwise.
	 */
	public boolean GJK(ArrayList<Vector3D> p, ArrayList<Vector3D> q) {
		
		Vector3D initial = new Vector3D(1, 1, 1);

		Vector3D a = dirMaxVec(p, initial).sub(dirMaxVec(q, initial.getScaled(-1)));
		
		ArrayList<Vector3D> simplex = new ArrayList<Vector3D>();

		simplex.add(a);

		dir = a.getScaled(-1);

		while (true) {

			a = dirMaxVec(p, dir).sub(dirMaxVec(q, dir.getScaled(-1)));

			if (a.dot(dir) < 0) {

				return false;

			}

			simplex.add(a);

			if (calcSimplex(simplex)) {

				return true;

			}

		}

	}
	
	private boolean calcSimplex(ArrayList<Vector3D> simplex) {

		switch (simplex.size()) {

		case 2:

			Vector3D ao = simplex.get(1).getScaled(-1);

			Vector3D ab = simplex.get(0).sub(simplex.get(1));

			dir = ab.cross(ao).cross(ab);

			break;

		case 3:
			
			//U, D, AC, AB, A

			ao = simplex.get(2).getScaled(-1);

			ab = simplex.get(1).sub(simplex.get(2));

			Vector3D ac = simplex.get(0).sub(simplex.get(2));

			Vector3D abc = ac.cross(ab);
			
			if (ac.cross(abc).dot(ao) > 0) {
				
				//AC, A
				 
				if (ac.dot(ao) > 0) {
					
					//AC
					
					dir = ac.cross(ao).cross(ac);
					
					simplex.remove(0);
					
				} else {
					
					//A
					
					dir = ao;
					
					simplex.remove(0);
					
					simplex.remove(0);
					
				}
				
			} else {
				
				//U, D, AB, A
				
				if (abc.cross(ab).dot(ao) > 0) {
					
					//AB, A
					
					if (ab.dot(ao) > 0) {
						
						//AB
						
						dir = ab.cross(ao).cross(ab);
						
						simplex.remove(0);
						
					} else {
						
						//A
						
						dir = ao;
						
						simplex.remove(0);
						
						simplex.remove(0);
						
					}
					
				} else {
					
					//U, D
					
					if (abc.dot(ao) > 0) {
						
						//U
						
						dir = abc;
						
					} else {
						
						//D
						
						dir = abc.scale(-1);
						
					}
					
				}
				
			}

			break;

		case 4:
			
			//8 cases to check: ABCD, ABC, ABD, ACD, AC, AB, AD, A

			ao = simplex.get(3).getScaled(-1);

			ab = simplex.get(2).sub(simplex.get(3));

			ac = simplex.get(1).sub(simplex.get(3));

			Vector3D ad = simplex.get(0).sub(simplex.get(3));

			abc = ac.cross(ab);

			Vector3D acd = ad.cross(ac);

			Vector3D abd = ab.cross(ad);
			
			//Triangle case 1
			if (abc.dot(ao) > 0) {
				
				//ABC, AB, AC, A
				
				if (abc.cross(ab).dot(ao) > 0) {
					
					//AB, A
					
					if (ab.dot(ao) > 0) {
						
						//AB
						
						dir = ab.cross(ao).cross(ab);
						
						simplex.remove(0);
						
						simplex.remove(0);
						
					} else {
						
						//A
						
						dir = ao;
						
						simplex.remove(0);
						
						simplex.remove(0);
						
						simplex.remove(0);
						
					}
					
				} else {
					
					//ABC, AC, A
					
					if (ac.cross(abc).dot(ao) > 0) {
						
						//AC, A
						
						if (ac.dot(ao) > 0) {
							
							//AC
							
							dir = ac.cross(ao).cross(ac);
							
							simplex.remove(2);
							
							simplex.remove(0);
							
						} else {
							
							//A
							
							dir = ao;
							
							simplex.remove(0);
							
							simplex.remove(0);
							
							simplex.remove(0);
							
						}
						
					} else {
						
						//ABC
						
						dir = abc;
						
						simplex.remove(0);
						
					}
					
				}
				
			}
			
			//Triangle case 2
			if (acd.dot(ao) > 0) {
				
				//ACD, AC, AD, A
				
				if (ad.cross(acd).dot(ao) > 0) {
					
					//AD, A
					
					if (ad.dot(ao) > 0) {
						
						//AD
						
						dir = ad.cross(ao).cross(ad);
						
						simplex.remove(1);
						
						simplex.remove(1);
						
					}
					
				} else {
					
					//ADC, AC, A
					
				}
				
			}
			
			//Triangle case 3
			if (abd.dot(ao) > 0) {
				
				//ABD, AB, AD, A
				
			}
			
			//ABCD
			
			return true;

		}

		return false;

	}
	
	private static Vector3D dirMaxVec(ArrayList<Vector3D> r, Vector3D v) {

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
	
	public IntersectData intersect(Boundary boundary) {
		
		switch(boundary.getType()) {
		
		case TYPE_PLANE:
			
			return intersect((Plane) boundary);
			
		case TYPE_SPHERE:
			
			return intersect((Sphere) boundary);
			
		case TYPE_CMB:
			
			return intersect((CMB) boundary);
			
		case TYPE_AABB:
			
			return intersect((AABB) boundary);
			
		default:
			
			System.err.println("CMB attempted to intersect with an undefined boundary");
			
			return new IntersectData(false, 0, 0);
		
		}
		
	}
	
	public IntersectData intersect(CMB cmb) {
		
		float distanceToCenter = getPos().sub(cmb.getPos()).length();
		
		float centerToBoundaryA = getPos().sub(getBoundary().get(dirMaxInt(getBoundary(), cmb.getPos()))).length();
		
		float centerToBoundaryB = cmb.getPos().sub(cmb.getBoundary().get(dirMaxInt(cmb.getBoundary(), getPos()))).length();
		
		float distanceToBoundary = distanceToCenter - centerToBoundaryA - centerToBoundaryB;
		
		return new IntersectData(GJK(this.getOffsetBoundary(), cmb.getOffsetBoundary()), distanceToCenter, distanceToBoundary);
		
	}
	
	public CollisionData collide(Boundary boundary) {
		
		return new CollisionData(new Vector3D(0, 0, 0), new Vector3D(0, 0, 0));
		
	}
	
	public ArrayList<Vector3D> getBoundary() {
		
		return convexBoundary;
		
	}
	
	public ArrayList<Vector3D> getOffsetBoundary() {
		
		ArrayList<Vector3D> newBoundary = new ArrayList<Vector3D>();
		
		for (int i = 0; i < convexBoundary.size(); i ++) {
			
			newBoundary.add(convexBoundary.get(i).add(getPos()));
			
		}
		
		return newBoundary;
		
	}
	
	public void setBoundary(ArrayList<Vector3D> boundary) {
		
		this.convexBoundary = boundary;
		
	}

}
