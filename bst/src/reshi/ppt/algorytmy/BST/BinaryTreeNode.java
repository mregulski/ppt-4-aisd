package reshi.ppt.algorytmy.BST;

/**
 * @author Marcin Regulski on 07.05.2016.
 */
public abstract class BinaryTreeNode<K extends Comparable<K>, V> {
    protected K key;
    protected V value;
    protected BinaryTreeNode<K, V> parent;
    protected BinaryTreeNode<K, V> left;
    protected BinaryTreeNode<K, V> right;

    protected BinaryTreeNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public BinaryTreeNode<?, ?> getParent() {
        return parent;
    }

    public void setParent(BinaryTreeNode<K, V> parent) {
        this.parent = parent;
    }

    public BinaryTreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<K, V> left) {
        this.left = left;
    }

    public BinaryTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<K, V> right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryTreeNode<?, ?> that = (BinaryTreeNode<?, ?>) o;

        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;
        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        return right != null ? right.equals(that.right) : that.right == null;

    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (left != null ? left.hashCode() : 0);
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
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
