package navigator;

import java.util.*;
import java.io.*;

public class Graph {
    private final HashMap<Integer, Node> nodeMap;
    private final HashMap<Integer, List<Link>> linkMap;
    private final Collection<Node> nodes;
    private final Collection<List<Link>> links;

    public Graph(String dataFolder) {
        nodeMap = new HashMap<>();
        linkMap = new HashMap<>();
        readNodes(dataFolder);
        nodes = nodeMap.values();
        readLinks(dataFolder);
        links = linkMap.values();
        readWaypoints(dataFolder);
    }

    private void readNodes(String dataFolder) {
        try {
            DataInputStream inStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/nodes.bin")));
            int numNodes = inStream.readInt();
            for (int i=0; i<numNodes; i++) {
                int nodeID = inStream.readInt();
                int x = inStream.readInt();
                int y = inStream.readInt();
                nodeMap.put(nodeID, new Node(x, y));
            }
            inStream.close();
        }
        catch (IOException e) {
            System.err.println("error on nodes: " + e.getMessage());
        }
    }



    private void readLinks(String dataFolder) {
        try {
            DataInputStream linksStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/links.bin")));
            int numLinks = linksStream.readInt();
            for (int i = 0; i < numLinks; i++) {
                int linkID = linksStream.readInt();
                int startNodeID = linksStream.readInt();
                int endNodeID = linksStream.readInt();
                String name = linksStream.readUTF();
                double length = linksStream.readDouble();
                int oneway = linksStream.readByte();  // 1 for one-way links, 2 for two-way links

                ArrayList<Link> links = new ArrayList<>(oneway);
                Node start = nodeMap.get(startNodeID);
                Node end = nodeMap.get(endNodeID);
                links.add(new Link(name, start, end, length));
                if (oneway == 2) links.add(new Link(name, end, start, length));
                linkMap.put(linkID, links);
            }
            linksStream.close();
        } catch (IOException e) {
            System.err.println("error on links: " + e.getMessage());
        }
    }

    private void readWaypoints(String dataFolder) {
        try {
            DataInputStream waypointsStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/links-waypoints.bin")));

            int numLinks = waypointsStream.readInt();
            for (int i = 0; i < numLinks; i++) {
                int linkID = waypointsStream.readInt();
                int numWaypoints = waypointsStream.readInt();
                for (int p = 0; p < numWaypoints; p++) {
                    int x = waypointsStream.readInt();
                    int y = waypointsStream.readInt();
                    Waypoint waypoint = new Waypoint(x, y);
                    List<Link> links = linkMap.get(linkID);
                    for (Link link : links) {
                        link.addWaypoint(waypoint);
                    }
                }
            }
            waypointsStream.close();
        } catch (IOException e) {
            System.err.println("error on waypoints: " + e.getMessage());
        }
    }

    // returns the navigator.Node closest to the given X and Y coordinates (OK to be O(N))
    public Node findClosestNode(int targetX, int targetY) {
        //TODO: write the findClosestNode function
        double smallestDist = Double.POSITIVE_INFINITY;
        Node closest = null;
        for (Node node : nodes) {
            double dist = Math.hypot(targetX - node.x, targetY - node.y);
            if (dist < smallestDist) {
                smallestDist = dist;
                closest = node;
            }
        }
        if (closest == null) throw new IllegalStateException("no nodes????");
        return closest;
    }

    // given two nodes, finds the shortest path between then using Dijkstra's Algorithm
    public List<Link> findPath(Node startNode, Node endNode) {
        //TODO: write the findPath function, implementing Dijkstra's Algorithm
        return null;
    }
}
