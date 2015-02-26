
Cell checkGetCell(int x, int y, Grid g) {
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


int checkActive(ArrayList<Cell> list) {
    int numActive = 0;
    for(Cell n : list) {
      if(n == null) continue;
      if(n.getValue() == BLOCKED_WEIGHT)
        numActive++;
    }
  
    return numActive;  
}

void iterateLife(Grid grid) {
  
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
