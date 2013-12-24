package UnitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Utils.Vector;

public class VectorTest {
	public Vector v1, v2;
	
	@Before
	public void setUp(){
		v1 = new Vector(1,2,3);
		v2 = new Vector(4,5,6);
	}
	
	@After
	public void cleanUp(){
		v1=null;
		v2 = null;
	}
	/**
	 * Average Test
	 */
	@Test
	public void avgTest(){
		assertEquals(v1.avg(v2), new Vector(2.5, 3.5, 4.5));
	}
	/**
	 * Rotate2D
	 */
	@Test
	public void rotate2DTest(){
		Vector v1 = new Vector(0, 0, 1);	
	
		assertEquals(v1.rotate2D(Math.toRadians(90)), new Vector(1, 0, 0));
	}
	
	@Test
	public void rotate2DTest2(){
		Vector v1 = new Vector(1, 1, 1);			
		Vector v2 = v1.rotate2D(Math.toRadians(180));
		assertEquals(v2 , new Vector(-1, 1, -1));
	}
	
	@Test
	public void rotate2DTest3(){
		Vector v1 = new Vector(1, 0, 0);			
		Vector v2 = v1.rotate2D(Math.toRadians(-90));
		assertEquals(v2 , new Vector(0, 0, 1));
	}
	
	/**
	 * Distance
	 */
	@Test
	public void distanceTest(){
		assertEquals(v1.distance(v2),3*Math.sqrt(3),0.0001);
	}
	
	/**
	 * Scale
	 */
	@Test
	public void scaleTest(){
		assertEquals(v1.scale(v2), new Vector(4, 10, 18));
	}
	
	/**
	 * length2D
	 */
	@Test
	public void length2DTest(){
		assertEquals(v1.length2D(),Math.sqrt(10),0.0001);
	}
	/**
	 * Normalize
	 */
	@Test
	public void normalize2D(){
		assertEquals(1, v1.normalize2D().length2D(),0.001);
	}
	
	@Test
	public void normalize2DTest2(){
		Vector v1 = new Vector(0,1,0);
		assertEquals(v1.length2D(), v1.normalize2D().length2D(),0.001);
	}
	
	@Test
	public void normalizeTest(){
		assertEquals(1, v1.normalize().length(),0.001);
	}
	
	@Test
	public void normalizeperDirTest(){
		assertEquals(Math.sqrt(3), v1.normalizePerdir().length(), 0.001);
	}
	
	@Test
	public void stepTest(){
		assertEquals(new Vector(1, 1, 1), v1.step());
	}
	
	@Test
	public void stepTest2(){
		assertEquals(new Vector(0,0,0), new Vector(-5,-3,-0.1).step());
	}
	/**
	 * Equals
	 */
	@Test
	public void EqualsTest(){
		Vector v1 = new Vector(1, 1, 0);
		Vector v2 = new Vector(1, 1, 0);
		
		assertEquals(v1, v2);
	}
	
	@Test
	public void EqualsTestfalse(){
		Vector v1 = new Vector(1, 0, 0);
		Vector v2 = new Vector(0, 1, 1);		
	
		assertNotEquals(v1, v2);
	}
	
	@Test
	public void equalsTestNotAVector(){		
		assertFalse(v1.equals("hello"));
	}
	/**
	 * Cross product, dot product
	 */
	@Test
	public void CrossProdtest() {
		Vector v1 = new Vector(1, 0, 0);
		Vector v2 = new Vector(0, 1, 0);
		Vector v3 = v1.crossprod(v2);
		assertEquals(new Vector(0, 0, 1), v3);
		assertEquals(new Vector(0, 0, -1), v2.crossprod(v1));
	}
	
	@Test
	public void dotProdTest(){		
		assertEquals(32,v1.dotprod(v2),0.000001);
	}
	
	@Test
	public void setZeroTest(){
		assertEquals(new Vector(0,0,0), v1.setZero());
	}
	
	@Test
	public void toStringTest(){
		assertEquals("1.0, 2.0, 3.0", v1.toString());
	}
}
