package fi.explorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Path class which keeps list of Cell objects which forms the path.
 * Path finding algorithms insantiates this class.
 */
public class Path {
    private List<Cell> path;

    public Path() {
        super();
        path = new ArrayList<Cell>();
    }

    /**
     * Add cell to path.
     * @param c Cell to be added to this Path
     */
    public void add(Cell c) {
        path.add(c);
    }

    /**
     * Get list of cells which represents this path.
     * @return ArrayList of the cells representing path in correct order.
     */
    public List<Cell> getCells() {
        return this.path;
    }

    /**
     * String representation of the Path.
     * @return String object which consist of all the cells
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Cell c : path) {
            sb.append(c).append("->");
        }

        return sb.toString();
    }
}
