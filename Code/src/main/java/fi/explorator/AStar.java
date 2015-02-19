package fi.explorator;

import fi.explorator.datastructures.ArrayList;

import fi.explorator.datastructures.HeapNode;
import fi.explorator.datastructures.PriorityQueue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A* pathfinding algorithm implementation.
 *
 * This implementation uses PriorityQueue for open set.
 *
 */
public class AStar extends PathFinder {
    /**
     * Keeps track of cost of the path.
     */
    private int cost[];

    /**
     * This holds the path from start to goal
     */
    private int path[];

    @Override
    public void setGrid(Grid g) {
        super.setGrid(g);

    }

    @Override
    public void findPathStep() {
        if (goal == null || start == null)
            return;

        if (goal.getValue() == blockedWeight || start.getValue() == blockedWeight) {
            pathFound = true;
            return;
        }

        PriorityQueue<Cell> open = new PriorityQueue<Cell>();

        cost = new int[this.numVertices];
        path = new int[this.numVertices];

        cost[start.getOrderNumber()] = 0;
        path[start.getOrderNumber()] = start.getOrderNumber();

        Cell startCopy = new Cell(start);
        open.add(new HeapNode<Cell>(cost[start.getOrderNumber()], startCopy));

        while (!open.isEmpty()) {
            HeapNode<Cell> current = open.poll();

            if (current.getPayload().getOrderNumber() == goal.getOrderNumber()) {
                break;
            }

            List<Cell> adj = getAdjacentCells(grid.getCell(current.getPayload().getOrderNumber()));
            for (Cell c : adj) {
                int newCost = cost[current.getPayload().getOrderNumber()] + c.getValue();
                if (cost[c.getOrderNumber()] == 0 || newCost < cost[c.getOrderNumber()]) {
                    path[c.getOrderNumber()] = current.getPayload().getOrderNumber();
                    cost[c.getOrderNumber()] = newCost;
                    int fCost = newCost + heuristicFunction(c);
                    open.add(new HeapNode<Cell>(fCost, c));
                }
            }
        }

        //Eventho path might not actually be found, we stop iterating in main loop.
        pathFound = true;
    }

    /**
     * Construct Path object from start cell to goal cell, by iterating over path array, where
     * we gather path in findPathStep() method.
     * @return Path object from start to goal cell.
     */
    @Override
    public Path getPath() {
        if (start == null || goal == null || goal.getValue() == blockedWeight || start.getValue() == blockedWeight)
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

    /**
     * Heuristic function which depends on multiple factors.
     * For example how many directions you can move in a grid.
     * In this case we are moving only 4 directions.
     *
     * @link http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html
     *
     * @param c current cell which we are using in calculations against goal cells position
     * @return
     */
    private int heuristicFunction(Cell c) {
        int dx = Math.abs(c.getX() - goal.getX());
        int dy = Math.abs(c.getY() - goal.getY());
        return (dx + dy);
    }
}
