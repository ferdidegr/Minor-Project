package Utils;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Models {
	public static int skybox, obelisk;
	public static List<Integer> monster = new ArrayList<Integer>(); // List with the different monster models for animation
	private static DecimalFormat df = new DecimalFormat("000000"); // Format for loading the right modelfile
	
	static {
		/*
		 * Loading the different files for the animation of the monster (20 files)
		 */
		for (int i = 1; i <= 20; i++) {
			try {
				String file = "scorpion_" + df.format(i) + ".obj";				// The path where to find the file is updated with i
				int drawlist = Model.loadModel("res/Models/Scorpion/",file).generateDList();
				monster.add(drawlist);											// The monster model is added to the list
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*
		 * Obelisk
		 */
		try {
			obelisk = Model.loadModel("res/Models/", "obelisk.obj").generateDList();
			System.out.println("here");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		/*
		 * SkyBox	
		 */
		skybox = glGenLists(1);
		glNewList(skybox, GL_COMPILE);
		
		// Enable/Disable features
		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL_TEXTURE_2D);
		    
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
		glDisable(GL_BLEND);
		    
		float smallnumber = 0.002f;
		// Just in case we set all vertices to white.
		glColor4f(1,1,1,1);
		 
		// Render the front quad
		Game.Textures.front.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f, -0.5f );
			glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f, -0.5f );
			glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f, -0.5f );
			glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f, -0.5f );			        
		glEnd();
		 
		// Render the left quad
		Game.Textures.left.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f,  0.5f );
			glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f, -0.5f );
			glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f, -0.5f );	
			glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f,  0.5f );			        
		glEnd();
		 
		// Render the back quad
		Game.Textures.back.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f,  0.5f );
			glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f,  0.5f );
			glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f,  0.5f );
			glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f,  0.5f );
		glEnd();
		 
		// Render the right quad
		Game.Textures.right.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f, -0.5f );
			glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f, -0.5f );
			glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f,  0.5f );
			glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f,  0.5f );
		glEnd();
		 
		// Render the top quad
		Game.Textures.top.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f,  0.5f, -0.5f );
			glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f,  0.5f, -0.5f );
			glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f,  0.5f,  0.5f );
			glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f,  0.5f,  0.5f );
		glEnd();
		 
		// Render the bottom quad
		Game.Textures.bottom.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0+smallnumber, 0+smallnumber); glVertex3f(  0.5f, -0.5f,  0.5f );
			glTexCoord2f(0+smallnumber, 1-smallnumber); glVertex3f(  0.5f, -0.5f, -0.5f );
			glTexCoord2f(1-smallnumber, 1-smallnumber); glVertex3f( -0.5f, -0.5f, -0.5f );  		       
			glTexCoord2f(1-smallnumber, 0+smallnumber); glVertex3f( -0.5f, -0.5f,  0.5f );
		glEnd();
		
		// Restore enable bits and matrix
		glPopAttrib();
		glEndList();
	}
}
