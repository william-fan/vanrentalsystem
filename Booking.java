import java.util.Calendar;
import java.util.Comparator;

public class Booking implements Comparator<Booking> {
	private int id;
	private Calendar startTime;
	private Calendar endTime;
	private int automatics;
	private int manuals;
	
	/**
	 * @param id The unique id number.
	 * @param startTime Start time of the booking.
	 * @param endTime End time of the booking.
	 * @param automatics Number of automatics, 0 for none.
	 * @param manuals Number of manuals, 0 for none.
	 */
	public Booking (int id, Calendar startTime, Calendar endTime, int automatics, int manuals) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.automatics = automatics;
		this.manuals = manuals;
	}
	
	/**
	 * Get the start time of the booking.
	 * @return The calendar object of the start time.
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Set the start time of the booking.
	 * @param startTime The calendar object of the start time.
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Get the end time of the booking.
	 * @return The calendar object of the end time.
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Set the end time of the booking.
	 * @param endTime The calendar object of the end time.
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Get the id of the booking.
	 * @return The integer value of the id.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Set the id of the booking.
	 * @param id The integer value of the id.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Get the number of automatics requested.
	 * @return The integer value of automatics requested.
	 */
	public int getAutomatics() {
		return this.automatics;
	}
	
	/**
	 * Set the number of automatics requested.
	 * @param automatics The integer value of automatics requested.
	 */
	public void setAutomatics(int automatics) {
		this.automatics = automatics;
	}
	
	/**
	 * Get the number of manuals requested.
	 * @return The integer value of manuals requested.
	 */
	public int getManuals() {
		return this.manuals;
	}
	
	/**
	 * Set the number of manuals requested.
	 * @param manuals The integer value of manuals requested.
	 */
	public void setManuals(int manuals) {
		this.manuals = manuals;
	}
	
	/** 
	 * @pre x strictly less than y or x strictly greater than y
	 * Compare booking x, y based on start time and end time.
	 * Only used to sort the list of bookings for printing,
	 * so we know start time and end time of both bookings do not conflict, due to prior checks.
	 */
	public int compare(Booking x, Booking y) {
		if (x.getStartTime().compareTo(y.getStartTime()) > 0 && x.getEndTime().compareTo(y.getEndTime()) > 0) {
			return 1;
		} else if (x.getStartTime().compareTo(y.getStartTime()) < 0 && x.getEndTime().compareTo(y.getEndTime()) < 0) {
			return -1;
		} else {
			return 0;  //this should never occur as equal dates are rejected
		}
	}
}
