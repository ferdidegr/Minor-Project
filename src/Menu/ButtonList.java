package Menu;

import java.util.*;

import org.lwjgl.input.Mouse;

public class ButtonList {
	protected ArrayList<MenuButton> lijst;
	
	public ButtonList(){
		lijst = new ArrayList<MenuButton>();
	}
	
	public void add(MenuButton button){
		lijst.add(button);
	}
	
	public int checkButtons(int bottom) {
		int buttonID = 0;
		for(MenuButton knop:lijst){
			if(knop.isButton(Mouse.getX(), Mouse.getY() + bottom))
			{buttonID = knop.getID();System.out.println(buttonID);break;}
		}
		return buttonID;
	}
	
	public void display(){
		for (MenuButton knop: lijst){
			knop.draw();
		}
	}

}
