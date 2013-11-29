package Utils;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

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
}
