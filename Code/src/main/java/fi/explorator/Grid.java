package fi.explorator;

import java.io.PrintStream;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2D grid which represents the area where the starting point and ending point of the path resides.
 * Grid cells are being numbered starting from zero at top-left corner and raising up by one when going right.
 * This number is being used to identify the cell in the grid.
 * 
 * This is the main composite object which holds the Cell and Edge instances.
 * This works as data transfer object between visualization layer and path finding algorithms.
 */
public class Grid {
    private int rows, cols;
    private Cell[][] g;
    private List<Edge> edgeList;

    private Map<Integer, Cell> cellCache;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.edgeList = new ArrayList<Edge>();
        this.cellCache = new HashMap<Integer, Cell>();
        this.g = new Cell[rows][cols];

        //Construct grid and edgelist
        //Add horizontal edges of grid
        Cell prevCell1 = null;
        for (int y = 0; y < rows; y++) {
            prevCell1 = null;
            for (int x = 0; x < cols; x++) {
                int orderNumber = x + y * cols;
                g[y][x] = new Cell(y, x, orderNumber);
                cellCache.put(orderNumber, g[y][x]);
                if (prevCell1 != null) {
                    Edge e = new Edge(prevCell1, g[y][x], 1);
                    edgeList.add(e);
                    prevCell1.addEdge(e);
                    g[y][x].addEdge(e);
                }
                prevCell1 = g[y][x];
            }
        }

        //Add vertical edges of grid
        for (int x = 0; x < cols; x++) {
            prevCell1 = null;
            for (int y = 0; y < rows; y++) {
                if (prevCell1 != null) {
                    Edge e = new Edge(prevCell1, g[y][x], 1);
                    edgeList.add(e);
                    prevCell1.addEdge(e);
                    g[y][x].addEdge(e);
                }

                prevCell1 = g[y][x];
            }
        }
    }

    /**
     * Get list of edges in this Grid.
     * @return List of Edge objects
     */
    public List<Edge> getEdges() {
        return this.edgeList;
    }

    /**
     * Get Cell at position y,x in grid.
     * @param y coordinate
     * @param x coordinate
     * @return Cell object
     */
    public Cell getCell(int y, int x) {
        return g[y][x];
    }

    /**
     * Get the cell represented by its orderNumber
     * @param orderNumber number of the cell
     * @return Cell object
     */
    public Cell getCell(int orderNumber) {
        return cellCache.get(orderNumber);
    }

    /**
     * Get all the Cells in this Grid.
     * @return List of Cell objects
     */
    public List<Cell> getCells() {
        List<Cell> cellList = new ArrayList<Cell>();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                cellList.add(g[y][x]);
            }
        }

        return cellList;
    }

    /**
     * Get number of rows in this grid.
     * @return Number of rows
     */
    public int getNumRows() {
        return rows;
    }

    /**
     * Get number of columns in this grid.
     * @return Number of columns.
     */
    public int getNumColumns() {
        return cols;
    }

    /**
     * String representation of the Grid
     * @return String object where 2D grid is represented. If value is larger than 99 return & character.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                sb.append("...");
            }
            sb.append(".\n");

            for (int x = 0; x < cols; x++) {
                String val = String.valueOf(g[y][x].getValue());
                sb.append(".");
                if (Integer.parseInt(val) > 99)
                    val = "&";
                else if (Integer.parseInt(val) == -1)
                    val = " ";
                sb.append(val);
                if (val.length() == 1)
                    sb.append(" ");
            }
            sb.append(".\n");
        }

        for (int x = 0; x < cols; x++) {
            sb.append("...");
        }
        sb.append(".\n");

        return sb.toString();
    }

    /*
     * Test grid functionality
     */

    public static void main(String[] args) throws UnsupportedEncodingException {
        Grid g = new Grid(3, 3);

        System.out.println("Edges");
        int i = 1;
        for (Edge e : g.getEdges()) {
            System.out.println((i++) + ": " + e);
        }

        i = 1;
        System.out.println("Cells");
        for (Cell c : g.getCells()) {
            System.out.println((i++) + ": " + c);
        }

        i = 1;
        Cell c = g.getCell(1, 0);
        System.out.println("Edges from cell " + c);
        for (Edge e : c.getEdges()) {
            System.out.println((i++) + ": " + e);
        }
    }
}
