package Game;

import static org.lwjgl.opengl.GL11.* ;

public class Wall extends GameObject{
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
	private int[][] boxfaces= {{0,1,5,4},
								  {1,2,6,5},
								  {2,3,7,6},
								  {3,0,4,7},
								  {4,5,6,7}};
	private double[][] normals = new double[][]
										{{0,0,1},
										 {1,0,0},
										 {0,0,-1},
										 {-1,0,0},
										 {0,1,0}};

	
	public Wall(double x, double y, double z,double size, double height){
		super(x, y, z);
		this.left = x-size/2;
		this.right = x+size/2;
		this.front = z+size/2;
		this.back = z-size/2;
		this.bottom = 0;
		this.top = height;
		
		this.boxvertices = new double[][]{
							{left, bottom, front},
							{right, bottom, front},
							{right, bottom, back},
							{left, bottom, back},
							{left, top, front},
							{right, top, front},
							{right, top, back},
							{left, top, back}};	
	}
	
	public void draw(){
		glBegin(GL_QUADS);
		for(int i = 0; i < boxfaces.length; i++){
			Utils.glNormalvec(normals[i]);
			for(int j = 0; j < boxfaces[0].length; j++){
				Utils.glVertvec(boxvertices[boxfaces[i][j]]);				
			}
		}
		glEnd();
	}
	
	public boolean isCollision(double x, double y, double z){
		return x>left && x<right && z<front && z>back && y>	bottom && y<top;	
	}
	
	public double[][] getVert(){ return boxvertices;}
	public double[][] getnorm(){ return normals;}	

}
