package ModelLoader;

import java.io.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.ResourceLoader;

public class OBJLoader {
	public static Model loadModel(File f) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		Model m = new Model();
		String line;
		Texture currentTexture = null;
		
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
	            m.textures.add(new Vector2f(x,y));
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
				m.faces.add(new Face(vertexIndices, textureIndices, normalIndices));
			}
		}
		reader.close();
		return m;
	}
}
