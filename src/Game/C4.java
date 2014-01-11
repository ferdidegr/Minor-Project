package Game;

import static org.lwjgl.opengl.GL11.*;
import ParticleSystem.ParticleEmitter;
import Utils.Models;
import Utils.Timer;
import Utils.Vector;
public class C4 extends levelObject{
	private boolean activated = false;
	private static float height = 0.3f;
	private ParticleEmitter pe = new ParticleEmitter(new Vector(0, 0.25*Mazerunner.SQUARE_SIZE, 0)	// Position
																,0.05,0.05,0.05				// Initial velocity
																,0,0,0			// acceleration
																,15, 500);				// pointsize;
																
	private Timer timer = new Timer();
	
	public C4(double x, double y, double z) {
		super(x, y, z);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		if(!activated){
			glPushMatrix();
			glTranslated(locationX, locationY, locationZ);
			glScaled(0.25, 0.25, 0.25);
			for(int i = 0 ; i<4; i++){
				glRotated(90*i, 0, 1, 0);
				glNormal3d(0, 1, 1);
				glBegin(GL_QUADS);
				glTexCoord2d(0, height);		glVertex3d(-0.5, 0, +0.5);	
				glTexCoord2d(1, height);		glVertex3d(0.5, 0, +0.5);	
				glTexCoord2d(1, 0);				glVertex3d(0.5, 0+height, +0.5);	
				glTexCoord2d(0, 0);				glVertex3d(-0.5, 0+height, +0.5);	
				glEnd();
			}
			glPopMatrix();
		}else{
			glShadeModel(GL_FLAT);
			glPushMatrix();
			glTranslated(locationX, locationY, locationZ);					
			/*
			 * Displays the flame when the monsterlist is empty
			 */			
			glPushMatrix();
	        glPushAttrib(GL_ENABLE_BIT);
	        glDisable(GL_CULL_FACE);
	        glDisable(GL_LIGHTING);		       
	        pe.display();
	        glPopAttrib();
			glPopMatrix();			
			glPopMatrix();
			glShadeModel(GL_SMOOTH);
		}
		
	}

	@Override
	public boolean isCollision(double x, double y, double z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getmaxDistX(double X) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getmaxDistZ(double Z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(int deltaTime) {
		if(activated){
			pe.update(deltaTime);
			for(int i = 0 ; i < 60 *deltaTime/16.5 && timer.getTime()<500; i++){
				pe.emit();
			}
		}
		
	}

	@Override
	public void change() {
		if(!activated){
			if(Math.abs(Mazerunner.player.getLocation().distance(this.getLocation()))<3){Mazerunner.player.getHealth().addHealth(-50);}
			
			for(Monster mo:Mazerunner.monsterlijst){
				if(Math.abs(mo.getLocation().distance(this.getLocation()))<3){mo.addHealth(-50);}
			}
			
			activated = true;	
			timer.start();
		}
	}
	
	public boolean exploded(){
		return (pe.getListSize()==0 && activated);
	}
	
}
