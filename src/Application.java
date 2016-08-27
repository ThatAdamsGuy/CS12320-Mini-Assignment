import java.io.IOException;
import java.util.Scanner;

/** 
 * @Author Harry Adams
 * @Version 1.01 (18/03/2016)
 * 
 */

public class Application {

	public static Scanner scan;
	private Map map;

	public Application() {
		scan = new Scanner(System.in);
		map = new Map();
	}
		
	/**
	 * Loads the previous data, runs the menu.
	 * Saves at the end (when user quits) 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		Application app = new Application();
		app.load();
		app.runMenu();
		app.save();
	}

	/**
	 * Calls PrintMenu then waits for user response (must be integer, checks for this)
	 * Any int between 1-7 is acceptable, if outside this the menu prints again and waits for new input
	 * Calls relevant function when acceptable input is done.
	 * @throws IOException
	 */
	private void runMenu() throws IOException {
		int menuInput = 0;
		do {
			printMenu();
			menuInput = 0;
			// Checks if the input is an integer. If not, informs the user, and try again.
			try {
				menuInput = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
			}
			//Takes user input for the menu choice
			switch (menuInput) {
			case 1: // Create Settlement
				createSettlement();
				break;			
			case 2: // Edit Settlement
				map.editSettlement();
				break;
			case 3: // Delete Settlement
				map.deleteSettlement();
				break;			
			case 4: // Create Road
				createRoad();
				break;			
			case 5: // Delete Road
				map.deleteRoad();
				break;			
			case 6: // Display Map
				map.display();
				break;
			case 7: // Save Map
				map.save();
				break;
			case 8: // Quit
				break;
			default:
				System.out.println("Invalid menu choice.");
			}
		} while (menuInput != 8);	//Will run menu until the menu choice is 8 - Quit
	}

	/**
	 * Asks user for the SettlementType (HAMLET, VILLAGE, TOWN, CITY) as per the ENUM
	 * If doesn't match, ask again
	 * @return result (SettlementType)
	 */
	public static SettlementType askForSettlementType() {
		SettlementType result = null;
		boolean valid;
		do {
			valid = false;
			System.out.print("Enter a settlement classification: ");
			for (SettlementType i : SettlementType.values()) {	//Prints out the SettlementType ENUM
				System.out.print(i + " ");
			}
			String choice = scan.nextLine().toUpperCase();	//Gets user input, and makes it uppercase
			try {
				result = SettlementType.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae) {	//Returns error, if the input does not match the ENUM
				System.out.println(choice + " is not one of the options. Try again.");
			}
		} while (!valid);
		return result;
	}

	/**
	 * Asks user for the Road Classification (M,A,B,U) as per the ENUM
	 * If doesn't match, ask again
	 * Very similar to askForSettlementType
	 * @return result (Classification)
	 */
	public static Classification askForRoadClassifier() {
		Classification result = null;
		boolean valid;
		do {
			valid = false;
			System.out.print("Enter a road classification: ");
			for (Classification cls : Classification.values()) {
				System.out.print(cls + " ");
			}
			String choice = scan.nextLine().toUpperCase();
			try {
				result = Classification.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae) {
				System.out.println(choice + " is not one of the options. Try again.");
			}
		} while (!valid);
		return result;
	}

	/**
	 * Prints the menu
	 */
	private void printMenu() {
		System.out.println("MENU");
		System.out.println("~~~~");
		System.out.println();
		System.out.println("1 - Create Settlement");
		System.out.println("2 - Edit Settlement");
		System.out.println("3 - Delete Settlement");
		System.out.println("4 - Create Road");
		System.out.println("5 - Delete Road");
		System.out.println("6 - Display Map");
		System.out.println("7 - Save Map");
		System.out.println("8 - Quit");
	}

	/**
	 * Returns the next line input as a string
	 * @return scan.nextLine()
	 */
	public static String readInput() {
		return scan.nextLine();
	}

	/**
	 * Creates a new settlement
	 * Checks if Settlement already exists (only using exactly what was input
	 * Can have same settlements but with different pops. etc
	 * Creates settlement and adds it to arraylist
	 */
	private void createSettlement() {
		int pop = 0;
		boolean intCheck = false;
		System.out.print("Name of settlement: ");
		String name = readInput();
		name = capitaliseString(name); // Capitalises the first letter of the
										// name. Good grammar.
		SettlementType type = askForSettlementType();

		// Asks for population of settlement
		// If input is not integer, inform must be an int, and ask again.
		while (!intCheck) {
			System.out.print("Population of settlement: ");
			try {
				pop = Integer.parseInt(readInput());
				intCheck = true;
			} catch (NumberFormatException e) {
				System.out.println("Input must be an integer!");
				intCheck = false;
			}
		}
		Settlement temp = new Settlement(name, type, pop);
		boolean exists = map.checkSettlementExists(temp);
		if (!exists) {	//If doesn't exist, add to arrayList of settlements
			map.addSettlement(temp);
			System.out.println("Settlement added successfully.");
		} else {		//If it exists, inform user, and do not add to the arrayList
			System.out.println("Settlement already exists!");
			System.out.println("Settlement NOT added successfully.");
		}

	}
		
	/**
	 * Creates a new road.
	 * Takes name from user, and the names of the source and destination settlements
	 * Function finds these settlements by name
	 * Also takes length, makes new road, adds it to ArrayList
	 */
	private void createRoad(){
		System.out.print("Name of road: ");
		String name = readInput();
		boolean intCheck = false;
		double length = 0;
		
		Classification roadType = askForRoadClassifier();
		
		System.out.print("Name of Source Settlement: ");
		String sourceName = readInput();
		Settlement sourceSet = map.getSettlementByName(sourceName);
		if (sourceSet != null){//If the Settlement is found (so NOT Null) continue
		}
		else { //Cancels creating road if it the source settlement is a null (does not exist)
			System.out.println("This is not a settlement! Aborting.");
			return;
		}
		
		System.out.print("Name of Destination Settlement: ");
		String destName = readInput();
		Settlement destSet = map.getSettlementByName(destName);
		if (destSet != null){ 
		}
		else {	//Cancels creating road if it the destination settlement is a null (does not exist)
			System.out.println("This is not a settlement! Aborting.");
			return;
		}
		if (sourceSet.equals(destSet)){	//Checks if the source and destination settlement are equal.
			System.out.println("Road cannot have the same source and destination! Aborting.");
			return;
		}		
		
		while (!intCheck) {
			System.out.print("Length of road: ");
			try {
				length = Double.parseDouble(readInput()); //Turns input into integer
				intCheck = true;
			} catch (NumberFormatException e) {
				System.out.println("Input must be a number! (Can be decimal)");
				intCheck = false;
			}
		}
				
		Road temp = new Road(name, roadType, sourceSet, destSet, length);
		
		if (!map.checkRoadExists(temp)){
			map.addRoad(temp);
			System.out.println("Road added successfully.");
		} else {
			System.out.println("Road already exists!");
			System.out.println("Road NOT added successfully.");
		}
			
	}
	
	/**
	 * Takes a string and capitalises the first letter.
	 * Used for grammatical reasons.
	 * @param name
	 * @return
	 */
	public String capitaliseString(String name) {
		char firstChar = name.charAt(0); // Take the first character
		String remainingString = name.substring(1).toLowerCase(); //Make substring from remaining chars, to lowercase
		firstChar = Character.toUpperCase(firstChar); //Capitalise the first character
		String properName = firstChar + remainingString; // Append the strings
		return properName; // Return the string
	}
	
	/**
	 * Save the current list of settlements and roads
	 * 
	 */
	private void save() throws IOException  {
		map.save();
	}
	
	/**
	 * Load list of settlements and roads from a file
	 */
	private void load() throws IOException {
		map.load();
	}

}
