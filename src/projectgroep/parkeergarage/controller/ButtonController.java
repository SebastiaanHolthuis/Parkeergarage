package projectgroep.parkeergarage.controller;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.main.Simulator;

@SuppressWarnings("serial")
public class ButtonController extends AbstractController {
    JButton Apply = new JButton("Start") {{
        setBounds(10, 572, 73, 26);
        addActionListener(e -> parkeerLogic.start());
    }};

    JButton OneStep = new JButton("+1") {{
        setBounds(141, 572, 73, 26);
        addActionListener(e -> parkeerLogic.tickSimulator());
    }};

    JButton Stop = new JButton("Stop") {{
        setBounds(144, 624, 73, 23);
        addActionListener(e -> parkeerLogic.stop());
    }};

    JButton StepBack = new JButton("Vorige stap") {{
        setBounds(10, 624, 73, 23);
        addActionListener(e -> parkeerLogic.GetHistory());
    }};

    JButton Reset = new JButton("Reset") {{
        setBounds(66, 681, 89, 23);
        addActionListener(e -> sim.restart(parkeerLogic.settings));
    }};


    ParkeerLogic parkeerLogic;
    Simulator sim;
    ParkeerLogic tickfor;
    ParkeerLogic tickback;

    public ButtonController(Simulator simulator, ParkeerLogic parkeerLogic) {
        this.parkeerLogic = parkeerLogic;
        this.sim = simulator;

        Container contentPane = simulator.getScreen().getContentPane();

        contentPane.add(Apply);
        contentPane.add(Stop);
        contentPane.add(OneStep);
        contentPane.add(StepBack);
        contentPane.add(Reset);
    }
}
