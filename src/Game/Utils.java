package Game;

import static org.lwjgl.opengl.GL11.*;

public class Utils {
	public static void glNormalvec(double[] vec){
		glNormal3d(vec[0], vec[1], vec[2]);
	}
	
	public static void glVertvec(double[] vec){
		glVertex3d(vec[0], vec[1], vec[2]);
	}
}
