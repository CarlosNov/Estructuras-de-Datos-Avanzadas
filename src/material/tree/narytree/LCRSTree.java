package material.tree.narytree;

import material.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A linked class for a tree where nodes have an arbitrary number of children.
 *
 * @param <E> the elements stored in the tree
 * @author Raul Cabido, Abraham Duarte, Jose Velez, Jesús Sánchez-Oro
 */
public class LCRSTree<E> implements NAryTree<E>
{
    //TODO: Practica 2 Ejercicio 2

    /**
     * Inner class which represents a node of the tree
     *
     * @param <T> the type of the elements stored in a node
     */
    private class LCRSNode<T> implements Position<T>
    {
        private T element; // The element stored in the position
        private LCRSNode<T> parent; // The parent of the node
        private List<LCRSNode<T>> children; // The children of the node
        private LCRSNode<T> sibling; // The parent of the node
        private LCRSTree<T> myTree; // A reference to the tree where the node belongs

        /**
         * Constructor of the class
         *
         * @param t the tree where the node is stored
         * @param e the element to store in the node
         * @param p the parent of the node
         * @param s the next sibling of the node
         * @param c the list of children of the node
         */
        public LCRSNode(LCRSTree<T> t, T e, LCRSNode<T> p, LCRSNode<T> s, List<LCRSNode<T>> c) {
            this.element = e;
            this.parent = p;
            this.sibling = s;
            this.children = c;
            this.myTree = t;
        }

        @Override
        public T getElement() {
            return element;
        }

        /**
         * Sets the element stored at this position
         *
         * @param o the element to store in the node
         */
        public final void setElement(T o) {
            element = o;
        }

        /**
         * Accesses to the list of children of this node
         *
         * @return the list of children
         */
        public List<LCRSNode<T>> getChildren() {
            return children;
        }

        /**
         * Sets the children of this node
         *
         * @param c the list of nodes to be used as children of this position
         */
        public final void setChildren(List<LCRSNode<T>> c) {
            children = c;
        }

        /**
         * Accesses to the parent of this node
         *
         * @return the parent of this node
         */
        public LCRSNode<T> getParent() {
            return parent;
        }

        /**
         * Sets the parent of this node
         *
         * @param v the node to be used as parent
         */
        public final void setParent(LCRSNode<T> v) {
            parent = v;
        }

        /**
         * Accesses to the sibling of this node
         *
         * @return the sibling of this node
         */
        public LCRSNode<T> getSibling() {
            return sibling;
        }

        /**
         * Sets the sibling of this node
         *
         * @param s the node to be used as sibling
         */
        public final void setSibling(LCRSNode<T> s) {
            sibling = s;
        }

        /**
         * Consults the tree in which this node is stored
         *
         * @return a reference to the tree where the node belongs
         */
        public LCRSTree<T> getMyTree() {
            return myTree;
        }

        /**
         * Sets the tree where this node belongs
         *
         * @param myTree the tree where this node belongs
         */
        public void setMyTree(LCRSTree<T> myTree) {
            this.myTree = myTree;
        }
    }

    private LCRSNode<E> root; // The root of the tree
    private int size; // The number of nodes in the tree

    /**
     * Creates an empty tree.
     */
    public LCRSTree() {
        root = null;
        size = 0;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return (size == 0); }

    @Override
    public Position<E> root() throws RuntimeException
    {
        if (root == null) {
            throw new RuntimeException("The tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException
    {
        LCRSNode<E> node = checkPosition(v);
        Position<E> parentPos = node.getParent();
        if (parentPos == null) {
            throw new RuntimeException("The node has not parent");
        }
        return parentPos;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v)
    {
        LCRSNode<E> node = checkPosition(v);
        return node.getChildren();
    }

    @Override
    public boolean isInternal(Position<E> v) { return !isLeaf(v); }

    @Override
    public boolean isLeaf(Position<E> v)
    {
        LCRSNode<E> node = checkPosition(v);
        return (node.getChildren() == null) || (node.getChildren().isEmpty());
    }

    @Override
    public boolean isRoot(Position<E> v)
    {
        LCRSNode<E> node = checkPosition(v);
        return (node == this.root());
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException
    {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root");
        }
        size = 1;
        root = new LCRSNode<E>(this, e, null, null,  new ArrayList<>());
        return root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public E replace(Position<E> p, E e)
    {
        LCRSNode<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2)
    {
        LCRSNode<E> node1 = checkPosition(p1);
        LCRSNode<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
    }

    /**
     * Validates the given position, casting it to TreeNode if valid
     *
     * @param v the position to be converted
     * @return the position casted to TreeNode
     * @throws IllegalStateException if the position is not valid
     */
    private LCRSNode<E> checkPosition(Position<E> v) throws IllegalStateException
    {
        if (v == null || !(v instanceof LCRSNode)) {
            throw new IllegalStateException("The position is invalid");
        }
        LCRSNode<E> aux = (LCRSNode<E>) v;

        if (aux.getMyTree() != this) {
            throw new IllegalStateException("The node is not from this tree");
        }
        return aux;
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void remove(Position<E> p) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }
}
