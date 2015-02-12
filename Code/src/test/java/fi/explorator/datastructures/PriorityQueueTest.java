package fi.explorator.datastructures;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PriorityQueueTest {
    public PriorityQueueTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testAddandPoll() {
        PriorityQueue q = new PriorityQueue();
        
        q.add(new HeapNode(4,"Neljä"));
        q.add(new HeapNode(1,"Yksi"));
        q.add(new HeapNode(5,"Viisi"));
        q.add(new HeapNode(2,"Kaksi"));
        q.add(new HeapNode(3,"Kolme"));
        
        int i=1;
        while(!q.isEmpty()) {
            HeapNode v = q.poll();
            assertTrue(i+"!="+v.getValue(),v.getValue() == i++);
            System.out.println(v.getPayload());
        }
    }

    @Test
    public void testContains() {
        PriorityQueue q = new PriorityQueue();
        
        q.add(new HeapNode(4,"Neljä"));
        q.add(new HeapNode(1,"Yksi"));
        q.add(new HeapNode(5,"Viisi"));
        q.add(new HeapNode(2,"Kaksi"));
        q.add(new HeapNode(3,"Kolme"));
        
        assertTrue("Number 2 does not found from PriorityQueue!",q.contains(2));        
        assertFalse("Number 10 should not be in PriorityQueue", q.contains(10));
        assertTrue("Number 5 does not found from PriorityQueue!",q.contains(5));
    }
    
    @Test
    public void testAddMoreElementsThanInList() {
        PriorityQueue q = new PriorityQueue();
        
        for(int i=0;i<80;i++) {
            q.add(new HeapNode(80-i,null));
        }
        
        int i=1;
        while(!q.isEmpty()) {
            HeapNode v = q.poll();
            assertTrue(i+"!="+v.getValue(),v.getValue() == i++);
        }
    }
}
