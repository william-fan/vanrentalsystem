import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Van {
	private String name;
	private String type;
	private ArrayList<Booking> bookingList = new ArrayList<Booking>();
	
	/**
	 * @param name The name of the van.
	 * @param type The transmission type of the van, should be either Automatic or Manual.
	 */
	public Van (String name, String type){
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Get the name of the van.
	 * @return The name of the van.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the van.
	 * @param name The name of the van.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the transmission type of the van.
	 * @return The transmission type of the van.
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Set the transmission type of the van.
	 * @param type The transmission type of the van.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Get the bookings that are tied to this van.
	 * @return The bookings that are tied to this van.
	 */
	public ArrayList<Booking> getBookingList() {
		return this.bookingList;
	}

	/**
	 * Set the bookings that are tied to this van.
	 * @param bookingList The bookings that are tied to this van.
	 */
	public void setBookingList(ArrayList<Booking> bookingList) {
		this.bookingList = bookingList;
	}
	
	/**
	 * Checks if this van is available to a certain time period.
	 * @param startTime Start time of a booking.
	 * @param endTime End time of a booking.
	 * @return Whether van is available for the time period.
	 */
	public boolean checkAvailable(Calendar startTime, Calendar endTime) {
		if (bookingList.isEmpty()) {
			return true;
		} else {
			for (Booking booking : bookingList) {
				if (startTime.compareTo(booking.getEndTime()) <= 0 && startTime.compareTo(booking.getStartTime()) > 0
						|| endTime.compareTo(booking.getStartTime()) >= 0 && endTime.compareTo(booking.getEndTime()) < 0
						|| startTime.compareTo(booking.getStartTime()) <= 0 && endTime.compareTo(booking.getEndTime()) >= 0) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Checks if there is any space available for a booking, but skip booking we are going to delete.
	 * Used in change booking, and similar logic as above.
	 * @param startTime The start time of the booking.
	 * @param endTime The end time of the booking.
	 * @param oldBooking The old booking that we will skip. 
	 * @return Whether van is available for the time period.
	 */
	public boolean checkAvailableSkip(Calendar startTime, Calendar endTime, Booking oldBooking) {
		if (bookingList.isEmpty()) {
			return true;
		} else {
			for (Booking booking : bookingList) {
				if (oldBooking == booking) {
					continue;
				}
				if (startTime.compareTo(booking.getEndTime()) <= 0 && startTime.compareTo(booking.getStartTime()) > 0
						|| endTime.compareTo(booking.getStartTime()) >= 0 && endTime.compareTo(booking.getEndTime()) < 0
						|| startTime.compareTo(booking.getStartTime()) <= 0 && endTime.compareTo(booking.getEndTime()) >= 0) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Print the bookings tied to this van in a specific format.
	 * @return Output string of formatted bookings.
	 */
	public String printVanBookings() {
		String print = "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MMM dd");
		for (Booking booking : bookingList) {
			print += sdf.format(booking.getStartTime().getTime())+" "+sdf.format(booking.getEndTime().getTime())+"\n";
		}
		return print.substring(0,print.length()-1);  //remove final space
	} 
}
