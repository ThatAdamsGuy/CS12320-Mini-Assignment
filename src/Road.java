/**
 * Represents a settlement with a name, population, type and
 * with connected roads.
 * @author Harry Adams
 * @version 1.01 (18th March 2016)
 */
public class Road {
	private String name;
	private Classification classification;
	private Settlement sourceSettlement;
	private Settlement destinationSettlement;
	private double length;
	
	/**
	 * Road constructor
	 * @param nm The road name
	 * @param classifier The class of road, e.g. 'A'
	 * @param source The source settlement
	 * @param destination The destination settlement (can be the same as the source!)
	 */
	public Road(String nm, Classification classifier, Settlement source, Settlement destination, double len){
		name = nm;
		classification = classifier;
		sourceSettlement = source;
		destinationSettlement = destination;
		length = len;
	}
	
	/**
	 * The name of the road
	 * @return The road's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The source settlement
	 * @return One end of the road we call source
	 */
	public Settlement getSourceSettlement() {
		return sourceSettlement;
	}
	
	/**
	 * The destination settlement
	 * @return One end of the road we call destination
	 */
	public Settlement getDestinationSettlement() {
		return destinationSettlement;
	}

	/**
	 * Compiles information about the road into a string
	 * @return String
	 */
	public String toString() {
		String result = "Road [Name: " + name + ". Class: " + classification + ". Source: " + sourceSettlement.getName()
						+ ". Destination: " + destinationSettlement.getName() + ". Length: " + length + "]";
		return result;
	}
	
	
	/**
	 * Compiles information about roads to the format
	 * used for saving
	 * @return
	 */
	public String toSaveString() {
		String result = name + ";" + classification + ";" + sourceSettlement.getName() + ";" + destinationSettlement.getName() + ";" + length;
		return result;
	}
}
