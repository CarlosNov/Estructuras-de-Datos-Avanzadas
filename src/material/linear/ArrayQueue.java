package material.linear;

public class ArrayQueue<E> implements Queue<E>
{
    private final int INITIAL_CAP 2;

    private E[] array;
    private int head;
    private int tail;
    private int capacity;
    private int size;

    public ArrayQueue()
    {
        array = new E[2];
        head = 0;
        tail = 0;
        capacity = INITIAL_CAP;
    }

    public ArrayQueue(int capacity)
    {
        throw new RuntimeException("Not yet implemented");
    }


    @Override
    public int size() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public E front() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void enqueue(E element) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public E dequeue() {
        throw new RuntimeException("Not yet implemented");
    }
}
