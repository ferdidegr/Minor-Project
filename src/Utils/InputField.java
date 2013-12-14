package Utils;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
/**
 * A text field in which you can type text (OpenGL)
 * You should catch all keyboard events in your main program, otherwise the input will be screwed 
 * @author ZL
 *
 */
public class InputField {
	private int Stringlength, Fontsize, x, y;
	private double width, height, actwidth;
	private boolean isfocused = false;
	private StringBuilder sb;
	double alpha =1;
	int sign = 1;
	private Text font;
	private static int mousex, mousey;
	
	/**
	 * Constructor
	 * @param StringLength The maximum length of the input string
	 * @param Fontsize As it is, the font size
	 * @param x	the top left x - coordinate
	 * @param y	the top left y - coordinate
	 * @param fontpath the path to the font you want to use
	 */
	public InputField(int StringLength, int Fontsize, int x, int y, Text font){
		setFont(font);
		// Set maximum string length
		setStringlength(StringLength);
		// Set font size
		setFontsize(Fontsize);
		// set top left coordinates
		setX(x);
		setY(y);
		// Create a new StringBuilder with the given maximum length
		sb = new StringBuilder(StringLength);
		
		// Worst case scenario for the length (width) of the string, For drawing the box
		String tempstring = "";
		for(int i = 0 ; i < Stringlength; i++){
			tempstring += '@';
		}
		width = (float) font.getWidth(Fontsize, tempstring);
		height = (float) font.getHeight(Fontsize);
	}
	
	/**
	 * Let the input field dominate
	 */
	public void poll(){
		// Check for mouse input, if clicked in the box it should enable typing
		mousepoll();
		
		// Check if the mouse has clicked an input field
		if(isField(mousex, Display.getHeight() - mousey)){
			isfocused = true;
		}else{
			isfocused=false;
		}
		
		// Check if the input field has focus
		if(isfocused){
			glLineWidth(3);
			setFocus();
		}else{
			glLineWidth(1);
		}
		
		// Draw the box
		glBegin(GL_LINE_LOOP);
		glVertex2d(x-width*0.05, y-height*0.05);
		glVertex2d(x-width*0.05, y+height*1.05);
		glVertex2d(x+width*1.05, y+height*1.05);
		glVertex2d(x+width*1.05, y-height*0.05);
		glEnd();
		
		// current length
		actwidth = font.getWidth(Fontsize, sb.toString());
		// Display the String so far
		font.draw((float) (x+(width-actwidth)/2f), y, Fontsize, sb.toString());
		
		// If the field is selected , draw a cursor
		if(isfocused){			
			drawCursor();			
		}

	}
	
	public void setFocus(){		
			// Check all keyboard events			
			while(Keyboard.next()){
				// If the event is key down
				if(Keyboard.getEventKeyState()){
					 // Reset the string if we press escape.
	                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
	                    sb.setLength(0);
	                }
	                // Append the pressed key to the string if the key isn't the back key or the shift key.
	                if (Keyboard.getEventKey() != Keyboard.KEY_BACK) {
	                    if (Keyboard.getEventKey() != Keyboard.KEY_LSHIFT && Keyboard.getEventKey()!= Keyboard.KEY_RSHIFT) {
	                    	if(sb.length()<Stringlength){
	                    		int temp = Keyboard.getEventCharacter();
	                    		// Limit the range of characters
	                    		if(temp>=32 && temp <=122){
	                    			sb.append((char)temp);
	                    		}
	                    	}	                       
	                    }
	                    // If the key is the back key, shorten the string by one character.
	                } else if (sb.length() > 0) {
	                    sb.setLength(sb.length() - 1);
	                }
	                // Check for the enter key
	                if(Keyboard.getEventKey()==Keyboard.KEY_RETURN){isfocused= false;}
				}				
				
			}		
		
	}
	/**
	 * Checks if the mouse is on this field
	 * @param x Mouse x location
	 * @param y Mouse y location
	 * @return true or false
	 */
	public boolean isField(int x, int y){
		return x>= this.x && x<= (this.x+width) && y>= this.y && y<=(this.y+height);
	}
	/**
	 * draws a flickering cursor
	 */
	public void drawCursor(){
		alpha = (alpha+0.015)%1;		
		
		if(alpha>0.5){
			glColor4d(1, 1, 1, alpha);
			glLineWidth(3);
			glBegin(GL_LINES);
			glVertex2d((float) (x+(width+actwidth)/2f), y+height);
			glVertex2d((float) (x+(width+actwidth)/2f), y);
			glEnd();
		}
	}			
	/**
	 * Checks for mouse input and put the mouse coordinates in the static attributes mousex and mouse y
	 */
	public void mousepoll(){
		while(Mouse.next()){
			if(Mouse.getEventButton()==0){
				mousex = Mouse.getX();
				mousey = Mouse.getY();
				while(Keyboard.next()){}
			}
		}
	}
	/*
	 * Getters and setters
	 */
	public void setFont(Text font){this.font = font;}
	public void setStringlength(int Stringlength){this.Stringlength = Stringlength;}
	public void setFontsize(int Fontsize){this.Fontsize = Fontsize;}
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public int getX(){ return x;}
	public int getY(){return y;}
	public double getWidth(){ return width;}
	public double getHeight(){ return height;}
	public String getString(){ return sb.toString();}
}
