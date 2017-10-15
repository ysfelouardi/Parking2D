import java.awt.EventQueue;

import javax.swing.JFrame;

public class ParkingApp extends JFrame{
    public ParkingApp() {

        initUI();
    }

    private void initUI() {

        add(new ParkingArea());
        setResizable(false);
        pack();
        setTitle("Parking2D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
    	ParkingApp ex = new ParkingApp();
    }
}
