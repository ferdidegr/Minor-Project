package ParticleSystem;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Utils.Vector;

public class Test {
	
	public void start(){
		initGL();
		ParticleEmitter pe = new ParticleEmitter(new Vector(Display.getWidth()/2f, Display.getHeight()/2f, 0),
													7,7,0,
													0,0.7,0,
													10);
		Particle.setAttractor(new Vector(Display.getWidth()/2f, 0, 0));
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			pe.display();
			pe.update();
			
			for(int i = 0 ; i < 200; i++){
				pe.emit();
			}		
				
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){	pe.setlocation(pe.getLocation().add(-10,0,0));}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){	pe.setlocation(pe.getLocation().add(10,0,0));}
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){	pe.setlocation(pe.getLocation().add(0,10,0));}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){	pe.setlocation(pe.getLocation().add(0,-10,0));}
			if(Keyboard.isKeyDown(Keyboard.KEY_F12)){ Utils.Utils.makeScreenShot("screenshots//flame1.png");}
			
			Display.update();
			Display.sync(60);
		}
	}
	
	public void initGL(){
		try{
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.create();
		}catch(LWJGLException e){}
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(),1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glClearColor(0, 0, 0, 1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void main(String[] args){
		new Test().start();
	}
}
