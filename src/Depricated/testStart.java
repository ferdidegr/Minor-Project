package Depricated;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Game.Mazerunner;

public class testStart {
	public static void main(String[] args) throws ClassNotFoundException, IOException, LWJGLException{
						
		Display.setDisplayMode(new DisplayMode(1280, 800));	
		Display.create();
		Mazerunner mr = new Mazerunner();
//		mr.start();	
	}
}
