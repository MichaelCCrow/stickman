import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class RotatingPlatform extends Platform {
	
	float angle = 1; float r,g,b;
	private Random rand = new Random();
	
	public RotatingPlatform(int x, int y, int w, int h) {
		super(x,y,w,h);
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
	
	private void randomize() {
        int x = rand.nextInt(Display.getWidth());
        int y = rand.nextInt(Display.getHeight()-100);
        
       this.hitbox.setLocation(x,y);

        r = rand.nextFloat();
        g = rand.nextFloat();
        b = rand.nextFloat();
    }
	
	public void onCollision(Entity other) {
//		randomize();
	}
	
	public void draw() {
		int x = hitbox.getX();
		int y = hitbox.getY();
		int w = hitbox.getWidth();
		int h = hitbox.getHeight();
		
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(1,0,1);
        
        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);
        
        GL11.glEnd();
        GL11.glPopMatrix();
	}
	
}
