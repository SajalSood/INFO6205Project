import java.util.Observable;

public class CarSimulation extends Observable implements Runnable  {

    private Thread thread = null; // the thread that runs my simulation
    private boolean paused = false;
    private boolean done = false; // set true to end the simulation loop
    private int ctr = 0;
    private boolean running = false; // set true if the simulation is running


    public void startSim() {
        System.out.println("Starting the simulation");
        if (thread != null) return; // A thread is already running

        thread = new Thread(this); // Create a worker thread

        paused = false;
        done = false; // reset the done flag.
        ctr = 0; // reset the loop counter
        thread.start();

    }

    public void pauseSim() {
        paused = !paused;
        System.out.println("Pause the simulation: " + paused);
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isPausable() {
        if (!running) return false;
        if (done) return false;
        return true;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stopSim() {
        System.out.println("Stop the simulation");
        if (thread == null) return; // defensive coding in case the thread is null
        done = true;
    }

    @Override
    public void run() {
        runSimLoop();
        thread = null; // flag that the simulation thread is finished
    }

    private void runSimLoop() {
        running = true;
        while(!done) {
            // do some simulation work
            if (!paused)
                updateSim();
            sleep(50L);

        }
        running = false;
    }
    /*
     * Make the current thread sleep a little
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Allow access to the simulation data
    public int getCtr() {
        return ctr;
    }

    private void updateSim() {
        System.out.println("Updating the simulation " + ctr++);
        setChanged();
        notifyObservers(this); // Send a copy of the simulation
    }
}
