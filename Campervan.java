import java.util.ArrayList;
import java.util.List;

/**
 *
 * The campervan is the vehicle used in the van rental. The Campervan class stores all the
 * bookings in a list and has a unique name as well as a unique type (Automatic or Manuel).
 *
 * @author jung <z5060165@student.unsw.edu.au>
 *
 */
public class Campervan {

	private String depot;
	private String name;
	private String type;
	private List<Booking> bookings;
	
	/**
	 * The constructor for the Location class
	 * @param name of the campervan
	 * @param type of transmission of the campervan
	 */
	public Campervan(String depot, String name, String type) {
		this.depot = depot;
		this.name = name;
		this.type = type;
		this.bookings = new ArrayList<Booking>();
	}
	
	/**
	 * @return the depot of the campervan
	 */
	public String getDepot() {
		return depot;
	}

	/**
	 * @param depot of the campervan to set
	 */
	public void setDeport(String depot) {
		this.depot = depot;
	}
	
	/**
	 * @return the name of the campervan
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name of campervan to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type of transmission of the campervan
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type of transmission of the campervan
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the bookings for the location
	 */
	public List<Booking> getBookings() {
		return bookings;
	}

	/**
	 * @param bookings for the location to set
	 */
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
}
