package Game;

import static org.lwjgl.opengl.GL11.*;


public class MiniMap {
	private int[][] maze;

	public MiniMap(int[][] maze) {
		this.maze = maze;
	}

	public void draw(Player player, int SQUARE_SIZE){
		int mapWidth = maze[0].length;
		int mapHeight = maze.length;
		
		int locX=player.getGridX(SQUARE_SIZE);
		int locZ=mapHeight-1-player.getGridZ(SQUARE_SIZE);
				
		int size=10;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		
		for (int i=locZ-size;i<locZ+size;i++){
			for (int j=locX-size;j<locX+size;j++){
				if (i>=0 && j>=0 && i<mapHeight && j<mapWidth){
					if (maze[mapHeight-i-1][j]>=1 && maze[i][j]<=10){
						drawBlock();
					}
				}
				glTranslatef(10f, 0f, 0f);
			}
			glTranslatef(-10f*(2*size), 10f, 0f);
		}
		glFinish();
		glPushMatrix();
		glLoadIdentity();
		for (int i=0;i<size*2;i++){
			glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
			drawBlock();
			glTranslatef(10f,0,0);
		}
		for (int i=0;i<size*2+1;i++){
			drawBlock();
			glTranslatef(0,10f,0);
		}
		glLoadIdentity();
		for (int i=0;i<size*2;i++){
			drawBlock();
			glTranslatef(0,10f,0);
		}
		for (int i=0;i<size*2+1;i++){
			drawBlock();
			glTranslatef(10f,0,0);
		}
		glPopMatrix();
		
		
		glPushMatrix();
		glTranslated(size*10, -size*10, 0);
		glColor3f(1.0f, 1.0f, 1.0f);
		drawBlock();
		glPopMatrix();

	}
	
	public void drawBlock(){
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(10.0f, 0.0f);
		glVertex2f(10.0f, 10.0f);
		glVertex2f(0.0f, 10.0f);
		glEnd();
	}
}
