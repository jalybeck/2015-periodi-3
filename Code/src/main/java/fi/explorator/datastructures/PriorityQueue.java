package fi.explorator.datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * PriorityQueue implemented as minheap implements java Queue.
 *
 * Only methods used in this project are implemented.
 *
 * @param <T>
 */
public class PriorityQueue<T> implements Queue {

    private List<HeapNode<T>> heap;
    private int heapSize;

    public PriorityQueue() {
        heap = new ArrayList<HeapNode<T>>(20, true);
        heapSize = 0;

    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    /**
     * Maintains minimum heap rule starting from heap node i.
     *
     * @param i
     */
    private void heapify(int i) {

        int l = left(i);
        int r = right(i);
        int smallest = i;

        if (l <= heapSize && heap.get(l).getValue() < heap.get(smallest).getValue())
            smallest = l;

        if (r <= heapSize && heap.get(r).getValue() < heap.get(smallest).getValue())
            smallest = r;

        if (smallest != i) {
            HeapNode<T> tmp = heap.get(i);
            heap.set(i, heap.get(smallest));
            heap.set(smallest, tmp);
            heapify(smallest);
        }
    }

    @Override
    public boolean add(Object e) {
        boolean ok = offer(e);
        if (!ok)
            throw new IllegalStateException("Can't add! No more space left on priorityqueue!");
        return true;
    }

    @Override
    public boolean offer(Object e) {
        heapSize++;

        //Expand arraylist by adding null object.
        if (heapSize == heap.size()) {
            heap.add(null);
        }

        int i = heapSize;
        while (i > 1 && heap.get(parent(i)).getValue() > ((HeapNode)e).getValue()) {
            heap.set(i, heap.get(parent(i)));
            i = parent(i);
        }

        heap.set(i, (HeapNode<T>)e);
        return true;
    }

    @Override
    public HeapNode<T> remove() {
        return poll();
    }

    @Override
    public HeapNode<T> poll() {
        HeapNode<T> min = heap.get(1);
        heap.set(1, heap.get(heapSize));
        heapSize--;
        heapify(1);
        return min;
    }


    @Override
    public int size() {
        return heapSize;
    }

    @Override
    public boolean isEmpty() {
        if (heapSize > 0)
            return false;

        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (o != null) {
            int ordNum = (Integer)o;
            for (HeapNode<T> n : heap) {
                if (n != null && n.getValue() == ordNum)
                    return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<HeapNode<T>> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public HeapNode<T> element() {
        return null;
    }

    @Override
    public HeapNode<T> peek() {
        return null;
    }
}
