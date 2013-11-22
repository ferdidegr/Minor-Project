package Game;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;


public class Graphics {
	public static FloatBuffer wallColour, floorColour;
	
	// Shortcuts to buffers
	static{
//		wallColour = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.5f).put(0.0f).put(0.7f).put(1.0f).flip();	// The walls are purple. 
		wallColour = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.7f).put(0.7f).put(0.7f).put(1.0f).flip();	// The walls are white. 
		floorColour = (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0.7f).put(0.7f).put(0.7f).put(0.0f).flip();// The floor was blue.

	}
	
	
	// Draw shapes
	public static Cylinder cylinder = new Cylinder();
	
	/**
	 * Draws a cube without top centered at (0,0,0)
	 * @param size
	 */
	public static void renderCube(double size, boolean left, boolean right, boolean fore, boolean back){
			double height = 6*size;	
	      GL11.glBegin(GL11.GL_QUADS);
	      if(!back){
	        GL11.glNormal3d(0, 0, 1);
	        GL11.glVertex3d(-size, height, size);	 	        
	        GL11.glVertex3d(-size, 0, size);	
	        GL11.glVertex3d(size, 0, size);	      	     
	        GL11.glVertex3d(size, height, size);		     
	      }
	      if(!right){
	        GL11.glNormal3d(1, 0, 0);
	        GL11.glVertex3d(size, height, size);	      	     
	        GL11.glVertex3d(size, 0, size);	
	        GL11.glVertex3d(size, 0, -size);	      	     
	        GL11.glVertex3d(size, height, -size);	
	      }
	      if(!fore){
	        GL11.glNormal3d(0, 0, -1);
	        GL11.glVertex3d(size, height, -size);	      	     
	        GL11.glVertex3d(size, 0, -size);
	        GL11.glVertex3d(-size, 0, -size);	      	     
	        GL11.glVertex3d(-size, height, -size);		        
	      }
	      if(!left){
	        GL11.glNormal3d(-1, 0, 0);
	        GL11.glVertex3d(-size, height, -size);	      	     
	        GL11.glVertex3d(-size, 0, -size);
	        GL11.glVertex3d(-size, 0, size);	 	        
	        GL11.glVertex3d(-size, height, size);		        
	      }
	        	        
	      GL11.glNormal3d(0, 1, 0);
	        GL11.glVertex3d(-size, height, size);	 
	        GL11.glVertex3d(size, height, size);
	        GL11.glVertex3d(size, height, -size);
	        GL11.glVertex3d(-size, height, -size);	        
	        GL11.glEnd();
	}
	
	public static void renderSpike(float baseradius, float height){
		//Cylinder spike = new Cylinder();
		GL11.glRotated(-90, 1, 0, 0);
		for(int i = -1; i <=1;i++){
			for(int j = -1; j<=1;j++){
				GL11.glPushMatrix();
				GL11.glTranslatef(i*baseradius*2/3f, j*baseradius*2/3f, 0);
				cylinder.draw(baseradius/3, 0, height, 10, 1);
				GL11.glPopMatrix();
			}
		}

	}
}
