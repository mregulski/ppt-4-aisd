package reshi.ppt.algorytmy.augmented;

import reshi.ppt.algorytmy.BST.BSTNode;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class RankedNode<Key extends Comparable<Key>, Value> extends BSTNode<Key, Value> {
    /**
     * Size of subtree rooted in this node.
     * Satisfies x.size == x.left.size + x.right.size + 1
     * [null].size = 0.
     */
    public int size;

    public RankedNode(Key key, Value value) {
        super(key, value);
        size = 1;
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
