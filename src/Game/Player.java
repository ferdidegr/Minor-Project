package Game;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.xml.sax.InputSource;

import Utils.Vector;


/**
 * Player represents the actual player in MazeRunner.
 * <p>
 * This class extends GameObject to take advantage of the already implemented location 
 * functionality. Furthermore, it also contains the orientation of the Player, ie. 
 * where it is looking at and the player's speed. 
 * <p>
 * For the player to move, a reference to a Control object can be set, which can then
 * be polled directly for the most recent input. 
 * <p>
 * All these variables can be adjusted freely by MazeRunner. They could be accessed
 * by other classes if you pass a reference to them, but this should be done with 
 * caution.
 * 
 * @author Bruno Scheele
 *
 */
public class Player extends GameObject {	
	private double horAngle, verAngle, width, height;
	private double speed;
	private Control control = null;
	private boolean spikejump= false;
	
	private double run;
	protected boolean jump = false;
	double tempVy =0 ;
	double y_begin;
	Vector velocity = new Vector(0, 0, 0);
	
	/**
	 * The Player constructor.
	 * <p>
	 * This is the constructor that should be used when creating a Player. It sets
	 * the starting location and orientation.
	 * <p>
	 * Note that the starting location should be somewhere within the maze of 
	 * MazeRunner, though this is not enforced by any means.
	 * 
	 * @param x		the x-coordinate of the location
	 * @param y		the y-coordinate of the location
	 * @param z		the z-coordinate of the location
	 * @param h		the horizontal angle of the orientation in degrees
	 * @param v		the vertical angle of the orientation in degrees
	 */
	public Player( double x, double y, double z, double h, double v, double width, double height ) {
		// Set the initial position and viewing direction of the player.
		super( x, y, z );
		setHorAngle(h);
		setVerAngle(v);
		setSpeed(0.005);
		setHeight(height);
		setWidth(width);
	}
	
	/**
	 * Sets the Control object that will control the player's motion
	 * <p>
	 * The control must be set if the object should be moved.
	 * @param input
	 */
	public void setControl(Control control)
	{
		this.control = control;
	}
	
	/**
	 * Gets the Control object currently controlling the player
	 * @return
	 */
	public Control getControl()
	{
		return control;
	}

	/**
	 * Returns the horizontal angle of the orientation.
	 * @return the horAngle
	 */
	public double getHorAngle() {
		return horAngle;
	}

	/**
	 * Sets the horizontal angle of the orientation.
	 * @param horAngle the horAngle to set
	 */
	public void setHorAngle(double horAngle) {
		this.horAngle = horAngle;
	}

	/**
	 * Returns the vertical angle of the orientation.
	 * @return the verAngle
	 */
	public double getVerAngle() {
		return verAngle;
	}

	/**
	 * Sets the vertical angle of the orientation.
	 * @param verAngle the verAngle to set
	 */
	public void setVerAngle(double verAngle) {
		this.verAngle = verAngle;
	}
	/**
	 * get the width (actually size as it is cubic
	 * @param width
	 */
	public void setHeight(double height){
		this.height = height;
	}
	/**
	 * getWidth
	 * @return
	 */
	public double getHeight(){
		return height;
	}
	/**
	 * get the width (actually size as it is cubic
	 * @param width
	 */
	public void setWidth(double width){
		this.width = width;
	}
	/**
	 * getWidth
	 * @return
	 */
	public double getWidth(){
		return width;
	}
	/**
	 * Returns the speed.
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Updates the physical location and orientation of the player
	 * @param deltaTime The time in milliseconds since the last update.
	 */
	public void update(int deltaTime)
	{

		if (control != null)
		{
			control.update();
					
			horAngle = control.getdX()*speed + horAngle;
			verAngle = control.getdY()*speed + verAngle;
			
			if(verAngle>89) verAngle=89;
			if(verAngle<-89) verAngle=-89;
			
			if(control.jump && !jump){
				tempVy = 0.04;			// 0.03
				jump=true;
				y_begin = locationY;
			}
			if(jump){

				tempVy-=0.002*deltaTime/14f;
				velocity.add(0, deltaTime*tempVy, 0);
			}
			
					
			if (control.getRun()){
				run = 2;
			} else{
				run =1;
			}
		}
			updateV(deltaTime);
			
			/*
			 * Collision detection
			 */
			// Reassign attribute names to make the equations shorter
			
			double px = getLocationX();				// Player X Location
			double py = locationY;					// Player Y location
			double pz = getLocationZ();				// Player Z location
			double ph	  = getHeight();			// Player Height
			double pw	  = getWidth();				// Player Width
			int Xin = getGridX(Mazerunner.SQUARE_SIZE);
			int Zin = getGridZ(Mazerunner.SQUARE_SIZE);
			boolean colX = false;
			boolean colZ = false;
			boolean colY = false;
			
			int signX = (int) Math.signum(velocity.getX()); // Direction of the velocity in X
			int signZ = (int) Math.signum(velocity.getZ()); // Direction of the velocity in Z
			
			ArrayList<Integer> tempindex = new ArrayList<Integer>();
			
			// Get indices of the arraylist with collidable objects
			for(int i = -1 ; i<=1;i++){
				for(int j = -1; j<=1; j++){
					if((Xin+i)>=0 && (Xin+i)<Mazerunner.maze[0].length && (Zin+j)>=0 && (Zin+j)<Mazerunner.maze.length){
						if(Mazerunner.objectindex[Zin+j][Xin+i]>=0){							// < zero means there is nothing so no index
							tempindex.add(Mazerunner.objectindex[Zin+j][Xin+i]);
						}
					}
				}
			}
			
	
			//Add extra block
			tempindex.add(Mazerunner.objlijst.size()-1);
			

			//collision X	
			for(int i = 0; i< tempindex.size();i++){
				levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));				
				if(tempobj.isCollision(px+velocity.getX()+pw*signX, py-ph, pz+pw)
				|| tempobj.isCollision(px+velocity.getX()+pw*signX, py-ph, pz-pw)){
					colX=true;
					locationX+=tempobj.getmaxDistX(locationX+pw*signX);
					break;
				}

			}			
			if(colX){}else{updateX();}
			px = locationX;
			// collsion Z						
			for(int i = 0; i< tempindex.size();i++){			
			
				levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));		
				if(tempobj.isCollision(px+pw, py-ph, pz+pw*signZ+velocity.getZ())
				|| tempobj.isCollision(px-pw, py-ph, pz+pw*signZ+velocity.getZ())){
					colZ=true;
					locationZ+=tempobj.getmaxDistZ(locationZ+pw*signZ);
					break;
				}
			}
			if(colZ){}else{	updateZ();}
			pz= getLocationZ();
			
			// CollisionY
			spikejump=false;
			
			for(int i = 0; i< tempindex.size();i++){
				levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));		
				if(tempobj.isCollision(px+pw,  py+velocity.getY()-ph , pz+pw)
				|| tempobj.isCollision(px-pw,  py+velocity.getY()-ph , pz+pw)
				|| tempobj.isCollision(px-pw,  py+velocity.getY()-ph , pz-pw)
				|| tempobj.isCollision(px+pw,  py+velocity.getY()-ph , pz-pw)){
					if(tempobj instanceof Spikes){control.jump=true;spikejump=true;}
					colY=true;
					locationY+=tempobj.getmaxDistY(locationY-ph);
				}				
			}
			if(!spikejump){control.jump = false;}
			if(colY){jump=false;}else{updateY();}
			py = getLocationY();
		
			// Check trigger
			if(control.triggered){
				for(int i = 0; i< tempindex.size();i++){
					Mazerunner.objlijst.get((tempindex.get(i).intValue())).change();						
				}
				control.triggered=false;
			}
	}
	/**
	 * get X grid location
	 * @param SQUARE_SIZE
	 * @return
	 */
	public int getGridX(int SQUARE_SIZE){
		return (int) Math.floor(locationX/SQUARE_SIZE);
	}
	/**
	 * get Z grid location
	 * @param SQUARE_SIZE
	 * @return
	 */
	public int getGridZ(int SQUARE_SIZE){
		return (int) Math.floor(locationZ/SQUARE_SIZE);
	}
	/**
	 * Update Velocity
	 * @param deltaTime
	 */
	public void updateV(int deltaTime){
		velocity.scale(0.1,.4,0.1);
		if (control.getForward()){
			velocity.add(-run*speed*deltaTime*Math.sin(Math.toRadians(horAngle)),
					0,
					- run*speed*deltaTime*Math.cos(Math.toRadians(horAngle)));
		}
		if (control.getBack()){
			velocity.add(run*speed*deltaTime*Math.sin(Math.toRadians(horAngle)),
					0,
					+ run*speed*deltaTime*Math.cos(Math.toRadians(horAngle)));
		}
		if (control.getLeft()){
			velocity.add(-run*speed*deltaTime*Math.cos(Math.toRadians(horAngle)),
					0,
					+ run*speed*deltaTime*Math.sin(Math.toRadians(horAngle)));
		}
		if (control.getRight()){
			velocity.add(+run*speed*deltaTime*Math.cos(Math.toRadians(horAngle)),
					0,
					- run*speed*deltaTime*Math.sin(Math.toRadians(horAngle)));
		}
		// Gravity
		velocity.add(0, -deltaTime*0.005, 0);

	}
	
	public void updateX(){
		locationX += velocity.getX();

	}
	
	public void updateY(){
		locationY += velocity.getY();
	}
	
	public void updateZ(){
		locationZ += velocity.getZ();
	}
	
	public void draw(){
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);
		glColor4f(1.0f, 1.0f, 1.0f,1.0f);
		glPointSize(50);
		glBegin(GL_POINTS);
		glVertex3d(locationX, locationY-height, locationZ);
		glEnd();
		glLineWidth(2);
		glBegin(GL_LINE_LOOP);
		glVertex3d(locationX+width, locationY-height, locationZ+width);
		glVertex3d(locationX-width, locationY-height, locationZ+width);
		glVertex3d(locationX-width, locationY-height, locationZ-width);
		glVertex3d(locationX+width, locationY-height, locationZ-width);
		glEnd();
		glEnable(GL_LIGHTING);
	}
}
