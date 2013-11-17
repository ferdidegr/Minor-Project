package Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class IO {
	public static int[][] readMaze(String input) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(input);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		int[][] maze = (int[][]) ois.readObject();
		
		ois.close();		
		return maze;
	}

	/**
	 * Load texture
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static Texture readtexture(String input) throws IOException{
					
		return TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(input));

	}
	
}
