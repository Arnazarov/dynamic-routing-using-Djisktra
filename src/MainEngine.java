// MainEngine class 
// Ovezmyrat Arnazarov
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class MainEngine {
	public static void main(String[] args) {
		
		while (true){
			System.out.println("====================================================================");
			System.out.println("Network Terminal-Based Simulator for the Dynamic Routing Capability:");
			System.out.println("====================================================================\n");
			System.out.println("------------------ STEP 1: INITIALIZING ROUTERS --------------------\n");
			String textFileContent = "";
			
			// INITIALIZING ROUTERS	-------------------------------------------------------------------------------------------------------------		
			try {

				System.out.println("Enter Text File Name to Initialize the Roters and Neighbors:"); 
				Scanner in = new Scanner(System.in);
				String textFileName = in.nextLine();
				Scanner sc = new Scanner(new File(textFileName));

				while (sc.hasNextLine()) {
					textFileContent += sc.nextLine() + "\n"; 
				}
			}
			catch(FileNotFoundException e){
				System.out.println("Error ocured " + e);
			}
			String[] lines = textFileContent.split("\n");
			HashMap<String, Router> routers = new HashMap<>();
			System.out.println("\nInitializing");
			System.out.println(".\n.\n.\n");
			
			// GENERATING ROUTERS ONE BY ONE -------------------------------------------------------------------------------------------------------------	
			for (int i = 1; i < lines.length; i++)
			{
				String[] temp = lines[i].split(":");
				String routerName = temp[0].strip();
				Router router = new Router(routerName); // Creating a new Router Object
				routers.put(routerName, router);
			}	

			// ADDING NEIGHBOURS & DESTINATIONS TO EACH ROUTER -------------------------------------------------------------------------------------------
			for (int i = 1; i < lines.length; i++)
			{
				String[] temp = lines[i].split(":");
				String routerName = temp[0].strip();
				Router currentRouter = routers.get(routerName);

				//currentRouter.setRouters(routers);

				String rightSide = temp[1].replaceAll("[()]", "");
				String[] temp1 = rightSide.split(",");
				for (int j = 0; j < temp1.length; j+=2)
				{
					currentRouter.addNeighbor(routers.get(temp1[j]), Integer.parseInt(temp1[j+1]));
				}
			}
			System.out.println("Initialization Process Completed!!!\n"); 			
			System.out.println("------------ STEP 2: RUNNING BUILDER AND LISTENER FUNCTIONS --------\n");
			
			// BUILDING & LISTENING ---------------------------------------------------------------------------------------------------------------
			System.out.println("Building and Listening Packets");
			System.out.println(".\n.\n.\n");
			System.out.println("Generating Routing Tables");
			System.out.println(".\n.\n.\n");
			for (Router router: routers.values()) {
				router.setRouters(routers);
			}

			for (Router router: routers.values()) {
				router.builder();
				//router.updateRoutingTable();
			}
			System.out.println("----------------- STEP 3: DISPLAYING ROUTING TABLES ----------------\n");
			for (int m = 1; m <= routers.size(); m++) {
				routers.get("R"+m).displayTable();
			}
			                
			System.out.println("===================================");
			System.out.println("CHOOSE FROM BELOW OPTIONS:\nSTEP 4. CHANGE COST BETWEEN ROUTERS \nSTEP 5. EXIT\n");	
			System.out.println("Command: (enter a numeric value i.e 4 or 5)");
			Scanner input = new Scanner(System.in);
			String regmessage = input.nextLine();
			if (regmessage.equals("4")){
				System.out.println("Typing Format Example: R1.Change_Cost(R2,400)");
				System.out.println("Enter: ");
				Scanner scr = new Scanner(System.in);
				String scrMessage = scr.nextLine();
				if (scrMessage.startsWith("R") && scrMessage.endsWith(")") && scrMessage.contains("Change_Cost")) {
					
					String[] arr = scrMessage.split("\\.");
					String temp = arr[1].replaceAll("Change_Cost", "");
					temp = temp.replaceAll("[()]","");
					String[] costChange = temp.split(",");
					
					Router tempRouter = routers.get(arr[0]);
					Router neighbor = routers.get(costChange[0]);
					tempRouter.updateNeighbor(neighbor, Integer.parseInt(costChange[1]));
					routers.replace(arr[0], tempRouter);
					
					for (Router router: routers.values()) {
						router.builder();
					}
					System.out.println("\n-------------- STEP4: DISPLAYING UPDATED ROUTING TABLES ----------\n");
					for (int m = 1; m <= routers.size(); m++) {
						routers.get("R"+m).displayTable();
					}
					regmessage = "5";
				}
				else
				{
					System.out.println("Wrong syntax. Try again next time!");
					regmessage = "5";
				}
			}	
			if (regmessage.equals("5")){				
				System.out.println("EXITING SIMULATOR...\nGOOD BYE!!!");
				System.exit(0);   	// Exit Simulator	
			}
		} // end while loop
	} // end main method
}
