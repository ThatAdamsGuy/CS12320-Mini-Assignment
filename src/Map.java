import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * Map contains majority of information, including the settlements and roads.
 * Contains the functions for saving, loading, adding and deleting from ArrayLists
 * @author Harry Adams
 * @version 1.01 (18th March 2016)
 *
 */

public class Map {

	private ArrayList<Settlement> listOfSettlements = new ArrayList<Settlement>();
	private ArrayList<Road> roads = new ArrayList<Road>();
	//private Scanner scan;
	
	/**
	 * Add newRoad to the arraylist of roads
	 * @param newRoad
	 */
	public void addRoad(Road newRoad){
		roads.add(newRoad);
	}

	/**
	 * Adds the newSettlement to the arraylist of Settlements (listOfSettlements)
	 * @param newSettlement
	 * @throws IllegalArgumentException
	 */
	public void addSettlement(Settlement newSettlement) throws IllegalArgumentException {
		listOfSettlements.add(newSettlement);
	}
	
	/**
	 * Edits the settlement
	 * Gives user the choice of settlements, and upon choosing one, can change population, settlement type, or cancel
	 * Then makes the change
	 */
	public void editSettlement() {
		boolean intCheck = false;
		int choice = 0;
		for (int i = 0; i < listOfSettlements.size(); i++) {
			System.out.println(i + " - " + listOfSettlements.get(i).toString());
		}
				
		while (!intCheck) {
			System.out.print("Which number settlement would you like to edit?");
			try {
				String tempInput = Application.readInput();
				choice = Integer.parseInt(tempInput);
				intCheck = true;
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
				intCheck = false;
			}
		}
		
		Settlement settlementChoice = listOfSettlements.get(choice);
		intCheck = false;
		choice = 0;
		
		System.out.println("1 - Change Population");
		System.out.println("2 - Change Settlement Type");
		System.out.println("3 - Cancel");
		
		while (!intCheck) {
			System.out.print("Which element would you like to change?");
			try {
				String tempInput = Application.readInput();
				choice = Integer.parseInt(tempInput);
				intCheck = true;
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
				intCheck = false;
			}
		}
		intCheck = false;
		switch(choice){
		case 1: 
			int pop = 0;
			while (!intCheck) {
				System.out.print("New population: ");
				try {
					String tempInput = Application.readInput();
					pop = Integer.parseInt(tempInput);
					intCheck = true;
				} catch (NumberFormatException e) {
					System.out.println("Input must be an integer!");
					intCheck = false;
				}
			}
			settlementChoice.setPop(pop);
			break;
		case 2:
			System.out.print("New Settlement Type: ");
			SettlementType type = Application.askForSettlementType();
			settlementChoice.setType(type);
			break;
		case 3:
			break;
		}
		
	}
	
	/**
	 * Checks if a settlement already exists
	 * @param temp
	 * @return boolean
	 */
	public boolean checkSettlementExists(Settlement temp) {
		boolean exists = false;
		for (int i = 0; i < listOfSettlements.size(); i++) {
			Settlement compare = listOfSettlements.get(i);
			if (temp.equals(compare)){
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	/**
	 * Checks if a road already exists
	 * @param temp
	 * @return boolean
	 */
	public boolean checkRoadExists(Road temp){
		boolean exists = false;
		for (int i = 0; i < roads.size(); i++){
			if (temp.equals(roads.get(i))) {
				exists = true;
			}
		}
		return exists;
	}
	
	/**
	 * Deletes a settlement from the arraylist
	 */
	public void deleteSettlement() {
		boolean intCheck = false;
		int removal = 0;
		for (int i = 0; i < listOfSettlements.size(); i++) {
			System.out.println(i + " - " + listOfSettlements.get(i).toString());
		}
		int cancel = listOfSettlements.size();
		System.out.println(cancel + " - Cancel");
		while (!intCheck) {
			System.out.print("Which number settlement would you like to remove? ");
			try {
				String tempInput = Application.readInput();
				removal = Integer.parseInt(tempInput);
				intCheck = true;
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
				intCheck = false;
			}
		}
		if (removal == cancel){
			System.out.println("Settlement deletion aborted.");
			return;
		}
		System.out.println(listOfSettlements.get(removal).getName() + " - Removed.");
		checkForAndDeleteRoadsAttached(listOfSettlements.get(removal).getName());
		listOfSettlements.remove(removal);
		
	}
	
	/**
	 * Deletes a road from the arraylist
	 */
	public void deleteRoad(){
		boolean intCheck = false;
		int removal = 0;
		for (int i = 0; i < roads.size(); i++) {
			System.out.println(i + " - " + roads.get(i).toString());
		}
		int cancel = roads.size();
		System.out.println(cancel + " - Cancel");
		while (!intCheck) {
			System.out.print("Which number settlement would you like to remove?");
			try {
				String tempInput = Application.readInput();
				removal = Integer.parseInt(tempInput);
				intCheck = true;
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
				intCheck = false;
			}
		}
		if (removal == cancel){
			System.out.println("Road deletion aborted.");
			return;
		}
		System.out.println(roads.get(removal).getName() + " - Removed.");
		roads.remove(removal);
	}
	
	/**
	 * Prints the map
	 * Runs through listOfSettlements arraylist, and prints the toString for the settlement
	 * Checks if any roads are attached, and if so, prints these too
	 */
	public void display() {
		for (int i = 0; i < listOfSettlements.size(); i++) {
			Settlement currentSet = listOfSettlements.get(i);
			System.out.println(currentSet.toString());
			printAttachedRoads(listOfSettlements.get(i).getName());
		}
	}

	/**
	 * Takes a name as input, and checks if a settlement with this name exists
	 * @param name
	 * @return
	 */
	public Settlement getSettlementByName(String name){
        //Iterate over all the settlements in the arraylist in order to try and find the correct settlement
        for (Settlement settlement : listOfSettlements){
            if (settlement.getName().equals(name)){
                return settlement;
            }
        }
        return null;
    }
	
	/**
	 * Clears the previous arraylists to avoid duplications
	 * Reads from the settlements.txt and roads.txt and adds these to relevant arraylists
	 * @throws IOException
	 */
    public void load() throws IOException {
        listOfSettlements.clear();
        Scanner file = new Scanner(new FileReader("settlements.txt"));
        file.useDelimiter(":|\r?\n|\r");
        int number = Integer.parseInt(file.nextLine());
        if (number > 0){        
        for(int i = 0; i < number; i++){
            String inputLine = file.nextLine();           
            String tempSet[];
            tempSet = inputLine.split(";");
            Settlement load = new Settlement(tempSet[0], SettlementType.valueOf(tempSet[1]), Integer.parseInt(tempSet[2]));
            listOfSettlements.add(load);
        }
        }
        file.close();
       
        roads.clear();
        Scanner file2 = new Scanner(new FileReader("roads.txt"));
        file2.useDelimiter(":|\r?\n|\r");
                
        number = Integer.parseInt(file2.nextLine());
        
        if (number > 0){
        for(int i = 0; i < number; i++){
        	String inputLine = file2.nextLine();           
            String tempRoad[];
            tempRoad = inputLine.split(";");
            
            
            Settlement sourceSet = getSettlementByName(tempRoad[2]);
            
            Settlement destinationSet = getSettlementByName(tempRoad[3]);
            
            Road load = new Road(tempRoad[0], Classification.valueOf(tempRoad[1]), 
            		sourceSet, destinationSet, Double.parseDouble(tempRoad[4]));
            roads.add(load);
        }
        }
        file2.close();
    }
   
    /**
     * Goes through each item of the arrayList (roads and settlements)
     * Saves each as a "SaveString" with each item separated by semicolon
     * @throws IOException
     */
    public void save() throws IOException {
        // STEP 6: INSERT CODE HERE
        PrintWriter file = new PrintWriter(new FileWriter("settlements.txt"));
        file.println(listOfSettlements.size());
        for(Settlement i : listOfSettlements){
            file.println(i.toSaveString());
        }
        file.close();
       
        PrintWriter file2 = new PrintWriter(new FileWriter("roads.txt"));
        file2.println(roads.size());
        for(Road i : roads){
            file2.println(i.toSaveString());
        }
        file2.close();
        System.out.println("Map saved successfully.");
    }
	
    /**
     * Runs when deleting a settlement
     * Checks if any roads have that settlement as a source or destination
     * If so, deletes the road
     * @param name
     */
	public void checkForAndDeleteRoadsAttached(String name){
		for (int i = 0; i < roads.size(); i++){
			String source = roads.get(i).getSourceSettlement().getName();
			String dest = roads.get(i).getDestinationSettlement().getName();
			
			if (name.equals(dest) || name.equals(source)){	//Checks if the deleted name matches and roads with it as a source or dest.
				roads.remove(i);
				i--;
			}
		}
	}

	/**
	 * Checks arraylist of roads, and if one has the input as source or destination settlement
	 * it prints the tostring for this road
	 * Used in the display map.
	 * @param input
	 */
	public void printAttachedRoads(String input) {
		int count = 0;
		for (int i = 0; i < roads.size(); i++){
			if (input.equals(roads.get(i).getDestinationSettlement().getName()) || input.equals(roads.get(i).getSourceSettlement().getName())){
				System.out.println("     " + roads.get(i).toString());
				count++;
			}
		}
		if (count == 0){	//If no roads are attached, print this
			System.out.println("     No roads attached");
		}
	}
	
}
