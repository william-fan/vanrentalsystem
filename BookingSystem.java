import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class BookingSystem {
	private ArrayList<Booking> bookingList = new ArrayList<Booking>();
	private ArrayList<Location> locationList = new ArrayList<Location>();
	
	/**
	 * Get the list of bookings.
	 * @return List of bookings.
	 */
	public ArrayList<Booking> getBookingList() {
		return this.bookingList;
	}
	
	/**
	 * Set the list of bookings.
	 * @param bookingList The list of bookings.
	 */
	public void setBookingList(ArrayList<Booking> bookingList) {
		this.bookingList = bookingList;
	}
	
	/**
	 * Get the list of locations.
	 * @return The list of locations.
	 */
	public ArrayList<Location> getLocationList() {
		return this.locationList;
	}
	
	/**
	 * Set the list of locations.
	 * @param locationList The list of locations.
	 */
	public void setLocationList(ArrayList<Location> locationList) {
		this.locationList = locationList;
	}
	
	/**
	 * Searches bookingList for specified id.
	 * @param id Unique number for each booking.
	 * @return If a booking with id is found return it, otherwise return null.
	 */
	public Booking findBooking(int id) {
		for (Booking booking : bookingList){
			if (booking.getId() == id){
				return booking;
			}
		}
		return null;
	}
	
	/**
	 * Searches locationList for specified name.
	 * @param name Name of the location.
	 * @return If a location with the name is found return it, otherwise return null;
	 */
	public Location findLocation(String name) {
		for (Location location : locationList){
			if (location.getName().equals(name)){
				return location;
			}
		}
		return null;
	}
	
	/**
	 * Add a location, if it is new, to location list, then add a van to the location van list.
	 * @param input The array of strings read as input.
	 */
	public void addNewLocation(String[] input) {
		Location location = findLocation(input[1]);
		if (location == null) {  //check if location has been added
			location = new Location(input[1]);
			getLocationList().add(location);
		}
		Van newVan = new Van(input[2], input[3]);
		for (Van van : location.getVanList()) {  //check if van has already been added
			if (van.getName().equals(newVan.getName())) {
				return;
			}	
		}
		location.getVanList().add(newVan);
	}
	
	/**
	 * Searches all vans in a location and if the van has bookings, calls printVanBookings.
	 * Sorts every booking in a van before printing based on time.
	 * @param depot The location name
	 */
	public void printLocationBookings(String depot) {
		Location tempLocation = findLocation(depot);
		if (tempLocation == null) {
			return;
		}
		String print = depot;
		for (Van van : tempLocation.getVanList()) {
			Collections.sort(van.getBookingList(), new Booking(0, null, null, 0, 0)); //sort before printing
			if (!van.getBookingList().isEmpty()) {
				print += " "+van.getName()+" "+van.printVanBookings();
				// add depot and van name in front of every new line that needs it.
				// usually when multiple bookings on one van.
			    print = print.replaceAll("\n([0-9])", "\n"+depot+" "+van.getName()+" $1");
				System.out.println(print);
				print = depot;
			}
		}
 	}
	
	/**
	 * Add a new booking if a booking spots are available.
	 * Also handles change booking, by deleting the booking and adding a new booking with the same id.
	 * @param input The array of strings read as input.
	 */
	public void addNewBooking(String[] input) {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.clear(Calendar.MILLISECOND);  //clear milliseconds,
		endDate.clear(Calendar.MILLISECOND); //prevented calendars from not being equal even if they have the same dates
		Booking booking;
		startDate.set(startDate.get(Calendar.YEAR), strToIntMonth(input[3]), Integer.parseInt(input[4]), Integer.parseInt(input[2]), 0);
		endDate.set(endDate.get(Calendar.YEAR), strToIntMonth(input[6]), Integer.parseInt(input[7]), Integer.parseInt(input[5]), 0);
		if (input[9].equals("Manual") && input.length == 10) {    //no automatics given
			booking = new Booking(Integer.parseInt(input[1]), startDate, endDate, 0, Integer.parseInt(input[8]));
		} else if (input[9].equals("Automatic") && input.length == 10) {  //no manuals given
			booking = new Booking(Integer.parseInt(input[1]), startDate, endDate, Integer.parseInt(input[8]), 0);
		} else if (input[9].equals("Manual") && input[11].equals("Automatic")){ //if input is flipped
			booking = new Booking(Integer.parseInt(input[1]), startDate, endDate, Integer.parseInt(input[10]), Integer.parseInt(input[8]));
		} else if (input[9].equals("Automatic") && input[11].equals("Manual")){
			booking = new Booking(Integer.parseInt(input[1]), startDate, endDate, Integer.parseInt(input[8]), Integer.parseInt(input[10]));
		} else if (input[9].equals("Automatic") && !input[11].equals("Manual")){  // in case of extra comments/inputs that increase input length
			booking = new Booking(Integer.parseInt(input[1]), startDate, endDate, Integer.parseInt(input[8]), 0);
	    } else if (input[9].equals("Manual") && !input[11].equals("Automatic")){  
			booking = new Booking(Integer.parseInt(input[1]), startDate, endDate, 0, Integer.parseInt(input[8]));
		} else {
			booking = new Booking(Integer.parseInt(input[1]), startDate, endDate, Integer.parseInt(input[8]), Integer.parseInt(input[10]));
		}
		Booking bookingChange = findBooking(Integer.parseInt(input[1]));
		//check valid inputs
		if (booking.getAutomatics() < 0 || booking.getManuals() < 0 || startDate.compareTo(endDate) >= 0 || (booking.getAutomatics() == 0 && booking.getManuals() == 0)) {
			if (input[0].equals("Change")) {
				System.out.println("Change rejected");
			} else {
				System.out.println("Booking rejected");
			}
			return;
		}
		//check if change is available, then confirm change.
		if (input[0].equals("Change") && checkBookingToVan(booking, bookingChange) && findBooking(booking.getId()) != null) {
			cancelBooking(bookingChange);
			bookingList.add(booking);
			addBookingToVan(booking);
			System.out.println("Change "+bookingOutput(booking));
		//check if spot is available and booking id has not been used before
		} else if (input[0].equals("Request") && addBookingToVan(booking) && findBooking(booking.getId()) == null){
			bookingList.add(booking);
			System.out.println("Booking "+bookingOutput(booking));
		} else {
			if (input[0].equals("Change")) {
				System.out.println("Change rejected");
			} else {
				System.out.println("Booking rejected");
			}
		}
	}
	
	/**
	 * The output once a booking has been confirmed.
	 * @param booking The booking that has been confirmed.
	 * @return Output string of van names.
	 */
	public String bookingOutput(Booking booking) {
		String print = String.valueOf(booking.getId())+" ";
		int locationCount = 0;  // which location number we are on
		int locationSame = 0;   // is the location same as the last van, i.e 0 = new location, !0 = same location
		int vanCount = 0;
		for (Location location : locationList) {
			locationSame = 0;
			for (Van van : location.getVanList()) {
				//check every van for specific booking, and print specific format
				if (van.getBookingList().contains(booking) && vanCount == 0) { //if first van 
					print += location.getName()+" ";
				} else if (locationCount == 0 && locationSame == 0 && van.getBookingList().contains(booking)) {
					print += location.getName()+" ";
					vanCount++;
				} else if (locationCount > 0 && locationSame == 0 && van.getBookingList().contains(booking) && vanCount != 0) { //if new location
					print = print.substring(0, print.length()-2);
					print += "; "+location.getName()+" ";
					vanCount++;
				} 
				if (van.getBookingList().contains(booking)) { 
					print += van.getName()+", ";
					vanCount++;
					locationSame++;
				}
			}
			locationCount++;
		}
		return print.substring(0, print.length()-2);
	}
	
	/**
	 * Change short month format into an integer.
	 * @param month The input month as a short month string, MMM.
	 * @return The integer value of the month.
	 */
	public int strToIntMonth(String month) {
		DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        int monthInt;
	    for (monthInt = 0;!months[monthInt].equals(month) && monthInt != 11; monthInt++);
	    return monthInt;
	}
	
	/**
	 * Checks vans can fit booking.
	 * @param booking The input booking.
	 * @return Whether booking can be fulfilled or not.
	 */
	public boolean addBookingToVan(Booking booking) {
		int tempAuto = booking.getAutomatics(); 
		int tempMan = booking.getManuals();
		ArrayList<Van> tempList = new ArrayList<Van>();
		for (Location location : locationList) {
			for (Van van : location.getVanList()) {
				if (van.checkAvailable(booking.getStartTime(), booking.getEndTime()) && van.getType().equals("Automatic") && tempAuto > 0) {
					tempList.add(van);
					tempAuto--;
				}
				if (van.checkAvailable(booking.getStartTime(), booking.getEndTime()) && van.getType().equals("Manual") && tempMan > 0) {
					tempList.add(van);
					tempMan--;
				}
			}
		}
		if (tempAuto > 0 || tempMan > 0) {  //if we cannot get enough vans
			return false;
		} else {
			for (Van van : tempList) {  //finally add bookings
				van.getBookingList().add(booking);
			}
			return true;
		}
		
	}
	
	/**
	 * Checks if there is any space for this booking, does not add to booking to vans.
	 * Used when changing bookings, prevents any rejections when all vans are occupied.
	 * Similar logic as above.
	 * @param booking The booking we wish to add.
	 * @param oldBooking The old booking we want to skip.
	 * @return Whether booking can be fulfilled or not.
	 */
	public boolean checkBookingToVan(Booking booking, Booking oldBooking) {
		int tempAuto = booking.getAutomatics(); 
		int tempMan = booking.getManuals();
		for (Location location : locationList) {
			for (Van van : location.getVanList()) {
				if (van.checkAvailableSkip(booking.getStartTime(), booking.getEndTime(), oldBooking) && van.getType().equals("Automatic") && tempAuto > 0) {
					tempAuto--;
				}
				if (van.checkAvailableSkip(booking.getStartTime(), booking.getEndTime(), oldBooking) && van.getType().equals("Manual") && tempMan > 0) {
					tempMan--;
				}
			}
		}
		if (tempAuto > 0 || tempMan > 0) {  //if we cannot get enough vans
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Delete booking and remove booking from all vans.
	 * @param booking The booking we wish to remove.
	 * @return Whether booking can be fulfilled or not.
	 */
	public boolean cancelBooking(Booking booking) {
		if (booking != null) {
			bookingList.remove(booking);
			for (Location location : locationList) {
				for (Van van : location.getVanList()) {
					van.getBookingList().remove(booking);
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
