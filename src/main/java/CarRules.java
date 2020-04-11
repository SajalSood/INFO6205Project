public class CarRules implements Constants {

    public int safeDistance = 100; //Distance to be checked while speeding up, allowing down and changing lanes.

    //Method to check for conditions when slowing down
    public String slowDown(){
//        if(checkForCollisionsSpeedDown(lane1.get(1))) {
        lane1.get(1).setVehSpeed(lane1.get(1).getVehSpeed() - 2);
        return statusSlowDownMsg;
//        }
//        return statusSlowDownMsg;
    }

    //Method to check for conditions when Speeding up
    public String speedUp(){
        if(checkForCollisionsSpeedUP(lane1.get(1)) && lane1.get(1).getVehSpeed() < 14) {
            lane1.get(1).setVehSpeed(lane1.get(1).getVehSpeed() + 2);
            return statusSpeedMsg + lane1.get(1).getVehSpeed();
        }
        return statusNoSpeed;
    }

    public void stop(){
        lane1.get(1).setVehSpeed(0);
    }

    public void start(){
        lane1.get(1).setVehSpeed(8);
    }

    //Method to check for conditions when Changing Lanes
    public String changeLanes(){
        if(lane1.get(1).getVehLocationY() == 120 && !lane1.get(1).isRotate()) {
            if(checkForCollisionLaneChange(lane1.get(1))) {
                lane2.get(1).setVehSpeed(10);
                lane2.get(0).setVehSpeed(9);
                lane1.get(1).setLaneNumber("Second");
                lane1.get(1).setVehLocationY(200);
                lane1.get(1).setVehSpeed(8);
                return statusLaneChanged;
            }
        } else if (lane1.get(1).getVehLocationY() == 200 && !lane1.get(1).isRotate()){
            if(checkForCollisionLaneChange(lane1.get(1))) {
                lane1.get(1).setLaneNumber("First");
                lane1.get(1).setVehLocationY(120);
                return statusLaneChanged;
            }
        } else if (lane1.get(1).getVehLocationY() == 392 && !lane1.get(1).isRotate()){
            if(checkForCollisionLaneChange(lane1.get(1))) {
                lane1.get(1).setLaneNumber("Second");
                lane1.get(1).setVehLocationY(320);
                return statusLaneChanged;
            }
        } else if(lane1.get(1).getVehLocationY() == 320 && !lane1.get(1).isRotate()){
            if(checkForCollisionLaneChange(lane1.get(1))) {
                lane1.get(1).setLaneNumber("First");
                lane1.get(1).setVehLocationY(392);
                return statusLaneChanged;
            }
        } else if(lane1.get(1).getVehLocationY() == 520 && !lane1.get(1).isRotate()){
            if(checkForCollisionLaneChange(lane1.get(1))) {
                lane1.get(1).setLaneNumber("Second");
                lane1.get(1).setVehLocationY(600);
                return statusLaneChanged;
            }
        } else if(lane1.get(1).getVehLocationY() == 600 && !lane1.get(1).isRotate()) {
            if(checkForCollisionLaneChange(lane1.get(1))) {
                lane1.get(1).setLaneNumber("First");
                lane1.get(1).setVehLocationY(520);
                return statusLaneChanged;
            }
        }
        return statusNoLaneChanged;
    }


    private boolean checkForCollisionLaneChange(Car vehicle){
        int locY = vehicle.getVehLocationY();

        if(locY == 120 || locY == 392 || locY == 520) {

            if (lane2.get(0).getVehLocationX() < vehicle.getVehLocationX()) {
                if ((vehicle.getVehLocationX() - (lane2.get(0).getVehLocationX() + vehicleWidth)) > safeDistance) {
                    return true;
                }
            } else if (lane2.get(1).getVehLocationX() < vehicle.getVehLocationX()) {
                if ((vehicle.getVehLocationX() - (lane2.get(1).getVehLocationX() + vehicleWidth)) > safeDistance) {
                    return true;
                }
            } else if(lane2.get(1).getVehLocationX() > vehicle.getVehLocationX()) {
                if ((lane2.get(1).getVehLocationX() - vehicle.getVehLocationX()+vehicleWidth) > safeDistance) {
                    return true;
                }
            }else {
                if ((lane2.get(0).getVehLocationX() - vehicle.getVehLocationX()+vehicleWidth) > safeDistance) {
                    return true;
                }
            }
        }

        if(locY == 320 || locY == 600) {
            if (lane1.get(0).getVehLocationX() < vehicle.getVehLocationX()) {
                if ((vehicle.getVehLocationX() - (lane1.get(0).getVehLocationX() + vehicleWidth)) > safeDistance) {
                    return true;
                }
            } else {
                if ((lane1.get(0).getVehLocationX() - vehicle.getVehLocationX()) > safeDistance) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkForCollisionsSpeedUP(Car vehicle){
        int locY = vehicle.getVehLocationY();

        if(locY == 120 || locY == 392 || locY == 520){
            if(lane1.get(0).getVehLocationX() < vehicle.getVehLocationX()){
                return true;
            }
            else{
                if(((lane1.get(0).getVehLocationX()) - vehicle.getVehLocationX()+vehicleWidth) > safeDistance){
                    return true;
                }
            }
        }
        if(locY == 200 || locY == 320 || locY <= 600) {
            if (lane2.get(0).getVehLocationX() < vehicle.getVehLocationX()) {
                return true;
            } else {
                if (((lane2.get(0).getVehLocationX()) - vehicle.getVehLocationX() + vehicleWidth) > safeDistance) {
                    return true;
                }
            }

            if (lane2.get(1).getVehLocationX() < vehicle.getVehLocationX()) {
                return true;
            } else {
                if (((lane2.get(1).getVehLocationX()) - vehicle.getVehLocationX() + vehicleWidth) > safeDistance) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkForCollisionsSpeedDown(Car vehicle){
        int locX = vehicle.getVehLocationX();

        if(locX == 120 || locX == 392 || locX == 520){
            if(lane1.get(0).getVehLocationX() < vehicle.getVehLocationX()){
                if((vehicle.getVehLocationX() - (lane1.get(0).getVehLocationX()+vehicleWidth)) > safeDistance) {
                    return true;
                }
            }
            else{
                return true;
            }
        }
        if(locX == 320 || locX == 600) {
            for (Car otherVeh : lane2) {
                if (otherVeh.getVehLocationX() < vehicle.getVehLocationX()) {
                    if ((vehicle.getVehLocationX() - (otherVeh.getVehLocationX()+vehicleWidth)) > safeDistance) {
                        return true;
                    }
                } else {
                    return true;

                }
            }
        }

        return false;
    }

//    public void CheckForCollisions(Car vehicle) {
//        if (vehicle.getVehLocationX() < lane1.get(1).getSafeDistance() &&
//                lane1.get(1).getLaneNumber().equals(vehicle.getLaneNumber()) && lane1.get(0).getVehLocationX() > lane1.get(1).getVehLocationX()) {
//            stop();
//        }
//        else {
//            start();
//        }
//    }
}
