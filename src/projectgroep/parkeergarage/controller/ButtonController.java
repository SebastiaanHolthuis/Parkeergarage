package projectgroep.parkeergarage.controller;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JSlider;

import projectgroep.parkeergarage.logic.ParkeerLogic;
import projectgroep.parkeergarage.main.Simulator;

@SuppressWarnings("serial")
public class ButtonController extends AbstractController {
    int width = 80;

    JButton OneStep = new JButton("+10") {{
        setBounds(141, 572, width, 26);
        addActionListener(e -> parkeerLogic.tickMany(10));
    }};

    JButton StepBack = new JButton("-10") {{
        setBounds(10, 572, width, 26);
        addActionListener(e -> parkeerLogic.stepBack(10));
    }};

    JButton Play = new JButton("Play") {{
        setBounds(10, 624, width, 23);
        addActionListener(e -> parkeerLogic.play());
    }};

    JButton Pause = new JButton("Pause") {{
        setBounds(144, 624, width, 23);
        addActionListener(e -> parkeerLogic.pause());
    }};

    JButton Reset = new JButton("Reset") {{
        setBounds(66, 725, 89, 23);
        addActionListener(e -> sim.restart(parkeerLogic.settings));
    }};

    JSlider TickPauseSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1) {{
        setBounds(10, 681, 200, 23);
        addChangeListener(e -> parkeerLogic.tickPause = 100 - getValue());
        setPaintTicks(true);
        setPaintLabels(true);
    }};


    ParkeerLogic parkeerLogic;
    Simulator sim;

    public ButtonController(Simulator simulator, ParkeerLogic parkeerLogic) {
        this.parkeerLogic = parkeerLogic;
        this.sim = simulator;

        Container contentPane = simulator.getScreen().getContentPane();

        contentPane.add(Play);
        contentPane.add(Pause);
        contentPane.add(OneStep);
        contentPane.add(StepBack);
        contentPane.add(Reset);
        contentPane.add(TickPauseSlider);

        TickPauseSlider.setValue(100 - parkeerLogic.tickPause);
    }
}
