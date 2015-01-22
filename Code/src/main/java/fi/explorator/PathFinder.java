package fi.explorator;

/**
 * Abstract base class which all path finding algorithms will be extending from.
 * This base class also works as Factory class for PathFinder type of classes.
 *
 * This holds the Grid object as well as start and goal Cell objects.
 */

public abstract class PathFinder {

    protected Grid grid;
    protected Cell start;
    protected Cell goal;

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
}
