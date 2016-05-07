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
    RankedNodeValue value;
//    RankedNode<Key, Value> left;
//    RankedNode<Key, Value> right;
//    RankedNode<Key, Value> parent;

    public RankedNode(Key key, Value value) {
        super(key, value);
        this.key = key;
        this.value = new RankedNodeValue(value);
    }

    public void elementAdded() {
        value.size++;
    }

    public void elementRemoved() {
        value.size--;
    }

    @Override
    public String toString() {
        return "{" + key + ": " + value.actual.toString() + ", size: " + value.size +"}";
    }

    private class RankedNodeValue {
        Value actual;
        int size;
        Value getActual() {
            return this.actual;
        }
        public RankedNodeValue(Value value) {
            this.actual = value;
            this.size = 0;
        }

    }
}
