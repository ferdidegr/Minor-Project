package Game;
import java.util.Calendar;

import Utils.*;

public class AvoidArea {
	
	public Vector location;
	public long startTime;
	public long forgetTime = 60000;
	public double radius = 2;
	
	/**
	 *  De avoidarea wordt gemaakt om een gegeven locatie heen. Huidige tijd wordt opgeslagen als starttijd.
	 * @param loc
	 */
	public AvoidArea(Vector loc){
		double x = loc.getX();
		double z = loc.getZ();
		location = new Vector(x,0,z);
		startTime = Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * Checkt of het verschil tussen de huidige tijd en de starttijd niet groter is dan de vergeettijd.
	 * @return
	 */
	public boolean isForgotten(){
			long currentTime = Calendar.getInstance().getTimeInMillis();
			if((currentTime - startTime) < forgetTime){
				return true;
			} else { return false;}
	}
	
	/**
	 * Geeft true wanneer een gegeven vector (locatie) zich in de area bevindt.
	 * @param vec
	 * @return
	 */
	public boolean inArea(Vector vec){
		if(location.distance(vec) < radius){
			return true;
		}
		return false;
	}
}
