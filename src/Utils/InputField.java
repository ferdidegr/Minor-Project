package Utils;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputField {
	private int Stringlength, Fontsize, x, y;
	private float width, height;
	private StringBuilder sb;
	
	public InputField(int StringLength, int Fontsize, int x, int y){
		new Text();
		setStringlength(StringLength);
		setFontsize(Fontsize);
		setX(x);
		setY(y);
		sb = new StringBuilder(StringLength);
		
		String tempstring = "";
		for(int i = 0 ; i < Stringlength; i++){
			tempstring += 'W';
		}
		width = (float) Text.getWidth(Fontsize, tempstring);
		height = (float) Text.getHeight(Fontsize);
	}
	
	public void display(){
		glBegin(GL_LINE_LOOP);
		glVertex2d(x-width*0.05, y-height*0.05);
		glVertex2d(x-width*0.05, y+height*1.05);
		glVertex2d(x+width*1.05, y+height*1.05);
		glVertex2d(x+width*1.05, y-height*0.05);
		glEnd();
		Text.draw(x, y, Fontsize, sb.toString());
	}
	
	public void setFocus(){
			
		
		while(!Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			glClear(GL_COLOR_BUFFER_BIT);
			while(Keyboard.next()){
				if(Keyboard.getEventKeyState()){
					 // Reset the string if we press escape.
	                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
	                    sb.setLength(0);
	                }
	                // Append the pressed key to the string if the key isn't the back key or the shift key.
	                if (Keyboard.getEventKey() != Keyboard.KEY_BACK) {
	                    if (Keyboard.getEventKey() != Keyboard.KEY_LSHIFT && Keyboard.getEventKey()!= Keyboard.KEY_RSHIFT) {
	                    	if(sb.length()<Stringlength){
	                    		sb.append(Keyboard.getEventCharacter());
	                    	}	                       
	                    }
	                    // If the key is the back key, shorten the string by one character.
	                } else if (sb.length() > 0) {
	                    sb.setLength(sb.length() - 1);
	                }
				}				
				
			}
			display();
			Display.update();
			Display.sync(60);
		}
	}
	
	public boolean isField(int x, int y){
		return x>= this.x && x<= (this.x+width) && y>= this.y && y<=(this.y+height);
	}
	
	/*
	 * Getters and setters
	 */
	public void setStringlength(int Stringlength){	this.Stringlength = Stringlength;	}
	public void setFontsize(int Fontsize){ this.Fontsize = Fontsize;}
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
}
