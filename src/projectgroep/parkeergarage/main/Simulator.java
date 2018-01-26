package projectgroep.parkeergarage.main;

import java.awt.*;
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
import projectgroep.parkeergarage.controller.ButtonController;

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
    private JPanel panel;
    private JMenuItem mntmSettings;
    private JMenu mnSimulator;
    private ButtonController buttonController;

    private JTabbedPane tabbedPane;

    public Simulator(Settings settings) {
        createInstances(settings);
        initializeFrame();
    }

    private void createInstances(Settings settings) {
        screen = new JFrame();
        screen.getContentPane().setBackground(SystemColor.control);
        settingsScreen = new JFrame();


        parkeerLogic = new ParkeerLogic(settings);
        buttonController = new ButtonController(this, parkeerLogic);

        initializeCarPark();
        initializePieChart();
        initializeStatistics();
        initializeSettings();

        addViews();
    }

    void addViews() {
        parkeerLogic.addView(carParkView);
        parkeerLogic.addView(settingsView);
        parkeerLogic.addView(textStatisticsView);
        parkeerLogic.addView(pieChartView);
    }

    void initializeStatistics() {
        textStatisticsView = new TextStatisticsView(parkeerLogic) {{
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            updateView();
            setBounds(10, 11, 207, 276);
            setBorder(null);
            setLayout(new GridLayout(1, 0, 0, 0));
        }};
    }

    void initializeCarPark() {
        carParkView = new CarParkView(parkeerLogic) {{
            setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
            setBackground(SystemColor.control);
            setBounds(227, 59, 865, 501);
        }};
    }

    void initializePieChart() {
        pieChartView = new PieChartView(parkeerLogic) {{
            setBackground(Color.WHITE);
            setBorder(new LineBorder(new Color(0, 0, 0)));
            setBounds(1102, 59, 207, 501);
        }};
    }

    void initializeTabs() {
        tabbedPane = new JTabbedPane() {{
            addTab("Statistics", textStatisticsView);
            addTab("Settings", settingsView);

            setEnabled(true);
            setBounds(0, 0, 207, 549);
        }};

        panel.add(tabbedPane);
    }

    void initializeMenu() {
        JMenuBar menuBar = new JMenuBar() {{
            setBackground(Color.LIGHT_GRAY);
            setBounds(0, 0, 1319, 36);
        }};

        mnSimulator = new JMenu("Simulator") {{
            setBackground(Color.GRAY);
            setForeground(Color.WHITE);
        }};


        mntmSettings = new JMenuItem("Settings") {{
            setForeground(Color.DARK_GRAY);
            setBackground(Color.WHITE);
            addActionListener(e -> settingsScreen.setVisible(true));
        }};

        screen.getContentPane().add(menuBar);
        mnSimulator.add(mntmSettings);
        menuBar.add(mnSimulator);
    }

    void initializePanel() {

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
//
//        textStatisticsView = new TextStatisticsView(parkeerLogic);
//        textStatisticsView.setBackground(SystemColor.control);
//

        initializeTabs();

//        textStatisticsView.setBorder(null);
//        parkeerLogic.addView(textStatisticsView);

        contentPane.add(carParkView);
        contentPane.add(buttonController);

        initializeMenu();

        screen.pack();
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);

        carParkView.updateView();
        pieChartView.updateView();
    }

    private void initializeSettings() {
        settingsView = new SettingsView(parkeerLogic, this);

    }


    /**
     * Disposes of the screens and reinitializes the simulator in a new thread
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
            initializeSettings();
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

    public JFrame getScreen() {
        return screen;
    }

}
