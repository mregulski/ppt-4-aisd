package reshi.ppt.algorytmy.BST.ranked;

import reshi.ppt.algorytmy.BST.BST;
import reshi.ppt.algorytmy.BST.BinaryTreeNode;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class RankedBST<K extends Comparable<K>, V> extends BST<K, V> {

//    protected RankedNode<K, V> root;

    @Override
    public BinaryTreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    public RankedBST() {
        super();
        size = 0;
    }

    @Override
    public BinaryTreeNode<K,V> insert(K key, V value) {
        RankedNode<K, V> node = new RankedNode<>(key, value);
        size++;
        if(isEmpty()) {
            root = node;
            node.elementAdded();
            return root;
        }
        else {
            insert(node, root);              // insert node normally
            node.elementAdded(); // notify it's parent about the update
            return node;
        }
    }

    @Override
    public BinaryTreeNode<K, V> delete(BinaryTreeNode<K, V> targetNode) {
        RankedNode removed = (RankedNode<K,V>) super.delete(targetNode);
        removed.elementRemoved();
        return removed;
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
            r += subtree.getLeft().getSize();
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
                ? node.getLeft().getSize() + 1
                : 1;
        RankedNode tmp = node;
        while(tmp != root) {
            if(tmp == tmp.getParent().getRight()) {
                r += tmp.getParent().getLeft() != null
                        ? tmp.getParent().getLeft().getSize() + 1
                        : 1;
            }
            tmp = tmp.getParent();
        }
        return r;
    }

}
