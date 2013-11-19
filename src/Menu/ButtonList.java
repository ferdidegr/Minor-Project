package Menu;

import java.util.*;

import org.lwjgl.input.Mouse;

public class ButtonList {
	private ArrayList<MenuButton> list;
	
	public ButtonList(){
		ArrayList<MenuButton> list = new ArrayList<MenuButton>(0);
	}
	
	public void add(MenuButton button){
		list.add(button);
	}
	
	public int checkButtons(int bottom) {
		int buttonID = 0;
		for(MenuButton knop:list){
			if(knop.isButton(Mouse.getX(), Mouse.getY() + bottom))
			{buttonID = knop.getID();System.out.println(buttonID);break;}
		}
		return buttonID;
	}
	
	public void display(){
		for (MenuButton knop: list){
			knop.draw();
		}
	}
	

}
