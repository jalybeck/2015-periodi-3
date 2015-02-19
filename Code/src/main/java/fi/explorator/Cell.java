package fi.explorator;

import fi.explorator.datastructures.ArrayList;
//import java.util.ArrayList;
import java.util.List;

/**
 * Represents single cell in 2D grid.
 */
public class Cell implements Comparable<Cell> {
    private int x, y;

    private List<Edge> edgeList;

    /**
     * This is identifier of the cell.
     * Number 0 would be cell at left-top corner,
     */
    private int orderNumber;

    /**
     * This is general purpose data.
     * Usage of this value depends on the algorithms used.
     */
    private int value;

    public Cell(int y, int x, int orderNumber) {
        this.y = y;
        this.x = x;
        this.orderNumber = orderNumber;
        this.edgeList = new ArrayList<Edge>();
        value = 1;
    }

    /**
     * Copy construct.
     *
     * @param cell
     */
    public Cell(Cell cell) {
        this.y = cell.y;
        this.x = cell.x;
        this.orderNumber = cell.orderNumber;
        this.edgeList = cell.edgeList;
        this.value = cell.value;
    }

    /**
     * Get x-coordinate of the cell.
     * @return positive integer
     */
    public int getX() {
        return x;
    }

    /**
     * Get y-coordinate of the cell
     * @return positive integer
     */
    public int getY() {
        return y;
    }

    /**
     * String representation of the Cell object.
     * @return String which consists y and x coordinates of the cell
     */
    public String toString() {
        return "(" + y + "," + x + "," + orderNumber + ")";
    }

    /**
     * Set value of the cell.
     * This can be thought as cell type.
     * For example: 100 could mean that cell is blocked while 0 would be cell which is passable.
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
        // for(Edge e : edgeList)
        //     e.setWeight(value);
    }

    /**
     * Value of the cell
     * @return int value
     */
    public int getValue() {
        return value;
    }

    /**
     * Identifier of the cell.
     * @return int value
     */
    public int getOrderNumber() {
        return this.orderNumber;
    }

    /**
     * Add edge to current cell.
     * @param e Edge to be added
     */
    public void addEdge(Edge e) {
        this.edgeList.add(e);
    }

    /**
     * Get list of edges connected to current cell.
     * @return List of edges
     */
    public List<Edge> getEdges() {
        return this.edgeList;
    }

    /**
     * From Comparable interface.
     * This will be used to cell to cell compare operations.
     * @param o Cell to be compared against.
     * @return negative, zero, or positive integer depending how this cell compares to cell given as parameter
     */
    @Override
    public int compareTo(Cell o) {
        return (value - o.value);
    }

}

