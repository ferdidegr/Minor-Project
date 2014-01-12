package Game;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import ParticleSystem.ParticleEmitter;
import Utils.Material;
import Utils.Timer;
import Utils.Vector;

/**
 * A C4 explosive in simplified form. 
 * It explodes and affects all creatures (you as well) in a radius of 3 around it. 
 * Regardless if there is a wall or not.
 * 
 * @author ZL
 *
 */
public class C4 extends levelObject{
	// Is the C4 detonated
	private boolean activated = false;
	// Height of the c4 package
	private static float height = 0.3f;
	// Particle system for the explosion
	private ParticleEmitter pe = new ParticleEmitter(new Vector(0, 0.25*Mazerunner.SQUARE_SIZE, 0)	// Position
																,0.05,0.05,0.05						// Initial velocity
																,0,0,0								// acceleration
																,30, 500);							// pointsize, life time;
	// Timer to keep track of the time passed after detonation
	private Timer timer = new Timer();
	// The corners of the C4 package, for collision detection
	private double Xmin, Xmax,Zmin,Zmax;
	/**
	 * constructor
	 * @param x Xlocation of the midpoint
	 * @param y	Ylocation
	 * @param z	Zlocation of the midpoint
	 */
	public C4(double x, double y, double z) {
		super(x, y, z);
		this.Xmin = x-0.125;
		this.Zmin = z-0.125;
		this.Xmax = x+0.125;
		this.Zmax = z+0.125;
		// TODO Auto-generated constructor stub
	}
	/**
	 * draws the C4 package or the explosion
	 */
	@Override
	public void display() {
		/*
		 * C4 package
		 */
		if(!activated){
			glPushMatrix();
			glTranslated(locationX, locationY, locationZ);
			glScaled(0.25, 0.25, 0.25);
			
			double Iwidth = Textures.c4.getWidth();
			double Iheight= Textures.c4.getHeight();
			
			Material.setMtlC4();
			// Top of the package
			glBegin(GL_QUADS);
			glTexCoord2d(0, 0);					glVertex3d(-0.5, height, -0.5);
			glTexCoord2d(0, Iheight);			glVertex3d(-0.5, height, 0.5);
			glTexCoord2d(Iwidth, Iheight);		glVertex3d(0.5, height, 0.5);
			glTexCoord2d(Iwidth, 0);			glVertex3d(0.5, height, -0.5);
			glEnd();
			// sides of the package
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
		}
		/*
		 * Explosion
		 */
		else{
			glShadeModel(GL_FLAT);
			glPushMatrix();
			glTranslated(locationX, locationY, locationZ);					
			
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
	/**
	 * No collision possible with the package, you just walk over it
	 */
	@Override
	public boolean isCollision(double x, double y, double z) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Not needed as there is no collision
	 */
	@Override
	public double getmaxDistX(double X) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Not needed as there is no collision
	 */
	@Override
	public double getmaxDistY(double Y) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Not needed as there is no collision
	 */
	@Override
	public double getmaxDistZ(double Z) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Update the explosion and the Ylocation in case of falling
	 */
	@Override
	public void update(int deltaTime) {
		// X index of maze[][]
		int Xin = getGridX(Mazerunner.SQUARE_SIZE);
		// Zindex od maze[][]
		int Zin = getGridZ(Mazerunner.SQUARE_SIZE);
		// Storage for objects in the 3x3 grid around you
		ArrayList<Integer> tempindex = new ArrayList<Integer>();
		// current Y location (make it shorter)
		double py = locationY;
		// Has collision occurred
		boolean colY = false;
		// initialize max distance you can travel in Y direction
		double maxY = -100;
		// downward movement due to gravity pull
		double velocityY = -deltaTime*0.005;
		
		// Get indices of the arraylist with collidable objects
		for(int i = -1 ; i<=1;i++){
			for(int j = -1; j<=1; j++){
				if((Xin+i)>=0 && (Xin+i)<Mazerunner.maze[0].length && (Zin+j)>=0 && (Zin+j)<Mazerunner.maze.length){

						tempindex.add(Mazerunner.objectindex[Zin+j][Xin+i]);							

				}
			}
		}		
		// Check collision with the objects obtained above
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
		// Move till you hit something or keep falling
		if(colY){locationY += maxY;}else{locationY += velocityY;}
		// If detonated, emit particles for half a second
		if(activated){
			pe.update(deltaTime);
			for(int i = 0 ; i < 60 *deltaTime/16.5 && timer.getTime()<500; i++){
				pe.emit();
			}
		}
		
	}
	/**
	 * Detonate the C4
	 */
	@Override
	public void change() {
		if(!activated){
			// Do damage to every living thing around it
			if(Math.abs(Mazerunner.player.getLocation().distance(this.getLocation()))<3){Mazerunner.player.getHealth().addHealth(-50);}
			
			for(Monster mo:Mazerunner.monsterlijst){
				if(Math.abs(mo.getLocation().distance(this.getLocation()))<3){mo.addHealth(-50);}
			}
			
			activated = true;	
			// Start timer
			timer.start();
		}
	}
	
	/**
	 * Is the C4 exploded and depleted
	 * @return true or false
	 */
	public boolean exploded(){
		return (pe.getListSize()==0 && activated);
	}
	
}
