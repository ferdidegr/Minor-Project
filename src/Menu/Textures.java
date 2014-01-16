package Menu;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;

public class Textures {
		public static Texture start = null, startover = null, menubackground, cursor, help1, help2, help3, help4, help5, help6,help7, help8;
		
		static{
			try {
				start = IO.loadtexture("res/button_normal.png",false);
				startover = IO.loadtexture("res/button_over.png",false);
				menubackground = IO.loadtexture("res/background.png",false);
				cursor = IO.loadtexture("res/cursor.png", false);
				help1 = IO.loadtexture("res/Help/1.png", false);
				help2 = IO.loadtexture("res/Help/2.png", false);
				help3 = IO.loadtexture("res/Help/3.png", false);
				help4 = IO.loadtexture("res/Help/4.png", false);
				help5 = IO.loadtexture("res/Help/5.png", false);
				help6 = IO.loadtexture("res/Help/6.png", false);
				help7 = IO.loadtexture("res/Help/7.png", false);
				help8 = IO.loadtexture("res/Help/8.png", false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
