package Editor;
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.Serializable;

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
	 * @throws IOException 
	 */
	public MazeMap(int width, int height) {
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
	
	
	public int getMazeX(float x){
		return (int)Math.floor(x/size);
	}
	
	public int getMazeY(float y){
		return (int)Math.floor((height*size-y)/size);
	}
	/**
	 * Reserved ID's
	 * 0 - No wall
	 * 1-10; Wall(corresponding height)
	 * 11 - Begin point
	 * 12 - End point
	 * 13 - Spikes
	 */
	public void draw(){
		
		glEnable(GL_TEXTURE_2D);
		//glBindTexture(GL_TEXTURE_2D, texempty.getTextureID());
		
		for(int j = maze.length-1;j>=0;j--){
			for(int i = 0; i<maze[0].length;i++){
				if (maze[j][i]==0){Textures.texempty.bind();}
				if (maze[j][i]==1){Textures.texwall1.bind();}
				if (maze[j][i]==2){Textures.texwall2.bind();}
				if (maze[j][i]==3){Textures.texwall3.bind();}
				if (maze[j][i]==4){Textures.texwall4.bind();}
				if (maze[j][i]==5){Textures.texwall5.bind();}
				if (maze[j][i]==6){Textures.texwall6.bind();}
				if (maze[j][i]==7){Textures.texwall7.bind();}				
				if (maze[j][i]==11){Textures.texflaggreen.bind();}
				if (maze[j][i]==12){Textures.texflagred.bind();}
				if (maze[j][i]==13){Textures.texspike.bind();}
				if (maze[j][i]==14){Textures.scorpion.bind();}
				if (maze[j][i]==15){Textures.pit.bind();}
				if (maze[j][i]==16){Textures.hatch.bind();}
				glBegin(GL_QUADS);				
				glTexCoord2d(0, 1);
				glVertex2f(0+i*size, height*size-(j+1)*size);
				glTexCoord2d(1, 1);
				glVertex2f(0+i*size+size, height*size-(j+1)*size);
				glTexCoord2d(1, 0);
				glVertex2f(0+i*size+size, height*size-(j+1)*size+size);
				glTexCoord2d(0, 0);
				glVertex2f(0+i*size, height*size-(j+1)*size+size);
				glEnd();
			}
		}
		
		glDisable(GL_TEXTURE_2D);
	}
	
	public void setObject(int ID, float x , float y){
		int xloc = getMazeX(x);
		int yloc = getMazeY(y);
		if(xloc>=0 && xloc<width && yloc >= 0 && yloc<height)
			maze[yloc][xloc] = ID;
	}
	
	public void setMaze(int[][] maze){
		this.maze = maze;
	}
	
	public static void setSize(float insize){
		size = insize;
	}
	
	public static float getSize(){
		return size;
	}
	/*
	 * Getters
	 */
	public int getWidth(){ return this.width;}
	public int getHeight(){ return this.height;}
	public int[][] getMaze(){ return this.maze;}
}
