import java.util.Queue;

public class GateIn implements Runnable {

    private CarParkManager carParkManager;
    private Queue<Vehicle> carQueue;
    private Queue<Vehicle> vanQueue;
    private Queue<Vehicle> motorbikeQueue;

    public GateIn(CarParkManager carParkManager, Queue<Vehicle> carQueue, Queue<Vehicle> vanQueue, Queue<Vehicle> motorbikeQueue) {
        this.carParkManager = carParkManager;
        this.carQueue = carQueue;
        this.vanQueue = vanQueue;
        this.motorbikeQueue = motorbikeQueue;
    }

    @Override
    public void run() {

        // first preference to cars
        if (carQueue.size() > 0) {
            for (int i=0; i<carQueue.size(); i++) {
                // let all the cars in any gate first
                this.carParkManager.parkVehicle(carQueue.poll());
            }
        }

        // then give the preference to vans
        if (vanQueue.size() > 0) {
            for (int i=0; i<vanQueue.size(); i++) {
                // let all the cars in any gate first
                this.carParkManager.parkVehicle(vanQueue.poll());
            }
        }

        // lowest preference to motorbikes
        if (motorbikeQueue.size() > 0) {
            for (int i=0; i<motorbikeQueue.size(); i++) {
                // let all the cars in any gate first
                this.carParkManager.parkVehicle(motorbikeQueue.poll());
            }
        }

    }
}
