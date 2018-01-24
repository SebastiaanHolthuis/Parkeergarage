package projectgroep.parkeergarage.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.view.CarParkView;
import projectgroep.parkeergarage.view.PieChartView;
import projectgroep.parkeergarage.view.SettingsView;
import projectgroep.parkeergarage.view.TextStatisticsView;

import javax.swing.GroupLayout.Alignment;

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
    private JButton Stop;
    private JSlider slider;
    private JPanel panel;
    private JMenuItem mntmSettings;
    private JMenu mnSimulator;
    private JMenuItem mntmStop;

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
        carParkView.setBounds(227, 59, 865, 501);
        settingsView = new SettingsView(parkeerLogic, this);

        textStatisticsView = new TextStatisticsView(parkeerLogic);
        textStatisticsView.setBounds(10, 11, 207, 276);

        textStatisticsView.setBorder(null);
        textStatisticsView.setBounds(10, 11, 207, 549);

        pieChartView = new PieChartView(parkeerLogic);
        pieChartView.setBackground(Color.WHITE);
        pieChartView.setBorder(new LineBorder(new Color(0, 0, 0)));
        pieChartView.setBounds(1102, 59, 207, 501);

        parkeerLogic.addView(carParkView);
        parkeerLogic.addView(settingsView);
        parkeerLogic.addView(textStatisticsView);
        textStatisticsView.setLayout(new GridLayout(1, 0, 0, 0));

        parkeerLogic.addView(pieChartView);
    }

    private void initializeFrame() {
        screen.setTitle("Parkeergarage simulator - ITV1C groep C");
        screen.setPreferredSize(new Dimension(1325, 800));
        screen.setResizable(false);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = screen.getContentPane();
        screen.getContentPane().setLayout(null);

        contentPane.add(pieChartView);

        panel = new JPanel();
        panel.setBounds(10, 59, 207, 501);
        screen.getContentPane().add(panel);
        panel.setLayout(null);

        textStatisticsView = new TextStatisticsView(parkeerLogic);
        textStatisticsView.setBackground(SystemColor.control);
        textStatisticsView.setBounds(0, 0, 207, 549);
        panel.add(textStatisticsView);

        textStatisticsView.setBorder(null);
        parkeerLogic.addView(textStatisticsView);
        textStatisticsView.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        textStatisticsView.updateView();
        contentPane.add(carParkView);

        Apply = new JButton("Apply");
        Apply.setBounds(10, 574, 73, 23);
        screen.getContentPane().add(Apply);

        Stop = new JButton("Stop");
        Stop.setBounds(141, 572,73, 26);
        screen.getContentPane().add(Stop);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.LIGHT_GRAY);
        menuBar.setBounds(0, 0, 1319, 36);
        screen.getContentPane().add(menuBar);

        mnSimulator = new JMenu("Simulator");
        mnSimulator.setBackground(Color.GRAY);
        mnSimulator.setForeground(Color.WHITE);
        menuBar.add(mnSimulator);

        mntmStop = new JMenuItem("Stop");
        mntmStop.setForeground(Color.DARK_GRAY);
        mntmStop.setBackground(Color.WHITE);
        mnSimulator.add(mntmStop);

        mntmSettings = new JMenuItem("Settings");
        mnSimulator.add(mntmSettings);
        mntmSettings.setForeground(Color.DARK_GRAY);
        mntmSettings.setBackground(Color.WHITE);
        
        JButton OneStep = new JButton("+1");
        OneStep.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        OneStep.setBounds(144, 624, 73, 23);
        screen.getContentPane().add(OneStep);
        
        JButton StepBack = new JButton("-1");
        StepBack.setBounds(10, 624, 73, 23);
        screen.getContentPane().add(StepBack);
        
        JButton Reset = new JButton("Reset");
        Reset.setBounds(66, 681, 89, 23);
        screen.getContentPane().add(Reset);

        mntmSettings.addActionListener(e -> settingsScreen.setVisible(true));
        mntmStop.addActionListener(e -> parkeerLogic.stop());
        Apply.addActionListener(e -> parkeerLogic.run());

        screen.pack();
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);

        carParkView.updateView();
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
        settingsScreen.setVisible(false);
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
