package Utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Editor.MazeMap;


public class IO {
	/**
	 * read a maze file (which is an int[][])
	 * @param input
	 * @return int[][] containing the maze
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static int[][] readMaze(String input) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(input);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		int[][] maze = (int[][]) ois.readObject();
		
		ois.close();		
		return maze;
	}
	/**
	 * Write maze as a whole object
	 * @param output
	 * @param maze
	 * @throws IOException
	 */
	public static void writeMaze(String output, MazeMap maze) throws IOException{
		FileOutputStream fos = new FileOutputStream(output);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(maze.getMaze());
		oos.close();
	}
	/**
	 * opens a Jfilechooser to select a maze to load 
	 * @return an int[][] containing the maze
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static int[][] loadchooser() throws ClassNotFoundException, IOException{
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));		
		jfc.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze Files", "maze");
		jfc.addChoosableFileFilter(filter);
		int res = jfc.showOpenDialog(null);
		int[][] maze;
		if(res == JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			String input = file.getAbsolutePath();
			maze = readMaze(input);
			return maze;
		}
		throw new IOException("You pressed cancel or X");
	}
	/**
	 * Dialog to save your file
	 * @param maze
	 * @throws IOException
	 */
	public static void savechooser(MazeMap maze) throws IOException{
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		jfc.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze Files", "maze");
		jfc.addChoosableFileFilter(filter);
		int res = jfc.showSaveDialog(null);
		if(res == JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			String output = file.getAbsolutePath();
			if(output.toLowerCase().endsWith(".maze")){
				writeMaze(output, maze);
			}else{
				writeMaze(output+".maze", maze);
			}
		}
	}
	/**
	 * Load texture
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static Texture loadtexture(String input, boolean flip) throws IOException{
					
		if(input.toLowerCase().endsWith(".jpg")){return TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(input),flip);}
		if(input.toLowerCase().endsWith(".png")){return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(input),flip);}
		if(input.toLowerCase().endsWith(".bmp")){return TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream(input),flip);}
		throw new IOException();

	}
	
}