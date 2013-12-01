package Menu;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;

public class Textures {
		public static Texture start = null, startover = null, menubackground;
		
		static{
			try {
				start = IO.loadtexture("res/wall.jpg",false);
				startover = IO.loadtexture("res/transparent.png",false);
				menubackground = IO.loadtexture("res/Wooden_floor_original.jpg",false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
