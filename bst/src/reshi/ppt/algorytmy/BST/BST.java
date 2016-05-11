package reshi.ppt.algorytmy.BST;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Marcin Regulski on 25.04.2016.
 */
public class BST<K extends Comparable<K>, V> implements Iterable<BinaryTreeNode<K,V>> {
    protected BinaryTreeNode<K, V> root;
    protected int size;

    /**
     * Create new empty BST.
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Check if tree contains no elements.
     * @return true if the tree contains no elements.
     */
    public boolean isEmpty() {
        return root == null;
    }

    public BinaryTreeNode<K, V> getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    /**
     * Create and insert a new node into the tree.
     * @param key key of the node. This is used for all comparisons within this tree.
     * @param value value associated with the key.
     */
    public BinaryTreeNode<K, V> insert (K key, V value) {
        BSTNode<K, V> node = new BSTNode<>(key, value);
        size++;
        if(isEmpty()) {
            root = node;
            return root;
        }
        else {
            return insert(node, root);
        }
    }

    public void insert(Map<K, V> pairs) {
        pairs.forEach(this::insert);
    }

    /**
     * Insert a node into the specified subtree.
     * @param node node to be inserted
     * @param subtree root of a subtree to insert the node into.
     */
    protected BinaryTreeNode<K,V> insert(BinaryTreeNode<K, V> node, BinaryTreeNode<K, V> subtree) {
        if(node.getKey().compareTo(subtree.getKey()) > 0) {
            if(subtree.right == null) {
                subtree.right = node;
                node.parent = subtree;
            }
            else {
                insert(node, subtree.right);
            }
        }
        else if (node.getKey().compareTo(subtree.getKey()) < 0) {
            if(subtree.left == null) {
                subtree.left = node;
                node.parent = subtree;
            }
            else {
                insert(node, subtree.left);
            }
        }
        else {
            throw new IllegalArgumentException("Tree cannot contain duplicate keys");
        }
        return node;
    }

    /**
     * Remove a specific node from the tree.
     * @param node node to be removed
     * @return the deleted node
     */
    public BinaryTreeNode<K, V> delete(BinaryTreeNode<K, V> node) {
        if(node == null) {
            return null;
        }
        // y - node that is actually deleted
        // same as node, unless node has 2 children - then node's successor is removed
        // and its data overwrites node's

        BinaryTreeNode<K, V> x, y;
        y = (node.left == null || node.right == null) ? node : successor(node);
        x = (y.left != null) ? y.left : y.right;
        if (x != null) {
            x.parent = y.parent;
        }
        if(y.parent == null) {  //
            root = x;
        }
        else if (y == y.parent.left) {
            y.parent.left = x;
        }
        else {
            y.parent.right = x;
        }
        if (y != node) {
            node.setKey(y.getKey());
            node.setValue(y.getValue());
        }
        this.size--;
        return y;
    }

    /**
     * Find element in the tree by key.
     * @param key key to look for
     * @return node with the specified key, null if such node cannot be found.
     */
    public BinaryTreeNode<K, V> search(K key) {
        return searchInSubtree(key, this.root);
    }

    /**
     * Find element by key under a specified node.
     * @param key key to find
     * @param subtree where to look
     * @return node matching the key or null if such node cannot be found
     */
    public BinaryTreeNode<K, V> searchInSubtree(K key, BinaryTreeNode<K, V> subtree) {
        if(this.isEmpty()) {
            return null;
        }

        if (subtree == null || subtree.getKey().equals(key)) {
            return subtree;
        }
        if(subtree.getKey().compareTo(key) < 0) {
            return searchInSubtree(key, subtree.right);
        }
        else {
            return searchInSubtree(key, subtree.left);
        }
    }

    /**
     * @return - minimal node of this tree.
     */
    public BinaryTreeNode<K, V> minimum() {
        return !isEmpty() ? minimum(root) : null;
    }

    /**
     * Find minimal element in a subtree.
     * @param node root of a subtree to searched through.
     * @return minimal element of a subtree rooted in node.
     */
    public BinaryTreeNode<K, V> minimum(BinaryTreeNode<K, V> node) {
        BinaryTreeNode<K, V> current = node;
        while(current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * @return - maximal node of this tree.
     */
    public BinaryTreeNode<K, V> maximum() {
        return !isEmpty() ? maximum(this.root) : null;
    }

    /**
     * Find maximal node in a subtree.
     * @param node root of a subtree to search through.
     * @return maximal element of a subtree rooted in node.
     */
    public BinaryTreeNode<K, V> maximum(BinaryTreeNode<K, V> node) {
        BinaryTreeNode<K, V> current = node;
        while(current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * Return first element in the tree that is greater than the specified node.
     * @param node node whose successor is to be found
     * @return node's successor or null if node is the maximal element of the tree.
     */
    public BinaryTreeNode<K,V> successor(BinaryTreeNode<K, V> node) {
        if(node.right != null) {
            return minimum(node.right);
        }
        BinaryTreeNode<K, V> cur = node.parent;
        while(cur != null && node == cur.right) {
            node = cur;
            cur = cur.parent;
        }
        return cur;
    }

    /**
     *
     * @param node starting element
     * @return node's predecessor or null if node is the minimal element of the tree.
     */
    public BinaryTreeNode<K, V> predecessor(BinaryTreeNode<K, V> node) {
        if(node.left != null) {
            return maximum(node.left);
        }
        BinaryTreeNode<K, V> cur = node.parent;
        while(cur != null && node == cur.left) {
            node = cur;
            cur = cur.parent;
        }
        return cur;
    }

    /**
     * Perform pre-order traversal of the whole tree, and apply a function at each node.
     * @param function function to call on each node.
     */
    public void preOrder(Consumer<BinaryTreeNode<K, V>> function) {
        preOrder(function, root);
    }

    /**
     * Perform pre-order traversal of a subtree rooted in the specified node, and call a function at each node.
     * @param function - function to call on each node
     * @param node - root of a subtree to traverse
     */
    private void preOrder(Consumer<BinaryTreeNode<K, V>> function, BinaryTreeNode<K, V> node) {
        if(node != null) {
            function.accept(node);
            preOrder(function, node.left);
            preOrder(function, node.right);
        }
    }

    public void inOrder(Consumer<BinaryTreeNode<K, V>> function) {
        inOrder(function, root);
    }

    protected void inOrder(Consumer<BinaryTreeNode<K, V>> function, BinaryTreeNode<K, V> node) {
        if(node != null) {
            inOrder(function, node.left);
            function.accept(node);
            inOrder(function, node.right);
        }
    }

    /**
     * Perform post-order traversal of the whole tree, and apply a function at each node.
     * @param function function to call on each node.
     */
    public void postOrder(Consumer<BinaryTreeNode<K, V>> function) {
        postOrder(function, root);
    }

    public void postOrder(Consumer<BinaryTreeNode<K, V>> function, BinaryTreeNode<K, V> node) {
        if(node != null) {
            postOrder(function, node.right);
            postOrder(function, node.left);
            function.accept(node);
        }
    }

    /**
     * @return list of tree nodes, sorted by keys
     */
    public List<BinaryTreeNode<K,V>> toList() {
        List<BinaryTreeNode<K, V>> list = new ArrayList<>(size);
        for(BinaryTreeNode<K, V> node : this) {
            list.add(node);
        }
        return list;

    }

    /**
     * Print a nicely formatted tree.
     */
    public void prettyPrint() {
        if(!isEmpty()) {
            root.prettyPrint();
        }
    }



    /**
     * Default iterator, used by {@link Iterable} interface.
     * @return an iterator that performs in-order traversal of the tree.
     */
    @Override
    public Iterator<BinaryTreeNode<K, V>> iterator() {
        return new Iterator<BinaryTreeNode<K, V>>() {
            private BinaryTreeNode<K, V> currentNode;
            private int index = 0;  // to avoid looking for successor in each hasNext() call
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public BinaryTreeNode<K, V> next() {
                index++;
                if(currentNode == null) {
                    currentNode = minimum();
                }
                else {
                    currentNode = successor(currentNode);
                }
                return currentNode;
            }
        };
    }

}
