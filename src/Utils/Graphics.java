package Utils;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;


public class Graphics {
	public static FloatBuffer  black,darkgrey, lightgrey, green, grey, white, blue, red;
	
	// Shortcuts to buffers
	static{
		darkgrey = Utils.createFloatBuffer(0.3f,0.3f,0.3f,0.0f);
		lightgrey = Utils.createFloatBuffer(0.7f,0.7f,0.7f,0.0f);
		white = Utils.createFloatBuffer(1f,1f,1f,0.0f);
		black = Utils.createFloatBuffer(0f,0f,0f,0.0f);
		green = Utils.createFloatBuffer(0f,1f,0f,0.0f);
		blue = Utils.createFloatBuffer(0f,0f,1f,0.0f);
		red = Utils.createFloatBuffer(1f,0f,0f,0.0f);
		grey = Utils.createFloatBuffer(0.5f,0.5f,0.5f,0.0f);
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
		
		cylinder.setNormals(GLU.GLU_OUTSIDE);		

		for(int i = -1; i <=1;i++){
			for(int j = -1; j<=1;j++){
				glPushMatrix();
				glRotated(-90, 1, 0, 0);
				glTranslatef(i*baseradius*2/3f, j*baseradius*2/3f, 0);
				cylinder.draw(baseradius/3, 0, height, 16, 1);
				glPopMatrix();
			}
		}

	}
}
