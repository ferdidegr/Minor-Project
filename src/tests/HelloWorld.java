package tests;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class HelloWorld {

	public HelloWorld() {
		try {
			Display.setDisplayMode(new DisplayMode(640,480));
			Display.setTitle("Hello World");
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
		}
		
		// Initialization Code OpenGL
		
		// Renderloop
		while (!Display.isCloseRequested()) {
			// Render
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}
	public static void main(String[] args) {
		new HelloWorld();

	}

}
