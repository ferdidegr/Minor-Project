package Utils;
/**
 * Vector class
 * 
 * @author ZL
 *
 */
public class Vector {
	private double x, y, z;
	
	public Vector(double x, double y, double z){
		setX(x);
		setY(y);
		setZ(z);
	}
	
		
	public Vector avg(Vector vec){
		double xnew = (vec.getX() + x)/2;
		double ynew = (vec.getY() + y)/2;
		double znew = (vec.getZ() + z)/2;
		return new Vector(xnew, ynew, znew);
	}
	
	public Vector rotate2D(double rad){
		float X = (float) x;
		float Z = (float) z;
		x = (float)(X *Math.cos(rad) + Z * Math.sin(rad));
		z = (float)(-X* Math.sin(rad) + Z * Math.cos(rad));
		
		x = Math.round(x*1E10)/1E10d;
		z = Math.round(z*1E10)/1E10d;
		
		return this;
	}	
	
	
	/**
	 * Gives the distance between current vector and given vector as a double.
	 * @param vec
	 * @return
	 */
	public double distance(Vector vec){
		Vector minusa = this.clone();
		
		return vec.minus(minusa).length();
	}
	
	/**
	 * Vector addition
	 * @param vec another Vector object
	 */
	public Vector add(Vector vec){
		add(vec.x, vec.y, vec.z);
		return this;
	}
	public Vector minus(Vector vec){
		this.add(vec.scale(-1));
		
		return this;
	}
	/**
	 * Vector addition with splitted cartesian components
	 * @param x	
	 * @param y
	 * @param z
	 */
	public Vector add(double x, double y ,double z){
		this.x+=x;
		this.y+=y;
		this.z+=z;
		
		return this;
	}
	/**
	 * Scale each component independently
	 * @param factor1
	 * @param factor2
	 * @param factor3
	 */
	public Vector scale(double factor1, double factor2, double factor3){
		this.x*=factor1;
		this.y*=factor2;
		this.z*=factor3;
		
		return this;
	}
	
	/**
	 * Scale each component independently by a vector
	 * @param factor1
	 * @param factor2
	 * @param factor3
	 */
	public Vector scale(Vector vec){
		scale(vec.x, vec.y, vec.z);
		
		return this;
	}
	/**
	 * Scale all components with the same factor
	 * @param factor
	 */
	public Vector scale(double factor){
		this.x*=factor;
		this.y*=factor;
		this.z*=factor;
		
		return this;
	}
	
	public double length(){
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public double length2D(){
		return Math.sqrt(x*x+z*z);
	}
	
	public Vector normalize2D(){
		
		double length = length2D();
		if(length!=0){
		x /= length;
		z /= length;
		}
		
		return this;
	}
	
	public Vector normalize(){
		double length = length();
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public Vector normalizePerdir(){
		x = Math.signum(x)*x/x;
		y = Math.signum(y)*y/y;
		z = Math.signum(z)*z/z;
		
		return this;
	}
	
	public Vector step(){
		x = (x>0? 1:0);
		y = (y>0? 1:0);
		z = (z>0? 1:0);
		
		return this;
	}
	public Vector clone(){
		return new Vector(x, y, z);
	}
	
	public boolean equals(Object v2){
		if(v2 instanceof Vector){
			Vector other = (Vector) v2;
			return this.x == other.x && this.y == other.y && this.z==other.z;
		}
		return false;
	}
	
	public Vector crossprod(Vector v2){
		return new Vector(	(this.y*v2.z-this.z*v2.y), 
						   -(this.x*v2.z - this.z*v2.x), 
							(this.x*v2.y - this.y*v2.x));
	}
	/**
	 * Dot product
	 * @param v2
	 * @return
	 */
	public double dotprod(Vector v2){
		return this.x*v2.x+this.y*v2.y+this.z*v2.z;
	}
	
	/**
	 * reset the vector to a zero vector
	 */
	public Vector setZero(){
		this.x=0;
		this.y=0;
		this.z=0;
		
		return this;
	}
	/**
	 * Describes the vector with its components
	 */
	public String toString(){
		return x+", "+y+", "+z;
	}
	/*
	 * Getters and setters
	 */
	public Vector setX(double x){this.x = x;return this;}
	public Vector setY(double y){this.y = y;return this;}
	public Vector setZ(double z){this.z = z;return this;}
	public double getX(){return this.x;}
	public double getY(){return this.y;}
	public double getZ(){return this.z;}
}
