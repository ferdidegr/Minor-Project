import static org.lwjgl.opengl.GL11.*;
import Game.*;
import Menu_example.*;

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
			render();
	}
	
	// Defines the content of each state
	private void render() {
		switch(state) {
		case MAIN_MENU:
			Menu menu = new Menu();
			try {
				menu.start();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
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

