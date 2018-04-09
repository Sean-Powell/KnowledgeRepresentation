package Question1;

public class Literal {
    private String data;
    private boolean negative;

    Literal(String _data, boolean _negative){
        data = _data;
        negative = _negative;
    }

    public String getData() {
        return data;
    }

    public boolean isNegative() {
        return negative;
    }
}
