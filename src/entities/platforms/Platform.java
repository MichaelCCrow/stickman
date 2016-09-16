package entities.platforms;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import entities.Entity;

public class Platform extends Entity {
	 
	float r,g,b;
	private Random rand = new Random();
	
	public Platform(int x, int y, int w, int h) {
		super(x,y,w,h);
	}
	
	public void setColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void randomize() {
//		int x = rand.nextInt(Display.getWidth());
//      int y = rand.nextInt(Display.getHeight()-100);
//		this.hitbox.setLocation(x,y);

        this.r = rand.nextFloat();
        this.g = rand.nextFloat();
        this.b = rand.nextFloat();
    }
	
	public void onCollision(Entity other) {
		randomize();
		setColor(r,g,b);
	}
	
	public void draw() {
		int x = hitbox.getX();
		int y = hitbox.getY();
		int w = hitbox.getWidth();
		int h = hitbox.getHeight();
		
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(r,g,b);
        
        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);
        
        GL11.glEnd();
        GL11.glPopMatrix();
	}
	
}
