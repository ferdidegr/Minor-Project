import static org.lwjgl.opengl.GL11.*;
import Game.*;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class GameStates {
	
	// Defining the different states
	private static enum State {
		MAIN_MENU, START_GAME, LEVEL_EDITOR, HIGH_SCORES, SETTINGS, LEVEL_SELECT, GAME, PAUSE, LEVEL_COMPLETE, LEVEL_FAIL;
	}
	
	private State state = State.MAIN_MENU; // Default state is main menu

	public GameStates() {
	/*	try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("Game States");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		// Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		while (!Display.isCloseRequested()) { */
			// Render
			
			checkInput();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clearing buffers
			
			render();
			
		//	Display.update();
		//	Display.sync(60);
	//	}
		
	//	Display.destroy();
	}
	
	// Defines the content of each state
	private void render() {
		switch(state) {
		case MAIN_MENU:
			MainMenu menu = new MainMenu();
			try {
				menu.start();
			} catch (LWJGLException | IOException e) {
				e.printStackTrace();
			}
			break;
		case START_GAME:
			
			break;
		case LEVEL_EDITOR:

			break;
		case HIGH_SCORES:

			break;
		case SETTINGS:

			break;
		case LEVEL_SELECT:

			break;
		case GAME:
			Mazerunner maze = new Mazerunner();
			maze.start();
			break;
		case PAUSE:

			break;
		case LEVEL_COMPLETE:

			break;
		case LEVEL_FAIL:

			break;
		}
	}
	
	// Switching between states with inputs
	private void checkInput() {
		switch(state) {
		case MAIN_MENU:
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				state = State.GAME;
			}
			break;
		case START_GAME:

			break;
		case LEVEL_EDITOR:

			break;
		case HIGH_SCORES:

			break;
		case SETTINGS:

			break;
		case LEVEL_SELECT:

			break;
		case GAME:

			break;
		case PAUSE:

			break;
		case LEVEL_COMPLETE:

			break;
		case LEVEL_FAIL:

			break;
		}
	}

	public static void main(String[] args) {
		new GameStates();
	}
}

