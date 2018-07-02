import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class VanRentalSystem {
	
	public static void main(String[] args) {
		BookingSystem bookingSystem = new BookingSystem();
		VanRentalSystem vanRentalSystem = new VanRentalSystem();
		Scanner scanInput = vanRentalSystem.scanFile(args[0]);
		
		while(scanInput.hasNextLine()) {
			String readInput = scanInput.nextLine();
			String[] input = readInput.split(" ");
			if(input[0].equals("Location")) {
				bookingSystem.addNewLocation(input);
			}
			else if(input[0].equals("Change")) {
				bookingSystem.addNewBooking(input);
			}		
			else if(input[0].equals("Request")) {
				bookingSystem.addNewBooking(input);
			}				
			else if(input[0].equals("Print")) {
				bookingSystem.printLocationBookings(input[1]);
			}
			else if(input[0].equals("Cancel")) {
				Booking tempBooking = bookingSystem.findBooking(Integer.parseInt(input[1]));
				if (tempBooking != null) {
					int tempId = tempBooking.getId();
					if (bookingSystem.cancelBooking(tempBooking)) {
						System.out.println("Cancel "+tempId);
					} else {
						System.out.println("Cancel rejected");
					}
				} else {
					System.out.println("Cancel rejected");
				}
			}
		}
		scanInput.close();
	}
	
	/**
	 * Read the input file as a scanner.
	 * @param input The input file name.
	 * @return The scanner object of the input file.
	 */
	public Scanner scanFile(String input) {
	    Scanner sc = null;
	    try
	    {
	    	sc = new Scanner(new FileReader(input));   
	    }
	    catch (FileNotFoundException e) {
	    	System.err.println("File not found");
	    }
        return sc;
	}
}
