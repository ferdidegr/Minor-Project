package tests;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


public class test {
 public void start(){
	 try {
		Display.create();
	} catch (LWJGLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 while(!Display.isCloseRequested()){
		 
		 Display.update();
		 Display.sync(30);
	 }
 }
 
 public static void main(String[] args){
	 test tst = new test();
	 tst.start();
 }
 
 public void init(){
		
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		// Now we set up our viewpoint.
		GL11.glMatrixMode(GL11.GL_PROJECTION);					// We'll use orthogonal projection.
		GL11.glLoadIdentity();									// REset the current matrix.
		GLU.gluPerspective(60, 800, 800, 200);	// Set up the parameters for perspective viewing. 
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		// Enable back-face culling.
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		// Enable Z-buffering
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		// Set and enable the lighting.
		
		 	FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4).put(new float[] { 0.0f, 50.0f, 0.0f, 1.0f });			// High up in the sky!
	        FloatBuffer lightColour = BufferUtils.createFloatBuffer(4).put(new float[] { 1.0f, 1.0f, 1.0f, 0.0f });				// White light!
	        GL11.glLight( GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);	// Note that we're setting Light0.
	        GL11.glLight( GL11.GL_LIGHT0, GL11.GL_AMBIENT, lightColour);
	        GL11.glEnable( GL11.GL_LIGHTING );
	        GL11.glEnable( GL11.GL_LIGHT0 );
	        
	        // Set the shading model.
	        GL11.glShadeModel( GL11.GL_SMOOTH );
 }
}
