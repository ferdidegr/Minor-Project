package Editor;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
	public static Texture texempty = null, texmenubar = null, texnewmaze = null, texwall1 = null,
			texwall2 = null,texwall3 = null,texwall4 = null,texwall5 = null,texwall6 = null,texwall7 = null
			, texsave = null, texload=null, texspike = null, texflaggreen=null, texflagred, scorpion;
	
	static {
		try {
			texempty = IO.readtexture("res/Textures/Editor/empty.jpg");
			texmenubar = IO.readtexture("res/Textures/Editor/Wooden_floor_original.jpg");
			texnewmaze = IO.readtexture("res/Textures/Editor/newmaze.jpg");
			texwall1 = IO.readtexture("res/Textures/Editor/wall1.jpg");
			texwall2 = IO.readtexture("res/Textures/Editor/wall2.jpg");
			texwall3 = IO.readtexture("res/Textures/Editor/wall3.jpg");
			texwall4 = IO.readtexture("res/Textures/Editor/wall4.jpg");
			texwall5 = IO.readtexture("res/Textures/Editor/wall5.jpg");
			texwall6 = IO.readtexture("res/Textures/Editor/wall6.jpg");
			texwall7 = IO.readtexture("res/Textures/Editor/wall7.jpg");
			texsave = IO.readtexture("res/Textures/Editor/save.jpg");
			texload = IO.readtexture("res/Textures/Editor/load.jpg");
			texspike = IO.readtexture("res/Textures/Editor/spikes.jpg");
			texflaggreen= IO.readtexture("res/Textures/Editor/flaggreen.jpg");
			texflagred= IO.readtexture("res/Textures/Editor/flagred.jpg");
			scorpion = IO.readtexture("res/Textures/Editor/scorpion.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
