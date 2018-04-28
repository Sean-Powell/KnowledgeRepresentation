package Question2;

public class Connection {
    private String parentName;
    private String childName;
    private boolean polarity;

    public Connection(String _parentName, String _childName, boolean _polarity){
        parentName = _parentName;
        childName = _childName;
        polarity = _polarity;
    }

    String getParentName() {
        return parentName;
    }

    String getChildName() {
        return childName;
    }

    boolean getPolarity(){
        return polarity;
    }
}