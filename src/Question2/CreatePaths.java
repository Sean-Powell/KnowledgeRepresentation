package Question2;

import java.util.ArrayList;

public class CreatePaths{
    //using Path objects

    public ArrayList<Path> getPaths(ArrayList<Node> _nodes, Node _startNode, String _endNodeName){

        Path path = new Path(new ArrayList<>(), _startNode, _endNodeName);
        path.addToPath(_startNode.getName());

        ArrayList<Connection> connections;
        ArrayList<Path> paths = new ArrayList<>();

        Node currentNode = _startNode;
        while(true) {
            connections = currentNode.getConnections();
            if (connections.size() == 0) {
                return null; //the _end node was not down this path
            } else if (connections.size() == 1) {
                if(connections.get(0).getParentName().equals(_endNodeName)){
                    path.addToPath(_endNodeName);
                    paths.add(path);
                    return paths;
                }else{
                    for(Node node: _nodes){
                        if(node.getName().equals(connections.get(0).getParentName())){
                            path.addToPath(connections.get(0).getParentName());
                            currentNode = node;
                            break;
                        }
                    }
                }
            }else{
                for(Connection connection: connections){
                    Path tempPath = path;
                    if(connection.getParentName().equals(_endNodeName)){
                        tempPath.addToPath(_endNodeName);
                        paths.add(tempPath);
                    }else{
                        ArrayList<Path> tempList = new ArrayList<>();
                        for(Node node: _nodes){
                            if(node.getName().equals(connection.getParentName())){
                                tempList = getPaths(_nodes, node, _endNodeName);
                                break;
                            }
                        }

                        for(Path pathTemp: tempList){
                            for(String string: pathTemp.getNodeNames()){
                                tempPath.addToPath(string);
                            }
                            paths.add(tempPath);
                            tempPath = path;
                        }
                    }
                }
                return paths;
            }
        }
    }
}


