import java.util.Queue;

public class GateOut implements Runnable {

    private CarParkManager carParkManager;
    private int floor;

    public GateOut(CarParkManager carParkManager, int floor) {
        this.carParkManager = carParkManager;
        this.floor = floor;
    }

    @Override
    public void run() {
        /*for (int i=0; i<2; i++) {
            this.carParkManager.exitVehicle(floor);
        }*/
        Queue<Vehicle> vehicles = this.carParkManager.getParkedVehicles(floor);

        for (int i=0; i<vehicles.size(); i++) {
            this.carParkManager.exitVehicle(floor);
        }

    }
}
