import java.util.Queue;

public class GateIn implements Runnable {

    private CarParkManager carParkManager;
    private Queue<Vehicle> carQueue;
    private Queue<Vehicle> vanQueue;
    private Queue<Vehicle> motorbikeQueue;
    private int floor;

    public GateIn(CarParkManager carParkManager, int floor, Queue<Vehicle> carQueue, Queue<Vehicle> vanQueue, Queue<Vehicle> motorbikeQueue) {
        this.carParkManager = carParkManager;
        this.carQueue = carQueue;
        this.vanQueue = vanQueue;
        this.motorbikeQueue = motorbikeQueue;
        this.floor = floor;
    }

    @Override
    public void run() {

        // first preference to cars
        if (carQueue.size() > 0) {
            for (int i=0; i<carQueue.size(); i++) {
                // let all the cars in any gate first
                this.carParkManager.parkVehicle(carQueue.poll(), floor);
            }
        }

        // then give the preference to vans
        if (vanQueue.size() > 0) {
            for (int i=0; i<vanQueue.size(); i++) {
                // let all the cars in any gate first
                this.carParkManager.parkVehicle(vanQueue.poll(), floor);
            }
        }

        // lowest preference to motorbikes
        if (motorbikeQueue.size() > 0) {
            for (int i=0; i<motorbikeQueue.size(); i++) {
                // let all the cars in any gate first
                this.carParkManager.parkVehicle(motorbikeQueue.poll(), floor);
            }
        }

    }
}
