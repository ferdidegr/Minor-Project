package Intelligence;

import java.util.ArrayList;
import java.util.Collections;

import Utils.*;

public class AStar {
	
	private ArrayList<Node> ActiveList;
	public static ArrayList<Node> AllNodes = new ArrayList<Node>();
	public ArrayList<Node> Visited;
	private Node CurrentNode;
	private Node Start;
	private Node End;
	private int maxIterations = 100;
	/**
	 * Nodig om te initialiseren
	 */
	public AStar(){
		ActiveList = new ArrayList<Node>();
		Visited = new ArrayList<Node>();
	}
	
	/**
	 * Maakt van gegeven Maze alle punten die geen muur zijn een Node, en zet deze in AllNodes.
	 * @param maze
	 */
	public static void loadMaze(int[][] maze){		
		AllNodes.clear();
		for(int j = 0; j < maze.length; j++){
			for(int i = 0; i<maze[0].length; i++){
				if((maze[j][i] < 1 | maze[j][i] > 10) && maze[j][i]!=12 && maze[j][i]!=15){
					Node no = new Node(i,j);
					AllNodes.add(no);
				}
			}
		}
	}
	
	/**
	 * Zet het eind- en beginpunt van de route als deze beloopbaar zijn (dus in allnodes zitten). Geeft true als dit gelukt is.
	 * Van vector A en B wordt hiervoor het x- en z-coordinaat gebruikt, die naar beneden afgerond worden.
	 * @param A
	 * 			A vector representing the starting point
	 * @param B
	 * 			A vector representing the end point
	 * @return
	 */
	public boolean setRoute(Vector A, Vector B){
		Double x0 = A.getX();
		Double z0 = A.getZ();
		Double x1 = B.getX();
		Double z1 = B.getZ();
		Node st = new Node(x0.intValue(), z0.intValue());
		Node en = new Node(x1.intValue(), z1.intValue());
		
		if(!setStart(st)){
			return false;
		}
		
		if(!tryEnd(en))return false;
		
		for(Node no: AllNodes){
			no.setH(no.distance(End));
		}
		return true;
	}
	
	/**
	 * Search for an alternative endpoint of the route if the player is not on a walkable tile. To avoid 
	 * stack overflow, a maximum of 10 iterations is used.
	 * @param en  
	 * 			The node you try to reach
	 * @return 
	 * 			True if an endpoint has been set, false if none has been found.
	 */
	public boolean tryEnd(Node en){
		int countTries = 0;
		while(countTries<10){
			if(setEnd(en))return true;
			countTries++;
			Node[] considered = en.getSuccessors();
			double shortestd = Double.MAX_VALUE;
			for(Node no: considered){
				double d = no.distance(Start);
				if(d<shortestd && AllNodes.contains(no)){
					shortestd=d;
					en = no;
				}
			}
		}
		return false;
	}
	
	/**
	 * PAS OP! Kan pas uitgevoerd worden na setRoute. 
	 * Zoekt met het A* algoritme de maze door totdat het einde gevonden is.
	 * Geeft true als het gelukt is binnen het maximum aantal iteraties.
	 * 
	 * @return
	 * 		True if a route has been found. False if the maximum iterations has passed without reaching the end point.
	 */
	public boolean explore(){
		Visited.clear();
		ActiveList.clear();
		CurrentNode = Start.clone();
		CurrentNode.setG(0);
		int count = 0;
		while(!(count > maxIterations)){
			Visited.add(CurrentNode.clone());
			succession();
			if(CurrentNode.equals(End)){
				End = CurrentNode.clone();
				return true;
			}
			if(ActiveList.size()>0){
				Collections.sort(ActiveList);
				CurrentNode = ActiveList.get(0).clone();
				ActiveList.remove(0);
			}
			count++;
		}
		return false;
	}
	
	/**
	 * Zoekt met explore de maze door en zet de gevonden snelste route in de arraylist Route.
	 * 
	 * @return
	 * 			ArrayList with all nodes of the route, ordered from start to end.
	 */
	public ArrayList<Node> findRoute(){
		ArrayList<Node> Route = new ArrayList<Node>();
		if(explore()){
			Route.add(End);
			Node next = End.clone();
			int count = 0;
			while(!next.equals(Start) && (count < maxIterations)){
				next = next.getParent();
				if(Route.contains(next)){
				}
				Route.add(next);
				count++;
			}
			if(next.equals(Start)){
			}
			Collections.reverse(Route);
		}
		return Route;
	}
	
	/**
	 * Zet van de currentNode alle nodes die ernaast liggen en zich in AllNodes bevinden in de ActiveList
	 */
	public void succession(){
		CurrentNode.setH(CurrentNode.distance(End));
		Node[] succ = CurrentNode.getSuccessors();
		for(int i = 0; i < 4; i++){
			int ind = AllNodes.indexOf(succ[i]);
			if(ind > -1){
				Node next = AllNodes.get(ind).clone();
				next.setH(next.distance(End));
				if(next.getG() > (CurrentNode.getG() +1)){
					next.setParent(CurrentNode.clone());
					next.findG();
				}if(!Visited.contains(next) && !ActiveList.contains(next)){
					ActiveList.add(next);
				}
			}
			
		}
	}
	
	/**
	 * Zet de StartNode op gegeven Node, als deze zich in AllNodes bevindt.
	 * @param A
	 * @return
	 */
	public boolean setStart(Node A){
		for(Node no: AllNodes){
			if(A.equals(no)){
				Start = no;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Zet de EndNode op gegeven Node, als deze zich in AllNodes bevindt.
	 * @param A
	 * @return
	 */
	public boolean setEnd(Node A){
		for(Node no: AllNodes){
			if(A.equals(no)){
				End = no;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verwijdert een Node van de pathfinding mogelijkheden. Gebruikt afgeronde waardes van x en z uit gegeven vector.
	 * @param in
	 */
	public static void removeNode(Vector in){
		Double x0 = in.getX();
		Double y0 = in.getZ();
		Node no = new Node(x0.intValue(), y0.intValue());
		AllNodes.remove(no);
	}
	
	/**
	 * Voegt een Node toe
	 * @param in
	 */
	public static void addNode(Vector in){
		Double x0 = in.getX();
		Double y0 = in.getZ();
		Node no = new Node(x0.intValue(), y0.intValue());
		AllNodes.add(no);
	}
	
	
}
