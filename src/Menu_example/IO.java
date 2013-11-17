package Menu_example;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class IO {
	public static Texture loadtex(String input) throws IOException{
		if(input.toLowerCase().endsWith(".jpg")){return TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(input));}
		if(input.toLowerCase().endsWith(".png")){return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(input));}
		throw new IOException();
	}
}
