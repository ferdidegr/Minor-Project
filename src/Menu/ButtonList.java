package Menu;

import java.util.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


/** Deze klasse bevat de standaardmethodes die ieder submenu moet hebben. 
 * In feite is het een lijst van buttons, met methodes om deze uit te lezen en te displayen.
 * Deze klasse opzich kan geen acties genereren, hiervoor moet een afgeleide klasse gemaakt worden.
 * 
 * @author Karin
 *
 */
public abstract class ButtonList {
	protected ArrayList<MenuButton> lijst;
	
	public ButtonList(){
		lijst = new ArrayList<MenuButton>();
	}
	
	/** Voeg een button toe
	 * 
	 * @param button
	 */
	public void add(MenuButton button){
		lijst.add(button);
	}
	/**
	 * Every button needs to have some left x and top y
	 * @param x
	 * @param y
	 * @param leftallignment
	 */
	public abstract void init(int x, int y);
	public abstract void actionButtons(int ID);
	
	/** 
	 * Returns on which button is clicked, when no button is clicked the method returns -1 
	 * 
	 * @param top y-coordinate of the top of the screen. Needed for scrolling
	 * 			
	 * @return
	 */
	public int checkButtons(int top) {
		int buttonID = -1;
		for(MenuButton knop:lijst){
			if(knop.isButton(Mouse.getX(), top - Mouse.getY()))
			{buttonID = knop.getID();break;}
		}
		return buttonID;
	}
	
	/** 
	 * Draw all the buttons in the list
	 */
	public void display(){
		for (MenuButton knop: lijst){
			knop.draw();
		}
		MenuButton temp = lijst.get(0);
		MenuButton temp2 = lijst.get(lijst.size()-1);
		
		double initheight = temp2.getY()-Display.getHeight();

		if(temp2.getY()<Display.getHeight()){
			if(Menu.top - temp.getY() < Display.getHeight()- temp.getY()){ Menu.top += 0.2*Menu.scrollspeed; Menu.bottom=Menu.top-Display.getHeight();}
			if(temp2.getY() - Menu.bottom < Display.getHeight() + initheight){ Menu.top -= 0.2*Menu.scrollspeed; Menu.bottom=Menu.top-Display.getHeight();}	
		}else{
			if(Menu.top - temp2.getY() > 2*temp2.height){ Menu.top -= 0.2*Menu.scrollspeed; Menu.bottom=Menu.top-Display.getHeight();}
			if(temp.getY() - Menu.bottom > temp.height){ Menu.top += 0.2*Menu.scrollspeed; Menu.bottom=Menu.top-Display.getHeight();}
		}
		
	}
	/**
	 * If a button is called with center, then after a resize of the window this should still be the case.
	 * This method fixes it
	 */
	public void reinit(){
		for(MenuButton mb:lijst){
			mb.reinit();
		}
	}
}
