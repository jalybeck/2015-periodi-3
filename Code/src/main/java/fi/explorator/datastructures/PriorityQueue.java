package fi.explorator.datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class PriorityQueue<HeapNode> implements Queue<HeapNode> {

    private List<HeapNode> heap;
    
    public PriorityQueue() {
        heap = new ArrayList<HeapNode>(20);
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
    
    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l <= heapSize && heap[l].sortVal < heap[smallest].sortVal)
            smallest = l;

        if (r <= heapSize && heap[r].sortVal < heap[smallest].sortVal)
            smallest = r;

        if (smallest != i) {
            HeapNode tmp = heap[i];
            heap[i] = heap[smallest];
            heap[smallest] = tmp;
            heapify(smallest);
        }
    }
    
    @Override
    public boolean add(HeapNode e) {
        boolean ok = offer(e);
        if(!ok)
            throw new IllegalStateException("Can't add! No more space left on priorityqueue!");
        return true;
    }

    @Override
    public boolean offer(HeapNode e) {
        return false;
    }

    @Override
    public HeapNode remove() {
        return null;
    }

    @Override
    public HeapNode poll() {
        return null;
    }

    @Override
    public HeapNode element() {
        return null;
    }

    @Override
    public HeapNode peek() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<HeapNode> iterator() {
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
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends HeapNode> c) {
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
    }
}
