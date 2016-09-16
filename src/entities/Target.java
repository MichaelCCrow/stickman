package entities;
import org.lwjgl.opengl.GL11;

import game.AudioManager;

import java.io.IOException;
import java.util.Random;
import org.lwjgl.opengl.Display;

public class Target extends Entity {

    private float r,g,b;
    private Random rand = new Random();

    public Target() throws IOException {
        super(0, 0, 30, 30);
        randomize();
        am = AudioManager.getInstance();
    	am.loadSample("explode", "res/Sounds/Bomb_Explosion.ogg");
    }

    private void randomize() {
        int x = rand.nextInt(Display.getWidth());
        int y = rand.nextInt(Display.getHeight()-100);
        
        if(checkBounds(y)) this.hitbox.setLocation(x,y);

        r = rand.nextFloat();
        g = rand.nextFloat();
        b = rand.nextFloat();
    }
    
    private boolean checkBounds(int y) {
    	if (y > 400) {
    		randomize();
    		return false;
    	}
    	if (y < 300) {
    		randomize();
    		return false;
    	}
    	return true;
    }


    public void onCollision(Entity other) {
        System.out.println("The target has been hit!");
        am.play("explode", 1.0f);
        randomize();
    }


    public void draw() {

        float x = hitbox.getX();
        float y = hitbox.getY();
        float w = hitbox.getWidth();
        float h = hitbox.getHeight();
        
        GL11.glColor3f(r,g,b);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);

        GL11.glEnd();            

    }




}
