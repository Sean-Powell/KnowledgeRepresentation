package Question2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreatePaths {
    //using Path objects


    public ArrayList<Path> getPaths(ArrayList<Node> _nodes, Node _startNode, String _endNodeName) {
        Path path = new Path(new ArrayList<>(), _startNode, _endNodeName);
        path.addToPath(_startNode.getName());

        ArrayList<Connection> connections;
        ArrayList<Path> paths = new ArrayList<>();

        Node currentNode = _startNode;

        //todo find and fix the bug that adds null to all paths rather than just the one that needs it
        while (true) {
            connections = currentNode.getConnections();
            if (connections.size() == 0) {
                path.addToPath("null");
                paths.add(path);
                return paths;//the _end node was not down this path
            } else if (connections.size() == 1) {
                if (connections.get(0).getPolarity()) {
                    if (connections.get(0).getParentName().equals(_endNodeName)) {
                        path.addToPath(_endNodeName);
                        paths.add(path);
                        return paths;
                    } else {
                        for (Node node : _nodes) {
                            if (node.getName().equals(connections.get(0).getParentName())) {
                                path.addToPath(connections.get(0).getParentName());
                                currentNode = node;
                                break;
                            }
                        }
                    }
                }else{
                    if(connections.get(0).getParentName().equals(_endNodeName)){
                        path.addToPath("!" + _endNodeName);
                        paths.add(path);
                        return paths;
                    }else{
                        path.addToPath("null");
                        paths.add(path);
                        return paths;
                    }
                }
            } else {
                for (Connection connection : connections) {
                    if(connection.getPolarity()) {
                        Path tempPath = new Path(new ArrayList<>(), _startNode, _endNodeName);
                        for (String string : path.getNodeNames()) {
                            tempPath.addToPath(string);
                        }
                        if (connection.getParentName().equals(_endNodeName)) {
                            tempPath.addToPath(_endNodeName);
                            paths.add(tempPath);
                        } else {
                            ArrayList<Path> tempList = new ArrayList<>();
                            for (Node node : _nodes) {
                                if (node.getName().equals(connection.getParentName())) {
                                    tempList = getPaths(_nodes, node, _endNodeName);
                                    //break;
                                }
                                //System.out.println("Node was not the parent");
                            }
                            for (Path pathTemp : tempList) {
                                tempPath = new Path(new ArrayList<>(), _startNode, _endNodeName);
                                for (String string : path.getNodeNames()) {
                                    tempPath.addToPath(string);
                                }
                                for (String string : pathTemp.getNodeNames()) {
                                    tempPath.addToPath(string);
                                }
                                paths.add(tempPath);
                            }
                        }
                    }else{
                        Path tempPath = new Path(new ArrayList<>(), _startNode, _endNodeName);
                        for (String string : path.getNodeNames()) {
                            tempPath.addToPath(string);
                        }
                        if(connection.getParentName().equals(_endNodeName)){
                            tempPath.addToPath("!" + _endNodeName);
                            paths.add(tempPath);
                        }else{
                            tempPath.addToPath("null");
                            paths.add(tempPath);
                        }
                    }
                }
                return paths;
            }
        }
    }
}


