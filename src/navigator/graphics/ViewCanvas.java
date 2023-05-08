package navigator.graphics;

import java.awt.*;
import java.awt.event.*;

public class ViewCanvas extends ImageCanvas implements MouseListener, MouseMotionListener
{
    private Graph graph;
    private Node startNode = null, endNode = null;
    private java.util.List<Link> path = null;
    private boolean clickSetsStart = true;

    ViewCanvas(Graph graph, int width, int height)
    {
        super(width, height);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.graph = graph;
    }

    @Override
    public void paintComponent(Graphics pen) {
        Graphics2D pen2 = (Graphics2D) pen.create();

        //TODO: Draw all of the links in the graph

        //TODO: Draw the startNode if it isn't null
        //TODO: Draw the endNode if it isn't null
        //TODO: Draw the path if it isn't null

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