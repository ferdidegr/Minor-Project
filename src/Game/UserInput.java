package Game;




import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import Menu.*;




/**
 * The UserInput class is an extension of the Control class. It also implements three 
 * interfaces, each providing handler methods for the different kinds of user input.
 * <p>
 * For making the assignment, only some of these handler methods are needed for the 
 * desired functionality. The rest can effectively be left empty (i.e. the methods 
 * under 'Unused event handlers').  
 * <p>
 * Note: because of how java is designed, it is not possible for the game window to
 * react to user input if it does not have focus. The user must first click the window 
 * (or alt-tab or something) before further events, such as keyboard presses, will 
 * function.
 * 
 * @author Mattijs Driel
 *
 */
public class UserInput extends Control 		
{
	// TODO: Add fields to help calculate mouse movement
	
		
	/**
	 * UserInput constructor.
	 * <p>
	 * To make the new UserInput instance able to receive input, listeners 
	 * need to be added to a GLCanvas.
	 * 
	 * @param canvas The GLCanvas to which to add the listeners.
	 */
	public UserInput()
	{
	}
	
	/*
	 * **********************************************
	 * *				Updating					*
	 * **********************************************
	 */

	@Override
	public void update()
	{
		
		
	}

	public void poll(){
		if(Mouse.isGrabbed()){
			dX = -5*Mouse.getDX();
			dY=5*Mouse.getDY();
		}
		if(Mouse.isButtonDown(0)){
			Mouse.setGrabbed(true);
		}
		
		/*
		 * KEYBOARD
		 */
		while (Keyboard.next()) {
		    if (Keyboard.getEventKeyState()) {
		    	/*
		    	 * Key Pressed events
		    	 */
		        if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
		   
		        	Menu.setState(GameState.PAUSE);
		        	Mouse.setGrabbed(false);
		        	dX=0;
		        	dY=0;
		        	}
				if (Keyboard.getEventKey() == Keyboard.KEY_W) {forward = true;}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {back = true;}
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {left = true;}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {right = true;}
				if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT) {run = true;}
				if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {jump = true;}
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {view_coord = true;}
				if (Keyboard.getEventKey() == Keyboard.KEY_E) {lookback = true;}
		    } else {
		    	/*
		    	 * Key Released events
		    	 */
		        if (Keyboard.getEventKey() == Keyboard.KEY_W) {forward=false;}
		    	if (Keyboard.getEventKey() == Keyboard.KEY_S) {back=false;}
		    	if (Keyboard.getEventKey() == Keyboard.KEY_A) {left = false;}
				if (Keyboard.getEventKey() == Keyboard.KEY_D) {right = false;}
				if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT) {run = false;}
				if (Keyboard.getEventKey() == Keyboard.KEY_F12){
					Fullscreen.setDisplayMode(Display.getWidth(), Display.getHeight(), true);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {jump = false;}
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {view_coord = false;}
				if (Keyboard.getEventKey() == Keyboard.KEY_E) {lookback = false;}
				if (Keyboard.getEventKey()== Keyboard.KEY_F2) {debug = !debug;}
				if (Keyboard.getEventKey()== Keyboard.KEY_M) {minimap = ! minimap;}
				if (Keyboard.getEventKey()==Keyboard.KEY_Q) {triggered = true;}
				if(Keyboard.getEventKey()==Keyboard.KEY_H){Mazerunner.player.getHealth().addHealth(100000);}
				
		    }
		}
	}
	

}
