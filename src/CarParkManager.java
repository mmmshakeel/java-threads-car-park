

import java.math.BigDecimal;
import java.util.Queue;

public interface CarParkManager {

	// convert the car park slots to motorbike slots as the minimum unit for easy calculation
	public static final int MAX_GROUND_FLOOR = 80 * 3; //Number of slots available in the Ground floor
	public static final int MAX_FIRST_FLOOR = 60 * 3; //Number of slots available in the first floor
	public static final int MAX_SECOND_FLOOR = 70 * 3; //Number of slots available in the second floor

	public static final int GROUND_FLOOR_LEVEL = 0;
	public static final int FIRST_FLOOR_LEVEL = 1;
	public static final int SECOND_FLOOR_LEVEL = 2;

	public void addVehicle(Vehicle obj);
	public void deleteVehicle(String IdPlate);
	public void printcurrentVehicles();
	public void printVehiclePercentage();
	public void printLongestPark();
	public void printLatestPark();
	public void printVehicleByDay(DateTime entryTime);
	public BigDecimal calculateChargers(String plateID, DateTime currentTime);

	public void exitVehicle(int floor);
	public void parkVehicle(Vehicle obj, int floor);
	public Queue<Vehicle> getParkedVehicles(int floor);

}
