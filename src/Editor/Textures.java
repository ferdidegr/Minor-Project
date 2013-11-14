package Editor;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
	public static Texture texempty = null;
	
	static {
		try {
			texempty = IO.readtexture("res/empty.jpg");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
