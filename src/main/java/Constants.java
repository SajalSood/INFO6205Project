import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public interface Constants {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //Observer Messages
    String changeLanesMsg = "ChangeLanes";
    String speedUpMsg = "SpeedUp";
    String speedSlowDownMsg = "SlowDown";
    String threadPauseMsg = "Pause";
    String threadUnPauseMsg = "UnPause";
    String startSimulationMsg = "Start Simulation";
    String startAgainSimulationMsg = "Star Again";
    String statusMsg = "After selecting color Press Start to Start the Simulation " +
            "Choose from options to control your car.";
    String statusSlowDownMsg = "Speed Slowed down.";
    String statusNotSlowedMsg = "Speed not Slowed Car behind!";
    String statusSpeedMsg = "Speed Increased";
    String statusNoSpeed = "Car ahead! cant speed up / Maximum Speed Limit reached.";
    String statusLaneChanged = "Lane Changed!";
    String statusNoLaneChanged = "Car Near can't change lane!";

    //App name and some constants
    String AppName = "Vehicle Density Simulation";
    int vehicleWidth = 40;
    int vehicleHeight = 25;
    int firstLaneY = 120;
    int secondLaneY = 160;
    int thirdLaneY = 210;
    int numberOfVehicles = 8;
    int frameWidth = 1400;
    int frameHeight = 800;
    int roadHeight = 150;
    int turningRightDistanceLane1 = 1330;
    int turningRightDistanceLane2 = 1242;
    int laneStopDistance = 80;
//    int lane1StopDistance = 80;
//    int lane2StopDistance = 80;
//    int lane3StopDistance = 100;

    ArrayList<Car> lane1 = new ArrayList<Car>();
    ArrayList<Car> lane2 = new ArrayList<Car>();
    ArrayList<Car> lane3 = new ArrayList<Car>();
    PriorityQueue<Car> pQueue = new PriorityQueue<Car>(1000, new CarComparator());
}
