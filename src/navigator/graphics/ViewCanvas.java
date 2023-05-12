package navigator.graphics;

import navigator.Graph;
import navigator.Node;
import navigator.Link;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ViewCanvas extends ImageCanvas implements MouseListener, MouseMotionListener {
    private Graph graph;
    private Node startNode = null, endNode = null;
    private List<Link> path = null;
    private boolean clickSetsStart = true;

    public ViewCanvas(Graph graph, int width, int height) {
        super(width, height);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.graph = graph;
    }

    @Override
    public void paintComponent(Graphics pen) {
        Graphics2D pen2 = (Graphics2D) pen.create();
        pen2.clearRect(0, 0, getWidth(), getHeight());

        graph.links().forEach((Link l) -> l.draw(pen2, false));
        if (path != null) path.forEach((Link l) -> l.draw(pen2, true));
        if (startNode != null) startNode.draw(pen2);
        if (endNode != null) endNode.draw(pen2);
    }

    public void mousePressed(MouseEvent event)
    {
        int mouseX = event.getX();
        int mouseY = event.getY();
        Node node = graph.findClosestNode(mouseX, mouseY);

        if (clickSetsStart) {
            startNode = node;
            clickSetsStart = false;
        } else {
            endNode = node;
            clickSetsStart = true;
        }
        if (startNode != null && endNode != null) {
            path = graph.findPath(startNode, endNode);
        }
        repaint();
    }

    public void mouseReleased(MouseEvent event)
    {
    }

    public void mouseClicked(MouseEvent event)
    {
    }

    public void mouseEntered(MouseEvent event)
    {
    }

    public void mouseExited(MouseEvent event)
    {
    }

    public void mouseDragged(MouseEvent event)
    {
    }

    public void mouseMoved(MouseEvent event)
    {
    }

}