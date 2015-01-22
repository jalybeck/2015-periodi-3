final int GRID_SIZE = 20;

int[][] GRID;
Tuple2 mC;
Tuple2 start,end;

boolean startDragged, endDragged, mouseLeftDown;

Tuple2 gridCoord(int x, int y) {
  return new Tuple2(x / GRID_SIZE,y / GRID_SIZE);
}

Tuple2 gridCoord(Tuple2 t) {
  t.a /= GRID_SIZE;
  t.b /= GRID_SIZE;
  return t;
}

void setup() {
  size(640,360);
  frameRate(30);
  start = new Tuple2(-1,-1);
  end   = new Tuple2(-1,-1);
  mC    = new Tuple2();
  GRID = new int[height/GRID_SIZE][width/GRID_SIZE];
}

void drawGrid() {
    stroke(128,128,128);
    for(int y=0;y<height;y+=GRID_SIZE) {
      for(int x=0;x<width;x+=GRID_SIZE) {
        int gx = x/GRID_SIZE;
        int gy = y/GRID_SIZE; 
        if(GRID[gy][gx] == 1)
          fill(190,190,190);
        else
          noFill();
       
        if(gx == start.a && gy == start.b && !startDragged)
          fill(25,190,25);
        if(gx == end.a && gy == end.b)
          fill(190,25,25);
        
        rect(x,y,GRID_SIZE,GRID_SIZE);
      }
    }  
}

void mousePressed() {
  mC = gridCoord(mouseX,mouseY);
  
  if(mouseButton == LEFT) {
    if( (mC.a != start.a && mC.b != start.b) || 
        (mC.a != end.a && mC.b != end.b))
      GRID[mC.b][mC.a] = 1;//GRID[mC.b][mC.a] ^ 1;
      mouseLeftDown = true;
  }
  if(mouseButton == RIGHT) {
    GRID[mC.b][mC.a] = 0;
    if(mC.a == start.a && mC.b == start.b)
      start.a = start.b = -1;
    if(mC.a == end.a && mC.b == end.b)
      end.a = end.b = -1;
  }
    
}

void mouseReleased() {
  startDragged = false;
  endDragged = false;
  mC = gridCoord(mouseX,mouseY);
  if(mouseButton == LEFT) {
    mouseLeftDown = false;
  }
}

void mouseDragged() {
  /*
   mC = gridCoord(mouseX,mouseY);
   */
  //println("mouseDragged at ("+mouseX+","+mouseY+")");
  /*if(mouseButton == LEFT) {
    if(mC.a == start.a && mC.b == start.b) 
      start.a = mouseX;
      start.b = mouseY;
      startDragged = true;
  }*/
}

void keyPressed() {
  if(key == 'a') {
    start = gridCoord(mouseX,mouseY);
  } else if(key == 'l') {
    end = gridCoord(mouseX,mouseY);
  }
}

void draw() {
    background(255,255,255);
    drawGrid();
    
    if(startDragged) {
      fill(25,190,25);
      rect(mouseX,mouseY,GRID_SIZE,GRID_SIZE);
    }

}
