package Question1;

import java.util.ArrayList;

public class Clause {
    private ArrayList<Literal> listOfLiterals = new ArrayList<>();
    private int lengthOfLiterals;

    Clause(String _data){
        _data = _data.substring(1, _data.length() - 1);
        String literals[] = _data.split(",");
        lengthOfLiterals = literals.length;
        for (String literal : literals) {
            Literal lit;
            if (literal.contains("!")) {
                lit = new Literal(literal.substring(1), true);
            } else {
                lit = new Literal(literal, false);
            }
            listOfLiterals.add(lit);
        }
    }

    public ArrayList<Literal> getListOfLiterals() {
        return listOfLiterals;
    }

    int getLengthOfLiterals() {
        return lengthOfLiterals;
    }
}
