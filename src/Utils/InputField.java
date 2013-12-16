package Utils;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
/**
 * A text field in which you can type text (OpenGL)
 * 
 * @author ZL
 *
 */
public class InputField {
	// maximum length of the input string, font size and top left coordinates
	private int Stringlength, Fontsize, x, y;
	// Dimensions of the box and the length of the string
	private double width, height, actwidth;
	// Status of this input field, does the user requested focus on this field
	private boolean isfocused = false;
	// keeps track of the inserted characters
	private StringBuilder sb;
	// keeps track of the flickering of the cursor, >0.5 visible otherwise not visible
	double alpha =1;	
	// Font used in the input field
	private Text font;
	// Color of the box
	private float[] boxcolor = new float[3];
	// static attributes shared among all inputfields to track the mouse
	private static int mousex, mousey;
	
	/**
	 * ********************************************************************************
	 * Constructor
	 * @param StringLength The maximum length of the input string
	 * @param Fontsize As it is, the font size
	 * @param x	the top left x - coordinate
	 * @param y	the top left y - coordinate
	 * @param fontpath the path to the font you want to use
	 * @param boxcolor the color of the surrounding box
	 * ********************************************************************************
	 */
	public InputField(int StringLength, int Fontsize, int x, int y, Text font, float... boxcolor){
		setBoxColor(boxcolor);
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
			tempstring += 'W';
		}
		width = (float) font.getWidth(Fontsize, tempstring);
		height = (float) font.getHeight(Fontsize);
	}
	/**
	 * Alternative constructor, when no color is specified the border is white
	 * @param StringLength The maximum length of the input string
	 * @param Fontsize As it is, the font size
	 * @param x	the top left x - coordinate
	 * @param y	the top left y - coordinate
	 * @param fontpath the path to the font you want to use
	 */
	public InputField(int StringLength, int Fontsize, int x, int y, Text font){
		
		this(StringLength, Fontsize, x, y, font,1f,1f,1f);
	}
	/**
	 * ********************************************************************************
	 * Render the input field while checking if focus is requested for this Field
	 * ********************************************************************************
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
		
		// Set color
		glColor3f(boxcolor[0], boxcolor[1], boxcolor[2]);
		// Draw the box
		glBegin(GL_LINE_LOOP);
		glVertex2d(x, y);
		glVertex2d(x, y+height*1.05);
		glVertex2d(x+width*1.05, y+height*1.05);
		glVertex2d(x+width*1.05, y);
		glEnd();
		
		// current length
		actwidth = font.getWidth(Fontsize, sb.toString());

		// Display the String so far
		font.draw((float) (x+(1.05*width-actwidth)/2f), y, Fontsize, sb.toString());
		
		// If the field is selected , draw a cursor
		if(isfocused){			
			drawCursor();			
		}

	}
	/**
	 * ********************************************************************************
	 * Set the focus to this inputField for listening to keyboard input
	 * ********************************************************************************
	 */
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
	                    			// current length
	                    			actwidth = font.getWidth(Fontsize, sb.toString());
	                    			// if the text goes outside the box, shorten it
	                    			if(actwidth>width){sb.setLength(sb.length() - 1);}
	                    		}
	                    	}	                       
	                    }
	                    // If the key is the back key, shorten the string by one character.
	                } else if (sb.length() > 0) {
	                    sb.setLength(sb.length() - 1);
	                }
	                
				}
				// Key release event, this solves the problem that releasing the ENTER key triggers an event in the main program
				else{
					// Check for the enter key
	                if(Keyboard.getEventKey()==Keyboard.KEY_RETURN){isfocused= false;mousex=0;mousey=0;while(Keyboard.next()){}}
				}
				
			}		
		
	}
	/**
	 * ****************************************
	 * Checks if the mouse is on this field
	 * @param x Mouse x location
	 * @param y Mouse y location
	 * @return true or false
	 * ****************************************
	 */
	public boolean isField(int x, int y){
		return x>= this.x && x<= (this.x+width) && y>= this.y && y<=(this.y+height);
	}
	/**
	 * ********************************************************************************
	 * Set the inputfield to the center of the screen
	 * ********************************************************************************
	 */
	public void centerScreen(){
		centerY();
		centerX();
	}
	/**
	 * ********************************************************************************
	 * Center the inputfield in y - direction
	 * ********************************************************************************
	 */
	public void centerY(){
		y = (int) ((Display.getHeight()-height)/2);
	}
	/**
	 * ********************************************************************************
	 * Center the inputfield in X - direction
	 * ********************************************************************************
	 */
	public void centerX(){
		x = (int) ((Display.getWidth()-width)/2);
	}
	/**
	 * ****************************************
	 * draws a flickering cursor
	 * ****************************************
	 */
	public void drawCursor(){
		alpha = (alpha+0.015)%1;		
		
		if(alpha>0.5){
			glColor4d(boxcolor[0], boxcolor[1], boxcolor[2], alpha);
			glLineWidth(3);
			glBegin(GL_LINES);
			glVertex2d((float) (x+(1.05*width+actwidth)/2f), y+height);
			glVertex2d((float) (x+(1.05*width+actwidth)/2f), y);
			glEnd();
		}
	}			
	/**
	 * ************************************************************************************************************************
	 * Checks for mouse input and put the mouse coordinates in the static attributes mousex and mouse y
	 * ************************************************************************************************************************
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
	 * ****************************************
	 * Getters and setters
	 * ****************************************
	 */
	public void setBoxColor(float... boxcolor){
		int length = boxcolor.length;
		
		for(int i = 0 ; i <Math.min(length, 3);i++){
			this.boxcolor[i] = boxcolor[i];
		}
	}
	public void setFont(Text font){this.font = font;}
	public void setStringlength(int Stringlength){this.Stringlength = Stringlength;}
	public void setFontsize(int Fontsize){this.Fontsize = Fontsize;}
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public void setString(String text){sb.setLength(0);sb.append(text);}
	public int getX(){ return x;}
	public int getY(){return y;}
	public double getWidth(){ return width;}
	public double getHeight(){ return height;}
	public String getString(){ return sb.toString();}
	public boolean isFocused(){return isfocused;}
}
