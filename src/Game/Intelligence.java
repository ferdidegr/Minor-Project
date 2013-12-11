package Game;

import java.util.ArrayList;

import Utils.*;

public class Intelligence {

	private static ArrayList<AvoidArea> avoidList;
	
	public static void init(){
		avoidList = new ArrayList<AvoidArea>();
	}
	
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
	
	public static boolean inAvoidArea(Vector vec){
		for (AvoidArea ar: avoidList){
			if(ar.inArea(vec)){
				return true;
			}
		}
		return false;
	}
	
	public static void addAvoid(Vector vec){
		Vector loc = vec.clone();
		avoidList.add(new AvoidArea(loc));
	}
	
	public static void addAvoid(AvoidArea ar){
		avoidList.add(ar);
	}
	
}
