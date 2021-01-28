// Subject Interface 
// Ovezmyrat Arnazarov

import java.util.HashMap;

public interface Subject {
	
	public void add(Observer o);
	public void notifyObserver(String source, HashMap<Router, Integer> neighbors);
}
