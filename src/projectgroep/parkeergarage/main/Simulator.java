package projectgroep.parkeergarage.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.view.CarParkView;
import projectgroep.parkeergarage.view.PieChartView;
import projectgroep.parkeergarage.view.SettingsView;
import projectgroep.parkeergarage.view.TextStatisticsView;

public class Simulator {

    private ParkeerLogic parkeerLogic;
    private CarParkView carParkView;
    private SettingsView settingsView;
    private TextStatisticsView textStatisticsView;
    private PieChartView pieChartView;
    
    private JFrame screen;
    private JFrame settingsScreen;

    private Container contentPane;
    private Container settingsContentPane;
    private JButton Apply;
    private JSlider slider;

    public Simulator(Settings settings) {
        createInstances(settings);
        initializeFrame();
        initializeSettingsFrame();
    }

    private void createInstances(Settings settings) {
        screen = new JFrame();
        screen.getContentPane().setBackground(SystemColor.control);
        settingsScreen = new JFrame();

        parkeerLogic = new ParkeerLogic(settings);
        
        carParkView = new CarParkView(parkeerLogic);
        carParkView.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        carParkView.setBackground(SystemColor.control);
        carParkView.setBounds(227, 11, 865, 549);
        settingsView = new SettingsView(parkeerLogic, this);
        
        textStatisticsView = new TextStatisticsView(parkeerLogic);
        textStatisticsView.setBounds(0, 0, 260, 328);

        textStatisticsView.setBorder(null);
        textStatisticsView.setBounds(10, 11, 207, 549);
        
        pieChartView = new PieChartView(parkeerLogic);
        pieChartView.setBackground(Color.WHITE);
        pieChartView.setBorder(new LineBorder(new Color(0, 0, 0)));
        pieChartView.setBounds(1102, 11, 207, 549);
        
        parkeerLogic.addView(carParkView);
        parkeerLogic.addView(settingsView);
        parkeerLogic.addView(textStatisticsView);
        parkeerLogic.addView(pieChartView);
    }

    private void initializeFrame() {
        screen.setTitle("Parkeergarage simulator - ITV1C groep C");
        screen.setPreferredSize(new Dimension(1325, 600));
        screen.setResizable(false);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = screen.getContentPane();
        screen.getContentPane().setLayout(null);

        contentPane.add(pieChartView);
        contentPane.add(textStatisticsView);
        contentPane.add(carParkView);
        
        Apply = new JButton("Apply");
        Apply.setBounds(80, 447, 89, 23);
        screen.getContentPane().add(Apply);
        
        slider = new JSlider();
        slider.setMinimum(1);
        slider.setBounds(23, 382, 200, 26);
        screen.getContentPane().add(slider);

        screen.pack();
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);

        carParkView.updateView();
        textStatisticsView.updateView();
        pieChartView.updateView();
    }

    private void initializeSettingsFrame() {
        settingsScreen.setTitle("Settings");
        settingsScreen.setPreferredSize(new Dimension(900, 600));
        settingsScreen.setResizable(false);
        settingsScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        settingsContentPane = settingsScreen.getContentPane();
        settingsContentPane.add(settingsView);

        settingsScreen.pack();
        settingsScreen.setLocationRelativeTo(null);
        settingsScreen.setVisible(true);
    }


    /**
     * Disposes of the screens and reinitializes the simulator in a new changed
     * with the new settings
     */
    public void restart(Settings settings) {
        SettingsRepository.saveSettings(settings);
        settingsScreen.dispose();
        screen.dispose();
        parkeerLogic.stop();

        Thread t = new Thread(() -> {
            createInstances(settings);
            initializeFrame();
            initializeSettingsFrame();
            parkeerLogic.run();
        });

        t.start();

    }

    public ParkeerLogic getParkeerLogic() {
        return parkeerLogic;
    }

    public CarParkView getCarParkView() {
        return carParkView;
    }

    public Container getContentPane() {
        return screen.getContentPane();
    }
}
