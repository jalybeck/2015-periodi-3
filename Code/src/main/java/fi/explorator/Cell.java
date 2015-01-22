package fi.explorator;

/**
 * Represents single cell in 2D grid.
 */
public class Cell {
    private int x, y;

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
     * @return String which consists x and y coordinates of the cell
     */
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Set value of the cell.
     * This can be thought as cell type.
     * For example: 100 could mean that cell is blocked while 0 would be cell which is passable.
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
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

}

