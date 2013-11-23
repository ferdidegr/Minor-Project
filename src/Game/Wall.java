package Game;

import static org.lwjgl.opengl.GL11.* ;

import org.lwjgl.opengl.GL11;

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
							{left, bottom, front},
							{right, bottom, front},
							{right, bottom, back},
							{left, bottom, back},
							{left, top, front},
							{right, top, front},
							{right, top, back},
							{left, top, back}};	
	}
	public void setSZ(int SQUARE_SIZE){
		this.SQUARE_SIZE= SQUARE_SIZE;
	}
	public void display(){
		double width = (right-left)/SQUARE_SIZE;
		double depth = (front-back)/SQUARE_SIZE;
//		glEnable(GL_TEXTURE_2D);
//		Textures.start.bind();
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		glBegin(GL_QUADS);
		for(int i = 0; i < boxfaces.length; i++){
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
		glEnd();
//		glDisable(GL_TEXTURE_2D);
	}
	/**
	 * Maximum distance you can travel till you collide
	 * @param x
	 * @return
	 */
	public double maxDistX(double x){
		
		return Math.min(Math.abs(left-x),Math.abs(right-x));
	}
	/**
	 * Maximum distance you can travel till you collide
	 * @param z
	 * @return
	 */
	public double maxDistZ(double z){
		return Math.min(Math.abs(front-z),Math.abs(back-z));
	}
	
	
	public boolean isCollision(double x, double y, double z){
		return x>left && x<right && z<front && z>back && y>	bottom && y<top;	
	}
	
	public double[][] getVert(){ return boxvertices;}
	public double[][] getnorm(){ return normals;}	

}
