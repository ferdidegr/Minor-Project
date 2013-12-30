package Editor;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;

public class Textures {
	public static Texture texempty = null, texmenubar = null, texnewmaze = null, texwall1 = null,
			texwall2 = null,texwall3 = null,texwall4 = null,texwall5 = null,texwall6 = null,texwall7 = null
			, texsave = null, texload = null, texspike = null, texflaggreen = null, texflagred, scorpion, pit, hatch, texresize , texquit, movwall;
	
	static {
		try {
			texempty = IO.loadtexture("res/Textures/Editor/empty.jpg",false);
			texmenubar = IO.loadtexture("res/Textures/Editor/Wooden_floor_original.jpg",false);
			texnewmaze = IO.loadtexture("res/Textures/Editor/newmaze.jpg",false);
			texwall1 = IO.loadtexture("res/Textures/Editor/wall1.jpg",false);
			texwall2 = IO.loadtexture("res/Textures/Editor/wall2.jpg",false);
			texwall3 = IO.loadtexture("res/Textures/Editor/wall3.jpg",false);
			texwall4 = IO.loadtexture("res/Textures/Editor/wall4.jpg",false);
			texwall5 = IO.loadtexture("res/Textures/Editor/wall5.jpg",false);
			texwall6 = IO.loadtexture("res/Textures/Editor/wall6.jpg",false);
			texwall7 = IO.loadtexture("res/Textures/Editor/wall7.jpg",false);
			texsave = IO.loadtexture("res/Textures/Editor/save.jpg",false);
			texload = IO.loadtexture("res/Textures/Editor/load.jpg",false);
			texspike = IO.loadtexture("res/Textures/Editor/spikes.jpg",false);
			texflaggreen= IO.loadtexture("res/Textures/Editor/flaggreen.jpg",false);
			texflagred= IO.loadtexture("res/Textures/Editor/flagred.jpg",false);
			scorpion = IO.loadtexture("res/Textures/Editor/scorpion.jpg",false);
			pit = IO.loadtexture("res/Textures/Editor/pitt.jpg",false);
			hatch = IO.loadtexture("res/Textures/Editor/hatch.jpg",false);
			texresize = IO.loadtexture("res/Textures/Editor/resize.jpg", false);
			texquit = IO.loadtexture("res/Textures/Editor/quit.png"	, false	);
			movwall = IO.loadtexture("res/Textures/Editor/movwall.jpg", false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
