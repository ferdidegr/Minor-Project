package Game;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

public class Textures {
		public static Texture start = null, startover = null, menubackground
				, front = null, back=null, left=null,right=null,top=null, bottom=null;
		
		static{
			try {
				start = IO.loadtex("res/wall.jpg");
				startover = IO.loadtex("res/transparent.png");
				menubackground = IO.loadtex("res/Wooden_floor_original.jpg");
				/*
				 * Comment all except one
				 */
				//#1
				front = IO.loadtex("res/Skybox/Skybox-Front.bmp");
				back = IO.loadtex("res/Skybox/Skybox-Back.bmp");
				left = IO.loadtex("res/Skybox/Skybox-Left.bmp");
				right = IO.loadtex("res/Skybox/Skybox-Right.bmp");
				top = IO.loadtex("res/Skybox/Skybox-Top.bmp");
				bottom = IO.loadtex("res/Skybox/Skybox-Bottom.bmp");
				//#2
//				front = IO.loadtex("res/Skybox/anachronismbk.bmp");
//				back = IO.loadtex("res/Skybox/anachronismft.bmp");
//				left = IO.loadtex("res/Skybox/anachronismlf.bmp");
//				right = IO.loadtex("res/Skybox/anachronismrt.bmp");
//				top = IO.loadtex("res/Skybox/anachronismup.bmp");
//				bottom = IO.loadtex("res/Skybox/anachronismdn.bmp");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
