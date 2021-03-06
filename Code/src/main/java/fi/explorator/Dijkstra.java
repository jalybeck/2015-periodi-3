package fi.explorator;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of Dijkstras pathfinding algorithm to find shortest path
 * between start and goal cells in given Grid object.
 *
 * This implementation does not use priorityqueue to find smallest distance,
 * so this might run little bit slower than the version which does use that.
 */
public class Dijkstra extends PathFinder {
    private int dist[];
    private int path[];
    private boolean visited[];

    public Dijkstra() {
        super();
    }

    @Override
    public void setGrid(Grid g) {
        super.setGrid(g);
        dist = new int[this.numVertices];
        path = new int[this.numVertices];
        visited = new boolean[this.numVertices];
    }

    /**
     * Main loop of the Dijkstras pathfinding algorithm.
     * Does not use priorityqueue.
     */
    @Override
    public void findPathStep() {
        if (goal == null || start == null)
            return;

        if (goal.getValue() == blockedWeight || start.getValue() == blockedWeight) {
            pathFound = true;
            return;
        }

        for (int i = 0; i < dist.length; i++) {
            dist[i] = MAX_NUM;
            visited[i] = false;
            path[i] = 0;
        }
        dist[start.getOrderNumber()] = 0;

        for (int i = 0; i < dist.length; i++) {
            int next = smallestCell();

            //Lets guard for indexoutofbounds exceptions
            if (next < 0)
                continue;

            visited[next] = true;

            List<Cell> adj = getAdjacentCells(grid.getCell(next));
            for (Cell c : adj) {
                int n = c.getOrderNumber();
                int d = dist[next] + (int)c.getValue();
                if (dist[n] > d) {
                    dist[n] = d;
                    path[n] = next;
                }
            }

        }
        pathFound = true;
    }

    /**
     * Find smallest cell distance, which we have not visited yet.
     *
     * @return smallest cell index to dist array
     */
    private int smallestCell() {
        int cellIndex = -1;
        int d = MAX_NUM;

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < d) {
                cellIndex = i;
                d = dist[i];
            }
        }
        return cellIndex;
    }

    /**
     * Construct Path object from start cell to goal cell, by iterating over path array, where
     * we gather path in findPathStep() method.
     * @return Path object from start to goal cell.
     */
    @Override
    public Path getPath() {
        if (start == null || goal == null)
            return null;

        if (goal.getValue() == blockedWeight || start.getValue() == blockedWeight)
            return null;

        if (path[goal.getOrderNumber()] == MAX_NUM || path[goal.getOrderNumber()] == 0)
            return null;

        Path p = new Path();

        int u = start.getOrderNumber();
        int v = goal.getOrderNumber();
        while (u != v) {
            p.add(grid.getCell(v));
            v = path[v];           
        }
        return p;
    }

    public void printArrays() {
        System.out.println("dist: " + Arrays.toString(dist));
        System.out.println("visited: " + Arrays.toString(visited));
    }

    public static void main(String[] args) throws InterruptedException {
        Grid grid = new Grid(4, 4);
        Cell start = new Cell(0, 0, 0);
        Cell goal = new Cell(2, 1, 1 + 2 * grid.getNumColumns());
        PathFinder pf = PathFinder.createPathFinder(PathFinderType.DIJKSTRA, grid, start, goal);
        ((Dijkstra)pf).printArrays();
        System.out.println();
        while (!pf.pathFound()) {
            pf.findPathStep();
        }
        ((Dijkstra)pf).printArrays();

        System.out.println("Path from " + start + " to " + goal + ":");
        System.out.println(pf.getPath());
    }
}
