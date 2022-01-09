package main.engine.physics;

import java.util.ArrayList;
import java.util.List;

import main.engine.core.Vector3D;

public class PhysicsEngine {
	
	private List<PhysicsBody> physicsBodies;
	
	//private List<IntersectData> physicsData;
	
	public PhysicsEngine() {
		
		physicsBodies = new ArrayList<PhysicsBody>();
		
		//physicsData = new ArrayList<IntersectData>();
		
	}
	
	public void add(PhysicsBody physicsBody) {
		
		physicsBodies.add(physicsBody);
		
	}
	
	public void simulate(float delta) {
		
		for (int i = 0; i < physicsBodies.size(); i ++) {
			
			physicsBodies.get(i).integrate(delta);
			
		}
		
	}
	
	public void handleCollisions() {
		
		for (int i = 0; i < physicsBodies.size(); i ++) {
			
			for (int j = i + 1; j < physicsBodies.size(); j ++) {
				
				IntersectData intersectData = physicsBodies.get(i).getBoundary().intersect(physicsBodies.get(j).getBoundary());
				
				if (!physicsBodies.get(i).getMaterial().getPermeable() && !physicsBodies.get(j).getMaterial().getPermeable()) {
					
					if (intersectData.getIntersect()) {
						
						if (physicsBodies.get(i).getMaterial().getElastic() && physicsBodies.get(j).getMaterial().getElastic()) {
							
							//Elastic collision
							
							// d/dt(p_i + p_j) = 0
							
							// d/dt(KE_i + KE_j) = 0
							
							System.out.println("Elastic collision.");
							
							Vector3D dx_i = physicsBodies.get(i).getPos().sub(physicsBodies.get(j).getPos());
							
							Vector3D dx_j = physicsBodies.get(j).getPos().sub(physicsBodies.get(i).getPos());
							
							Vector3D v_i = physicsBodies.get(i).getVelocity();
							
							Vector3D v_j = physicsBodies.get(j).getVelocity();
							
							Vector3D dv_i = v_i.sub(v_j);
							
							Vector3D dv_j = v_j.sub(v_i);
							
							float m_i = (-2.0f * physicsBodies.get(j).getMass()) / (physicsBodies.get(i).getMass() + physicsBodies.get(j).getMass());
							
							float m_j = (-2.0f * physicsBodies.get(i).getMass()) / (physicsBodies.get(i).getMass() + physicsBodies.get(j).getMass());
							
							float dot_i = dv_i.dot(dx_i);
							
							float dot_j = dv_j.dot(dx_j);
							
							float len_sq = dx_i.lengthSq();
							
							float s_i = (m_i * dot_i) / len_sq;
							
							float s_j = (m_j * dot_j) / len_sq;
							
							Vector3D u_i = v_i.add(dx_i.getScaled(s_i));
							
							Vector3D u_j = v_j.add(dx_j.getScaled(s_j));
							
							physicsBodies.get(i).setVelocity(u_i);
							
							physicsBodies.get(j).setVelocity(u_j);
							
						} else {
							
							//Inelastic collision
							
							// d/dt(p_i + p_j) = 0
							
							System.out.println("Inelastic collision.");
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public PhysicsBody getBody(int index) {
		
		return physicsBodies.get(index);
		
	}
	
	public int getNumBodies() {
		
		return physicsBodies.size();
		
	}

}
