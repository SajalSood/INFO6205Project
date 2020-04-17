
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MyPanel extends JPanel implements Constants, Observer {

    private Graphics2D g2d;
    private int x = 20;

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;

        //Grass around the road
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, frameWidth, 100);
        g2d.fillRect(0, 100 + roadHeight, frameWidth, 300);

        //road Setup
        g2d.setColor(Color.gray);
        g2d.fillRect(0, 100, frameWidth, roadHeight);

        //Block Colours
        g2d.setColor(Color.red);
        g2d.fillRect(550, 210, 30, 30);
        g2d.fillRect(500, 210, 30, 30);

        g2d.fillRect(950, 160, 30, 30);
        g2d.fillRect(1000, 160, 30, 30);

        //Lines in the middle of the road
        Stroke dashedLane = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setColor(Color.getHSBColor(148, 93, 20));
        g2d.setStroke(dashedLane);
        //road-1 line
        g2d.drawLine(0, 150, frameWidth, 150);
        g2d.drawLine(0, 200, frameWidth, 200);

        //Lane numbers
        int size = g.getFont().getSize();
        g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 36));
        g2d.setColor(Color.WHITE);
        g2d.drawString("1", frameWidth - 75, 140);
        g2d.drawString("2", frameWidth - 75, 190);
        g2d.drawString("3", frameWidth - 75, 240);
        g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), size));

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 350, frameWidth, frameHeight);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Priority Queue(Elements and priority)", 50, 400);

        createVehiclesByVehicleInstances();

        displayPriorityQueue();
    }

    //Method to create instances of the vehicles on thread counter
    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof CarSimulation) {
            x = x + 20;
            if (x > 60)
                x = 20;
            CarSimulation sim = (CarSimulation) o;
            if (sim.getCtr() % x == 0) {
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


    //Method to merge all lane vehicles
    private void createVehiclesByVehicleInstances() {
        ArrayList<Car> allVehicles = new ArrayList<Car>();
        allVehicles.addAll(lane1);
        allVehicles.addAll(lane2);
        allVehicles.addAll(lane3);

        for (Car allVehicle : allVehicles) {
            drawRectangle(allVehicle);
        }
    }

    //Method to draw vehicles in the respective lanes on the panel.
    private void drawRectangle(Car vehicle) {
        Rectangle rect = new Rectangle(vehicle.getVehLocationX(), vehicle.getVehLocationY(),
                vehicle.getVehWidth(), vehicle.getVehHeight());
        g2d.setColor(Color.pink);
        g2d.fill(rect);

        int size = g2d.getFont().getSize();
        g2d.setFont(new Font(g2d.getFont().getFontName(), g2d.getFont().getStyle(), 10));
        g2d.setColor(Color.BLACK);
        g2d.drawString(vehicle.getId(), (int) rect.getX() + 1, (int) rect.getY() + 15);
        g2d.setFont(new Font(g2d.getFont().getFontName(), g2d.getFont().getStyle(), size));
    }


    //Method to Display Queue Elements with Priority.
    private void displayPriorityQueue() {
        g2d.setColor(Color.BLUE);

        if (pQueue.size() > 0) {
            int threshX = 50;
            int threshY = 450;

            for (Car car : pQueue) {
                if (threshY > 700) {
                    threshX = threshX + 100;
                    threshY = 450;
                }
                System.out.println("Ele: " + car.getId());
                g2d.drawString(car.getId() + " - " + car.getPriority(), threshX, threshY);
                threshY = threshY + 30;
            }
        }
    }

    //Method to check safe distance between cars
    private void checkSafeDistance(Car currCar, Car preCar) {
        if (currCar.getVehLocationX() + vehicleWidth < (preCar.getVehLocationX() - currCar.getStopDistance())) {
            if (currCar.getVehSpeed() == 0) {
                currCar.setVehSpeed(8);
            }
            currCar.setVehLocationX(currCar.getVehLocationX() + currCar.getVehSpeed());
        } else {
            currCar.setVehSpeed(0);
        }
    }

    //Method to update Location of instances
    private void updateLocations() {
        for (int i = 0; i < lane1.size(); i++) {
            if (i == 0) {
                lane1.get(i).setVehLocationX(lane1.get(i).getVehLocationX() + lane1.get(i).getVehSpeed());
                if (lane1.get(i).getVehLocationX() > frameWidth) {
                    pQueue.remove(lane1.get(i));
                    lane1.remove(lane1.get(i));
                }
            } else {
                checkSafeDistance(lane1.get(i), lane1.get(i - 1));
            }
        }
        for (int i = 0; i < lane2.size(); i++) {
            if (i == 0) {
                if ((lane2.get(i).getVehLocationX() + vehicleWidth + lane2.get(i).getStopDistance()) < 950) {
                    if (lane2.get(i).getVehSpeed() == 0) {
                        lane2.get(i).setVehSpeed(8);
                    }
                    lane2.get(i).setPriority(1);
                    lane2.get(i).setVehLocationX(lane2.get(i).getVehLocationX() + lane2.get(i).getVehSpeed());
                } else {
                    lane2.get(i).setVehSpeed(0);
                    checkIfCarCanMerge(lane2.get(i), lane2, lane1);
                }
            } else {
                checkSafeDistance(lane2.get(i), lane2.get(i - 1));
            }
        }
        for (int i = 0; i < lane3.size(); i++) {
            if (i == 0) {
                if ((lane3.get(i).getVehLocationX() + vehicleWidth + lane3.get(i).getStopDistance()) < 500) {
                    if (lane3.get(i).getVehSpeed() == 0) {
                        lane3.get(i).setVehSpeed(8);
                    }
                    lane3.get(i).setPriority(1);
                    lane3.get(i).setVehLocationX(lane3.get(i).getVehLocationX() + lane3.get(i).getVehSpeed());
                } else {
                    lane3.get(i).setVehSpeed(0);
                    checkIfCarCanMerge(lane3.get(i), lane3, lane2);
                }
            } else {
                checkSafeDistance(lane3.get(i), lane3.get(i - 1));
            }
        }
    }

    //Method to check if car can merge
    private void checkIfCarCanMerge(Car car, ArrayList<Car> from, ArrayList<Car> to) {
        for (int i = 0; i < to.size(); i++) {
            int rearSafeDistance = car.getVehLocationX() - 20;
            int frontSafeDistance = car.getVehLocationX() + vehicleWidth + 20;
            if (!carExists(rearSafeDistance, frontSafeDistance, to)) {
                car.setPriority(0);
                if (car.getLaneNumber().equals("Third")) {
                    car.setLaneNumber("Second");
                    car.setVehLocationY(secondLaneY);
                } else if (car.getLaneNumber().equals("Second")) {
                    car.setLaneNumber("First");
                    car.setVehLocationY(firstLaneY);
                }
                pQueue.remove(car);
                car.setStopDistance(laneStopDistance);
                car.setVehLocationX(rearSafeDistance + 10);
                car.setVehSpeed(8);
                to.add(getNearCarIndex(rearSafeDistance, to), car);
                from.remove(car);
                updateLocations();
            } else {
                ArrayList<Car> collidingCars = GetCollidingCars(from, rearSafeDistance);
                collidingCars.add(car);
                CheckPriorityQueue(collidingCars);
            }
            repaint();
        }
    }

    //Method to get all colliding cars
    private ArrayList<Car> GetCollidingCars(ArrayList<Car> from,
                                            int rearSafeDistance) {

        ArrayList<Car> collisionCars = new ArrayList<Car>();

        for (Car car : from) {
            if (car.getVehLocationX() + vehicleWidth < rearSafeDistance) {
                collisionCars.add(car);
            }
        }
        return collisionCars;
    }

    //Method to check if car can merge
    private void CheckPriorityQueue(ArrayList<Car> cars) {
        for (Car car : cars) {
            addToPriorityQueue(car);
        }
    }

    //Method to add to the priority queue
    private void addToPriorityQueue(Car car) {
        if (!pQueue.contains(car) && car.getVehLocationX() > 0) {
            pQueue.add(car);
        }
        deQueueEnqueue();
    }

    private void deQueueEnqueue() {
        ArrayList<Car> list = new ArrayList<Car>(pQueue);
        pQueue.clear();
        pQueue.addAll(list);
    }

    private boolean carExists(int rearLocX, int frontLocX, ArrayList<Car> to) {
        for (Car car : to) {
            if (car.getVehLocationX() + vehicleWidth > rearLocX && car.getVehLocationX() < frontLocX) {
                return true;
            }
        }
        return false;
    }

    //Method to retrive next lane car index
    private int getNearCarIndex(int rearLocX, ArrayList<Car> to) {
        ArrayList<Car> arrayList = new ArrayList<Car>();
        for (Car car : to) {
            if (car.getVehLocationX() + vehicleWidth < rearLocX) {
                arrayList.add(car);
            }
        }
        return to.indexOf(findMaxLoc(arrayList));
    }

    private Car findMaxLoc(ArrayList<Car> list) {
        int max = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(max).getVehLocationX() < list.get(i).getVehLocationX()) {
                max = i;
            }
        }
        return list.get(max);
    }

}
