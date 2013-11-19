package Menu;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
		public static Texture start = null, startover = null, menubackground;
		
		static{
			try {
				start = IO.loadtex("res/wall.jpg");
				startover = IO.loadtex("res/transparent.png");
				menubackground = IO.loadtex("res/Wooden_floor_original.jpg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
