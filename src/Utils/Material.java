package Utils;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureImpl;

import Game.Textures;
/**
 * Material settings for several game objects
 * @author ZL
 *
 */
public class Material {
	static FloatBuffer none = Utils.createFloatBuffer(0f,0f,0f,0f);
	/**
	 * Set aterial for the movable wall
	 */
	public static void setMtlMWall(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
		glMaterial(GL_FRONT, GL_SPECULAR, none);
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		Textures.movewall.bind();		
	}
	
	/**
	 * Set material for the wall
	 */
	public static void setMtlWall(){

			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
			glMaterial(GL_FRONT, GL_SPECULAR, none);
			Textures.ingamewall.bind();				

	}
	/**
	 * Set material for the ground
	 */
	public static void setMtlGround(){

			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
			glMaterial(GL_FRONT, GL_SPECULAR, none);
			Textures.ground.bind();		

	}
	/**
	 * Set material for the spikes
	 */
	public static void setMtlsteel(){

			glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.grey);
			glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
			glMaterial(GL_FRONT, GL_SPECULAR, Graphics.grey);
			glMaterialf(GL_FRONT, GL_SHININESS, 15);
			TextureImpl.bindNone();

	}

	/**
	 * set material for the hatch
	 */
	public static void setMtlHatch(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.white);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.darkgrey);
		glMaterial(GL_FRONT, GL_SPECULAR, none);
		Textures.hatch.bind();	
	}
	/**
	 * set material for the ground thickener
	 */
	public static void setMtlHole(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.grey);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.grey);
		glMaterial(GL_FRONT, GL_SPECULAR, none);
		Textures.ground.bind();
	}
	/**
	 * Set material for the C4
	 */
	public static void setMtlC4(){
		glMaterial( GL_FRONT, GL_DIFFUSE, Graphics.grey);
		glMaterial(	GL_FRONT, GL_AMBIENT, Graphics.grey);
		glMaterial(GL_FRONT, GL_SPECULAR, none);
		Textures.c4.bind();
	}
}


