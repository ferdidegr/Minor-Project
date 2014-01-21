package Game;

import static org.lwjgl.opengl.GL11.*;
import Utils.Graphics;
import Utils.Material;

/**
 * A floor for the game
 * 
 * @author ZL
 *
 */
public class Floor extends levelObject{
	private double width;
	private double height;
	private int up;

	/**
	 * 
	 * @param x	left top corner
	 * @param y	left top corner
	 * @param z	left top corner
	 * @param width x+width = right ;
	 * @param height  z + height = bottom;
	 * @param up	indicates what the up vector is
	 * @param SQUARE_SIZE
	 */
	public Floor(double x, double y, double z,double width, double height, int up) {
		super(x, y, z);
		setHeight(height);
		setWidth(width);
		setUp(up);

	}
	/**
	 * Set up vector, kept in mind that it ight be needed at some time that the floor becomes a ceiling
	 * @param up
	 */
	public void setUp(int up){
		this.up = (int) Math.signum(up);
	}
	/**
	 * The size in X direction
	 * @param width
	 */
	public void setWidth(double width){
		this.width = width;		
	}
	/**
	 * The size in Z direction
	 * @param height
	 */
	public void setHeight(double height){
		this.height = height;
	}
	/**
	 * Set the maximum distance in Y direction you can travel before collision
	 */
	public double getmaxDistY(double Y){
		return up*(locationY-Y);
	}
	/**
	 * Display the floor
	 */
	@Override
	public void display() {
		// Top face of the ground
		glBegin(GL_QUADS);
		glNormal3d(0, 1, 0);
		glTexCoord2d(0, 1);		glVertex3d(this.locationX, this.locationY,this.locationZ);
		glTexCoord2d(0, 0);		glVertex3d(this.locationX, this.locationY,this.locationZ+height);
		glTexCoord2d(1, 0);		glVertex3d(this.locationX+width, this.locationY,this.locationZ+height);
		glTexCoord2d(1, 1);		glVertex3d(this.locationX+width, this.locationY,this.locationZ);
		glEnd();
		/*
		 * Mainly to thicken the floor. Put side faces only when there i no floor next to it.
		 */
		int ss = Mazerunner.SQUARE_SIZE;
		int[][] maze = Mazerunner.maze;
		glPushMatrix();
		glTranslated(locationX, -1, locationZ);
		
		int X = getGridX((int) ss);
		int Z = getGridZ((int) ss);
		boolean left  = (X==0? false: maze[Z][X-1]<10 || maze[Z][X-1]==14);
		boolean fore  = (Z==0? false: maze[Z-1][X]<10 || maze[Z-1][X]==14);
		boolean right  = (X==maze[0].length-1? false: maze[Z][X+1]<10 || maze[Z][X+1]==14);
		boolean back  = (Z== maze.length-1? false: maze[Z+1][X]<10 || maze[Z+1][X]==14);
		
		Material.setMtlHole();
		Graphics.groundThickner(left, fore, right, back);	
		glPopMatrix();
	}
	/**
	 * Checks if there is a collision with the floor, the floor goes to minus infinity.
	 * this to ensure not falling through the ground at very low fps
	 */
	@Override
	public boolean isCollision(double x, double y, double z) {
		
		return y<this.locationY && x>this.locationX && x<(this.locationX+width) &&
				z>this.locationZ && z<(this.locationZ+height);
				
	}
	/**
	 * Get the maximum distance in X you can travel before you collide, not applicable for a floor
	 */
	@Override
	public double getmaxDistX(double X) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Get the maximum distance in Z you can travel before you collide, not applicable for a floor
	 */
	@Override
	public double getmaxDistZ(double Z) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Not needed here
	 */
	@Override
	public void update(int deltaTime) {
		// TODO Auto-generated method stub		
	}
	/**
	 * Not needed here
	 */
	@Override
	public void change() {
		// TODO Auto-generated method stub
		
	}

}
