package Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.GL11.*;
/**
 * Test for loading models
 * @author ZL
 *
 */
public class Modelviewer2 {
	private float horangle=0;
	private float verangle=0;
	private float dX = 0, dY=0, dZ=0;
	private List<Integer> list = new ArrayList<Integer>();
	private int j=0;
	
	public void start() throws LWJGLException{
		
		Display.setDisplayMode(Display.getDesktopDisplayMode());
		Display.setFullscreen(false);
		Display.setVSyncEnabled(true);
		Display.create();
		
		int i=1;
		String file = "scorpion_000001.obj";
		DecimalFormat df = new DecimalFormat("000000");
		
		while (i <= 20) {
			try {
				int drawlist = Model.loadModel("res/Models/Scorpion/",file).generateDList();
				list.add(drawlist);
				i++;
				file = "scorpion_" + df.format(i) + ".obj";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		initGL();
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			
			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
			
			glPushMatrix();
			glTranslatef(dX, dY, dZ);
			glRotatef(horangle, 0, 1, 0);
			glRotatef(verangle, 1, 0, 0);
						
			int buffer = list.get(j % 20);
			glCallList(buffer);
						
			glPushAttrib(GL_ENABLE_BIT);
			glDisable(GL_LIGHTING);
			glColor3f(1f, 1f, 1f);
			glLineWidth(10f);
			glBegin(GL_LINE_LOOP);
			glVertex3d(-0.5, 0.5,-0.5);
			glVertex3d(-0.5, 0.5,0.5);
			glVertex3d(0.5, 0.5,0.5);
			glVertex3d(0.5, 0.5,-0.5);
			
			glVertex3d(0.5, -0.5,-0.5);
			glVertex3d(-0.5, -0.5,-0.5);
			glVertex3d(-0.5, -0.5,0.5);
			glVertex3d(0.5, -0.5,0.5);
			
			glEnd();
			glPopAttrib();
			glPopMatrix();
			
			glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0).put(0).put(-10).put(1).flip());
			inputpoll();
			
			Display.update();
			Display.sync(60);
			j++;
		}
	}
	
	public void CallList(List<Integer> lijst) {
		int i=0;
		while (i < 40) {
			int buffer = list.get(i);
			glCallList(buffer);
			i++;
		}
	}
	/**
	 * Poll for Keyinput
	 */
	public void inputpoll(){
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){horangle+=.5;}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){horangle-=.5;}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){verangle +=.5;}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){verangle -=.5;}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){dZ -=.1;}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){dZ +=.1;}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){dX -=.1;}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){dX += .1;}

	}
	/**
	 * Initialize openGL
	 */
	public void initGL(){
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		// Initialize matrices
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60, (float)Display.getWidth()/Display.getHeight(), 0.001f, 100f);
		glMatrixMode(GL_MODELVIEW);
		
		// Cullface
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		
		//depth
		glEnable(GL_DEPTH_TEST);
		
	     // Set the shading model.
        glShadeModel( GL_SMOOTH );
        
		glClearDepth(1.0f);			
		glDepthFunc(GL_LEQUAL);
		//
		glEnable(GL_LIGHT0);
		glEnable(GL_LIGHTING);
		
		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(0).put(0).put(-10).put(1).flip());
		
		
		gluLookAt(0, 0, -10, 0, 0, 0, 0, 1, 0);
	}
	
	public File choosefile() throws IOException{
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));		
		jfc.setMultiSelectionEnabled(false);
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("OBJ Files", "obj");
		jfc.addChoosableFileFilter(filter);
		int res = jfc.showOpenDialog(null);
		
		if(res == JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			return file;
			
		}
		throw new IOException("You pressed cancel or X");
	}
	
	/**
	 * Main program 
	 * @param args
	 */
	public static void main(String[] args){
		try {
			new Modelviewer2().start();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
}
