import java.util.Date;

/**
 *
 * The booking is the appointments made for the van rental. The information for a
 * booking is stored in the class. The {@link Campervan} stores each booking in
 * a list.
 *
 * @inv start is before end
 * @author jung <z5060165@student.unsw.edu.au>
 *
 */
public class Booking {

	private int id;
	private Date start;
	private Date end;
	
	/**
	 * The constructor for the Booking class
	 * @param id of the booking
	 * @param start of the booking
	 * @param end of the booking
	 */
	public Booking(int id, Date start, Date end) {
		this.id = id;
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the id of the booking
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id of the booking
	 * @param id the id of the booking to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the start of the booking
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Sets the start if the biijubg
	 * @param start the start of the booking to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end of the booking
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * Sets the end of the booking
	 * @param end the end of the booking to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	
}
