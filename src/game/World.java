package game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.lwjgl.opengl.Display;

import entities.*;

public class World {

    private Entity[][] grid;
    private int countw;
    private int counth;
    
    TextureManager grass = new TextureManager("res/Background/grass.png");
    TextureManager ob = new TextureManager("res/Background/Bricks.png");
    Texture t = ob.getTheTexture();

    private boolean drawEdges;
    private boolean drawRandomOb;

    Random rand = new Random();
    HashMap<Entity, Boolean> hm = new HashMap<Entity, Boolean>();

    public static class Cell implements Comparable<Cell>
    {
        private int row;
        private int col;
        private World world;
        
        public Cell(int row, int col, World w)
        {
            this.row = row;
            this.col = col;
            this.world = w;
        }
        
        public int getRow() { return row; }
        public int getCol() { return col; }
        
        public String toString() 
        {
        	return "Cell[row="+row+"; col="+col+"]";
        }
        
        public int compareTo(Cell other) 
        {
        	if (row < other.row) return -1;
        	if (row > other.row) return 1;
        	if (col < other.col) return -1;
        	if (col > other.col) return 1;
        	return 0;
        }
    }


    public World(int countw, int counth) throws IOException
    {
        this.countw = countw;
        this.counth = counth;
        grid = new Entity[counth][countw];
        drawEdges = false;
        
        float dx = Display.getWidth() / countw;
        float dy = Display.getHeight() / counth;
        for (int i=0; i<countw; i++) {
        	for (int j=0; j<counth; j++) {
        		drawRandomOb = obstacleChance();
//        		hm.put(grid[i][j], drawRandomOb);
        		if (drawRandomOb) {
//        			this.set(this, new Obstacle(t, (int) (c*dx), (int) (r*dy), (int) dx, (int) dy));
        		}
        	}
        }
        // drawStuff(countw, counth);
    }
    
    public void drawStuff(int cw, int ch) {
    	float dx = Display.getWidth() / cw;
        float dy = Display.getHeight() / ch;
        
        ob.drawAt(4*dx, 4*dy, dx, dy);
        for (int c=0; c<cw; c++) {
        	for (int r=0; r<ch; r++) {
        		if (rand.nextFloat()<.25 && grid[r][c] == null) { 
        			// grid[r][c] = drawObstacle((int)(c*dx), (int)(r*dy), (int)dx, (int)dy);
        			set(cellAt(r,c), drawObstacle(c,r, (int) dx, (int) dy));
        			// ob.drawAt(c*dx, r*dy, dx, dy);
        		}
        		else {
        			grass.drawAt(c*dx, r*dy, dx, dy);
        		}
        	}
        }
    }

    public void clear()
    {
        for (int c=0; c<countw; c++)
        {
            for (int r=0; r<counth; r++)
            {
                grid[r][c]=null;
            }
        }
    }

    public void set(Cell cell, Entity e)
    {
    	int r = cell.getRow();
    	int c = cell.getCol();
        if (r >=0 && r < counth && c >= 0 && c < countw)
        {
            grid[r][c] = e;
        }
    }
//    public void set(int r, int c, Entity e)
//    {
//    	if (r >=0 && r < counth && c >= 0 && c < countw)
//    	{
//    		grid[r][c] = e;
//    	}
//    }

    public void setDrawEdges(boolean flag)
    {
        drawEdges = flag;
    }

    public Cell cellAtCoord(float x, float y)
    {
        float dx = Display.getWidth() / countw;
        float dy = Display.getHeight() / counth;

        int col = (int) (x/dx);
        int row = (int) (y/dy);

        return new Cell(row,col,this);
    }

    public void update(float delta) throws IOException
    {
        for (int c=0; c<countw; c++)
        {
            for (int r=0; r<counth; r++)
            {
                if (grid[r][c] != null)
                {
                    grid[r][c].update(delta);
                }
            }
        }
    }
    
    
    
    public Cell cellAt(int row, int col) {
    	return new Cell(row, col, this);
    }
    public Cell randomizedCell() 
    {
    	Random rand = new Random();
    	int x = rand.nextInt(Display.getWidth());
    	int y = rand.nextInt(Display.getHeight());
    	Cell randomCell = cellAtCoord(x,y);
    	return randomCell;
    }
    
    
    
    public Obstacle drawObstacle(int x, int y, int dx, int dy) {	
    	
    	return new Obstacle(t, x*dx, y*dy, dx, dy);
    }
    
    public boolean obstacleChance() {
    	Random rr = new Random();
    	if (rr.nextFloat()<.25f) return true;
    	else return false;
    }
    
    
    
    

    public void draw()
    {
        float dx = Display.getWidth() / countw;
        float dy = Display.getHeight() / counth;
        
        for (int c=0; c<countw; c++)
        {
            for (int r=0; r<counth; r++)
            {
            	Entity key = grid[r][c];
            	if (hm.get(key)) ob.drawAt(c*dx, r*dy, dx, dy);
            	
//            	tex.drawAt(c*dx, r*dy, dx, dy);
//            	Random rand = new Random();
//            	if (rand.nextFloat() < .25) ob.drawAt(c*dx, r*dy, dx, dy);
            	
                if (grid[r][c] != null)
                {
                    grid[r][c].drawAt(c*dx, r*dy, dx, dy);
                }

                if (drawEdges)
                {
                	float x = c*dx;
                    float y = r*dy;
                	
                    GL11.glColor3f(1, 1, 1);
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                    GL11.glBegin(GL11.GL_QUADS);

                    GL11.glVertex2f(x,y);
                    GL11.glVertex2f(x+dx,y);
                    GL11.glVertex2f(x+dx,y+dy);
                    GL11.glVertex2f(x,y+dy);

                    GL11.glEnd();
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);                    
                }
            }
        }
    }
    
    
    public List<Cell> findPath(Cell start, Cell end)
    {
        System.out.println("looking for path from " + start + " to " + end);

        class Data
        {
            public double cost;
            public double est;
            public Cell prev;

            public Data(double cost, double est, Cell prev)
            {
                this.cost = cost;
                this.est  = est;
                this.prev = prev;
            }

            public double estimate()
            {
                return cost + est;
            }
        }

        TreeMap<Cell, Data> costs = new TreeMap<>();
        TreeSet<Cell> open = new TreeSet<Cell>();
        TreeSet<Cell> closed = new TreeSet<Cell>();

        costs.put(start, new Data(0, 0, null));
        open.add(start);

        while (open.size() > 0) {

        	Cell current = null;
        	double min = 999999999999.0;
        	
			for (Cell c: open) {
				
				double cEst = costs.get(c).estimate();

				if (cEst < min) {
					
					current = c;
					min = cEst;
				}
			}
        	
        	if (current.compareTo(end) == 0) {

        		System.out.println("found the path");
        		
        		List<Cell> path = new LinkedList<Cell>();
        		
        		while (current.compareTo(start) != 0) {
        			path.add(current);
        			current = costs.get(current).prev;
        			// this.set(current, new PathBox());
        		}
        		path.add(current);
        		
        		return path;
        	}
        	
        	open.remove(current);
        	closed.add(current);
        	
        	for (Cell n : getNeighbors(current, end)) {
        		
        		double tmpcost = costs.get(current).cost + 1;
        		
        		if (closed.contains(n)) {

        			if (tmpcost >= costs.get(n).cost) {
        				continue;
        			}
        		}
        		
        		if (!(open.contains(n)) || tmpcost < costs.get(n).cost) {
        			
        			double cost = tmpcost;
        			double est = calculateDistance(n, end);
        			costs.put(n, new Data(cost, est, current));
        			open.add(n);
        			// set(n, new NbrBox());
        			
        		}
        	} // end neighbors loop
        } // end while loop

        return null;

    } // end findPath
    
    
    public double calculateDistance(Cell cell, Cell end) {
    	double result = Math.abs( (cell.getCol() - end.getCol() ) +
    			Math.abs( (cell.getRow() - end.getRow()) ));
    	return result;
    }



    public List<Cell> getNeighbors(Cell c, Cell include)
    {
        List<Cell> nbrs = new LinkedList<Cell>();

        int row = c.getRow();
        int col = c.getCol();
        
        if (include != null) 
        {
        	if (Math.abs(include.getRow()-row) + 
        			Math.abs(include.getCol()-col) == 1) 
        	{
        		nbrs.add(include);
        	}
        }

        if (row - 1 >= 0 && grid[row-1][col] == null)
            nbrs.add(new Cell(row-1,col,this));

        if (row + 1 < counth && grid [row+1][col] == null)
            nbrs.add(new Cell(row+1,col,this));

        if (col - 1 >= 0 && grid[row][col-1] == null)
            nbrs.add(new Cell(row,col-1,this));
        
        if (col +1 < countw && grid[row][col+1] == null)
            nbrs.add(new Cell(row,col+1,this));


        return nbrs;
    }

    
    public float getMovementCost(int x0, int y0, int x1, int y1) {
    	
    	float xdiff = x1 - x0;
    	float ydiff = y1 - y0;
    	
    	float result = (float) (Math.sqrt( (xdiff*xdiff) + (ydiff*ydiff) ));
    	return result;
    }
    
        
}