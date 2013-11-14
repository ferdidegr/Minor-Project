package Editor;

import java.io.File;

import javax.swing.JFileChooser;

public class JfilechooserTest {
	public void start(){
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		int res =jfc.showSaveDialog(null);
		if(res==JFileChooser.APPROVE_OPTION){
			File file = jfc.getSelectedFile();
			System.out.println(file.getAbsolutePath()+".maze");
		}
	}
	
	public static void main(String[] args){
		JfilechooserTest jft = new JfilechooserTest();
		jft.start();
	}
}
