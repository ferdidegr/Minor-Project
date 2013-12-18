package Intelligence;

public class Node implements Comparable<Node> {
	
	protected int x;
	protected int y;
	private int G;
	private double H;
	private Node parent;
	
	/**
	 * Nodig om te initialiseren
	 * @param x
	 * @param y
	 */
	public Node(int x, int y){
		this.x=x;
		this.y=y;
		this.G = Integer.MAX_VALUE;
		this.H = Double.MAX_VALUE;
	}
	
	/**
	 * Geeft een volledige kopie van de Node
	 */
	public Node clone(){
		Node res = new Node(x, y);
		res.setG(G);
		res.setH(H);
		res.setParent(parent);
		return res;
	}
	
	/**
	 * Set de parent node.
	 * @param p
	 */
	public void setParent(Node p){
		this.parent = p;
	}
	
	/**
	 * Geeft een array van vier Nodes die de buren van deze Node zijn
	 * @return
	 */
	public Node[] getSuccessors(){
		
		Node[] res = new Node[4];
		
		res[0] = new Node((x+1),y);
		res[1] = new Node((x-1),y);
		res[2] = new Node(x,(y-1));
		res[3] = new Node(x,(y+1));
		
		return res;
	}
	
	/**
	 * Reset G met behulp van de parent node
	 */
	public void findG(){
		G = parent.getG() + 1;
	}
	
	/**
	 * Set G handmatig
	 * @param G
	 */
	public void setG(int G){
		this.G = G;
	}
	
	/**
	 * Set H handmatig
	 * @param H
	 */
	public void setH(double H){
		this.H=H;
	}
	
	/**
	 * Vergelijkt Node met gegeven object.
	 * PAS OP! Checkt alleen x en y.
	 */
	@Override
	public boolean equals(Object other){
		if(other instanceof Node){
			Node that = (Node) other;
			if(this.x == that.getX() && this.y == that.getY()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Geeft de afstand tot andere node mbv pythagoras.
	 * @param other
	 * @return
	 */
	public double distance(Node other){
		return Math.sqrt(x * other.x + y * other.y);
	}
	
	/**
	 * Nodig voor geordende lijst. Ordening is op basis van hoogste F (=G+H)
	 */
	@Override
	public int compareTo(Node no) {
	    Double sum = no.getF();
	    return sum.compareTo(getF());
	}
	
	/**
	 * ToString methode, geeft "x, y"
	 */
	public String toString() {
	    return x + ", " + y;
	}
	
	 // GETTERS
	public int getX(){		return x;	}
	public int getY(){		return y;	}
	public int getG(){		return G;	}
	public double getF(){		return G + H;	}
	public double getH(){		return H;	}
	public Node getParent(){		return parent;	}

}
