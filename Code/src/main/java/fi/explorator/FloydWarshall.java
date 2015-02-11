package fi.explorator;

/**
 * Floyd-Warshall implementation.
 * This class extends PathFinder abstract base class and implements its defined interface.
 */
class FloydWarshall extends PathFinder {
    /**
     * 2D weight matrix
     */
    private int[][] D;

    /**
     * Path matrix, which is populated same time as weight matrix
     */
    private int path[][];

    FloydWarshall() {

    }

    @Override
    public void setGrid(Grid g) {
        super.setGrid(g);
        
        D = new int[this.numVertices][this.numVertices];
        path = new int[this.numVertices][this.numVertices];

    }

    /**
     * This was intended to run single step at a time,
     * so we could visualize the algorithm step by step.
     * Floyd-Warshall algorithm behaves differently in contrast of
     * other path finding algorithms and that is why we populate whole weight and path matrices
     * here and we can simulate path finding process in getPath() method.
     */
    @Override
    public void findPathStep() {
        
        //Initialize weight matrix
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                path[i][j] = j;
                if (i == j)
                    D[i][j] = 0;
                else
                    D[i][j] = MAX_NUM;
            }
        }

        //Populate weight matrix with edge weights
        for (Edge e : grid.getEdges()) {
            Cell start = e.getCellStart();
            Cell end = e.getCellEnd();
            if(start.getValue() == blockedWeight || end.getValue() == blockedWeight)
                continue;
            D[start.getOrderNumber()][end.getOrderNumber()] = start.getValue();
            D[end.getOrderNumber()][start.getOrderNumber()] = end.getValue();
        }
        
        for (int k = 0; k < this.numVertices; k++) {
            for (int i = 0; i < this.numVertices; i++) {
                for (int j = 0; j < this.numVertices; j++) {
                    if (D[i][k] + D[k][j] < D[i][j]) {
                        D[i][j] = D[i][k] + D[k][j];
                        path[i][j] = path[i][k];
                    }
                }
            }
        }
        pathFound = true;
    }

    /**
     * This will run in step-by-step mode in busy loop,
     * so we can simulate the path finding algorithm in visualization layer.
     * @return  Path object from start cell to goal cell.
     */
    @Override
    public Path getPath() {
        if(start == null || goal == null)
            return null;
        if (path[start.getOrderNumber()][goal.getOrderNumber()] == MAX_NUM)
            return null;

        Path p = new Path();

        int u = start.getOrderNumber();
        int v = goal.getOrderNumber();
        while (u != v) {
            u = path[u][v];
            p.add(grid.getCell(u));
        }

        return p;
    }

    public void printWeightMatrix() {
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (D[i][j] == MAX_NUM)
                    System.out.print("& ");
                else
                    System.out.print(D[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*
     * Testing the Floyd-Warshall algorithm.
     *
     */

    public static void main(String[] args) throws InterruptedException {
        Grid grid = new Grid(4, 4);
        Cell start = new Cell(0, 0, 0);
        Cell goal = new Cell(2, 1, 1+2 *grid.getNumColumns() );
        PathFinder pf = PathFinder.createPathFinder(PathFinderType.FLOYD_WARSHALL, grid, start, goal);
        ((FloydWarshall)pf).printWeightMatrix();
        System.out.println();
        while (!pf.pathFound()) {
            pf.findPathStep();
        }
        ((FloydWarshall)pf).printWeightMatrix();
        
        System.out.println("Path from "+start+" to "+goal+":");
        System.out.println(pf.getPath());
    }

}
