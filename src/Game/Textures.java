package Game;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;

public class Textures {
		public static Texture ingamewall, startover, ground
				, front, back, left,right,top, bottom;
		
		static{
			try {				
				ingamewall = IO.loadtexture("res/ingamewall.jpg",false);
				startover = IO.loadtexture("res/transparent.png",false);
				ground = IO.loadtexture("res/ground1.jpg",false);
				
				// Skybox
				front = IO.loadtexture("res/Skybox/Sahara/north.png",true);
				back = IO.loadtexture("res/Skybox/Sahara/south.png",true);
				left = IO.loadtexture("res/Skybox/Sahara/west.png",true);
				right = IO.loadtexture("res/Skybox/Sahara/east.png",true);
				top = IO.loadtexture("res/Skybox/Sahara/up.png",true);
				bottom = IO.loadtexture("res/Skybox/Sahara/down.png",true);
//				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
