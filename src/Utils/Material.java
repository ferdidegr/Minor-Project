package Utils;
import static org.lwjgl.opengl.GL11.*;
import Game.Textures;

public class Material {
	public static void setMtlWall(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
		glMaterial(GL_FRONT, GL_SPECULAR, Utils.createFloatBuffer(0f,0f,0f,0f));
		Textures.ingamewall.bind();		
	}
	
	public static void setMtlGround(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
		glMaterial(GL_FRONT, GL_SPECULAR, Utils.createFloatBuffer(0f,0f,0f,0f));
		Textures.ground.bind();		
	}
	
	public static void setMtlsteel(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.grey);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
		glMaterial(GL_FRONT, GL_SPECULAR, Utils.createFloatBuffer(0.5f,0.5f,0.5f,1f));
		glMaterialf(GL_FRONT, GL_SHININESS, 15);
	}
	
	public static void setMtlScorp(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.red);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
		glMaterial(GL_FRONT, GL_SPECULAR, Utils.createFloatBuffer(1f,0f,0f,0f));
		glMaterialf(GL_FRONT, GL_SHININESS, 2f);
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
		glMaterial(GL_FRONT, GL_SPECULAR, Utils.createFloatBuffer(1f,0f,0f,0f));
		glMaterialf(GL_FRONT, GL_SHININESS, 2f);
	}
}
