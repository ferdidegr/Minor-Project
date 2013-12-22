package ParticleSystem;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.util.Calendar;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Utils.Vector;
public class Test3D {
	private double horangle = 0, verangle = 0, x=0, y=0, z=50;
	private long currentTime = 0, previousTime = 0;
	public void start(){		
		initDisp();
		ParticleEmitter pe = new ParticleEmitter(new Vector(0, 0, 0)	// Position
													,1,0.5,1				// Initial velocity
													,0,0.07,0			// acceleration
													,15);				// pointsize
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			// Calculating time since last frame.
			Calendar now = Calendar.getInstance();		
			long currentTime = now.getTimeInMillis();
			int deltaTime = (int)(currentTime - previousTime);
			previousTime = currentTime;
			
			glPushMatrix();
			glTranslated(-x	, -y, -z);
			glRotated(horangle, 0, 1, 0);
			pe.display();
			glPopMatrix();
			pe.update(deltaTime);
			
			inputpoll();
			
			for(int i = 0 ; i < 100; i++){
				pe.emit();
			}		
			
			Display.update();
			Display.sync(60);
		}
	}
	
	public void initDisp(){
		try{
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.create();
		}catch(Exception e){}
		initGL();
		
	}
	
	public void initGL(){
		glViewport(0, 0, Display.getWidth()	, Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60, (float) Display.getWidth()/Display.getHeight(), 0.001f, 1000);
		glMatrixMode(GL_MODELVIEW);
		
		glClearColor(0, 0, 0, 1);	
		
		glDisable(GL_CULL_FACE);
		// Enable normalize of normals
		glEnable(GL_NORMALIZE);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void inputpoll(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){ z-=5;}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){ z+=5;}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){horangle +=5;}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){horangle -=5;}
	}
	
	public static void main(String[] args){
		new Test3D().start();
	}
}
