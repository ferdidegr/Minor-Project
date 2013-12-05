package Utils;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Chooser extends JFrame implements ActionListener{
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 7746449571087818959L;
	private DisplayMode[] lijst;	
	private String[] lijst2;
	private DisplayMode displaymodus;
	private JComboBox<String> box;
	private JCheckBox checkbox;
	private boolean fullscreen;
	private int bitdepth = Display.getDesktopDisplayMode().getBitsPerPixel();
	private int freq = Display.getDesktopDisplayMode().getFrequency();
	private boolean enableFS;
	/**
	 * constructor
	 */
	public Chooser(boolean enableFS){
		this.displaymodus = null;
		this.enableFS = enableFS;
		/*
		 * Get all displaymodes that matches the bitdepth and the frequency of the pc
		 */
		try {
			DisplayMode[] templijst = Display.getAvailableDisplayModes();
			int teller = 0;
			for(DisplayMode dpm:templijst){
				if(dpm.getBitsPerPixel()==bitdepth && dpm.getFrequency()==freq)teller++;
			}
			lijst = new DisplayMode[teller];
			lijst2 = new String[teller];
			int teller2 = 0;
			for(DisplayMode dpm:templijst){
				if(dpm.getBitsPerPixel()==bitdepth && dpm.getFrequency()==freq){
					lijst[teller2]=dpm;
					lijst2[teller2] = dpm.toString();
					teller2++;
				}
			}
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Arrays.sort(lijst2);
		setResizable(false);		
		setUndecorated(true);
		
		// Declare and create new combo box
		box = new JComboBox<String>(lijst2);
		// Declare new button to confirm
		JButton button = new JButton("Select resolution");
		// declare checkbox to set fullscreen or not
		checkbox = new JCheckBox("Fullscreen");
		
		setTitle("Choose resolution");
		
		setLayout(new FlowLayout());
		add(box);
		add(checkbox);
		add(button);		
		/*
		 * Only pressing the set resolution button will cause any action
		 */
		button.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250,110);
		getContentPane().setBackground(Color.BLACK);
		
		checkbox.setBackground(Color.BLACK);
		box.setBackground(Color.BLACK);
		button.setBackground(Color.BLACK);
		checkbox.setForeground(Color.white);
		box.setForeground(Color.WHITE);
		button.setForeground(Color.WHITE);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
		checkbox.setEnabled(enableFS);
		checkbox.setFont(font);
		box.setFont(font);
		button.setFont(font);
		
		setLocation(Display.getDisplayMode().getWidth()/2, Display.getDisplayMode().getHeight()/2);
		setVisible(true);
		
		
	}
	/*
	 * Getters
	 */
	public DisplayMode getDisplay(){return displaymodus;}
	public boolean getFullscreen(){ return fullscreen;}
	/*
	 * ActionListener
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
//		displaymodus = (DisplayMode) box.getSelectedItem();
		String modus = (String) box.getSelectedItem();
		
		// Get the display mode matching this string
		for(DisplayMode dpm:lijst){
			if(dpm.toString().equals(modus)){
				displaymodus = dpm;
			}
		}
		
		fullscreen = checkbox.isSelected();
		
		try {
			Display.setDisplayMode(displaymodus);
			Display.setFullscreen(fullscreen);
			
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		this.dispose();		
	}	
	public void exit() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
  
}