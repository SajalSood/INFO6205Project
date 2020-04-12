
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MyPanel extends JPanel implements Constants, Observer {

    private int counter = 0;
    private CarRules rule = new CarRules();
    private Graphics2D g2d;
    private ArrayList<Timer> timers = new ArrayList<Timer>(); //List to initiate Timers.
    private Color color; //store the color of the vehicle.
    private String status = statusMsg; //To show the message on the top panel based on the status number.
    private CarSimulation sim = null;
    //private int count = 1;
    private int index;
//    private int thresh = 50;
    private int threshY = 500;


    public void paint(Graphics g) {
        g2d = (Graphics2D) g;

        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, frameWidth, 100);

        g2d.setColor(Color.gray);
        //road-1
        g2d.fillRect(0, 100, frameWidth, roadHeight);
        //road-2

//        g2d.fillRect(0, 500, frameWidth-50, roadHeight);
//        //road-patch
//        g2d.fillRect(1250, 200, roadHeight, 300);

        g2d.setColor(Color.red);
        g2d.fillRect(550, 210, 30, 30);
        g2d.fillRect(500, 210, 30, 30);

        g2d.fillRect(950, 160, 30, 30);
        g2d.fillRect(1000, 160, 30, 30);

        Stroke dashedLane = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setColor(Color.getHSBColor(148,93,20));
        g2d.setStroke(dashedLane);
        //road-1 line
        g2d.drawLine(0, 150, frameWidth, 150);
        g2d.drawLine(0, 200, frameWidth, 200);

        int size = g.getFont().getSize();
        g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 36));
        g2d.setColor(Color.WHITE);
        g2d.drawString("1", frameWidth - 75, 140);
        g2d.drawString("2", frameWidth - 75, 190);
        g2d.drawString("3", frameWidth - 75, 240);

        g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), size));
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 100+roadHeight, frameWidth - 150, 300);
        g2d.fillRect(0, 500+roadHeight, frameWidth, 300);
        g2d.fillRect(1250+roadHeight, 100+roadHeight, frameWidth, frameHeight);
        g2d.fillRect(0, 500, frameWidth-50, roadHeight);
//        //road-patch
        g2d.fillRect(1250, 100+roadHeight, roadHeight, 300);
        g2d.fillRect(1100, 510, vehicleWidth, vehicleHeight );

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 350, frameWidth, frameHeight);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Priority Queue", 50, 400);

        createVehiclesByVehicleInstances();

        updatePriorityQueue();

    }

    private void createVehiclesByVehicleInstances(){
        ArrayList<Car> allVehicles = new ArrayList<Car>();
        allVehicles.addAll(lane1);
        allVehicles.addAll(lane2);
        allVehicles.addAll(lane3);

        for(int i =0; i<allVehicles.size(); i++) {
            drawRectangle(allVehicles.get(i), Color.pink);
        }
    }

    //Method to create vehicles in the respective lanes.
    private void drawRectangle(Car vehicle, Color color) {
        Rectangle rect = new Rectangle(vehicle.getVehLocationX(), vehicle.getVehLocationY(),
                vehicle.getVehWidth(), vehicle.getVehHeight());
        g2d.setColor(color);
        g2d.fill(rect);

        int size = g2d.getFont().getSize();
        g2d.setFont(new Font(g2d.getFont().getFontName(), g2d.getFont().getStyle(), 10));
        g2d.setColor(Color.BLACK);
        g2d.drawString(vehicle.getId(), (int)rect.getX() + 1, (int)rect.getY() + 15);
        g2d.setFont(new Font(g2d.getFont().getFontName(), g2d.getFont().getStyle(), size));
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof CarSimulation) {
            sim = (CarSimulation) o;
            if (sim.getCtr() % 50 == 0) {
                Car.createVehicleInstancesLane1();
            }
            if (sim.getCtr() % 30 == 0) {
                Car.createVehicleInstancesLane2();
            }
            if (sim.getCtr() % 10 == 0) {
                Car.createVehicleInstancesLane3();
            }

            updateLocations();
            repaint();
        }
    }

    private void updatePriorityQueue() {
        g2d.setColor(Color.BLUE);
        Object[] arr = pQueue.toArray();

        if(arr.length > 0) {
            int threshX = 50;
            int threshY = 500;

            for (int i = 0; i < arr.length; i++) {
                if(threshX > 1000){
                    threshX = 50;
                    threshY = threshY + 30;
                }
                System.out.println("Ele: " + arr[i].toString());
                g2d.drawString(arr[i].toString(), threshX, threshY);
                threshX = threshX + 60;
            }
        }
    }

    private void updateLocations() {
        for(int i=0; i < lane1.size(); i++){
            if(i==0) {
                lane1.get(i).setVehLocationX(lane1.get(i).getVehLocationX() + lane1.get(i).getVehSpeed());
                if(lane1.get(i).getVehLocationX() > frameWidth){
                    lane1.remove(lane1.get(i));
                }
            }
            else {
                if(lane1.get(i).getVehLocationX()+vehicleWidth < (lane1.get(i-1).getVehLocationX() - lane1.get(i).getStopDistance())) {
                    if(lane1.get(i).getVehSpeed() == 0){
                        lane1.get(i).setVehSpeed(8);
                    }
                    lane1.get(i).setVehLocationX(lane1.get(i).getVehLocationX() + lane1.get(i).getVehSpeed());
                }
                else{
                    lane1.get(i).setVehSpeed(0);
                }
            }
        }
        for(int i=0; i < lane2.size(); i++){
            if(i==0) {
                if((lane2.get(i).getVehLocationX()+vehicleWidth+lane2.get(i).getStopDistance()) < 950) {
                    if(lane2.get(i).getVehSpeed() == 0){
                        lane2.get(i).setVehSpeed(8);
                    }
                    lane2.get(i).setVehLocationX(lane2.get(i).getVehLocationX() + lane2.get(i).getVehSpeed());
                }
                else{
                    lane2.get(i).setVehSpeed(0);
                    checkIfCarCanMerge(lane2.get(i), lane2, lane1);
                }
            }
            else {
                if(lane2.get(i).getVehLocationX()+vehicleWidth < (lane2.get(i-1).getVehLocationX() - lane2.get(i).getStopDistance())) {
                    if(lane2.get(i).getVehSpeed() == 0){
                        lane2.get(i).setVehSpeed(8);
                    }
                    lane2.get(i).setVehLocationX(lane2.get(i).getVehLocationX() + lane2.get(i).getVehSpeed());
                }
                else{
                    lane2.get(i).setVehSpeed(0);
                }
            }
        }
        for(int i=0; i < lane3.size(); i++){
            if(i==0) {
                if((lane3.get(i).getVehLocationX()+vehicleWidth+lane3.get(i).getStopDistance()) < 500) {
                    if(lane3.get(i).getVehSpeed() == 0){
                        lane3.get(i).setVehSpeed(8);
                    }
                    lane3.get(i).setVehLocationX(lane3.get(i).getVehLocationX() + lane3.get(i).getVehSpeed());
                }
                else{
                    lane3.get(i).setVehSpeed(0);
                    checkIfCarCanMerge(lane3.get(i), lane3, lane2);
                }
            }
            else {
                if(lane3.get(i).getVehLocationX()+vehicleWidth < (lane3.get(i-1).getVehLocationX() - lane3.get(i).getStopDistance())) {
                    if(lane3.get(i).getVehSpeed() == 0){
                        lane3.get(i).setVehSpeed(8);
                    }
                    lane3.get(i).setVehLocationX(lane3.get(i).getVehLocationX() + lane3.get(i).getVehSpeed());
                }
                else{
                    lane3.get(i).setVehSpeed(0);
                }
            }
        }
    }

    private void checkIfCarCanMerge(Car car,  ArrayList<Car> from,  ArrayList<Car> to){
        for(int i=0; i< to.size(); i++) {
            int rearSafeDistance = car.getVehLocationX() - 20;
            int frontSafeDistance = car.getVehLocationX() + vehicleWidth + 20;
            if(!carExists(rearSafeDistance, frontSafeDistance, to)) {
                if(car.getLaneNumber() == "Third") {
                    car.setLaneNumber("Second");
                    car.setVehLocationY(secondLaneY);
                }
                else if(car.getLaneNumber() == "Second") {
                    car.setLaneNumber("First");
                    car.setVehLocationY(firstLaneY);
                }
                car.setStopDistance(laneStopDistance);
                car.setVehLocationX(rearSafeDistance+10);
                car.setVehSpeed(8);
                to.add(getNearCarIndex(rearSafeDistance, frontSafeDistance, to), car);
                from.remove(car);
                updateLocations();
                repaint();
            }
            else {
                if(!pQueue.contains(car)){
                    pQueue.add(car);
                }
            }
        }
    }

    private boolean carExists(int rearLocX, int frontLocX, ArrayList<Car> to){
        for(int i = 0 ; i < to.size(); i++){
            if(to.get(i).getVehLocationX() + vehicleWidth > rearLocX && to.get(i).getVehLocationX() < frontLocX) {
                return true;
            }
        }
        return false;
    }

//    private Car moveToLane(Car car){
//        car.setLaneNumber("First");
//        car.setVehLocationY(firstLaneY);
//        car.setStopDistance(lane1StopDistance);
//        car.setVehSpeed(10);
//        car.setVehLocationX(lane2.get(4).getVehLocationX()+50);
//        lane2.remove(car);
//        return car;
//    }

    private int getNearCarIndex(int rearLocX, int frontLocX, ArrayList<Car> to) {
        ArrayList<Car> arrayList = new ArrayList<Car>();
        for(int i =0; i<to.size(); i++) {
            if (to.get(i).getVehLocationX() + vehicleWidth < rearLocX) {
                arrayList.add(to.get(i));
            }
        }
        return to.indexOf(findMaxLoc(arrayList));
    }

    private Car findMaxLoc(ArrayList<Car> list) {
        int max = 0;
        for (int i =1; i<list.size(); i++){
            if(list.get(max).getVehLocationX() < list.get(i).getVehLocationX()) {
                max = i;
            }
        }
        return list.get(max);
    }
}
