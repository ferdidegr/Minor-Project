package tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class DisplayTest {
	public static void main(String[] args){
		try {
			Display.setDisplayMode(new DisplayMode(1280, 1024));
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
//			glLineWidth(3);
//			glBegin(GL_LINES);
//			
//			glColor3f(0	,1, 0);
//			glVertex2d(0, 1);
//			glVertex2d(0, 500);
//			glEnd();
			
			glLineWidth(1.5f);
			glBegin(GL_LINES);
			
			glColor3f(1, 0, 0);
			glVertex2d(0, 50);
			glVertex2d(0, 500);
			glEnd();
//			glRectd(0, 0, 200, 200);
			Display.update();
		}
	}
}
