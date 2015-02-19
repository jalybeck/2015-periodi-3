package fi.explorator.datastructures;

/**
 * HeapNode to be used with PriorityQueue class.
 * int value is used for comparison inside PriorityQueue.
 *
 * Payload holds the actual data object.
 * @param <E>
 */
public class HeapNode<E> {

    private int value;
    private E payload;

    public HeapNode(int value, E payload) {
        super();
        this.value = value;
        this.payload = payload;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setPayload(E payload) {
        this.payload = payload;
    }

    public E getPayload() {
        return this.payload;
    }
}
