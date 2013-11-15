package Editor;
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.Serializable;

import org.newdawn.slick.opengl.Texture;


public class MazeMap implements Serializable{
	/**
	 * SerialUID
	 */
	private static final long serialVersionUID = 2851020050736279902L;
	private int width;
	private int height;
	private int[][] maze;
	private static float size;
	private Texture texempty;
	/**
	 * constructor
	 * @param width
	 * @param height
	 * @throws IOException 
	 */
	public MazeMap(int width, int height) {
		setHeight(height);
		setWidth(width);
		maze = new int[height][width];
		
		try {
			texempty = IO.readtexture("res/empty.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * set width
	 * @param width
	 */
	public void setWidth(int width){
		this.width = width;
	}
	/**
	 * set height
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
	}
	
	
	public int getMazeX(float x){
		return (int)(x/size);
	}
	
	public int getMazeY(float y){
		return (int)((height*size-y)/size);
	}
	
	public void draw(){
		Textures.texempty.bind();
		glEnable(GL_TEXTURE_2D);
		//glBindTexture(GL_TEXTURE_2D, texempty.getTextureID());
		glBegin(GL_QUADS);
		for(int j = maze.length-1;j>=0;j--){
			for(int i = 0; i<maze[0].length;i++){
				
				glVertex2f(0+i*size, 0+j*size);
				glTexCoord2d(0, 0);
				glVertex2f(0+i*size+size, 0+j*size);
				glTexCoord2d(1, 0);
				glVertex2f(0+i*size+size, 0+j*size+size);
				glTexCoord2d(1, 1);
				glVertex2f(0+i*size, 0+j*size+size);
				glTexCoord2d(0, 1);
			}
		}
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}
	
	public void setMaze(int[][] maze){
		this.maze = maze;
	}
	
	public static void setSize(float insize){
		size = insize;
	}
	/*
	 * Getters
	 */
	public int getWidth(){ return this.width;}
	public int getHeight(){ return this.height;}
	public int[][] getMaze(){ return this.maze;}
}
