/**
 * Represents a road that is linked to two settlements: source and destination.
 * @author Harry Adams
 * @version 1.01 (18/03/2016)
 */
public class Settlement {
	private String name;
	private int population;
	private SettlementType kind;
	
	/**
	 * Constructor to build a settlement
	 * @param nm The name of the settlement
	 */
	public Settlement(String nm, SettlementType k, int pop){
		name = nm;
		kind = k;
		population = pop;
	}
	
	/**
	 * The name of the settlement. Note that there is no way to change
	 * the name once created.
	 * @return The name of the settlement. 
	 */
	public String getName() {
		return name;
	}

	public void setPop(int pop){
		population = pop;
	}
	
	public void setType(SettlementType type){
		kind = type;
	}
	
	/**
	 * The state of the settlement 
	 * @return The data in the settlement object.
	 */
	public String toString() {
		String result = "Settlement[Name = " + name + ". Type = " + kind + ". Population = " + population + "]";
		/*for(Road r: roads){
			result += r.toString();
		}*/
		return result;
	}
	
	/**
	 * Prints string in a save format (separated by semicolon)
	 * @return data in settlement object
	 */
	public String toSaveString(){
		String result = name + ";" + kind + ";" + population;
		return result;
	}
	
}
