package navigator;

import java.util.*;

public class Link {
    private final Node start;
    private final Node end;
    private final String name;
    private final double length;
    private final List<Waypoint> waypoints;

    public Link(String name, Node start, Node end, double length) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.length = length;
        waypoints = new ArrayList<>();
    }

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return start.equals(link.start) && end.equals(link.end) && name.equals(link.name) && waypoints.equals(link.waypoints);
    }
}
