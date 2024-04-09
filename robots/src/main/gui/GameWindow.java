package gui;


import java.awt.*;
import javax.swing.JPanel;

import model.Robot;

public class GameWindow extends AbstractWindow
{
    private final GameVisualizer m_visualizer;
    public GameWindow(Robot robot)
    {
        super("Игровое поле");
        m_visualizer = new GameVisualizer(robot);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
