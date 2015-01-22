package fi.explorator;

/**
 * Edge between 2 Cell objects.
 * This class contains all characteristics of the edge,
 * for example edge weights are stored here.
 */
public class Edge {
    private Cell a;
    private Cell b;
    private int weight;

    public Edge(Cell a, Cell b, int weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    /**
     * Set edge weight.
     * @param weight clamped to be positive integer.
     */
    public void setWeight(int weight) {
        if (weight >= 0)
            this.weight = weight;
    }

    /**
     * Get edge weight.
     * @return positive integer
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Get edges starting cell.
     * @return Cell object
     */
    public Cell getCellStart() {
        return a;
    }

    /**
     * Get edges ending cell.
     * @return Cell object
     */
    public Cell getCellEnd() {
        return b;
    }

    /**
     * String representation of the Edge
     * @return String which consists Cell a, Cell b and weight
     */
    public String toString() {
        if (a == null || b == null)
            return "";

        String s = "";

        if (a != null)
            s += a.toString();

        if (b != null)
            s += b.toString();

        return a.toString() + "-" + b.toString() + " = " + weight;
    }
}
