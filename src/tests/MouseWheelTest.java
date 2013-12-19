package tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class MouseWheelTest {
	public static void main(String[] args) throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		while(!Display.isCloseRequested()){
			System.out.println(Mouse.isButtonDown(2));
			Display.update();Display.sync(60);
		}
	}
}
