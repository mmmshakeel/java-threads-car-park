

public class Van extends Vehicle {
	//Properties
	private double cargoVolume;
	
	//Constructor
	public Van(String idPlate, String brand, String model, DateTime entryTime, double cargoVolume) {
		super(idPlate, brand, model, entryTime);
		this.cargoVolume=cargoVolume;
	}
	
	public double getCargoVolume() {
		return cargoVolume;
	}
	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume=cargoVolume;
	}

}
