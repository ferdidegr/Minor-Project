package Game;

import java.io.Serializable;

import static org.lwjgl.opengl.GL11.*;


public class MazeMap implements Serializable{
	/**
	 * SerialUID
	 */
	private static final long serialVersionUID = 2851020050736279902L;
	private int width;
	private int height;
	private int[][] maze;
	private static float size;
	/**
	 * constructor
	 * @param width
	 * @param height
	 */
	public MazeMap(int width, int height){
		setHeight(height);
		setWidth(width);
		maze = new int[height][width];
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
	
	public void draw(){
		glBegin(GL_QUADS);
		for(int j = maze.length-1;j>=0;j--){
			for(int i = 0; i<maze[0].length;i++){
				glVertex2f(0+i*size, 0+j*size);
				glVertex2f(0+i*size+size, 0+j*size);
				glVertex2f(0+i*size+size, 0+j*size+size);
				glVertex2f(0+i*size, 0+j*size+size);
			}
		}
		glEnd();
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
