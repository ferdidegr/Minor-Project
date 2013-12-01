package Game;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import Utils.IO;

public class Textures {
		public static Texture ingamewall = null, startover = null, ground
				, front = null, back=null, left=null,right=null,top=null, bottom=null;
		
		static{
			try {
				ingamewall = IO.loadtexture("res/ingamewall.jpg",false);
				startover = IO.loadtexture("res/transparent.png",false);
				ground = IO.loadtexture("res/ground1.jpg",false);
				/*
				 * Comment all except one
				 */
				//#1
//				front = IO.loadtexture("res/Skybox/Skybox-Front.bmp",true);
//				back = IO.loadtexture("res/Skybox/Skybox-Back.bmp",true);
//				left = IO.loadtexture("res/Skybox/Skybox-Left.bmp",true);
//				right = IO.loadtexture("res/Skybox/Skybox-Right.bmp",true);
//				top = IO.loadtexture("res/Skybox/Skybox-Top.bmp",true);
//				bottom = IO.loadtexture("res/Skybox/Skybox-Bottom.bmp",true);
////				#2
//				front = IO.loadtexture("res/Skybox/anachorism/anachronismbk.bmp",true);
//				back = IO.loadtexture("res/Skybox/anachorism/anachronismft.bmp",true);
//				left = IO.loadtexture("res/Skybox/anachorism/anachronismlf.bmp",true);
//				right = IO.loadtexture("res/Skybox/anachorism/anachronismrt.bmp",true);
//				top = IO.loadtexture("res/Skybox/anachorism/anachronismup.bmp",true);
//				bottom = IO.loadtexture("res/Skybox/anachorism/anachronismdn.bmp",true);
////				# 3 Sun set
//				front = IO.loadtexture("res/Skybox/SunSetFront1024.png",true);
//				back = IO.loadtexture("res/Skybox/SunSetBack1024.png",true);
//				left = IO.loadtexture("res/Skybox/SunSetLeft1024.png",true);
//				right = IO.loadtexture("res/Skybox/SunSetRight1024.png",true);
//				top = IO.loadtexture("res/Skybox/SunSetUp1024.png",true);
//				bottom = IO.loadtexture("res/Skybox/SunSetDown1024.png",true);
				// # 4 Sahara
				front = IO.loadtexture("res/Skybox/Sahara/north.bmp",true);
				back = IO.loadtexture("res/Skybox/Sahara/south.bmp",true);
				left = IO.loadtexture("res/Skybox/Sahara/west.bmp",true);
				right = IO.loadtexture("res/Skybox/Sahara/east.bmp",true);
				top = IO.loadtexture("res/Skybox/Sahara/up.bmp",true);
				bottom = IO.loadtexture("res/Skybox/Sahara/down.bmp",true);
//				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
