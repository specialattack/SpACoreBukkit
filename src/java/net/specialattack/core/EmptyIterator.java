
package net.specialattack.core;

import java.util.Iterator;

public class EmptyIterator<E> implements Iterator<E> {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        return null;
    }

    @Override
    public void remove() {}

}
