import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AStarTest extends Scene {
    
    private World w;
    Player p1;
    Player p2;

    public static class MovingBox extends Entity {
    	private float r, g, b;
    	private World w;
    	private World.Cell current;

    	public MovingBox(World w, float r, float g, float b) {
    		this.r = r;
    		this.g = g;
    		this.b = b;
    		this.w = w;
    	}

    	public void move(int row, int col) {
    		w.set(current, null);
    		current = new World.Cell(current.getRow() + row, current.getCol() + col, w);
    		w.set(current, this);
    	}

    	public void drawAt(float x, float y, float dx, float dy) {
    		GL11.glColor3f(r, g, b);
    		GL11.glBegin(GL11.GL_QUADS);
    		GL11.glVertex2f(x, y);
    		GL11.glVertex2f(x + dx, y);
    		GL11.glVertex2f(x + dx, y + dy);
    		GL11.glVertex2f(x, y + dy);
    		GL11.glEnd();
    	}

    	public void setCell(int row, int col) {
    		if (current != null) {
    			w.set(current, null);
    		}
    		current = new World.Cell(row, col, w);
    		w.set(current, this);
    	}

    	public World.Cell getCell() {
    		return current;
    	}
    }

    private static class HumanPlayer implements Player {
    	private MovingBox mb;

    	public HumanPlayer(MovingBox mb) {
    		this.mb = mb;
    	}

    	public void update(float delta) {
    		while (Keyboard.next()) {
    			if (Keyboard.getEventKeyState()) {
    			
    				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
    					mb.move(-1, 0);
    				}
    				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
    					mb.move(1, 0);
    				}
    				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
    					mb.move(0, -1);
    				}
    				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
    					mb.move(0, 1);
    				}
    			}
    		}
    	}
    }

	private static class AIPlayer implements Player {
		private MovingBox mb;
		private MovingBox human;
		private float accumulated = 0;
		private float threshold = 1000;
		private World w;

		public AIPlayer(MovingBox mb, MovingBox human, World w) {
			this.mb = mb;
			this.human = human;
			this.w = w;
		}

		public void update(float delta) {

			accumulated += delta;
			
			if (accumulated > threshold) {
				
				accumulated -= threshold;
				// List<World.Cell> path = w.findPath(mb.getCell(), human.getCell());
				// List<World.Cell> path = w.findPath(mb.getCell(), new World.Cell(2, 2, w));
				List<World.Cell> path = w.findPath(mb.getCell(), human.getCell());
				
				if (path != null && path.size() > 1) {
					path.remove(0);
					World.Cell next = path.remove(0);
					System.out.println("moving to " + next); 
					mb.setCell(next.getRow(), next.getCol());
				}
				else { System.out.println("No path found"); }
			}
		}
	}

	public AStarTest() {
		
		w = new World(10, 10);
		
		MovingBox hero = new MovingBox(w, 1f, 0f, 0f); 
		hero.setCell(1,1);
		
		MovingBox enemy = new MovingBox(w, 0f, 1f, 0f); 
		enemy.setCell(8,8);
		
		p1 = new HumanPlayer(hero);
		p2 = new AIPlayer(enemy, hero, w);
	}

	public boolean drawFrame(float delta) throws IOException {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		w.setDrawEdges(true);
		p1.update(delta);
		p2.update(delta);
		w.update(delta); 
		w.draw(); 
		return true;
	}

    
    
    
    
    
    
    
    
    
    
    
	{
//    // ------------------------------------- //
//    public void drawObstacles() {
//    	World.Cell obstacle = w.randomizedCell();
//    	w.set(obstacle.getRow(), obstacle.getCol(), new Box());
//    }
//
//    public void drawStartingCells() {
//    	w.set(1, 1, new StartingBox());
//    	w.set(18, 18, new DestinationBox());
//    }
//
//    public void drawRandomCell(boolean flag) {
//
//    	if (flag == true) {
//    		w.set(sc.getRow(), sc.getCol(), new StartingBox());
//    	}
//    	else {
//    		w.set(dc.getRow(), dc.getCol(), new DestinationBox());    		
//    	}
//    }
//    // ------------------------------------- //
	}
    
//    public boolean drawFrame(float delta)
//    {
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
//        w.setDrawEdges(true);
//        
//        while (Mouse.next()) 
//        {
//            if (Mouse.getEventButtonState()) 
//            {
//                if (Mouse.getEventButton() == 0)
//                {
//                    // left
//                    World.Cell c = w.cellAtCoord(Mouse.getEventX(), Display.getHeight()-Mouse.getEventY());
//                    w.set(c.getRow(), c.getCol(), new Box());
//                    
//                }
//                if (Mouse.getEventButton() == 1)
//                {
//                    // right
//                }
//            }
//        }
//
//        end = w.cellAtCoord(Mouse.getX(), Display.getHeight()-Mouse.getY());
//        
//        // if (path != null && )
//
//        w.draw();
//        return true;
//    }


}