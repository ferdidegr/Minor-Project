package Menu;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class Text {
	
	private TrueTypeFont myfont;
	private String s;
	private Double size;
	private int lineh;
	private int width;
	
	public Text(double insize, String s){
		this.s = s;
		size = new Double(insize);

	}
	
	/** Schrijft tekst vanaf de coordinaten x,y met tekst s
	 *  
	 * @param x
	 * @param y
	 * @param scale
	 * @param s
	 */
	public void draw(int x, int y,float scale){
		

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		GL11.glTranslatef(x - width/2, y + lineh/2, 0);
		GL11.glScalef(scale, -scale, scale);
		myfont.drawString(0, 0, s,Color.white);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	/** Initialiseert het font
	 *  !Definieer hier het juiste font bestand!
	 * 
	 */
	public void initFont(){
			try {
			float fontsize = size.floatValue();
			InputStream inputStream	= ResourceLoader.getResourceAsStream("BEBAS.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(fontsize); // set font size
			myfont = new TrueTypeFont(awtFont2, true);
			
			lineh = myfont.getHeight();
			width = myfont.getWidth(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getHeight(){
		return lineh;
	}
	public int getWidth(){
		return width;
	}
}
