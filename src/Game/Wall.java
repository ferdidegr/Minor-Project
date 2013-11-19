package Game;

import static org.lwjgl.opengl.GL11.* ;

public class Wall extends GameObject{
	
	private double[][] boxvertices;
	private double[][] boxfaces= {{0,1,5,4},
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
		this.boxvertices = new double[][]{{x-size, 0, z+size},
							{x+size, 0, z+size},
							{x+size, 0, z-size},
							{x-size, 0, z-size},
							{x-size, height, z+size},
							{x+size, height, z+size},
							{x+size, height, z-size},
							{x-size, height, z-size}};		
	}
	
	public void draw(){
		glBegin(GL_QUADS);
		for(int i = 0; i < 5; i++){
			glNormal3d(normals[i][0],normals[i][1],normals[i][2]);
			for(int j = 0; j < 3; j++){
				
			}
		}
		glEnd();
	}
}
