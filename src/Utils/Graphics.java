package Utils;

import static org.lwjgl.opengl.GL11.*;


import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;


public class Graphics {
	public static FloatBuffer  black,darkgrey, lightgrey, green, grey, white, blue, red;
	
	// Shortcuts to buffers
	static{
		darkgrey = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.3f).put(0.3f).put(0.3f).put(0.0f).flip();
		lightgrey = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.7f).put(0.7f).put(0.7f).put(0.0f).flip();
		white = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
		black = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.0f).put(0.0f).put(0.0f).put(0.0f).flip();
		green = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.0f).put(1.0f).put(0.0f).put(0.0f).flip();
		blue = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.0f).put(0.0f).put(1.0f).put(0.0f).flip();
		red = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(1.0f).put(0.0f).put(0.0f).put(0.0f).flip();
		grey = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.5f).put(0.5f).put(0.5f).put(0.0f).flip();
	}
	
	
	// Draw shapes
	public static Cylinder cylinder = new Cylinder();
	
	/**
	 * Draws a cube without top centered at (0,0,0)
	 * @param size
	 */
	public static void renderCube(double size, boolean left, boolean right, boolean fore, boolean back){
			double height = size;	
			size = size/2;
	      glBegin(GL_QUADS);
	      if(!back){
	        glNormal3d(0, 0, 1);
	        glVertex3d(-size, height, size);	 	        
	        glVertex3d(-size, 0, size);	
	        glVertex3d(size, 0, size);	      	     
	        glVertex3d(size, height, size);		     
	      }
	      if(!right){
	        glNormal3d(1, 0, 0);
	        glVertex3d(size, height, size);	      	     
	        glVertex3d(size, 0, size);	
	        glVertex3d(size, 0, -size);	      	     
	        glVertex3d(size, height, -size);	
	      }
	      if(!fore){
	        glNormal3d(0, 0, -1);
	        glVertex3d(size, height, -size);	      	     
	        glVertex3d(size, 0, -size);
	        glVertex3d(-size, 0, -size);	      	     
	        glVertex3d(-size, height, -size);		        
	      }
	      if(!left){
	        glNormal3d(-1, 0, 0);
	        glVertex3d(-size, height, -size);	      	     
	        glVertex3d(-size, 0, -size);
	        glVertex3d(-size, 0, size);	 	        
	        glVertex3d(-size, height, size);		        
	      }
	        	        
	      glNormal3d(0, 1, 0);
	        glVertex3d(-size, height, size);	 
	        glVertex3d(size, height, size);
	        glVertex3d(size, height, -size);
	        glVertex3d(-size, height, -size);	        
	        glEnd();
	}
	
	public static void renderSpike(float baseradius, float height){
		//Cylinder spike = new Cylinder();
		cylinder.setNormals(GLU.GLU_OUTSIDE);

		for(int i = -1; i <=1;i++){
			for(int j = -1; j<=1;j++){
				glPushMatrix();
				glRotated(-90, 1, 0, 0);
				glTranslatef(i*baseradius*2/3f, j*baseradius*2/3f, 0);
				cylinder.draw(baseradius/3, 0, height, 50, 50);
				glPopMatrix();
			}
		}

	}
}