package Game;

import org.lwjgl.opengl.GL11;


public class MiniMap {
	private int[][] maze;

	public MiniMap(int[][] maze) {
		this.maze = maze;
	}

	public void draw(Player player, int SQUARE_SIZE){
		int mapWidth = maze[0].length;
		int mapHeight = maze.length;
		
		int locX=player.getGridX(SQUARE_SIZE);
		int locZ=mapHeight-player.getGridZ(SQUARE_SIZE);
		
		
		
		
		for (int i=0;i<mapHeight;i++){
			for (int j=0;j<mapWidth;j++){
				if (maze[i][j]>=1 && maze[i][j]<=10){
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glColor3f(1.0f, 0.0f, 0.0f);
					GL11.glVertex2f(0.0f, 0.0f);
					GL11.glVertex2f(10.0f, 0.0f);
					GL11.glVertex2f(10.0f, 10.0f);
					GL11.glVertex2f(0.0f, 10.0f);
					GL11.glEnd();
					
				}
				GL11.glTranslatef(10f, 0f, 0f);
				
				
			}
			GL11.glTranslatef(-10f*mapWidth, 10f, 0f);
		}
		
		GL11.glPushMatrix();
		GL11.glTranslated(locX*10, -locZ*10, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex2f(0.0f, 0.0f);
		GL11.glVertex2f(10.0f, 0.0f);
		GL11.glVertex2f(10.0f, 10.0f);
		GL11.glVertex2f(0.0f, 10.0f);
		GL11.glEnd();
		GL11.glPopMatrix();

	}
}
