import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Laser extends Projectile {

	protected static final int WIDTH=20;
    protected static final int HEIGHT=10;
    private static final float SPEED=1f;
	
    public Laser(int x, int y, int direction) throws IOException {
		super(x, y, WIDTH, HEIGHT, direction);
		velocity = new Vector2f(direction*SPEED, -.002f);
		audioPath = "blaster";
	}
	
	public void update(float delta) {

        float x = hitbox.getX();
        float y = hitbox.getY();
        
        // apply gravity
        Vector2f.add(velocity,
                     (Vector2f)new Vector2f(0, .0001f).scale(delta),
                     velocity);


        x += velocity.getX()*delta;
        y += velocity.getY()*delta;

        if (x < 0 - hitbox.getWidth() || x > Display.getWidth())
        {
            this.deactivate();
        }

        hitbox.setLocation((int)x,(int)y);
    }
    
    public void draw() {

        float x = hitbox.getX();
        float y = hitbox.getY();
        float w = hitbox.getWidth();
        float h = hitbox.getHeight();

        GL11.glColor3f(0,0,1);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);

        GL11.glEnd();            

    }	
}
