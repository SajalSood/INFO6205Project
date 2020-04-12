
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SegmentedRoadView implements Constants, ActionListener {

    private JFrame frame = null;//Simulation main screen frame.
    private MyPanel panel = null; // My Panel instance;
    private CarSimulation sim = null; //Vehicle Simulation Instance.
    private boolean paused = false; // to Pause the race simulation
    private JDialog dialogueWindow; // Opens dialogue window when clicked Quit.
    private int vehicleColor; // Used to store the index of the color.
    private JButton btnStart;

    public SegmentedRoadView() {
        initVehicleGUi();
    }

    //Method to initialise the main frame of the simulation
    private void initVehicleGUi() {
        frame = new JFrame();
        frame.setTitle(AppName);
        frame.setResizable(false);
        frame.setSize(frameWidth, frameHeight);
        frame.setBackground(Color.GREEN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(getNorthPanel(), BorderLayout.NORTH);
        panel = new MyPanel();
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel getNorthPanel(){
        btnStart = new JButton("Start");
        btnStart.setActionCommand("Start");
        btnStart.addActionListener(this);
        JPanel pan = new JPanel();
        pan.add(btnStart);
        return pan;
    }

    public static void main(String[] args) {
        SegmentedRoadView myApp = new SegmentedRoadView();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        sim = new CarSimulation();
        System.out.println("Start was pressed");
        sim.addObserver(panel);
        sim.startSim();
        sim.setRunning(true); // force this on early, because we're about to reset the buttons
    }
}
