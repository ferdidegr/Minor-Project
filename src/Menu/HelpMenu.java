package Menu;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class HelpMenu extends ButtonList{
	private int helpID = 1;
	private int numMenus = 9;
	private int offset = (int) (75*Display.getWidth()/1024f);
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
			case 2:
				helpID = Math.min(numMenus, ++helpID);
			break;
			case 3:
				helpID = Math.max(1, --helpID);
			break;
		}
	}
	
	public void display(){
		glEnable(GL_BLEND);
		glColor4f(0, 0, 0,0.2f);
		glRectd(0, 0, Display.getWidth(),Display.getHeight());
		
		glColor3f(1,1,1);
		
		if(helpID==1)	Textures.help1.bind();
		if(helpID==2)	Textures.help2.bind();
		if(helpID==3)	Textures.help3.bind();
		if(helpID==4)	Textures.help4.bind();
		if(helpID==5)	Textures.help5.bind();
		if(helpID==6)	Textures.help6.bind();
		if(helpID==7)	Textures.help7.bind();
		if(helpID==8)	Textures.help8.bind();
		if(helpID==9)	Textures.help9.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
			glTexCoord2d(0, 0); 	glVertex2d(offset, offset);
			glTexCoord2d(0, 1); 	glVertex2d(offset, Display.getHeight()-offset);
			glTexCoord2d(1, 1); 	glVertex2d(Display.getWidth()-offset, Display.getHeight()-offset);
			glTexCoord2d(1, 0); 	glVertex2d(Display.getWidth()-offset, offset);
		glEnd();
		
		super.display();
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

}
