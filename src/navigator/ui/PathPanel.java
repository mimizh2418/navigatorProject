package navigator.ui;

import navigator.graph.Link;

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
            if (!prevPathname.equals(l.name)) {
                if (runningLength >= 0) {
                    directions.append(String.format("%s - %.2f mi%n", shortenName(prevPathname), runningLength));
                    totalPathLength += runningLength;
                }
                runningLength = l.length;
                prevPathname = l.name;
            } else {
                runningLength += l.length;
            }
        }
        directions.append(String.format("%s - %.2f mi%n", shortenName(prevPathname), runningLength));
        totalPathLength += runningLength;
        pathLengthLabel.setText(String.format("Route length: %.2f %s", totalPathLength, "mi"));
        pathStats.setText(directions.toString());
    }

    private String shortenName(String streetName) {
        if (streetName.isEmpty()) return "Unnamed Rd";
        StringBuilder shortened = new StringBuilder();
        String[] words = streetName.split(" ");

        String direction = words[0];
        String shortDirect = switch (direction) {
            case "North" -> "N";
            case "Northwest" -> "NW";
            case "Northeast" -> "NE";
            case "West" -> "W";
            case "East" -> "E";
            case "South" -> "S";
            case "Southwest" -> "SW";
            case "Southeast" -> "SE";
            default -> "";
        };
        shortened.append(shortDirect);
        if (!shortDirect.isEmpty()) shortened.append(" ");

        for (int i = shortDirect.isEmpty() ? 0 : 1; i < words.length - 1; i++) {
            shortened.append(words[i]).append(" ");
        }

        String streetType = words[words.length - 1];
        String shortStreet = switch (streetType) {
            case "Street" -> "St";
            case "Road" -> "Rd";
            case "Lane" -> "Ln";
            case "Drive" -> "Dr";
            case "Avenue" -> "Ave";
            case "Court" -> "Ct";
            case "Center" -> "Ctr";
            case "Boulevard" -> "Blvd";
            case "Highway" -> "Hwy";
            case "Freeway" -> "Fwy";
            default -> streetType;
        };
        shortened.append(shortStreet);

        return shortened.toString();
    }

    public void reset() {
        pathStats.setText("No path generated");
        pathLengthLabel.setText("Route length: ");
    }
}
