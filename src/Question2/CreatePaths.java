package Question2;
import java.util.ArrayList;

public class CreatePaths {
    //using Path objects

    public ArrayList<Path> getPaths(ArrayList<Node> _nodes, Node _startNode, String _endNodeName) {
        Path path = new Path(new ArrayList<>(), _startNode, _endNodeName);
        path.addToPath(_startNode.getName());

        ArrayList<Connection> connections;
        ArrayList<Path> paths = new ArrayList<>();

        Node currentNode = _startNode;
        boolean currentNodeChanged;
        while (true) {
            connections = currentNode.getConnections();
            currentNodeChanged = false;
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
                                currentNodeChanged = true;
                                break;
                            }
                        }
                        if(!currentNodeChanged){
                            return null;
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

    public ArrayList<Path> checkIfRedundant(ArrayList<Path> _listOfPaths){
        for(int i = 0; i < _listOfPaths.size(); i++){
            ArrayList<String> nodeName = _listOfPaths.get(i).getNodeNames();
            for(int j = 0; j < nodeName.size() - 1; j++){
                String childName = nodeName.get(j);
                String parentName = nodeName.get(j + 1);
                for(Path path1: _listOfPaths){
                    if(path1 != _listOfPaths.get(i)){
                        int childNameIndex = -1;
                        int parentNameIndex = -1;
                        ArrayList<String> nodeNamesPath2 = path1.getNodeNames();
                        for(int k = 0; k < nodeNamesPath2.size(); k++){
                            if(nodeNamesPath2.get(k).equals(childName)){
                                childNameIndex = k;
                            }else if(nodeNamesPath2.get(k).equals(parentName)){
                                parentNameIndex = k;
                            }
                        }

                        if(parentNameIndex - childNameIndex > 1 && childNameIndex != -1 && parentNameIndex != -1){
                            //path is redundant
                            System.out.println("Deleting path as redundant: " + _listOfPaths.get(i).getNodeNames().toString());
                            _listOfPaths.remove(i);
                            j = nodeName.size();
                            break;
                        }
                    }
                }
            }
        }

        return _listOfPaths;
    }

    public ArrayList<Path> checkForPreemption(ArrayList<Path> _listOfPaths){
        for (int i = 0; i < _listOfPaths.size(); i++) {
            ArrayList<String> nodeNames = _listOfPaths.get(i).getNodeNames();
            String beforeEndName = nodeNames.get(nodeNames.size() - 2);
            String endNodeName = nodeNames.get(nodeNames.size() - 1);

            for(int j = 0; j < _listOfPaths.size(); j++){
                if(j != i){
                    int beforeEndNameIndex = -1;
                    for(int k = 0; k < _listOfPaths.get(j).getNodeNames().size(); k++){
                        if(beforeEndName.equals(_listOfPaths.get(j).getNodeNames().get(k))){
                            beforeEndNameIndex = k;
                            k = _listOfPaths.get(j).getNodeNames().size() + 1;
                        }
                    }

                    if(beforeEndNameIndex != -1){
                        if(_listOfPaths.get(i).getNodeNames().size() - beforeEndNameIndex > 1){
                            //check if polarity is different.
                            String subPathEndNodeName = _listOfPaths.get(j).getNodeNames().get(_listOfPaths.get(j).getNodeNames().size() - 1);
                            if(!endNodeName.equals(subPathEndNodeName)){
                                //path is preempted
                                System.out.println("Deleting path as preempted: " + _listOfPaths.get(j).getNodeNames().toString());
                                _listOfPaths.remove(j);
                                j = _listOfPaths.size();
                            }
                        }
                    }
                }
            }
        }

        return _listOfPaths;
    }
}