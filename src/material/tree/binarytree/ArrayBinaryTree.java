package material.tree.binarytree;

import material.Position;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayBinaryTree<E> implements BinaryTree<E> {

    protected class BTPos<T> implements Position<T> {

        private T element;
        private int rank;

        /**
         * Main constructor.
         *
         * @param element element stored in this node
         * @param rank position of the node
         */
        public BTPos(T element, int rank) {
            setElement(element);
            setRank(rank);
        }

        /**
         * Returns the element stored at this node.
         *
         * @return the element stored at this node
         */
        @Override
        public T getElement() {
            return element;
        }

        /**
         * Sets the element stored at this node.
         *
         * @param o the element to be stored
         */
        public final void setElement(T o) {
            element = o;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    private BTPos<E> root;
    private ArrayList<BTPos<E>> tree = new ArrayList<>();

    @Override
    public Position<E> left(Position<E> v) throws RuntimeException
    {
        BTPos<E> node = checkPosition(v);
        Position<E> leftPos = tree.get(2*node.getRank());
        if (leftPos == null) {
            throw new RuntimeException("No left child");
        }
        return leftPos;
    }

    @Override
    public Position<E> right(Position<E> v) throws RuntimeException
    {
        BTPos<E> node = checkPosition(v);
        Position<E> rightPos = tree.get(2*node.getRank()+1);
        if (rightPos == null) {
            throw new RuntimeException("No right child");
        }
        return rightPos;
    }

    @Override
    public boolean hasLeft(Position<E> v)
    {
        BTPos<E> node = checkPosition(v);
        return tree.size() >= 2*node.getRank();
    }

    @Override
    public boolean hasRight(Position<E> v)
    {
        BTPos<E> node = checkPosition(v);
        return tree.size() >= 2*node.getRank()+1;
    }

    @Override
    public E replace(Position<E> p, E e) {
        return null;
    }

    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException
    {
        return null;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException
    {
        return null;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        return null;
    }

    @Override
    public E remove(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {

    }

    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        return null;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {

    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {

    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Position<E> root() throws RuntimeException
    {
        if (root == null) {
            throw new RuntimeException("The tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        return null;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        checkPosition(v);
        return (hasLeft(v) || hasRight(v));
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) {
        checkPosition(v);
        return (v == root());
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        return null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return null;
    }

    private BTPos<E> checkPosition(Position<E> p) {
        if (p == null || !(p instanceof BTPos)) {
            throw new RuntimeException("The position is invalid");
        }
        return (BTPos<E>) p;
    }
}
