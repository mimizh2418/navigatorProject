package navigator;

import navigator.graph.Graph;
import navigator.ui.ControlPanel;
import navigator.ui.PathPanel;
import navigator.ui.ViewCanvas;

import javax.swing.*;
import java.awt.*;

class Main
{
    public static void main(String[] args)
    {
        Graph graph;
        graph = new Graph("data/Catlin2-allroads-2020");
        if (System.getProperty("os.name").startsWith("Windows")) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ViewCanvas viewCanvas = new ViewCanvas(graph,1050, 700);

        JFrame frame = new JFrame("Atari Maps");

        JPanel mainPanel = new JPanel();
        PathPanel pathPanel = new PathPanel();
        ControlPanel controlPanel = new ControlPanel(viewCanvas, pathPanel);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(viewCanvas, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(pathPanel, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
        frame.setVisible(true);
        frame.toFront();
    }

}