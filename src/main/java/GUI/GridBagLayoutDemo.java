package GUI;

import java.awt.*;
import javax.swing.*;

public class GridBagLayoutDemo {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    public static void addComponentsToPane(Container pane) {
        JPanel panel1 = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.weightx = 0.3;
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton button = new JButton("BUTTON 1");
        panel1.add(button, BorderLayout.CENTER);

        button = new JButton("BUTTON 2");
        panel2.add(button, gbc);
        JButton button1 = new JButton("BUTTON 3");

        gbc.weighty = 0.2;
        gbc.gridy = 1;

        panel2.add(button1, gbc);

        pane.add(panel1, BorderLayout.WEST);
        pane.add(panel2, BorderLayout.CENTER);
    }

    private static void createAndShowGUI() {
        // Создание окна 
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setSize(100,100);
        // Установить панель содержания
        addComponentsToPane(panel);
        frame.getContentPane().add(panel);
        // Показать окно 
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
} 