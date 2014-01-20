package Utils;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;


public class Utils {
	/**
	 * Work around to set a normal vector using a array
	 * @param vec array with the components of the normal vector
	 */
	public static void glNormalvec(double[] vec){
		glNormal3d(vec[0], vec[1], vec[2]);
	}
	/**
	 * Work around to set a vertex vector using a array
	 * @param vec array with the components of the vertex vector
	 */
	public static void glVertvec(double[] vec){
		glVertex3d(vec[0], vec[1], vec[2]);
	}
	/**
	 * Quick way to create a FloatBuffer
	 * @param values You can enter as many floats as you like (separated by commas)
	 * @return FloatBuffer containing your floats
	 */
	public static FloatBuffer createFloatBuffer(float... values) {
		return (FloatBuffer) BufferUtils.createFloatBuffer(values.length). put(values).flip(); 
	}
	/**
	 * Load all file names in the folder level
	 * @return a sorted arraylist with the levelnames
	 */
	public static ArrayList<String> loadLevelList(){
		ArrayList<String> MazeList = new ArrayList<String>();
		String currentdir = System.getProperty("user.dir");		

		String[] files = new File(currentdir+"/"+"levels").list();
		String[] custfiles = new File(currentdir+"/"+"customlevels").list();
		// Load standard levels
		for(String name:files){
			if(name.toLowerCase().endsWith(".maze")){
				MazeList.add("levels/"+name);
			}
		}
		// Sort standard levels
		Collections.sort(MazeList, new NaturalOrderComparator());
		
		// Load custom levels, you dont care about the order
		for(String name:custfiles){
			if(name.toLowerCase().endsWith(".maze")){
				MazeList.add("customlevels/"+name);
			}
		}		
		
		return MazeList;
	}
	/**
	 * Goes through all pixels on your screen and converts it into a PNG file
	 */
	public static void makeScreenShot(){
		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer );
		
		File file = new File("screenshots"+"/"+"screenshot-"+getTime.getCurrentTimeStamp("YYYMMddHHmmss")+".png"); // The file to save to.
		if(!file.exists()){file.mkdirs();}
		String format = "PNG"; // Example: "PNG" or "JPG"
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		  
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++)
			{
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}
		  
		try {
			ImageIO.write(image, format, file);			
		} catch (IOException e) { }
			
	}
}
