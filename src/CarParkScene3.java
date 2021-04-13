import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class CarParkScene3 {

    private static BambaCarParkManager bambaCarParkManager =  BambaCarParkManager.getInstance();

    public static void main(String[] args) {

        DateTime dateTime = new DateTime(2021, 4, 6, 20, 10, 20);

        // set ground floor parking and first floor parking full for this example.
        bambaCarParkManager.setGroundFloorAvailableSlots(0);
        Queue<Vehicle> gfVehicleQueue = new LinkedList<Vehicle>();
        for (int i = 0; i < CarParkManager.MAX_GROUND_FLOOR; i += 3) {
            gfVehicleQueue.offer(new Car("KM-988"+i, "Toyota", "Premio", dateTime, 4, Color.BLUE));
        }
        bambaCarParkManager.setGroundFloorParkedVehicles(gfVehicleQueue);

        bambaCarParkManager.setFirstFloorAvailableSlots(0);
        Queue<Vehicle> firstVehicleQueue = new LinkedList<Vehicle>();
        for (int i = 0; i < CarParkManager.MAX_FIRST_FLOOR; i += 3) {
            firstVehicleQueue.offer(new Car("KM-766"+i, "Toyota", "Premio", dateTime, 4, Color.BLUE));
        }
        bambaCarParkManager.setFirstFloorParkedVehicles(firstVehicleQueue);

        // All parking slots are empty when start on this example.
        // create some vehicles to park
        Queue<Vehicle> carQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueue = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueue = new LinkedList<Vehicle>();

        carQueue.offer(new Car("KX-5213", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueue.offer(new Car("CAS-1234", "Toyota", "Prado", dateTime, 4, Color.WHITE));
        carQueue.offer(new Car("BAT-2234", "Toyota", "Prius", dateTime, 4, Color.GRAY));

        vanQueue.offer(new Van("KD-4563", "Nissan", "Model1", dateTime, 23.5));
        vanQueue.offer(new Van("KH-8745", "Ford", "Model2", dateTime, 12.5));

        motorbikeQueue.offer(new MotorBike("UAE-2311", "Yamaha", "Fz-2", dateTime, "200cc"));

        // create runnables for ground floor gates
        Runnable groundGateIn = new GateIn(bambaCarParkManager, CarParkManager.GROUND_FLOOR_LEVEL, carQueue, vanQueue, motorbikeQueue);
        Runnable groundGateOut = new GateOut(bambaCarParkManager, CarParkManager.GROUND_FLOOR_LEVEL, (gfVehicleQueue.size() + carQueue.size() + vanQueue.size() + motorbikeQueue.size() + 1000));

        // create a thread group for ground floor
        // ThreadGroup groundFloorGroup = new ThreadGroup("Ground Floor");
        Thread[] threads = new Thread[45];

        // ground floor northern gates with highest priority
        Thread northernGateIn1 = new Thread(groundGateIn, "Ground Northern gate entry 1");
        Thread northernGateIn2 = new Thread(groundGateIn, "Ground Northern gate entry 2");
        northernGateIn1.setPriority(Thread.MAX_PRIORITY);
        northernGateIn2.setPriority(Thread.MAX_PRIORITY);
        threads[0] = northernGateIn1;
        threads[1] = northernGateIn2;

        // other ground floor threads
        threads[2] = new Thread(groundGateIn, "Ground West gate entry");
        threads[3] = new Thread(groundGateOut, "Ground West gate exit");

        threads[4] = new Thread(groundGateIn, "Ground East gate entry");
        threads[5] = new Thread(groundGateOut, "Ground East gate exit");

        threads[6] = new Thread(groundGateIn, "Ground South gate entry");
        threads[7] = new Thread(groundGateOut, "Ground South gate exit");


        // create some vehciles for first floor gates
        Queue<Vehicle> carQueue2 = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueue2 = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueue2 = new LinkedList<Vehicle>();

        // set some vehicles to first floor
        carQueue2.offer(new Car("JK-1122", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueue2.offer(new Car("WES-5899", "Toyota", "Prado", dateTime, 4, Color.WHITE));

        vanQueue2.offer(new Van("ER-2100", "Ford", "Model2", dateTime, 12.5));

        motorbikeQueue2.offer(new MotorBike("DFG-4009", "Yamaha", "Fz-2", dateTime, "200cc"));

        // create runnables for ground floor gates
        Runnable firstGateIn = new GateIn(bambaCarParkManager, CarParkManager.FIRST_FLOOR_LEVEL, carQueue2, vanQueue2, motorbikeQueue2);
        Runnable firstGateOut = new GateOut(bambaCarParkManager, CarParkManager.FIRST_FLOOR_LEVEL, (firstVehicleQueue.size() + carQueue2.size() + vanQueue2.size() + motorbikeQueue2.size() + 1000));

        // first floor threads
        threads[8] = new Thread(firstGateIn, "First floor West gate entry 1");
        threads[9] = new Thread(firstGateIn, "First floor West gate entry 2");

        threads[10] = new Thread(firstGateOut, "First floor East gate exit 1");
        threads[11] = new Thread(firstGateOut, "First floor East gate exit 2");

        // second floor threads
        Queue<Vehicle> carQueue3 = new LinkedList<Vehicle>();
        Queue<Vehicle> vanQueue3 = new LinkedList<Vehicle>();
        Queue<Vehicle> motorbikeQueue3 = new LinkedList<Vehicle>();

        carQueue3.offer(new Car("JJ-3333", "Toyota", "Premio", dateTime, 4, Color.BLACK));
        carQueue3.offer(new Car("EGB-9990", "Toyota", "Prado", dateTime, 4, Color.WHITE));

        vanQueue3.offer(new Van("QQ-0009", "Ford", "Model2", dateTime, 12.5));

        // create runnables for ground floor gates
        Runnable liftIn = new GateIn(bambaCarParkManager, CarParkManager.SECOND_FLOOR_LEVEL, carQueue3, vanQueue3, motorbikeQueue3);
        Runnable liftOut = new GateOut(bambaCarParkManager, CarParkManager.SECOND_FLOOR_LEVEL, (carQueue3.size() + vanQueue3.size() + motorbikeQueue3.size() + 1000));

        // create 12 threads for second floor
        for (int i = 12; i < 24; i++) {
            threads[i] = new Thread(liftIn, "Second floor lift " + (i-11) + " entry");
        }

        for (int i = 24; i < 36; i++) {
            threads[i] = new Thread(liftOut, "Second floor lift " + (i-23) + " exit");
        }

        // start all threads
        for (Thread thread: threads) {
            if (thread != null && !thread.isAlive()) {
                System.out.println(thread.getName());
                thread.start();
            }
        }

    }
}
