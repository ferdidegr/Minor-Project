package Game;
import static org.lwjgl.opengl.GL11.*;
import ParticleSystem.ParticleEmitter;
import Utils.Models;
import Utils.Timer;
import Utils.Vector;

public class EndObelisk extends levelObject{
	private ParticleEmitter pe ; 									// Flame
	private Timer timer = new Timer();
	public EndObelisk(double x, double y, double z) {
		super(x, y, z);	
		// Define flame
		pe = new ParticleEmitter(new Vector(x, 8.25*Mazerunner.SQUARE_SIZE, z)	// Position
		,0.015,0.0005,0.015				// Initial velocity
		,0,0.00075,0			// acceleration
		,10, 700);				// pointsize

	}

	@Override
	public void display() {
		glShadeModel(GL_FLAT);
		glPushMatrix();
		glTranslated(locationX, locationY, locationZ);		
		glCallList(Models.obelisk);		
		glPopMatrix();
		glShadeModel(GL_SMOOTH);
		
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
	}


	public boolean isCollision(double x, double y, double z) {
		double width = Mazerunner.SQUARE_SIZE/2f;			
		return x>(locationX-width) && x<(locationX+width) && z>(locationZ-width) && z<(locationZ+width);
	}

	public double getmaxDistX(double X) {
		if(locationX>X)return locationX-Mazerunner.SQUARE_SIZE/2f-X;
		return locationX+Mazerunner.SQUARE_SIZE/2f-X;
	}

	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistZ(double Z) {
		if(locationZ>Z)return locationZ-Mazerunner.SQUARE_SIZE/2f-Z;
		return locationZ+Mazerunner.SQUARE_SIZE/2f-Z;
	}

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

	@Override
	public void change() {
		// TODO Auto-generated method stub
	}
}
