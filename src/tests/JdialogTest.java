package tests;

import javax.swing.JOptionPane;

public class JdialogTest {
	public void start(){
		JOptionPane.showMessageDialog(null, "test","niet doen", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args){
		JdialogTest jdt = new JdialogTest();
		jdt.start();
	}
}
