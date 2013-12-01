package tests;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

public class listfilesTest {
	public void start(){
		String currentdir = System.getProperty("user.dir");
		
		String[] files = new File(currentdir+"\\levels").list();
		
		for(String str:files){
			System.out.println(str);
		}
	}
	
	public static void main(String[] args){
		new listfilesTest().start();
	}
}
