package Utils;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

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
}
