package projectgroep.parkeergarage.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.view.CarParkView;
import projectgroep.parkeergarage.view.ChartTabs;
import projectgroep.parkeergarage.view.LegendView;
import projectgroep.parkeergarage.fx.SettingsView;
import projectgroep.parkeergarage.view.TextStatisticsView;


public class Simulator {

    private ParkeerLogic parkeerLogic;
    private CarParkView carParkView;
    private SettingsView settingsView;
    private TextStatisticsView textStatisticsView;

    private ChartTabs chartTabs;
    private LegendView legendView;

    private JFrame screen;
    private JFrame settingsScreen;

    private Container contentPane;
    private Container settingsContentPane;

    private JPanel panel;
    private JMenuItem mntmSettings;
    private JMenu mnSimulator;

    private JTabbedPane tabbedPane;

    private int
            yOffset = 10,
            gap = 20,
            panelWidth = 300,
            carParkWidth = 865,
            pieChartWidth = 607;

    public Simulator(Settings settings) {
        createInstances(settings);
        initializeFrame();
    }

    void createInstances(Settings settings) {
        screen = new JFrame() {{
            setTitle("Parkeergarage simulator - ITV1C groep C");
            setPreferredSize(new Dimension((gap * 3) + panelWidth + carParkWidth + pieChartWidth, 800));
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            contentPane = getContentPane();
            contentPane.setBackground(SystemColor.control);
            contentPane.setLayout(null);
        }};

        parkeerLogic = new ParkeerLogic(settings);
//        buttonController = new ButtonController(this, parkeerLogic);

        initializeCarPark();
        initializeCharts();
        initializeStatistics();
        initializeSettings();
        initializeLegend();
        initializePanel();
        initializeTabs();
        initializeMenu();

        initializeFrame();

        addViews();
    }

    void initializeFrame() {
        addElementsToContentPane();

        screen.pack();
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);

        carParkView.updateView();
        chartTabs.updateView();
        legendView.updateView();
    }


    void addViews() {
        parkeerLogic.addView(carParkView);
        parkeerLogic.addView(settingsView);
        parkeerLogic.addView(textStatisticsView);
        parkeerLogic.addView(chartTabs);
        parkeerLogic.addView(legendView);
    }

    void initializeStatistics() {
        textStatisticsView = new TextStatisticsView(parkeerLogic) {{
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            updateView();
            setBounds(gap, 11, panelWidth, 276);
            setBorder(null);
            setLayout(new GridLayout(1, yOffset, 0, 0));
        }};
    }

    void initializeCarPark() {
        carParkView = new CarParkView(parkeerLogic) {{
            setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
            setBackground(SystemColor.control);
            setBounds(panelWidth + gap, yOffset, carParkWidth, 501);
        }};
    }

    void initializeCharts() {
        chartTabs = new ChartTabs(parkeerLogic) {{
            setBackground(Color.WHITE);
//            setBorder(new LineBorder(new Color(0, 0, 0)));
            setBounds(panelWidth + carParkWidth + gap * 2, yOffset, pieChartWidth, 501);
        }};
    }

    void initializeTabs() {
        tabbedPane = new JTabbedPane() {{
            addTab("Statistics", textStatisticsView);
            addTab("Settings", settingsView);
            setEnabled(true);
            setBounds(0, 0, panelWidth, 501);
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

        mnSimulator.add(mntmSettings);
        menuBar.add(mnSimulator);
    }

    void initializePanel() {
        panel = new JPanel() {{
            setBounds(10, yOffset, panelWidth, 501);
            setLayout(null);
        }};
    }

    void initializeLegend() {
        legendView = new LegendView(parkeerLogic) {{
            setBackground(Color.WHITE);
            setBorder(new LineBorder(new Color(0, 0, 0)));
            setBounds(panelWidth + carParkWidth + gap * 2, 550, pieChartWidth, 131);
        }};
    }

    void addElementsToContentPane() {
        contentPane.add(chartTabs);
        contentPane.add(carParkView);
//        contentPane.add(buttonController);
        contentPane.add(legendView);
        contentPane.add(panel);

        // Menu bar voor nu gedeactiveerd
        // contentPane.add(menuBar);
    }


    private void initializeSettings() {
//        settingsView = new SettingsView(parkeerLogic, this);
    }


    /**
     * Disposes of the screens and reinitializes the simulator in a new thread
     * with the new settings
     */
    public void restart(Settings settings) {
        SettingsRepository.saveSettings(settings);
        screen.dispose();
        parkeerLogic.pause();

        Thread t = new Thread(() -> {
            createInstances(settings);
            initializeFrame();
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
