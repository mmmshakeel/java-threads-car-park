

public class Van extends Vehicle {

	public static final int MOTORBIKE_UNITS = 6;

	//Properties
	private double cargoVolume;
	private final String vehicleType = "Van";

	//Constructor
	public Van(String idPlate, String brand, String model, DateTime entryTime, double cargoVolume) {
		super(idPlate, brand, model, entryTime);
		this.cargoVolume=cargoVolume;
	}

	public int getParkingUnits() {
		return MOTORBIKE_UNITS;
	}

	public String getVehicleType() {
		return this.vehicleType;
	}

	public double getCargoVolume() {
		return cargoVolume;
	}
	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume=cargoVolume;
	}

}
