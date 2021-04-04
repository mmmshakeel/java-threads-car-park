

import java.awt.Color;

public class Car extends Vehicle { 
	
	//Car Properties
	private int doors;
	private Color color;
	
	//Constructor
	public Car(String idPlate, String brand, String model, DateTime entryTime, int doors, Color color) {
		super(idPlate, brand, model, entryTime);
		this.doors=doors;
		this.color=color;
	}
	
	public int getDoors() {
		return doors;
	}
	public void setDoors(int doors) {
		this.doors=doors;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color=color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + doors;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (doors != other.doors)
			return false;
		return true;
	}
	
	

	
}
