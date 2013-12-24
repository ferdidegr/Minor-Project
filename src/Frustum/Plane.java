package Frustum;

import Utils.Vector;

public class Plane {
	private double a, b, c, d;
	
	public Plane(double a, double b, double c, double d){
		setA(a);
		setB(b);
		setC(c);
		setD(d);
	}
	
	public double signDist(Vector v){
		return (a*v.getX()+b*v.getY()+c*v.getZ()+d)/Math.sqrt(a*a+b*b+c*c);
	}
	
	public int sign(Vector v){
		return (int) Math.signum(a*v.getX()+b*v.getY()+c*v.getZ()+d);
	}
	
	public static Plane derivPlane(Vector v1, Vector v2,Vector point){
		double a,b,c,d;
		Vector v3 = v1.crossprod(v2);
		a = v3.getX();
		b = v3.getY();
		c = v3.getZ();
		d= - a*point.getX() - b*point.getY() - c*point.getZ();
		
		return new Plane(a, b, c, d);
		
	}
	
	public static Plane derivPlane(Vector normal, Vector point){
		double a,b,c,d;		
		a = normal.getX();
		b = normal.getY();
		c = normal.getZ();
		d= - a*point.getX() - b*point.getY() - c*point.getZ();
		
		return new Plane(a, b, c, d);		
	}
	
	public String toString(){
		return a+" "+b+" "+c+" "+d;
	}
	
	public Plane normalize(){
		double length = Math.sqrt(a*a+b*b+c*c+d*d);
		a /= length;
		b /= length;
		c /= length;
		d /= length;
		return this;
	}
	
	/*
	 * Getters and setters
	 */
	public void setA(double a){ this.a = a;}
	public void setB(double b){ this.b = b;}
	public void setC(double c){ this.c = c;}
	public void setD(double d){ this.d = d;}
	
}
