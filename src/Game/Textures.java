package Game;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;

public class Textures {
		public static Texture ingamewall, ground
				, front, back, left,right,top, bottom, movewall, hatch, c4;
		
		static{
			try {				
				ingamewall = IO.loadtexture("res/ingamewall.jpg",false);

				ground = IO.loadtexture("res/ground1.jpg",false);
				movewall  = IO.loadtexture("res/sandwall.png", false);
				hatch = IO.loadtexture("res/Wooden_floor_original.jpg", false);
				c4 = IO.loadtexture("res/C4.png", false);
				
				// Skybox
				front = IO.loadtexture("res/Skybox/Sahara/north.png",true);
				back = IO.loadtexture("res/Skybox/Sahara/south.png",true);
				left = IO.loadtexture("res/Skybox/Sahara/west.png",true);
				right = IO.loadtexture("res/Skybox/Sahara/east.png",true);
				top = IO.loadtexture("res/Skybox/Sahara/up.png",true);
				bottom = IO.loadtexture("res/Skybox/Sahara/down.png",true);			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
