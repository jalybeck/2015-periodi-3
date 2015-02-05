package fi.explorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class which all path finding algorithms will be extending from.
 * This base class also works as Factory class for PathFinder type of classes.
 *
 * This holds the Grid object as well as start and goal Cell objects.
 */

public abstract class PathFinder {
    /**
     * This constant represents infinity.
     */
    protected final int MAX_NUM = 10000000;

    protected Grid grid;
    protected Cell start;
    protected Cell goal;
    protected int passableWeight;
    protected int blockedWeight;
    protected boolean pathFound;
    protected int numVertices;

    protected PathFinder() {

    }

    /**
     * Factory method which is used to instantiate different kinds of PathFinder type classes.
     * @param type  Enumeration of the algorithm types.
     * @param grid  2D grid.
     * @param start Starting cell for path.
     * @param goal  Goal cell for path.
     * @return  PathFinder type class.
     */
    public static PathFinder createPathFinder(PathFinderType type, Grid grid, Cell start, Cell goal) {
        PathFinder pf = null;

        switch (type) {
        case FLOYD_WARSHALL:
            pf = new FloydWarshall();
            break;
        case DIJKSTRA:
            pf = new Dijkstra();
            break;
        case A_STAR:
            pf = new AStar();
            break;
        }

        if (pf != null) {
            pf.setGrid(grid);
            pf.setStart(start);
            pf.setGoal(goal);
        }

        return pf;

    }

    /**
     * Runs algorithm one step at a time.
     * Some algorithms might run continuosly in this method
     * depending on the algorithm implementation.
     */
    public abstract void findPathStep();

    /**
     * This should be called in loop to determine when the path is found.
     * @return true/false depending if the path is found between start and goal cells.
     */
    public abstract boolean pathFound();

    public abstract void resetPathFound();

    /**
     * Get result path from PathFinder
     * @return  Path object which represents found path from start cell to goal cell.
     */
    public abstract Path getPath();

    /**
     * Set 2D grid which PathFinder is trying to search the path.
     * @param grid 2D grid
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
        this.numVertices = grid.getCells().size();
    }

    /**
     * Get 2D grid
     * @return 2D grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Set starting Cell where PathFinder is starting to find the path to the goal.
     * @param start Cell object
     */
    public void setStart(Cell start) {
        this.start = start;
    }

    /**
     * Get starting cell.
     * @return Cell object
     */
    public Cell getStart() {
        return start;
    }

    /**
     * Set goal cell which PathFinder tries to reach.
     * @param goal Cell object
     */
    public void setGoal(Cell goal) {
        this.goal = goal;
    }

    /**
     * Get goal cell.
     * @return Cell object
     */
    public Cell getGoal() {
        return goal;
    }

    public void setBlockedWeight(int maxwWeight) {
        this.blockedWeight = maxwWeight;
    }

    public int getBlockedWeight() {
        return blockedWeight;
    }

    /**
     * Set passable weight of the cell.
     * This function iterates all the cells in grid and adds weight as a value to it.
     * @param passableWeight
     */
    public void setPassableWeight(int passableWeight) {
        this.passableWeight = passableWeight;

        for (Cell c : grid.getCells()) {
            c.setValue(passableWeight);
        }
    }

    public int getPassableWeight() {
        return passableWeight;
    }
    
    /**
     * Get adjacent cells of the given cell parameter.
     * Cells with value of blockedWeight will be not traversed.
     * @param c Cell object which adjacent cells we want to be returned
     * @return ArrayList of adjacent cells
     */
    protected List<Cell> getAdjacentCells(Cell c) {
        List<Cell> adj = new ArrayList<Cell>();

        for (Edge e : c.getEdges()) {
            if(e.getWeight() == blockedWeight)
                continue;
            
            Cell cs = e.getCellStart();
            Cell ce = e.getCellEnd();

            if (c.getOrderNumber() != cs.getOrderNumber()) {
                adj.add(cs);
            } else if (c.getOrderNumber() != ce.getOrderNumber()) {
                adj.add(ce);
            }

        }

        return adj;
    }
}
