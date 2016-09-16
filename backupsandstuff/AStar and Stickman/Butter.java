import java.io.IOException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;;

public class Butter extends Projectile {

	protected static final int WIDTH=40;
    protected static final int HEIGHT=20;
    private static final float SPEED=.2f;
	
	public Butter(int x, int y, int direction) throws IOException {
		super(x, y, WIDTH, HEIGHT, direction);
		velocity = new Vector2f(direction*SPEED, -.002f);
		audioPath = "butter";
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
	
	public void spinButter() {
		
//		while(this.isActive()) {
	        GL11.glRotatef(45f,1,0,0);
//		}
	}
    
    public void draw() {

        float x = hitbox.getX();
        float y = hitbox.getY();
        float w = hitbox.getWidth();
        float h = hitbox.getHeight();

        GL11.glColor3f(1,1,0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);
        // spinButter();

        GL11.glEnd();            

    }
	

}
