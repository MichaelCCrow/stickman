package scenes;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

import java.util.LinkedList;
import java.io.IOException;
import java.util.Iterator;

import entities.*;
import entities.platforms.RotatingPlatform;
import entities.platforms.StationaryPlatform;
import entities.projectiles.Projectile;
import entities.weapons.GameBreaker;
import entities.weapons.items.*;
import game.*;


public class LevelTest extends Scene {

    private LinkedList<Projectile> bullets;
    private StickmanTest stickman;
    private Target target;
    private RotatingPlatform rotatingPlatform;
    private StationaryPlatform stationaryPlatform, s1, s2;
    public int SCREEN_WIDTH = Entity.SCREEN_WIDTH;
    public int SCREEN_HEIGHT = Entity.SCREEN_HEIGHT;
    int threshold, placeholder;
    private LinkedList<Item> weaponItems;
    private LinkedList<GroundFloor> gflist;
    private LinkedList<Alien> aliens;
    private Alien alien, alien2;
    private TanisBoss tb;
    private RifleItem droppedRifle;
    private ButterGunItem droppedButter;
    

    public LevelTest() throws IOException, SlickException
    {
        bullets = new LinkedList<>();
        stickman = new StickmanTest(bullets, 0,300,150,150);
        target = new Target();
        aliens = new LinkedList<>();
        alien = new Alien(bullets, 800, 800);
        alien2 = new Alien(bullets, 1600, 800);
        aliens.add(alien);
        tb = new TanisBoss(bullets, 3000, 100);
        
        threshold = (int) (SCREEN_WIDTH - (SCREEN_WIDTH/1.5));
        placeholder = stickman.getX();
        
        weaponItems = new LinkedList<>();
        addSceneItems();
        droppedRifle = new RifleItem(800, 500, 140, 75);
        droppedButter = new ButterGunItem(1600, 400, 150, 50);
        
        gflist = new LinkedList<>();
        for (int i=-1; i<5; i++) {
        	gflist.add(drawMoreFloor(i));
        }
        
        rotatingPlatform = new RotatingPlatform(700, 400, 200, 10);
        stationaryPlatform = new StationaryPlatform(200, 550, 100, 20);
        s1 = new StationaryPlatform(400, 450, 100, 20);
        s2 = new StationaryPlatform(600, 350, 100, 20);
        GL11.glClearColor(1f, 1f, 1f, 1f);
    }
    
    public void addSceneItems() {
//    	weaponItems.add(new RifleItem(200, 500, 140, 75));
//    	weaponItems.add(new ButterGunItem(400, 400, 150, 50));
//    	weaponItems.add(new GameBreakerItem(600, 270, 150, 75));
    }
    boolean a = false;
    public boolean makeActive() {
    	a = true;
    	return a;
    }
    public void addEnemies() {
    	try {
			aliens.add(new Alien(bullets, 800, 1200));
			aliens.add(new Alien(bullets, 1200, 800));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    public Scene nextScene() { return null; }
    
    private void resetFrame(int x) {
    	
    	if (x > placeholder + threshold) {
    		
    		placeholder += threshold;
    	}
    	
    	if (x < placeholder - threshold) {
    		
    		placeholder -= threshold;
    	}
    	
    	GL11.glOrtho(placeholder - (SCREEN_WIDTH/2), placeholder + (SCREEN_WIDTH/2), Display.getHeight(), 0, 1, -1);
    }
    
    private GroundFloor drawMoreFloor(int i) {
    	return new GroundFloor("res/Background/Bricks.png", (SCREEN_WIDTH*i), (int) (SCREEN_HEIGHT-(SCREEN_HEIGHT*.15)), (int) SCREEN_WIDTH, (int) (SCREEN_HEIGHT/0.95));	
    }
    
    
    float anotherCounter=0;
    float yetanotherCounter=0;
    public boolean drawFrame(float delta) throws IOException {

        Projectile bullet;

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(1, 1, 1, 1);
        
        Iterator<GroundFloor> gfit = gflist.iterator();
        GroundFloor gf;
        while (gfit.hasNext()) {
        	gf = gfit.next();
        	gf.draw();
        }
        
        stickman.update(delta);
        stickman.draw();
        stickman.testCollision(rotatingPlatform); 	stickman.testCollision(stationaryPlatform);
        stickman.testCollision(s2); 				stickman.testCollision(s1);

        if (alien.isActive()) {
        	alien.update(delta);
        	alien.draw();
        }
        if (alien2.isActive()) {
        	alien2.update(delta);
        	alien2.draw();
        }
        if (!alien.isActive()) {
        	if (droppedRifle.isActive()) {
        		droppedRifle.draw();
        		droppedRifle.testCollision(stickman);
        	}
        }
        if (!alien2.isActive()) {
        	if (droppedButter.isActive()) {
        		droppedButter.draw();
        		droppedButter.testCollision(stickman);
        	}
        }
        
//        Iterator<Alien> eit = aliens.iterator();
//        Alien e;
//        while (eit.hasNext()) {
//        	e = eit.next();
//        	if (e.isActive()) {
//        		alien.update(delta);
//        		alien.draw();
//        		alien2.update(delta);
//        		alien2.draw();
//        	} else {
//        		if (droppedRifle.isActive()) {
//        			droppedRifle.draw();
//        		}
//        		droppedRifle.testCollision(stickman);
//        		if (droppedButter.isActive()) {
//        			droppedButter.draw();
//        		}
//        		droppedRifle.testCollision(stickman);
//        	}
//        }
        
        Item item;
        Iterator<Item> wit = weaponItems.iterator();
        while (wit.hasNext()) {
        	item = wit.next();
        	item.testCollision(stickman);
        	if (item.isActive()) item.draw();
        }
        
        rotatingPlatform.update(delta);
        rotatingPlatform.draw(); 	
        stationaryPlatform.draw();
        s1.draw();
        s2.draw();
        
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        resetFrame(stickman.getX());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        Iterator<Projectile> it = bullets.iterator();
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
                
                alien.testCollision(bullet);
                alien.testCollision(stickman);
                alien2.testCollision(bullet);
                stickman.testCollision(bullet);
                tb.testCollision(bullet);
                tb.testCollision(stickman);
            }
        }

        target.update(delta);
        target.draw();

        
        if (tb.isActive()) {
        	tb.draw();
        	tb.update(delta);
        } else { makeActive();
        	if (anotherCounter < 20) {
        		anotherCounter+=delta;
        		if (a)  weaponItems.add(new GameBreakerItem(tb.getX(), 500, 150, 75));
        	}
        }
        if (GameBreaker.shot) {
        	if (yetanotherCounter < 5000) {
        		yetanotherCounter+=delta;
        	} else System.exit(1);
        }
        if (stickman.testReturnToMenu()) {
        	stickman.returnToMenu(false);
        	exit();
        }
        	
                
        return true;
    }

}
