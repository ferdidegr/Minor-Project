package Editor;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
	public static Texture texempty = null, texmenubar = null, texnewmaze = null, texwall = null;
	
	static {
		try {
			texempty = IO.readtexture("res/empty.jpg");
			texmenubar = IO.readtexture("res/Wooden_floor_original.jpg");
			texnewmaze = IO.readtexture("res/newmaze.jpg");
			texwall = IO.readtexture("res/wall.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
