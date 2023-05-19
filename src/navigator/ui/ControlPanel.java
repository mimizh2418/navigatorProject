package navigator.ui;

import navigator.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private final ViewCanvas canvas;

    public ControlPanel(ViewCanvas canvas) {
        this.canvas = canvas;
        JPanel chooserPanel = new JPanel();
        chooserPanel.add(new MapChooser());
        chooserPanel.setBorder(BorderFactory.createTitledBorder("Map source"));
        add(chooserPanel);
        JPanel pathPanel = new JPanel();
        pathPanel.add(new ResetPathButton());
        pathPanel.add(new FindPathButton());
        pathPanel.setBorder(BorderFactory.createTitledBorder("Navigation"));
        add(pathPanel);
    }

    private class MapChooser extends JComboBox<String> implements ActionListener {
        public MapChooser() {
            super(new String[] {"Catlin2-allroads-2020", "Portland1-allroads-2020", "US-primary-2020"});
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setGraph(new Graph("data/" + getSelectedItem()));
        }
    }

    private class ResetPathButton extends JButton implements ActionListener {
        public ResetPathButton() {
            super("Reset");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.reset();
        }
    }

    private class FindPathButton extends JButton implements ActionListener {
        public FindPathButton() {
            super("Generate path");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.generatePath();
        }
    }
}
