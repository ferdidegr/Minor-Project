package Menu;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class HelpMenu extends ButtonList{

	@Override
	public void init(int x, int y) {
		int buttonyloc = (int) (Display.getHeight()-1.5*MenuButton.height);
		lijst.add(new MenuButton(buttonyloc, Textures.start, Textures.startover,3, "Previous", MenuButton.Alignment.LEFT));
		lijst.add(new MenuButton(buttonyloc, Textures.start, Textures.startover,1, "Main Menu", MenuButton.Alignment.CENTER));
		lijst.add(new MenuButton(buttonyloc, Textures.start, Textures.startover,2, "Next", MenuButton.Alignment.RIGHT));
		
	}

	@Override
	public void actionButtons(int ID) {
		switch(ID){
			case 1:
				Menu.setState(GameState.MAIN);
			break;
		}
		
	}
	
	public void display(){
		glEnable(GL_BLEND);
		glColor4f(0, 0, 0,0.2f);
		glRectd(0, 0, Display.getWidth(),Display.getHeight());	
		super.display();
		glDisable(GL_BLEND);
	}

}
