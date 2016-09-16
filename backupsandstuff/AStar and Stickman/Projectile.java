import org.lwjgl.util.Rectangle;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;


public class Projectile extends Entity {

//    protected static final int WIDTH=20;
//    protected static final int HEIGHT=10;
    private static final float SPEED=1f;
    float r,g,b;

    protected Vector2f velocity;
    private float mass;

    // initial x,y.  direction should be 1 to go right, -1 to go left
    public Projectile(int x, int y, int w, int h, int direction) throws IOException {
        super(x,y,w,h);
//        velocity = new Vector2f(direction*.7f, -.002f);

    }

	
    float c=0;
//    public void update(float delta) {
//
//        float x = hitbox.getX();
//        float y = hitbox.getY();
//        
//        // apply gravity
//        Vector2f.add(velocity,
//                     (Vector2f)new Vector2f(0, .0001f).scale(delta),
//                     velocity);
//
//
//        x += velocity.getX()*delta;
//        y += velocity.getY()*delta;
//
//
//        if (x < 0 - hitbox.getWidth() || x > Display.getWidth())
//        {
//            this.deactivate();
//        }
//
//        hitbox.setLocation((int)x,(int)y);
//    }
//    
//    public void draw() {
//
//        float x = hitbox.getX();
//        float y = hitbox.getY();
//        float w = hitbox.getWidth();
//        float h = hitbox.getHeight();
//
//        GL11.glColor3f(1,0,0);
//        GL11.glBegin(GL11.GL_QUADS);
//
//        GL11.glVertex2f(x,y);
//        GL11.glVertex2f(x+w,y);
//        GL11.glVertex2f(x+w,y+h);
//        GL11.glVertex2f(x,y+h);
//
//        GL11.glEnd();            
//
//    }

    
}