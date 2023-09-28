package navigator.graph;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Link {
    private final Node start;
    private final Node end;
    private final String name;
    private final double length;
    private final List<Waypoint> waypoints;
    private final int id;

    public Link(String name, int id, Node start, Node end, double length) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.length = length;
        this.id = id;
        waypoints = new ArrayList<>();
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public double length() {
        return length;
    }

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public void draw(Graphics2D pen, boolean isPath) {
        pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isPath) {
            pen.setColor(Color.BLUE);
            pen.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        } else {
            pen.setColor(Color.BLACK);
            pen.setStroke(new BasicStroke(1F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Waypoint p1 = waypoints.get(i), p2 = waypoints.get(i + 1);
            if (isPath) pen.drawLine(p1.x(), p1.y(), p2.x(), p2.y());
            else pen.drawLine(p1.x()*2, p1.y()*2, p2.x()*2, p2.y()*2);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return id == link.id && start.equals(link.start);
    }

    @Override
    public String toString() {
        return name;
    }
}
