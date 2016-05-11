package reshi.ppt.algorytmy.BST.ranked;

import reshi.ppt.algorytmy.BST.BinaryTreeNode;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class RankedNode<Key extends Comparable<Key>, Value> extends BinaryTreeNode<Key, Value> {
    /**
     * Size of subtree rooted in this node.
     * Satisfies x.size == x.left.size + x.right.size + 1
     * [null].size = 0.
     */
    private int size;

    public int getSize() {
        return size;
    }

    public RankedNode(Key key, Value value) {
        super(key, value);
        size = 0;
    }

    public void elementAdded() {
        RankedNode tmp = this;
        while(tmp!=null) {
            tmp.size++;
            tmp = tmp.getParent();
        }
    }

    public void elementRemoved() {
        RankedNode tmp = this;
        while(tmp != null) {
            tmp.size--;
            tmp = tmp.getParent();
        }
    }


    public RankedNode<Key, Value> getLeft() {
        return (RankedNode<Key, Value>) left;
    }
    public RankedNode<Key, Value> getRight() {
        return (RankedNode<Key, Value>) right;
    }
    public RankedNode<Key, Value> getParent() {
        return (RankedNode<Key, Value>) parent;
    }

    @Override
    public String toString() {
        return "{" + key + ": " + value.toString() + ", size: " + size +"}";
    }

}
