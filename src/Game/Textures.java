package Game;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
		public static Texture ingamewall = null, startover = null, ground
				, front = null, back=null, left=null,right=null,top=null, bottom=null;
		
		static{
			try {
				ingamewall = IO.loadtex("res/ingamewall.jpg",false);
				startover = IO.loadtex("res/transparent.png",false);
				ground = IO.loadtex("res/ground1.jpg",false);
				/*
				 * Comment all except one
				 */
				//#1
//				front = IO.loadtex("res/Skybox/Skybox-Front.bmp",true);
//				back = IO.loadtex("res/Skybox/Skybox-Back.bmp",true);
//				left = IO.loadtex("res/Skybox/Skybox-Left.bmp",true);
//				right = IO.loadtex("res/Skybox/Skybox-Right.bmp",true);
//				top = IO.loadtex("res/Skybox/Skybox-Top.bmp",true);
//				bottom = IO.loadtex("res/Skybox/Skybox-Bottom.bmp",true);
				//#2
//				front = IO.loadtex("res/Skybox/anachorism/anachronismbk.bmp",true);
//				back = IO.loadtex("res/Skybox/anachorism/anachronismft.bmp",true);
//				left = IO.loadtex("res/Skybox/anachorism/anachronismlf.bmp",true);
//				right = IO.loadtex("res/Skybox/anachorism/anachronismrt.bmp",true);
//				top = IO.loadtex("res/Skybox/anachorism/anachronismup.bmp",true);
//				bottom = IO.loadtex("res/Skybox/anachorism/anachronismdn.bmp",true);
				//# 3 Sun set
//				front = IO.loadtex("res/Skybox/SunSetFront1024.png",true);
//				back = IO.loadtex("res/Skybox/SunSetBack1024.png",true);
//				left = IO.loadtex("res/Skybox/SunSetLeft1024.png",true);
//				right = IO.loadtex("res/Skybox/SunSetRight1024.png",true);
//				top = IO.loadtex("res/Skybox/SunSetUp1024.png",true);
//				bottom = IO.loadtex("res/Skybox/SunSetDown1024.png",true);
				// # 4 Sahara
				front = IO.loadtex("res/Skybox/Sahara/north.bmp",true);
				back = IO.loadtex("res/Skybox/Sahara/south.bmp",true);
				left = IO.loadtex("res/Skybox/Sahara/west.bmp",true);
				right = IO.loadtex("res/Skybox/Sahara/east.bmp",true);
				top = IO.loadtex("res/Skybox/Sahara/up.bmp",true);
				bottom = IO.loadtex("res/Skybox/Sahara/down.bmp",true);
//				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
