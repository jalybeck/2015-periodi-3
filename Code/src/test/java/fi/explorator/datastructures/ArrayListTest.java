package fi.explorator.datastructures;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ArrayListTest {
    public ArrayListTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     * @see ArrayList#contains(Object)
     */
    @Test
    public void testContains() {
        List<String> aList = new ArrayList<String>();
        int numStrings = 100;
        for (int i = 0; i < numStrings; i++) {
            aList.add("Test" + i);
        }

        assertTrue("List does not contain string Test59!", aList.contains("Test59"));
        assertTrue("List does not contain string Test99!", aList.contains("Test99"));

        assertFalse("List should not contain string Test200!", aList.contains("Test200"));
    }

    /**
     * @see ArrayList#add(E)
     */
    @Test
    public void testAdd() {
        List<String> aList = new ArrayList<String>();
        int numStrings = 100;
        for (int i = 0; i < numStrings; i++) {
            aList.add("Test" + i);
        }
        int i = 0;
        for (String s : aList) {
            if (s.compareTo("Test" + i) != 0) {
                fail("String should be Test" + i + " but was " + s);
            }
            i++;
        }
    }

    /**
     * @see ArrayList#isEmpty()
     */
    @Test
    public void testIsEmpty() {
        List<String> l = new ArrayList<String>();

        assertTrue("1: isEmpty() should return true but returned false!", l.isEmpty());

        l.add("Test");
        assertFalse("2: isEmpty() should return false but returned true!", l.isEmpty());
    }

    /**
     * @see ArrayList#size()
     */
    @Test
    public void testSize() {
        List<String> l = new ArrayList<String>();

        assertTrue("1: size() should return 0 but returned " + l.size(), l.size() == 0);

        l.add("Test");

        assertTrue("2: size() should return 1 but returned " + l.size(), l.size() == 1);

        for (int i = 0; i < 50; i++) {
            l.add("Test" + i);
        }

        assertTrue("3: size() should return 51 but returned " + l.size(), l.size() == 51);
    }

    /**
     * @see ArrayList#get(int)
     */
    @Test
    public void testGet() {
        List<String> l = new ArrayList<String>();

        l.add("Test");
        String s = l.get(0);
        if (s.compareTo(s) != 0) {
            fail("String should be Test, but was " + s);
        }
    }

    /**
     * @see ArrayList#remove(int)
     */
    @Test
    public void testSortAndRemove() {
        List<Integer> l = new ArrayList<Integer>();

        for (int i = 0; i < 20; i++) {
            l.add(19 - i);
        }

        Collections.sort(l);

        int i = 0;
        while (!l.isEmpty()) {
            int v = l.remove(0);

            if (v != i) {
                fail("Removed number should be " + i + " but was " + v);
            }
            i++;
        }
    }
}
