package reshi.ppt.algorytmy.augmented;

import reshi.ppt.algorytmy.BST.BST;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class RankedBST<K extends Comparable<K>, V> extends BST<K, V> {

    private RankedNode<K, V> root;

    @Override
    public RankedNode<K, V> getRoot() {
        return this.root;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    public RankedBST() {
        this.size = 0;
        this.root = null;
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
            this.root = node;
        }
        else {
            this.insert(node, this.root);
        }
    }

    private void insert(RankedNode<K, V> node, RankedNode<K, V> subtree) {
        super.insert(node, subtree, (x)->elementAdded((RankedNode<K, V>)x));
    }
}
