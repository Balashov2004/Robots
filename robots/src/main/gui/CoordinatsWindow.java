package gui;

import model.Robot;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class CoordinatsWindow extends AbstractWindow implements Observer {
        private Robot robot;
        private JTextArea textField;

    public CoordinatsWindow(Robot robot){
            super("Окно состояния робота");
            JPanel panel = new JPanel(new BorderLayout());

            textField = new JTextArea();
            panel.add(textField, BorderLayout.CENTER);

            getContentPane().add(panel);
            pack();

            this.robot = robot;
            robot.addObserver(this);
        }

        @Override
        public void update(Observable o, Object arg) {
            if (o.equals(robot)) {
                if (arg.equals("robot moved"))
                    onRobotMoved();
            }
        }

        private void onRobotMoved() {
            textField.setText(robot.getInfo());
        }
}