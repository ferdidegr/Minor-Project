package Game;
import static org.lwjgl.opengl.GL11.*;
import Utils.Material;
import Utils.Timer;


/**
 * Level element which you can open and close on command.
 * When opened, opening or closing. 
 * All objects with vertical collision detection will fall through.
 * 
 * @author ZL
 *
 */

public class Hatch extends levelObject{
	private float rotangle = 0;
	private float rotspeed = 0f;
	private HatchState hatchstatus;
	private Timer timer = new Timer();
	private boolean first = true;
	/**
	 * Places the hatch on the given location.
	 * It is randomly determined if the begin state is opened or closed.
	 * @param x
	 * @param y
	 * @param z
	 */
	public Hatch(double x, double y, double z) {
		super(x, y, z);
		// Randomly determine of the hatch is open or closed
		hatchstatus=HatchState.CLOSED;
		// Set rotation angle to the chosen state
		rotangle = 0;		
		timer.start();
	}
	/**
	 * Draw the hatch
	 */
	@Override
	public void display() {
		glNormal3d(0, 1, 0);
		/*
		 * Left side
		 */
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);
		glRotatef(-rotangle, 0, 0, 1);
		Material.setMtlHatch();
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);			glVertex3d(0, 0, 0);
		glTexCoord2d(0, 1);			glVertex3d(0, 0, Mazerunner.SQUARE_SIZE);
		glTexCoord2d(0.5, 1);		glVertex3d(Mazerunner.SQUARE_SIZE/2.0, 0, Mazerunner.SQUARE_SIZE);
		glTexCoord2d(0.5, 0);		glVertex3d(Mazerunner.SQUARE_SIZE/2.0, 0, 0);
		glEnd();		
		
		glPopMatrix();
		/*
		 * Right side
		 */
		glPushMatrix();
		glTranslated(locationX+Mazerunner.SQUARE_SIZE, locationY, locationZ);
		glRotatef(rotangle, 0, 0, 1);
		
		glBegin(GL_QUADS);
		glTexCoord2d(1, 0);			glVertex3d(0, 0, 0);
		glTexCoord2d(0.5, 0);			glVertex3d(-Mazerunner.SQUARE_SIZE/2.0, 0, 0);
		glTexCoord2d(0.5, 1);			glVertex3d(-Mazerunner.SQUARE_SIZE/2.0, 0, Mazerunner.SQUARE_SIZE);
		glTexCoord2d(1, 1);			glVertex3d(0, 0, Mazerunner.SQUARE_SIZE);
		
		
		glEnd();		
		
		glPopMatrix();
		
	}
	/**
	 * Open or close the hatch on command
	 */
	public void change(){
		if(hatchstatus==HatchState.CLOSED && (timer.getTime()>5000 || first)){
			hatchstatus = HatchState.OPENING;
			first = false;
		}
	}
	/**
	 * Checks if there is collision, there is only collision when the hatch is closed.
	 * Which means that you wont fall through it only when it is closed.
	 */
	@Override
	public boolean isCollision(double x, double y, double z) {
		if(hatchstatus==HatchState.CLOSED){ 
			return y<0 && y> -1 && x>locationX && x<locationX+Mazerunner.SQUARE_SIZE && z>locationZ && z<locationZ+Mazerunner.SQUARE_SIZE;
		}
		return false;
	}
	/**
	 * Not needed
	 */
	@Override
	public double getmaxDistX(double X) {
		return 0;
	}
	/**
	 * Distance to the hatch
	 */
	@Override
	public double getmaxDistY(double Y) {
		return locationY-Y;
	}
	/**
	 * Not needed
	 */
	@Override
	public double getmaxDistZ(double Z) {
		return 0;
	}
	/**
	 * Display opening and closing animation
	 */
	@Override
	public void update(int deltaTime) {
		
		if(rotangle >85) {rotangle =85;rotspeed*=0;hatchstatus = HatchState.OPEN;}
		if(rotangle <0) {rotangle =0;rotspeed*=0;hatchstatus = HatchState.CLOSED; timer.start();}
		if(hatchstatus == HatchState.OPENING){rotspeed=0.2f;}
		if(hatchstatus == HatchState.CLOSING){rotspeed= -0.2f;}
		if(hatchstatus == HatchState.OPEN){hatchstatus = HatchState.CLOSING;}
		rotangle =rotangle%360 + rotspeed * deltaTime;
	}
	/**
	 * Enum class for the states
	 * @author ZL
	 *
	 */
	private enum HatchState{
		OPEN, OPENING, CLOSING, CLOSED;
	}
	
}

