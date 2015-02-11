package fi.explorator;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CellTest {
    public CellTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * @see Cell#compareTo(Cell)
     */
    @Test
    public void testCompareTo() {
        List<Cell> list = new java.util.ArrayList<Cell>();
        
        for(int i=0;i<20;i++) {
            Cell c = new Cell(0,0,i);
            c.setValue(19-i);
            list.add(c);
        }
        
        Collections.sort(list);
        int i=0;
        for(Cell c : list) {
            if(c.getValue() != i) {
                fail("getValue() should return "+i+" but returned "+c.getValue());
            }
            i++;
        }
        
    }
}
