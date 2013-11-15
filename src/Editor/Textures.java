package Editor;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
	public static Texture texempty = null, texmenubar = null, texnewmaze = null, texwall = null
			, texsave = null, texload=null;
	
	static {
		try {
			texempty = IO.readtexture("res/empty.jpg");
			texmenubar = IO.readtexture("res/Wooden_floor_original.jpg");
			texnewmaze = IO.readtexture("res/newmaze.jpg");
			texwall = IO.readtexture("res/wall.jpg");
			texsave = IO.readtexture("res/save.jpg");
			texload = IO.readtexture("res/load.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
