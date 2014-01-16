package UnitTests;

import static org.junit.Assert.*;
import Intelligence.Node;
import Utils.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {
public Node n1, n2;
	
	@Before
	public void setUp(){
		n1 = new Node(1,2);
		n1.setG(50);
		n1.setH(23);
		n2 = new Node(1,6);
		n2.setParent(n1);
	}
	
	@After
	public void cleanUp(){
		n1=null;
		n2 = null;
	}
	
	@Test
	public void cloneTest(){
		Node n3= n1.clone();
		assertTrue(n3.getF()==n1.getF());
	}
	
	@Test
	public void cloneTestParent(){
		Node n3= n1.clone();
		assertEquals(n3.getParent(), n1.getParent());
	}
	
	@Test 
	public void cloneTestFalse(){
		Node n3= n1.clone();
		assertFalse(n3.getF()==n2.getF());
	}
	
	@Test 
	public void cloneTestNotSame(){
		Node n3= n1.clone();
		assertNotSame(n3, n1);
	}
	
	@Test
	public void equalsTest(){
		Node n3= new Node(1,2);
		assertTrue(n3.equals(n1));
	}
	
	@Test
	public void equalsTestObject(){
		Vector vec = new Vector(1, 0, 2);
		assertFalse(n1.equals(vec));
	}
	
	@Test
	public void equalsTestFalseZ(){
		assertFalse(n2.equals(n1));
	}
	
	@Test
	public void equalsTestFalseX(){
		Node n3 = new Node(5,2);
		assertFalse(n1.equals(n3));
	}
	
	@Test
	public void equalsTestFalseboth(){
		Node n3 = new Node(4,3);
		assertFalse(n1.equals(n3));
	}
	
	
	@Test
	public void testSuccessors(){
		Node[] res = n1.getSuccessors();
		Node exp2 = new Node(0,2);
		Node exp1 = new Node(2,2);
		Node exp3 = new Node(1,1);
		Node exp4 = new Node(1,3);
		Node[] expected = {exp1, exp2, exp3, exp4};
		assertArrayEquals(res, expected);
	}
	
	@Test
	public void testFindG(){
		n2.findG();
		assertTrue(n2.getG()==(n1.getG() +1));
	}
	
	@Test(expected = NullPointerException.class)
	public void testFindGException(){
		n1.findG();
		assertTrue(n1.getG()==50);
	}
	
	@Test
	public void testDistance(){
		double res= n1.distance(n2);
		assertTrue(res == 4);
	}
	
	@Test
	public void testDistanceFalse(){
		double res= n1.distance(n2);
		assertFalse(res == 2);
	}
	
	@Test
	public void testCompareTo(){
		n1.setG(0);
		n2.setG(0);
		n1.setH(20);
		n2.setH(20.001);
		int res = n1.compareTo(n2);
		assertTrue(res<0);
	}
	
	@Test
	public void testCompareToFalse(){
		n1.setG(0);
		n2.setG(0);
		n1.setH(21);
		n2.setH(20);
		int res = n1.compareTo(n2);
		assertFalse(res<0);
	}
	
	@Test
	public void testToString(){
		String res=n2.toString();
		assertEquals(res, "[1, 6] G= 500 H= 500.0 F= 5500.0 Parent: [1, 2]");
	}
	
	@Test
	public void testToStringNoparent(){
		String res=n1.toString();
		assertEquals(res, "[1, 2] G= 50 H= 23.0 F= 280.0 No Parent");
	}
}
