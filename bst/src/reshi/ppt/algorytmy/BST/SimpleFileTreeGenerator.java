package reshi.ppt.algorytmy.BST;

import java.io.*;

/**
 * @author Marcin Regulski on 28.04.2016.
 */
@SuppressWarnings("WeakerAccess")
public class SimpleFileTreeGenerator {
    private BufferedReader source;
    private BST<Integer, Integer> tree;

    public BST<Integer, Integer> getTree() {
        return this.tree;
    }

    /**
     * Initialize generator with an input file.
     * @param filename - text file containing instructions
     * @throws IOException
     */
    public SimpleFileTreeGenerator(String filename) throws IOException {
        FileReader f = new FileReader(filename);
        source = new BufferedReader(f);
    }

    /**
     * Parse and execute commands from source file.
     */
    public void generate() {
        tree = new BST<>();
        String line;
        String[] instruction;
        String operation, argument;
        try {
            line = source.readLine();
            while(line != null) {
                instruction = line.split(" ");
                if(instruction.length == 2) {
                    operation = instruction[0];
                    argument = instruction[1];
                    execute(operation, argument);
                }
                else if (instruction.length == 1) {
                    operation = instruction[0];
                    execute(operation);
                }
                else {
                    System.out.println("Unknown instruction: " + String.join(", ", (CharSequence[]) instruction));
                }
                line = source.readLine();

            }
        }
        catch(IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }


    }

    /**
     * Execute a single unary operation
     * @param operation operation to execute
     * @param argument parameter for operation (key)
     */
    private void execute(String operation, String argument) {
        int key;
        try {
            key = Integer.parseInt(argument);
        }
        catch (NumberFormatException ex) {
            System.out.println(String.format("Not an integer: '%s'", argument));
            return;
        }
        switch(operation) {
            case "insert":
                tree.insert(key, key);
                break;
            case "delete":
                tree.delete(tree.search(key));
                break;
            case "find":
                System.out.println(String.format("key %d is %spresent", key, tree.search(key) == null ? "not " : ""));
                break;
            default:
                System.out.println(String.format("Unknown operation: '%s'", operation));
        }
    }

    /**
     * Execute a single operation without arguments
     * @param operation operation to execute.
     */
    private void execute(String operation) {
        BinaryTreeNode<Integer, Integer> tmp;
        switch(operation) {
            case "min":
                tmp = tree.minimum();
                System.out.println("min: " + (tmp != null ? tmp.getValue() : ""));
                break;
            case "max":
                tmp = tree.maximum();
                System.out.println("max: " + (tmp != null ? tmp.getValue() : ""));
                break;
            case "inorder":
                System.out.print("in-order: ");
                tree.inOrder((x) -> System.out.print(x.getValue()+ " "));
                System.out.println();
                break;
            case "preorder":
                System.out.print("pre-order: ");
                tree.preOrder((x) -> System.out.print(x.getValue()+ " "));
                System.out.println();
                break;
            case "postorder":
                System.out.print("post-order: ");
                tree.postOrder((x) -> System.out.print(x.getValue()+ " "));
                System.out.println();
                break;
            case "pretty":
                tree.prettyPrint();
                break;
            default:
                System.out.println(String.format("Unknown operation: '%s'", operation));
        }
    }
}
