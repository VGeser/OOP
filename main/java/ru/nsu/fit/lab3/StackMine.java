package ru.nsu.fit.lab3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class StackMine<T> implements Iterable {
    public T[] getContainArray() {
        return containArray;
    }

    private T[] containArray;
    private static int size; //existing elements

    public StackMine(int cap) {
        try {
            containArray = (T[]) new Object[cap];
        } catch (OutOfMemoryError out) {
            out.printStackTrace();
        }
    }

    /**
     * @return number of initialized elements in container
     */
    public int size() {
        return size;
    }

    /**
     * resizes array multiplying by 2
     *
     * @return - 1 or 0 depending on success of operation
     * handles possible exceptions for the caller
     */
    public byte resize() {
        int newSize = (containArray.length) * 2;
        try {
            containArray = Arrays.copyOf(containArray, newSize);
        } catch (OutOfMemoryError | NullPointerException out) {
            return 0;
        }
        return 1;
    }

    /**
     * pushes single elements to the stack
     *
     * @param elem - element to be pushed
     * @return - 1 or 0 depending on success
     */
    public byte push(T elem) {
        if (size < containArray.length) {
            containArray[size] = elem;
        } else {
            byte success = resize();
            if (success == 0) {
                return 0;
            }
            push(elem);
            return 1;
        }
        size++;
        return 1;
    }

    /**
     * concatenates stacks
     *
     * @param entry - a stack object to be pushed into existing stack
     * @return - 1 or 0 depending on success
     */
    public byte pushStack(T[] entry) {
        int success, entryLen;
        entryLen = entry.length;
        if (entryLen + size <= containArray.length) {
            System.arraycopy(entry, 0, containArray, size, entryLen);
            size += entryLen;
        } else {
            success = resize();
            if (success == 0) return 0;
            return pushStack(entry); //to make sure array is resized enough times to fit
        }
        return 1;
    }

    /**
     * pops a single element from stack
     *
     * @return - first stack element (last in container)
     * @throws NullPointerException - if an array is empty
     */
    public T pop() throws NullPointerException {
        T elem = containArray[size - 1];
        containArray[size - 1] = null;
        size--;
        return elem;
    }

    /**
     * pops several elements off
     *
     * @param num - number of elements to be popped
     * @return - a stack-like array
     * @throws NullPointerException - if a container is empty
     */
    public T[] popStack(int num) throws NullPointerException {
        T[] current = (T[]) new Object[num];
        int j = num;
        for (int i = 1; i <= num; i++) {
            current[j - 1] = containArray[size - i];
            containArray[size - i] = null;
            j--;
        }
        size -= num;
        return current;
    }

    @Override
    public Iterator iterator() {
        return new smallIterator<>(this);
    }
}

class smallIterator<T> implements Iterator {
    private final T[] elems;
    private int cursor;
    private final int len;

    public smallIterator(StackMine<T> tStackMine) {
        cursor = 0;
        elems = tStackMine.getContainArray();
        len = tStackMine.size();
    }

    @Override
    public boolean hasNext() {
        return cursor < len;
    }

    @Override
    public Object next() {
        T elem = elems[cursor];
        cursor++;
        return elem;
    }
}
