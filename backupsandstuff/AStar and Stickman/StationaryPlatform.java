import java.util.Random;

import org.lwjgl.opengl.GL11;

public class StationaryPlatform extends Platform {
	
	private float r,g,b;
	private Random rand = new Random();
	public StationaryPlatform(int x, int y, int w, int h) {
		super(x,y,w,h);
		r = rand.nextFloat();
		g = rand.nextFloat();
		b = rand.nextFloat();
	}
	public void update(float delta) {};
	
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
	}
}
