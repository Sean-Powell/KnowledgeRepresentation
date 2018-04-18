import Question1.Clause;
import Question1.InputManager;
import Question1.Query;
import Question2.Connection;
import Question2.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class run {
    private static Scanner scanner = new Scanner(System.in);
    private static InputManager IM = new InputManager();
    private static Query Q = new Query();

    public static void main(String[] args) {
        boolean loop = true;
        int option;
        while (loop) {
            try {
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
            }catch(Exception e){
                scanner = null;
                scanner = new Scanner(System.in);
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

            choice = getInt();

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

    private static ArrayList<Node> nodes = new ArrayList<>();
    private static void runQuestion2() throws InputMismatchException {
        int choice;
        boolean loop = true;
        boolean polarity;
        boolean connectionFound = false;
        boolean startName = false;

        String input;
        String splitInput[];
        String endNodeName = null;

        Node startNode = null;

        while (loop){
            System.out.println("Enter 1 to add information to the network");
            System.out.println("Enter 2 to enter the start and end points");
            System.out.println("Enter -1 to quit");

            choice = getInt();

            switch (choice){
                case 1:
                    while(true) {
                        System.out.println("Enter -1 to stop inputting");
                        System.out.println("Input the info in the Child IS-A or IS-NOT-A Parent format");
                        input = getStringInput();
                        if (input != null && input.equals("-1")) {
                            break;
                        } else if (input != null && input.contains("IS-A")) {
                            splitInput = input.split(" IS-A ");
                            polarity = true;
                        } else if (input != null && input.contains("IS-NOT-A")) {
                            splitInput = input.split(" IS-NOT-A ");
                            polarity = false;
                        } else {
                            System.out.println("Invalid input");
                            break;
                        }
                        Connection connection = new Connection(splitInput[1], splitInput[0], polarity);
                        for (Node node : nodes) {
                            if (node.getName().equals(splitInput[0])) {
                                node.addConnection(connection);
                                connectionFound = true;
                            }
                        }

                        if (!connectionFound) {
                            Node node = new Node(splitInput[0]);
                            node.addConnection(connection);
                            nodes.add(node);
                        }
                        connectionFound = false;
                    }
                    break;
                case 2:
                    System.out.println("Input the start and end point in the Start TO End format please");
                    input = getStringInput();
                    if(input != null && input.contains("TO")){
                        splitInput = input.split(" TO ");
                        for(Node node: nodes){
                            if(!startName && node.getName().equals(splitInput[0])) {
                                startName = true;
                                startNode = node;
                            }
                        }
                        endNodeName = splitInput[1];
                    }
                    boolean endNodeExists = false;
                    for(int i = 0; i < nodes.size(); i++){
                        if(nodes.get(i).getName().equals(endNodeName) && !endNodeExists){
                            endNodeExists = true;
                        }
                    }

                    if(!endNodeExists){
                        Node node = new Node(endNodeName);
                        nodes.add(node);
                    }
                    if(startName){
                        //Run program
                        System.out.println(findPaths("", startNode, endNodeName));
                    }else{
                        System.out.println("The start or end node does not exist in the network");
                    }
                    break;
                case 3:
                    //build knowledge network.
                    break;
                case -1:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static String getStringInput(){
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        } catch (Exception e) {
            System.out.println("An error occurred during the input of a string");
            return null;
        }
    }


    private static int getInt(){
        int input;
        try {
            input = scanner.nextInt();
        }catch (InputMismatchException ignored){
            scanner = null;
            scanner = new Scanner(System.in);
            input = -2;
        }
        return input;
    }

    private static ArrayList<String> solutions = new ArrayList<>();
    //TODO create node for end nodes that have no children.
    private static String findPaths(String _currentPath, Node _nextNode, String _endNodeName){
        //TODO debug why this never completes the path
        String newPath = _currentPath + ", " + _nextNode.getName();
        if(_nextNode.getName().equals(_endNodeName)){
            return newPath;
        }else{
            for(Connection connection : _nextNode.getConnections()){
                if(connection.getChildName().equals(_endNodeName)){
                    //found a solution
                    newPath += _currentPath + ",";
                    solutions.add(newPath);
                    return newPath;//Integer.toString(solutions.size());
                }else{
                    for(Node node: nodes){
                        if(node.getName().equals(connection.getParentName())){
                            if(connection.getPolarity()){
                                newPath += _currentPath + node.getName() + ",";
                                String result = findPaths(newPath, node, _endNodeName);
                                if(!result.equals("-1")){
                                    solutions.add(result);
                                    return Integer.toString(solutions.size());
                                }
                            }else{
                                return "-1";
                            }
                        }
                    }
                //for(Node node: nodes){
                //    if(node.getName().equals(connection.getParentName())){
                //        if(connection.getPolarity()){
                //           return findPaths(newPath, node, _endNodeName);
                //        }else{
                //            if(connection.getChildName().equals(_endNodeName)){
                //                newPath = _currentPath + ", " + _endNodeName;
                //                return newPath;
                //            }else{
                //                return "-1";
                //            }
                //       }
                //    }
                }
            }
            return "-1";
        }
    }
}