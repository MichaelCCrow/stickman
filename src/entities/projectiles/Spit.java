package entities.projectiles;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

public class Spit extends Projectile {

	protected static final int WIDTH=20;
    protected static final int HEIGHT=20;
    private static final float SPEED=.1f;
    public static final int DAMAGE = 3;
    
	
    public Spit(int x, int y, int direction) throws IOException {
		super(x, y, WIDTH, HEIGHT, direction);
		
		velocity = new Vector2f(direction*SPEED, -.002f);
		setColor(0,1,0); // green
		setDamage(DAMAGE);
	}
	
}
