package Game;

public class Vector {
	private double x, y, z;
	
	public Vector(double x, double y, double z){
		setX(x);
		setY(y);
		setZ(z);
	}
	/**
	 * Vector addition
	 * @param vec
	 */
	public void add(Vector vec){
		this.x+=vec.x;
		this.y+=vec.y;
		this.z+=vec.z;
	}
	
	public void add(double x, double y ,double z){
		this.x+=x;
		this.y+=y;
		this.z+=z;
	}
	public void scale(double factor1, double factor2, double factor3){
		this.x*=factor1;
		this.y*=factor2;
		this.z*=factor3;
	}
	public void scale(double factor){
		this.x*=factor;
		this.y*=factor;
		this.z*=factor;
	}
	
	public void setZero(){
		this.x=0;
		this.y=0;
		this.z=0;
	}
	
	public String toString(){
		return x+", "+y+", "+z;
	}
	/*
	 * Getters and setters
	 */
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
	public void setZ(double z){this.z = z;}
	public double getX(){return this.x;}
	public double getY(){return this.y;}
	public double getZ(){return this.z;}
}
