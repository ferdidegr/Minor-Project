package Utils;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import Game.Textures;

public class Material {
	static FloatBuffer none = Utils.createFloatBuffer(0f,0f,0f,0f);

	
	public static void setMtlWall(){

			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
			glMaterial(GL_FRONT, GL_SPECULAR, none);
			Textures.ingamewall.bind();				

	}
	
	public static void setMtlGround(){

			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
			glMaterial(GL_FRONT, GL_SPECULAR, none);
			Textures.ground.bind();		

	}
	
	public static void setMtlsteel(){

			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.grey);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
			glMaterial(GL_FRONT, GL_SPECULAR, Graphics.grey);
			glMaterialf(GL_FRONT, GL_SHININESS, 15);


	}
	
	public static void setMtlPickup(boolean on){
		if (on){
			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.red);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
		}
		else {
			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.blue);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.lightgrey);
		}
		glMaterial(GL_FRONT, GL_SPECULAR, Graphics.red);
		glMaterialf(GL_FRONT, GL_SHININESS, 2f);
	}
}


