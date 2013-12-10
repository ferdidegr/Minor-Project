package tests;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

public class listfilesTest {
	private ArrayList<String> MazeList = new ArrayList<String>();
	
	public void start(){
		String currentdir = System.getProperty("user.dir");
		
		String[] files = new File(currentdir+"\\levels").list();
		
		for(String name:files){
			if(name.toLowerCase().endsWith(".maze")){
				MazeList.add(name);
			}
		}
		
		for(String str:MazeList){
			System.out.println(str.split(".maze")[0]);
		}
	}
	
	public static void main(String[] args){
		new listfilesTest().start();
	}
}
