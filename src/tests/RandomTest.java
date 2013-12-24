package tests;

import java.util.Random;

public class RandomTest {
	public static void main(String[] args){
		Random rand = new Random();
		
		for(int i = 0 ; i < 50; i++){
			System.out.println(rand.nextInt(4));
		}
	}
}
