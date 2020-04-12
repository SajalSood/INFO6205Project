import java.util.Random;

public class Car implements Constants {

    Random r = new Random();
    private int id;
    private int vehSpeed;
    private int vehLocationX;
    private int vehLocationY;
    private int vehHeight;
    private int vehWidth;
    private String laneNumber;
    private boolean rotate;
    private int stopDistance;

    public Car(int vehSpeed, int vehLocationX, int vehLocationY, int vehHeight, int vehWidth, String laneNumber, boolean rotate, int stopDistance){
        this.id = r.nextInt(1000);
        this.vehSpeed = vehSpeed;
        this.vehLocationX = vehLocationX;
        this.vehLocationY = vehLocationY;
        this.vehHeight = vehHeight;
        this.vehWidth = vehWidth;
        this.laneNumber = laneNumber;
        this.rotate = rotate;
        this.stopDistance =  stopDistance;
    }

    /**
     * Vehicle static constructor
     * Creates instances of vehicles
     *
     */
    public static void createVehicleInstancesLane1() {
        Car vehicle = new Car(10, -40, firstLaneY, vehicleHeight,
                vehicleWidth, "First", false, lane1StopDistance);
        lane1.add(vehicle);
    }

    public static void createVehicleInstancesLane2() {
        Car vehicle = new Car(8, -40, secondLaneY, vehicleHeight,
                vehicleWidth, "Second", false, lane2StopDistance);
        lane2.add(vehicle);

    }

    public static void createVehicleInstancesLane3() {
        Car vehicle = new Car(8, -40, thirdLaneY, vehicleHeight,
                vehicleWidth, "Third", false, lane3StopDistance);
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

    public boolean isRotate() {
        return rotate;
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    public int getId(){
        return id;
    }
}
