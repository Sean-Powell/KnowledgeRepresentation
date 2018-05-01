import Question1.*;
import Question2.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class run {
    private static Scanner scanner = new Scanner(System.in);
    private static InputManager IM = new InputManager();
    private static Query Q = new Query();
    private static CreatePaths CP = new CreatePaths();

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
                    if(IM.getQuery() != null) {
                        if (Q.runQuery(knowledgeBase, IM.getQuery())) {
                            System.out.println("SOLVED");
                        } else {
                            System.out.println("NOT SOLVED");
                        }
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
        boolean startNodeFound = false;

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
                    System.out.println("Input the start and end points in the Start IS-A/IS-NOT-A End format please");
                    input = getStringInput();
                    boolean isNot = false;
                    if(input != null && (input.contains(" IS-A ") || input.contains(" IS-NOT-A "))){
                        if(input.contains(" IS-NOT-A ")){
                            splitInput = input.split(" IS-NOT-A ");
                            isNot = true;
                        }else{
                            splitInput = input.split(" IS-A ");
                        }
                        for(Node node: nodes){
                            if(!startNodeFound && node.getName().equals(splitInput[0])) {
                                startNodeFound = true;
                                startNode = node;
                            }
                        }
                        endNodeName = splitInput[1];
                    }

                    if(startNodeFound){
                        //Run program
                        ArrayList<Path> paths = CP.getPaths(nodes, startNode, endNodeName);
                        if(isNot && paths == null){
                            System.out.println(startNode.getName() + " is not a child of " + endNodeName);
                        }else {
                            if(paths.size() > 0){
                                for (int i = 0; i < paths.size(); i++) {
                                    for (String string : paths.get(i).getNodeNames()) {
                                        if (string.equals("null")) {
                                            System.out.println("Removing path");
                                            paths.remove(i);
                                        }
                                    }
                                }
                                System.out.println("-----------------------");
                                int lowestDistance = Integer.MAX_VALUE;
                                ArrayList<Path> shortestPath = new ArrayList<>();
                                System.out.println("Paths:");
                                for (Path path : paths) {
                                    if (path.getLength() < lowestDistance) {
                                        System.out.println(path.getNodeNames().toString());
                                        lowestDistance = path.getLength();
                                        shortestPath.clear();
                                        shortestPath.add(path);
                                    } else if (path.getLength() == lowestDistance) {
                                        shortestPath.add(path);
                                    }
                                }
                                System.out.println("-----------------------");
                                paths = CP.checkIfRedundant(paths);
                                paths = CP.checkForPreemption(paths);
                                System.out.println("-----------------------");
                                System.out.println("Shortest path: ");
                                for(Path path: shortestPath){
                                    System.out.println(path.getNodeNames().toString());
                                }
                                System.out.println("Inferential Distance:");
                                for (Path path : paths) {
                                    System.out.println(path.getNodeNames().toString());
                                }
                                System.out.println("-----------------------");
                            }else {
                                System.out.println("There is no valid path from " + startNode.getName() + " to " + endNodeName);
                            }
                        }

                    }else{
                        System.out.println("The start does not exist in the network");
                    }
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
}