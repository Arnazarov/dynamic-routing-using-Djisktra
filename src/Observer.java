// Observer Interface 
// Ovezmyrat Arnazarov

import java.util.HashMap;

public interface Observer 
{
	public void listener(String label, HashMap<Router, Integer> neighbors);
}
