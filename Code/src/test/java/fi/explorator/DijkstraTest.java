package fi.explorator;

import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DijkstraTest {
    public DijkstraTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * @see PathFinder#findPathStep()
     */
    @Test
    public void testFindPathStep() {
        Grid grid = new Grid(10, 10);
        Cell start = grid.getCell(0,0);
        Cell goal = grid.getCell(7,8);
        PathFinder pf = PathFinder.createPathFinder(PathFinderType.DIJKSTRA, grid, start, goal);

        while (!pf.pathFound()) {
            pf.findPathStep();
        }
        Path path = pf.getPath();
        List<Cell> pathCells =  path.getCells();

        assertTrue("Path length should be 15",path.length() == 15);
        
        assertTrue("Goal should be (7,8)!",pathCells.get(0).getY() == 7 && pathCells.get(0).getX() == 8);
    }
}
