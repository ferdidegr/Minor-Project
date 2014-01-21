package Game;
import static org.lwjgl.opengl.GL11.*;
import ParticleSystem.ParticleEmitter;
import Utils.Material;
import Utils.Models;
import Utils.Timer;
import Utils.Vector;
/**
 * The endpoint of the game, you should go here to finish.
 * When all monsters are dead the obelisk is activated, a flame will be on top of the obelisk.
 * 
 * @author ZL
 *
 */
public class EndObelisk extends levelObject{
	private ParticleEmitter pe ; 			// Flame	
	
	/**
	 * Constructor: set the location of the midpoint of the Obelisk
	 * @param x X-location
	 * @param y Y-location
	 * @param z Z-location
	 */
	public EndObelisk(double x, double y, double z) {
		// Set location
		super(x, y, z);	
		// Define flame
		pe = new ParticleEmitter(new Vector(0, 8.25*Mazerunner.SQUARE_SIZE, 0)	// Position
												,0.015,0.0005,0.015				// Initial velocity
												,0,0.00075,0					// acceleration
												,10, 700);						// pointsize
	}
	/**
	 * Display the obelisk
	 */
	@Override
	public void display() {
		glShadeModel(GL_FLAT);
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);		
		glCallList(Models.obelisk);			
		/*
		 * Displays the flame when the monsterlist is empty
		 */
		if(Mazerunner.monsterlijst.size()==0){
			glPushMatrix();
	        glPushAttrib(GL_ENABLE_BIT);
	        glDisable(GL_CULL_FACE);
	        glDisable(GL_LIGHTING);		       
	        pe.display();
	        glPopAttrib();
			glPopMatrix();
		}
		glPopMatrix();
		glShadeModel(GL_SMOOTH);
		
		Material.setMtlHole();
		glPushMatrix();
		glTranslated(locationX-0.5, -1, locationZ-0.5);
		Utils.Graphics.groundThickner(false, false, false, false);
		glPopMatrix();
	}
	/**
	 * Checks for the input x,y and z if it is inside the obelisk
	 */
	public boolean isCollision(double x, double y, double z) {
		double width = Mazerunner.SQUARE_SIZE/2f;			
		return x>(locationX-width) && x<(locationX+width) && z>(locationZ-width) && z<(locationZ+width);
	}
	/**
	 * The maximum distance you may travel in X direction before hitting this
	 */
	public double getmaxDistX(double X) {
		if(locationX>X)return locationX-Mazerunner.SQUARE_SIZE/2f-X;
		return locationX+Mazerunner.SQUARE_SIZE/2f-X;
	}
	/**
	 * The maximum distance you may travel in Y direction before hitting this
	 * Not used in this case. You can't jump on top of an obelisk
	 */
	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * The maximum distance you may travel in Z direction before hitting this
	 */
	@Override
	public double getmaxDistZ(double Z) {
		if(locationZ>Z)return locationZ-Mazerunner.SQUARE_SIZE/2f-Z;
		return locationZ+Mazerunner.SQUARE_SIZE/2f-Z;
	}
	/**
	 * Keep emitting particles when the obelisk is activated
	 */
	@Override
	public void update(int deltaTime) {		
		pe.update(deltaTime);
		// Flame, starts the flame when the monsterlist is empty
		if(Mazerunner.monsterlijst.size()==0){						
			for(int i = 0 ; i < 20 *deltaTime/16.5; i++){
				pe.emit();
			}
		}
	}
	/**
	 * Obelisk can not be triggered to move, so not needed.
	 */
	@Override
	public void change() {
		// TODO Auto-generated method stub
	}
}
