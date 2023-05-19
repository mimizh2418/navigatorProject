package navigator;

import navigator.ui.ControlPanel;
import navigator.ui.ViewCanvas;

import javax.swing.*;
import java.awt.*;

class Main
{
    public static void main(String[] args)
    {
        Graph graph;
        graph = new Graph("data/Catlin2-allroads-2020");
//        graph = new navigator.Graph("data/Portland1-allroads-2020");
//        graph = new navigator.Graph("data/US-primary-2020");

        ViewCanvas viewCanvas = new ViewCanvas(graph,1000, 700);

        JFrame frame = new JFrame("Path Finder");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(viewCanvas, BorderLayout.CENTER);
        mainPanel.add(new ControlPanel(viewCanvas), BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.toFront();
    }

}