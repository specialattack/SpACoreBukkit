
package net.specialattack.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EmptyList<T> implements List<T> {

    private static final Object[] EMPTY_OBJ_ARRAY = new Object[] {};

    @Override
    public boolean add(T e) {
        return false;
    }

    @Override
    public void add(int index, T element) {}

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public void clear() {}

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new EmptyIterator<T>();
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new EmptyListIterator<T>();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index != 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return new EmptyListIterator<T>();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this;
    }

    @Override
    public Object[] toArray() {
        return EMPTY_OBJ_ARRAY;
    }

    @Override
    public <A> A[] toArray(A[] array) {
        for (int index = 0; index < array.length; index++) {
            array[index] = null;
        }

        return array;
    }

}
