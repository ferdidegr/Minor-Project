package Game;
import static org.lwjgl.opengl.GL11.*;
import Utils.Graphics;
import Utils.Material;
/**
 * Spikes
 * 
 * @author ZL
 *
 */
public class Spikes extends levelObject{
private static int spike =0;
	/**
	 * set the location of the spikes
	 * @param x locationX
	 * @param y locationY
	 * @param z locationZ
	 */
	public Spikes(double x, double y, double z) {
		super(x, y, z);		
		// When the spike has not been initialized. Compile drawlist
		if(spike == 0){
			spike = glGenLists(1);
			glNewList(spike, GL_COMPILE);
			Graphics.renderSpike(Mazerunner.SQUARE_SIZE/2f, Mazerunner.SQUARE_SIZE);
			glEndList();
		}
	}
	/**
	 * Show spike
	 */
	@Override
	public void display() {
		double ss = Mazerunner.SQUARE_SIZE;
		int[][] maze = Mazerunner.maze;
		glPushMatrix();
		glTranslated(locationX+ss/2f, locationY-ss, locationZ+ss/2f);		
		glCallList(spike);
		//Bottom face
		glBegin(GL_QUADS);
		glVertex3d(-0.5, 0, -0.5);
		glVertex3d(-0.5, 0, 0.5);
		glVertex3d(0.5, 0, 0.5);
		glVertex3d(0.5, 0, -0.5);
		glEnd();
		
		glPopMatrix();
		
	}
	/**
	 * checks collision
	 */
	@Override
	public boolean isCollision(double x, double y, double z) {
		boolean col = y<=this.locationY && x>this.locationX && x<(this.locationX+Mazerunner.SQUARE_SIZE) &&
				z>this.locationZ && z<(this.locationZ+Mazerunner.SQUARE_SIZE);			
		return col;
	}
	/**
	 * Not used here.
	 */
	@Override
	public double getmaxDistX(double X) {

		return 0;
	}
	/**
	 * Not used here.
	 */
	@Override
	public double getmaxDistY(double Y) {

		return 0;
	}
	/**
	 * Not used here.
	 */
	@Override
	public double getmaxDistZ(double Z) {

		return 0;
	}
	/**
	 * Not used here.
	 */
	@Override
	public void update(int deltaTime) {
		
	}
	/**
	 * Not used here.
	 */
	@Override
	public void change() {
		
	}
	
}
