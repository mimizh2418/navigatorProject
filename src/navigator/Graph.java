package navigator;

import java.util.*;
import java.io.*;

public class Graph {
    private final HashMap<Integer, Node> nodeMap;
    private final HashMap<Integer, Link[]> linkMap;
    private final ArrayList<Node> nodes;
    public final ArrayList<Link> links;

    public Graph(String dataFolder) {
        nodes = new ArrayList<>();
        links = new ArrayList<>();
        nodeMap = new HashMap<>();
        linkMap = new HashMap<>();
        readNodes(dataFolder);
        readLinks(dataFolder);
        readWaypoints(dataFolder);
    }

    public List<Link> links() {
        return links;
    }

    private void readNodes(String dataFolder) {
        try {
            DataInputStream inStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFolder + "/nodes.bin")));
            int numNodes = inStream.readInt();
            for (int i=0; i<numNodes; i++) {
                int nodeID = inStream.readInt();
                int x = inStream.readInt();
                int y = inStream.readInt();
                Node n = new Node(nodeID, x, y);
                nodeMap.put(nodeID, n);
                nodes.add(n);
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

                Link[] road = new Link[oneway];
                Node start = nodeMap.get(startNodeID);
                Node end = nodeMap.get(endNodeID);
                Link l1 = new Link(name, linkID, start, end, length);
                road[0] = l1;
                links.add(l1);
                nodeMap.get(startNodeID).addNeighbor(l1);
                if (oneway == 2) {
                    Link l2 = new Link(name, linkID, end, start, length);
                    road[1] = l2;
                    links.add(l2);
                    nodeMap.get(endNodeID).addNeighbor(l2);
                }
                linkMap.put(linkID, road);
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
                    Link[] links = linkMap.get(linkID);
                    Arrays.stream(links).forEach((Link l) -> l.addWaypoint(waypoint));
                }
            }
            waypointsStream.close();
        } catch (IOException e) {
            System.err.println("error on waypoints: " + e.getMessage());
        }
    }

    // returns the Node closest to the given X and Y coordinates (OK to be O(N))
    public Node findClosestNode(int targetX, int targetY) {
        double smallestDist = Double.POSITIVE_INFINITY;
        Node closest = null;
        for (Node node : nodes) {
            double dist = Math.hypot(targetX - node.x, targetY - node.y);
            if (dist < smallestDist) {
                smallestDist = dist;
                closest = node;
            }
        }
        if (closest == null) throw new IllegalStateException("no nodes");
        return closest;
    }

    // given two nodes, finds the shortest path between then using Dijkstra's Algorithm
    public List<Link> findPath(Node startNode, Node endNode) {
        nodes.forEach(Node::resetPath);
        startNode.shortestPathLength = 0;
        PriorityQueue<Node> frontier = new PriorityQueue<>();

        frontier.add(startNode);
        while (!frontier.isEmpty()) {
            Node best = frontier.poll();
            if (best.equals(endNode)) break;
            for (Link neighbor : best.neighbors) {
                if (best.shortestPathLength + neighbor.length < neighbor.end.shortestPathLength) {
                    neighbor.end.shortestPathLength = best.shortestPathLength + neighbor.length;
                    neighbor.end.bestInbound = neighbor;
                    frontier.add(neighbor.end);
                }
            }
        }

        List<Link> path = new LinkedList<>();
        Node currentNode = endNode;
        while (!currentNode.equals(startNode)) {
            if (currentNode.bestInbound == null) return null;
            path.add(0, currentNode.bestInbound);
            currentNode = currentNode.bestInbound.start;
        }
        return path;
    }
}
