package Game;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import Utils.Vector;

public class MiniMap {
	private int[][] maze;
	Vector dir;
	private int[][] visited;

	public MiniMap(int[][] maze) {
		this.maze = maze;
		this.visited = new int[maze.length][maze[0].length];
//		this.visited = new int[maze.length][maze[0].length];
//		for (int i = 0; i < visited.length; i++) {
//			for (int j = 0; j < visited[0].length; j++) {
//				visited[i][j] = 0;
//			}
//		}
	}

	public void draw(Player player, ArrayList<Monster> monsterlijst, int SQUARE_SIZE) {
		int mapWidth = maze[0].length;
		int mapHeight = maze.length;

		int locX = player.getGridX(SQUARE_SIZE);
		int locZ = player.getGridZ(SQUARE_SIZE);

		int size = 10;

		// draw red maze
		glEnable(GL_BLEND);
		glColor4f(1.0f, 0.0f, 0.0f, 0.5f);

		for (int z = locZ - size; z < locZ + size; z++) {
			for (int x = locX - size; x < locX + size; x++) {
				if (x >= 0 && z >= 0 && x < mapWidth && z < mapHeight) {
					if (Math.abs(z - locZ) < 5 && Math.abs(x - locX) < 5) {
						visited[z][x] = 1;
					}

					if (maze[z][x] >= 1 && maze[z][x] <= 10 && visited[z][x] == 1) {
						for (int k = 0; k < maze[z][x]; k++) {
							drawBlock();
						}
					}

				}
				glTranslatef(10f, 0f, 0f);
			}
			glTranslatef(-10f * (2 * size), 10f, 0f);
		}
		
		

		// draw black box around the minimap
		glPushMatrix();
		glLoadIdentity();
		for (int i = 0; i < size * 2; i++) {
			glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
			drawBlock();
			glTranslatef(10f, 0, 0);
		}
		for (int i = 0; i < size * 2 + 1; i++) {
			drawBlock();
			glTranslatef(0, 10f, 0);
		}
		glLoadIdentity();
		for (int i = 0; i < size * 2; i++) {
			drawBlock();
			glTranslatef(0, 10f, 0);
		}
		for (int i = 0; i < size * 2 + 1; i++) {
			drawBlock();
			glTranslatef(10f, 0, 0);
		}
		glPopMatrix();

		// draw monster dots

		glColor4f(0.2f, 0.2f, 1f, 0.5f);
		for (Monster monster : monsterlijst) {
			if(monster.active){
				glColor3f(0.2f, 0.2f, 1f);
				if (monster.playerSight()) {
					glColor4f(0.2f, 1.0f, 0.2f, 0.5f);
				}

				int relX = locX - monster.getGridX(SQUARE_SIZE);
				int relZ = locZ - monster.getGridZ(SQUARE_SIZE);
				if ((Math.abs(relX) < 10) && (Math.abs(relZ) < 10)) {
					glPushMatrix();
					glTranslated(size * 10, -size * 10, 0);
					glTranslated(-relX * size, -relZ * size, 0);
					drawBlock();
					glPopMatrix();
				}
			}

		}

		// draw white player dot
		glPushMatrix();
		glTranslated(size * 10.5, -size * 9.5, 0);
		dir = player.lookat();
		rotateV();

		glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
		drawPlayer();
		glPopMatrix();

	}

	public void drawBlock() {
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(10.0f, 0.0f);
		glVertex2f(10.0f, 10.0f);
		glVertex2f(0.0f, 10.0f);
		glEnd();
	}

	public void drawPlayer() {
		int size = 2;
		glBegin(GL_TRIANGLES);
		glVertex2f(0.0f, 6.0f * size);
		glVertex2f(3.0f * size, -3.0f * size);
		glVertex2f(-3.0f * size, -3.0f * size);
		glEnd();
	}

	public void rotateV() {
		float x = (float) dir.getX();
		float z = (float) dir.getZ();
		FloatBuffer m = Utils.Utils.createFloatBuffer(z, -x, 0, 0, x, z, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		glMultMatrix(m);
	}
}
