package ModelLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class LoadOBJ {
	Model m = null;

	public LoadOBJ(String location) {
		int objectDisplayList = glGenLists(1);
		glNewList(objectDisplayList, GL_COMPILE);
		
		try {
			m = loadModel(new File(location));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}	
		draw(m);
	}
	
	public static Model loadModel(File f) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		Model m = new Model();
		String line;
		
		while ((line = reader.readLine()) != null) { // goes through the file line by line
			if (line.startsWith("v ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				m.vertices.add(new Vector3f(x, y, z));
			} else if (line.startsWith("vn ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				m.normals.add(new Vector3f(x, y, z));
			} else if (line.startsWith("vt ")) {
			    float x = Float.valueOf(line.split(" ")[1]);
			    float y = Float.valueOf(line.split(" ")[2]);
			    m.texcoords.add(new Vector2f(x,y));
			} else if (line.startsWith("f ")) {
				Vector3f vertexIndices = new Vector3f(
						Float.valueOf(line.split(" ")[1].split("/")[0]) - 1, 
						Float.valueOf(line.split(" ")[2].split("/")[0]) - 1, 
						Float.valueOf(line.split(" ")[3].split("/")[0]) - 1);
				Vector3f textureIndices = new Vector3f(
						Float.valueOf(line.split(" ")[1].split("/")[1]) - 1, 
						Float.valueOf(line.split(" ")[2].split("/")[1]) - 1, 
						Float.valueOf(line.split(" ")[3].split("/")[1]) - 1);
				Vector3f normalIndices = new Vector3f(
						Float.valueOf(line.split(" ")[1].split("/")[2]) - 1, 
						Float.valueOf(line.split(" ")[2].split("/")[2]) - 1, 
						Float.valueOf(line.split(" ")[3].split("/")[2]) - 1);
				m.faces.add(new Face(vertexIndices, normalIndices));
			}
		}
		reader.close();
		return m;
	}
	
	public void draw(Model m) {
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
