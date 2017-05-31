import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * The {@link VanRentalSystem} class handles the appointments and utilizes the
 * console to retreive inputs from the user.
 *
 * @author jung <z5060165@student.unsw.edu.au>
 *
 */
public class VanRentalSystem {

	private List<Location> locations = new ArrayList<Location>();
	private List<String> print = new ArrayList<String>();
	private SimpleDateFormat sdf = new SimpleDateFormat("HH MM dd");

	/**
	 * The main for the VanRentalSystem class
	 */
	public static void main(String[] args) {
		Scanner sc = null;
		VanRentalSystem vanRentalSystem = new VanRentalSystem();
		try {
			sc = new Scanner(new FileReader(args[0])); 
			while (sc.hasNextLine())
				vanRentalSystem.command(sc.nextLine().split(" "));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sc != null)
				sc.close();
		}
	}

	/**
	 * Constructs a new van rental system.
	 */
	public VanRentalSystem() {
	}
	
	/**
	 * Handles all the command input(s) from the console
	 * @param in argument(s) from the console
	 */
	private void command(String[] in) {
		switch (in[0]) {
		case "Location":
			location(in[1], in[2], in[3]);
			break;
		case "Request":
			request(in[1], in[2], in[3], in[4], in[5], in[6], in[7], in[8], in[9],
					in.length == 12 ? in[10] : "0", in.length == 12 ? in[11] : "");
			break;
		case "Change":
			change(in[1], in[2], in[3], in[4], in[5], in[6], in[7], in[8], in[9],
					in.length == 12 ? in[10] : "0", in.length == 12 ? in[11] : "");
			break;
		case "Cancel":
			cancel(in[1]);
			break;
		case "Print":
			print(in[1]);
			break;
		}
	}
	
	/**
	 * Handles the location of the booking with the given inputs
	 * @param depot is the location of the campervan
	 * @param name of the campervan
	 * @param type of the transmission
	 */
	private void location(String depot, String name, String type) {
		Location location = getLocation(depot);
		location.getCampervans().add(new Campervan(depot, name, type));
	}
	
	/**
	 * Checks if a location exist in the locations list
	 * if not, then the location will be added to the list
	 * @param name the location to check
	 * @return a location
	 */
	public Location getLocation(String name) {
		for (Location location : locations)
			if (location.getName().equals(name)) return location;
		Location newLocation = new Location(name);
		locations.add(newLocation);
		return newLocation;
	}
	
	/**
	 * Handles any request of the booking with the given inputs
	 * @param id the index of the request
	 * @param hour1 start hour of the booking
	 * @param month1 start month of the booking
	 * @param date1 start date of the booking
	 * @param hour2 end hour of the booking
	 * @param month2 end month of the booking
	 * @param date2 end date of the booking
	 * @param num1 amount of type1
	 * @param type1 the transmission of the campervan
	 * @param num2 amount of type2
	 * @param type2 the transmission of the campervan
	 */
	private void request(String id, String hour1, String month1, String date1, String hour2, String month2,
			String date2, String num1, String type1, String num2, String type2) {
		Booking booking = new Booking(Integer.parseInt(id), parseDate(hour1, month1, date1),
				parseDate(hour2, month2, date2));
		int amount1 = Integer.parseInt(num1);
		int amount2 = Integer.parseInt(num2);
		int i = 0;
		print.add("Booking " + id);
		for (Location location : locations) {
			String s = (i == 0 ? " " : "; ") + location.getName();
			int j = 0;
			print.add(s);
			for (Campervan campervan : location.getCampervans()) {
				if (!checkBookingAvail(campervan, parseDate(hour1, month1, date1), parseDate(hour2, month2, date2)))
					continue;
				if (campervan.getType().equals(type1) && amount1 > 0) {
					campervan.getBookings().add(booking);
					print.add((j == 0 ? " " : ", ") + campervan.getName());
					amount1--;
					j++;
				} else if (campervan.getType().equals(type2) && amount2 > 0) {
					campervan.getBookings().add(booking);
					print.add((j == 0 ? " " : ", ") + campervan.getName());
					amount2--;
					j++;
				}
			}
			if (j == 0) print.remove(s);
			else i++;
		}
		if (amount1 + amount2 == 0) printList(print, false);
		else {
			System.out.println("Booking rejected");
			clearBooking(Integer.parseInt(id));
		}
		print.clear();
	}
	
	/**
	 * Changes any booking request
	 * @pre the booking must exist in order to change
	 * @param id the index of the request
	 * @param hour1 start hour of the booking
	 * @param month1 start month of the booking
	 * @param date1 start date of the booking
	 * @param hour2 end hour of the booking
	 * @param month2 end month of the booking
	 * @param date2 end date of the booking
	 * @param num1 amount of type1
	 * @param type1 the transmission of the campervan
	 * @param num2 amount of type2
	 * @param type2 the transmission of the campervan
	 */
	private void change(String id, String hour1, String month1, String date1, String hour2, String month2,
			String date2, String num1, String type1, String num2, String type2) {
		if (!clearBooking(Integer.parseInt(id))) {
			System.out.println("Change rejected");
			return;
		}
		Booking booking = new Booking(Integer.parseInt(id), parseDate(hour1, month1, date1),
				parseDate(hour2, month2, date2));
		Map<Campervan, Booking> bookingsChanged = getBookings(Integer.parseInt(id));
		int amount1 = Integer.parseInt(num1);
		int amount2 = Integer.parseInt(num2);
		int i = 0;
		print.add("Change " + id);
		for (Location location : locations) {
			String s = (i == 0 ? " " : "; ") + location.getName();
			int j = 0;
			print.add(s);
			for (Campervan campervan : location.getCampervans()) {
				if (!checkBookingAvail(campervan, parseDate(hour1, month1, date1), parseDate(hour2, month2, date2)))
					continue;
				if (campervan.getType().equals(type1) && amount1 > 0) {
					campervan.getBookings().add(booking);
					print.add((j == 0 ? " " : ", ") + campervan.getName());
					amount1--;
					j++;
				} else if (campervan.getType().equals(type2) && amount2 > 0) {
					campervan.getBookings().add(booking);
					print.add((j == 0 ? " " : ", ") + campervan.getName());
					amount2--;
					j++;
				}
			}
			if (j == 0) print.remove(s);
			else i++;
		}
		if (amount1 + amount2 == 0) printList(print, false);
		else {
			System.out.println("Change rejected");
			for (Map.Entry<Campervan, Booking> entry : bookingsChanged.entrySet())
				entry.getKey().getBookings().add(entry.getValue());
		}
		print.clear();	
	}
	
	/**
	 * Cancels bookings
	 * @param id of the booking
	 */
	private void cancel(String id) {
		System.out.println("Cancel " + (clearBooking(Integer.parseInt(id)) ? id : "rejected"));
	}
	
	/**
	 * Removes any booking associated with the id
	 * @param id of the booking
	 */
	private boolean clearBooking(int id) {
		boolean cleared = false;
		for (Map.Entry<Campervan, Booking> entry : getBookings(id).entrySet()) {
			if (id == entry.getValue().getId()) {
				entry.getKey().getBookings().remove(entry.getValue());
				cleared = true;
			}
		}
		return cleared;
	}
	
	/**
	 * Gets a map of all the bookings with their respective campervan
	 * @param id of the booking
	 * @return the map of the bookings with their respective campervan
	 */
	private Map<Campervan, Booking> getBookings(int id) {
		Map<Campervan, Booking> bookings = new LinkedHashMap<Campervan, Booking>();
		for (Location location : locations) {
			for (Campervan campervan : location.getCampervans()) {
				Iterator<Booking> iter = campervan.getBookings().iterator(); 
				while (iter.hasNext()) {
					Booking booking = iter.next();
					if (id == booking.getId())
						bookings.put(campervan, booking);
				}
			}
		}
		return bookings;
	}
	/**
	 * Parses a date using the SimpleDateFormat class
	 * @param hour of the date
	 * @param month of the date
	 * @param date of the date
	 * @return the date that is parsed
	 */
	private Date parseDate(String hour, String month, String date) {
		try {
			return sdf.parse(hour + " " + getMonth(month) + " " + date);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Checks if a booking is available
	 * @param campervan to be checked
	 * @param start of the booking
	 * @param end of the booking
	 * @return
	 */
	private boolean checkBookingAvail(Campervan campervan, Date start, Date end) {
		for (Booking booking : campervan.getBookings())
			if (start.before(booking.getEnd()) &&
					end.after(booking.getStart()))
				return false;
		return true;
	}
	
	/**
	 * Prints the bookings of a given location name
	 * @param name of the location
	 */
	private void print(String name) {
		Location location = getLocation(name);
		if (location == null) return;
		for (Campervan campervan : location.getCampervans())
			for (Booking booking : campervan.getBookings())
				print.add(name + " " + campervan.getName() + " " + getDateFormat(booking.getStart()) + " "
						+ getDateFormat(booking.getEnd()));
		Collections.sort(print);
		printList(print, true);
		print.clear();
	}
	
	/**
	 * Gets the date format of a given date
	 * @param date to be formatted
	 * @return the date format
	 */
	private String getDateFormat(Date date) {
		String format = "";
		String[] split = sdf.format(date).split(" ");
		split[0] = split[0] + ":00";
		split[1] = new DateFormatSymbols().getMonths()[Integer.parseInt(split[1]) - 1].substring(0, 3);
		for (String s : split)
			format += (s.equals(split[0]) ? "" : " ") + s;
		return format;
	}
	
	/**
	 * Gets the month integer from a given string
	 * @param month to get
	 * @return the month in terms of integer
	 */
	private String getMonth(String month) {
		switch (month) {
		case "Jan": return "01";
		case "Feb": return "02";
		case "Mar": return "03";
		case "Apr": return "04";
		case "May": return "05";
		case "Jun": return "06";
		case "Jul": return "07";
		case "Aug": return "08";
		case "Sep": return "09";
		case "Oct": return "10";
		case "Nov": return "11";
		case "Dec": return "12";
		default: return "01";
		}
	}

	/**
	 * Prints a list of string and leaves a new line afterwards
	 * @param list to be printed
	 * @param newLine true then new line for each iteration
	 */
	private void printList(List<String> list, boolean newLine) {
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			System.out.print(iter.next() + (newLine ? "\n" : ""));
			iter.remove();
		}
		if (!newLine) System.out.println();
	}
	
}
