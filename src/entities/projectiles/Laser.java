package entities.projectiles;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Laser extends Projectile {

	protected static final int WIDTH=20;
    protected static final int HEIGHT=10;
    private static final float SPEED=1f;
    public static final int DAMAGE = 8;
	
    public Laser(int x, int y, int direction) throws IOException {
		super(x, y, WIDTH, HEIGHT, direction);
		
		velocity = new Vector2f(direction*SPEED, -.002f);
		audioPath = "blaster";
		setColor(0,0,1); // blue
		setDamage(DAMAGE);
	}
	
}
