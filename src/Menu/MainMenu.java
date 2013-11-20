package Menu;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class MainMenu extends ButtonList {
	
	public MainMenu(){
		super();
	}
	
	public void init(int buttonwidth, int buttonheight){
		lijst.add(new MenuButton(buttonwidth, 3*buttonheight, Textures.start, Textures.startover,1));
		lijst.add(new MenuButton(buttonwidth, 0, Textures.start, Textures.startover,2));
	}
	
	public static void actionButtons(int ButtonID){
		switch(ButtonID){
		case 1:
			Menu.setState(GameState.SETTINGS);
			break;
			
			default: break;
		}
	}
	
}
