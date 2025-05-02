package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class DriverWindow {

    public DriverWindow() {
        JFrame frame = new JFrame("Водитель");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Интерфейс для водителя");
        label.setBounds(10, 20, 150, 25);
        panel.add(label);

        // Здесь можно добавить действия, доступные для водителя
    }
}
