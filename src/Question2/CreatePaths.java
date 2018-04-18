package Question2;

import java.util.ArrayList;

class CreatePaths{

    //start at first node
    //go along the path until a intersection is reached. First go along the top path, then return and go down the lower path
    //continue until all connections from the node have been covered.
    //recursion is gonna have to be used.
    ArrayList<String> getPaths(ArrayList<Node> _nodes, ArrayList<String> _currentPaths, Node _startNode,
                               String _endNodeName){

        ArrayList<Connection> connections = _startNode.getConnections();
        Node nextNode;

        if(connections.size() == 1){
            for(int i = 0; i < _currentPaths.size(); i++){
                String newPath = _currentPaths.get(i) + ", " + connections.get(0).getChildName();
                _currentPaths.set(i, newPath);
                if(!connections.get(0).getChildName().equals(_endNodeName)){
                    //end of path
                    return _currentPaths;
                }else{
                    for(Node node: _nodes){
                        if(node.getName().equals(connections.get(0).getChildName())){
                            nextNode = node;
                            getPaths(_nodes, _currentPaths, nextNode, _endNodeName);
                        }
                    }
                }
            }
        }else{
            for(Connection connection: connections){
                //if there is two paths in the list already and there are two connections at the end of this there are
                //going to have to be 4 paths. This is going to have to recursively find each of the new paths.
            }
        }
    }
}