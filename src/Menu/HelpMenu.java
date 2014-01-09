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
		
		glColor3f(1,1,1);
		Textures.help1.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
			glTexCoord2d(0, 0); 	glVertex2d(0, 0);
			glTexCoord2d(0, 1); 	glVertex2d(0, Display.getHeight());
			glTexCoord2d(1, 1); 	glVertex2d(Display.getWidth(), Display.getHeight());
			glTexCoord2d(1, 0); 	glVertex2d(Display.getWidth(), 0);
		glEnd();
		
		super.display();
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

}
