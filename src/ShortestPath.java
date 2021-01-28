// Shortest Path class 
// Ovezmyrat Arnazarov

// class that stores neighbor router and cost
public class ShortestPath {
	String neighbor;
	int cost;
	
	public ShortestPath(String neighbor, int cost)
	{
		this.neighbor = neighbor;
		this.cost = cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public String toString() {
		return "           " + (neighbor == null ? "- " : neighbor) + "      " + Integer.toString(cost);
	}
}
