// Messenger class 
// Ovezmyrat Arnazarov

import java.util.ArrayList;
import java.util.HashMap;

public class Messenger implements Subject {
	
	private ArrayList<Observer> routers;
	
	public Messenger() {
		
		// Creates an ArrayList to hold all observers
		routers = new ArrayList<Observer>();
	}
	
	
	@Override
	public void add(Observer newRouter) {
		
		// Adds an observer (neighbor) to the ArrayList
		routers.add(newRouter);
	}
	@Override
	public void notifyObserver(String source, HashMap<Router, Integer> neighbors) {
		
		// Cycle through all observers and notify them of changes
		for (Observer observer : routers){		
			observer.listener(source, neighbors);
		}
	}
}
