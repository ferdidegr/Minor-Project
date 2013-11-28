package ModelLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

public class Object3D {
	Model m = null;

	public Object3D(File f) {
		int objectDisplayList = glGenLists(1);
		glNewList(objectDisplayList, GL_COMPILE);
		
		try {
			m = OBJLoader.loadModel(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}	
		draw();
	}
	
	public void draw() {
		glBegin(GL_TRIANGLES);
		
        for (Face face : m.getFaces()) {
        	Vector3f n1 = m.getNormals().get((int) face.getNormal().x);
        	glNormal3f(n1.x, n1.y, n1.z);
        	Vector3f v1 = m.getVertices().get((int) face.getVertex().x);
        	glVertex3f(v1.x, v1.y, v1.z);
        	
        	Vector3f n2 = m.getNormals().get((int) face.getNormal().y);
        	glNormal3f(n2.x, n2.y, n2.z);
        	Vector3f v2 = m.getVertices().get((int) face.getVertex().y);
        	glVertex3f(v2.x, v2.y, v2.z);
        	
        	Vector3f n3 = m.getNormals().get((int) face.getNormal().z);
        	glNormal3f(n3.x, n3.y, n3.z);
        	Vector3f v3 = m.getVertices().get((int) face.getVertex().z);
        	glVertex3f(v3.x, v3.y, v3.z);
        }
        glEnd();
        glEndList();
	}
	
	public void display() {
	//	glTranslatef(SQUARE_SIZE * 2, SQUARE_SIZE, SQUARE_SIZE * 2);
	//	glScalef(0.1f, 0.1f, 0.1f);
		
	}
}
