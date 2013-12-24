package UnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import Utils.Vector;
import Frustum.Plane;

public class PlaneTest {

	@Test
	public void signedDistTest() {
		Plane p = new Plane(2, -2, 0, 2);
		Vector point = new Vector(-7, 3, 0);
		System.out.println(p.signDist(point));
	}
	
	@Test
	public void createPlaneTest(){
		Vector v1 = new Vector(3, 0, -5);
		Vector v2 = new Vector(0, 4, -5);
		Vector point = new Vector(3, 0, 0);
		Plane p = Plane.derivPlane(v1, v2, point);
		System.out.println(p.toString());
		System.out.println(p.signDist(new Vector(2, 1, 1)));
	}
	
	@Test
	public void createPlaneTest2(){
		Vector v1 = new Vector(0, 1, 0);
		Vector point = new Vector(0, 2, 0);
		Plane p = Plane.derivPlane(v1, point);
		System.out.println(p.toString());
		System.out.println(p.signDist(new Vector(-7, 5, 0)));
	}

}
