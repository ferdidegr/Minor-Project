package Menu;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;

public class Textures {
		public static Texture start = null, startover = null, menubackground, cursor;
		
		static{
			try {
				start = IO.loadtexture("res/button_normal.png",false);
				startover = IO.loadtexture("res/button_over.png",false);
				menubackground = IO.loadtexture("res/background.png",false);
				cursor = IO.loadtexture("res/cursor.png", false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
