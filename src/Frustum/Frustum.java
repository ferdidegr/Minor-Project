package Frustum;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import Utils.Vector;

public class Frustum {
	private Plane pnear, pfar, pbottom, ptop, pleft, pright;					// Planes
	private float 	m11, m12, m13, m14,
					m21, m22, m23, m24,
					m31, m32, m33, m34, 
					m41, m42, m43, m44;
	
	public void update(FloatBuffer Projection, FloatBuffer ModelView){
		FloatBuffer M = BufferUtils.createFloatBuffer(16);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
		glMultMatrix(ModelView);
//		glMultMatrix(Projection);
		glGetFloat(GL_MODELVIEW_MATRIX, M);
		glPopMatrix();
		
		m11 = M.get(0); m12 = M.get(4);m13 = M.get(8);m14 = M.get(12);
		m21 = M.get(1); m22 = M.get(5);m23 = M.get(9);m24 = M.get(13);
		m31 = M.get(2); m32 = M.get(6);m33 = M.get(10);m34 = M.get(14);
		m41 = M.get(3); m42 = M.get(7);m43 = M.get(11);m41 = M.get(15);
		
		pleft = new Plane(m41+m11, m42+m12, m43+m13, m44+m14);
		pleft.normalize();
	}
	
	public boolean isFrustum(Vector location){
		System.out.println(pleft.signDist(location));
		if(pleft.signDist(location)>4) return true;
		return false;
	}
}
