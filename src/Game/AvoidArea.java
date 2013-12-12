package Game;
import java.util.Calendar;

import Utils.*;

public class AvoidArea {
	
	public Vector location;
	public long startTime;
	public long forgetTime = 60000;
	public double radius = 2;
	
	public AvoidArea(Vector loc){
		System.out.println("AvoidArea gemaakt");
		double x = loc.getX();
		double z = loc.getZ();
		location = new Vector(x,0,z);
		startTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public boolean isForgotten(){
			long currentTime = Calendar.getInstance().getTimeInMillis();
			if((currentTime - startTime) < forgetTime){
				return true;
			} else { return false;}
	}
	
	public boolean inArea(Vector vec){
		if(location.distance(vec) < radius){
			return true;
		}
		return false;
	}
}
