import fi.explorator.*;

int GRID_SIZE = 20;
final int BLOCKED_WEIGHT = 100;
final int PASSABLE_WEIGHT = 1;

Grid grid;
Cell start,goal;
PathFinder pf_fw,pf_di, pf;
Path path;
Tuple2 mC;

Tuple2 gridCoord(Tuple2 t) {
  t.a /= GRID_SIZE;
  t.b /= GRID_SIZE;
  return t;
}

Tuple2 gridCoord(int x, int y) {
  return gridCoord(new Tuple2(x,y));
}

void setup() {
  size(640,360);
  frameRate(30);
  mC    = new Tuple2();
  println(height/GRID_SIZE+","+width/GRID_SIZE);
  grid = new Grid(height/GRID_SIZE,width/GRID_SIZE);  
  pf_fw = PathFinder.createPathFinder(PathFinderType.FLOYD_WARSHALL, grid, start, goal);
  pf_fw.setBlockedWeight(BLOCKED_WEIGHT);
  pf_di = PathFinder.createPathFinder(PathFinderType.DIJKSTRA, grid, start, goal);
  pf_di.setBlockedWeight(BLOCKED_WEIGHT);  
  pf = pf_fw;
  
}

void drawGrid() {
    stroke(128,128,128);
    for(int y=0;y<height;y+=GRID_SIZE) {
      for(int x=0;x<width;x+=GRID_SIZE) {
        int gx = x/GRID_SIZE;
        int gy = y/GRID_SIZE; 
        
        if(grid.getCell(gy,gx).getValue() == BLOCKED_WEIGHT)
          fill(190,190,190);
        else
          noFill();
          
        if(path != null) {
          for(Cell c : path.getCells()) {
            if(gx == c.getX() && gy == c.getY())
              fill(90,90,230);
          }
        }
        
        if(start != null && gx == start.getX() && gy == start.getY())
          fill(25,190,25);
        if(goal != null && gx == goal.getX() && gy == goal.getY())
          fill(190,25,25);
        

        
        rect(x,y,GRID_SIZE,GRID_SIZE);
      }
    }  
}

void keyPressed() {
  if(key == 'a') {
    Tuple2 gCoord = gridCoord(mouseX,mouseY);
    start = grid.getCell(gCoord.b, gCoord.a);
    pf_di.setStart(start);
    pf_fw.setStart(start);
    
  } else if(key == 'l') {
    Tuple2 gCoord = gridCoord(mouseX,mouseY);
    goal = grid.getCell(gCoord.b, gCoord.a);
    pf_di.setGoal(goal);
    pf_fw.setGoal(goal);
  }
  
  if(key == 'd')
    pf = pf_di;
  else if(key == 'f')
    pf = pf_fw;
}

void processMouseInputs() {
   if(mousePressed) {
       mC = gridCoord(mouseX, mouseY);
       
       if( (mC.b >= 0 && mC.b < height / GRID_SIZE) &&
           (mC.a >= 0 && mC.a < width / GRID_SIZE)) {
           
           if(mouseButton == LEFT) {
                   grid.getCell(mC.b,mC.a).setValue(BLOCKED_WEIGHT);
                   pf_fw.resetPathFound();
                   pf_di.resetPathFound();  
           }
           if(mouseButton == RIGHT) {
             grid.getCell(mC.b,mC.a).setValue(PASSABLE_WEIGHT);
             pf_fw.resetPathFound();
             pf_di.resetPathFound();  
            if(start != null && mC.a == start.getX() && mC.b == start.getY())
              start = null;
            if(goal != null && mC.a == goal.getX() && mC.b == goal.getY())
              goal = null;
           }
       }
   }
}

void draw() {
    background(255,255,255);
    processMouseInputs();
    if(start != null && goal != null) {
      while (!pf.pathFound()) {
          pf.findPathStep();
      }
      path = pf.getPath();
    }
    drawGrid();

}
