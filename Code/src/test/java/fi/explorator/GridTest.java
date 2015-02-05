package fi.explorator;

import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * Test class
 * 
 * @see Grid
 */
public class GridTest {
    public GridTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Create 5x5 Grid and check that correct amount of cells are being
     * returned. We will do sanity check that, no Cell object is same.
     * 
     * @see Grid#getCells()
     */
    @Test
    public void testGetCells() {
        Grid g = new Grid(5,5);
        List<Cell> cells = g.getCells();
        
        assertEquals("Returned amount of cells should 25!",25,cells.size());
        
        for(int i=0;i<cells.size();i++) {
            for(int j=i+1;j<cells.size();j++) {
                assertFalse("There are duplicate Cell objects in Grid!", cells.get(i) == cells.get(j));
            }
        }
        
    }

    /**
     * Create 5x5 grid and test, 
     * that correct orderNumbers are being assigned to cell objects.
     * 
     * @see Grid#getCell(int)
     */
    @Test
    public void testGetCellCorrectOrderNumber() {
        Grid g = new Grid(5,5);
        
        Cell c = g.getCell(0);
        assertTrue("Top left corner coordinates should be (0,0) for orderNumber=0!",c.getY() == 0 && c.getX() == 0);
        
        c = g.getCell(24);
        assertTrue("Bottom right corner coordinates should be (4,4) for orderNumber=24!",c.getY() == 4 && c.getX() == 4);
        
        c = g.getCell(4);
        assertTrue("Top right corner coordinates should be (0,4) for orderNumber=4!",c.getY() == 0 && c.getX() == 4);
        
        c = g.getCell(20);
        assertTrue("Bottom left corner coordinates should be (4,0) for orderNumber=20!",c.getY() == 4 && c.getX() == 0);         
    }

    /**
     * Create 5x5 grid and test we can get correct orderNumber for given coordinate pair (y,x)
     * 
     * @see Grid#getCell(int,int)
     */
    @Test
    public void testGetCorrectCellForCoordinates() {
        Grid g = new Grid(5,5);
        
        Cell c = g.getCell(0,0);
        assertEquals("Ordernumber should be 0 for top left coordinates (0,0)",0, c.getOrderNumber());
        
        c = g.getCell(4,4);
        assertEquals("Ordernumber should be 24 for bottom right coordinates (4,4)",24,c.getOrderNumber());
        
        c = g.getCell(0,4);
        assertEquals("Ordernumber should be 4 for top right coordinates (0,4)",4,c.getOrderNumber());
        
        c = g.getCell(4,0);
        assertEquals("Ordernumber should be 20 for bottom left coordinates (4,0)",20,c.getOrderNumber());          
    }

    /**
     * Create 5x5 grid and test that all the edges are connected to correct cells.
     * Check first that the amount of edges are right for Grid.
     * 
     * @see Grid#getEdges()
     */
    @Test
    public void testGetEdges() {
        Grid g = new Grid(5,5);

        List<Edge> edges = g.getEdges();
        
        //For any n*n grid there should be ((n-1)*n)*2 edges
        assertEquals("5x5 grid should have 40 edges!",40,edges.size());
        
        
    }
}
