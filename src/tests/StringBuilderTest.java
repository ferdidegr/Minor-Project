package tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class StringBuilderTest {
	public void start() throws LWJGLException{
		StringBuilder sb = new StringBuilder();
		
		Display.create();
		
		while(!Display.isCloseRequested()){
			System.out.println(sb);
		}
		
		
	}
	
	public static void main(String[] args){
		try {
			new StringBuilderTest().start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
