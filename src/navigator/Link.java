package navigator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Link {
    public final Node start;
    public final Node end;
    public final String name;
    public final double length;
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

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public void draw(Graphics2D pen, boolean isPath) {
        pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isPath) {
            pen.setColor(Color.BLUE);
            pen.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        } else {
            pen.setColor(Color.BLACK);
            pen.setStroke(new BasicStroke(0.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Waypoint p1 = waypoints.get(i), p2 = waypoints.get(i + 1);
            pen.drawLine(p1.x(), p1.y(), p2.x(), p2.y());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return id == link.id && start.equals(link.start);
    }
}
