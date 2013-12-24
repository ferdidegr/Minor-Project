package Menu;

import org.newdawn.slick.Color;

public class MouseSensitivity extends ButtonList{
	
	public MouseSensitivity(){
		super();
	}
	
	@Override
	public void init(int x, int y) {
		int counter = 1;
		
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Low", MenuButton.Alignment.CENTER));		counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "Medium", MenuButton.Alignment.CENTER));		counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover,counter, "high", MenuButton.Alignment.CENTER));		counter++;
		lijst.add(new MenuButton(2*counter* y, Textures.start, Textures.startover, counter,"Back", MenuButton.Alignment.CENTER));		counter++;

		
	}

	@Override
	public void actionButtons(int ID) {
		switch(ID){
			case 1:
				Menu.mousesensitivity = 3;
				break;
			case 2:
				Menu.mousesensitivity = 5;
				break;
			case 3:
				Menu.mousesensitivity = 7;
				break;
			case 4:
				if(Menu.ingame){Menu.setState(GameState.PSETTINGS);}
				else{Menu.setState(GameState.SETTINGS);}
				break;
		}
	}
	
	public void display(){		
		super.display();
		selectedColor();
		
	}
	public void selectedColor(){
		for(MenuButton mb: lijst){
			mb.setNormalTextColor(Color.white);
			mb.setMouseoverTextColor(Color.black);
		}
		switch (Menu.mousesensitivity) {
			case 3:
				lijst.get(0).setTextColor(Color.green);
			break;
			case 5:
				lijst.get(1).setTextColor(Color.green);
			break;
			case 7:
				lijst.get(2).setTextColor(Color.green);
			break;		
		}
	}
}
