package Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Models {
	public static int monster;
	static{
		try {
			monster = Model.loadModel("res/Models/", "scorpion2.obj").generateDList();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
