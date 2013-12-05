package Menu;

import java.util.*;

import org.lwjgl.input.Mouse;


/** Deze klasse bevat de standaardmethodes die ieder submenu moet hebben. 
 * In feite is het een lijst van buttons, met methodes om deze uit te lezen en te displayen.
 * Deze klasse opzich kan geen acties genereren, hiervoor moet een afgeleide klasse gemaakt worden.
 * 
 * @author Karin
 *
 */
public class ButtonList {
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
	 
	/** Wanneer de muis op een button in de lijst staat geeft deze methode 
	 *  de ID van die button.
	 * 
	 * @param bottom
	 * 			De onderkant van het scherm, nodig voor definite van coordinaten.
	 * @return
	 */
	public int checkButtons(int top) {
		int buttonID = 0;
		for(MenuButton knop:lijst){
			if(knop.isButton(Mouse.getX(), top - Mouse.getY()))
			{buttonID = knop.getID();break;}
		}
		return buttonID;
	}
	
	/** roept de draw methode van alle buttons uit de lijst
	 * 
	 */
	public void display(){
		for (MenuButton knop: lijst){
			knop.draw();
		}
	}

}
