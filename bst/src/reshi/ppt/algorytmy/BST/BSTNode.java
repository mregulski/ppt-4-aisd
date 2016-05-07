package reshi.ppt.algorytmy.BST;

/**
 * @author Marcin Regulski on 25.04.2016.
 */
@SuppressWarnings("WeakerAccess")
public class BSTNode<Key extends Comparable<Key>, Value> {
    protected Key key;
    protected Value value;

    protected BSTNode<Key, Value> parent;
    protected BSTNode<Key, Value> left;
    protected BSTNode<Key, Value> right;

    public BSTNode(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public BSTNode<Key, Value> getParent() {
        return parent;
    }

    public BSTNode<Key, Value> getLeft() {
        return left;
    }

    public BSTNode<Key, Value> getRight() {
        return right;
    }
    public Key getKey() {
        return key;
    }
    public Value getValue() {
        return value;
    }

    public void setParent(BSTNode<Key, Value> parent) {
        this.parent = parent;
    }

    public void setLeft(BSTNode<Key, Value> left) {
        this.left = left;
    }

    public void setRight(BSTNode<Key, Value> right) {
        this.right = right;
    }

    public void setValue(Value value) {
        this.value = value;
    }
    public void setKey(Key key) {
        this.key = key;
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
