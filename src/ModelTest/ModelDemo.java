package ModelTest;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.*;

public class ModelDemo {

	public ModelDemo() {
		try {
			Display.setDisplayMode(new DisplayMode(640,480));
			Display.setTitle("Bunny");
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
		}
		
		// Initialization Code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		int objectDisplayList = glGenLists(1);
        glNewList(objectDisplayList, GL_COMPILE);
        {
            Model m = null;
            try {
            	m = OBJLoader.loadModel(new File("src/ModelTest/bunny.obj"));
            } catch (FileNotFoundException e) {
            	e.printStackTrace();
            	Display.destroy();
            	System.exit(1);
            } catch (IOException e) {
            	e.printStackTrace();
            	Display.destroy();
            	System.exit(1);
            }
            glBegin(GL_TRIANGLES);
            for (Face face : m.faces) {
            	Vector3f n1 = m.normals.get((int) face.normal.x - 1);
            	glNormal3f(n1.x, n1.y, n1.z);
            	Vector3f v1 = m.vertices.get((int) face.vertex.x - 1);
            	glVertex3f(v1.x, v1.y, v1.z);
            	Vector3f n2 = m.normals.get((int) face.normal.y - 1);
            	glNormal3f(n2.x, n2.y, n2.z);
            	Vector3f v2 = m.vertices.get((int) face.vertex.y - 1);
            	glVertex3f(v2.x, v2.y, v2.z);
            	Vector3f n3 = m.normals.get((int) face.normal.z - 1);
            	glNormal3f(n3.x, n3.y, n3.z);
            	Vector3f v3 = m.vertices.get((int) face.vertex.z - 1);
            	glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        
		// Render loop
		while (!Display.isCloseRequested()) {
			// Render
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Last one only for 3D
			
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // if removed, makes it a mesh
			
			glCallList(objectDisplayList);
			
			Display.update();
			Display.sync(60);
		}
		glDeleteLists(objectDisplayList, 1);
		Display.destroy();
		System.exit(0);
	}
	public static void main(String[] args) {
		new ModelDemo();
	}

}
