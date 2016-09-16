package scenes;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;

import java.util.LinkedList;
import java.io.IOException;
import java.util.Iterator;

import entities.*;
import entities.platforms.RotatingPlatform;
import entities.platforms.StationaryPlatform;
import entities.projectiles.Projectile;
import game.*;


public class LevelOne extends Scene {

    private LinkedList<Projectile> bullets;
    private Stickman stickman;
    private Target target;
    private GroundFloor background;
    private RotatingPlatform rotatingPlatform;
    private StationaryPlatform stationaryPlatform, s1, s2;
    public int SCREEN_WIDTH = Entity.SCREEN_WIDTH;
    public int SCREEN_HEIGHT = Entity.SCREEN_HEIGHT;
    

    public LevelOne() throws IOException, SlickException
    {
        bullets = new LinkedList<>();
        stickman = new Stickman(bullets,0,300,150,150);
        target = new Target();
        background = new GroundFloor("res/Background/Bricks.png", 0, (int) (SCREEN_HEIGHT-(SCREEN_HEIGHT*.15)), (int) SCREEN_WIDTH, (int) (SCREEN_HEIGHT/0.95));
        rotatingPlatform = new RotatingPlatform(700, 400, 200, 10);
        stationaryPlatform = new StationaryPlatform(200, 550, 100, 20);
        s1 = new StationaryPlatform(300, 450, 100, 20);
        s2 = new StationaryPlatform(400, 350, 100, 20);
        GL11.glClearColor(1f, 1f, 1f, 1f);
    }

    public Scene nextScene() { return null; }

    public boolean drawFrame(float delta) throws IOException {

        Projectile bullet;

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        background.draw(); 
        
        stickman.update(delta);
        stickman.draw();
        stickman.testCollision(rotatingPlatform);
        stickman.testCollision(stationaryPlatform);
        stickman.testCollision(s2);
        stickman.testCollision(s1);
        
        rotatingPlatform.update(delta);
        rotatingPlatform.draw(); 	
        stationaryPlatform.draw();
        s1.draw();
        s2.draw();
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(stickman.getX()-(SCREEN_WIDTH/2), stickman.getX()+(SCREEN_WIDTH/2), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        Iterator<Projectile> it= bullets.iterator();
        while (it.hasNext())
        {
            bullet = it.next();
            
            bullet.update(delta);

            if (! bullet.isActive())
            {
                System.out.println("removing inactive projectile");
                it.remove();
            }
            else 
            {
                bullet.draw();
                target.testCollision(bullet);
            }
        }

        target.update(delta);
        target.draw();
        
        return true;
    }

}
