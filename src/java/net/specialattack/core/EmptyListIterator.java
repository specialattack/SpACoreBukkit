
package net.specialattack.core;

import java.util.ListIterator;

public class EmptyListIterator<E> implements ListIterator<E> {

    @Override
    public void add(E e) {}

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public E next() {
        return null;
    }

    @Override
    public int nextIndex() {
        return 0;
    }

    @Override
    public E previous() {
        return null;
    }

    @Override
    public int previousIndex() {
        return 0;
    }

    @Override
    public void remove() {}

    @Override
    public void set(E e) {}

}
