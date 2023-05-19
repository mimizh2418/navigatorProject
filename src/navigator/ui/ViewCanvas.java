package navigator.ui;

import navigator.Graph;
import navigator.Node;
import navigator.Link;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ViewCanvas extends ImageCanvas implements MouseListener, MouseMotionListener {
    private Graph graph;
    private Node startNode = null, endNode = null;
    private List<Link> path = null;
    private boolean clickSetsStart = true;
    private BufferedImage mapImage;

    public ViewCanvas(Graph graph, int width, int height) {
        super(width, height);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.graph = graph;
        updateMapImage();
    }

    private void updateMapImage() {
        mapImage = new BufferedImage(getWidth()*2, getHeight()*2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D pen = mapImage.createGraphics();
        pen.setColor(Color.WHITE);
        graph.links().forEach((Link l) -> l.draw(pen, false));
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        updateMapImage();
        reset();
    }

    public void reset() {
        startNode = null;
        endNode = null;
        path = null;
        clickSetsStart = true;
        repaint();
    }

    public void generatePath() {
        if (startNode != null && endNode != null) {
            path = graph.findPath(startNode, endNode);
            if (path == null) {
                JOptionPane.showMessageDialog(this, "Path does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                reset();
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        Graphics2D pen2 = (Graphics2D) pen.create();
        if (mapImage != null) pen2.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);
        if (path != null) path.forEach((Link l) -> l.draw(pen2, true));

        if (startNode != null) startNode.draw(pen2, true);
        if (endNode != null) endNode.draw(pen2, false);
    }

    public void mousePressed(MouseEvent event)
    {
        int mouseX = event.getX();
        int mouseY = event.getY();
        Node node = graph.findClosestNode(mouseX, mouseY);

        if (clickSetsStart) {
            if (path != null) {
                path = null;
                endNode = null;
            }
            startNode = node;
            clickSetsStart = false;
        } else {
            endNode = node;
            clickSetsStart = true;
        }
        repaint();
    }

    public void mouseReleased(MouseEvent event) {}

    public void mouseClicked(MouseEvent event) {}

    public void mouseEntered(MouseEvent event) {}

    public void mouseExited(MouseEvent event) {}

    public void mouseDragged(MouseEvent event) {}

    public void mouseMoved(MouseEvent event) {}
}