package Question1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class InputManager {
    // for the String input
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<Clause> knowledgeBase = new ArrayList<>();
    private Literal query;

    public ArrayList<Clause> createKnowledgeBase() {
        boolean loop = true;
        String input;
        Clause clause;
        System.out.println("Enter the literals in the clause separated by ',' use '!' at the start of the literal" +
                "for negation. The literal should be enclosed in []");
        while (loop) {
            System.out.println("Enter -1 to stop inputting");
            input = getInputString();
            if (null == input) {
                System.out.println("An error occurred during the input please input it again");
            } else {
                try {
                    if (Integer.parseInt(input) == -1) {
                        loop = false;
                    }
                } catch (Exception e) {
                    //ignored
                }
                if(loop){
                    clause = new Clause(input);
                    knowledgeBase.add(clause);
                }

            }
        }
        return knowledgeBase;
    }

    public void clearKnowledgeBase(){
        knowledgeBase = null;
        knowledgeBase = new ArrayList<>();
    }

    public void setQuery(){
        query = null;
        query = new Literal(getInputString(), true);
    }

    public Literal getQuery() {
        return query;
    }

    private String getInputString() {
        try {
            return br.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
}