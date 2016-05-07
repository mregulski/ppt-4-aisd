package reshi.ppt.algorytmy.BST;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        SimpleFileTreeGenerator gen;
        System.out.println(System.getProperty("user.dir"));
        try {
            gen = new SimpleFileTreeGenerator("bst\\input.txt");
            gen.generate();
        }
        catch(IOException ioe) {
            System.out.println("error opening input.txt");
        }
    }

    static void log(String m) {
        System.out.println(m);
    }
}
