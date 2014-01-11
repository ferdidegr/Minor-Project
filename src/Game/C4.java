package Game;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

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
																,30, 500);				// pointsize;
																
	private Timer timer = new Timer();
	private double Xmin, Xmax,Zmin,Zmax;
	
	public C4(double x, double y, double z) {
		super(x, y, z);
		this.Xmin = x-0.125;
		this.Zmin = z-0.125;
		this.Xmax = x+0.125;
		this.Zmax = z+0.125;
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
		int Xin = getGridX(Mazerunner.SQUARE_SIZE);
		int Zin = getGridZ(Mazerunner.SQUARE_SIZE);
		ArrayList<Integer> tempindex = new ArrayList<Integer>();
		double py = locationY;
		boolean colY = false;
		double maxY = -100;
		double velocityY = -deltaTime*0.005;
		
		// Get indices of the arraylist with collidable objects
		for(int i = -1 ; i<=1;i++){
			for(int j = -1; j<=1; j++){
				if((Xin+i)>=0 && (Xin+i)<Mazerunner.maze[0].length && (Zin+j)>=0 && (Zin+j)<Mazerunner.maze.length){

						tempindex.add(Mazerunner.objectindex[Zin+j][Xin+i]);							

				}
			}
		}		

		for(int i = 0; i< tempindex.size();i++){
			levelObject tempobj = Mazerunner.objlijst.get((tempindex.get(i).intValue()));		
			if(tempobj.isCollision(Xmax,  py+velocityY , Zmax)
			|| tempobj.isCollision(Xmin,  py+velocityY , Zmax)
			|| tempobj.isCollision(Xmin,  py+velocityY , Zmin)
			|| tempobj.isCollision(Xmax,  py+velocityY , Zmin)){					
				colY=true;
				if(maxY < tempobj.getmaxDistY(locationY)){					
					maxY = tempobj.getmaxDistY(locationY);
				}
					
			}				
		}
		
		if(colY){locationY += maxY;}else{locationY += velocityY;}
		
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
