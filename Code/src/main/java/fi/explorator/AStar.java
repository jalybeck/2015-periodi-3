package fi.explorator;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
/**
 * A* pathfinding algorithm implementation.
 * 
 * This implementation uses PriorityQueue for open set and 
 * HashMap for closed set.
 * 
 */
public class AStar extends PathFinder {
    private boolean pathFound;
    /**
     * Keeps track of cost of the path.
     */
    private int cost[];  
    /**
     * Keeps track of total cost of path including heuristics
     */
    private int totCost[]; 
    /**
     * This holds the path from start to goal
     */
    private int path[];
    
    @Override
    public void setGrid(Grid g) {
        super.setGrid(g);
        cost = new int[this.numVertices];
        totCost = new int[this.numVertices];        
        path = new int[this.numVertices];
    }
    
    @Override
    public void findPathStep() {
        PriorityQueue<Cell> open = new PriorityQueue<Cell>();
        HashMap<Integer, Cell> closed = new HashMap<Integer, Cell>();
        
        cost[start.getOrderNumber()] = 0;
        totCost[start.getOrderNumber()] = heuristicFunction(start);
        path[start.getOrderNumber()] = start.getOrderNumber();
        
        open.add(start);
        
        while(!open.isEmpty()) {
            Cell current = open.poll();
            
            closed.put(current.getOrderNumber(),current);
            
            if(current.getOrderNumber() == goal.getOrderNumber()) {
                pathFound = true;
                return;
            }
            
            List<Cell> adj = getAdjacentCells(grid.getCell(current.getOrderNumber()));
            for (Cell c : adj) {
                if(closed.containsKey(c.getOrderNumber()))
                    continue;
                
                int newCost = cost[current.getOrderNumber()] + c.getValue();
                if(!open.contains(c) || newCost < cost[c.getOrderNumber()]) {
                    path[c.getOrderNumber()] = current.getOrderNumber();
                    cost[c.getOrderNumber()] = newCost;
                    totCost[c.getOrderNumber()] = cost[c.getOrderNumber()] + heuristicFunction(c);
                    if(!open.contains(c))
                        open.offer(c);
                }
            }
        }
    }

    @Override
    public boolean pathFound() {
        return pathFound;
    }

    @Override
    public void resetPathFound() {
        pathFound = false;
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
        if (path[goal.getOrderNumber()] == MAX_NUM || path[goal.getOrderNumber()] == 0)
            return null;

        Path p = new Path();

        int u = start.getOrderNumber();
        int v = goal.getOrderNumber();
        while (u != v) {
            v = path[v];
            p.add(grid.getCell(v));
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
        return 1 * (dx + dy);
    }
}
