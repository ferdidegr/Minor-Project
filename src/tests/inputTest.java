
package tests;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import Utils.InputField;
import Utils.Text;


public class inputTest {
	private InputField ipf, ipf2;
	
	public void start() throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(1280, 1024));
		Display.create();
		Display.setFullscreen(true);
		initGL();
		Text font = new Text("BEBAS.TTF");
		ipf = new InputField(12, 60, Display.getWidth()/4, Display.getHeight()/2,font,1f);
		ipf2 = new InputField(12, 30, 0, 0,font);
		ipf.centerX();
		ipf.setY((int) (Display.getHeight()*2f/3));
//		ipf2.centerScreen();
		
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			glRectd(0, 0, 50, 50);
			glBegin(GL_LINES);
			glVertex2d(0, 0);
			glVertex2d(0, 50);
			glEnd();
			
			ipf.poll();
			ipf2.poll();
			
			Display.update();Display.sync(60);
		}
		
	}
	

	
	public void initGL(){
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(),  Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glClearColor(0, 0, 0, 1);
	}
	
	public static void main(String[] args){
		try {
			new inputTest().start();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
}
