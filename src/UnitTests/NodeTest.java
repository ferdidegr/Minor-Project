package UnitTests;

import static org.junit.Assert.*;
import Intelligence.Node;

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
	public void equalsTestFalse(){
		assertFalse(n2.equals(n1));
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
}
