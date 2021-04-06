

public class MotorBike extends Vehicle {

	public static final int MOTORBIKE_UNITS = 1;

	private String engineSize;
	private String vehicleType = "Motor Bike";

	public MotorBike(String idPlate, String brand, String model, DateTime entryTime, String engineSize) {
		super(idPlate, brand, model, entryTime);
		this.engineSize=engineSize;
	}

	public int getParkingUnits() {
		return MOTORBIKE_UNITS;
	}

	public String getVehicleType() {
		return this.vehicleType;
	}

	public String getEngineSize() {
		return engineSize;
	}
	public void setEngineSize(String engineSize) {
		this.engineSize=engineSize;
	}

}
