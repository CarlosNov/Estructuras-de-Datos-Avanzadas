package material.tree.iterators;

import material.Position;
import material.tree.Tree;

import java.util.*;
import java.util.function.Predicate;

/**
 * Generic preorder iterator for trees.
 *
 * @param <E>
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, JD. Quintana
 */

public class PreorderIterator<E> implements Iterator<Position<E>>
{
    private final Stack<Position<E>> nodeStack;
    private final Tree<E> tree;
    private final Predicate<Position<E>> predicate;

    public PreorderIterator(Tree<E> tree)
    {
        nodeStack = new Stack<>();
        this.tree = tree;
        predicate = null;
        if(!tree.isEmpty())
            nodeStack.add(tree.root());
    }

    public PreorderIterator(Tree<E> tree, Position<E> start)
    {
        nodeStack = new Stack<>();
        this.tree = tree;
        predicate = null;
        nodeStack.add(start);
    }

    public PreorderIterator(Tree<E> tree, Position<E> start, Predicate<Position<E>> predicate)
    {
        nodeStack = new Stack<>();
        this.tree = tree;
        this.predicate = predicate;
        nodeStack.add(start);
    }


    @Override
    public boolean hasNext()
    {
        return (nodeStack.size() != 0);
    }

    @Override
    public Position<E> next() throws NoSuchElementException
    {
        if(!hasNext())
            throw new NoSuchElementException();

        Position<E> element = getStackElement(this.nodeStack);

        if(this.predicate != null)
            if(!predicate.test(element))
                return next();

        return element;
    }

    private Position<E> getStackElement(Stack<Position<E>> stack)
    {
        Stack<Position<E>> auxiliaryStack = new Stack<>();

        Position<E> element = stack.pop();

        for (Position<E> node : tree.children(element))
        {
            auxiliaryStack.push(node);
        }

        while (!auxiliaryStack.empty())
        {
            stack.push(auxiliaryStack.pop());
        }

        return element;
    }
}
