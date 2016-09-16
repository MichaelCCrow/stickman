package entities.projectiles;
import java.io.IOException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;;

public class GameBreakerBullet extends Projectile {

	protected static final int WIDTH=40;
    protected static final int HEIGHT=20;
    private static final float SPEED=.2f;
    private float angle = 1;
    // private int counter=0;
	
	public GameBreakerBullet(int x, int y, int direction) throws IOException {
		super(x, y, WIDTH, HEIGHT, direction);
		
		velocity = new Vector2f(direction*SPEED, -.002f);
		audioPath = "butter";
		setColor(1,1,0); // yellow
	}
	
	public void update(float delta) {

        float x = hitbox.getX();
        float y = hitbox.getY();
        counter+=delta;
        
        // apply gravity
        Vector2f.add(velocity,
                     (Vector2f)new Vector2f(0, .0001f).scale(delta),
                     velocity);


        x += velocity.getX()*delta;
        y += velocity.getY()*delta;


//        if (x < 0 - hitbox.getWidth() || x > Display.getWidth())
        if(counter >= 5000)
        {
            this.deactivate();
            counter=0;
        }

        int w = hitbox.getWidth();
		int h = hitbox.getHeight();
		angle += delta/20;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x+w/2, y+h/2, 0);
		GL11.glRotatef(angle, 0, 0, 1);
		GL11.glTranslatef(-x-w/2, -y-h/2, 0);
        hitbox.setLocation((int)x,(int)y);
        
    }
	
}
