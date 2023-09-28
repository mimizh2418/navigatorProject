package navigator.ui;

import navigator.graph.Link;
import navigator.utils.RoadNameShortener;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PathPanel extends JPanel {
    private final JTextArea pathStats = new JTextArea("No route selected");
    private final JScrollPane scrollPane = new JScrollPane(pathStats);
    private final JLabel pathLengthLabel = new JLabel("Route length: ");

    public PathPanel() {
        setBorder(BorderFactory.createTitledBorder("Route Information"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        pathStats.setEditable(false);
        pathStats.setLineWrap(false);
        pathStats.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        pathStats.setColumns(20);
        pathStats.setRows(40);
        add(pathLengthLabel);
        add(Box.createVerticalStrut(15));
        add(new JLabel("Directions"));
        add(scrollPane);
    }

    public void setPath(List<Link> path) {
        StringBuilder directions = new StringBuilder();

        String prevPathname = "";
        double runningLength = -1;
        double totalPathLength = 0;
        for (Link l : path) {
            if (!prevPathname.equals(l.toString())) {
                if (runningLength >= 0) {
                    directions.append(String.format("%s - %.2f mi%n", RoadNameShortener.shorten(prevPathname), runningLength));
                    totalPathLength += runningLength;
                }
                runningLength = l.length();
                prevPathname = l.toString();
            } else {
                runningLength += l.length();
            }
        }
        directions.append(String.format("%s - %.2f mi%n", RoadNameShortener.shorten(prevPathname), runningLength));
        totalPathLength += runningLength;
        pathLengthLabel.setText(String.format("Route length: %.2f %s", totalPathLength, "mi"));
        pathStats.setText(directions.toString());
    }

    public void reset() {
        pathStats.setText("No path generated");
        pathLengthLabel.setText("Route length: ");
    }
}
