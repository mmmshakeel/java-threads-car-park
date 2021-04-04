

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class BambaCarParkManager implements CarParkManager{
	
	private ArrayList<Vehicle> listOfVehicle = new ArrayList<Vehicle>();
	private static BambaCarParkManager instance = null;
	private int availableSlots = 20; 
	private double chargePerHour = 300;
	private double addCharge = 100;
	private double maxCharge = 3000;
	private int addFromthisHour =3;
	
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
		// Check whether there are sufficient space available for any vehicle to park
		if(listOfVehicle.size()<20) {	
			if(obj instanceof Van ) {
				if(listOfVehicle.size()<19) {
					listOfVehicle.add(obj);
					availableSlots -=2;
					System.out.println("Available slots : "+availableSlots);
					System.out.println("\n");
				}else {
					System.out.println("Sorry..There are no slots available to park your Van."+"\n");
				}
			}
			if(obj instanceof MotorBike || obj instanceof Car) {
				listOfVehicle.add(obj);
				availableSlots --;
				System.out.println("Available slots : "+availableSlots);
			}
		}else {
			System.out.println("Sorry...There are not space availble for parking...");
		}
	}
  
	@Override
	public void deleteVehicle(String IdPlate) {
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
