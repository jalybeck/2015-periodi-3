package fi.explorator.datastructures;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * ArrayList implementation, which implements List and Iterable interfaces.
 * Methods used in this project are only implemented.
 *
 * @param <E> Object type
 */
public class ArrayList<E> implements List<E>, Iterable<E> {
    //List variables
    private final int DEFAULT_SIZE = 10;
    private E arr[];
    private int currentSize;

    /**
     * Private class used to return iterator to ArrayList
     * 
     * @param <T> type template
     */
    private class ArrayListIterator<T> implements ListIterator<T> {

        private int currentIndex;
        private int previousIndex;
        private ArrayList<T> aList;

        private ArrayListIterator(ArrayList<T> aList) {
            this.aList = aList;
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            boolean next = currentIndex < aList.size();

            if (!next) {
                this.aList = null;
            }

            return next;
        }

        @Override
        public T next() {
            T obj = aList.get(currentIndex);
            previousIndex = currentIndex;
            currentIndex++;
            return obj;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported!");
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void set(T e) {
            aList.set(previousIndex, e);
        }

        @Override
        public void add(T e) {
        }
    }

    public ArrayList() {
        initialize(DEFAULT_SIZE, false);
    }

    public ArrayList(int size) {
        super();
        initialize(size, false);
    }

    /**
     * Constructor to create ArrayList with size.
     * If expandToSize is true lists currentsize will be set to size,
     * so set method can be used for the empty list.
     *
     * @param size
     * @param expandToSize
     */
    public ArrayList(int size, boolean expandToSize) {
        super();
        initialize(size, expandToSize);
    }

    private void initialize(int size, boolean expandToSize) {
        arr = (E[])new Object[size];
        if (expandToSize)
            currentSize = size;
        else
            currentSize = 0;
    }

    @Override
    public int size() {
        return currentSize > Integer.MAX_VALUE ? Integer.MAX_VALUE : currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize > 0 ? false : true;
    }

    /**
     * If underlying array grows larger than its current size,
     * it will be copied to new array and new arrays size is multiple of 2 of the old arrays size.
     *
     * @param e object to be added at the tail of the list
     * @return always true
     */
    @Override
    public boolean add(E e) {
        if (currentSize == arr.length) {
            Object tmpArr[] = new Object[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                tmpArr[i] = arr[i];
            }

            arr = (E[])tmpArr;
        }

        arr[currentSize++] = e;

        return true;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (o == null && arr[i] == null)
                return true;
            else if (o != null && o.equals(arr[i]))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator<E>(this);
    }

    @Override
    public Object[] toArray() {
        Object[] newArr = new Object[currentSize];

        for (int i = 0; i < currentSize; i++) {
            newArr[i] = arr[i];
        }

        return newArr;
    }

    @Override
    public Object[] toArray(Object[] a) {
        for (int i = 0; i < currentSize; i++) {
            a[i] = arr[i];
        }

        return a;
    }

    @Override
    public void add(int index, E element) {
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E obj = arr[index];

        for (int i = index; i < currentSize - 1; i++) {
            arr[i] = arr[i + 1];
        }

        currentSize--;

        return obj;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
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
    public void clear() {

        for (int i = 0; i < currentSize; i++) {
            arr[i] = null;
        }
        currentSize = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return (E)arr[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E obj = arr[index];

        arr[index] = element;

        return obj;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ArrayListIterator<E>(this);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return Collections.emptyList();
    }

    private boolean checkIndex(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("index < 0, index = " + index);
        } else if (index >= currentSize) {
            throw new ArrayIndexOutOfBoundsException("index >= " + currentSize + " index = " + index);
        }
        return true;
    }
}
