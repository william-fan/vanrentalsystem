import java.util.ArrayList;

public class Location {
	private String name;
	private ArrayList<Van> vanList = new ArrayList<Van>();
	
	/**
	 * @param name The name of the location.
	 */
	public Location (String name) {
		this.name = name;
	}
	
	/**
	 * Get the name of the location.
	 * @return The name of the location.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the location.
	 * @param name The name of the location.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the van list stored in the location.
	 * @return The van list stored in the location.
	 */
	public ArrayList<Van> getVanList() {
		return vanList;
	}

	/**
	 * Set the van list stored in the location.
	 * @param vanList The van list stored in the location.
	 */
	public void setVanList(ArrayList<Van> vanList) {
		this.vanList = vanList;
	}
	
}
