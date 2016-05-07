package reshi.ppt.algorytmy.augmented;

import reshi.ppt.algorytmy.BST.BST;
import reshi.ppt.algorytmy.BST.BSTNode;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class RankedBST<K extends Comparable<K>, V> extends BST<K, V> {

//    protected RankedNode<K, V> root;

    @Override
    public BSTNode<K, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    public RankedBST() {
        root = null;
        size = 0;
    }

    public void elementAdded(RankedNode<K, V> node) {
        node.elementAdded();
    }

    public void elementRemoved(RankedNode<K, V> node) {
        node.elementRemoved();
    }

    @Override
    public void insert(K key, V value) {
        RankedNode<K, V> node = new RankedNode<>(key, value);
        size++;
        if(isEmpty()) {
            root = node;
        }
        else {
            insert(node, root);              // insert node normally
            node.getParent().elementAdded(); // notify it's parent about the update
        }
    }

    @Override
    public BSTNode<K, V> delete(BSTNode<K, V> targetNode) {
        RankedNode removed = (RankedNode<K,V>)super.delete(targetNode);
        removed.elementRemoved();
        return removed;
//        RankedNode node = (RankedNode<K,V>)targetNode;
//        if(node == null) {
//            return null;
//        }
//        RankedNode<K, V> x, y;
//        y = (node.getLeft() == null || node.getRight() == null)
//            ? node
//            : (RankedNode<K,V>)successor(node);
//        x = (y.getLeft() != null)
//            ? y.getLeft()
//            : y.getRight();
//
//        if (x != null) {    // node has at least 1 child
//            x.setParent(y.getParent());
//        }
//        if(y.getParent() == null) {  // node is root
//            root = x;
//        }
//        else if (y == y.getParent().getLeft()) {
//            y.getParent().setLeft(x);
//            y.elementRemoved(); // seems to handle all cases
//        }
//        else {
//            y.getParent().setRight(x);
//            y.elementRemoved(); // seems to handle all cases
//        }
//        if (y != node) {
//            node.setKey(y.getKey());
//            node.setValue(y.getValue());
//        }
//        this.size--;
//        return null;
    }
    public RankedNode<K, V> select(int i) {
        if(i < size && i > 0) {
            return select(i, (RankedNode<K, V>) root);
        }
        return null;
    }

    private RankedNode<K, V> select(int i, RankedNode<K,V> subtree) {
        int r = 1;
        if(subtree.getLeft() != null) {
            r += subtree.getLeft().size;
        }
        if(i == r) {
            return subtree;
        }
        if(i < r) {
            return select(i, subtree.getLeft());
        }
        return select(i-r, subtree.getRight());
    }

    public int rank(RankedNode<K,V> node) {
        int r = node.getLeft() != null
                ? node.getLeft().size + 1
                : 1;
        RankedNode tmp = node;
        while(tmp != root) {
            if(tmp == tmp.getParent().getRight()) {
                r += tmp.getParent().getLeft() != null
                        ? tmp.getParent().getLeft().size + 1
                        : 1;
            }
            tmp = tmp.getParent();
        }
        return r;
    }

}
