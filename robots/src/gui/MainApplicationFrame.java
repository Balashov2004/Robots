package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
//        logWindow.setLocation(10,10);
//        logWindow.setSize(300, 800);
//        setMinimumSize(logWindow.getSize());
//        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Меню");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Новое игровое поле");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("окно");
        menuItem.addActionListener((event) -> {
            GameWindow newGameWindow = new GameWindow();
            newGameWindow.setSize(400, 400);
            addWindow(newGameWindow);
        });
        menu.add(menuItem);

        JMenuItem LogItem = new JMenuItem("Окно логов");
        LogItem.setMnemonic(KeyEvent.VK_L);
        LogItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK)); // Исправлено на VK_L
        LogItem.setActionCommand("Лог");

        LogItem.addActionListener((event) -> {
            LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
            addWindow(logWindow);
        });
        menu.add(LogItem);


        menuItem = new JMenuItem("Выход");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("выход");
        menuItem.addActionListener((event) -> {
            String message = "Вы уверены, что хотите выйти?";
            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");
            int confirmation = JOptionPane.showConfirmDialog(this, message, "Подтверждение выхода", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                JMenuItem source = (JMenuItem) event.getSource();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
                if (frame == null) {
                    this.dispose();
                }
            }
        });
        menu.add(menuItem);


        
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        
        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        
        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
