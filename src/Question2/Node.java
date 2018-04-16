package Question2;

import java.util.ArrayList;

public class Node {
    private ArrayList<Connection> connections = new ArrayList<>();
    private String name;

    public Node(String _name){
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void addConnection(Connection _connection){
        connections.add(_connection);
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }
}
