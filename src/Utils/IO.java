package Utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicFileChooserUI;

import org.newdawn.slick.opengl.PNGDecoder;
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
	public static int[][] loadchooser(boolean master) throws ClassNotFoundException, IOException{
		// Creates a File to the directory
		File dirToLock = new File(System.getProperty("user.dir")+(master?"/levels":"/customlevels"));
		// Create a new filesystemview which locks the chosen directory
		FileSystemView fsv = new SingleRootFileSystemView(dirToLock);
		// create the filechooser
		JFileChooser jfc = new JFileChooser(fsv);	
		// Disable the new folder button
		BasicFileChooserUI ui = (BasicFileChooserUI)jfc.getUI();
		ui.getNewFolderAction().setEnabled(false);
		// Disable selecting multiple files
		jfc.setMultiSelectionEnabled(false);
		// Filter on .maze files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze Files", "maze");
		jfc.addChoosableFileFilter(filter);
		// open dialog
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
	public static void savechooser(MazeMap maze, boolean master) throws IOException{
		// Creates a File to the directory
		File dirToLock = new File(System.getProperty("user.dir")+(master?"/levels":"/customlevels"));
		// Create a new filesystemview which locks the chosen directory
		FileSystemView fsv = new SingleRootFileSystemView(dirToLock);
		// create the filechooser
		JFileChooser jfc = new JFileChooser(fsv);	
		// Disable the new folder button
		BasicFileChooserUI ui = (BasicFileChooserUI)jfc.getUI();
		ui.getNewFolderAction().setEnabled(false);
		// Disable selecting multiple files
		jfc.setMultiSelectionEnabled(false);
		// Filter on .maze files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze Files", "maze");
		jfc.addChoosableFileFilter(filter);
		// open save dialog
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
	/**
	 * load the icon of the Display
	 * @param path path to the icon file
	 * @return
	 */
	public static ByteBuffer loadIcon(String path) {
        
        try(InputStream inputStream = new FileInputStream(path)) {
            PNGDecoder decoder = new PNGDecoder(inputStream);
            ByteBuffer bytebuf = ByteBuffer.allocateDirect(decoder.getWidth()*decoder.getHeight()*4);
            decoder.decode(bytebuf, decoder.getWidth()*4, PNGDecoder.RGBA);            
            bytebuf.flip();
            return bytebuf;
        }catch(IOException e){return null;}
    }
}
