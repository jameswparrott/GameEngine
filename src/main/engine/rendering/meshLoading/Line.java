package main.engine.rendering.meshLoading;

import main.engine.core.Vector3D;

public class Line {
	
	private Vector3D pos;
	
	private Vector3D dir;
	
	public Line(Vector3D pos, Vector3D dir) {
		
		this.pos = pos;
		
		this.dir = dir;
		
	}
	
	public boolean inter(Vector3D point) {
		
		return dir.getY() * (point.getX() - pos.getX()) == dir.getX() * (point.getY() - pos.getY()) &&
			   dir.getZ() * (point.getY() - pos.getY()) == dir.getY() * (point.getZ() - pos.getZ());
		
	}
	
	public boolean parallel(Line line) {
		
		return getDir().cross(line.getDir()).lengthSq() == 0;
		
	}
	
	public boolean inter(Line line) {
		
		return (!parallel(line)) && (getDir().cross(line.getDir()).dot(line.getPos().sub(getPos())) == 0);
		
	}
	
	public Vector3D getInter(Line line) {
		
		float a = getDir().getY() * line.getDir().getX() - getDir().getX() * line.getDir().getY();
		float b = getDir().getZ() * line.getDir().getY() - getDir().getY() * line.getDir().getZ();
		float c = getDir().getX() * line.getDir().getZ() - getDir().getZ() * line.getDir().getX();
		
		return new Vector3D((getDir().getY() * line.getDir().getX() * getPos().getX() - 
							getDir().getX() * line.getDir().getY() * line.getPos().getX() +
							getDir().getX() * line.getDir().getX() * (line.getPos().getY() - getPos().getY())) / a,
							
							(getDir().getZ() * line.getDir().getY() * getPos().getY() - 
							getDir().getY() * line.getDir().getZ() * line.getPos().getY() +
							getDir().getY() * line.getDir().getY() * (line.getPos().getZ() - getPos().getZ())) / b,
							
							(getDir().getX() * line.getDir().getZ() * getPos().getZ() - 
							getDir().getZ() * line.getDir().getX() * line.getPos().getZ() +
							getDir().getZ() * line.getDir().getZ() * (line.getPos().getX() - getPos().getX())) / c
							);
		
	}
	
	public Vector3D getPos() {
		
		return this.pos;
		
	}
	
	public Vector3D getDir() {
		
		return this.dir;
		
	}

}
