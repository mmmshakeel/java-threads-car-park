import java.util.Queue;

public class GateOut implements Runnable {

    private CarParkManager carParkManager;

    public GateOut(CarParkManager carParkManager) {
        this.carParkManager = carParkManager;
    }

    @Override
    public void run() {
        this.carParkManager.exitVehicle(CarParkManager.GROUND_FLOOR_LEVEL);
        this.carParkManager.exitVehicle(CarParkManager.FIRST_FLOOR_LEVEL);
        this.carParkManager.exitVehicle(CarParkManager.SECOND_FLOOR_LEVEL);

    }
}
