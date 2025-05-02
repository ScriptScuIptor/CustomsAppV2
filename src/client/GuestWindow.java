package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class GuestWindow {

    public GuestWindow() {
        JFrame frame = new JFrame("Гость");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Интерфейс для гостя");
        label.setBounds(10, 20, 150, 25);
        panel.add(label);

        // Здесь можно добавить действия, доступные для гостя
    }
}
