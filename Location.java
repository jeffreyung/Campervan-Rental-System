import java.util.ArrayList;
import java.util.List;

/**
 *
 * Each location has a list of campervan(s) and a unique name. The locations are stored in
 * a list in the {@link VanRentalSystem} class.
 * 
 * @author jung <z5060165@student.unsw.edu.au>
 *
 */
public class Location {

	private String name;
	private List<Campervan> campervans;
	
	public Location(String name) {
		this.name = name;
		this.campervans = new ArrayList<Campervan>();
	}
	
	/**
	 * @return the name of the location
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name of the location to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the campervans in the location
	 */
	public List<Campervan> getCampervans() {
		return campervans;
	}

	/**
	 * @param campervans in the location to set
	 */
	public void setCampervans(List<Campervan> campervans) {
		this.campervans = campervans;
	}
	
}
