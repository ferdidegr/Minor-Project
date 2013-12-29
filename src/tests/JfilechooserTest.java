package tests;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JfilechooserTest {
	public void start(){
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze Files", "maze");
		jfc.addChoosableFileFilter(filter);
		int res = jfc.showSaveDialog(null);
		
		if(res == JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			System.out.println(file.getAbsolutePath());
		}
		JOptionPane.showMessageDialog(null, "error" , "error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args){
		JfilechooserTest jft = new JfilechooserTest();
		jft.start();
	}
}
