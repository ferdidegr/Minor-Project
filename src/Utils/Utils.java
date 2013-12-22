package Utils;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;


public class Utils {
	public static void glNormalvec(double[] vec){
		glNormal3d(vec[0], vec[1], vec[2]);
	}
	
	public static void glVertvec(double[] vec){
		glVertex3d(vec[0], vec[1], vec[2]);
	}
	
	public static FloatBuffer createFloatBuffer(float... values) {
		return (FloatBuffer) BufferUtils.createFloatBuffer(values.length). put(values).flip(); 
	}
	
	public static ArrayList<String> loadLevelList(){
		ArrayList<String> MazeList = new ArrayList<String>();
		String currentdir = System.getProperty("user.dir");		
		String[] files = new File(currentdir+"\\levels").list();
		
		for(String name:files){
			if(name.toLowerCase().endsWith(".maze")){
				MazeList.add(name);
			}
		}
		
		return MazeList;
	}
	
	public static void makeScreenShot(){
		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer );
		
		File file = new File("screenshots//screenshot-"+getTime.getCurrentTimeStamp("YYYMMddHHmmss")+".png"); // The file to save to.
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
