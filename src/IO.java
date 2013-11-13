import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class IO {
	public static MazeMap readMaze(String input) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(input);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		MazeMap maze = (MazeMap) ois.readObject();
		
		ois.close();
		
		return maze;
	}
	
	public static void writeMaze(String output, MazeMap maze) throws IOException{
		FileOutputStream fos = new FileOutputStream(output);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(maze);
		oos.close();
	}
	
	public static Texture readtexture(String input) throws IOException{
		/*
		 * Load floor texture
		 */
				
		return TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(input));

	}
	
}
