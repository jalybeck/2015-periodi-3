package fi.explorator;

/**
 * Class to do Performance tests for Floyd-Warshall, Dijkstra and A* pathfinding algorithms.
 * At the moment tests are being done to static map, where there are about 33% blocked cells.
 * Cell count is 2304 and number of paths being resolved is 100.
 * 
 * It might be good idea in the future to make cell count to rise 
 * progressively as well as path count, to get better idea of the performance of the algorithms.
 */
public class PerformanceTests {
    final int GRID_SIZE_IN_PIXELS = 20;
    final int RES_X = 1280;
    final int RES_Y = 720;
    
    final int TILES_X = RES_X / GRID_SIZE_IN_PIXELS;
    final int TILES_Y = RES_Y / GRID_SIZE_IN_PIXELS;
    
    int NUM_PATHS = 100; 
    
    final int BLOCKED_WEIGHT = 100;
    
    private Grid grid;
    private Cell start;
    private Cell goal;
    
    private PathFinder pf_fw;
    private PathFinder pf_di;
    private PathFinder pf_as;
    private PathFinder pf;
    
    public PerformanceTests() {
        grid = new Grid(TILES_Y, TILES_X);
        pf_fw = PathFinder.createPathFinder(PathFinderType.FLOYD_WARSHALL, grid, start, goal);
        pf_fw.setBlockedWeight(BLOCKED_WEIGHT);
        
        pf_di = PathFinder.createPathFinder(PathFinderType.DIJKSTRA, grid, start, goal);
        pf_di.setBlockedWeight(BLOCKED_WEIGHT);
        
        pf_as = PathFinder.createPathFinder(PathFinderType.A_STAR, grid, start, goal);
        pf_as.setBlockedWeight(BLOCKED_WEIGHT);            

        //Randomize blocked cells
        int numBlocked = (TILES_Y * TILES_X) / 3;
        for(int i=0;i<numBlocked;i++) {
            Cell c = grid.getCell((int)Math.round(Math.random() * (TILES_Y - 1)), 
                                  (int)Math.round(Math.random() * (TILES_X - 1)));
            
            c.setValue(BLOCKED_WEIGHT);
        }        
    }
    
    public void measureFloydWarshall() {
        float cumulativeTimeInSeconds = 0;
        
        pf = pf_fw;
        
        for(int i=0;i<NUM_PATHS;i++) {
            do {
                start = grid.getCell((int)Math.round(Math.random() * (TILES_Y - 1)), 
                                     (int)Math.round(Math.random() * (TILES_X - 1)));
            } while(start.getValue() == BLOCKED_WEIGHT);
            
            do {
                goal = grid.getCell((int)Math.round(Math.random() * (TILES_Y - 1)), 
                                    (int)Math.round(Math.random() * (TILES_X - 1)));            
            } while(goal.getValue() == BLOCKED_WEIGHT);
            
            pf.setStart(start);
            pf.setGoal(goal);
            
            long startTime = System.currentTimeMillis();
            while (!pf.pathFound()) {
                pf.findPathStep();
            }
            
            Path path = pf.getPath();
            //System.out.println(path);
            cumulativeTimeInSeconds += ((System.currentTimeMillis() - startTime) / 1000.0);
        }
        
        System.out.println(NUM_PATHS+" paths resolved using Floyd-Warshall in "+cumulativeTimeInSeconds+" secs.");
    }
    
    public void measureDijkstra() {
        float cumulativeTimeInSeconds = 0;
        
        pf = pf_di;
        
        for(int i=0;i<NUM_PATHS;i++) {
            do {
                start = grid.getCell((int)Math.round(Math.random() * (TILES_Y - 1)), 
                                     (int)Math.round(Math.random() * (TILES_X - 1)));
            } while(start.getValue() == BLOCKED_WEIGHT);
            
            do {
                goal = grid.getCell((int)Math.round(Math.random() * (TILES_Y - 1)), 
                                    (int)Math.round(Math.random() * (TILES_X - 1)));            
            } while(goal.getValue() == BLOCKED_WEIGHT);
            
            pf.setStart(start);
            pf.setGoal(goal);
            
            long startTime = System.currentTimeMillis();
            pf.resetPathFound();
            while (!pf.pathFound()) {
                pf.findPathStep();
            }
            
            Path path = pf.getPath();
            //System.out.println(path);
            cumulativeTimeInSeconds += ((System.currentTimeMillis() - startTime) / 1000.0);
        }
        
        System.out.println(NUM_PATHS+" paths resolved using Dijkstra in "+cumulativeTimeInSeconds+" secs.");
        
    }
    
    public void measureAStar() {
        float cumulativeTimeInSeconds = 0;
        
        pf = pf_as;
        
        for(int i=0;i<NUM_PATHS;i++) {
            do {
                start = grid.getCell((int)Math.round(Math.random() * (TILES_Y - 1)), 
                                     (int)Math.round(Math.random() * (TILES_X - 1)));
            } while(start.getValue() == BLOCKED_WEIGHT);
            
            do {
                goal = grid.getCell((int)Math.round(Math.random() * (TILES_Y - 1)), 
                                    (int)Math.round(Math.random() * (TILES_X - 1)));            
            } while(goal.getValue() == BLOCKED_WEIGHT);
            
            pf.setStart(start);
            pf.setGoal(goal);
            
            long startTime = System.currentTimeMillis();
            pf.resetPathFound();
            while (!pf.pathFound()) {
                pf.findPathStep();
            }
            
            Path path = pf.getPath();
            //System.out.println(path);
            cumulativeTimeInSeconds += ((System.currentTimeMillis() - startTime) / 1000.0);
        }
        
        System.out.println(NUM_PATHS+" paths resolved using A* in "+cumulativeTimeInSeconds+" secs.");
        
    }
    
    public static void main(String[] args) {
        PerformanceTests perf = new PerformanceTests();
        System.out.println("Cells in grid: "+perf.TILES_X * perf.TILES_Y);
        int count = 1;
        while(count++ <= 10) {
            perf.NUM_PATHS = 100 * (count-1);
            perf.measureFloydWarshall();
            perf.measureDijkstra();
            perf.measureAStar();
        }
    }
}