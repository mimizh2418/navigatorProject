package navigator.ui;

import navigator.graph.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ControlPanel extends JPanel {
    private final ViewCanvas canvas;
    private final PathPanel pathStatPanel;

    public ControlPanel(ViewCanvas canvas, PathPanel pathStatPanel) {
        this.canvas = canvas;
        this.pathStatPanel = pathStatPanel;
        JPanel chooserPanel = new JPanel();
        chooserPanel.add(new MapChooser());
        chooserPanel.setBorder(BorderFactory.createTitledBorder("Map source"));
        add(chooserPanel);
        JPanel pathControlPanel = new JPanel();
        pathControlPanel.add(new ResetPathButton());
        pathControlPanel.add(new FindPathButton());
        pathControlPanel.setBorder(BorderFactory.createTitledBorder("Navigation"));
        add(pathControlPanel);
    }

    private class MapChooser extends JComboBox<MapType> implements ActionListener {
        public MapChooser() {
            super(new MapType[] {MapType.Catlin, MapType.Portland, MapType.United_States});
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            super.actionPerformed(e);
            canvas.setGraph(new Graph("data/" + ((MapType) Objects.requireNonNull(getSelectedItem())).filename));
            pathStatPanel.reset();
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
            pathStatPanel.reset();
        }
    }

    private class FindPathButton extends JButton implements ActionListener {
        public FindPathButton() {
            super("Find route");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.generatePath();
            if (canvas.getGeneratedPath() != null) pathStatPanel.setPath(canvas.getGeneratedPath());
        }
    }
}
