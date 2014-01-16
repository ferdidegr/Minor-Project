package Depricated;

import java.util.ArrayList;

import Utils.*;

public class Intelligence {

	private static ArrayList<AvoidArea> avoidList;
	
	/**
	 * Deze methode moet opgeroepen worden voordat Intelligence gebruikt kan worden!
	 */
	public static void init(){
		avoidList = new ArrayList<AvoidArea>();
	}
	
	/**
	 * Checkt voor alle avoidArea's of ze niet al vergeten zijn.
	 */
	public static void update(){
		ArrayList<AvoidArea> removelist = new ArrayList<AvoidArea>();
		for (AvoidArea ar: avoidList){
			if(ar.isForgotten()){
				removelist.add(ar);
			}
		}
		for (AvoidArea ar: removelist){
			avoidList.remove(ar);
		}
		removelist.clear();
	}
	
	/**
	 * Checkt of gegeven vector (locatie) zich in een avoidarea in de lijst bevindt.
	 * @param vec
	 * @return
	 */
	public static boolean inAvoidArea(Vector vec){
		for (AvoidArea ar: avoidList){
			if(ar.inArea(vec)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Voegt een avoidarea toe door een nieuwe te maken rond de gegeven vector (locatie)
	 * @param vec
	 */
	public static void addAvoid(Vector vec){
		Vector loc = vec.clone();
		avoidList.add(new AvoidArea(loc));
	}
	
	/**
	 * Voegt een avoidarea toe met gegeven avoidarea
	 * @param ar
	 */
	public static void addAvoid(AvoidArea ar){
		avoidList.add(ar);
	}
	
}
