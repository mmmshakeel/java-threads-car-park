

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BambaCarParkManager implements CarParkManager {
	
	private ArrayList<Vehicle> listOfVehicle = new ArrayList<Vehicle>();
	private static BambaCarParkManager instance = null;

	private int groundFloorAvailableSlots = CarParkManager.MAX_GROUNG_FLOOR;
	private int firstFloorAvailableSlots = CarParkManager.MAX_FIRST_FLOOR;
	private int secondFloorAvailableSlots = CarParkManager.MAX_SECOND_FLOOR;

	private Queue<Vehicle> groundFloorParkedVehicles = new LinkedList<Vehicle>();
	private Queue<Vehicle> firstFloorParkedVehicles = new LinkedList<Vehicle>();
	private Queue<Vehicle> secondFloorParkedVehicles = new LinkedList<Vehicle>();

	// create 3 FIFO queues to handle vehicle preferences
	private Queue<Vehicle> carQueue = new LinkedList<Vehicle>();
	private Queue<Vehicle> vanQueue = new LinkedList<Vehicle>();
	private Queue<Vehicle> motorbikeQueue = new LinkedList<Vehicle>();

	private int secondFloorLift = 12;

	private double chargePerHour = 300;
	private double addCharge = 100;
	private double maxCharge = 3000;
	private int addFromthisHour = 3;
	
	//private constructor
	private BambaCarParkManager() {
	}
	
	//method which returns an object of same type
	public static BambaCarParkManager getInstance() {
		if(instance == null) {
			synchronized(BambaCarParkManager.class){
				if(instance==null) {
					instance = new BambaCarParkManager();
				}
			}
		}
		return instance;
	}
	
	
	@Override
	public void addVehicle(Vehicle obj) {
		//check whether the vehicle is already parked or not
		for(Vehicle item : listOfVehicle) {
			if(item.equals(obj)) {
				System.out.println("This vehicle is already parked.");
				return;
			}
		}


		// try to find a floor based on the vehicle type
		if(obj instanceof Van ) {
			vanQueue.offer(obj);
			listOfVehicle.add(obj);
//				availableSlots -=2;
//				System.out.println("Available slots : "+availableSlots);
//				System.out.println("\n");

		}

		if(obj instanceof Car) {
			carQueue.offer(obj);
			listOfVehicle.add(obj);
//			availableSlots --;
//			System.out.println("Available slots : "+availableSlots);
		}

		if(obj instanceof MotorBike) {
			motorbikeQueue.offer(obj);
			listOfVehicle.add(obj);
		}
	}

	/**
	 * Park a vehicle in a slot
	 *
	 * @param obj
	 */
	public synchronized void parkVehicle(Vehicle obj) {
		boolean jobDone = false;

		while ((groundFloorAvailableSlots + firstFloorAvailableSlots + secondFloorAvailableSlots) == 0) {
			// check for any available slot, if not wait
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// van and car is allowed in all 3 floors
		if (obj instanceof Car || obj instanceof Van) {
			// first admit into ground floor then check other floors
			if (checkSlotAvailableForVehicleType(obj, groundFloorAvailableSlots)) {
				groundFloorAvailableSlots -= obj.getParkingUnits();
				groundFloorParkedVehicles.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Parked at ground floor");
				jobDone = true;

			} else if (checkSlotAvailableForVehicleType(obj, firstFloorAvailableSlots)) {
				firstFloorAvailableSlots -= obj.getParkingUnits();
				firstFloorParkedVehicles.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Parked at first floor");
				jobDone = true;

			} else if (checkSlotAvailableForVehicleType(obj, secondFloorAvailableSlots)) {
				secondFloorAvailableSlots -= obj.getParkingUnits();
				secondFloorParkedVehicles.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Parked at second floor");
				jobDone = true;
			}
		}

		// bike is allowed only on ground and first floor
		if (obj instanceof MotorBike) {
			if (checkSlotAvailableForVehicleType(obj, groundFloorAvailableSlots)) {
				groundFloorAvailableSlots -= obj.getParkingUnits();
				groundFloorParkedVehicles.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Parked at ground floor");
				jobDone = true;

			} else if (checkSlotAvailableForVehicleType(obj, firstFloorAvailableSlots)) {
				firstFloorAvailableSlots -= obj.getParkingUnits();
				firstFloorParkedVehicles.offer(obj);
				System.out.println(Thread.currentThread().getName());
				System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Parked at first floor");
				jobDone = true;

			}
		}

		if (jobDone) {
			System.out.println("Ground floor available slots: " + groundFloorAvailableSlots/3.0);
			System.out.println("First floor available slots: " + firstFloorAvailableSlots/3.0);
			System.out.println("Second floor available slots: " + secondFloorAvailableSlots/3.0);
			System.out.println("----------");
			System.out.println("");
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		notifyAll();

	}

	/**
	 * Exit from parking
	 *
	 * @param floor
	 */
	public synchronized void exitVehicle(int floor) {
		boolean jobDone = false;
		while ((groundFloorAvailableSlots + firstFloorAvailableSlots + secondFloorAvailableSlots) == (MAX_GROUNG_FLOOR + MAX_FIRST_FLOOR + MAX_SECOND_FLOOR)) {
			try {
				System.out.println("Waiting - " + (groundFloorAvailableSlots + firstFloorAvailableSlots + secondFloorAvailableSlots));
				// no vehicles parked on any floors, no vehicle is available for exit
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// exit from ground floor
		if (floor == GROUND_FLOOR_LEVEL && groundFloorAvailableSlots < MAX_GROUNG_FLOOR) {
			Vehicle obj = groundFloorParkedVehicles.poll();
			groundFloorAvailableSlots += obj.getParkingUnits();

			System.out.println(Thread.currentThread().getName());
			System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Exit ground floor");
			jobDone = true;
		}

		// exit from first floor
		if (floor == FIRST_FLOOR_LEVEL && firstFloorAvailableSlots < MAX_FIRST_FLOOR) {
			Vehicle obj = firstFloorParkedVehicles.poll();
			firstFloorAvailableSlots += obj.getParkingUnits();

			System.out.println(Thread.currentThread().getName());
			System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Exit first floor");
			jobDone = true;
		}

		// exit from second floor
		if (floor == SECOND_FLOOR_LEVEL && secondFloorAvailableSlots < MAX_SECOND_FLOOR) {
			Vehicle obj = secondFloorParkedVehicles.poll();
			secondFloorAvailableSlots += obj.getParkingUnits();

			System.out.println(Thread.currentThread().getName());
			System.out.println(obj.getVehicleType() + " with ID plate: "+ obj.getIdPlate() +" Exit second floor");
			jobDone = true;
		}


		if (jobDone) {
			System.out.println("Ground floor available slots: " + groundFloorAvailableSlots/3.0);
			System.out.println("First floor available slots: " + firstFloorAvailableSlots/3.0);
			System.out.println("Second floor available slots: " + secondFloorAvailableSlots/3.0);
			System.out.println("----------");
			System.out.println("");
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		notifyAll();
	}

	public Queue<Vehicle> getCarQueue() {
		return carQueue;
	}

	public Queue<Vehicle> getVanQueue() {
		return vanQueue;
	}

	public Queue<Vehicle> getMotorbikeQueue() {
		return motorbikeQueue;
	}

	/**
	 * One parking slot equals to 3 motorbike slots (3 motor bikes can be parked in one parking slot)
	 * Therefore, Van needs 2 parking slots = 6 motorbike slots unit
	 * Car needs one parking slot = 3 motorbike slots unit
	 *
	 * @param vehicle
	 * @param slots
	 * @return
	 */
	private boolean checkSlotAvailableForVehicleType(Vehicle vehicle, int slots) {

		if (vehicle instanceof Van) {
			return slots >= 6; // one slot equals to 3 motorbike slots and the van needs 2 main slots (6 motorbike slots)
		}

		if (vehicle instanceof Car) {
			return slots >= 3;
		}

		if (vehicle instanceof MotorBike) {
			return slots > 0;
		}

		return false;
	}

	@Override
	public void deleteVehicle(String IdPlate) {
		int availableSlots = 70;

		for(Vehicle item: listOfVehicle) {
			//Checking for a particular vehicle with its' plate ID
			if(item.getIdPlate().equals(IdPlate)) {
				System.out.println("Vehicle Found.");
				if(item instanceof Van) {
					availableSlots+=2;
					System.out.println("Space cleared after deleting a Van.\nAvailable Slots : "
							+availableSlots);
						}else{
							availableSlots++;
							System.out.println("Space cleared after deleting a vehicle.\nAvailable Slots : "
							+availableSlots);
						}
			}else {
				System.out.println("Vehicle not found.");
			}
		}
	}
			
	
	@Override
	public void printcurrentVehicles() {
		Collections.sort(listOfVehicle, Collections.reverseOrder());
		for( Vehicle item:listOfVehicle) {
			if(item instanceof Van) {
				System.out.println("Vehicle Type is a Van");
			}else if(item instanceof MotorBike) {
				System.out.println("Vehicle Type is a MotorBike");
			}else {
				System.out.println("Vehicle Type is a Car.");
			}
			System.out.println("******************");
			System.out.println("ID Plate : "+item.getIdPlate());
			System.out.println("Entry Time : "
			+item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
			+":"+item.getEntryDate().getSeconds()+"-"+item.getEntryDate().getDate()
			+"/"+item.getEntryDate().getMonth()+"/"+item.getEntryDate().getYear());
			System.out.println("\n");
		}	
	}

	@Override
	public void printLongestPark() {
		//sort to the ascending order
		Collections.sort(listOfVehicle);
		System.out.println("The longest parked vehicle is : ");
		System.out.println("................................................");
		System.out.println("ID Plate : "+listOfVehicle.get(0).getIdPlate());
		if(listOfVehicle.get(0) instanceof Car) {
			System.out.println("Vehicle Type is a Car.");
		}else if(listOfVehicle.get(0) instanceof Van){
			System.out.println("Vehicle Type is a Van.");
		}else {
			System.out.println("Vehicle Type is a MotorBike.");
		}
		System.out.println("Parked Time : "+listOfVehicle.get(0).getEntryDate().getHours()
				+":"+listOfVehicle.get(0).getEntryDate().getMinutes()
				+":"+listOfVehicle.get(0).getEntryDate().getSeconds());
		System.out.println("Parked Date  : "+listOfVehicle.get(0).getEntryDate().getDate()
				+"/"+listOfVehicle.get(0).getEntryDate().getMonth()
				+"/"+listOfVehicle.get(0).getEntryDate().getYear());
	}

	@Override
	public void printLatestPark() {
		// sort to the descending order
		Collections.sort(listOfVehicle, Collections.reverseOrder());
		System.out.println("The latest parked vehicle is : ");
		System.out.println("..............................................");
		System.out.println("ID Plate : "+listOfVehicle.get(0).getIdPlate());
		if(listOfVehicle.get(0) instanceof Car) {
			System.out.println("Vehicle Type is a Car.");
		}else if(listOfVehicle.get(0) instanceof Van){
			System.out.println("Vehicle Type is a Van.");
		}else {
			System.out.println("Vehicle Type is a MotorBike.");
		}
		System.out.println("Parked Time : "+listOfVehicle.get(0).getEntryDate().getHours()
				+":"+listOfVehicle.get(0).getEntryDate().getMinutes()
				+":"+listOfVehicle.get(0).getEntryDate().getSeconds());
		System.out.println("Parked Date  : "+listOfVehicle.get(0).getEntryDate().getDate()
				+"/"+listOfVehicle.get(0).getEntryDate().getMonth()
				+"/"+listOfVehicle.get(0).getEntryDate().getYear());	
	}

	
	@Override
	public void printVehicleByDay(DateTime givenDate) {
		for(Vehicle item:listOfVehicle) {
		if(givenDate.getYear()==item.getEntryDate().getYear() &&
				givenDate.getMonth()==item.getEntryDate().getMonth() && 
						givenDate.getDate() == item.getEntryDate().getDate()) {
			
				System.out.println("ID Plate : "+item.getIdPlate());
				
				System.out.println("Parked Date and Time : "+item.getEntryDate().getDate()+"/"+
				item.getEntryDate().getMonth()+"/"+item.getEntryDate().getHours()+"-"
				+item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
				+":"+item.getEntryDate().getYear());
				
				if(item instanceof Van) {
					System.out.println("Vehicle Type is a Van");
				}else if(item instanceof MotorBike) {
					System.out.println("Vehicle Type is a Motor Bike.");
				}else {
					System.out.println("Vehicle Type is a Car.");
				}	
				System.out.println("--------------------------");
				System.out.println("\n");
			}
		}
	}
		
	@Override
	public void printVehiclePercentage() {
		int numCars=0;
		int numBikes=0;
		int numVans=0;
		for(Vehicle item:listOfVehicle) {
			if(item instanceof Car) {
				numCars++;
			}else if(item instanceof MotorBike) {
				numBikes++;
			}else {
				numVans++;
			}
		}
		double carPercentage = (numCars/listOfVehicle.size())*100;
		double bikePercentage = (numBikes/listOfVehicle.size())*100;
		double vanPercentage = (numVans/listOfVehicle.size())*100;
		
		System.out.printf("Car Percentage is : %.f ",carPercentage);
		System.out.printf("\nBike Percentage is : %.f ",bikePercentage);
		System.out.printf("\nVan Percentage is : %.f ",vanPercentage);
		System.out.println("\n");
	}

	@Override
	public BigDecimal calculateChargers(String plateID, DateTime currentTime) {
		boolean found = false;
		BigDecimal charges = null;
		for(Vehicle item:listOfVehicle) {
			if(item.getIdPlate().equals(plateID)) {
				System.out.println("Vehicle found.");
				//vehicle parked time
				System.out.println("Parked Time : "+item.getEntryDate().getDate()+"/"
						+item.getEntryDate().getMonth()+"/"+item.getEntryDate().getDate()
								+"-"+item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
								+":"+item.getEntryDate().getSeconds());
				//making the charges
				found = true;
				DateTime entryDateTime = item.getEntryDate();
				int differenceInSeconds = currentTime.compareTo(entryDateTime);
				double differenceInHours = differenceInSeconds/(60.0*60.0);
				
				double dayCharge=0;
				double hourCharge = 0;
				double totalCost=0;
				double days = differenceInHours/24;
				
				if(days>1) {
					dayCharge =maxCharge;	
				}
				if (differenceInHours>=3) {
					double additional = (differenceInHours-addFromthisHour) ;
					hourCharge=(additional*addCharge)+(addFromthisHour *chargePerHour);
					System.out.printf("hour Charge : %.2f",hourCharge);
				}else if(differenceInHours<1) {
					hourCharge = chargePerHour;
				}else {
					hourCharge=(differenceInHours * chargePerHour);
				}
				
				totalCost=dayCharge + hourCharge;
				BigDecimal vehicleCharge = new BigDecimal(totalCost);
				System.out.printf("Total charge for the vehicle is LKR %.2f", vehicleCharge);
				System.out.println("\n");
			}
		}
		if(!found) {
			System.out.println("Vehicle not found\n");
		}
		return charges;
	}

	

	

	
	
}
