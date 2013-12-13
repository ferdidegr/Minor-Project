package Utils;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputField {
	private int Stringlength, Fontsize, x, y;
	private double width, height;
	private boolean isfocused = false;
	private StringBuilder sb;
	double alpha =1;
	int sign = 1;
	private Text font;
	
	public InputField(int StringLength, int Fontsize, int x, int y, String fontpath){
		font = new Text("CALIBRI.TTF");
		setStringlength(StringLength);
		setFontsize(Fontsize);
		setX(x);
		setY(y);
		sb = new StringBuilder(StringLength);
		
		String tempstring = "";
		for(int i = 0 ; i < Stringlength; i++){
			tempstring += 'W';
		}
		width = (float) font.getWidth(Fontsize, tempstring);
		height = (float) font.getHeight(Fontsize);
	}
	
	public void display(){
		glClear(GL_COLOR_BUFFER_BIT);
		
		mousepoll();
		if(isfocused){
			glLineWidth(3);
		}else{
			glLineWidth(1);
		}
		glBegin(GL_LINE_LOOP);
		glVertex2d(x-width*0.05, y-height*0.05);
		glVertex2d(x-width*0.05, y+height*1.05);
		glVertex2d(x+width*1.05, y+height*1.05);
		glVertex2d(x+width*1.05, y-height*0.05);
		glEnd();
		
		font.draw(x, y, Fontsize, sb.toString());
		
		if(isfocused){			
			alpha = (alpha+0.015)%1;		
			double width = font.getWidth(Fontsize, sb.toString());
			if(alpha>0.5){
			glColor4d(1, 1, 1, alpha);
			glLineWidth(3);
			glBegin(GL_LINES);
			glVertex2d(x+width, y+height);
			glVertex2d(x+width, y);
			glEnd();
			}
		}

	}
	
	public String setFocus(){
		isfocused = true;	
		
		while(!Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			while(Display.isCloseRequested()){}
			Mouse.next();
			if(Mouse.getEventButton()==0){
				if(!isField(Mouse.getX(), Display.getHeight() - Mouse.getY())){
					break;
				}
			}
			
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
	                    		int temp = Keyboard.getEventCharacter();
	                    		if(temp>=32 && temp <=122){
	                    			sb.append((char)temp);
	                    		}
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
		isfocused = false;
		return sb.toString();
	}
	
	public boolean isField(int x, int y){
		return x>= this.x && x<= (this.x+width) && y>= this.y && y<=(this.y+height);
	}
	
	public void mousepoll(){
		while(Mouse.next()){
			if(Mouse.getEventButton()==0){
				if(isField(Mouse.getX(), Display.getHeight() - Mouse.getY())){
					setFocus();
				}
			}
		}
	}
	/*
	 * Getters and setters
	 */
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
