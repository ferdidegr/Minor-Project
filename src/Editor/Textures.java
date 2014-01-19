package Editor;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;
/**
 * By creating the static block, all resources will be available once it is loaded in the begin
 * 
 * @author ZL
 *
 */
public class Textures {
	public static Texture texempty = null, texmenubar = null, texnewmaze = null, texwall1 = null,
			texwall2 = null,texwall3 = null,texwall4 = null,texwall5 = null,texwall6 = null,texwall7 = null
			, texsave = null, texload = null, texspike = null, texstart = null, texend,
			scorpion, pit, hatch, texresize , texquit, movwalldown, movwallup, mazebg, texshift;
	
	static {
		try {
			texempty = IO.loadtexture("res/Textures/Editor/empty.jpg",false);
			texshift = IO.loadtexture("res/Textures/Editor/shift.jpg",false);
			texmenubar = IO.loadtexture("res/sandwall.png",false);
			texnewmaze = IO.loadtexture("res/Textures/Editor/newmaze.jpg",false);
			texwall1 = IO.loadtexture("res/Textures/Editor/wall1.png",false);
			texwall2 = IO.loadtexture("res/Textures/Editor/wall2.png",false);
			texwall3 = IO.loadtexture("res/Textures/Editor/wall3.png",false);
			texwall4 = IO.loadtexture("res/Textures/Editor/wall4.png",false);
			texwall5 = IO.loadtexture("res/Textures/Editor/wall5.png",false);
			texwall6 = IO.loadtexture("res/Textures/Editor/wall6.png",false);
			texwall7 = IO.loadtexture("res/Textures/Editor/wall7.png",false);
			texsave = IO.loadtexture("res/Textures/Editor/save.jpg",false);
			texload = IO.loadtexture("res/Textures/Editor/load.jpg",false);
			texspike = IO.loadtexture("res/Textures/Editor/spikes.jpg",false);
			texstart = IO.loadtexture("res/Textures/Editor/start.png",false);
			texend = IO.loadtexture("res/Textures/Editor/end.png",false);
			scorpion = IO.loadtexture("res/Textures/Editor/scorpion.jpg",false);
			pit = IO.loadtexture("res/Textures/Editor/pitt.jpg",false);
			hatch = IO.loadtexture("res/Textures/Editor/hatch.jpg",false);
			texresize = IO.loadtexture("res/Textures/Editor/resize.jpg", false);
			texquit = IO.loadtexture("res/Textures/Editor/quit.png"	, false	);
			movwalldown = IO.loadtexture("res/Textures/Editor/movwalldown.jpg", false);
			movwallup = IO.loadtexture("res/Textures/Editor/movwallup.jpg", false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
