package tests;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class TextureTest {
	public void start() throws IOException{
		try{
			Display.setDisplayMode(new DisplayMode(1280, 1024));
			Display.create();
		}catch(LWJGLException e){
			System.exit(1);
		}
		
		Texture temp = Utils.IO.loadtexture("res/test.png", false);
		
		initGL();
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			glRectd(50, 50, 500, 500);
			glBegin(GL_QUADS);
			glTexCoord2d(0, 0); glVertex2d(50, 50);
			glTexCoord2d(0, 1); glVertex2d(50, 500);
			glTexCoord2d(1, 1); glVertex2d(500, 500);
			glTexCoord2d(1, 0); glVertex2d(500, 50);
			glEnd();
			
			
			Display.update();
			Display.sync(60);
		}
	}
	

	
	public void initGL(){
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void main(String[] args){
		try {
			new TextureTest().start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
