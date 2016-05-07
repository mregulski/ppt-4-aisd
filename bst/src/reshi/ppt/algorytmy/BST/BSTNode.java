package reshi.ppt.algorytmy.BST;

/**
 * @author Marcin Regulski on 25.04.2016.
 */
@SuppressWarnings("WeakerAccess")
public class BSTNode<Key extends Comparable<Key>, Value> {
    protected Key key;
    protected Value value;

    public BSTNode<Key, Value> parent;
    public BSTNode<Key, Value> left;
    public BSTNode<Key, Value> right;


    public Key getKey() {
        return key;
    }
    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    void setKey(Key key) {
        this.key = key;
    }
    protected BSTNode() {}
    public BSTNode(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + key + ": " + value.toString() + "}";
    }

    /**
     * Pretty print this node and all it's descendants.
     * based on http://stackoverflow.com/a/8948691
     */
    public void prettyPrint() {
        prettyPrint("", true);
    }
    private void prettyPrint(String prefix, boolean isTail) {

        System.out.println(prefix + (isTail ? "└──" : "├──") + this.toString());
        if(this.left != null) {
             this.left.prettyPrint(prefix + (isTail ? "    " : "|    "), false);
        }
        if(this.right != null) {
            this.right.prettyPrint(prefix + (isTail ? "    " : "|    "), true);
        }
    }
}
