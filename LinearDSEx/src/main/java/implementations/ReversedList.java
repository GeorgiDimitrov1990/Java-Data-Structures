package implementations;

import java.util.Iterator;

public class ReversedList<E> implements Iterable<E> {
    private final int INITIAL_CAPACITY = 2;
    private Object[] elements;
    private int size;
    private int capacity;


    public ReversedList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;

    }

    public void Add(E element) {
        if (size == capacity) {
            this.elements = grow();
        }
        this.elements[size++] = element;
    }

    private Object[] grow() {
        capacity *= 2;
        Object[] newElements = new Object[capacity];
        for (int i = 0; i < this.elements.length; i++) {
            newElements[i] = this.elements[i];
        }
        return newElements;
    }

    public int Size() {
        return this.size;
    }

    public int Capacity() {
        return this.capacity;
    }

    public E Get(int index) {
        isIndexInBounds(index);
        int realIndex = (this.size - 1) - index;
        return (E) this.elements[realIndex];
    }


    public void RemoveAt(int index) {
        isIndexInBounds(index);
        int realIndex = (this.size - 1) - index;
        for (int i = realIndex; i < this.size; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[size] = null;
        this.size--;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = size - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                return (E) elements[index--];
            }
        };
    }

    private void isIndexInBounds(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }
}
