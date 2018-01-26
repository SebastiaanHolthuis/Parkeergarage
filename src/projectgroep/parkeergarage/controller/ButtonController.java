package projectgroep.parkeergarage.controller;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.main.Simulator;

@SuppressWarnings("serial")
public class ButtonController extends AbstractController {
    JButton Apply = new JButton("Start");
    JButton OneStep = new JButton("+1");
    JButton Stop = new JButton("Stop");
    JButton StepBack = new JButton("Vorige stap");

    JButton Reset = new JButton("Reset");
    ParkeerLogic parkeerLogic;
    Simulator sim;
    ParkeerLogic tickfor;
    ParkeerLogic tickback;

    public ButtonController(Simulator simulator, ParkeerLogic parkeerLogic) {
        this.parkeerLogic = parkeerLogic;
        this.sim = simulator;
        Apply.setBounds(10, 572, 73, 26);
        simulator.getScreen().getContentPane().add(Apply);

        Stop.setBounds(141, 572, 73, 26);
        simulator.getScreen().getContentPane().add(Stop);

        OneStep.setBounds(144, 624, 73, 23);
        simulator.getScreen().getContentPane().add(OneStep);

        StepBack.setBounds(10, 624, 73, 23);
        simulator.getScreen().getContentPane().add(StepBack);

        Reset.setBounds(66, 681, 89, 23);
        simulator.getScreen().getContentPane().add(Reset);

        Apply.addActionListener(e -> parkeerLogic.run());
        Stop.addActionListener(e -> parkeerLogic.stop());
        OneStep.addActionListener(e -> parkeerLogic.tickSimulator());
        StepBack.addActionListener(e -> parkeerLogic.GetHistory());
        Reset.addActionListener(e -> sim.restart(parkeerLogic.settings));
    }
}
