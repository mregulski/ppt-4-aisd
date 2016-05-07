package reshi.ppt.algorytmy.BST;

/**
 * @author Marcin Regulski on 25.04.2016.
 */
public class EmptyTreeException extends Exception{
    public EmptyTreeException() {
        super("Tree has no elements");
    }
}
