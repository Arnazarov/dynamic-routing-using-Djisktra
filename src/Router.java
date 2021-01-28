// Router & Dijkstra classes 
// Ovezmyrat Arnazarov

import java.util.*;
public class Router implements Observer{
	
	// instance variables
	public String label;									// Label
	HashMap<Router, Integer> neighbors;						// Neighbors
	private HashMap<String, Router> routers;				// Destination Routers
	private HashMap<String, ShortestPath> costs;			// Costs
	private Subject messenger;
	
	// constructor
	public Router(String routerName)	{
		label = routerName;
		neighbors = new HashMap<Router, Integer>();
		messenger = new Messenger();

	}
	
	// adding neighbors of a router and corresponding costs
	public void addNeighbor(Router neighbor, int cost) {
		neighbors.put(neighbor, cost);
	}
	
	// update cost of a neighbor
	public void updateNeighbor(Router neighbor, int cost) {
		neighbors.replace(neighbor, cost);
	}
	
	// builder function
	public void builder() {
		messenger.notifyObserver(label, neighbors);
	}
	
	// listener function
	public void listener(String source, HashMap<Router, Integer> neighbors) {
		updateRoutingTable();
	}

	// generate all destination routers in a routing table
	public void setRouters(HashMap<String, Router> routers) {
		this.routers = routers;
		for(Router router: routers.values()) {
			if(router.label != label) {
				messenger.add(router);
			}
		}
	}
	
	// prints out routing table
	public void displayTable()
	{
		System.out.println("Router: " + label);
		System.out.println("Destination  " + "Neighbor  " + "Cost");
		for(String routerName: costs.keySet())
			System.out.println("  " + routerName + costs.get(routerName));
		
		System.out.println();
	}
	
	// generates and updates routing tables using Dijkstra algorithm
	public void updateRoutingTable() {
		costs = Dijkstra.Djikstra(routers, this);
	}
	
	// class that implements Dijsktra algorithm
	static class Dijkstra {
		
		public static HashMap<String, ShortestPath> Djikstra(HashMap<String, Router> routers, Router source){
		    
			HashMap<String, ShortestPath> costs = new HashMap<String, ShortestPath>();    // map that stores destinations neighbors and costs
		    Queue<CostDesc> queue = new LinkedList<CostDesc>(); 		// queue that stores all neighbors 
		    HashSet<String> visited = new HashSet<String>();			// set that stores visited routers

		    queue.add(new CostDesc(source, 0));
		    
		    // initial filling of routing tables with 0 and Infinity values
		    for(Router router: routers.values()){
		        if(router.label == source.label){
		            costs.put(source.label, new ShortestPath(null, 0));
		        } else{
		            costs.put(router.label, new ShortestPath(null, Integer.MAX_VALUE));
		        }
		    }
		    
		    // computing shortest path from source router to all destination routers
		    while(queue.size() > 0) {
		        CostDesc current = queue.remove();
		        
		        if(!visited.contains(current.destination.label)){
		            HashMap<Router, Integer> neighbors = current.destination.neighbors;
		            
		            for(Router router : neighbors.keySet()){
		                ShortestPath currentCost = costs.get(router.label);               
		                int newCost = current.cost + neighbors.get(router);
		                
		                if(newCost < currentCost.cost) {
		                    currentCost.cost = newCost;
		                    ShortestPath path = costs.get(current.destination.label);

		                    if(source.label != current.destination.label) {
			                    if(path.neighbor == null) {
			                        currentCost.neighbor = current.destination.label;
			                    } 
			                    else{
			                        currentCost.neighbor = path.neighbor;
			                    }
		                    } 
		                    else {	
		                    	currentCost.neighbor = router.label;
		                    }
		                    costs.put(router.label, currentCost);
		                }	                
		                if(!visited.contains(router.label)) {
		                    queue.add(new CostDesc(router, currentCost.cost));
		                }
		            } // end for loop  
		            visited.add(current.destination.label);
        
		        }  // end if-statement 		   
		        
		    } //end while loop
		    
		    return costs;
		}
	}
}
