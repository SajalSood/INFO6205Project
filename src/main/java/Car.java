import java.util.Comparator;
import java.util.Random;

public class Car implements Constants {

    private String id;
    private int vehSpeed;
    private int vehLocationX;
    private int vehLocationY;
    private int vehHeight;
    private int vehWidth;
    private String laneNumber;
    private boolean rotate;
    private int stopDistance;
    final String alpha="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final Random r = new Random();
    private int priority;

    public Car(int vehSpeed, int vehLocationX, int vehLocationY, int vehHeight, int vehWidth, String laneNumber,
               boolean rotate, int stopDistance, int priority){
        this.id =  myUID(laneNumber);
        this.vehSpeed = vehSpeed;
        this.vehLocationX = vehLocationX;
        this.vehLocationY = vehLocationY;
        this.vehHeight = vehHeight;
        this.vehWidth = vehWidth;
        this.laneNumber = laneNumber;
        this.rotate = rotate;
        this.stopDistance =  stopDistance;
        this.priority = priority;
    }

    public String myUID(String laneNumber) {
        int i = 4; String uid = "";

        if(laneNumber.equals("First")){
            uid += "1-";
        }
        else if(laneNumber.equals("Second")){
            uid += "2-";
        }
        else {
            uid += "3-";
        }
        while (i-- > 0) {
            uid += alpha.charAt(r.nextInt(36));
        }
        return uid;
    }

    /**
     * Vehicle static constructor
     * Creates instances of vehicles
     *
     */
    public static void createVehicleInstancesLane1() {
        Car vehicle = new Car(10, -40, firstLaneY, vehicleHeight,
                vehicleWidth, "First", false, laneStopDistance, 0);
        lane1.add(vehicle);
    }

    public static void createVehicleInstancesLane2() {
        Car vehicle = new Car(8, -40, secondLaneY, vehicleHeight,
                vehicleWidth, "Second", false, laneStopDistance, 0);
        lane2.add(vehicle);
    }

    public static void createVehicleInstancesLane3() {
        Car vehicle = new Car(8, -40, thirdLaneY, vehicleHeight,
                vehicleWidth, "Third", false, laneStopDistance, 0);
        lane3.add(vehicle);
    }


    //getters and setters
    public int getStopDistance() {
        return stopDistance;
    }

    public void setStopDistance(int stopDistance) {
        this.stopDistance = stopDistance;
    }

    public int getVehSpeed() {
        return vehSpeed;
    }

    public void setVehSpeed(int vehSpeed) {
        this.vehSpeed = vehSpeed;
    }

    public int getVehLocationX() {
        return vehLocationX;
    }

    public void setVehLocationX(int vehLocationX) {
        this.vehLocationX = vehLocationX;
    }

    public int getVehLocationY() {
        return vehLocationY;
    }

    public void setVehLocationY(int vehLocationY) {
        this.vehLocationY = vehLocationY;
    }

    public int getVehHeight() {
        return vehHeight;
    }

    public int getVehWidth() {
        return vehWidth;
    }

    public String getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(String laneNumber) {
        this.laneNumber = laneNumber;
    }

    public String getId(){
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    @Override
    public String toString() {
        return id;
    }
}

