package Game;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import Utils.Vector;

/**
 * @author Ferdi
 * Class drawing the minimap in the HUD
 */
public class MiniMap {
	private int[][] maze;
	Vector dir;
	private int[][] visited;
	double factor = (Display.getHeight()<768?Display.getHeight()/768f:1);
	double blocksize = 10 * factor;
	/**
	 * Constructor for the minimap
	 * @param maze the game maze
	 */
	public MiniMap(int[][] maze) {
		this.maze = maze;
		this.visited = new int[maze.length][maze[0].length];
	}

	/**
	 * General draw minimap function. View has already been changed when this function is called, therefore it contains only drawing in 2d
	 * @param player player object
	 * @param monsterlijst monsters still in the game
	 * @param SQUARE_SIZE size of a single square
	 */
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
				glTranslated(blocksize, 0f, 0f);
			}
			glTranslated(-blocksize * (2 * size), blocksize, 0f);
		}
		
		// draw black box around the minimap
		glPushMatrix();
		glLoadIdentity();
		
		for (int i = 0; i < size * 2; i++) {
			glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
			drawBlock();
			glTranslated(blocksize, 0, 0);
		}
		for (int i = 0; i < size * 2 + 1; i++) {
			drawBlock();
			glTranslated(0, blocksize, 0);
		}
		glLoadIdentity();
		
		for (int i = 0; i < size * 2; i++) {
			drawBlock();
			glTranslated(0, blocksize, 0);
		}
		for (int i = 0; i < size * 2 + 1; i++) {
			drawBlock();
			glTranslated(blocksize, 0, 0);
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
					glTranslated(size * 10*factor, -size * 10*factor, 0);
					glTranslated(-relX * size*factor, -relZ * size*factor, 0);
					drawBlock();
					glPopMatrix();
				}
			}
		}

		// draw white player dot, rotate it according to the players direction
		glPushMatrix();
		glTranslated(size * 10.5*factor, -size * 9.5*factor, 0);
		dir = player.lookat();
		rotateV();

		glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
		drawPlayer();
		glPopMatrix();
		glDisable(GL_BLEND);
	}

	/**
	 * Draw a simple block part of the minimap
	 */
	public void drawBlock() {
		glBegin(GL_QUADS);
		glVertex2f(0.0f, 0.0f);
		glVertex2d(blocksize, 0.0f);
		glVertex2d(blocksize, blocksize);
		glVertex2d(0.0f, blocksize);
		glEnd();
	}

	/**
	 * Draw a triangle for the player
	 */
	public void drawPlayer() {
		int size = 2;
		glBegin(GL_TRIANGLES);
		glVertex2d(0.0f, 6.0f * size*factor);
		glVertex2d(3.0f * size*factor, -3.0f * size*factor);
		glVertex2d(-3.0f * size*factor, -3.0f * size*factor);
		glEnd();
	}
	
	/**
	 * Rotate player triangle using matrix multiplication (faster than GL_Rotate)
	 */
	public void rotateV() {
		float x = (float) dir.getX();
		float z = (float) dir.getZ();
		FloatBuffer m = Utils.Utils.createFloatBuffer(z, -x, 0, 0, x, z, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		glMultMatrix(m);
	}
}
