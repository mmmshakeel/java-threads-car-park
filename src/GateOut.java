import java.util.Queue;

public class GateOut implements Runnable {

    private final CarParkManager carParkManager;
    private final int floor;
    private final int noOfVehicles;

    public GateOut(CarParkManager carParkManager, int floor, int noOfVehicles) {
        this.carParkManager = carParkManager;
        this.floor = floor;
        this.noOfVehicles = noOfVehicles;
    }

    @Override
    public void run() {

        for (int i = 0; i < noOfVehicles; i++) {
            this.carParkManager.exitVehicle(floor);
        }

    }
}
