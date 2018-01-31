package projectgroep.parkeergarage.controller;

import projectgroep.parkeergarage.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AbstractController extends JPanel implements ActionListener, View {

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void updateView() {

    }
}
