package Question2;

import java.util.ArrayList;

public class Path {
    private ArrayList<String> nodeNames;
    private Node nextNode;
    private String targetName;
    private int length = 0;

    Path(ArrayList<String> _currentPath, Node _nextNode, String _endName){
        nodeNames = _currentPath;
        nextNode = _nextNode;
        targetName = _endName;
    }

    public int getLength(){
        return length;
    }

    void addToPath(String _nodeName){
        nodeNames.add(_nodeName);
        length++;
    }

    public ArrayList<String> getNodeNames() {
        return nodeNames;
    }

    public void setNextNode(Node _nextNodeNew){
        nextNode = _nextNodeNew;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public String getTargetName() {
        return targetName;
    }
}
