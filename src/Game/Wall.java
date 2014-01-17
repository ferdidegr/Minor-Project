package Game;

import static org.lwjgl.opengl.GL11.* ;

import org.lwjgl.opengl.GL11;

import Utils.Graphics;
import Utils.Material;
import Utils.Utils;
/**
 * Walls of the game. They can be different in height
 * 
 * @author ZL
 *
 */
public class Wall extends levelObject{
	/**
	 * 		 7 +--------+ 6
  	 *		  /|       /|
  	 * 		 / |      / |
  	 * 	4	+--+-----+ 5|
  	 * 		|  |     |  |
  	 * 		| 3|_____|__| 2
  	 * 		| /      | /
  	 * 		|/       |/
  	 * 	0	+--------+ 1
	 */
	private double[][] boxvertices;
	private double left, right, back, front, top, bottom;
	private boolean wleft, wright, wfront, wback;
	private double height;
	private int SQUARE_SIZE;
	private int[][] boxfaces= {{0,1,5,4},		// front (near you, positive z axis)
								  {1,2,6,5},	// right
								  {2,3,7,6},	// back
								  {3,0,4,7},	// left
								  {4,5,6,7}};	// top
	private double[][] normals = new double[][]
										{{0,0,1},
										 {1,0,0},
										 {0,0,-1},
										 {-1,0,0},
										 {0,1,0}};

	/**
	 * Initialize wall
	 * @param x X-location of the midpoint
	 * @param y Y-location, does not matter is not used, is always 0
	 * @param z Z-location of the midpoint
	 * @param size size of a side
	 * @param height height of the wall (1-7)
	 * @param SQUARE_SIZE
	 */
	public Wall(double x, double y, double z,double size, double height, int SQUARE_SIZE){
		super(x, y, z);
		this.left = x-size/2;
		this.right = x+size/2;
		this.front = z+size/2;
		this.back = z-size/2;
		this.bottom = 0;
		this.top = height;
		this.height= height;
		setSZ(SQUARE_SIZE);
		this.boxvertices = new double[][]{
							{left, bottom, front},	//0
							{right, bottom, front},	//1
							{right, bottom, back},	//2
							{left, bottom, back},	//3
							{left, top, front},		//4
							{right, top, front},	//5
							{right, top, back},		//6
							{left, top, back}};		//7
	}
	/**
	 * Sets the square size used in the game
	 * @param SQUARE_SIZE
	 */
	public void setSZ(int SQUARE_SIZE){
		this.SQUARE_SIZE= SQUARE_SIZE;
	}
	/**
	 * Draw the wall
	 */
	public void display(){
		/*
		 * contact culling, removes all faces which has contact with a wall with an equal or bigger height
		 */		
		int ss = Mazerunner.SQUARE_SIZE;
		int[][] maze = Mazerunner.maze;
		int X = getGridX((int) ss);
		int Z = getGridZ((int) ss);
		boolean[] contact = new boolean[5];
		contact[0] = (Z== maze.length-1? false: (maze[Z+1][X]> height && maze[Z+1][X]<10));
		contact[1] = (X==maze[0].length-1? false: (maze[Z][X+1]> height && maze[Z][X+1] <10));
		contact[2] = (Z==0? false: (maze[Z-1][X]> height && maze[Z-1][X] <10));
		contact[3] = (X==0? false: (maze[Z][X-1]> height && maze[Z][X-1] <10));
		contact[4] = false;
		
		/*
		 * Wall
		 */
		double width = (right-left)/SQUARE_SIZE;
		double depth = (front-back)/SQUARE_SIZE;
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		glBegin(GL_QUADS);
		for(int i = 0; i < boxfaces.length; i++){
			if(!contact[i]){
				Utils.glNormalvec(normals[i]);
				glTexCoord2d(0, (i==boxfaces.length-1?depth:height/SQUARE_SIZE));
				Utils.glVertvec(boxvertices[boxfaces[i][0]]);
				glTexCoord2d(width, (i==boxfaces.length-1?depth:height/SQUARE_SIZE));
				Utils.glVertvec(boxvertices[boxfaces[i][1]]);
				glTexCoord2d(width, 0);
				Utils.glVertvec(boxvertices[boxfaces[i][2]]);
				glTexCoord2d(0, 0);
				Utils.glVertvec(boxvertices[boxfaces[i][3]]);
			}
		}
		glEnd();
		
		/*
		 * Floor thickener
		 */
		
		glPushMatrix();
		glTranslated(left, -1, back);		

		boolean left  = (X==0? false: maze[Z][X-1]==0);
		boolean fore  = (Z==0? false: maze[Z-1][X]==0);
		boolean right  = (X==maze[0].length-1? false: maze[Z][X+1]==0);
		boolean back  = (Z== maze.length-1? false: maze[Z+1][X]==0);
		
		Material.setMtlHole();
		Graphics.groundThickner(left, fore, right, back);	
		glPopMatrix();
	}
	/**
	 * Maximum distance you can travel till you collide
	 * @param x
	 * @return
	 */
	public double getmaxDistX(double X){
		if(locationX>X)return left-X;
		return right - X;
	}
	/**
	 * Maximum distance you can travel till you collide
	 * @param z
	 * @return
	 */
	public double getmaxDistZ(double Z){
		if(locationZ>Z)return back-Z;
		return front - Z;
	}
	/**
	 * Check if you collide with the wall	
	 */
	public boolean isCollision(double x, double y, double z){
		return x>left && x<right && z<front && z>back  && y<top;	
	}
	
	public double[][] getVert(){ return boxvertices;}
	public double[][] getnorm(){ return normals;}
	
	@Override
	public double getmaxDistY(double Y) {

		return top-Y;
	}
	@Override
	public void update(int deltaTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void change() {
		// TODO Auto-generated method stub
		
	}	

}
