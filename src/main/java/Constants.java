import java.util.ArrayList;
import java.util.PriorityQueue;

public interface Constants {
    //App name and some constants
    String AppName = "Vehicle Density Simulation";
    int vehicleWidth = 40;
    int vehicleHeight = 25;
    int firstLaneY = 120;
    int secondLaneY = 160;
    int thirdLaneY = 210;
    int frameWidth = 1400;
    int frameHeight = 800;
    int roadHeight = 150;
    int laneStopDistance = 80;

    ArrayList<Car> lane1 = new ArrayList<Car>();
    ArrayList<Car> lane2 = new ArrayList<Car>();
    ArrayList<Car> lane3 = new ArrayList<Car>();
    PriorityQueue<Car> pQueue = new PriorityQueue<Car>(1000, new CarComparator());
}
