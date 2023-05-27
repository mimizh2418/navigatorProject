package navigator.graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    public final int x;
    public final int y;
    public final int id;
    public double shortestPathLength;
    public Link bestInbound;
    public final List<Link> neighbors;

    public Node(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id  = id;
        neighbors = new ArrayList<>();
        shortestPathLength = Double.POSITIVE_INFINITY;
        bestInbound = null;
    }

    public void resetPath() {
        shortestPathLength = Double.POSITIVE_INFINITY;
        bestInbound = null;
    }

    public void addNeighbor(Link neighbor) {
        if (neighbor.start.equals(this)) neighbors.add(neighbor);
    }

    public void draw(Graphics2D pen, boolean isStart) {
        pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        pen.setColor(isStart ? Color.GREEN : Color.RED);
        int r = 3;
        pen.fillOval(x - r, y - r, 2*r, 2*r);
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(shortestPathLength, other.shortestPathLength);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }
}
  