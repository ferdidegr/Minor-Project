package Menu;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;


public class Settings extends ButtonList {
	
	public Settings(){
		super();
	}
	
	public void init(int buttonwidth, int buttonheight){
		lijst.add(new MenuButton(buttonwidth, 2*buttonheight, Textures.start, Textures.startover,1, "Main Menu"));
		lijst.add(new MenuButton(buttonwidth, 0, Textures.start, Textures.startover,2, "Undefined"));
	}
	
	public static void actionButtons(int ButtonID){
		switch(ButtonID){
		case 1:
			Menu.setState(GameState.MAIN);
			break;
			
			default: break;
		}
	}
	
}
