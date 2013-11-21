package Editor;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
	public static Texture texempty = null, texmenubar = null, texnewmaze = null, texwall1 = null,
			texwall2 = null,texwall3 = null,texwall4 = null,texwall5 = null,texwall6 = null,texwall7 = null
			, texsave = null, texload=null, texspike = null, texflaggreen=null, texflagred;
	
	static {
		try {
			texempty = IO.readtexture("res/empty.jpg");
			texmenubar = IO.readtexture("res/Wooden_floor_original.jpg");
			texnewmaze = IO.readtexture("res/newmaze.jpg");
			texwall1 = IO.readtexture("res/wall1.jpg");
			texwall2 = IO.readtexture("res/wall2.jpg");
			texwall3 = IO.readtexture("res/wall3.jpg");
			texwall4 = IO.readtexture("res/wall4.jpg");
			texwall5 = IO.readtexture("res/wall5.jpg");
			texwall6 = IO.readtexture("res/wall6.jpg");
			texwall7 = IO.readtexture("res/wall7.jpg");
			texsave = IO.readtexture("res/save.jpg");
			texload = IO.readtexture("res/load.jpg");
			texspike = IO.readtexture("res/spikes.jpg");
			texflaggreen= IO.readtexture("res/flaggreen.jpg");
			texflagred= IO.readtexture("res/flagred.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
