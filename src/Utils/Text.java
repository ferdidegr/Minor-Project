package Utils;
import java.awt.Font;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.ResourceLoader;

public class Text{
	private static Font awtFont;
	private static TrueTypeFont myfont;
	private static float baseFontSize = 55;
	
	public Text(){
		initFont();
	}
	
	public static void draw(float x , float y , float FontSize, String text){
		draw(x, y, FontSize, text, Color.white);
	}
	
	public static void draw(float x , float y , float FontSize, String text, Color color){
		float derivedFont = FontSize/baseFontSize;
		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glPushMatrix();
		glTranslatef(x, y, 0);
		glScalef(derivedFont, derivedFont, derivedFont);
		TextureImpl.unbind();
		myfont.drawString(0, 0, text,color);
		glPopMatrix();
		glPopAttrib();
	}
	
	public static double getWidth(float FontSize, String text){
		return (myfont.getWidth(text)*FontSize/baseFontSize);
	}
	
	public static double getHeight(float FontSize){
		return (myfont.getHeight()*FontSize/baseFontSize);
	}
	
	public static void initFont(){
		try {			
			InputStream inputStream	= ResourceLoader.getResourceAsStream("BEBAS.TTF");

			awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);		
			awtFont = awtFont.deriveFont(baseFontSize); // set font size
			myfont = new TrueTypeFont(awtFont, true);			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}