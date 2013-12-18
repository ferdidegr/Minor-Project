package Intelligence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import Utils.*;

public class AStar {
	
	private PriorityQueue<Node> ActiveList;
	private ArrayList<Node> Route;
	private ArrayList<Node> AllNodes;
	private Node CurrentNode;
	private Node Start;
	private Node End;
	private int maxIterations = 50;
	
	/**
	 * Nodig om te initialiseren
	 */
	public AStar(){
		ActiveList = new PriorityQueue<Node>();
		Route = new ArrayList<Node>();
		AllNodes = new ArrayList<Node>();
	}
	
	/**
	 * Maakt van gegeven Maze alle punten die geen muur zijn een Node, en zet deze in AllNodes.
	 * @param maze
	 */
	public void loadMaze(int[][] maze){
		System.out.println("Loading maze");
		for(int j = 0; j < maze.length; j++){
			for(int i = 0; i<maze[0].length; i++){
				if(maze[j][i] < 1 | maze[j][i] > 11){
					Node no = new Node(j,i);
					AllNodes.add(no);
				}
			}
		}
	}
	
	/**
	 * Zet het eind- en beginpunt van de route als deze beloopbaar zijn (dus in allnodes zitten). Geeft true als dit gelukt is.
	 * Van vector A en B wordt hiervoor het x- en z-coordinaat gebruikt, die naar beneden afgerond worden.
	 * @param A
	 * @param B
	 * @return
	 */
	public boolean setRoute(Vector A, Vector B){
		Double x0 = A.getX();
		Double y0 = A.getZ();
		Double x1 = B.getX();
		Double y1 = B.getZ();
		Node st = new Node(x0.intValue(), y0.intValue());
		Node en = new Node(x1.intValue(), y1.intValue());
		
		if(!setStart(st) | !setEnd(en)){
			System.out.println("failed");
			return false;
		}
		
		for(Node no: AllNodes){
			no.setH(no.distance(End));
		}
		return true;
	}
	
	/**
	 * PAS OP! Kan pas uitgevoerd worden na setRoute. 
	 * Zoekt met het A* algoritme de maze door totdat het einde gevonden is.
	 * Geeft true als het gelukt is binnen het maximum aantal iteraties.
	 * @return
	 */
	public boolean explore(){
		CurrentNode = Start;
		int count = 0;
		while(!CurrentNode.equals(End) && !(count > maxIterations)){
			succession();
			CurrentNode = ActiveList.poll();
			count++;
		}
		if(CurrentNode.equals(End)){
			return true;
		}
		return false;
	}
	
	/**
	 * Zoekt met explore de maze door en zet de gevonden snelste route in de arraylist Route.
	 */
	public void findRoute(){
		if(explore()){
			Route.add(End);
			Node next = End;
			while(!next.equals(Start)){
				next = next.getParent();
				Route.add(next);
			}
			Collections.reverse(Route);
		}
	}
	
	/**
	 * Geeft een print van de gevonden Route
	 */
	public void printRoute(){
		for(Node no: Route){
			System.out.println(no.toString());
		}
	}
	
	/**
	 * Zet van de currentNode alle nodes die ernaast liggen en zich in AllNodes bevinden in de ActiveList
	 */
	public void succession(){
		Node[] succ = CurrentNode.getSuccessors();
		for(int i = 0; i < 4; i++){
			int ind = AllNodes.indexOf(succ[i]);
			if(ind > -1){
				Node add = AllNodes.get(ind);
				add.setH(add.distance(End));
				if(add.getG() > (CurrentNode.getG() +1)){
					add.setParent(CurrentNode);
					add.findG();
				}
				ActiveList.add(AllNodes.get(ind));
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
	
	
	
}
