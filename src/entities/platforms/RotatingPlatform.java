package entities.platforms;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import entities.Entity;

public class RotatingPlatform extends Platform {
	
	float angle = 1; 
	
	public RotatingPlatform(int x, int y, int w, int h) {
		super(x,y,w,h);
		randomize();
		setColor(r,g,b);
	}

	public void update(float delta) {
		int x = hitbox.getX();
		int y = hitbox.getY();
		int w = hitbox.getWidth();
		int h = hitbox.getHeight();
		angle += delta/20;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x+w/2, y+h/2, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-x-w/2, -y-h/2, 0);					
		
	}
}
