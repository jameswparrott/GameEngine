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
							
							float m_i = physicsBodies.get(i).getMass();
							
							float m_j = physicsBodies.get(j).getMass();
							
							Vector3D v_i = physicsBodies.get(i).getVelocity();
							
							Vector3D v_j = physicsBodies.get(j).getVelocity();
							
							Vector3D x_i = physicsBodies.get(i).getPos();
							
							Vector3D x_j = physicsBodies.get(j).getPos();
							
							Vector3D dx_i = x_i.sub(x_j);
							
							Vector3D dx_j = x_j.sub(x_i);
							
							float dot_i = (v_i.sub(v_j)).dot(dx_i);
							
							float dot_j = (v_j.sub(v_i)).dot(dx_j);
							
							float len_i = dx_i.lengthSq();
							
							float len_j = dx_j.lengthSq();
							
							float mas_i = (2 * m_j) / (m_i + m_j);
							
							float mas_j = (2 * m_i) / (m_i + m_j);
							
							Vector3D dv_i = v_i.sub(dx_i.getScaled((mas_i * dot_i) / len_i));
							
							Vector3D dv_j = v_j.sub(dx_j.getScaled((mas_j * dot_j) / len_j));
							
							physicsBodies.get(i).setVelocity(dv_i);
							
							physicsBodies.get(i).setVelocity(dv_j);
							
							//physicsBodies.get(i).setVelocity(physicsBodies.get(i).getVelocity().getScaled(-1.0f));
							
							//physicsBodies.get(j).setVelocity(physicsBodies.get(j).getVelocity().getScaled(-1.0f));
							
							// m_i * v_i + m_j * v_j = m_i * u_i + m_j * u_j
							
							// 0.5 * (m_i * (v_i).length * (v_i).length + m_j * (v_j).length * (v_j).length) = 
							// 0.5 * (m_i * (u_i).length * (u_i).length + m_j * (u_j).length * (u_j).length)
							
						} else {
							
							//Inelastic collision
							
							// d/dt(p_i + p_j) = 0
							
							physicsBodies.get(i).setVelocity(physicsBodies.get(i).getVelocity().getScaled(-1.0f));
							
							physicsBodies.get(j).setVelocity(physicsBodies.get(j).getVelocity().getScaled(-1.0f));
							
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
