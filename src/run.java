import Question1.Clause;
import Question1.InputManager;
import Question1.Query;

import java.util.ArrayList;
import java.util.Scanner;

public class run {
    private static Scanner scanner = new Scanner(System.in);
    private static InputManager IM = new InputManager();
    private static Query Q = new Query();

    public static void main(String[] args) {
        boolean loop = true;
        int option;
        while (loop) {
            System.out.println("Enter -1 to quit.");
            System.out.println("Enter 1 to run part 1.");
            System.out.println("Enter 2 to run part 2.");
            option = scanner.nextInt();
            switch (option) {
                case -1:
                    loop = false;
                    break;
                case 1:
                    runQuestion1();
                    break;
                case 2:
                    runQuestion2();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void runQuestion1() {
        boolean loop = true;
        int choice;

        ArrayList<Clause> knowledgeBase = null;

        while (loop){
            System.out.println("Enter '1' to add to the knowledge base");
            System.out.println("Enter '2' to clear the knowledge base");
            System.out.println("Enter '3' to display the knowledge base");
            System.out.println("Enter '4' to enter a query");
            System.out.println("Enter '5' to run the query");
            System.out.println("Enter '-1' to return to the main menu");

            choice = scanner.nextInt();
            switch(choice){
                case 1:
                    knowledgeBase = IM.createKnowledgeBase();
                    break;
                case 2:
                    knowledgeBase = null;
                    IM.clearKnowledgeBase();
                    break;
                case 3:
                    if(knowledgeBase != null) {
                        for (Clause aKnowledgeBase : knowledgeBase) {
                            System.out.print("[");
                            for (int j = 0; j < aKnowledgeBase.getListOfLiterals().size(); j++) {
                                if(aKnowledgeBase.getListOfLiterals().get(j).isNegative()) {
                                    System.out.printf("!%s,", aKnowledgeBase.getListOfLiterals().get(j).getData());
                                }else{
                                    System.out.printf("%s,", aKnowledgeBase.getListOfLiterals().get(j).getData());
                                }
                            }
                            System.out.print("]\n");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Please enter the query in the negative form, there is no need for the '!' ");
                    IM.setQuery();
                    break;
                case 5:
                    if(Q.runQuery(knowledgeBase, IM.getQuery())){
                        System.out.println("SOLVED");
                    }else{
                        System.out.println("NOT SOLVED");
                    }
                    break;
                case -1:
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void runQuestion2() {

    }
}