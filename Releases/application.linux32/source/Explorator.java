import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import fi.explorator.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Explorator extends PApplet {




int GRID_SIZE = 20;
final int BLOCKED_WEIGHT = 100;
final int PASSABLE_WEIGHT = 1;

Grid grid;
Cell start,goal;
PathFinder pf_fw,pf_di,pf_astar,pf;
Path path;
Tuple2 mC;

boolean reportPathFound;

boolean simulateLife;

public Tuple2 gridCoord(Tuple2 t) {
  t.a /= GRID_SIZE;
  t.b /= GRID_SIZE;
  return t;
}

public Tuple2 gridCoord(int x, int y) {
  return gridCoord(new Tuple2(x,y));
}

public void setup() {
  size(1280,720); //640,360
  frameRate(30);
  mC    = new Tuple2();
  println(height/GRID_SIZE+","+width/GRID_SIZE);
  grid = new Grid(height/GRID_SIZE,width/GRID_SIZE);  
  pf_fw = PathFinder.createPathFinder(PathFinderType.FLOYD_WARSHALL, grid, start, goal);
  pf_fw.setBlockedWeight(BLOCKED_WEIGHT);
  pf_di = PathFinder.createPathFinder(PathFinderType.DIJKSTRA, grid, start, goal);
  pf_di.setBlockedWeight(BLOCKED_WEIGHT);
  pf_astar = PathFinder.createPathFinder(PathFinderType.A_STAR, grid, start, goal);
  pf_astar.setBlockedWeight(BLOCKED_WEIGHT);  
  pf = pf_di;
  
  frame.setTitle("Explorator - Dijkstra");
  
}

public void drawGrid() {
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

public void keyPressed() {
  if(key == 'a') {
    Tuple2 gCoord = gridCoord(mouseX,mouseY);
    start = grid.getCell(gCoord.b, gCoord.a);
    pf_fw.setStart(start);    
    pf_di.setStart(start);
    pf_di.resetPathFound();
    pf_astar.setStart(start);
    pf_astar.resetPathFound();
    reportPathFound = true;
    
  } else if(key == 'l') {
    Tuple2 gCoord = gridCoord(mouseX,mouseY);
    goal = grid.getCell(gCoord.b, gCoord.a);
    pf_fw.setGoal(goal);
    pf_di.setGoal(goal);
    pf_di.resetPathFound();
    pf_astar.setGoal(goal);
    pf_astar.resetPathFound();
    reportPathFound = true;
  }
  
  if(key == '1') {
    pf = pf_fw;
    println("Floyd-Warshall");
    frame.setTitle("Explorator - Floyd-Warshall");
    reportPathFound = true;
  }    
  else if(key == '2') {
    pf = pf_di;
    println("Dijkstra");
    frame.setTitle("Explorator - Dijkstra");
    reportPathFound = true;
  }
  else if(key == '3') {
    pf = pf_astar;
    println("A*");
    frame.setTitle("Explorator - A*");
    reportPathFound = true;
  } else if(key == 'g') {
    simulateLife = !simulateLife;
  } else if(key == 'c') {
    for(Cell c : grid.getCells()) {
      c.setValue(PASSABLE_WEIGHT);
    }
  }
}

public void processMouseInputs() {
   if(mousePressed) {
       simulateLife = false;
       mC = gridCoord(mouseX, mouseY);
       
       if( (mC.b >= 0 && mC.b < height / GRID_SIZE) &&
           (mC.a >= 0 && mC.a < width / GRID_SIZE)) {
           
           if(mouseButton == LEFT) {
                   grid.getCell(mC.b,mC.a).setValue(BLOCKED_WEIGHT);
                   pf_fw.resetPathFound();
                   pf_di.resetPathFound();  
                   pf_astar.resetPathFound();
            if(start != null && mC.a == start.getX() && mC.b == start.getY()) {
              start = null;
              path = null;
            }
            if(goal != null && mC.a == goal.getX() && mC.b == goal.getY()) {
              path = null;
              goal = null;
            }              
           }
           if(mouseButton == RIGHT) {
             grid.getCell(mC.b,mC.a).setValue(PASSABLE_WEIGHT);
             pf_fw.resetPathFound();
             pf_di.resetPathFound();
             pf_astar.resetPathFound();
            if(start != null && mC.a == start.getX() && mC.b == start.getY()) {
              start = null;
              path = null;
            }
            if(goal != null && mC.a == goal.getX() && mC.b == goal.getY()) {
              goal = null;
              path = null;
            }
           }
       }
   }
}

public void draw() {
    background(255,255,255);
    processMouseInputs();
    if(simulateLife)  {
      iterateLife(grid);
       pf_astar.resetPathFound();
       pf_di.resetPathFound();
    }
    if(start != null && goal != null) {
      while (!pf.pathFound()) {
          pf.findPathStep();
          reportPathFound = true;
      }
      if(pf.pathFound() && reportPathFound) {
        path = pf.getPath();
        if(path != null) {
          println("path length: "+path.length());
        }
        reportPathFound = false;
      }
    }
    drawGrid();

}

public Cell checkGetCell(int x, int y, Grid g) {
  //if(y < 0 || x < 0 || x >= g.getNumColumns() || y >= g.getNumRows())
  //  return null;
    
  if(x < 0) {
    x = g.getNumColumns() - 1;
  } else if(x >= g.getNumColumns()) {
    x = 0;
  }
  
  if(y < 0) {
    y = g.getNumRows() - 1;
  } else if(y >= g.getNumRows()) {
    y = 0;
  }
  
  return g.getCell(y,x);
}


public int checkActive(ArrayList<Cell> list) {
    int numActive = 0;
    for(Cell n : list) {
      if(n == null) continue;
      if(n.getValue() == BLOCKED_WEIGHT)
        numActive++;
    }
  
    return numActive;  
}

public void iterateLife(Grid grid) {
  
  Grid g = new Grid(grid);
  
  for(Cell c : g.getCells()) {
    ArrayList<Cell> neighbours = new ArrayList<Cell>();
    neighbours.add(checkGetCell(c.getX()-1,c.getY(),g));
    neighbours.add(checkGetCell(c.getX()+1,c.getY(),g));
    neighbours.add(checkGetCell(c.getX(),c.getY()-1,g));
    neighbours.add(checkGetCell(c.getX(),c.getY()+1,g));
    
    neighbours.add(checkGetCell(c.getX()-1,c.getY()-1,g));
    neighbours.add(checkGetCell(c.getX()+1,c.getY()-1,g));
    neighbours.add(checkGetCell(c.getX()-1,c.getY()+1,g));
    neighbours.add(checkGetCell(c.getX()+1,c.getY()+1,g));
    
    
    int numActive = checkActive(neighbours);
    
    if(c.getValue() == BLOCKED_WEIGHT) {
      if(numActive < 2 || numActive > 3)
        grid.getCell(c.getY(),c.getX()).setValue(PASSABLE_WEIGHT);//c.setValue(PASSABLE_WEIGHT);
      else if(numActive == 2 || numActive == 3)
        grid.getCell(c.getY(),c.getX()).setValue(BLOCKED_WEIGHT);
    } else if(c.getValue() == PASSABLE_WEIGHT) {
      if(numActive == 3)
        grid.getCell(c.getY(),c.getX()).setValue(BLOCKED_WEIGHT);
    }
  }
}
class Tuple2 {
  int a,b;
  Tuple2(int a, int b) {
    this.a = a;
    this.b = b;
  }
  Tuple2() {
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Explorator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
