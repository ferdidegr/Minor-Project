package tests;

import javax.swing.JOptionPane;

public class JoptionpaneTest {
	public void start(){
		JOptionPane jop = new JOptionPane("test");
		String res = jop.showInputDialog("test");
		
		System.out.println(res);
	}
	
	public static void main(String[] args){
		JoptionpaneTest test = new JoptionpaneTest();
		test.start();
	}
}
