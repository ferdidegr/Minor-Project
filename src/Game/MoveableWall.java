package Game;
import static org.lwjgl.opengl.GL11.*;
import Utils.Material;
/**
 * Wall that moves up and down.
 * Can be used as elevator or room separator
 * 
 * @author ZL
 *
 */
public class MoveableWall extends levelObject{
	// Height of the wall
	private double bottom, height=3;
	// Initial wall state
	private wallState wallstate;
	// Draw list 
	private static int Wallmodel = 0;
	
	/**
	 * set Location of the wall
	 * @param x locationX
	 * @param y locationY
	 * @param z locationZ
	 */
	public MoveableWall(double x, double y, double z, boolean up) {
		super(x, y, z);
		// set the wall to up or down position
		this.bottom = (up?0:-height);
		wallstate = (up?wallState.UP:wallState.DOWN);		
		
		if(Wallmodel == 0){
			Wallmodel = glGenLists(1);
			glNewList(Wallmodel, GL_COMPILE);
			drawwall();
			glEndList();
		}
		

	}
	/**
	 * show the wall in its current state
	 */
	@Override
	public void display() {
		glPushMatrix();
		glTranslated(locationX, bottom, locationZ);
		glCallList(Wallmodel);
		glPopMatrix();
	}
	/**
	 * Draw the movable wall
	 */
	public void drawwall(){
		Material.setMtlMWall();
		/*
		 * Sides
		 */
		for(int i = 0 ; i<4; i++){
			glRotated(90*i, 0, 1, 0);
			glNormal3d(0, 1, 1);
			glBegin(GL_QUADS);
			glTexCoord2d(0, height);		glVertex3d(-0.5, -1, +0.5);	
			glTexCoord2d(1, height);		glVertex3d(0.5, -1, +0.5);	
			glTexCoord2d(1, 0);		glVertex3d(0.5, height, +0.5);	
			glTexCoord2d(0, 0);		glVertex3d(-0.5, height, +0.5);	
			glEnd();
		}
		/*
		 * top
		 */
		glNormal3d(0, 1, 0);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);	glVertex3d(-0.5, height, -0.5);
		glTexCoord2d(0, 1);	glVertex3d(-0.5, height, 0.5);
		glTexCoord2d(1, 1);	glVertex3d(0.5, height, 0.5);
		glTexCoord2d(1, 0);	glVertex3d(0.5, height, -0.5);
		glEnd();
	}
	/**
	 * Checks collision of the other object with this
	 */
	@Override
	public boolean isCollision(double x, double y, double z) {
		double ss  = Mazerunner.SQUARE_SIZE;		
		return x>(locationX-ss/2) && x<(locationX+ss/2) && z<(locationZ+ss/2) && z>(locationZ-ss/2)  && y<(bottom+height);			
	}
	/**
	 * Gives maximum distance in X you can travel when outside the bounds.
	 * Other wise do not move.
	 */
	@Override
	public double getmaxDistX(double X) {
		double ss  = Mazerunner.SQUARE_SIZE;
		if(locationX-ss/2>X)return locationX-Mazerunner.SQUARE_SIZE/2f-X;
		else if(locationX+ss/2<X)return locationX+Mazerunner.SQUARE_SIZE/2f-X;
		return 0;
	}
	/**
	 * Distance to the top face
	 */
	@Override
	public double getmaxDistY(double Y) {
		return (bottom+height)-Y;		
	}
	/**
	 * Gives maximum distance in Z you can travel when outside the bounds.
	 * Other wise do not move.
	 */
	@Override
	public double getmaxDistZ(double Z) {
		double ss  = Mazerunner.SQUARE_SIZE;
		
		if(locationZ-ss/2>Z)return locationZ-Mazerunner.SQUARE_SIZE/2f-Z;
		else if(locationZ+ss/2<Z)return locationZ+Mazerunner.SQUARE_SIZE/2f-Z;
		return 0;
	}
	/**
	 * Animation for moving wall
	 */
	@Override
	public void update(int deltaTime) {
		if(wallstate == wallState.ASCENDING){bottom += deltaTime*0.004;}
		if(wallstate == wallState.DESCENDING){bottom -= deltaTime*0.004;}
		if(bottom > 0){bottom=0; wallstate = wallState.UP;}
		if(bottom < -height){bottom = - height; wallstate = wallState.DOWN;}			
	}
	/**
	 * Toggle wall state
	 */
	@Override
	public void change() {
		if(wallstate == wallState.DOWN){wallstate = wallState.ASCENDING;}
		if(wallstate == wallState.UP){wallstate = wallState.DESCENDING;}
	}

	/**
	 * Enum class to keep track of the wall state
	 * @author ZL
	 *
	 */
	private enum wallState{
		UP, ASCENDING, DESCENDING, DOWN;
	}

}
